package com.apollogix.demo.web.rest;

import com.apollogix.demo.config.AuthoritiesConstants;
import com.apollogix.demo.service.ExaminationService;
import com.apollogix.web.rest.api.ExaminationV1ApiDelegate;
import com.apollogix.web.rest.model.ExaminationAssignmentRequestDTO;
import com.apollogix.web.rest.model.ExaminationAssignmentResponseDTO;
import com.apollogix.web.rest.model.ExaminationCriteria;
import com.apollogix.web.rest.model.ExaminationRequestDTO;
import com.apollogix.web.rest.model.ExaminationResponseDTO;
import com.apollogix.web.rest.model.ExaminationResponsePaginatedDTO;
import com.apollogix.web.rest.model.ExaminationStudentResponsePaginatedDTO;
import com.apollogix.web.rest.model.ExaminationWithoutAnswerResponseDTO;
import com.apollogix.web.rest.model.SubmitExaminationRequestDTO;
import com.apollogix.web.rest.model.SubmitExaminationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.apollogix.demo.util.RestResponseUtil.fromPage;

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

    @Secured({AuthoritiesConstants.TEACHER})
    @Override
    public ResponseEntity<ExaminationResponsePaginatedDTO> getExaminationByCriteria(ExaminationCriteria criteria, Pageable pageable) {
        var pages = examinationService.findByCriteria(criteria, pageable);
        return ResponseEntity.ok(
                ExaminationResponsePaginatedDTO.builder()
                        .pagination(fromPage(pages))
                        .payload(pages.getContent())
                        .build()
        );
    }

    @Secured({AuthoritiesConstants.TEACHER})
    @Override
    public ResponseEntity<ExaminationAssignmentResponseDTO> assignExaminationForStudent(ExaminationAssignmentRequestDTO examinationAssignmentRequestDTO) {
        return ResponseEntity.ok(
                examinationService.assignExaminationForStudent(examinationAssignmentRequestDTO)
        );
    }

    @Secured({AuthoritiesConstants.STUDENT})
    @Override
    public ResponseEntity<ExaminationStudentResponsePaginatedDTO> fetchExaminationNonCorrectAnswerUsingGet(Pageable pageable) {
        return ResponseEntity.ok(
                examinationService.fetchExaminationNonCorrectAnswerUsingGet(pageable)
        );
    }

    @Secured({AuthoritiesConstants.STUDENT})
    @Override
    public ResponseEntity<ExaminationWithoutAnswerResponseDTO> startExamination(Long examinationId) {
        var responseDTO = examinationService.startExamination(examinationId);
        return ResponseEntity.ofNullable(responseDTO);
    }

    @Override
    public ResponseEntity<SubmitExaminationResponseDTO> submitExamination(Long id, List<@Valid SubmitExaminationRequestDTO> requestDTO) {
        return ResponseEntity.ok(
                examinationService.submitExamination(id, requestDTO)
        );
    }
}
