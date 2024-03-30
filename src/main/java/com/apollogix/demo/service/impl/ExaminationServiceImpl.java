package com.apollogix.demo.service.impl;

import com.apollogix.demo.domain.Question;
import com.apollogix.demo.mapper.ExaminationRequestDTOMapper;
import com.apollogix.demo.mapper.ExaminationResponseDTOMapper;
import com.apollogix.demo.repository.ExaminationRepository;
import com.apollogix.demo.repository.QuestionRepository;
import com.apollogix.demo.repository.predicate.ExaminationPredicates;
import com.apollogix.demo.service.ExaminationService;
import com.apollogix.web.rest.model.BaseDTO;
import com.apollogix.web.rest.model.ExaminationCriteria;
import com.apollogix.web.rest.model.ExaminationRequestDTO;
import com.apollogix.web.rest.model.ExaminationResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ExaminationServiceImpl implements ExaminationService {

    private final ExaminationRepository examinationRepository;
    private final ExaminationResponseDTOMapper examinationResponseDTOMapper;
    private final ExaminationRequestDTOMapper examinationRequestDTOMapper;
    private final QuestionRepository questionRepository;


    @Override
    public ExaminationResponseDTO createExamination(ExaminationRequestDTO requestDTO) {
        var examination = examinationRequestDTOMapper.toEntity(requestDTO);
        var requestedQuestionIds = requestDTO.getQuestions().stream()
                .map(BaseDTO::getId)
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

}
