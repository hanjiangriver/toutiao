package com.zhj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zhj.model.Comment;
import com.zhj.model.User;


@Mapper
public interface CommentDAO {

	String TABLE_NAME = " comment ";
	String INSERT_FIELDS = " content,entity_id, entity_type,created_date,user_id,status  ";
	String SELECT_FIELDS = " id , "+INSERT_FIELDS;

	@Insert({ "insert into", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{content},#{entityId},#{entityType},#{createdDate},#{userId},#{status})" })
	int addComment(Comment comment);
	
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where entity_id=#{entityId} and entity_type=#{entityType} order by id desc"})
	List<Comment>selectByEntity(@Param("entityId") int entityId,@Param("entityType") String entityType);
	
	
	@Select({"select count(id) from ",TABLE_NAME," where entity_id=#{entityId} and entity_type=#{entityType} order by id desc"})
	int getCommentCount(@Param("entityId") int entityId,@Param("entityType") String entityType);
	
	
}
