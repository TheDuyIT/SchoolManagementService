package com.apollogix.demo.repository;

import com.apollogix.demo.domain.UserExamination;
import com.apollogix.demo.domain.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserExaminationRepository extends CrudRepository<UserExamination, Long> {

    Page<UserExamination> findByUser(UserInfo user, Pageable pageable);
}
