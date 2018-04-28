package com.xu.jsonwebtoken.util;

import java.io.UnsupportedEncodingException;

/**
 * @author CharleyXu Created on 2018/4/26.
 */
public class UrlUtil {
	private final static String ENCODE = "GBK";
	/**
	 * URL 解码
	 *
	 * @return String
	 * @author lifq
	 * @date 2015-3-17 下午04:09:51
	 */
	public static String getURLDecoderString(String str) {
		String result = "";
		if (null == str) {
			return "";
		}
		try {
			result = java.net.URLDecoder.decode(str, ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * URL 转码
	 *
	 * @return String
	 * @author lifq
	 * @date 2015-3-17 下午04:10:28
	 */
	public static String getURLEncoderString(String str) {
		String result = "";
		if (null == str) {
			return "";
		}
		try {
			result = java.net.URLEncoder.encode(str, ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 *
	 * @return void
	 * @author lifq
	 * @date 2015-3-17 下午04:09:16
	 */
	public static void main(String[] args) {
		String str = "y2SGbYFbXRqZ2PaBCrGJNA==";
		String urlEncoderString = getURLEncoderString(str);
		System.out.println(urlEncoderString);
		System.out.println(getURLDecoderString(str));
		String other = "s8nNGGKsgScsYmw7QHbu%2FNFxf%2FBsYfhppAAj%2FL38brg1fkUq2Tv%2BRSWU7NKblElcS%2BmOw61Gryf1LlXLf1PZ%2Bw%3D%3D";
		System.out.println(urlEncoderString.equals(other));
	}
}
