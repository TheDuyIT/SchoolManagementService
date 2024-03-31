package com.apollogix.demo.service;

import com.apollogix.web.rest.model.ExaminationAssignmentRequestDTO;
import com.apollogix.web.rest.model.ExaminationAssignmentResponseDTO;
import com.apollogix.web.rest.model.ExaminationCriteria;
import com.apollogix.web.rest.model.ExaminationRequestDTO;
import com.apollogix.web.rest.model.ExaminationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExaminationService {
    ExaminationResponseDTO createExamination(ExaminationRequestDTO examinationRequestDTO);

    Page<ExaminationResponseDTO> findByCriteria(ExaminationCriteria criteria, Pageable pageable);

    ExaminationAssignmentResponseDTO assignExaminationForStudent(ExaminationAssignmentRequestDTO requestDTO);
}
