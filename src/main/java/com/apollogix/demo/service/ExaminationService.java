package com.apollogix.demo.service;

import com.apollogix.web.rest.model.ExaminationRequestDTO;
import com.apollogix.web.rest.model.ExaminationResponseDTO;

public interface ExaminationService {
    ExaminationResponseDTO createExamination(ExaminationRequestDTO examinationRequestDTO);
}
