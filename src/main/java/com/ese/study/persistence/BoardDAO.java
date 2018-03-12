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
	
}
