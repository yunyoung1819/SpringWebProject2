package com.ese.study.service;

import com.ese.dto.LoginDTO;
import com.ese.study.domain.UserVO;

public interface UserService {
	
	// 로그인한 사용자의 아이디와 패스워드로 사용자 정보를 조회
	public UserVO login(LoginDTO dto) throws Exception;
}
