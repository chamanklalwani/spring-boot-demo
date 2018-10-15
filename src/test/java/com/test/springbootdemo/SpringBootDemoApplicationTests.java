package com.test.springbootdemo;

import com.test.springbootdemo.init.Main;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes={Main.class})
public class SpringBootDemoApplicationTests {

	@Test
	public void contextLoads() {
	}

}
