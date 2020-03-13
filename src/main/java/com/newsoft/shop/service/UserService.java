package com.newsoft.shop.service;

import java.util.List;

import com.newsoft.shop.bean.User;

public interface UserService {
	
	/**
	 * 添加
	 * @param user
	 */
	void addUser(User user);
	/**
	 * 163邮箱发送验证码
	 * @param user
	 */
	String sendEmail(User user);
	/**
	 * 通过用户名查找用户
	 * @param uname
	 * @return
	 */
	List<User> getUserByUname(String uname);
	/**
	 * 通过邮箱查找用户
	 * @param email
	 * @return
	 */
	List<User> getUserByEmail(String email);
}
