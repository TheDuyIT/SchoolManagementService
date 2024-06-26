package com.apollogix.demo.repository;

import com.apollogix.demo.domain.Examination;
import com.apollogix.demo.domain.UserExamination;
import com.apollogix.demo.domain.UserInfo;
import com.apollogix.demo.domain.enums.ExamStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserExaminationRepository extends CrudRepository<UserExamination, Long> {

    Page<UserExamination> findByUser(UserInfo user, Pageable pageable);

    Optional<UserExamination> findByUserAndExamination_IdAndStatus(UserInfo user, Long examId, ExamStatus status);

    Set<UserExamination> findByUserInAndExamination(HashSet<UserInfo> existingStudents, Examination examination);
}
