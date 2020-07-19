package com.fline.form.util;

import java.util.Random;

/**
 * 随机字符串生成
 */
public class RandomStringGenerator {

	/**
	 * 获取一定长度的随机字符串 a-z0-9
	 * 
	 * @param length
	 *            指定字符串长度
	 * @return 一定长度的字符串
	 */
	public static String getRandomStringByLength(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 获取一定长度的随机数字字符串 0-9
	 * 
	 * @param length
	 *            指定数字字符串长度
	 * @return 一定长度的数字字符串
	 */
	public static String getRandomNumberByLength(int length) {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

}
