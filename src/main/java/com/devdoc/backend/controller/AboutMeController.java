package com.devdoc.backend.controller;

import com.devdoc.backend.dto.AboutMeDTO;
import com.devdoc.backend.service.AboutMeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumes")
@Tag(name = "AboutMe Management", description = "개인 이력(AboutMe) 항목 관리 API")
public class AboutMeController {

    private final AboutMeService aboutMeService;

    public AboutMeController(AboutMeService aboutMeService) {
        this.aboutMeService = aboutMeService;
    }

    @Operation(summary = "개인 이력 업데이트/저장")
    @PostMapping("/{resumeId}/aboutMes")
    public ResponseEntity<AboutMeDTO> saveOrUpdateAboutMe(@PathVariable int resumeId, @RequestBody AboutMeDTO aboutMeDTO) {
        try {
            AboutMeDTO updatedAboutMe = aboutMeService.saveOrUpdateAboutMe(resumeId, aboutMeDTO);
            return ResponseEntity.ok(updatedAboutMe); // 업데이트된 AboutMe 데이터 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 서버 에러 발생 시 500 반환
        }
    }

    @Operation(summary = "개인 이력 삭제")
    @DeleteMapping("/{resumeId}/aboutMes/{aboutMeId}")
    public ResponseEntity<Void> deleteAboutMe(@PathVariable int resumeId, @PathVariable int aboutMeId) {
        try {
            aboutMeService.deleteAboutMe(resumeId, aboutMeId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
