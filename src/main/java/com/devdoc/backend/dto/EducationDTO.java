package com.devdoc.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "학력 항목을 담는 DTO")
public class EducationDTO {

    @Schema(description = "Education ID", example = "1")
    private Integer id;

    @Schema(description = "학교명", example = "카카오대학교")
    private String schoolName;

    @Schema(description = "전공", example = "컴퓨터 공학")
    private String major;

    @Schema(description = "입학 날짜", example = "2018-03-01")
    private String startDate;

    @Schema(description = "졸업 날짜", example = "2022-02-28")
    private String endDate;

    @Schema(description = "현재 상태", example = "졸업")
    private String status;

    @Schema(description = "교육 유형", example = "학사")
    private String educationType;
}
