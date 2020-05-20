package com.lemon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemon.entity.SsoUser;
import com.lemon.mapper.SsoUserMapper;
import com.lemon.service.test;

@Service
public class impl implements test{

	@Autowired
	private SsoUserMapper mapper;
	
	@Override
	public String getPassword() {
		long id = 1;
		SsoUser user = mapper.selectByPrimaryKey(id);
		return user.getAppkey();
	}

}
