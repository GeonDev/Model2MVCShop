package com.model2.mvc.service.fileImages.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.service.domain.FileImages;
import com.model2.mvc.service.fileImages.FileImagesDao;
import com.model2.mvc.service.fileImages.FileImagesService;

@Service("fileImagesServiceImpl")
public class FileImagesServiceImpl implements FileImagesService {

	@Autowired
	@Qualifier("fileImagesDaoImpl")
	private FileImagesDao fileImagesDao;
	
	@Override
	public List<FileImages> getFileList(int prodno) throws Exception {
		// TODO Auto-generated method stub
		return fileImagesDao.getFileList(prodno);
	}

	@Override
	public void addFileImage(FileImages file) throws Exception {
		fileImagesDao.addFileImage(file);

	}

	@Override
	public void deleteFileImage(FileImages file) throws Exception {
		fileImagesDao.deleteFileImage(file);
	}

}
