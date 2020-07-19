package com.fline.form.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;



public class SFTPUtils {
	private static Logger LOG = LoggerFactory
			.getLogger(SFTPUtils.class);

	/**
	 * 通过文件名称与文件内容上传到sftp服务器
	 * @param host 主机地址
	 * @param port 主机端口号
	 * @param fileName 文件名
	 * @param fileContent 文件文本内容
	 * @return
	 */
	public static String uploadFile(String host,int port, String user, String password, String fileName, String fileContent, String directory){
		Session sshSession = null;
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
	        jsch.getSession(user, host, port);
	        sshSession = jsch.getSession(user, host, port);
	        if (sshSession == null)
	        {
	        	LOG.error("Session created failed");
	        }
	        sshSession.setPassword(password);
	        Properties config = new Properties();  
            config.put("StrictHostKeyChecking", "no");  
            sshSession.setConfig(config);  
	        sshSession.connect();
	        LOG.info("SFTP Session connected.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            sftp.cd(directory);
            LOG.info("Connected to " + host);
			byte[] ktrBytes = fileContent.getBytes();
			ByteArrayInputStream bais = new ByteArrayInputStream(ktrBytes);
			sftp.put(bais,fileName);
			bais.close();
			InputStream is = sftp.get(fileName);
			int size = is.read();
			is.close();
			if (size == -1)  {
				LOG.info("上传文件失败:" + fileName);
				return "fail";
			} 
			channel.disconnect();
			LOG.info("上传文件成功:"+fileName);
		} catch (Throwable e) {
			LOG.error(e.getMessage());
			return "fail";
		} finally {
			if (sftp != null && sftp.isConnected()) {
				try {
					if (sftp.getSession() != null) {
						sftp.getSession().disconnect();
					}
					sftp.quit();
					sftp.disconnect();
					LOG.info("sftp连接释放成功");
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
			}
			if (sshSession != null) {  
	            if (sshSession.isConnected()) { 
	            	try {
						sshSession.disconnect();
					} catch (Exception e) {
						LOG.error(e.getMessage());
					}  
	            }  
	        }  

		}
		return "success";
	}

	public static String deleteFile(String host,int port, String user, String password, String fileName, String directory){
		Session sshSession = null;
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
	        jsch.getSession(user, host, port);
	        sshSession = jsch.getSession(user, host, port);
	        if (sshSession == null)
	        {
	        	LOG.error("Session created failed");
	        }
	        sshSession.setPassword(password);
	        Properties config = new Properties();  
            config.put("StrictHostKeyChecking", "no");  
            sshSession.setConfig(config);  
	        sshSession.connect();
	        LOG.info("SFTP Session connected.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            sftp.cd(directory);
            LOG.info("Connected to " + host);
            sftp.rm(fileName);
			channel.disconnect();
			LOG.info("删除文件成功:"+fileName);
		} catch (Throwable e) {
			LOG.error(e.getMessage());
			return "fail";
		} finally {
			if (sftp != null && sftp.isConnected()) {
				try {
					if (sftp.getSession() != null) {
						sftp.getSession().disconnect();
					}
					sftp.quit();
					sftp.disconnect();
					LOG.info("sftp连接释放成功");
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
			}
			if (sshSession != null) {  
	            if (sshSession.isConnected()) { 
	            	try {
						sshSession.disconnect();
					} catch (Exception e) {
						LOG.error(e.getMessage());
					}  
	            }  
	        }  

		}
		return "success";
	}
	
	
}
