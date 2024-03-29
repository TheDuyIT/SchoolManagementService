package com.apollogix.demo.web.rest;

import com.apollogix.demo.config.AuthoritiesConstants;
import com.apollogix.demo.service.ExaminationService;
import com.apollogix.web.rest.api.ExaminationV1ApiDelegate;
import com.apollogix.web.rest.model.ExaminationRequestDTO;
import com.apollogix.web.rest.model.ExaminationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExaminationApiDelegateApiV1Impl implements ExaminationV1ApiDelegate {
    private final ExaminationService examinationService;


    @Secured({AuthoritiesConstants.TEACHER})
    @Override
    public ResponseEntity<ExaminationResponseDTO> createExaminationUsingPost(ExaminationRequestDTO examinationRequestDTO) {
        return ResponseEntity.ok(
                examinationService.createExamination(examinationRequestDTO)
        );
    }
}
