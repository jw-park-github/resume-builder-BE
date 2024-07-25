package com.devdoc.backend.controller;

import com.devdoc.backend.dto.AwardDTO;
import com.devdoc.backend.service.AwardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumes")
@Tag(name = "Award Management", description = "수상 이력(Award) 항목 관리 API")
public class AwardController {

    private final AwardService awardService;

    public AwardController(AwardService awardService) {
        this.awardService = awardService;
    }

    @Operation(summary = "수상 이력 업데이트/저장")
    @PostMapping("/{resumeId}/awards")
    public ResponseEntity<AwardDTO> saveOrUpdateAward(@PathVariable int resumeId, @RequestBody AwardDTO awardDTO) {
        try {
            AwardDTO updatedAward = awardService.saveOrUpdateAward(resumeId, awardDTO);
            return ResponseEntity.ok(updatedAward);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "수상 이력 삭제")
    @DeleteMapping("/{resumeId}/awards/{awardId}")
    public ResponseEntity<Void> deleteAward(@PathVariable int resumeId, @PathVariable int awardId) {
        try {
            awardService.deleteAward(resumeId, awardId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
