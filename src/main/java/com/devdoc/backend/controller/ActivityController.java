package com.devdoc.backend.controller;

import com.devdoc.backend.dto.ActivityDTO;
import com.devdoc.backend.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumes")
@Tag(name = "Activity Management", description = "대외 활동(Activity) 항목 관리 API")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Operation(summary = "대외 활동 업데이트/저장")
    @PostMapping("/{resumeId}/activities")
    public ResponseEntity<ActivityDTO> saveOrUpdateActivity(@PathVariable int resumeId, @RequestBody ActivityDTO activityDTO) {
        try {
            ActivityDTO updatedActivity = activityService.saveOrUpdateActivity(resumeId, activityDTO);
            return ResponseEntity.ok(updatedActivity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "대외 활동 삭제")
    @DeleteMapping("/{resumeId}/activities/{activityId}")
    public ResponseEntity<Void> deleteActivity(@PathVariable int resumeId, @PathVariable int activityId) {
        try {
            activityService.deleteActivity(resumeId, activityId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
