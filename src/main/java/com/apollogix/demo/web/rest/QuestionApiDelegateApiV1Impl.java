package com.apollogix.demo.web.rest;

import com.apollogix.demo.config.AuthoritiesConstants;
import com.apollogix.demo.service.QuestionService;
import com.apollogix.web.rest.api.QuestionV1ApiDelegate;
import com.apollogix.web.rest.model.QuestionCriteria;
import com.apollogix.web.rest.model.QuestionRequestDTO;
import com.apollogix.web.rest.model.QuestionResponseDTO;
import com.apollogix.web.rest.model.QuestionResponsePaginatedDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import static com.apollogix.demo.util.RestResponseUtil.fromPage;

@Component
@RequiredArgsConstructor
public class QuestionApiDelegateApiV1Impl implements QuestionV1ApiDelegate {
    private final QuestionService questionService;

    @Secured({AuthoritiesConstants.TEACHER})
    @Override
    public ResponseEntity<QuestionResponseDTO> createQuestionUsingPost(QuestionRequestDTO body) {
        return ResponseEntity.ok(questionService.createQuestion(body));
    }


    @Override
    public ResponseEntity<QuestionResponsePaginatedDTO> getQuestionByCriteria(QuestionCriteria criteria, Pageable pageable) {
        var page = questionService.findByCriteria(criteria, pageable);
        return ResponseEntity.ok(QuestionResponsePaginatedDTO.builder().pagination(fromPage(page)).payload(page.getContent()).build());
    }
}
