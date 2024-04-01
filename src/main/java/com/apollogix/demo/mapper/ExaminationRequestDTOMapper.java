package com.apollogix.demo.mapper;

import com.apollogix.demo.domain.Examination;
import com.apollogix.web.rest.model.ExaminationRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {QuestionResponseDTOMapper.class})
public interface ExaminationRequestDTOMapper extends EntityMapper<ExaminationRequestDTO, Examination> {
}
