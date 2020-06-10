package com.model2.mvc.service.fileImages.Impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.service.domain.Discount;
import com.model2.mvc.service.domain.FileImages;
import com.model2.mvc.service.fileImages.FileImagesDao;

@Repository("fileImagesDaoImpl")
public class FileImagesDaoImpl implements FileImagesDao {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	
	@Override
	public List<FileImages> getFileList(int prodno) throws Exception {
		List<FileImages> list = sqlSession.selectList("FileImagesMapper.getImagesList",prodno);
		return list;
	}

	@Override
	public void addFileImage(FileImages file) throws Exception {		
		sqlSession.insert("FileImagesMapper.addImages", file);

	}

	@Override
	public void deleteFileImage(FileImages file) throws Exception {
		sqlSession.delete("FileImagesMapper.deleteImages", file);
	}

}
