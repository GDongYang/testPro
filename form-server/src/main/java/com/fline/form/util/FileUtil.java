package com.fline.form.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.http.client.utils.DateUtils;

import com.feixian.tp.common.util.Detect;

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


	//校验文件类型是否是被允许的
	public static boolean allowUpload(String fileName){
		String postfix = fileName.substring(fileName.lastIndexOf("."));
		return ALLOW_TYPES.contains(postfix.toLowerCase());
	}

	public static String urlToBase64(String URL) throws Exception {
		ByteArrayOutputStream outPut = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		//路径处理
		String[] array=URL.split("/");
		String name=array[array.length-1];
		String domain=array[0]+"//"+array[2]+"/";
		String dir="";
		for(int i=3;i<array.length-1;i++) {
			dir+=array[i]+"/";
		}
		// 统一资源
		URL url = new URL(domain+dir+URLEncoder.encode(name,"UTF-8"));

		// 创建链接
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(10 * 1000);
		conn.setRequestProperty("Charset", "UTF-8");
		if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("文件下载失败");
		}
		InputStream inStream = conn.getInputStream();
		int len = -1;
		while ((len = inStream.read(data)) != -1) {
			outPut.write(data, 0, len);
		}
		inStream.close();
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(outPut.toByteArray());
	}

	public static void main(String[] args) {
		try {
			urlToBase64("http://59.202.39.119:32007/yztb/rest/fileManager/findFile/331419513001/e83111780efb4830b648695063a31df0/1、长汇村社区社会组织服务中心可行性报告(1).doc");
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static boolean isChineseChar(char c) {
		return String.valueOf(c).matches("[\u4e00-\u9fa5]");
	}
}
