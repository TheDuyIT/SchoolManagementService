package com.apollogix.demo.repository;

import com.apollogix.demo.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>, QuerydslPredicateExecutor<Question> {
}
