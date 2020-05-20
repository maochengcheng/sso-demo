package com.lemon.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lemon.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lemon.entity.SsoUser;
import com.lemon.service.Loginservice;
import com.lemon.utils.CommonResult;

@RestController
public class LoginController {

	@Autowired
	private Loginservice loginservice;
	
	@PostMapping("/login")
	public CommonResult login(@RequestBody JSONObject jsonObject,HttpServletRequest request,HttpServletResponse response){
		SsoUser user = jsonObject.toJavaObject(SsoUser.class);
		CommonResult result = loginservice.login(user.getAppkey(), user.getPassword(), request, response);
		return result;
	}
	
	@GetMapping("/token/{token}")
	public CommonResult getUserByToken(@PathVariable String token){
		CommonResult result = loginservice.getUserByToken(token);
		return result;
	}

}
