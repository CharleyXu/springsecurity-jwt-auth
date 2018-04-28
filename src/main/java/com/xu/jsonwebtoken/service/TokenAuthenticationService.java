package com.xu.jsonwebtoken.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collections;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * @author CharleyXu Created on 2018/4/28.
 */
public class TokenAuthenticationService {

	private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 1; // 1 days
	private static final String SECRET = "ThisIsASecret";
	private static final String TOKEN_PREFIX = "Bearer";
	private static final String HEADER_STRING = "Authorization";

	public static void addAuthentication(HttpServletResponse res, String username) {
		String JWT = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}

	public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			// parse the token.
			String user = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody()
					.getSubject();

			return user != null ?
					new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) :
					null;
		}
		return null;
	}
}
