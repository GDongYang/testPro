package com.fline.form.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFbox {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        PDF2JPG("c:/szr/3306.pdf", "c:/szr/3306.jpg");
    }

    /**
     * PDF转JPG
     *
     * @param inUrl PDFURL
     *
     * @param outUrl JPGURL
     *
     */
    public static void PDF2JPG(String inUrl, String outUrl) {
        File file = new File(inUrl);
        PDDocument doc = null;
        try {
            doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
			/*int pageCount = doc.getNumberOfPages();
			for (int i = 0; i < pageCount; i++) {
				// BufferedImage image = renderer.renderImageWithDPI(i, 296);
				BufferedImage image = renderer.renderImage(i, 2.0f);
				ImageIO.write(image, "PNG", new File(outputPreviewFile));
			}*/
            BufferedImage image = renderer.renderImage(0, 1.6f);
            ImageIO.write(image, "jpg", new File(outUrl));
        } catch (InvalidPasswordException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (doc != null) {
                    doc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte [] PDF2JPG( byte [] bytes) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PDDocument doc = null;
        try {
            doc = PDDocument.load(bytes);
            PDFRenderer renderer = new PDFRenderer(doc);
            BufferedImage image = renderer.renderImage(0, 1.6f);
            ImageIO.write(image, "jpg", out);
        } catch (InvalidPasswordException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (doc != null) {
                    doc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out.toByteArray();
    }

    public static byte[] pdf2Jpg(byte[] pdf) {
        PDDocument doc = null;
        try {
            doc = PDDocument.load(pdf);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            List<ByteArrayOutputStream> outs = new ArrayList<>();
            for (int i = 0; i < pageCount; i++) {
                BufferedImage image = renderer.renderImage(i, 2.0f);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", outputStream);
                outs.add(outputStream);
            }
            return joinPic(outs);
        } catch (InvalidPasswordException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (doc != null) {
                    doc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将多张图片合并成一张图片
     */
    private static byte[] joinPic(List<ByteArrayOutputStream> outs) {
        try {
            Integer allWidth = 0;	// 图片总宽度
            Integer allHeight = 0;	// 图片总高度
            List<BufferedImage> imgs = new ArrayList<>();
            for(int i=0; i<outs.size(); i++){
                imgs.add(ImageIO.read(new ByteArrayInputStream(outs.get(i).toByteArray())));
                //竖向
                if (i==0) {
                    allWidth = imgs.get(0).getWidth();
                }
                allHeight += imgs.get(i).getHeight();
            }
            BufferedImage combined = new BufferedImage(allWidth, allHeight, BufferedImage.TYPE_INT_RGB);
            Graphics g = combined.getGraphics();
            // 竖向合成
            Integer height = 0;
            for(int i=0; i< imgs.size(); i++){
                g.drawImage(imgs.get(i), 0, height, null);
                height +=  imgs.get(i).getHeight();
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(combined, "jpg", outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            System.out.println("合成失败");
            e.printStackTrace();
        }
        return null;
    }

}
