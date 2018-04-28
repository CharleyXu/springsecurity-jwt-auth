package com.xu.jsonwebtoken.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CharleyXu Created on 2018/4/25.
 */
public class JWTUtils {

	public static final String JWT_HEADER = "Authorization";

	public static final String JWT_TOKEN_PREFIX = "Bearer";

	private static final String JWT_SECRET = "L7A56zARSkK1j7Vd5SDD9pSSqZlqF7mAhiOgRbg09Sm2e6tf4cJnvKOjtKPxNNnWQj+2lQEScm3XIUjhW+YVZg==";

	//1800_000 	half hour		3600_000	1 hour		1day	864	7day	6048
	private static final long JWT_EXPIRATION_TIME = 604800_000L;

	private static final Logger LOGGER = LoggerFactory.getLogger(JWTUtils.class);

	/**
	 * 解析JWT
	 */
	public static Claims parseJWT(String jsonWebToken) {
		Claims claims = null;
		try {
			claims = Jwts.parser()
					.setSigningKey(JWT_SECRET)
					.parseClaimsJws(jsonWebToken.replace(JWT_TOKEN_PREFIX, ""))
					.getBody();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return claims;
	}

	/**
	 * 创建JWT
	 */
	public static String createJWT(String userName) {
		long currentTimeMillis = System.currentTimeMillis();
		return Jwts.builder()
				.setSubject(userName)
				.setExpiration(new Date(currentTimeMillis + JWT_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET)
				.compact();
	}

	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}
}
