package com.xu.jsonwebtoken.util;

import java.io.UnsupportedEncodingException;

/**
 * @author CharleyXu Created on 2018/4/27.
 */
public class UrlUtils {
	private final static String ENCODE = "UTF-8";
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

	public static void main(String[] args) {
		//unicode转码
		String original = "你好_=#+//a71eFGscJC1WMe5QtEu56xYZm7VpvjyrNG+wzK3fvl6zF+ddoVDwe9UCe+iZ+KkojMaAqhjnh91pGSKZJUuTk1BvxhseiijpKmZ7x6Jtq7fL/CbxcZVh7X+tjLBI/G3T4iQVY//EhImEnNBxTF9wrrKNaXE8T9Zk1G3BQ+FVlW8=";
		String urlEncode = "_%3D%23%2B%2F%2Fa71eFGscJC1WMe5QtEu56xYZm7VpvjyrNG%2BwzK3fvl6zF%2BddoVDwe9UCe%2BiZ%2BKkojMaAqhjnh91pGSKZJUuTk1BvxhseiijpKmZ7x6Jtq7fL%2FCbxcZVh7X%2BtjLBI%2FG3T4iQVY%2F%2FEhImEnNBxTF9wrrKNaXE8T9Zk1G3BQ%2BFVlW8%3D";
		String urlDecoderString = UrlUtils.getURLDecoderString(urlEncode);
		System.out.println("Encode:"+UrlUtils.getURLEncoderString(original));
		System.out.println(original.equals(urlDecoderString));
	}
}
