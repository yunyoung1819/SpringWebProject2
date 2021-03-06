package com.ese.study.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ese.study.domain.BoardVO;
import com.ese.study.domain.PageMaker;
import com.ese.study.domain.SearchCriteria;
import com.ese.study.service.BoardService;

/**
 * �˻��� �ʿ��� Controller
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/sboard/*")
public class SearchBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);
	
	@Inject
	private BoardService service;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception{
		
		logger.info(cri.toString());
		
		model.addAttribute("list", service.listSearchCriteria(cri));
		
		PageMaker pageMaker = new PageMaker();
		
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listSearchCount(cri));
		
		model.addAttribute("pageMaker", pageMaker);
	}
	
	@RequestMapping(value = "/readPage", method = RequestMethod.GET)
	public void read(@RequestParam("bno") int bno, 
			@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception{
		
		model.addAttribute(service.read(bno));
	}
	
	// �Խù� ���� ó��
	@RequestMapping(value = "/removePage", method = RequestMethod.POST)
	public String remove(@RequestParam("bno") int bno,
			SearchCriteria cri,
			RedirectAttributes rttr) throws Exception{
		
		service.remove(bno);
		
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/sboard/list";
	}
	
	// �Խù� ���� ó�� (GET ���)
	@RequestMapping(value = "/modifyPage", method = RequestMethod.GET)
	public void modifyPagingGET(int bno, @ModelAttribute("cri") SearchCriteria cri,
			Model model) throws Exception{
		
		model.addAttribute(service.read(bno));
	}
	
	// �Խù� ���� ó�� (POST ���)
	@RequestMapping(value = "/modifyPage", method = RequestMethod.POST)
	public String modifyPagingPOST(BoardVO board, 
			SearchCriteria cri, 
			RedirectAttributes rttr) throws Exception{
		
		logger.info(cri.toString());
		service.modify(board);

		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		logger.info(rttr.toString());
		
		return "redirect:/sboard/list";
	}
	
	// ��� �κ�
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registGET() throws Exception {
		
		logger.info("regist get...........");
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registPOST(BoardVO board, RedirectAttributes rttr) throws Exception{
		
		logger.info("regist post...........");
		logger.info(board.toString());
		
		service.regist(board);
		
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/sboard/list";
	}
	
	/**
	 * Download Excel File
	 * @Method Name : excelDown
	 * @create Date : 2018. 02. 20.
	 * @made by : Yun Young
	 * @param :
	 * @return : 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/excelDown.do")
	public String excelDown(HttpServletRequest request, HttpServletResponse response, SearchCriteria cri) throws Exception{
		
		System.out.println("excel Controller!");
		Map<String, Object> paramMap = new HashMap<>();
		
		System.out.println("page: " + cri.getPage());
		System.out.println("perPageNum: " + cri.getPerPageNum());
		System.out.println("searchType: " + cri.getSearchType());
		System.out.println("keyword: " + cri.getKeyword());
		
		paramMap.put("page", cri.getPage());
		paramMap.put("perPageNum", cri.getPerPageNum());
		paramMap.put("searchType", cri.getSearchType());
		paramMap.put("keyword", cri.getKeyword());
		
		service.excelDown(request, response, paramMap);
		
		return null;
	}
	
	// Ajax로 호출되는 특정 게시물의 첨부 파일을 처리하는 메소드
	// 호출하는 경로는 '/sboard/getAttach/게시물 번호'의 형태가 되고 리턴 타입은 첨부파일의 문자열 리스트 형태
	@RequestMapping("/getAttach/{bno}")
	@ResponseBody
	public List<String> getAttach(@PathVariable("bno") Integer bno) throws Exception{
		
		return service.getAttach(bno);
	}
}