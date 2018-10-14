package com.ese.study.persistence;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.ese.dto.LoginDTO;
import com.ese.study.domain.UserVO;

@Repository
public class UserDAOImpl implements UserDAO {

	@Inject
	private SqlSession session;
	
	private static String namespace = "com.ese.mapper.UserMapper";
	
	@Override
	public UserVO login(LoginDTO dto) throws Exception {
		return session.selectOne(namespace + ".login", dto);
	}

	@Override
	public void keepLogin(String uid, String sessionId, Date next) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("uid", uid);
		paramMap.put("sessionId", sessionId);
		paramMap.put("next", next);
		
		session.update(namespace + ".keepLogin", paramMap);
		
	}

	@Override
	public UserVO checkUserWithSessionKey(String value) {
		// TODO Auto-generated method stub
		return null;
	}

}
