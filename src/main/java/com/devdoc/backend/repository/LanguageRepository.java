package com.devdoc.backend.repository;

import com.devdoc.backend.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
    Optional<Language> findByIdAndResumeId(Integer languageId, Integer resumeId);
}
