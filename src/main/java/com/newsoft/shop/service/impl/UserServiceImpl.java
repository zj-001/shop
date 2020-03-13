package com.newsoft.shop.service.impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chinasofti.commons.CommonUtils;
import com.chinasofti.mail.Mail;
import com.chinasofti.mail.MailUtils;
import com.newsoft.shop.bean.User;
import com.newsoft.shop.bean.UserExample;
import com.newsoft.shop.exception.ServiceException;
import com.newsoft.shop.mapper.UserMapper;
import com.newsoft.shop.service.UserService;

/**
 * 用户serviceImpl
 * @author zj
 *
 */
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserMapper userMapper;
	
	@Override
	public void addUser(User user) {
		userMapper.insert(user);
	}
	
	@Override
	public String sendEmail(User user){
		Properties pr=new Properties();
		try {
			pr.load(this.getClass().getClassLoader()
					.getResourceAsStream("email.properties"));
			String host=pr.getProperty("host");
			String username=pr.getProperty("username");
			String password=pr.getProperty("password");
			Session session = MailUtils.createSession(host, username, password);
			String from=pr.getProperty("from");
			String to=user.getEmail();
			String subject=pr.getProperty("subject");
			String content=pr.getProperty("content");
			String uuid = CommonUtils.uuid();
			Random random = new Random();
			int nextInt = random.nextInt(25);
			String sendCode = uuid.substring(nextInt, nextInt+6);
			content=MessageFormat.format(content, sendCode);
			Mail mail=new Mail(from, to, subject, content);
			MailUtils.send(session, mail);
			return sendCode;
		} catch (Exception e) {
			throw new ServiceException("邮件发送失败");
		}
	}

	@Override
	public List<User> getUserByUname(String uname) {
		UserExample example=new UserExample();
		example.createCriteria().andUnameEqualTo(uname);
		List<User> findUsers = userMapper.selectByExample(example);
		return findUsers;
	}

	@Override
	public List<User> getUserByEmail(String email) {
		UserExample example=new UserExample();
		example.createCriteria().andEmailEqualTo(email);
		List<User> findUsers = userMapper.selectByExample(example);
		return findUsers;
	}
	
}
