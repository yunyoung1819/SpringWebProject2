package com.ese.interceptor;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.ese.study.domain.UserVO;
import com.ese.study.service.UserService;

/**
 * LoginInterceptor는 로그인한 사용자에 대해서 postHandle()을 이용해서 HttpSession에 보관하는 처리를 담당
 * AuthInterceptor는 특정 경로에 접근하는 경우 현재 사용자가 로그인한 상태의 사용자인지를 체크하는 역할
 * @author Administrator
 *
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	@Inject
	private UserService service;
	
	// 사용자가 원하는 URI가 무엇인지 보관했다가 로그인 성공 후 해당 경로로 이동
	private void saveDest(HttpServletRequest req) {
		String uri = req.getRequestURI();
		
		String query = req.getQueryString();
		
		if(query == null || query.equals("null")) {
			query = "";
		}else{
			query = "?" + query;
		}
		
		if(req.getMethod().equals("GET")) {
			logger.info("dest : " + (uri + query));
			req.getSession().setAttribute("dest", uri + query);
		}
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("login") == null) {
			
			logger.info("current user is not logined");
			
			saveDest(request);
			
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			
			if(loginCookie != null) {
				
				UserVO userVO = service.checkLoginBefore(loginCookie.getValue());
				
				logger.info("USER VO : " +userVO);
				
				if(userVO != null) {
					session.setAttribute("login", userVO);
					return true;
				}
			}
			
			response.sendRedirect("/user/login");
			
			return false;
		}
		
		return true;
	}
	
}
