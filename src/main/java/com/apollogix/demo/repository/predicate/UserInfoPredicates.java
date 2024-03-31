package com.apollogix.demo.repository.predicate;

import com.apollogix.demo.domain.QUserInfo;
import com.apollogix.demo.domain.enums.Role;
import com.apollogix.web.rest.model.UserCriteria;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@UtilityClass
public class UserInfoPredicates {

    public Predicate byCriteria(final UserCriteria criteria) {
        if (Objects.isNull(criteria)) {
            return new BooleanBuilder();
        }
        var query = new BooleanBuilder();

        byEmailIn(criteria.getEmail()).ifPresent(query::and);
        byFirstnameIn(criteria.getFirstname()).ifPresent(query::and);
        byLastnameIn(criteria.getLastname()).ifPresent(query::and);
        byRoleIn(criteria.getRole()).ifPresent(query::and);
        return query;
    }

    private static Optional<Predicate> byRoleIn(@Valid List<com.apollogix.web.rest.model.@Valid Role> args) {
        if (CollectionUtils.isEmpty(args)) {
            return Optional.empty();
        }
        List<Role> roles = args.stream()
                .map(com.apollogix.web.rest.model.Role::getValue)
                .map(Role::valueOf)
                .toList();
        return Optional.of(QUserInfo.userInfo.role.in(roles));
    }

    private static Optional<Predicate> byLastnameIn(List<String> args) {
        return Optional.ofNullable(args)
                .filter(CollectionUtils::isNotEmpty)
                .map(QUserInfo.userInfo.lastname::in);
    }

    private static Optional<Predicate> byFirstnameIn(List<String> args) {
        return Optional.ofNullable(args)
                .filter(CollectionUtils::isNotEmpty)
                .map(QUserInfo.userInfo.firstname::in);
    }

    private static Optional<Predicate> byEmailIn(List<String> args) {
        return Optional.ofNullable(args)
                .filter(CollectionUtils::isNotEmpty)
                .map(QUserInfo.userInfo.email::in);
    }
}