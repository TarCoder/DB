package com.db.common.aspect;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

public class CacheKey<T> implements Serializable {
	
	private static final long serialVersionUID = 691438123435201180L;
	private Class<T> targetClass;
	private Method targetMethod;
	private Object[] args;
	public CacheKey(Class<T> targetClass, Method targetMethod, Object[] args) {
		super();
		this.targetClass = targetClass;
		this.targetMethod = targetMethod;
		this.args = args;
	}
	public Class<T> getTargetClass() {
		return targetClass;
	}
	public void setTargetClass(Class<T> targetClass) {
		this.targetClass = targetClass;
	}
	public Method getTargetMethod() {
		return targetMethod;
	}
	public void setTargetMethod(Method targetMethod) {
		this.targetMethod = targetMethod;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "CacheKey [targetClass=" + targetClass + ", targetMethod=" + targetMethod + ", args="
				+ Arrays.toString(args) + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(args);
		result = prime * result + ((targetClass == null) ? 0 : targetClass.hashCode());
		result = prime * result + ((targetMethod == null) ? 0 : targetMethod.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheKey other = (CacheKey) obj;
		if (!Arrays.equals(args, other.args))
			return false;
		if (targetClass == null) {
			if (other.targetClass != null)
				return false;
		} else if (!targetClass.equals(other.targetClass))
			return false;
		if (targetMethod == null) {
			if (other.targetMethod != null)
				return false;
		} else if (!targetMethod.equals(other.targetMethod))
			return false;
		return true;
	}
	
	
}
