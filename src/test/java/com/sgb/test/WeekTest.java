package com.sgb.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sgb.bean.User;
import com.sgb.utils.StringUtils;
import javax.annotation.Resource;
@SuppressWarnings("restriction")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:applicationContext-redis.xml")
public class WeekTest {
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Test
	public void testDate(){
		List<User> ulist=new ArrayList<User>();
		for (int i = 0; i <100000; i++) {
			User user = new User();
			user.setId(i+1);
			String randomChinese = StringUtils.getRandomChinese(3);
			user.setName(randomChinese);
			Random random = new Random();
			String sex = random.nextBoolean()?"男":"女";
			user.setSex(sex);
			String phone ="13"+ StringUtils.getRandomNumber(9);
			user.setPhone(phone);
			int random2 = (int) (Math.random()*20);
			int len=random2<3?random2+3:random2;
			String randomStr = StringUtils.getRandomStr(len);
			String randomEmailSuffex = StringUtils.getRandomEmailSuffex();
			user.setEmail(randomStr+randomEmailSuffex);
			String randomBirthday = StringUtils.randomBirthday();
			user.setBirthday(randomBirthday);
			ulist.add(user);
	}
		/*System.out.println("jdk的序列化方式");
		long start = System.currentTimeMillis();
		BoundListOperations<String, Object> boundListOps = redisTemplate.boundListOps("jdk");
		boundListOps.leftPush(ulist);
		long end = System.currentTimeMillis();
		System.out.println("耗时:"+(end-start)+"毫秒");*/
		/*System.out.println("json的序列化方式");
		long start = System.currentTimeMillis();
		BoundListOperations<String, Object> boundListOps = redisTemplate.boundListOps("json");
		boundListOps.leftPush(ulist);
		long end = System.currentTimeMillis();
		System.out.println("耗时:"+(end-start)+"毫秒");*/
		System.out.println("hash的序列化方式");
		long start = System.currentTimeMillis();
		BoundHashOperations<String, Object, Object> boundHashOps = redisTemplate.boundHashOps("hash");
		boundHashOps.put("hash", ulist);
		long end = System.currentTimeMillis();
		System.out.println("耗时:"+(end-start)+"毫秒");

}
}
