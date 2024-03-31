package com.apollogix.demo.mapper;

import com.apollogix.demo.domain.Examination;
import com.apollogix.web.rest.model.ExaminationResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {QuestionResponseDTOMapper.class})
public interface ExaminationResponseDTOMapper extends EntityMapper<ExaminationResponseDTO, Examination>{
}
