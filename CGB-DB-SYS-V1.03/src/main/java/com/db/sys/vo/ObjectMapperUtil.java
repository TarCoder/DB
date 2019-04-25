package com.db.sys.vo;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	public static String toJson(Object obj) {
		String json = null;
		try {
			json = objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return json;
	}
	
	public static <T> T toObject(String json,Class<T> cls) {
		T t = null;
		try {
			t = objectMapper.readValue(json,cls);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return t;
	}
	
	
}
