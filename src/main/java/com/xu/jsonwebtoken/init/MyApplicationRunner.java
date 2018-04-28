package com.xu.jsonwebtoken.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author CharleyXu Created on 2018/4/28.
 *
 * 启动初始化数据,比如读取配置文件，数据库连接
 */
@Component
@Order(1)
public class MyApplicationRunner implements ApplicationRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyApplicationRunner.class);

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		LOGGER.info("==服务启动后，初始化数据操作==");
	}
}
