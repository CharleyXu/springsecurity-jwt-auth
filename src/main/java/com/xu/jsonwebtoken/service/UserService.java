package com.xu.jsonwebtoken.service;

import com.xu.jsonwebtoken.bean.SysUser;
import com.xu.jsonwebtoken.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author CharleyXu Created on 2018/4/28.
 *
 *  自定义登录校验
 */
@Service
public class UserService implements UserDetailsService {

	@Autowired
	SysUserRepository sysUserRepository;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		SysUser user = sysUserRepository.findByUsername(s);
		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		return user;
	}

	public boolean validate(SysUser user) {
		SysUser entity = sysUserRepository
				.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		if (entity != null) {
			return true;
		}
		return false;
	}

	public boolean save(SysUser user) {
		sysUserRepository.save(user);
		return true;
	}

	public boolean delete(Long id) {
		sysUserRepository.delete(id);
		return true;
	}

	public boolean edit(SysUser user) {
		sysUserRepository.save(user);
		return true;
	}

	public boolean exits(SysUser user) {
		SysUser entity = sysUserRepository.findByUsername(user.getUsername());
		if (entity != null) {
			return true;
		}
		return false;
	}


}
