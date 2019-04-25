package com.db.test.service.impl;

import org.springframework.stereotype.Service;

import com.db.test.service.TestService;
@Service
public class TestServiceImpl implements TestService {

	@Override
	public int search(Integer id) {
		return 2;
	}
	
}
