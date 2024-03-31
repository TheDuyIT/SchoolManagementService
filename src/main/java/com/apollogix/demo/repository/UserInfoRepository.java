package com.apollogix.demo.repository;

import com.apollogix.demo.domain.enums.Role;
import com.apollogix.demo.domain.UserInfo;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>, QuerydslPredicateExecutor<UserInfo> {
    Optional<UserInfo> findByEmail(@NonNull String email);

    List<UserInfo> findAllByIdInAndRole(@NonNull Collection<Long> ids, Role role);

    default List<UserInfo> findAllStudentById(@NonNull Collection<Long> ids) {
        return findAllByIdInAndRole(ids, Role.STUDENT);
    }
}
