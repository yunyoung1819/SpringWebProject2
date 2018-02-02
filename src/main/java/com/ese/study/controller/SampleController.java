package com.ese.study.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ese.study.domain.SampleVO;

/**
 * @RestController를 위한 Sample Controller
 * @since 2018.01.24
 * @author Yun Young
 *
 */
/* RestController는 JSP와 같은 뷰를 만들어 내지 않는 대신에 데이터 자체를 반환
이때 주로 사용되는 것은 단순 문자열과 JSON, XML 등으로 나누어 볼 수 있다 */ 
@RestController
@RequestMapping("/sample")
public class SampleController{
	
	// 문자열 데이터
	@RequestMapping("/hello")
	public String sayHello(){
		return "Hello World";
	}
	
	// 객체를 JSON 으로 반환하는 경우
	@RequestMapping("/sendVO")
	public SampleVO sendVO(){
		
		SampleVO vo = new SampleVO();
		vo.setFirstName("효린");
		vo.setLastName("이");
		vo.setMno(123);
		
		return vo;
	}
	
	// 컬렉션 타입의 객체를 반환하는 경우 (List)
	@RequestMapping("/sendList")
	public List<SampleVO> sendList(){
		
		List<SampleVO> list = new ArrayList<SampleVO>();
		for(int i = 0; i < 10; i++){
			SampleVO vo = new SampleVO();
			vo.setFirstName("주미");
			vo.setLastName("이");
			vo.setMno(i);
			list.add(vo);
		}
		
		return list;
	}
}
