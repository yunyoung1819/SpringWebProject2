package com.ese.study.controller;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ese.study.domain.MessageVO;
import com.ese.study.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageController {

	@Inject
	private MessageService service;
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<String> addMessage(@RequestBody MessageVO vo){
		
		ResponseEntity<String> entity = null;
		
		try{
			service.addMessage(vo);
			entity = new ResponseEntity<>("SUCCES", HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
}
