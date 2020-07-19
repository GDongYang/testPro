/**
 * Copyright© 2003-2013 浙江汇信科技有限公司, All Rights Reserved <br/>
 * 描述: 系统工具 <br/>
 * @author 朱勇
 * @date 2013-12-6
 * @version 1.0
 */
package com.fline.request;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

import it.sauronsoftware.base64.Base64;

/**
 * 描述: DES 操作<br>
 * 
 * @author 朱勇
 * @date 2014-10-21
 */
public class HxDesUtil {
	// 密钥
	private static final String KEY = "hysbzm@qazxswed@11223344";

	// 密钥类型
	private static final String KEY_TYPE = "0";

	// 向量值
	private static final String IV = "ZJHUIXIN";

	/**
	 * 3DES加密方法
	 * 
	 * @author zhuyong
	 * @param msg
	 * @return
	 */
	public static String encDES(String msg) {
		return TripleDesEncrypt(msg, KEY, KEY_TYPE, IV);
	}

	/**
	 * 3DES解密方法
	 * 
	 * @param msg
	 * @return
	 */
	public static String decDES(String msg) {
		if (StringUtils.isBlank(msg))
			return "";
		return TripleDesDecrypt(msg, KEY, KEY_TYPE, IV);
	}

	/**
	 * 描述: 三重 DES 加密操作
	 * 
	 * @author 朱勇
	 * @date 2014-8-27
	 * @param strMing
	 *            明文
	 * @param strKey
	 *            参照 私有 getSecreKey() 方法说明
	 * @param strKeyType
	 *            参照私有 getSecreKey() 方法说明
	 * @param strIv
	 *            向量值（8位）（向量值不为空的话，采用CBC模式）
	 * @return
	 */
	private static String TripleDesEncrypt(String strMing, String strKey, String strKeyType, String strIvValue) {
		try {
			// 获取秘钥
			SecretKey deskey = getSecreKey(strKeyType, strKey, "DESede");

			// 加、解密处理
			Cipher cipher = null;

			// strIv 不为空，使用向量
			if (strIvValue != null && strIvValue.length() > 0) {
				// 含向量需要采用CBC模式
				cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
				// 定义一个向量变量
				AlgorithmParameterSpec iv = new IvParameterSpec(strIvValue.getBytes());
				// 设置工作模式为加密模式，给出密钥和向量
				cipher.init(Cipher.ENCRYPT_MODE, deskey, iv);
			} else {
				// 不含向量采用ECB模式
				cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
				// 加密模式
				cipher.init(Cipher.ENCRYPT_MODE, deskey);
			}

			// 转成BASE64
			byte[] byteFina = cipher.doFinal(strMing.getBytes("UTF8"));
			String strMi = new String(Base64.encode(byteFina));
			return strMi.replaceAll("\r|\n", "");

		} catch (Exception e) {
			return e.toString();
		}
	}

	/**
	 * 描述: 三重 DES 解密操作
	 * 
	 * @author 朱勇
	 * @date 2014-10-21
	 * @param strMi
	 *            密文
	 * @param strKey
	 *            参照 私有 getSecreKey() 方法说明
	 * @param strKeyType
	 *            参照私有 getSecreKey() 方法说明
	 * @param strIv
	 *            向量值（8位）（向量值不为空的话，采用CBC模式）
	 * @return
	 */
	private static String TripleDesDecrypt(String strMi, String strKey, String strKeyType, String strIvValue) {
		try {
			// 获取秘钥
			SecretKey deskey = getSecreKey(strKeyType, strKey, "DESede");
			// 加、解密处理
			Cipher cipher = null;
			// strIv 不为空，使用向量
			if (strIvValue != null && strIvValue.length() > 0) {
				// 含向量需要采用CBC模式
				cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
				// 定义一个向量变量
				AlgorithmParameterSpec iv = new IvParameterSpec(strIvValue.getBytes());
				// 设置工作模式为加密模式，给出密钥和向量
				cipher.init(Cipher.DECRYPT_MODE, deskey, iv);
			} else {
				// 不含向量采用ECB模式
				cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
				// 加密模式
				cipher.init(Cipher.DECRYPT_MODE, deskey);
			}
			// 转成BASE64
			byte[] byteFina = cipher.doFinal(Base64.decode(strMi.getBytes()));
			String strMing = new String(byteFina, "UTF-8");
			return strMing.replaceAll("\r|\n", "");
		} catch (Exception e) {
			return e.toString();
		}
	}

	/**
	 * 描述: 通过密码类型,与密码获取 SecretKey 对象
	 * 
	 * @author 朱勇
	 * @date 2014-10-21
	 * @param strKeyType
	 *            秘钥类型 默认 2 0: 通过 SecretKeySpec 产生 （DES 加密"秘钥" 只能支持 8 位；3DES
	 *            加密"秘钥" 只能支持 24位） 1: 通过 SecretKeyFactory 产生 （DES 加密"秘钥" 支持 8
	 *            位及以上，比如9位；3DES 加密"秘钥" 支持 24 位及以上，比如9位） 2: 通过 KeyGenerator 产生
	 *            （DES、3DES 加密"秘钥" 支持任意位数，可为空）
	 * @param strKey
	 *            秘钥
	 * @param algorithm
	 *            算法（DES/DESede/AES）等 默认 DES
	 * @return
	 */
	private static SecretKey getSecreKey(String strKeyType, String strKey, String algorithm) {

		SecretKey deskey = null;
		try {
			// 算法默认DES
			if (algorithm == null || algorithm.equals("")) {
				algorithm = "DES";
			}
			// key 类型默认 原始密钥
			if (strKeyType == null || strKeyType.equals("")) {
				strKeyType = "2";
			}

			// 通过 SecretKeySpec 产生秘钥
			if ("0".equals(strKeyType)) {
				deskey = new SecretKeySpec(strKey.getBytes(), algorithm);
			}
			// 通过 SecretKeyFactory 产生秘钥
			else if ("1".equals(strKeyType)) {
				SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
				if (algorithm.equals("DES")) {
					// DES 秘钥生成器（使用SecretKeyFactory DES加密，要使用 DESKeySpec ）
					DESKeySpec desKeySpec = new DESKeySpec(strKey.getBytes());
					// 生成秘钥
					deskey = secretKeyFactory.generateSecret(desKeySpec);
				} else if (algorithm.equals("DESede")) {
					// 3DES 秘钥生成器（使用SecretKeyFactory 3DES加密，要使用 DESedeKeySpec ）
					DESedeKeySpec desSedeKeySpec = new DESedeKeySpec(strKey.getBytes());
					// 生成秘钥
					deskey = secretKeyFactory.generateSecret(desSedeKeySpec);
				}
			}
			// 通过 keyGenerator 产生秘钥
			else if ("2".equals(strKeyType)) {
				KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
				byte[] keyByte = strKey.getBytes();
				keyGenerator.init(new SecureRandom(keyByte));
				deskey = keyGenerator.generateKey();
			}
		} catch (Exception er) {
			er.printStackTrace();
		}
		return deskey;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		//加密秘钥
//		String pass = "hysbzm@qazxswed@11223344";
//		//向量值
//		String ivStr = "ZJHUIXIN";
//		//明文
//		String mingwen = "cccccccccccccccccc333333333333ccccccccccccccccccccc9999999999999999999";
//		//密文
//		String miwen = HxDesUtil.TripleDesEncrypt(mingwen, pass, "0", ivStr);
//		System.out.println("3DES 加密：" + miwen);
//		miwen = miwen.replaceAll("/", "#");
//		System.out.println("3DES 加密 替换后：" + miwen);
//		System.out.println("after urlencode:" + URLEncoder.encode(miwen, "utf-8"));
//		System.out.println("3DES 解密前 替换");
//		miwen = miwen.replaceAll("#", "/");
//		System.out.println("3DES 解密：" + HxDesUtil.TripleDesDecrypt(miwen, pass, "0", ivStr));

		String enStr = encDES("cccccccccccccccccc333333333333ccccccccccccccccccccc9999999999999999999");
		System.out.println("加密：" + enStr);
		System.out.println("解密:" + decDES(enStr));
		
	}

}
