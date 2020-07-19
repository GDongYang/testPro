package com.fline.form.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fline.form.constant.Constant;
import com.fline.form.mgmt.service.FdpClientMgmtService;

@Controller
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FdpClientMgmtService fdpClientMgmtService;

	@GetMapping(value = "/find/{itemInnerCode}/{formBusiCode}/{fileName:.+}")
	public void findFile(@PathVariable("itemInnerCode") String itemInnerCode,
			@PathVariable("formBusiCode") String formBusiCode,
			@PathVariable("fileName") String fileName,
			HttpServletResponse response) throws IOException {
	    String fileKey = Constant.FDP_FILE_KEY + "/" + itemInnerCode + "/" + formBusiCode + "/" + fileName;
		byte[] bytes = fdpClientMgmtService.readFile(fileKey);
		fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
		String mimeType = URLConnection.guessContentTypeFromName(fileName);
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + fileName + "\""));
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(bytes);
		outputStream.close();
	}

	@GetMapping("/pic")
	public void getPic(
			@RequestParam(value = "busiCode", required = false) String busiCode,
			HttpServletResponse response) throws IOException {
		String filePath = System.getProperty("user.dir") + File.separator + "download"
				+ File.separator + busiCode + ".jpg";
		byte[] bytes = null;
		try {
			FileInputStream fis = new FileInputStream(filePath);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			bytes = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String mimeType = URLConnection.guessContentTypeFromName(".jpg");
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition",
				String.format("inline; filename=\"" + busiCode + ".jpg" + "\""));
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(bytes);
		outputStream.close();
	}

}
