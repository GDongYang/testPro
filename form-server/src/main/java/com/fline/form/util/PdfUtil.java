package com.fline.form.util;

import com.feixian.tp.common.util.Detect;
import com.fline.form.constant.Constant;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.PdfDocumentContentParser;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IEventListener;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import org.apache.commons.io.FileUtils;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class PdfUtil {
	
	public static byte[] addImg2PdfByKeyword(byte[] pdf,byte[] img,String keyWord,float xoffset,float yoffset,float height,float width){
		float[][] position = getKeyWords(pdf,keyWord);
		for (float[] fs : position) {
			pdf = addImg2Pdf(pdf, img, fs[0]+xoffset, fs[1]+yoffset, height, width,(int)fs[2]);
		}
		return pdf;
	}
	public static byte[] addImg2PdfByKeyword(byte[] pdf,String imgBase64,String keyWord,float xoffset,float yoffset,float height,float width){
		BASE64Decoder base64Decoder = new BASE64Decoder();
		byte[] img = null;
		try {
			img = base64Decoder.decodeBuffer(imgBase64);
		} catch (IOException e) {
			e.printStackTrace();
			img = "".getBytes();
		}
		float[][] position = getKeyWords(pdf,keyWord);
		for (float[] fs : position) {
			if(fs!=null)
				pdf = addImg2Pdf(pdf, img, fs[0]+xoffset, fs[1]+yoffset, height, width,(int)fs[2]);
		}
		return pdf;
	}
	private static float[][] getKeyWords(byte[] pdf,final String keyword) {
	 	 final Vector<float[]> resu = new Vector<>();
          try {
              PdfReader pdfReader = new PdfReader(new ByteArrayInputStream(pdf));
              PdfDocument pdfDoc = new PdfDocument(pdfReader);
              int pageNum = pdfDoc.getNumberOfPages();
              PdfDocumentContentParser contentParser = new PdfDocumentContentParser(pdfDoc);
              // 下标从1开始
              int i = 1;
              for ( ; i <= pageNum; i++) {
            	  contentParser.processContent(i, new MyRenderListener(i, resu, keyword));
              }
          } catch (IOException e)
          {
          e.printStackTrace();
      }
      return resu.toArray(new float[][]{{}});
  }
	private static class MyRenderListener implements IEventListener {
		private int pageNum;
		private Vector<float[]> res;
		private String keyword;
		public MyRenderListener(int pageNum,Vector<float[]> res,String keyword) {
			this.pageNum = pageNum;
			this.res = res;
			this.keyword = keyword;
		}
		@Override
		public void eventOccurred(IEventData data, EventType type) {
			// TODO Auto-generated method stub
			if (EventType.RENDER_TEXT == type) {
				TextRenderInfo textRenderInfo = (TextRenderInfo)data;
				String text = textRenderInfo.getText();
				if (null != text && text.contains(keyword)) {
					Rectangle boundingRectange = textRenderInfo.getBaseline().getBoundingRectangle();
					List<TextRenderInfo> textList = textRenderInfo.getCharacterRenderInfos();
					int index = text.indexOf(keyword);
					if (textList.size() > index) {
						boundingRectange = textList.get(index).getBaseline().getBoundingRectangle();
					}
					float[] pageRes = new float[3];
					pageRes[0] = boundingRectange.getX();
					pageRes[1] = boundingRectange.getY();
					pageRes[2] = pageNum;
					this.res.add(pageRes);
				}
			}
		}
		@Override
		public Set<EventType> getSupportedEvents() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	public static byte[] addImg2Pdf(byte[] pdf,String imgRelaPath,float x,float y){
		return addImg2Pdf(pdf, imgRelaPath, x, y, 100, 100);
	}
	public static byte[] addImg2Pdf(byte[] pdf,byte[] img,float x,float y){
		return addImg2Pdf(pdf, img, x, y, 100, 100);
	}
	public static byte[] addImg2Pdf(byte[] pdf,String imgRelaFtlPath,float x,float y,float height,float width){
		String abPath = Constant.REAL_PATH + "/" + imgRelaFtlPath;
		InputStream is = ClassLoader.getSystemResourceAsStream(abPath);
		byte[] img = null;
		try {
			img = new byte[is.available()];
			is.read(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return addImg2Pdf(pdf, img, x, y, height, width);
	}
	public static byte[] addImg2Pdf(byte[] pdf,byte[] img,float x,float y,float height,float width){
		return addImg2Pdf(pdf, img, x, y, height, width, 1);
	}
	public static byte[] addImg2Pdf(byte[] pdf,byte[] img,float x,float y,float height,float width,int pageNum){
		if(!Detect.notEmpty(pdf)||!Detect.notEmpty(img)){
			return null;
		}
		x = x>0?x:0;
		y = y>0?y:0;
		PdfReader reader = null;
        PdfDocument pdfDoc = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            reader = new PdfReader(new ByteArrayInputStream(pdf));
            pdfDoc = new PdfDocument(reader, new PdfWriter(baos));
            PdfCanvas canvas = new PdfCanvas(pdfDoc.getPage(pageNum));
            ImageData imgData = ImageDataFactory.create(img);
            Rectangle rect = new Rectangle(x, y, width, height);
			canvas.addImage(imgData, rect, false);
        } catch (Exception e) {
            e.printStackTrace();
           
        } finally {
            try {
            	pdfDoc.close();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		
		return baos.toByteArray();
	}
	
	public static byte[] mergePdf(byte[]... pdfs){
		List<InputStream> biss = new ArrayList<>();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (Detect.notEmpty(pdfs)) {
			for (byte[] pdf : pdfs) {
				biss.add(new ByteArrayInputStream(pdf));
			}
		}
		concatPDFs(biss, baos, false);
		
		return baos.toByteArray();
	}
	
	public static void concatPDFs(List<InputStream> streamOfPDFFiles,
            OutputStream outputStream, boolean paginate) {

		PdfDocument pdf = new PdfDocument(new PdfWriter(outputStream)); 
		PdfMerger merger = new PdfMerger(pdf); 

		for (InputStream inputStream : streamOfPDFFiles) {
			PdfDocument sourcePdf = null;
			try {
				sourcePdf = new PdfDocument(new PdfReader(inputStream));
				merger.merge(sourcePdf, 1, sourcePdf.getNumberOfPages()); 
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (sourcePdf != null) {
					sourcePdf.close();
				}
			}
		}
		pdf.close(); 
    }

	public static byte[] pdfWatermark(byte[] pdf, String value) throws Exception {

		// 读取原来的pdf
		PdfReader pdfReader = new PdfReader(new ByteArrayInputStream(pdf));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PdfWriter pdfWriter = new PdfWriter(outputStream);
		PdfDocument outDocument = new PdfDocument(pdfWriter);
		PdfDocument redDocument = new PdfDocument(pdfReader);
		// 添加事件，该事件拥有添加水印
		WaterMark waterMark = new WaterMark(value);
		outDocument.addEventHandler(PdfDocumentEvent.INSERT_PAGE, waterMark);
		// 获取总页数
		int numberOfPages = redDocument.getNumberOfPages();
		for (int i = 1; i <= numberOfPages; i++) {
			PdfPage page = redDocument.getPage(i);
			// 复制每页内容添加到新的文件中
			outDocument.addPage(page.copyTo(outDocument));
		}
		outDocument.close();
		redDocument.close();
		pdfReader.close();
		return outputStream.toByteArray();
	}

	/** * 监听事件 添加水印 * * @author Administrator * */ 
	protected static class WaterMark implements IEventHandler {
		private String value;
		
		public WaterMark(String value) {
			this.value = value;
		}
		
		@Override
		public void handleEvent(Event event) {
			PdfDocumentEvent documentEvent = (PdfDocumentEvent) event;
			PdfDocument document = documentEvent.getDocument();
			PdfPage page = documentEvent.getPage();
			PdfFont pdfFont = null;
			try {
				// 将字体包拖到路径下
				pdfFont = PdfFontFactory.createFont(Constant.FONT_PATH, PdfEncodings.IDENTITY_H,false);
			} catch (IOException e) {
				e.printStackTrace();
			}
			PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), document);
			Canvas can = new Canvas(canvas, document, page.getPageSize());
			can.setFontColor(new DeviceRgb(46, 46, 46)); // 颜色
			can.setFontSize(100); // 字体大小
			can.setFont(pdfFont); // 字体的格式 即导入的字体包 //水印的内容（中英文都支持） 坐标 当前页数 最后的值为倾斜度（170）
			can.showTextAligned(new Paragraph(value), 298f, 421f, document.getPageNumber(page),TextAlignment.CENTER, VerticalAlignment.MIDDLE, 170);
			can.close();
		}
	}
	public static void main(String[] args) throws Exception{
		FileInputStream pdffis = new FileInputStream("F:/文档/杭州/test_img.pdf");
		FileInputStream imgfis = new FileInputStream("F:/文档/杭州/test.png");
		byte[] pdf = new byte[pdffis.available()];
		byte[] img = new byte[imgfis.available()];
		pdffis.read(pdf);
		pdffis.close();
		imgfis.read(img);
		imgfis.close();
        byte[] bytes = addImg2Pdf(pdf, img, 200, 200);//左下角原点
        FileUtils.writeByteArrayToFile(new File("F:/文档/杭州/test_img.pdf"), bytes);
    }
}
