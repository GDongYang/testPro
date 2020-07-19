package com.fline.form.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.ImageIcon;

import org.springframework.web.context.ContextLoader;

public class PictureUtil {
	
    private static int color_range = 210;  
    
    public static byte[] change(byte[] data) {
    	String realpath = "";
		if(ContextLoader.getCurrentWebApplicationContext() != null){
			realpath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		}else{
			realpath = System.getProperty("user.dir");
		}
		String path = realpath + File.separator + "photo" + File.separator + UUID.randomUUID();
    	byte2image(data, path + ".jpg");
    	convert(path + ".jpg");
    	return image2byte(path + ".png");
    }
   
	private static void byte2image(byte[] data,String path){
	    if(data.length<3||path.equals("")) return;
	    try{
	    FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
	    imageOutput.write(data, 0, data.length);
	    imageOutput.close();
	    System.out.println("Make Picture success,Please find image in " + path);
	    } catch(Exception ex) {
	      System.out.println("Exception: " + ex);
	      ex.printStackTrace();
	    }
	  }
	
    private static void convert(String path) {  
        try {  
            BufferedImage image = ImageIO.read(new File(path));  
            ImageIcon imageIcon = new ImageIcon(image);  
            BufferedImage bufferedImage = new BufferedImage(  
                    imageIcon.getIconWidth(), imageIcon.getIconHeight(),  
                    BufferedImage.TYPE_4BYTE_ABGR);  
            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();  
            g2D.drawImage(imageIcon.getImage(), 0, 0,  
                    imageIcon.getImageObserver());  
            int alpha = 0;  
            for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage  
                    .getHeight(); j1++) {  
                for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage  
                        .getWidth(); j2++) {  
                    int rgb = bufferedImage.getRGB(j2, j1);  
                    if (colorInRange(rgb))  
                        alpha = 0;  
                    else  
                        alpha = 255;  
                    rgb = (alpha << 24) | (rgb & 0x00ffffff);  
                    bufferedImage.setRGB(j2, j1, rgb);  
                }  
            }  
            g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());  
            // 生成图片为PNG  
            String outFile = path.substring(0, path.lastIndexOf("."));  
            ImageIO.write(bufferedImage, "png", new File(outFile + ".png"));  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
   
    private static boolean colorInRange(int color) {  
        int red = (color & 0xff0000) >> 16;  
        int green = (color & 0x00ff00) >> 8;  
        int blue = (color & 0x0000ff);  
        if (red >= color_range && green >= color_range && blue >= color_range)  
            return true;  
        return false;  
    } 
    
    private static byte[] image2byte(String path){
        byte[] data = null;
        FileImageInputStream input = null;
        try {
          input = new FileImageInputStream(new File(path));
          ByteArrayOutputStream output = new ByteArrayOutputStream();
          byte[] buf = new byte[1024];
          int numBytesRead = 0;
          while ((numBytesRead = input.read(buf)) != -1) {
          output.write(buf, 0, numBytesRead);
          }
          data = output.toByteArray();
          output.close();
          input.close();
        }
        catch (FileNotFoundException ex1) {
          ex1.printStackTrace();
        }
        catch (IOException ex1) {
          ex1.printStackTrace();
        }
        return data;
      }


}
