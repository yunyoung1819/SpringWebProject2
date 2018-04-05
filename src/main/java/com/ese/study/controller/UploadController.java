package com.ese.study.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	// 파일 경로
	@Resource(name = "uploadPath")
	private String uploadPath;

	/* Ajax 방식의 파일 업로드 */
	@RequestMapping(value="/uploadAjax", method = RequestMethod.GET)
	public void uploadAjax(){
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/uploadAjax",
					method = RequestMethod.POST,
					produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception{
		
		logger.info("originalName : " + file.getOriginalFilename());
		logger.info("size :" + file.getSize());
	    logger.info("contentType : " + file.getContentType());
	    
	    return new ResponseEntity<>(UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()), HttpStatus.CREATED);
	}
	
}
