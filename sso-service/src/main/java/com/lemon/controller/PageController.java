package com.lemon.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	/**
	 * 展示登录页面
	 */
	@RequestMapping("/page/login")
	public String showLogin(String redirectURL,HttpServletRequest request) {
		request.setAttribute("redirect", redirectURL);
		return "login";
	}
}
