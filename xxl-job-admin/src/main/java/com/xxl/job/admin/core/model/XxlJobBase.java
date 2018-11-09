package com.xxl.job.admin.core.model;

import java.io.Serializable;

public class XxlJobBase<T extends Serializable> {

	T id;

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}
	
	
}
