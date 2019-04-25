package com.db.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Service;

import com.db.test.pojo.Black;
import com.db.test.pojo.Red;

@Configuration
@ComponentScan(value="com.db.test",includeFilters={
		@Filter(type=FilterType.ANNOTATION,classes={Service.class}),//按照注解类型
		@Filter(type=FilterType.ASSIGNABLE_TYPE,classes={Black.class}),//按照给定的类型
		@Filter(type=FilterType.CUSTOM,classes={MyTypeFilter.class})
		})
public class MyConfig {
	
	@Bean
	public Red red(){
		return new Red();
	}
	
}
