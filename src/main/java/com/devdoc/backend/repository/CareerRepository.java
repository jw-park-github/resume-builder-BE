package com.devdoc.backend.repository;

import com.devdoc.backend.model.Career;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CareerRepository extends JpaRepository<Career, Integer> {
    Optional<Career> findByIdAndResumeId(Integer CareerId, Integer resumeId);
}
