package com.devdoc.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "개인 이력 정보를 담는 모델")
public class AboutMe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "AboutMe ID", example = "1")
    private Integer id;

    @Schema(hidden = true)
    private String photo;

    @Schema(description = "이름", example = "김구름")
    private String name;

    @Schema(hidden = true)
    private String birthday;

    @Schema(description = "이메일", example = "example@gmail.com")
    private String email;

    @Schema(description = "깃허브 주소", example = "github.com/example")
    private String github;

    @Schema(description = "전화 번호", example = "1")
    private String phoneNumber;

    @Schema(hidden = true)
    private String blog;

    @Schema(description = "자기 소개", example = "개발자 김구름입니다.")
    private String introduction;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
