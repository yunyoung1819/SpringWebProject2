package com.ese.study.persistence;

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
}
