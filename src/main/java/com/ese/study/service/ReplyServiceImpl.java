package com.ese.study.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ese.study.domain.Criteria;
import com.ese.study.domain.ReplyVO;
import com.ese.study.persistence.ReplyDAO;

/**
 * 댓글 작성 Service 구현 Class
 * @author Administrator
 *
 */
@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Inject
	private ReplyDAO dao;

	@Override
	public void addReply(ReplyVO vo) throws Exception {
		
		dao.create(vo);
	}

	@Override
	public List<ReplyVO> listReply(Integer bno) throws Exception {
		
		return dao.list(bno);
	}

	@Override
	public void modifyReply(ReplyVO vo) throws Exception {
		
		dao.update(vo);
	}

	@Override
	public void removeReply(Integer rno) throws Exception {
		
		dao.delete(rno);
		
	}

	@Override
	public List<ReplyVO> listReplyPage(Integer bno, Criteria cri) throws Exception {

		return dao.listPage(bno, cri);
	}

	@Override
	public int count(Integer bno) throws Exception {

		return dao.count(bno);
	}
}
