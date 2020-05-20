package com.lemon.service.impl;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemon.entity.SsoUser;
import com.lemon.entity.SsoUserExample;
import com.lemon.mapper.SsoUserMapper;
import com.lemon.service.Loginservice;
import com.lemon.utils.CommonResult;
import com.lemon.utils.CookieUtils;
import com.lemon.utils.JsonUtils;
import com.lemon.utils.MD5Util;
import com.lemon.utils.ResultStatus;
import com.lemon.utils.util.RedisUtils;

import redis.clients.jedis.Jedis;

@Service
public class LoginServiceImpl implements Loginservice{
	
	@Autowired
	private SsoUserMapper mapper;

	@Override
	public CommonResult login(String appkey, String password, HttpServletRequest request,
			HttpServletResponse response) {
		SsoUserExample example = new SsoUserExample();
		SsoUserExample.Criteria criteria = example.createCriteria();
		criteria.andAppkeyEqualTo(appkey);
		//验证appkey
		List<SsoUser> list = mapper.selectByExample(example);
		if (list == null || list.isEmpty()) {
			return new CommonResult(ResultStatus.WARN, "用户名不存在！");
		}
		//验证密码
		SsoUser user = list.get(0);
		if(!user.getPassword().equals(MD5Util.MD5(password))) {
			return new CommonResult(ResultStatus.WARN, "密码错误！");
		}
		//登录成功
		//生成token
		String token = UUID.randomUUID().toString();
		//把用户信息写入redis
		//value:user转json
		user.setPassword(null);
		
		Jedis jedis = RedisUtils.getJedis();
		
		jedis.set(token, JsonUtils.objectToJson(user));
		//设置session的过期时间
		jedis.expire(token, 1800);
		//写cookie
		CookieUtils.setCookie(request, response, "LEMON_TOKEN", token);
		
		return new CommonResult(ResultStatus.SUCCESS, user);
	}

	@Override
	public CommonResult logout(String token, HttpServletRequest request,
			HttpServletResponse response) {
		Jedis jedis = RedisUtils.getJedis();
		jedis.del(token);
		CookieUtils.deleteCookie(request, response, token);
		return new CommonResult(ResultStatus.SUCCESS, "退出成功！");
	}

	@Override
	public CommonResult getUserByToken(String token) {
		Jedis jedis = RedisUtils.getJedis();
		String json = jedis.get(token);
		if (StringUtils.isBlank(json)) {
			return new CommonResult(ResultStatus.WARN, "用户session已经过期！");
		}
		SsoUser user = JsonUtils.jsonToPojo(json, SsoUser.class);
		jedis.expire(token, 1800);
		return new CommonResult(ResultStatus.SUCCESS, user);
	}

}
