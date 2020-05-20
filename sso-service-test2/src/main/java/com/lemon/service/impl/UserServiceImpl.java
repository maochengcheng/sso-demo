package com.lemon.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lemon.entity.SsoUser;
import com.lemon.service.UserService;
import com.lemon.utils.CommonResult;
import com.lemon.utils.CookieUtils;
import com.lemon.utils.HttpClientUtil;
import com.lemon.utils.JsonUtils;

@Service
public class UserServiceImpl implements UserService{

	@Override
	public SsoUser getUserByToken(HttpServletRequest request, HttpServletResponse response) {
		//从cookie中取token
		String token = CookieUtils.getCookieValue(request, "LEMON_TOKEN");
		//判断token是否有值
		if (StringUtils.isBlank(token)) {
			return null;
		}
		//调用sso的服务查询用户信息
		String json = HttpClientUtil.doGet("http://localhost:1527/sso/token/" + token);
		//把json转换成java对象
		//CommonResult result = JsonUtils.jsonToPojo(json, CommonResult.class);
		CommonResult result = JSON.parseObject(json, CommonResult.class);  
		if (result.getCode() != 2000) {
			return null;
		}
		//取用户对象
		JSONObject jsonObject = (JSONObject) result.getData();
		SsoUser user = jsonObject.toJavaObject(SsoUser.class);
		
		return user;

	}
	
	public static void main(String[] args) {
		String json = HttpClientUtil.doGet("http://localhost:1527/sso/token/" 
	+ "f9cd87a0-724b-4fb0-b7e7-595c751b4d2c");
		System.out.println(json);
	}

}
