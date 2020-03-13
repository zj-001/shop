package com.newsoft.shop.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinasofti.commons.CommonUtils;
import com.newsoft.shop.bean.User;
import com.newsoft.shop.service.UserService;

@Controller
public class UserContriller {

	@Autowired
	UserService userService;
	
	/**
	 * 登录
	 * @param user
	 * @param model
	 * @param request
	 * @return
	 */
	@PostMapping("/login")
	public String toLogin(User user, Model model, HttpServletRequest request) {
		System.out.println(user);
		// 验证账号名
		String uname = user.getUname();
		if (StringUtils.isEmpty(uname) || uname.length() < 3 || uname.length() > 6) {
			model.addAttribute("error", "账号名错误");
			return "login";
		} 
		User findUser = userService.getUserByUname(uname).get(0);
		if(findUser==null) {
			model.addAttribute("error", "账号名错误");
			return "login";
		}
		//验证密码
		if(!findUser.getPassword().equals(user.getPassword())) {
			model.addAttribute("error", "密码错误");
			return "login";
		}
		// 登录
		request.getSession().setAttribute("session_user", findUser);
		return "loginSuccess";
	}
	/**
	 *    注册
	 * @param user
	 * @param model
	 * @param request
	 * @return
	 */
	@PostMapping("/register")
	public String toRegister(User user, Model model, HttpServletRequest request) {
		System.out.println(user);
		// 验证账号名
		String uname = user.getUname();
		if (StringUtils.isEmpty(uname) || uname.length() < 3 || uname.length() > 6) {
			model.addAttribute("error", "账号名错误");
			request.getSession().removeAttribute("sendCode");
			return "register";
		} else {
			List<User> findUsers = userService.getUserByUname(uname);
			if (findUsers.size() > 0) {
				model.addAttribute("error", "账号名已存在");
				request.getSession().removeAttribute("sendCode");
				return "register";
			}
		}
		//验证邮箱是否已被注册
		List<User> findUsers = userService.getUserByEmail(user.getEmail());
		if(findUsers.size()>0) {
			model.addAttribute("error", "邮箱已被注册");
			request.getSession().removeAttribute("sendCode");
			return "register";
		}
		// 验证 验证码
		String code = user.getCode();
		Object sendCode = request.getSession().getAttribute("sendCode");
		if (StringUtils.isEmpty(code) || code.length() != 6) {
			model.addAttribute("error", "请输入6位验证码");
			model.addAttribute("user", user);
			request.getSession().removeAttribute("sendCode");
			return "register";
		} else {
			if (!code.equals(sendCode)) {
				model.addAttribute("error", "验证码错误");
				request.getSession().removeAttribute("sendCode");
				model.addAttribute("user", user);
				return "register";
			}
		}
		// 插入数据库
		user.setRigistertime(new Date());
		user.setStatus(true);
		user.setUid(CommonUtils.uuid());
		userService.addUser(user);
		request.getSession().removeAttribute("sendCode");
		return "registerSuccess";
	}
	/**
	 * 发送邮件
	 * @param request
	 * @param response
	 * @param email
	 * @throws IOException
	 */
	@RequestMapping("/sendCode")
	public void sendCode(HttpServletRequest request, HttpServletResponse response, String email) throws IOException {
		String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		response.setCharacterEncoding("UTF-8");
		List<User> findUsers = userService.getUserByEmail(email);
		if (StringUtils.isEmpty(email)) {
			response.getWriter().write("{'error':'邮箱不能为空'}");
		} else if (!email.matches(regex)) {
			response.getWriter().write("{'error':'邮箱格式错误'}");
		} else {
			User user = new User();
			user.setEmail(email);
			String sendCode = userService.sendEmail(user);
			request.getSession().setAttribute("sendCode", sendCode);
			response.getWriter().write("{'error':'邮箱也发送'}");
		}

	}
}
