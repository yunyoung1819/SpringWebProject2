package com.ese.study.persistence;

import com.ese.study.domain.MessageVO;

/**
 * 도메인 객체와 SQL을 처리하는 DAO
 * @author Administrator
 *
 */
public interface MessageDAO {

	public void create(MessageVO vo) throws Exception;
	
	public MessageVO readMessage(Integer mid) throws Exception;
	
	public void updateState(Integer mid) throws Exception;
	
}
