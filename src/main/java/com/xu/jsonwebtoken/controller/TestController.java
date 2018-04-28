package com.xu.jsonwebtoken.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CharleyXu Created on 2018/4/11.
 */
@RestController
public class TestController {

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello() {
		return "123";
	}


}
