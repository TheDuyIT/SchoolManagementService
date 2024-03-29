package com.apollogix.demo.service.impl;

import com.apollogix.demo.mapper.QuestionRequestDTOMapper;
import com.apollogix.demo.mapper.QuestionResponseDTOMapper;
import com.apollogix.demo.repository.QuestionRepository;
import com.apollogix.demo.repository.predicate.QuestionPredicates;
import com.apollogix.demo.service.QuestionService;
import com.apollogix.demo.web.errors.BadRequestException;
import com.apollogix.web.rest.model.QuestionCriteria;
import com.apollogix.web.rest.model.QuestionRequestDTO;
import com.apollogix.web.rest.model.QuestionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionResponseDTOMapper questionResponseDTOMapper;
    private final QuestionRequestDTOMapper questionRequestDTOMapper;

    @Override
    public QuestionResponseDTO createQuestion(QuestionRequestDTO dto) {
        if(!(dto.getCorrectAnswer() >= 0 && dto.getCorrectAnswer() <= 3))
            throw new BadRequestException("Correct answer must be between 0 and 3");
        var question = questionRequestDTOMapper.toEntity(dto);
        return questionResponseDTOMapper.toDTO(questionRepository.save(question));
    }

    @Override
    public Page<QuestionResponseDTO> findByCriteria(QuestionCriteria criteria, Pageable pageable) {
        var predicate = QuestionPredicates.byCriteria(criteria);
        return questionRepository.findAll(predicate, pageable).map(questionResponseDTOMapper::toDTO);

    }
}
