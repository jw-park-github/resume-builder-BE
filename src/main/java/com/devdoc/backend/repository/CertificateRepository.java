package com.devdoc.backend.repository;

import com.devdoc.backend.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Integer> {
    Optional<Certificate> findByIdAndResumeId(Integer id, Integer resumeId);
}

