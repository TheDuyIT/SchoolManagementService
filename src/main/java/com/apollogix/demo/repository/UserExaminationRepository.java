package com.apollogix.demo.repository;

import com.apollogix.demo.domain.UserExamination;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserExaminationRepository extends CrudRepository<UserExamination, Long> {
}
