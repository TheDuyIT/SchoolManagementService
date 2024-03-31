package com.apollogix.demo.mapper;

import com.apollogix.demo.domain.Examination;
import com.apollogix.web.rest.model.ExaminationWithoutAnswerResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {QuestionNonCorrectAnswerResponseDTOMapper.class})
public interface ExaminationWithoutAnswerResponseDTOMapper
        extends EntityMapper<ExaminationWithoutAnswerResponseDTO, Examination> {
}
