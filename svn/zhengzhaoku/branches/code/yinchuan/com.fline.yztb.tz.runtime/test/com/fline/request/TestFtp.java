package com.fline.request;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

public class TestFtp {

	public static void main(String[] args) {
		upload();
	}
	
	/**
     * FTP上传单个文件测试
     */
    public static void upload() {
        FTPClient ftpClient = new FTPClient();
        FileInputStream fis = null;

        try {
            ftpClient.connect("10.23.41.30");
            ftpClient.login("yztb", "gongshang");

            File srcFile = new File("E:\\图标\\服务器.png");
            fis = new FileInputStream(srcFile);
            //设置上传目录
            ftpClient.changeWorkingDirectory("/201707/国土资源/");
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("GBK");
            //设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.storeFile(new String("33-EE服务器323.png".getBytes("GBK"),"iso-8859-1"), fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            try {
            	fis.close();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
    } 

}
