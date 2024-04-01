package com.apollogix.demo.domain;

import com.apollogix.demo.domain.enums.ExamStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
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
public class UserExamination extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGeneratorUserExamination")
    @SequenceGenerator(name = "sequenceGeneratorUserExamination", sequenceName = "sequence_generator_user_examination",
            allocationSize = 1, initialValue = 100)
    private Long id;

    private double score;
    @Enumerated(EnumType.STRING)
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
