package com.lemon.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lemon.service.test;
import com.lemon.utils.util.RedisUtils;

import redis.clients.jedis.Jedis;

@Controller
public class HelloWorldController {

	@Autowired
	private test service;
	
	@RequestMapping("/test")
	public String test(HttpServletRequest request){
		String string = service.getPassword();
		request.setAttribute("hello", string);
		System.out.println(string);
		Jedis jedis = RedisUtils.getJedis();
		jedis.set("test", "hello jedis");
		String string2 = jedis.get("test");
		request.setAttribute("hello2", string2);
		return "test";
	}
	
}
