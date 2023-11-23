package com.ssafy.vue.comment.model.mapper;

import com.ssafy.vue.comment.model.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    int countComment(int articleNo) throws Exception;

    void writeComment(CommentDto commentDto) throws Exception;

    void modifyComment(CommentDto commentDto) throws Exception;

    void deleteComment(int commentId) throws Exception;

    List<CommentDto> listComment(int articleNo) throws  Exception;

}
