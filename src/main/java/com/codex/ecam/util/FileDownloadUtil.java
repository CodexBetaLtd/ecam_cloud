package com.codex.ecam.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.util.FileCopyUtils;

public class FileDownloadUtil {

	public static void flushFile(String filePath, String contentType, HttpServletResponse response ) throws IOException {
		InputStream inputStream = getInputStream(filePath, contentType, response);
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		response.getOutputStream().flush();
	}
	
	public static byte[] getByteInputStream(String filePath ) throws IOException {
		File file = getFile(filePath);  
		InputStream inputStream = new FileInputStream(file);
		return IOUtils.toByteArray(inputStream);
	}
	
	private static InputStream getInputStream(String filePath, String contentType, HttpServletResponse response) throws IOException {
		File file = getFile(filePath); 
		setResponseHeaders(contentType, file, response);
		InputStream inputStream = new FileInputStream(file);
		return inputStream;
	}

	private static void setResponseHeaders(String contentType, File file, HttpServletResponse response) {
		response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        response.setHeader("Content-Length", String.valueOf(file.length()));
	}

	public static File getFile(String filePath) {
		File file = new File(filePath); 
		
		if(!file.exists()){
            String errorMessage = "Sorry. The file you are looking for does not exist";
            throw new RuntimeException(errorMessage);
        } 
		return file;
	}
	
}
