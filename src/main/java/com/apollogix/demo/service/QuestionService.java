package com.apollogix.demo.service;

import com.apollogix.web.rest.model.QuestionCriteria;
import com.apollogix.web.rest.model.QuestionRequestDTO;
import com.apollogix.web.rest.model.QuestionResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
    QuestionResponseDTO createQuestion(QuestionRequestDTO body);

    Page<QuestionResponseDTO> findByCriteria(QuestionCriteria criteria, Pageable pageable);
}
