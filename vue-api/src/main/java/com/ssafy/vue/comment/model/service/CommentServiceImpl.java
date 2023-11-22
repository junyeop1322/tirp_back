package com.ssafy.vue.comment.model.service;

import com.ssafy.vue.comment.model.CommentDto;
import com.ssafy.vue.comment.model.mapper.CommentMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentMapper commentMapper;

    public CommentServiceImpl(CommentMapper commentMapper) {
        super();
        this.commentMapper = commentMapper;
    }

    @Override
    public int countComment(int articleNo) throws Exception {
        return commentMapper.countComment(articleNo);
    }

    @Override
    public void writeComment(CommentDto commentDto) throws Exception {
        commentMapper.writeComment(commentDto);
    }

    @Override
    public void modifyComment(CommentDto commentDto) throws Exception {
        commentMapper.modifyComment(commentDto);
    }

    @Override
    public void deleteComment(int commentId) throws Exception {
        commentMapper.deleteComment(commentId);
    }
}
