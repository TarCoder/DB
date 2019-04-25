package com.db.common.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import com.db.common.utils.LruCache;

@Service
@Aspect
public class CacheAspect {
	
	private LruCache<CacheKey<?>,Object> cache = new LruCache<>(5);
	
	@Around("@annotation(com.db.common.annotation.RequiredCache)")
	public Object around(ProceedingJoinPoint jp) throws Throwable{
		System.out.println("CacheAspect.around()");
		CacheKey<?> key = getKey(jp);
		//1.从缓存中取数据，假如缓存中有则直接返回
		Object obj = cache.get(key);
		if(obj != null)return obj;
		//2.缓存没有数据，则直接目标方法
		obj = jp.proceed();
		//3.将目标方法的执行结果存储到缓存对象
		cache.put(key, obj);
		//4.返回结果
		return obj;
	}

	private CacheKey<?> getKey(ProceedingJoinPoint jp) {
		Class<?> targetClass = jp.getTarget().getClass();
		MethodSignature ms = (MethodSignature)jp.getSignature();
		Method method = ms.getMethod();
		Object[] args = jp.getArgs();
		CacheKey<?> key = new CacheKey<>(targetClass, method, args);
		return key;
	}
}
