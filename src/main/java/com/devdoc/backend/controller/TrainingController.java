package com.devdoc.backend.controller;

import com.devdoc.backend.dto.TrainingDTO;
import com.devdoc.backend.service.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumes")
@Tag(name = "Training Management", description = "교육 이수(Training) 항목 관리 API")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Operation(summary = "교육 이수 업데이트/저장")
    @PostMapping("/{resumeId}/trainings")
    public ResponseEntity<TrainingDTO> saveOrUpdateTraining(@PathVariable int resumeId, @RequestBody TrainingDTO trainingDTO) {
        try {
            TrainingDTO updatedTraining = trainingService.saveOrUpdateTraining(resumeId, trainingDTO);
            return ResponseEntity.ok(updatedTraining);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "교육 이수 삭제")
    @DeleteMapping("/{resumeId}/trainings/{trainingId}")
    public ResponseEntity<Void> deleteTraining(@PathVariable int resumeId, @PathVariable int trainingId) {
        try {
            trainingService.deleteTraining(resumeId, trainingId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
