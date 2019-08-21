package com.neolith.focus.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream; 
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	public static String createFile (MultipartFile fileData, String refId, String uploadFolder, String uploadLocation) throws Exception{
		String pathName = refId + "/";
		String fileName = fileData.getOriginalFilename();
		
		File dir = new File(uploadLocation + uploadFolder + pathName);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		generateFile(new File(dir.getAbsolutePath() + File.separator + fileName), fileData);
		
		return  uploadFolder + pathName + fileName;
	}
	
	public static String createFile (MultipartFile fileData, String refId, String name, String uploadFolder, String uploadLocation) throws Exception{
		String pathName = refId + "/"; 
		String fileName = fileData.getOriginalFilename();

		File dir = new File(uploadLocation + uploadFolder + pathName);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		generateFile(new File(dir.getAbsolutePath() + File.separator + fileName), fileData);

		return  uploadFolder + pathName + fileName;
	}

	private static void generateFile (File serverFile, MultipartFile file) throws Exception {
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile, false));
		byte[] bytes = file.getBytes(); 
		stream.write(bytes);
		stream.close();
	}

}
