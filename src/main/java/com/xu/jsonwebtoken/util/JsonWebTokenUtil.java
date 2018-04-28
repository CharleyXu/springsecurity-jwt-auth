package com.xu.jsonwebtoken.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CharleyXu Created on 2018/4/11.
 */
public class JsonWebTokenUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonWebTokenUtil.class);
	public static final long EXPIRATION_TIME = 5000; //1800_000 0.5 hour	3600_000	 1 hour
	//加密解密的key
	public static final String SECRET = "L7A56zARSkK1j7Vd5SDD9pSSqZlqF7mAhiOgRbg09Sm2e6tf4cJnvKOjtKPxNNnWQj+2lQEScm3XIUjhW+YVZg==";
	public static final String TOKEN_PREFIX = "Bearer";
	public static final String HEADER_STRING = "Authorization";
	public static final String ROLE = "ROLE";

	public static String generateToken(String userName) {
		String jwt = Jwts.builder()
				.setSubject(userName)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		return TOKEN_PREFIX + " " + jwt;
	}

	public static void getClaimsFromToken(String token){
		try {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""));
			Claims body = claimsJws.getBody();
			//jwt 已经过期，在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期，如果过期则会抛出这个异常，我们可以抓住这个异常并作相关处理
			System.out.println("Claims:"+body);
			System.out.println("sub:"+body.getSubject()+",exp:"+body.getExpiration());
		} catch (SignatureException | MalformedJwtException e) {
			LOGGER.error(e.getMessage(),e);
			// jwt 解析错误
		} catch (ExpiredJwtException e) {
			LOGGER.error(e.getMessage(),e);
		}

	}

	public static void main(String[] args) {
		Key secretKey = MacProvider.generateKey();
		String token = generateToken("user");
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(token);
		getClaimsFromToken(token);

	}

	public static Map<String, Object> validateTokenAndGetClaims(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token == null){
			throw new TokenValidationException("Missing token");
		}
		// parse the token. exception when token is invalid
		Map<String, Object> body = Jwts.parser()
				.setSigningKey(SECRET)
				.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
				.getBody();
		return body;
	}

	static class TokenValidationException extends RuntimeException {
		public TokenValidationException(String msg) {
			super(msg);
		}
	}
}
