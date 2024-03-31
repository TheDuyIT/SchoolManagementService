package com.apollogix.demo.domain;

import com.apollogix.demo.domain.enums.ExamStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table
@AllArgsConstructor
@RequiredArgsConstructor
public class UserExamination extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private double score;
    private ExamStatus status;
    private LocalDateTime startDoingTime;
    private LocalDateTime doneTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;

    @ManyToOne
    @JoinColumn(name = "examination_id")
    private Examination examination;
}
