package com.ese.study.service;

import java.util.List;

import com.ese.study.domain.Criteria;
import com.ese.study.domain.ReplyVO;

/**
 * 댓글 작성 Service Interface
 * 
 * @since 2018.02.04
 * @author Yun Young
 *
 */
public interface ReplyService {

	public void addReply(ReplyVO vo) throws Exception; 			  //댓글 추가
	
	public List<ReplyVO> listReply(Integer bno) throws Exception; // 댓글 조회
	
	public void modifyReply(ReplyVO vo) throws Exception;  		  // 댓글 수정
	
	public void removeReply(Integer rno) throws Exception; 		  // 댓글 삭제
	
	public List<ReplyVO> listReplyPage(Integer bno, Criteria cri) throws Exception;
	
	public int count(Integer bno) throws Exception;
}
