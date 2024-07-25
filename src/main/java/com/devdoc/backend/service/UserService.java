package com.devdoc.backend.service;


import com.devdoc.backend.model.UserEntity;
import com.devdoc.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// 사용자 생성 처리
	public UserEntity create(final UserEntity userEntity) {
		if(userEntity == null || userEntity.getEmail() == null ) {
			throw new RuntimeException("이메일 주소를 입력하세요.");
		}
		final String email = userEntity.getEmail();
		if(userRepository.existsByEmail(email)) {
			log.warn("Email already exists {}", email);
			throw new RuntimeException("이미 가입된 이메일 주소입니다."); // 이메일 중복 예외 발생
		}

		return userRepository.save(userEntity); // 사용자 정보를 데이터베이스에 저장
	}

	// 사용자 인증 처리
	public UserEntity getByCredentials(final String email, final String password, final PasswordEncoder encoder) {
		final UserEntity originalUser = userRepository.findByEmail(email); // 이메일로 사용자 정보 조회

		if(originalUser != null && encoder.matches(password, originalUser.getPassword())) {
			return originalUser; // 비밀번호가 일치하면 사용자 정보를 반환
		}
		return null; // 비밀번호가 일치하지 않으면 null 반환
	}
}

// UserService: 사용자 생성 처리 및 인증 관련 비즈니스 로직 처리