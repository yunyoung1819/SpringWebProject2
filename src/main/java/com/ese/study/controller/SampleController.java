package com.ese.study.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ese.study.domain.SampleVO;

/**
 * @RestController�� ���� Sample Controller
 * @since 2018.01.24
 * @author Yun Young
 *
 */
/* RestController�� JSP�� ���� �並 ����� ���� �ʴ� ��ſ� ������ ��ü�� ��ȯ
�̶� �ַ� ���Ǵ� ���� �ܼ� ���ڿ��� JSON, XML ������ ������ �� �� �ִ� */ 
@RestController
@RequestMapping("/sample")
public class SampleController{
	
	// ���ڿ� ������
	@RequestMapping("/hello")
	public String sayHello(){
		return "Hello World";
	}
	
	// ��ü�� JSON ���� ��ȯ�ϴ� ���
	@RequestMapping("/sendVO")
	public SampleVO sendVO(){
		
		SampleVO vo = new SampleVO();
		vo.setFirstName("ȿ��");
		vo.setLastName("��");
		vo.setMno(123);
		
		return vo;
	}
	
	// �÷��� Ÿ���� ��ü�� ��ȯ�ϴ� ��� (List)
	@RequestMapping("/sendList")
	public List<SampleVO> sendList(){
		
		List<SampleVO> list = new ArrayList<SampleVO>();
		for(int i = 0; i < 10; i++){
			SampleVO vo = new SampleVO();
			vo.setFirstName("�ֹ�");
			vo.setLastName("��");
			vo.setMno(i);
			list.add(vo);
		}
		
		return list;
	}
	
	// �÷��� Ÿ���� ��ü�� ��ȯ�ϴ� ��� (Map)
	@RequestMapping("/sendMap")
	public Map<Integer, SampleVO> sendMap(){
		
		Map<Integer, SampleVO> map =  new HashMap();
		
		for(int i=0; i<10; i++){
			SampleVO vo = new SampleVO();
			vo.setFirstName("����");
			vo.setLastName("��");
			vo.setMno(i);
			map.put(i, vo);
		}
		
		return map;
	}
	
	// ResponseEntity Ÿ�� (��� ������ + HTTP ���� �ڵ�)
	@RequestMapping("/sendErrorAuth")
	public ResponseEntity<Void> sendListAuth(){
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	// ResponseEntity Ÿ�� (��� ������ + HTTP ���� �ڵ�)
	@RequestMapping("/sendErrorNot")
	public ResponseEntity<List<SampleVO>> sendListNot(){
		
		List<SampleVO> list = new ArrayList<SampleVO>();
		for(int i=0; i<10; i++){
			SampleVO vo = new SampleVO();
			vo.setFirstName("�浿");
			vo.setLastName("ȫ");
			vo.setMno(i);
			list.add(vo);
		}
		
		return new ResponseEntity(list, HttpStatus.NOT_FOUND);
	}
}                                                  
