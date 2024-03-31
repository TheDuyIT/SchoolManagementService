package com.apollogix.demo.repository;

import com.apollogix.demo.domain.UserExamination;
import com.apollogix.demo.domain.UserQuestionAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuestionAnswerRepository extends CrudRepository<UserQuestionAnswer, Long> {
}
