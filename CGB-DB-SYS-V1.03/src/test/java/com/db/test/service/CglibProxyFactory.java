package com.db.test.service;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CglibProxyFactory {
	/**
	 * 为目标对象创建代理对象（目标对象无实现接口）
	 * @param target 目标对象
	 * @return 代理对象
	 */
	public static Object newProxy(Object target){
		//1.创建Enhancer对象（此对象相当于JDK中的Proxy类）
		Enhancer e = new Enhancer();
		//2.设置要创建的代理对象应该继承哪个类（目标对象的类）
		e.setSuperclass(target.getClass());
		//3.设置代理对象处理器
		e.setCallback(new CglibMethodInterceptor(target));
		Object proxy = e.create();
		return proxy;
	}
	
	public static void main(String[] args) {
		DefaultSearchService defaultSearchService = new DefaultSearchService();
		DefaultSearchService proxy = (DefaultSearchService)CglibProxyFactory.newProxy(defaultSearchService);
		proxy.search("mei");
	}
}

class CglibMethodInterceptor implements MethodInterceptor {
	
	Object target = null;
	
	public CglibMethodInterceptor() {
	}
	
	public CglibMethodInterceptor(Object target) {
		this.target = target;//目标对象
	}
	
	@Override
	public Object intercept(Object paramObject,//代理对象
			Method paramMethod,//目标对象的方法
			Object[] paramArrayOfObject,//实际参数
			MethodProxy paramMethodProxy)//目标对象的方法的代理对象
					throws Throwable {
		System.out.println("**"+paramObject.getClass().getName());
		System.out.println("**"+paramMethodProxy.getClass().getName());
		System.out.println("search start...");
		Object result = paramMethodProxy.invokeSuper(paramObject, paramArrayOfObject);//方法一
//		Object result = paramMethod.invoke(target,paramArrayOfObject);//方法二
		System.out.println("search end...");
		return result;
	}
	
}
