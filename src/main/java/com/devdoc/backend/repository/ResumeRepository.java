package com.devdoc.backend.repository;

import com.devdoc.backend.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {
    @Query("SELECT DISTINCT r FROM Resume r LEFT JOIN FETCH r.aboutMe WHERE r.user.id = :userId")
    List<Resume> findByUserIdWithAboutMe(@Param("userId") String userId);
}