package com.ese.study.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ese.study.domain.MessageVO;
import com.ese.study.persistence.MessageDAO;
import com.ese.study.persistence.PointDAO;

@Service
public class MessageServiceImpl implements MessageService {

	@Inject
	private MessageDAO messageDAO;
	
	@Inject
	private PointDAO pointDAO;
	
	@Override
	public void addMessage(MessageVO vo) throws Exception {

		messageDAO.create(vo);
		pointDAO.updatePoint(vo.getSender(), 10);
		
	}

	@Override
	public MessageVO readMessage(String uid, Integer mid) throws Exception {

		messageDAO.updateState(mid);
		
		pointDAO.updatePoint(uid, 5);
		
		return messageDAO.readMessage(mid);
	}

}