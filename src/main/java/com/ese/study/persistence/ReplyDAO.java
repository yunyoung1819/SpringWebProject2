package com.ese.study.persistence;

import java.util.List;

import com.ese.study.domain.Criteria;
import com.ese.study.domain.ReplyVO;

public interface ReplyDAO {
	
	public List<ReplyVO> list(Integer bno) throws Exception;
	
	public void create(ReplyVO vo) throws Exception;
	
	public void update(ReplyVO vo) throws Exception;
	
	public void delete(Integer rno) throws Exception;
	
	// 댓글 페이징 처리
	public List<ReplyVO> listPage(Integer bno, Criteria cri) throws Exception;
	
	public int count(Integer bno) throws Exception;
	
	public int getBno(Integer rno) throws Exception; // 댓글이 삭제될 때 해당 게시물의 번호를 알아낸다
}
