package com.ese.study.controller;

import java.util.ArrayList;
import java.util.List;

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
}
