package com.fline.form.util;

import com.swetake.util.Qrcode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class QRCoderUtil {
	public static byte[] encoderQRCoder(String content) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		encoderQRCoder(content,baos);
		return baos.toByteArray();
		
	}
	public static void encoderQRCoder(String content, File file) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(fos!=null)
			encoderQRCoder(content, fos);
	}
	public static void encoderQRCoder(String content, OutputStream os) {
		try {
			Qrcode qrcodeHandler = new Qrcode();
			// 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
			qrcodeHandler.setQrcodeErrorCorrect('L');
			qrcodeHandler.setQrcodeEncodeMode('B');
			int size = 4;// 20180108 szr 原来是16
			qrcodeHandler.setQrcodeVersion(size);
			System.out.println(content);
			byte[] contentBytes = content.getBytes("utf-8");
			// 图片尺寸
			int imgSize = 67 + 12 * (size - 1);
			BufferedImage bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, imgSize, imgSize);
			// 设定图像颜色> BLACK
			gs.setColor(Color.BLACK);

			// 设置偏移量 不设置可能导致解析出错
			int pixoff = 2;

			// 输出内容> 二维码
			if (contentBytes.length > 0 && contentBytes.length < 800) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} else {
				System.err.println("QRCode content bytes length = " + contentBytes.length + " not in [ 0,320 ]. ");
			}
			gs.dispose();
			bufImg.flush();
			// 生成二维码QRCode图片
			ImageIO.write(bufImg, "png", os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String content = "{certCode=sx_zfgjj_cer_001, cerNo=330621197903296336}";
		File file = new File("C:/szr/test.png");
		encoderQRCoder(content, file);
	}
}
