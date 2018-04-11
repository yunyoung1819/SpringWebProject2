package com.ese.study.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.ese.study.domain.BoardVO;
import com.ese.study.domain.Criteria;
import com.ese.study.domain.SearchCriteria;

/**
 * BoardDAO �������̽��� ������ BoardDAOImpl
 * 
 * @since : 2017.10.20
 * @author Administrator
 *
 */

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	@Inject
	private SqlSession session;

	private static String namespace="com.ese.mapper.BoardMapper";
	
	@Override
	public void create(BoardVO vo) throws Exception {
		session.insert(namespace + ".create", vo);
	}

	@Override
	public BoardVO read(Integer bno) throws Exception {
		return session.selectOne(namespace + ".read", bno);
	}

	@Override
	public void update(BoardVO vo) throws Exception {
		session.update(namespace + ".update", vo);
	}

	@Override
	public void delete(Integer bno) throws Exception {
		session.delete(namespace + ".delete", bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return session.selectList(namespace + ".listAll");
	}

	@Override
	public List<BoardVO> listPage(int page) throws Exception {
		
		if(page <= 0){  //page�� 0�̰ų� 0���� ���� �� 
			page = 1;
		}
		
		page = (page - 1 ) * 10;
		
		return session.selectList(namespace + ".listPage", page);
	}

	// Criteria
	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		
		return session.selectList(namespace + ".listCriteria", cri); 
	}

	// ȭ�� �ϴ��� ������ ��ȣ ó���� ���� Total Count ��ȯ
	@Override
	public int countPaging(Criteria cri) throws Exception {
		
		return session.selectOne(namespace + ".countPaging", cri);
	}

	// �˻� �� ����¡ ó��
	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {
		return session.selectList(namespace + ".listSearch", cri);
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		return session.selectOne(namespace + ".listSearchCount", cri);
	}

	@Override
	public List<BoardVO> excelDown(Map<String, Object> paramMap) throws Exception {
		System.out.println("excel dao");
		return session.selectList(namespace + ".listExcel");
	}

	// 댓글의 숫자를 업데이트 처리
	@Override
	public void updateReplyCnt(Integer bno, int amount) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("bno", bno);
		paramMap.put("amount", amount);
		
		session.update(namespace + ".updateReplyCnt", paramMap);
	}

	// 특정 게시물의 조회 수를 1씩 증가하도록 처리
	@Override
	public void updateViewCnt(Integer bno) throws Exception {
		
		session.update(namespace + ".updateViewCnt", bno);
	}

	// 게시물 첨부파일 정보 저장
	@Override
	public void addAttach(String fullName) throws Exception {
		
		session.insert(namespace + ".addAttach", fullName);
	}

	@Override
	public List<String> getAttach(Integer bno) throws Exception {

		return session.selectList(namespace + ".getAttach", bno);
	}

	@Override
	public void deleteAttach(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void replaceAttach(String fullName, Integer bno) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
