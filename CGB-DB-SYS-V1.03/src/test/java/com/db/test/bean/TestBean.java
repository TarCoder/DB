package com.db.test.bean;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.db.test.config.MyConfig;

public class TestBean {
	
	ApplicationContext con;
	
	@Test
	public void test01() {
		con = new ClassPathXmlApplicationContext("spring-configs.xml");
		String[] names = con.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println("**"+name+"**");
		}
	}
	
	@Test
	public void test02(){
		con = new AnnotationConfigApplicationContext(MyConfig.class);
		String[] names = con.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println("**"+name+"**");
		}
	}
}
