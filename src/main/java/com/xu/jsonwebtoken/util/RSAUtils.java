package com.xu.jsonwebtoken.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;

/**
 * @author CharleyXu Created on 2018/4/25.
 */
public class RSAUtils {

	private static final String KEY_ALGORITHM = "RSA";

	private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4GEIMeYzs9jbcItqdFBQLFaFbUe487beO1nKLV2NGTii2YU91Sq0G2AFiNtIDiDiQHMnX5KZoi4Sl2gnlTnNFrU3i/eMm2ZQEa8+BVlMEwF5/T25vS5INoqPYpphF/avbZvA1NhTpr8O7ysf0IQXlEb5LAd96t+AuWXMsdmI5GwIDAQAB";
	private static final String PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALgYQgx5jOz2Ntwi2p0UFAsVoVtR7jztt47WcotXY0ZOKLZhT3VKrQbYAWI20gOIOJAcydfkpmiLhKXaCeVOc0WtTeL94ybZlARrz4FWUwTAXn9Pbm9Lkg2io9immEX9q9tm8DU2FOmvw7vKx/QhBeURvksB33q34C5Zcyx2YjkbAgMBAAECgYEAoWGKjPsFIjlYsDPRpv+R7oGguLgi+8B8ODgfo60QtvIPWIoHqM+0zJoQ5HMgOb5xr0u9WYf0pRMhTm7MW/sgqoWP5r5amCv4ABitHdU7t74aIpGfTvEQcU8rJLSIx8PsejXkgoG6dxAAyIFoFL/T8eLoZUkvrNtcHyIKuk9D4WECQQD1sO5zX4Cf9gHeupP0CHFd4kqKomAGR5nAt3PG3ZUmxY4+4kDLLSaxafeyNuLQNqsoPMB6+mSN/kPNJIG8IqHRAkEAv9Gx2zgj+p4xPyVXP1KmaZoW3sjt1fqdBZLY74O9CEVMMmAzh+nv6FxyPAZRIUm0ThEu1XVRN+s8Y+IivYAbKwJBAJfWbqcSoHU7HdC+Ue2Awj8ZOKQQ0XNQEK9PMNf33NOHR++I+C4Yi08dhBy5Wtb+0iwdz36w/C/csZ44L41LgeECQHdbuFw8lsa/4Mz2I1AX0c0sQMboKHZZt5NcSPjg9KNM25kNG4ObIdlojPykwZ8BCdz5/DZ/BcbR7YYJlKxuJnkCQQDzDRKRBUO+Ek14wPVmXMAYld1v92ttot8LunlQcfGUj3M05990pJsII3V4y1xn56EN74iHI+5J374vFyeiUqLi";


	public static final String decryptByPrivateKey(String data){
		try {
			return decryptByPrivateKey(data, PRIVATE_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用私钥解密
	 */
	private static String decryptByPrivateKey(String data, String key)
			throws Exception{
			// 对密文解密
			byte[] bytes = decryptBASE64(data);
			// 对密钥解密
			byte[] keyBytes = decryptBASE64(key);
			// 取得私钥
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			// 对数据解密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return new String(cipher.doFinal(bytes));
	}

	/**
	 * 用公钥加密
	 * @param data  加密数据
	 * @param key   密钥
	 * @return
	 * @throws Exception
	 */
	private static byte[] encryptByPublicKey(String data, String key)
			throws Exception {
		// 对公钥解密
		byte[] keyBytes = decryptBASE64(key);
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data.getBytes());
	}

	/**
	 * 取得私钥
	 */
	private static String getPrivateKey(Map<String, Key> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get("private_key");
		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 取得公钥
	 */
	private static String getPublicKey(Map<String, Key> keyMap) throws Exception {
		Key key = keyMap.get("public_key");
		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 初始化密钥
	 */
	private static Map<String, Key> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator
				.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		Map<String, Key> keyMap = new HashMap(2);
		keyMap.put("public_key", keyPair.getPublic());// 公钥
		keyMap.put("private_key", keyPair.getPrivate());// 私钥
		return keyMap;
	}

	private static byte[] decryptBASE64(String key) {
		return Base64.getDecoder().decode(key);
	}

	private static String encryptBASE64(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}

	public static void main(String[] args) throws Exception {
		//前台JS传递的secret
		String secret = "a71eFGscJC1WMe5QtEu56xYZm7VpvjyrNG+wzK3fvl6zF+ddoVDwe9UCe+iZ+KkojMaAqhjnh91pGSKZJUuTk1BvxhseiijpKmZ7x6Jtq7fL/CbxcZVh7X+tjLBI/G3T4iQVY//EhImEnNBxTF9wrrKNaXE8T9Zk1G3BQ+FVlW8=";
		String outputStr = RSAUtils
				.decryptByPrivateKey(secret);
		System.out.println("前台secret解密后: " + outputStr);

		String inputStr = "1524811619917";
		byte[] encodedData = RSAUtils.encryptByPublicKey(inputStr, PUBLIC_KEY);
		String encodeToString = Base64.getEncoder().encodeToString(encodedData);
		String decryptString = RSAUtils.decryptByPrivateKey(encodeToString,
				PRIVATE_KEY);
		System.out.println("加密前: " + inputStr + "\n\r" + "加密后: " + encodeToString + "\n解密后: " + decryptString);
		//unicode转码
		String original = "#+//a71eFGscJC1WMe5QtEu56xYZm7VpvjyrNG+wzK3fvl6zF+ddoVDwe9UCe+iZ+KkojMaAqhjnh91pGSKZJUuTk1BvxhseiijpKmZ7x6Jtq7fL/CbxcZVh7X+tjLBI/G3T4iQVY//EhImEnNBxTF9wrrKNaXE8T9Zk1G3BQ+FVlW8=";
		String urlEncode = "%23%2B%2F%2Fa71eFGscJC1WMe5QtEu56xYZm7VpvjyrNG%2BwzK3fvl6zF%2BddoVDwe9UCe%2BiZ%2BKkojMaAqhjnh91pGSKZJUuTk1BvxhseiijpKmZ7x6Jtq7fL%2FCbxcZVh7X%2BtjLBI%2FG3T4iQVY%2F%2FEhImEnNBxTF9wrrKNaXE8T9Zk1G3BQ%2BFVlW8%3D";
		String urlDecoderString = UrlUtils.getURLDecoderString(urlEncode);
		System.out.println("Encode:"+UrlUtils.getURLEncoderString(original));
		System.out.println(original.equals(urlDecoderString));
	}
}
