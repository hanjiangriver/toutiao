package com.zhj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.zhj.model.Comment;
import com.zhj.model.Message;

@Mapper
public interface MessageDAO {

	String TABLE_NAME = " message ";
	String INSERT_FIELDS = " content,from_id, to_id,created_date,has_read,conversation_id  ";
	String SELECT_FIELDS = " id , "+INSERT_FIELDS;

	@Insert({ "insert into", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{content},#{fromId},#{toId},#{createdDate},#{hasRead},#{conversationId})" })
	int addMessage(Message message);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,"  where conversation_id=#{conversationId} order by id desc limit #{offset},#{limit}" })
	List<Message>getConversationDetail(@Param("conversationId")String conversationId,
			                           @Param("offset")int offset,@Param("limit")int limit );
	

    @Select({"select ", INSERT_FIELDS, " ,count(id) as id from ( select * from ", TABLE_NAME, " where from_id=#{userId} or to_id=#{userId} order by id desc) tt group by conversation_id order by id desc limit #{offset},#{limit}"})
    List<Message> getConversationList(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);
    
    
    @Select({"select count(id) from ",TABLE_NAME," where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConversationUnReadCount(@Param("userId") int userId,@Param("conversationId") String conversationId);
}