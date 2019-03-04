package xyz.thelostsoul;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.thelostsoul.bean.User;
import xyz.thelostsoul.service.impl.RedisServiceTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FirstApplicationTests {

	@Autowired
	private RedisServiceTest service;

	@Test
	public void contextLoads() {
		User user = new User(19, "zzm");
		service.addUser(user);

		System.out.println(service.getStudent(user.getId()).getId());
	}

}
