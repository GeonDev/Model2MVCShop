package com.model2.mvc.service.fileImages;

import java.util.List;

import com.model2.mvc.service.domain.FileImages;

public interface FileImagesDao {

	public List<String> getFileList(int prodno) throws Exception;
	
	public void addFileImage(FileImages file ) throws Exception;
	
	public void deleteFileImage(FileImages file ) throws Exception;
}
