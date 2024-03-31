package com.apollogix.demo.service.impl;

import com.apollogix.demo.domain.Question;
import com.apollogix.demo.domain.UserExamination;
import com.apollogix.demo.domain.UserInfo;
import com.apollogix.demo.mapper.ExaminationRequestDTOMapper;
import com.apollogix.demo.mapper.ExaminationResponseDTOMapper;
import com.apollogix.demo.mapper.UserResponseDTOMapper;
import com.apollogix.demo.repository.ExaminationRepository;
import com.apollogix.demo.repository.QuestionRepository;
import com.apollogix.demo.repository.UserExaminationRepository;
import com.apollogix.demo.repository.UserInfoRepository;
import com.apollogix.demo.repository.predicate.ExaminationPredicates;
import com.apollogix.demo.service.ExaminationService;
import com.apollogix.web.rest.model.ExaminationAssignmentRequestDTO;
import com.apollogix.web.rest.model.ExaminationAssignmentResponseDTO;
import com.apollogix.web.rest.model.ExaminationCriteria;
import com.apollogix.web.rest.model.ExaminationRequestDTO;
import com.apollogix.web.rest.model.ExaminationResponseDTO;
import com.apollogix.web.rest.model.IdWrapperDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        Objects.requireNonNull(requestDTO.getExamination(), "Field examination must not be null");
        Collection<IdWrapperDTO> students = requestDTO.getStudents();
        Objects.requireNonNull(students, "Field students must not be null");

        var examination = examinationRepository.findById(requestDTO.getExamination().getId())
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
                .map(student -> UserExamination.builder().user(student).examination(examination).build())
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
}
