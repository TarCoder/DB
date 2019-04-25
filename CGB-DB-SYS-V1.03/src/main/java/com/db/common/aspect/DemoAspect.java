package com.db.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.db.common.utils.SpringUtils;

@Order(2)
@Service
@Aspect
public class DemoAspect {

	@Pointcut("bean(*ServiceImpl)")
	public void demoPointCut(){}

	@Before("demoPointCut()")
	public void doBefore(){
		System.out.println("**@Before**");
	}
	@After("demoPointCut()")
	public void doAfter(){
		System.out.println("**@After**");
	}
	/**核心业务正常结束时执行
	 * 说明：假如有after，先执行after,再执行returning*/
	@AfterReturning("demoPointCut()")
	public void doAfterReturning(){
		System.out.println("**@AfterReturning**");
	}
	/**核心业务出现异常时执行
                 说明：假如有after，先执行after,再执行Throwing*/
	@AfterThrowing("demoPointCut()")
	public void doAfterThrowing(){
		System.out.println("**@AfterThrowing**");
	}
	@Around("demoPointCut()")
	public Object doAround(ProceedingJoinPoint jp) throws Throwable{
		System.out.println("**@AroundBefore**");
		Object obj = jp.proceed();
		System.out.println("**@AroundAfter**");
		return obj;
	}
	
	
	/**
	 * 切面通知的执行顺序：
	 *        **@AroundBefore**
	 *        **@Before**
	 *        **@AroundAfter**
	 *        **@After**
	 * 		  **@AfterReturning**
	 */
	
	
	
	@After("@annotation(com.db.common.annotation.TestOnly)")
	public void doAfter02(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-configs.xml");
		String[] names = context.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println("**"+name+"**");
		}
	}

}
