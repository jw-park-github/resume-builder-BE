package com.devdoc.backend.repository;

import com.devdoc.backend.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EducationRepository extends JpaRepository<Education, Integer> {
    Optional<Education> findByIdAndResumeId(Integer id, Integer resumeId);
}
