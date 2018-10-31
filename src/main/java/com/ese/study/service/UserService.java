package com.ese.study.service;

import java.util.Date;

import com.ese.dto.LoginDTO;
import com.ese.study.domain.UserVO;

public interface UserService {
	
	// 로그인한 사용자의 아이디와 패스워드로 사용자 정보를 조회
	public UserVO login(LoginDTO dto) throws Exception;
	
	// 로그인 정보를 유지하는 keepLogin
	public void keepLogin(String uid, String sessionId, Date next) throws Exception;
	
	// 과거에 접속한 사용자인지를 확인하는 기능 
	public UserVO checkLoginBefore(String value);
	
}
