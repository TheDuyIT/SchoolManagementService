package com.apollogix.demo.repository;

import com.apollogix.demo.domain.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long>, QuerydslPredicateExecutor<Examination> {
}
