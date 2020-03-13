package com.newsoft.shop;



import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chinasofti.commons.CommonUtils;
import com.newsoft.shop.bean.User;
import com.newsoft.shop.service.UserService;


@RunWith (SpringRunner.class)
@SpringBootTest
public class ShopApplicationTests {
	
	@Autowired
	UserService userService;
	
    @Test
    public void test01() {
    	User user=new User();
    	user.setCode("1234");
    	user.setEmail("z931407229@163.com");
		userService.sendEmail(user);
    }
    
    @Test
    public void test02() {
    	String uuid = new CommonUtils().uuid();
		System.out.println(uuid);
		Random random = new Random();
		int nextInt = random.nextInt(25);
		System.out.println(nextInt);
		System.out.println(uuid.substring(nextInt, nextInt+6));
    }
}
