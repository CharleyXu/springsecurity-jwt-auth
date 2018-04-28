package com.xu.jsonwebtoken.repository;

import com.xu.jsonwebtoken.bean.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CharleyXu Created on 2018/4/28.
 */
public interface SysRoleRepository extends JpaRepository<SysRole,Long> {
	
}
