package com.ssafy.vue.comment.model.service;

import com.ssafy.vue.comment.model.CommentDto;

public interface CommentService {

    int countComment(int articleNo) throws Exception;

    void writeComment(CommentDto commentDto) throws Exception;

    void modifyComment(CommentDto commentDto) throws Exception;

    void deleteComment(int commentId) throws Exception;

}
