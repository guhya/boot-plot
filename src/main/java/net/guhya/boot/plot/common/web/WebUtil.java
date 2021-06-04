package net.guhya.boot.plot.common.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import net.guhya.boot.plot.common.web.request.Box;


public class WebUtil {
	
	public static List<Map<String, MultipartFile>> getFilesFromRequest(HttpServletRequest request){
		List<Map<String, MultipartFile>> fileList = new ArrayList<Map<String,MultipartFile>>();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> it = multipartRequest.getFileNames();
		while(it.hasNext()){
			String key = it.next();
			if(!multipartRequest.getFile(key).isEmpty()){
				Map<String, MultipartFile> file = new HashMap<String, MultipartFile>();
				file.put(key, multipartRequest.getFile(key));			
				fileList.add(file);
			}
		}
		
		return fileList;
	}
	
	public static String[] getDeletedFilesFromRequest(HttpServletRequest request){
		String rawDeletedFiles;
		String[] deletedFiles = {};
		if(request.getParameterValues(AbstractRestController.REQ_DELETED_FILES) != null){
			rawDeletedFiles = request.getParameter(AbstractRestController.REQ_DELETED_FILES);
			deletedFiles = rawDeletedFiles.split("\\,");			
		}
		
		return deletedFiles;
	}
	
	public static String[] getDeletedFiles(Box paramBox){
		String rawDeletedFiles;
		String[] deletedFiles = {};
		if(paramBox.getString(AbstractRestController.REQ_DELETED_FILES) != null){
			rawDeletedFiles = paramBox.getString(AbstractRestController.REQ_DELETED_FILES);
			deletedFiles = rawDeletedFiles.split("\\,");			
		}
		
		return deletedFiles;
	}
	
	public static String getFileExtension(String fileName){
		String ext = "";
		if(fileName.indexOf(".") == -1){
			ext = "";
		}else{
			String[] arrFile = fileName.split("\\.");
			ext = arrFile[arrFile.length-1];
		}

		return ext;
	}
	
	public static boolean validateMime(String mimeType, String[] allowedMimeType){
		for(String allowed : allowedMimeType){
			if(mimeType.equalsIgnoreCase(allowed)){
				return true;
			}
		}		
		return false;
	}
	

}
