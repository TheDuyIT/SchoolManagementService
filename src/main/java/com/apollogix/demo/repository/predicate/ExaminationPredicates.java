package com.apollogix.demo.repository.predicate;

import com.apollogix.demo.domain.QExamination;
import com.apollogix.web.rest.model.ExaminationCriteria;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.Optional;

@UtilityClass
public class ExaminationPredicates {

    public Predicate byCriteria(final ExaminationCriteria criteria) {
        if (Objects.isNull(criteria)) {
            return new BooleanBuilder();
        }

        var query = new BooleanBuilder();

        // id
        Optional.ofNullable(criteria.getId())
                .map(QExamination.examination.id::eq).ifPresent(query::and);

        // title
        Optional.ofNullable(criteria.getTitle())
                .map(val -> String.format("%s%s%s", "%", val, "%"))
                .map(QExamination.examination.title::likeIgnoreCase).ifPresent(query::and);

        // duration
        Optional.ofNullable(criteria.getDurationInMinute())
                .map(val -> val + 1e-9)
                .map(QExamination.examination.durationInMinute::loe).ifPresent(query::and);
        return query;
    }
}