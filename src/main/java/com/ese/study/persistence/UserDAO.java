package com.ese.study.persistence;

import java.util.Date;
import com.ese.dto.LoginDTO;
import com.ese.study.domain.UserVO;

public interface UserDAO {

	// 로그인할 때 사용자의 아이디와 패스워드를 이용해서 사용자의 정보를 조회하는 SQL문을 처리
	public UserVO login(LoginDTO dto) throws Exception;
	
	// 로그인한 사용자의 sessionKey 와 sessionLimit를 업데이트 하는 기
	public void keepLogin(String uid, String sessionId, Date next);
	
	// loginCookie 에 기록된 값으로 사용자의 정보를 조회하는 기능
	public UserVO checkUserWithSessionKey(String value);
}
