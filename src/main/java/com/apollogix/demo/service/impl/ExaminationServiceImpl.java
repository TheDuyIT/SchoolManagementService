package com.apollogix.demo.service.impl;

import com.apollogix.demo.domain.Examination;
import com.apollogix.demo.domain.Question;
import com.apollogix.demo.domain.UserExamination;
import com.apollogix.demo.domain.UserInfo;
import com.apollogix.demo.domain.UserQuestionAnswer;
import com.apollogix.demo.domain.enums.ExamStatus;
import com.apollogix.demo.mapper.ExaminationRequestDTOMapper;
import com.apollogix.demo.mapper.ExaminationResponseDTOMapper;
import com.apollogix.demo.mapper.ExaminationWithoutAnswerResponseDTOMapper;
import com.apollogix.demo.mapper.UserResponseDTOMapper;
import com.apollogix.demo.repository.ExaminationRepository;
import com.apollogix.demo.repository.QuestionRepository;
import com.apollogix.demo.repository.UserExaminationRepository;
import com.apollogix.demo.repository.UserInfoRepository;
import com.apollogix.demo.repository.UserQuestionAnswerRepository;
import com.apollogix.demo.repository.predicate.ExaminationPredicates;
import com.apollogix.demo.service.ExaminationService;
import com.apollogix.demo.util.SecurityUtil;
import com.apollogix.web.rest.model.ExaminationAssignmentRequestDTO;
import com.apollogix.web.rest.model.ExaminationAssignmentResponseDTO;
import com.apollogix.web.rest.model.ExaminationCriteria;
import com.apollogix.web.rest.model.ExaminationRequestDTO;
import com.apollogix.web.rest.model.ExaminationResponseDTO;
import com.apollogix.web.rest.model.ExaminationStudentResponsePaginatedDTO;
import com.apollogix.web.rest.model.ExaminationWithoutAnswerResponseDTO;
import com.apollogix.web.rest.model.IdWrapperDTO;
import com.apollogix.web.rest.model.SubmitExaminationRequestDTO;
import com.apollogix.web.rest.model.SubmitExaminationResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.apollogix.demo.util.RestResponseUtil.fromPage;

@Service
@Transactional
@RequiredArgsConstructor
public class ExaminationServiceImpl implements ExaminationService {

    private final ExaminationRepository examinationRepository;
    private final ExaminationResponseDTOMapper examinationResponseDTOMapper;
    private final ExaminationRequestDTOMapper examinationRequestDTOMapper;
    private final QuestionRepository questionRepository;
    private final UserInfoRepository userInfoRepository;
    private final UserExaminationRepository userExaminationRepository;
    private final UserResponseDTOMapper userResponseDTOMapper;
    private final ExaminationWithoutAnswerResponseDTOMapper examinationWithoutAnswerResponseDTOMapper;
    private final UserQuestionAnswerRepository userQuestionAnswerRepository;

    @Override
    public ExaminationResponseDTO createExamination(ExaminationRequestDTO requestDTO) {
        var examination = examinationRequestDTOMapper.toEntity(requestDTO);
        var requestedQuestionIds = requestDTO.getQuestions().stream()
                .map(IdWrapperDTO::getId)
                .collect(Collectors.toSet());
        var existingQuestions = new HashSet<>(questionRepository.findAllById(requestedQuestionIds));

        if (existingQuestions.size() != requestedQuestionIds.size()) {
            var foundQuestionIds = existingQuestions.stream()
                    .map(Question::getId)
                    .collect(Collectors.toSet());
            requestedQuestionIds.removeAll(foundQuestionIds);
            throw new EntityNotFoundException("Questions not found with IDs: " + requestedQuestionIds);
        }
        examination.setQuestions(existingQuestions);
        return examinationResponseDTOMapper.toDTO(examinationRepository.save(examination));
    }

    @Override
    public Page<ExaminationResponseDTO> findByCriteria(ExaminationCriteria criteria, Pageable pageable) {
        var predicate = ExaminationPredicates.byCriteria(criteria);
        return examinationRepository.findAll(predicate, pageable)
                .map(examinationResponseDTOMapper::toDTO);
    }

    @Override
    public ExaminationAssignmentResponseDTO assignExaminationForStudent(ExaminationAssignmentRequestDTO requestDTO) {
        Objects.requireNonNull(requestDTO.getExaminationId(), "Field examinationId must not be null");
        Collection<IdWrapperDTO> students = requestDTO.getStudents();
        Objects.requireNonNull(students, "Field students must not be null");

        var examination = examinationRepository.findById(requestDTO.getExaminationId())
                .orElseThrow(() -> new EntityNotFoundException("Examination not found"));

        var requestedStudentIds = students.stream()
                .map(IdWrapperDTO::getId)
                .collect(Collectors.toSet());
        var existingStudents = new HashSet<>(userInfoRepository.findAllStudentById(requestedStudentIds));
        if (existingStudents.size() != requestedStudentIds.size()) {
            var foundQuestionIds = existingStudents.stream()
                    .map(UserInfo::getId)
                    .collect(Collectors.toSet());
            requestedStudentIds.removeAll(foundQuestionIds);
            throw new EntityNotFoundException("Students not found with IDs: " + requestedStudentIds);
        }

        existingStudents = filterOutStudentsAlreadyAssigned(existingStudents, examination);

        var userExaminations = existingStudents.stream()
                .map(student -> UserExamination.builder().user(student).examination(examination).status(ExamStatus.INITIAL).build())
                .collect(Collectors.toSet());
        var savedUserExaminations = (List<UserExamination>) userExaminationRepository.saveAll(userExaminations);
        var savedExamination = savedUserExaminations.stream()
                .findFirst()
                .map(UserExamination::getExamination)
                .orElse(examination);

        var savedStudents = savedUserExaminations.stream()
                .map(UserExamination::getUser)
                .collect(Collectors.toSet());

        return ExaminationAssignmentResponseDTO.builder()
                .examination(examinationResponseDTOMapper.toDTO(savedExamination))
                .students(savedStudents.stream()
                        .map(userResponseDTOMapper::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    private HashSet<UserInfo> filterOutStudentsAlreadyAssigned(HashSet<UserInfo> existingStudents, Examination examination) {
        var alreadyAssignedStudents =
                userExaminationRepository.findByUserInAndExamination(existingStudents, examination).stream()
                .map(UserExamination::getUser)
                .collect(Collectors.toSet());
        existingStudents.removeAll(alreadyAssignedStudents);
        return existingStudents;
    }


    @Override
    public ExaminationStudentResponsePaginatedDTO fetchExaminationNonCorrectAnswerUsingGet(Pageable pageable) {
        UserInfo user = SecurityUtil.getCurrentUser();
        if (user == null) return null;

        var userExaminationsPage = userExaminationRepository.findByUser(user, pageable);
        var examinationDTOs = userExaminationsPage.getContent()
                .stream()
                .map(userExam -> {
                    var exam = examinationWithoutAnswerResponseDTOMapper.toDTO(userExam.getExamination());
                    exam.setStatus(userExam.getStatus().name());
                    if (EnumUtils.getEnum(ExamStatus.class, exam.getStatus()) == ExamStatus.INITIAL)
                        exam.setQuestions(null);
                    return exam;
                })
                .toList();
        return ExaminationStudentResponsePaginatedDTO.builder()
                .payload(examinationDTOs)
                .pagination(fromPage(userExaminationsPage))
                .build();
    }

    @Override
    public ExaminationWithoutAnswerResponseDTO startExamination(Long examinationId) {
        UserInfo user = SecurityUtil.getCurrentUser();
        if (user == null) return null;

        var userExam = getExamForUser(user, examinationId, ExamStatus.INITIAL);
        userExam.setStartDoingTime(LocalDateTime.now());
        userExam.setStatus(ExamStatus.DOING);
        userExaminationRepository.save(userExam);
        var responseDTO = examinationWithoutAnswerResponseDTOMapper.toDTO(userExam.getExamination());
        responseDTO.setStatus(userExam.getStatus().name());
        return responseDTO;
    }

    @Override
    public SubmitExaminationResponseDTO submitExamination(Long examId, List<SubmitExaminationRequestDTO> requestDTO) {
        UserInfo user = SecurityUtil.getCurrentUser();
        if (user == null) return null;
        var userExam = getExamForUser(user, examId, ExamStatus.DOING);
        var exam = userExam.getExamination();

        var qId2Q = exam.getQuestions()
                .stream().collect(Collectors.toMap(Question::getId, q -> q));

        int score = requestDTO.stream()
                .mapToInt(userQA -> {
                    Question question = qId2Q.get(userQA.getQuestionId());
                    return (question != null && question.getCorrectAnswer() == userQA.getAnswer()) ? 1 : 0;
                })
                .sum();
        var userInfo = userInfoRepository.findById(user.getId()); // Avoid no session
        var userQAs = requestDTO.stream()
                .map(userQA -> {
                    var question = questionRepository.findById(userQA.getQuestionId());
                    return question.map(value -> UserQuestionAnswer.builder()
                            .examination(exam)
                            .question(value)
                            .user(userInfo.get())
                            .userAnswer(userQA.getAnswer())
                            .build()).orElse(null);
                }).filter(Objects::nonNull)
                .collect(Collectors.toSet());

        userQuestionAnswerRepository.saveAll(userQAs);
        userExam.setScore(score);
        userExam.setStatus(ExamStatus.SUBMITTED);
        userExaminationRepository.save(userExam);


        return SubmitExaminationResponseDTO.builder()
                .score(BigDecimal.valueOf(score))
                .build();
    }

    private UserExamination getExamForUser(UserInfo user, Long examinationId, ExamStatus status) {
        return userExaminationRepository.findByUserAndExamination_IdAndStatus(user, examinationId, status)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format("No examination with Id %d and status %s is assigned to user with email %s!",
                                        examinationId, status, user.getEmail())
                        )
                );
    }
}
