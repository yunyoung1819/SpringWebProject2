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
	
	// 컬렉션 타입의 객체를 반환하는 경우 (Map)
	@RequestMapping("/sendMap")
	public Map<Integer, SampleVO> sendMap(){
		
		Map<Integer, SampleVO> map =  new HashMap();
		
		for(int i=0; i<10; i++){
			SampleVO vo = new SampleVO();
			vo.setFirstName("수빈");
			vo.setLastName("장");
			vo.setMno(i);
			map.put(i, vo);
		}
		
		return map;
	}
	
	// ResponseEntity 타입 (결과 데이터 + HTTP 상태 코드)
	@RequestMapping("/sendErrorAuth")
	public ResponseEntity<Void> sendListAuth(){
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	// ResponseEntity 타입 (결과 데이터 + HTTP 상태 코드)
	@RequestMapping("/sendErrorNot")
	public ResponseEntity<List<SampleVO>> sendListNot(){
		
		List<SampleVO> list = new ArrayList<SampleVO>();
		for(int i=0; i<10; i++){
			SampleVO vo = new SampleVO();
			vo.setFirstName("길동");
			vo.setLastName("홍");
			vo.setMno(i);
			list.add(vo);
		}
		
		return new ResponseEntity(list, HttpStatus.NOT_FOUND);
	}
}                                                  
