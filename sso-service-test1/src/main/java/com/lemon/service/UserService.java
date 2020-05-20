package com.lemon.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lemon.entity.SsoUser;

public interface UserService {

	SsoUser getUserByToken(HttpServletRequest request, HttpServletResponse response);
}
