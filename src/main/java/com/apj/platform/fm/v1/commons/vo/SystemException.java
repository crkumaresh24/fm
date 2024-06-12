package com.apj.platform.fm.v1.commons.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public abstract class SystemException extends Exception {
	public List<Object> params;

	public abstract String getErrorcode();

	public void addToParams(Object value) {
		if (null == params) {
			params = new ArrayList<>();
		}
		params.add(value);
	}

	public SystemException(String message) {
		super(message);
	}

	public HttpStatus getStatusCode() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
