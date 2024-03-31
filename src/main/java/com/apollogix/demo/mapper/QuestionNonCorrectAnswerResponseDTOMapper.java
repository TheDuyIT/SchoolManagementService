package com.apollogix.demo.mapper;

import com.apollogix.demo.domain.Question;
import com.apollogix.web.rest.model.QuestionNonCorrectAnswerResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionNonCorrectAnswerResponseDTOMapper
        extends EntityMapper<QuestionNonCorrectAnswerResponseDTO, Question> {
}
