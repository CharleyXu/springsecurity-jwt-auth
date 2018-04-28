package com.xu.jsonwebtoken.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xu.jsonwebtoken.bean.RestfulResponse;
import com.xu.jsonwebtoken.bean.SysUser;
import com.xu.jsonwebtoken.service.TokenAuthenticationService;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author CharleyXu Created on 2018/4/28.
 *
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	//或者继承UsernamePasswordAuthenticationFilter，还是重写这些方法

	public JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	/**
	 * 接收并解析用户凭证
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
			throws AuthenticationException, IOException, ServletException {
		SysUser sysUser = new ObjectMapper()
				.readValue(httpServletRequest.getInputStream(), SysUser.class);
		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
						sysUser.getUsername(),
						sysUser.getPassword(),
						Collections.emptyList()
				)
		);
	}

	/**
	 * 用户成功验证后，这个方法会被调用，我们在这个方法里生成token
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain, Authentication authResult) throws IOException, ServletException {
		TokenAuthenticationService.addAuthentication(response, authResult.getName());
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		ObjectMapper mapper = new ObjectMapper();
		response.getOutputStream().println(
				mapper.writeValueAsString(new RestfulResponse("0", "Internal Server Error!!!", 500)));
	}

	public static void main(String[] args) {
		String pass = "admin";
		BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
		String hashPass = bcryptPasswordEncoder.encode(pass);
		System.out.println(hashPass);

		boolean f = bcryptPasswordEncoder.matches("admin", hashPass);
		System.out.println(f);

	}

}
