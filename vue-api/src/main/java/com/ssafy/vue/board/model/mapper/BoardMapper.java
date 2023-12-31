package com.ssafy.vue.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.vue.board.model.BoardCommentDto;
import com.ssafy.vue.board.model.CommentBDto;
import com.ssafy.vue.comment.model.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.board.model.BoardDto;
import com.ssafy.vue.board.model.FileInfoDto;

@Mapper
public interface BoardMapper {

	void writeArticle(BoardDto boardDto) throws SQLException;

	void registerFile(BoardDto boardDto) throws Exception;

	List<BoardDto> listArticle(Map<String, Object> param) throws SQLException;

	int getTotalArticleCount(Map<String, Object> param) throws SQLException;

	BoardDto getArticle(int articleNo) throws SQLException;

	BoardCommentDto getArticleComment(int articleNo) throws Exception;

	void updateHit(int articleNo) throws SQLException;

	void modifyArticle(BoardDto boardDto) throws SQLException;

	void deleteFile(int articleNo) throws Exception;

	void deleteArticle(int articleNo) throws SQLException;

	List<FileInfoDto> fileInfoList(int articleNo) throws Exception;

	int countArticle() throws Exception;

	List<CommentBDto> listComment(int articleNo) throws Exception;

	int countComment(int articleNo) throws Exception;

	
}
