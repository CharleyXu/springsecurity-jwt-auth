package com.xu.jsonwebtoken.init;

import com.xu.jsonwebtoken.bean.SysRole;
import com.xu.jsonwebtoken.bean.SysUser;
import com.xu.jsonwebtoken.repository.SysRoleRepository;
import com.xu.jsonwebtoken.repository.SysUserRepository;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author CharleyXu Created on 2018/4/28.
 */
@Component
@Order(2)
public class MyCommandLineRunner implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyCommandLineRunner.class);
	@Autowired
	private SysUserRepository sysUserRepository;
	@Autowired
	private SysRoleRepository sysRoleRepository;

	@Override
	public void run(String... strings) throws Exception {
		LOGGER.info(">>>>>>>>>>>>>>>服务启动检查1，开始检查用户系统<<<<<<<<<<<<<");

		if (sysUserRepository.count() != 0) {
			LOGGER.info(">>>>>>>>>>>>>>>用户已经初始化<<<<<<<<<<<<<");
		} else {
			LOGGER.info(">>>>>>>>>>>>>>不存在初始用户，开始创建<<<<<<<<<<<<<");
			SysRole sysRole1 = new SysRole(1L, "ROLE_ADMIN");
			SysRole sysRole2 = new SysRole(2L, "ROLE_USER");
			sysRoleRepository.save(Arrays.asList(sysRole1, sysRole2));

			List<SysRole> roles1 = Arrays.asList(sysRole1);
			SysUser sysUser1 = new SysUser(1L, "root", "nibudong");
			sysUser1.setRoles(roles1);

			List<SysRole> roles2 = Arrays.asList(sysRole2);
			SysUser sysUser2 = new SysUser(2L, "shilei", "nicai");
			sysUser2.setRoles(roles2);

			sysUserRepository.save(Arrays.asList(sysUser1, sysUser2));
			LOGGER.info(">>>>>>>>>>>>>>初始用户创建结束<<<<<<<<<<<<<");
		}

		LOGGER.info(">>>>>>>>>>>>>>>检查用户系统结束<<<<<<<<<<<<<");
	}
}
