package com.apollogix.demo.service;

import com.apollogix.web.rest.model.ExaminationAssignmentRequestDTO;
import com.apollogix.web.rest.model.ExaminationAssignmentResponseDTO;
import com.apollogix.web.rest.model.ExaminationCriteria;
import com.apollogix.web.rest.model.ExaminationRequestDTO;
import com.apollogix.web.rest.model.ExaminationResponseDTO;
import com.apollogix.web.rest.model.ExaminationStudentResponsePaginatedDTO;
import com.apollogix.web.rest.model.ExaminationWithoutAnswerResponseDTO;
import com.apollogix.web.rest.model.SubmitExaminationRequestDTO;
import com.apollogix.web.rest.model.SubmitExaminationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExaminationService {
    ExaminationResponseDTO createExamination(ExaminationRequestDTO examinationRequestDTO);

    Page<ExaminationResponseDTO> findByCriteria(ExaminationCriteria criteria, Pageable pageable);

    ExaminationAssignmentResponseDTO assignExaminationForStudent(ExaminationAssignmentRequestDTO requestDTO);

    ExaminationStudentResponsePaginatedDTO fetchExaminationNonCorrectAnswerUsingGet(Pageable pageable);

    ExaminationWithoutAnswerResponseDTO startExamination(Long examinationId);

    SubmitExaminationResponseDTO submitExamination(Long examId, List<SubmitExaminationRequestDTO> requestDTO);
}
