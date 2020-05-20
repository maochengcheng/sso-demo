package com.lemon.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lemon.entity.SsoUser;
import com.lemon.service.UserService;

public class LoginInterceptor implements HandlerInterceptor{

	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// 1、拦截请求url
		// 2、从cookie中取token
		// 3、如果没有toke跳转到登录页面。
		// 4、取到token，需要调用sso系统的服务查询用户信息。
		System.out.println("这里是拦截器1111:"+request.getRequestURL());
		SsoUser user = userService.getUserByToken(request, response);
		// 5、如果用户session已经过期，跳转到登录页面
		System.out.println("这里是拦截器22222:"+request.getRequestURL());
		if (user == null) {
			response.sendRedirect("http://192.168.1.144:1527/sso/page/login?redirectURL="+request.getRequestURL());
			return false;
		}
		// 6、如果没有过期，放行。
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}



}
