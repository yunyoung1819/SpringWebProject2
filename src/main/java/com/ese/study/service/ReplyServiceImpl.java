package com.ese.study.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ese.study.domain.Criteria;
import com.ese.study.domain.ReplyVO;
import com.ese.study.persistence.BoardDAO;
import com.ese.study.persistence.ReplyDAO;

/**
 * 댓글 작성 Service 구현 Class
 * @author Administrator
 *
 */
@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Inject
	private ReplyDAO replyDAO;
	
	@Inject
	private BoardDAO boardDAO;

	@Transactional // 트랜잭션 처리 : 댓글이 등록되어야 댓글의 수를 업데이트한다
	@Override
	public void addReply(ReplyVO vo) throws Exception {
		
		replyDAO.create(vo); //댓글이 등록되면
		boardDAO.updateReplyCnt(vo.getBno(), 1); //댓글의 수를 업데이트한다
	}

	@Override
	public List<ReplyVO> listReply(Integer bno) throws Exception {
		
		return replyDAO.list(bno);
	}

	@Override
	public void modifyReply(ReplyVO vo) throws Exception {
		
		replyDAO.update(vo);
	}

	@Transactional // 트랜잭션 처리
	@Override
	public void removeReply(Integer rno) throws Exception {
		System.out.println("트랜잭션 처리");
		int bno = replyDAO.getBno(rno); //해당 게시물의 번호를 가져온다
		replyDAO.delete(rno); //댓글을 삭제하고
		boardDAO.updateReplyCnt(bno, -1);  //댓글의 수를 -1 감소시킨다
	}

	@Override
	public List<ReplyVO> listReplyPage(Integer bno, Criteria cri) throws Exception {

		return replyDAO.listPage(bno, cri);
	}

	@Override
	public int count(Integer bno) throws Exception {

		return replyDAO.count(bno);
	}
}
