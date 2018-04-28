package com.xu.jsonwebtoken.repository;

import com.xu.jsonwebtoken.bean.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CharleyXu Created on 2018/4/28.
 */
public interface SysUserRepository extends JpaRepository<SysUser,Long> {
	SysUser findByUsername(String username);

	SysUser findByUsernameAndPassword(String name, String password);
}
