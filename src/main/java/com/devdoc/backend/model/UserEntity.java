package com.devdoc.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 정보를 저장하고 관리하는 모델")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class UserEntity {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Schema(description = "사용자 고유 ID", example = "1")
	private String id;

	@Column(nullable = false)
	@Schema(description = "사용자 이메일", example = "example1@example.com")
	private String email;

	@Column(nullable = false)
	@Schema(description = "사용자 비밀번호", example = "mypassword11!")
	private String password;

	@Schema(description = "생성일자", example = "2024-07-05T10:15:30")
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Schema(description = "사용자가 생성한 이력서 목록")
	private List<Resume> resumes;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}



