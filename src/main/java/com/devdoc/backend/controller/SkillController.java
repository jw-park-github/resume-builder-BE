package com.devdoc.backend.controller;

import com.devdoc.backend.dto.SkillDTO;
import com.devdoc.backend.service.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumes")
@Tag(name = "Skill Management", description = "기술 스택(Skill) 항목 관리 API")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @Operation(summary = "기술 스택 업데이트/저장")
    @PostMapping("/{resumeId}/skills")
    public ResponseEntity<SkillDTO> saveOrUpdateSkill(@PathVariable int resumeId, @RequestBody SkillDTO skillDTO) {
        try {
            SkillDTO updatedSkill = skillService.saveOrUpdateSkill(resumeId, skillDTO);
            return ResponseEntity.ok(updatedSkill);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "기술 스택 삭제")
    @DeleteMapping("/{resumeId}/skills/{skillId}")
    public ResponseEntity<Void> deleteSkill(@PathVariable int resumeId, @PathVariable int skillId) {
        try {
            skillService.deleteSkill(resumeId, skillId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
