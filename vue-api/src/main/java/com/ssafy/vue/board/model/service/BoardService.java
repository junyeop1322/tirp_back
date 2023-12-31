package com.ssafy.vue.board.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.vue.board.model.BoardCommentDto;
import com.ssafy.vue.board.model.BoardDto;
import com.ssafy.vue.board.model.BoardListDto;
import com.ssafy.vue.board.model.CommentBDto;
import com.ssafy.vue.comment.model.CommentDto;

public interface BoardService {

	void writeArticle(BoardDto boardDto) throws Exception;
	BoardListDto listArticle(Map<String, String> map) throws Exception;
//	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	BoardDto getArticle(int articleNo) throws Exception;

	BoardCommentDto getArticleComment(int articleNo) throws Exception;

	void updateHit(int articleNo) throws Exception;
	
	void modifyArticle(BoardDto boardDto) throws Exception;

	void deleteArticle(int articleNo) throws Exception;

	int countArticle() throws Exception;

	List<CommentBDto> listComment(int articleNo) throws Exception;

	int countComment(int articleNo) throws Exception;
	
}
