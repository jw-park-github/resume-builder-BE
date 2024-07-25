package com.devdoc.backend.controller;

import com.devdoc.backend.dto.ResumeDTO;
import com.devdoc.backend.model.Resume;
import com.devdoc.backend.service.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
@Tag(name = "Resume Management", description = "이력서 항목 관리 API")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @Operation(summary = "이력서 저장")
    @PostMapping("/{resumeId}/save")
    public ResponseEntity<?> saveResume(@PathVariable int resumeId, @RequestBody ResumeDTO resumeDTO, @AuthenticationPrincipal String userId) {
        if (!resumeService.isOwner(userId, resumeId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("잘못된 접근입니다.");
        }
        try {
            resumeService.saveResume(resumeId, resumeDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "이력서 불러오기")
    @GetMapping("/{resumeId}")
    public ResponseEntity<?> getResumeByResumeId(@PathVariable int resumeId, @AuthenticationPrincipal String userId) {
        if (!resumeService.isOwner(userId, resumeId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("잘못된 접근입니다.");
        }
        try {
            ResumeDTO resumeDTO = resumeService.getResumeByResumeId(resumeId);
            if (resumeDTO != null) {
                return new ResponseEntity<>(resumeDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "이력서 목록 조회")
    @GetMapping
    public ResponseEntity<List<ResumeDTO>> getAllResumesByUser(@AuthenticationPrincipal String userId) {
        try {
            List<ResumeDTO> resumes = resumeService.getAllResumesByUser(userId);
            return new ResponseEntity<>(resumes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "이력서 생성")
    @PostMapping
    public ResponseEntity<Resume> createResume(@RequestBody String title, @AuthenticationPrincipal String userId) {
        try {
            Resume createdResume = resumeService.createResume(title, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdResume);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "이력서 삭제")
    @DeleteMapping("/{resumeId}")
    public ResponseEntity<?> deleteResumeByResumeId(@PathVariable int resumeId, @AuthenticationPrincipal String userId) {
        if (!resumeService.isOwner(userId, resumeId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("잘못된 접근입니다.");
        }
        try {
            resumeService.deleteResumeByResumeId(resumeId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "이력서 제목 업데이트")
    @PutMapping("/{resumeId}/title")
    public ResponseEntity<?> saveResumeTitleByResumeId(@PathVariable int resumeId, @RequestBody String newTitle, @AuthenticationPrincipal String userId) {
        if (!resumeService.isOwner(userId, resumeId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("잘못된 접근입니다.");
        }
        try {
            ResumeDTO updatedResume = resumeService.saveResumeTitleByResumeId(resumeId, newTitle);
            if (updatedResume != null) {
                return ResponseEntity.ok(updatedResume);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
