package com.apollogix.demo.mapper;

import com.apollogix.demo.domain.Question;
import com.apollogix.web.rest.model.QuestionRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionRequestDTOMapper extends EntityMapper<QuestionRequestDTO, Question>{
}
