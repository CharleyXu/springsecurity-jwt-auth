package com.xu.jsonwebtoken.bean;

import java.io.Serializable;

/**
 * @author CharleyXu Created on 2018/4/9.
 */
public class RestfulResponse implements Serializable {

	private static final long serialVersionUID = -981176702199566057L;
	private Object data;
	private String message;
	private int code;

	public RestfulResponse() {
	}

	public RestfulResponse(Object data, String message, int code) {
		this.data = data;
		this.message = message;
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "RestfulResponse{" +
				"data=" + data +
				", message='" + message + '\'' +
				", code=" + code +
				'}';
	}
}
