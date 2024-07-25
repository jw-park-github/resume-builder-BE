package com.devdoc.backend.controller;

import com.devdoc.backend.dto.EducationDTO;
import com.devdoc.backend.service.EducationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumes")
@Tag(name = "Education Management", description = "학력(Education) 항목 관리 API")
public class EducationController {

    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @Operation(summary = "학력 업데이트/저장")
    @PostMapping("/{resumeId}/educations")
    public ResponseEntity<EducationDTO> saveOrUpdateEducation(@PathVariable int resumeId, @RequestBody EducationDTO educationDTO) {
        try {
            EducationDTO updatedEducation = educationService.saveOrUpdateEducation(resumeId, educationDTO);
            return ResponseEntity.ok(updatedEducation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "학력 삭제")
    @DeleteMapping("/{resumeId}/educations/{educationId}")
    public ResponseEntity<Void> deleteEducation(@PathVariable int resumeId, @PathVariable int educationId) {
        try {
            educationService.deleteEducation(resumeId, educationId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
