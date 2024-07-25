package com.devdoc.backend.controller;

import com.devdoc.backend.dto.CareerDTO;
import com.devdoc.backend.service.CareerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumes")
@Tag(name = "Career Management", description = "경력(Career) 항목 관리 API")
public class CareerController {

    private final CareerService careerService;

    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @Operation(summary = "경력 업데이트/저장")
    @PostMapping("/{resumeId}/careers")
    public ResponseEntity<CareerDTO> saveOrUpdateCareer(@PathVariable int resumeId, @RequestBody CareerDTO careerDTO) {
        try {
            CareerDTO updatedCareer = careerService.saveOrUpdateCareer(resumeId, careerDTO);
            return ResponseEntity.ok(updatedCareer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "경력 삭제")
    @DeleteMapping("/{resumeId}/careers/{careerId}")
    public ResponseEntity<Void> deleteCareer(@PathVariable int resumeId, @PathVariable int careerId) {
        try {
            careerService.deleteCareer(resumeId, careerId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
