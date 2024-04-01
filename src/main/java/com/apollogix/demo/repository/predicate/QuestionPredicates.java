package com.apollogix.demo.repository.predicate;

import com.apollogix.demo.domain.QQuestion;
import com.apollogix.web.rest.model.QuestionCriteria;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Objects;
import java.util.Optional;

@UtilityClass
public class QuestionPredicates {

    public Predicate byCriteria(final QuestionCriteria criteria) {
        if (Objects.isNull(criteria)) {
            return new BooleanBuilder();
        }

        var query = new BooleanBuilder();

        // id
        Optional.ofNullable(criteria.getId())
                .filter(CollectionUtils::isNotEmpty)
                .map(QQuestion.question.id::in).ifPresent(query::and);

        // clause
        Optional.ofNullable(criteria.getClause())
                .filter(CollectionUtils::isNotEmpty)
                .map(QQuestion.question.clause::in).ifPresent(query::and);

        // correctAnswer
        Optional.ofNullable(criteria.getCorrectAnswer())
                .filter(CollectionUtils::isNotEmpty)
                .map(QQuestion.question.correctAnswer::in).ifPresent(query::and);

        // createdBy
        Optional.ofNullable(criteria.getCreatedBy())
                .filter(CollectionUtils::isNotEmpty)
                .map(QQuestion.question.createdBy::in).ifPresent(query::and);
        return query;
    }
}