package com.fline.form.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.http.client.utils.DateUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class FileUtil {
	/**
	 * 将文件转成base64 字符串
	 * 
	 * @param path文件路径
	 * @return *
	 * @throws Exception
	 */

	public static String encodeBase64File(String path) throws Exception {
		File file = new File(path);
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		return new BASE64Encoder().encode(buffer);

	}

	/**
	 * 将base64字符解码保存文件
	 * 
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */

	public static void decoderBase64File(String base64Code, String targetPath)
			throws Exception {
		byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(buffer);
		out.close();

	}

	/**
	 * 将base64字符保存文本文件
	 * 
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */

	public static void toFile(String base64Code, String targetPath)
			throws Exception {

		byte[] buffer = base64Code.getBytes();
		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(buffer);
		out.close();
	}

	public static final String FILE_PATTERN = "yyyyMMddHHmmss";
	public static final List<String> ALLOW_TYPES = Arrays.asList(
            "jpg","jpeg","png","gif"
    );
	//文件重命名
    public static String rename(String fileName){
        int i = fileName.lastIndexOf(".");
        String str = fileName.substring(i);
        String prefix = DateUtils.formatDate(new Date(), FileUtil.FILE_PATTERN);
        return prefix+RandomStringGenerator.getRandomNumberByLength(6)+str;
    }
    //校验文件类型是否是被允许的
    public static boolean allowUpload(String fileName){
    	String postfix = fileName.substring(fileName.lastIndexOf("."));
        return ALLOW_TYPES.contains(postfix.toLowerCase());
    }
	
	public static void main(String[] args) {
		try {
			String base64Code = encodeBase64File("D:/0101-2011-qqqq.tif");
			System.out.println(base64Code);
			decoderBase64File(base64Code, "D:/2.tif");
			toFile(base64Code, "D:\\three.txt");
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
