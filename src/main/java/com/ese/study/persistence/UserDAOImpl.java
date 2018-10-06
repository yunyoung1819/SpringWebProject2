package com.ese.study.persistence;

import java.sql.Date;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserVO checkUserWithSessionKey(String value) {
		// TODO Auto-generated method stub
		return null;
	}

}
