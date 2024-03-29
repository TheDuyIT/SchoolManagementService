package com.apollogix.demo.mapper;

import com.apollogix.demo.domain.Question;
import com.apollogix.web.rest.model.QuestionResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionResponseDTOMapper extends EntityMapper<QuestionResponseDTO, Question>{
}
