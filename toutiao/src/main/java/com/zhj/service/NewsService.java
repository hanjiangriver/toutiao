package com.zhj.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zhj.ToutiaoUtil.ToutiaoUtil;
import com.zhj.dao.NewsDAO;
import com.zhj.model.News;



@Service
public class NewsService {
  @Autowired
  private NewsDAO newsDAO;
  public List<News> getLastestNews(int userId,int offset,int limit){
	  return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
  }
  public String saveImage(MultipartFile file)throws IOException{
	int dotPos=  file.getOriginalFilename().lastIndexOf(".");
	if(dotPos<0){
		return null;
	}
	String fileExt=file.getOriginalFilename().substring(dotPos+1).toLowerCase();
	if(!ToutiaoUtil.isFileAllowed(fileExt)){
		return null;
	}
	String filename=UUID.randomUUID().toString().replaceAll("-", "")+"."+fileExt;
	Files.copy(file.getInputStream(), new File(ToutiaoUtil.IMAGE_DIR+filename).toPath(),StandardCopyOption.REPLACE_EXISTING);
    return ToutiaoUtil.TOUTIAO_DOMAIN+"image?name="+filename;
  }
  public int addNews(News news){
	  newsDAO.addNews(news);
	  return news.getId();
  }
  
  public News getById(int id){
	  return  newsDAO.getById(id);
	  
  }
  public int updateCommentCount(int id,int count){
	  return newsDAO.updateCommentCount(id,count);
	   
  }
  public int updateLikeCount(int id,int count){
	  return newsDAO.updateLikeCount(id,count);   
  }
}
