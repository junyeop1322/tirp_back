package com.ssafy.vue.comment.model.service;

import com.ssafy.vue.comment.model.CommentDto;

import java.util.List;

public interface CommentService {

    int countComment(int articleNo) throws Exception;

    void writeComment(CommentDto commentDto) throws Exception;

    void modifyComment(CommentDto commentDto) throws Exception;

    void deleteComment(int commentId) throws Exception;

    List<CommentDto> listComment(int articleNo) throws  Exception;


}
