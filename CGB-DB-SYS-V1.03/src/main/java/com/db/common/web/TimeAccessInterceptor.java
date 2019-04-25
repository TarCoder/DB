package com.db.common.web;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.db.common.exception.ServiceException;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.CalendarDeserializer;
@Component
public class TimeAccessInterceptor extends HandlerInterceptorAdapter {
	/**
	 * 控制层目标方法执行之前执行
	 * @param request
	 * @param response
	 * @param handler
	 * @return 此方法返回值会决定我们的请求是否要交给后端控制器执行
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("**TimeAccessInterceptor.preHandle()**");
		//获取当前时间的日历对象
		Calendar c = Calendar.getInstance();
		//修改当前时间的时分秒
		c.set(Calendar.HOUR_OF_DAY,8);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		//获取修改之后的时间毫秒值（可以作为访问开始时间）
		long startTime = c.getTimeInMillis();
		//修改当前时间的时分秒
		c.set(Calendar.HOUR_OF_DAY,20);
		//获取修改之后的时间毫秒值（可以作为访问结束时间）
		long endTime = c.getTimeInMillis();
		long currentTime = System.currentTimeMillis();
		if(currentTime < startTime||currentTime > endTime){
			throw new ServiceException("不在访问时间内");
		}
		return true;//表示放行
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("**TimeAccessInterceptor.afterCompletion()**");
		super.afterCompletion(request, response, handler, ex);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("**TimeAccessInterceptor.postHandle()**");
		super.postHandle(request, response, handler, modelAndView);
	}
	
	
	
}
