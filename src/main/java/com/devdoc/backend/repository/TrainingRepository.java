package com.devdoc.backend.repository;

import com.devdoc.backend.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainingRepository extends JpaRepository<Training, Integer> {
    Optional<Training> findByIdAndResumeId(Integer trainingId, Integer resumeId);
}
