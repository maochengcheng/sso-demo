package com.lemon.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lemon.utils.CommonResult;

public interface Loginservice {

	CommonResult login(String appkey,String password,
			HttpServletRequest request,HttpServletResponse response);

	CommonResult getUserByToken(String token);
	
	CommonResult logout(String token, HttpServletRequest request,
			HttpServletResponse response);
}
