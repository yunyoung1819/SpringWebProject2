package com.ese.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * LoginInterceptor는 로그인한 사용자에 대해서 postHandle()을 이용해서 HttpSession에 보관하는 처리를 담당
 * AuthInterceptor는 특정 경로에 접근하는 경우 현재 사용자가 로그인한 상태의 사용자인지를 체크하는 역할
 * @author Administrator
 *
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("login") == null) {
			
			logger.info("current user is not logined");
			
			response.sendRedirect("/user/login");
			
			return false;
		}
		
		return true;
	}
	
}
