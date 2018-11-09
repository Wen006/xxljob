package com.xxl.job.admin.core.db.keygen.impl;

import java.util.concurrent.atomic.AtomicInteger;

import com.xxl.job.admin.core.db.keygen.XxlKeyGentor;

public class SimpleKeyGentor implements XxlKeyGentor<Integer> {
	
	public static AtomicInteger at = new AtomicInteger(1);
	

	@Override
	public Integer nextId() {
		// TODO Auto-generated method stub
		return at.getAndAdd(1);
	}

}
