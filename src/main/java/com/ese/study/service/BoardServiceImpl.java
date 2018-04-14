package com.ese.study.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriUtils;

import com.ese.study.domain.BoardVO;
import com.ese.study.domain.Criteria;
import com.ese.study.domain.SearchCriteria;
import com.ese.study.persistence.BoardDAO;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.ScriptStyle;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * BoardServiceImpl 
 * BoardService�� ������ Ŭ����
 * 
 * @author Administrator
 * 
 */
//
@Service
public class BoardServiceImpl implements BoardService {
	
	@Inject
	private BoardDAO dao;
	
	@Transactional
	@Override
	public void regist(BoardVO board) throws Exception {
		dao.create(board);
		
		String[] files = board.getFiles();
		
		if(files == null) { return; }
		
		for(String fileName : files){
			dao.addAttach(fileName);
		}
	}

	@Transactional(isolation=Isolation.READ_COMMITTED)  // 격리 수준은 대부분의 데이터베이스가 기존 사용 수준. 다른 연결이 커밋하지 않은 데이터는 볼 수 없다
	@Override
	public BoardVO read(Integer bno) throws Exception {
		dao.updateViewCnt(bno);;
		return dao.read(bno);
	}

	// 게시물 수정 
	@Transactional
	@Override
	public void modify(BoardVO board) throws Exception {
		// 원래의 게시물 수정
		dao.update(board);
		
		//기존 첨부파일 목록 삭제
		Integer bno = board.getBno();
		dao.deleteAttach(bno);
		
		// 새로운 첨부파일 정보 입력
		String[] files = board.getFiles();
		
		if(files == null) { return; }
		
		for(String fileName : files){
			dao.replaceAttach(fileName, bno);
		}
	}

	// 게시물 삭제
	// 데이터베이스에 저장된 첨부파일의 정보와 게시물 삭제 
	@Transactional
	@Override
	public void remove(Integer bno) throws Exception {
		dao.deleteAttach(bno);  // 첨부파일 정보 삭제
		dao.delete(bno);  		// 게시물 삭제
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return dao.listAll();
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		
		return dao.listCriteria(cri);
	}

	// ȭ�� �ϴ��� ������ ��ȣ ó��
	@Override
	public int listCountCriteria(Criteria cri) throws Exception {
		
		return dao.countPaging(cri);
	}

	@Override
	public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception {
		
		return  dao.listSearch(cri);
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		
		return dao.listSearchCount(cri);
	}
	
	/**
	 * Download Excel File
	 * @Method Name : excelDown
	 * @create Date : 2018. 02. 08.
	 * @made by : Yun Young
	 */
	@Override
	public void excelDown(HttpServletRequest request, HttpServletResponse response, Map<String, Object> paramMap) throws Exception {
		
		System.out.println("excel Service");
		
		FileInputStream fin = null;
		
		try{
			
			String filePath = request.getSession().getServletContext().getRealPath("/") + "file_uplad_excel"; //파일 경로 지정(없을 경우 자동 생성 있어야함)
			File f = new File(filePath);
			
			boolean result = true;
			if(!f.exists()){
				result = f.mkdir();
			}
			
			if(result){
				System.out.println("excelDown, Folder has been created.");
			}
			
			// Create WorkBook
			String excelTitle = "국내도서 베스트셀러"; //시트 제목, 문서제목
			
			String fileName = UriUtils.encodeFragment(excelTitle+"_"+System.currentTimeMillis(), "UTF-8");
			
			// 엑셀 파일 하나 생성 (WritableWorkbook)
			WritableWorkbook myWorkbook = Workbook.createWorkbook(new File(filePath + File.separator + fileName + ".xls"));
			myWorkbook.setColourRGB(Colour.getInternalColour(61), 218, 238, 243); // 컬러 지정

			WritableSheet mySheet = myWorkbook.createSheet(excelTitle, 0);
			WritableFont.FontName fontNm = WritableFont.createFont("맑은 고딕"); //맑은 고딕
			// 타이틀! 시트 내용 제일 위에 들어가는 제목
			WritableCellFormat mTitleFormat = new WritableCellFormat(new WritableFont (fontNm, 20, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT));
			// 테이블 헤더
			WritableCellFormat titleFormat = new WritableCellFormat(new WritableFont (fontNm, 9,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT));  
			// 테이블 바디
			WritableCellFormat dataFormat = new WritableCellFormat(new WritableFont (fontNm, 9,WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT)); // 데이터 셀 포멧 생성
			
			// 타이틀 모양 지정(Alignment)
			mTitleFormat.setAlignment(Alignment.CENTRE); // 셀 가운데 정렬
			mTitleFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 셀 수직 가운데 정렬
			
			// 테이블 헤더 모양
			titleFormat.setAlignment(Alignment.CENTRE);
			titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
			titleFormat.setBackground(Colour.getInternalColour(61));
			titleFormat.setWrap(true); //자동 줄바꿈 지원
			
			// 테이블 바디 모양
			dataFormat.setAlignment(Alignment.CENTRE);
			dataFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			dataFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
			
			//세로줄(열) 0번부터 시작이고, 넓이 지정
			mySheet.setColumnView(0, 15); 
			mySheet.setColumnView(1, 15);
			mySheet.setColumnView(2, 20);
			mySheet.setColumnView(3, 20);
			mySheet.setColumnView(4, 20);
			mySheet.setColumnView(5, 20);
			mySheet.setColumnView(6, 20);
			mySheet.setColumnView(7, 20);
			mySheet.setColumnView(8, 20);
			mySheet.setColumnView(9, 20);
			mySheet.setColumnView(10, 20);
			mySheet.setColumnView(11, 20);
			mySheet.setColumnView(12, 20);
			mySheet.mergeCells(0, 0, 4, 0); //mergeCells(int col1, int row1, int col2, int row2)
			
			// 가로줄(행) 0번부터 시작이고, 넓이 지정
			mySheet.setRowView(0, 1000);
			mySheet.setRowView(1, 600);
			
			//테이블 헤더 부분에 들어갈 내용, 형식(열,행)
			mySheet.addCell(new Label(0, 0, "국내도서 베스트셀러 목록", mTitleFormat));
			mySheet.addCell(new Label(0, 1, "글번호", titleFormat));
			mySheet.addCell(new Label(1, 1, "제목", titleFormat));
			mySheet.addCell(new Label(2, 1, "글쓴이", titleFormat));
			mySheet.addCell(new Label(3, 1, "등록일자", titleFormat));
			mySheet.addCell(new Label(4, 1, "조회수", titleFormat));
			
			BoardVO boardVO;
			
			List<BoardVO> list = dao.excelDown(paramMap);
			
			System.out.println("excel Service list : " + list);
			
			int listSize = list.size();
			
			if(listSize > 0){
				for(int i = 0; i < listSize; i++){
					int j = i + 2;
					
					mySheet.setRowView(j, 400);
					
					mySheet.addCell(new Label(0, j, String.valueOf(list.get(i).getBno()), dataFormat));
					mySheet.addCell(new Label(1, j, list.get(i).getTitle(), dataFormat));
					mySheet.addCell(new Label(2, j, list.get(i).getWriter(), dataFormat));
					mySheet.addCell(new Label(3, j, String.valueOf(list.get(i).getRegdate()), dataFormat));
					mySheet.addCell(new Label(4, j, String.valueOf(list.get(i).getViewcnt()), dataFormat));
				}
			}else{
				mySheet.addCell(new Label(0, 3, "", dataFormat));
				mySheet.addCell(new Label(1, 3, "", dataFormat));
				mySheet.addCell(new Label(2, 3, "", dataFormat));
				mySheet.addCell(new Label(3, 3, "", dataFormat));
				mySheet.addCell(new Label(4, 3, "", dataFormat));
			}
			
			// WorkSheet 쓰기
			myWorkbook.write();
			// WorkSheet 닫기
			myWorkbook.close();
			
			File file = new File(filePath + File.separator + fileName + ".xls");
			fin = new FileInputStream(file);
			
			int ifilesize = (int) file.length();
			byte b[] = new byte[ifilesize];
			response.setContentLength(ifilesize);  		  // 컨텐츠 길이는 file size 만큼
			response.setContentType("application/smnet"); // MIME Type
			response.setHeader(
					"Content-Disposition", 
					"attachment; filename=" + fileName + ".xls" + ";");
			
			ServletOutputStream out = response.getOutputStream();
			fin.read(b);
			out.write(b, 0, ifilesize);
			out.close();
			fin.close();
			file.delete();
		}catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public List<String> getAttach(Integer bno) throws Exception {
		
		return dao.getAttach(bno);
	}
}