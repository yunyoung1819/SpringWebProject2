package com.ese.study.persistence;

import java.util.List;
import java.util.Map;

import com.ese.study.domain.BoardVO;
import com.ese.study.domain.Criteria;
import com.ese.study.domain.SearchCriteria;

/**
 * 6. BoardDAO �������̽� ����
 */
public interface BoardDAO {

	public void create(BoardVO vo) throws Exception;
	
	public BoardVO read(Integer bno) throws Exception;
	
	public void update(BoardVO vo) throws Exception;
	
	public void delete(Integer bno) throws Exception;
	
	public List<BoardVO> listAll() throws Exception;
	
	public List<BoardVO> listPage(int page) throws Exception; //BoardDAO �������̽��� ����¡ ó���� ���õ� ����� �߰�
	
	public List<BoardVO> listCriteria(Criteria cri) throws Exception; 
	
	public int countPaging(Criteria cri) throws Exception; // ȭ�� �ϴ��� ������ ��ȣ ó��(tocalCount)�b ��ȯ 
	
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception; // �˻��� ����¡ ó��
	
	public int listSearchCount(SearchCriteria cri) throws Exception; // �˻��� ����¡ ó��
	
	public List<BoardVO> excelDown(Map<String, Object> paramMap) throws Exception;
	
	public void updateReplyCnt(Integer bno, int amount) throws Exception; // 댓글의 숫자를 변경
	
	public void updateViewCnt(Integer bno) throws Exception; // 특정 게시물의 조회 수를 1씩 증가
	
	public void addAttach(String fullName) throws Exception; // 첨부파일 정보를 저장하는 기능
	
	public List<String> getAttach(Integer bno) throws Exception; // 게시물 첨부 파일 목록 조회
	
	// 게시물 첨부파일 수정 (기존 첨부파일 삭제 후 새롭게 추가)
	public void deleteAttach(Integer bno) throws Exception;
	
	public void replaceAttach(String fullName, Integer bno) throws Exception;
}
