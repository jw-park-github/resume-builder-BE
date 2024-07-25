package com.devdoc.backend.controller;

import com.devdoc.backend.dto.ResponseDTO;
import com.devdoc.backend.dto.UserDTO;
import com.devdoc.backend.model.UserEntity;
import com.devdoc.backend.security.TokenProvider;
import com.devdoc.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@Tag(name = "User Authentication", description = "회원 가입 및 로그인 API")
public class UserController {

	private final UserService userService;

	private final TokenProvider tokenProvider;

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public UserController(UserService userService, TokenProvider tokenProvider) {
		this.userService = userService;
		this.tokenProvider = tokenProvider;
	}

	@Operation(summary = "회원 가입 처리")
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
		try {
			UserEntity user = UserEntity.builder()
					.email(userDTO.getEmail())
					.password(passwordEncoder.encode(userDTO.getPassword()))
					.build();

			UserEntity registeredUser = userService.create(user);

			UserDTO responseUserDTO = UserDTO.builder()
					.email(registeredUser.getEmail())
					.id(registeredUser.getId())
					.createdAt(registeredUser.getCreatedAt())
					.build();

			return ResponseEntity.ok(responseUserDTO);
		} catch (Exception e) {
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

	@Operation(summary = "로그인 처리")
	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
		// 사용자 인증
		UserEntity user = userService.getByCredentials(
				userDTO.getEmail(), // 이메일로 사용자 조회
				userDTO.getPassword(), // 비밀번호 확인
				passwordEncoder); // 비밀번호 인코더 사용

		if (user != null) {
			// JWT 토큰 생성
			final String token = tokenProvider.create(user);

			final UserDTO responseUserDTO = UserDTO.builder()
					.email(user.getEmail())
					.id(user.getId())
					.token(token)
					.createdAt(user.getCreatedAt())
					.build();

			return ResponseEntity.ok().body(responseUserDTO);
		} else {
			ResponseDTO responseDTO = ResponseDTO.builder()
					.error("로그인에 실패했습니다. 다시 시도해주세요.")
					.build();

			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

	@Operation(summary = "로그아웃 처리")
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
		String actualToken = token.replace("Bearer ", "");
		tokenProvider.invalidateToken(actualToken);
		return ResponseEntity.ok().build();
	}
}
