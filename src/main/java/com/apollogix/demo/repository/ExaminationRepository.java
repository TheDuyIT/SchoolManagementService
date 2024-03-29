package com.apollogix.demo.repository;

import com.apollogix.demo.domain.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ExaminationRepository extends JpaRepository<Examination, Long>, QuerydslPredicateExecutor<Examination> {
}
