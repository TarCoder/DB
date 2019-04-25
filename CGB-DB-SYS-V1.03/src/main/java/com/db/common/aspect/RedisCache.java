package com.db.common.aspect;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.db.sys.vo.ObjectMapperUtil;

import redis.clients.jedis.Jedis;

import com.db.common.annotation.RediCache;

@Aspect
@Component
public class RedisCache {
	
	@Autowired
	private Jedis jedis;
	
	//@annotation
	@Around("@annotation(com.db.common.annotation.RediCache)")
	public Object findItemCatList(ProceedingJoinPoint jp) throws Throwable{
		Object result = null;
		Signature signature = jp.getSignature();
		
		//获取类
		Class<?> target = jp.getTarget().getClass();
		//获取参数列表
		Object[] args = jp.getArgs();
		//获取参数列表的类型
		Class<?>[] parameterTypes = new Class<?>[args.length];
		for (int i = 0;i<args.length;i++) {
			parameterTypes[i] = args[i].getClass();
		}
		//获取方法
		Method method = target.getMethod(signature.getName(), parameterTypes);
		//获取方法上的Cache注解
		RediCache annotation = method.getAnnotation(RediCache.class);
		//获取注解的信息
		String value = annotation.value();
		//获取方法返回值类型
		Type genericReturnType = method.getGenericReturnType();
		Class<?> cls = Class.forName(genericReturnType.getTypeName());
		//获取key值
		String key = target.getName()+"_"+method.getName();
		System.out.println("***"+key);
		for (int i = 0;i<args.length;i++) {
			key = key +"_"+ args[i];
		}
		//从Redis里获取数据
		String json = jedis.get(key);
		if(StringUtils.isEmpty(json)) {
			//如果没有,就从数据库中获取数据,并存到Redis里
			result = jp.proceed();
			String json2 = ObjectMapperUtil.toJson(result);
			jedis.set(key, json2);
			//设置缓存的有效时间为60s
			jedis.expire(key, 60);
			System.out.println("**从数据库获取数据**");
		}else {
			result = ObjectMapperUtil.toObject(json, cls);
			System.out.println("**从Redis获取数据**");
		}
		return result;
	}
}
