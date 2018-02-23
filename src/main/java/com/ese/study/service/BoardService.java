package com.ese.study.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ese.study.domain.BoardVO;
import com.ese.study.domain.Criteria;
import com.ese.study.domain.SearchCriteria;

/**
 * BoardService �������̽�
 * 
 * @since : 2017.10.29
 * @author Yoon Young
 *
 */
public interface BoardService {
	
	public void regist(BoardVO board) throws Exception; // ���
	
	public BoardVO read(Integer bno) throws Exception;  // ��ȸ
	
	public void modify(BoardVO board) throws Exception; // ����
	
	public void remove(Integer bno) throws Exception;   // ����
	
	public List<BoardVO> listAll() throws Exception;    // ��ü��ȸ
	
	public List<BoardVO> listCriteria(Criteria cri) throws Exception; // ����¡ ó���� �޼ҵ� �߰�
	
	public int listCountCriteria(Criteria cri) throws Exception; // ȭ�� �ϴ��� ������ ��ȣ ó��
	
	public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception; // �˻�
	
	public int listSearchCount(SearchCriteria cri) throws Exception; // �˻�
	
	public void excelDown(HttpServletRequest request, HttpServletResponse response, Map<String, Object> paramMap) throws Exception; 
}
