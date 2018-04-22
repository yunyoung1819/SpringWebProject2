package com.ese.study.persistence;

import com.ese.dto.LoginDTO;
import com.ese.study.domain.UserVO;

public interface UserDAO {

	// 로그인할 때 사용자의 아이디와 패스워드를 이용해서 사용자의 정보를 조회하는 SQL문을 처리
	public UserVO login(LoginDTO dto) throws Exception;
}
