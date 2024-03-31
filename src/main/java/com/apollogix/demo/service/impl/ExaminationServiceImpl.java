package com.apollogix.demo.service.impl;

import com.apollogix.demo.domain.Question;
import com.apollogix.demo.domain.UserExamination;
import com.apollogix.demo.domain.UserInfo;
import com.apollogix.demo.domain.enums.ExamStatus;
import com.apollogix.demo.mapper.ExaminationRequestDTOMapper;
import com.apollogix.demo.mapper.ExaminationResponseDTOMapper;
import com.apollogix.demo.mapper.ExaminationWithoutAnswerResponseDTOMapper;
import com.apollogix.demo.mapper.UserResponseDTOMapper;
import com.apollogix.demo.repository.ExaminationRepository;
import com.apollogix.demo.repository.QuestionRepository;
import com.apollogix.demo.repository.UserExaminationRepository;
import com.apollogix.demo.repository.UserInfoRepository;
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
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

        var userExam = userExaminationRepository.findByUserAndExamination_IdAndStatus(user, examinationId, ExamStatus.INITIAL)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format("No examination with Id %d and status %s is assigned to user with email %s!",
                                        examinationId, ExamStatus.INITIAL, user.getEmail())
                        )
                );
        userExam.setStartDoingTime(LocalDateTime.now());
        userExam.setStatus(ExamStatus.DOING);
        userExaminationRepository.save(userExam);
        var responseDTO = examinationWithoutAnswerResponseDTOMapper.toDTO(userExam.getExamination());
        responseDTO.setStatus(userExam.getStatus().name());
        return responseDTO;
    }
}
