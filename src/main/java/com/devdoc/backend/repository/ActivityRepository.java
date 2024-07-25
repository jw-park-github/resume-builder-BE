package com.devdoc.backend.repository;

import com.devdoc.backend.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    Optional<Activity> findByIdAndResumeId(Integer id, Integer resumeId);
}
