package com.fline.form.util;

import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * AES-128-CBC 加密方式 注： AES-128-CBC可以自己定义“密钥”和“偏移量“。 AES-128是jdk自动生成的“密钥”。
 */
public class EncryptionUtil {
	private static final String DEFAULT_ENCRYPTION_KEY = Base64.encodeBase64String("1234567812345678".getBytes());
	private static final String DEFAULT_ENCRYPTION_IV = "y6eDiddV2nkpACbwB6Squg==";
	private static Logger LOG = LoggerFactory.getLogger(EncryptionUtil.class);
	static {
		// BouncyCastle是一个开源的加解密解决方案，主页在http://www.bouncycastle.org/
		Security.addProvider(new BouncyCastleProvider());
	}

	public static String defaultAesEncrypt(String data) throws Exception {
		return encrypt(data, DEFAULT_ENCRYPTION_KEY, DEFAULT_ENCRYPTION_IV, "UTF-8");
	}

	public static String defaultAesDecrypt(String data) throws Exception {
		return decrypt(data, DEFAULT_ENCRYPTION_KEY, DEFAULT_ENCRYPTION_IV, "UTF-8");
	}

	/**
	 * AES解密
	 *
	 * @param data           //密文，被加密的数据
	 * @param key            //秘钥
	 * @param iv             //偏移量
	 * @param encodingFormat //解密后的结果需要进行的编码
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String key, String iv, String encodingFormat) throws Exception {
//        initialize();

		// 待加密的数据
		byte[] dataByte = data.getBytes(encodingFormat);
		// 加密秘钥
		byte[] keyByte = Base64.decodeBase64(key);
		// 偏移量
		byte[] ivByte = Base64.decodeBase64(iv);

		try {
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");

			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));

			cipher.init(Cipher.ENCRYPT_MODE, spec, parameters);// 初始化

			byte[] resultBytes = cipher.doFinal(dataByte);
			if (null != resultBytes && resultBytes.length > 0) {
				String result = Base64.encodeBase64String(resultBytes);
				return result;
			}
			return null;
		} catch (Exception e) {
			LOG.error("AES encryption failed.", e);
		}
		return null;
	}

	/**
	 * AES解密
	 *
	 * @param data           //密文，被加密的数据
	 * @param key            //秘钥
	 * @param iv             //偏移量
	 * @param encodingFormat //解密后的结果需要进行的编码
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String data, String key, String iv, String encodingFormat) throws Exception {
//        initialize();

		// 被加密的数据
		byte[] dataByte = Base64.decodeBase64(data);
		// 加密秘钥
		byte[] keyByte = Base64.decodeBase64(key);
		// 偏移量
		byte[] ivByte = Base64.decodeBase64(iv);

		try {
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");

			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));

			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化

			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				String result = new String(resultByte, encodingFormat);
				return result;
			}
			return null;
		} catch (Exception e) {
			LOG.error("AES decryption failed.", e);
		}
		return null;
	}

	/**
	 * MD5 加密
	 * 
	 * @param s
	 * @return
	 */
	public static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			LOG.error("MD5 encryption failed.", e);
			return null;
		}
	}

	/**
	 * 使用私钥加密
	 * 
	 * @param data
	 * @param secretKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static String encrypt(String data, String privateKeyStr) throws Exception {
		byte[] privateKeyBytes = Base64.decodeBase64(privateKeyStr);
		RSAPrivateKey privateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
				.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		String result = new String(Base64.encodeBase64(cipher.doFinal(data.getBytes("UTF-8"))));
		return result;
	}

	public static String decrypt(String data, String publicKeyStr) throws Exception {
		byte[] inputData = Base64.decodeBase64(data);
		byte[] publicKeyBytes = Base64.decodeBase64(publicKeyStr);
		RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
				.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		String result = new String(cipher.doFinal(inputData), "UTF-8");
		return result;
	}

	/**
	 * 随机生成密钥对
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public static Map<Integer, String> genKeyPair() throws NoSuchAlgorithmException {
		Map<Integer, String> keyMap = new HashMap<Integer, String>(); // 用于封装随机产生的公钥与私钥
		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		// 初始化密钥对生成器，密钥大小为96-1024位
		keyPairGen.initialize(1024, new SecureRandom());
		// 生成一个密钥对，保存在keyPair中
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate(); // 得到私钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic(); // 得到公钥
		String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
		// 得到私钥字符串
		String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
		// 将公钥和私钥保存到Map
		keyMap.put(0, publicKeyString); // 0表示公钥
		keyMap.put(1, privateKeyString); // 1表示私钥
		return keyMap;
	}

	public static void main(String[] args) throws Exception {
		String appId = "test_app_2";
		// appPriKey 为 嵊州接口调用参数.txt中的 privateKey
		String appPriKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMi29wM97rpnt6frbpiYs8OIvVQlQUVp12fYneONfiiCPPZNXlSoOMY7FI1dqFVnhZlss8rW7CBkiiVI8PVZ5DxKi+MbHDxVXkkPFkuWYcq+WALfagOMUJtR3M5njP0X4Vi9CE1TAGgdUXEk+kYJQFSWW9n3ILx1KrdYl85Z7EopAgMBAAECgYEAs5z/yRfoBUmBUTRe4RFtdKJuAtnf4hUIHTb8e8CH0ApUMXkk3A851zLRfnv8gojERnq/HdpcrdQqx5jRgE3z7eQ6j2g+1BRLiS1g9teHDLGV+kj55eVTIhXLZkbRymaC6/2GwjlCXFJCZ4JtFdhUg9hLC1/wmOiMy1UJeg/P/jECQQD7zB/mK8zl0YON+42y2fLjPkBu0Cv4pzrAUsUo08Qqw9mZfYJ+AaoF5NH1WmXSqi3vbd1lauaiTAU7TwaUmbOlAkEAzBCTQK4nef4t2Rh0Fxvv3HNscXblSn1FPSse/s83ltVQKxzCCmZHuYlc1laST9UrTly8KGgg30o3qBhlrDJlNQJBAM+QTRtoL9ejBlccbopor5gz0NmIMTcgY4X2tSAasTKvj8i/dbp5lLaXEZy3kAhA0Oz2G/NmVAilsgpZ8oq8ySECQH6EaEdZsi/4XwSSHKeXModDKKDQTih4skzSR01Du/tQFXwlQEiiUSW+/EgHNH86crcK171Vkcvef9NADQlN1WECQQCM8eT8nsbElgk+DWJnZxx/wfmiF2jENJlSMdBIoRPqrefZH6Q1O03NddlchlLvIYJLm+uFWz07bLFS13OwWlDK";
		String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String token = EncryptionUtil.encrypt(appId+"," + today + ",1", appPriKey);
		System.out.println(token);

	}

}
