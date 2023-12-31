package com.ssafy.vue.comment.controller;

import com.ssafy.vue.board.model.BoardDto;
import com.ssafy.vue.comment.model.CommentDto;
import com.ssafy.vue.comment.model.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/comment")
@Api("댓글 컨드롤러 API V1")
@Slf4j
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        super();
        this.commentService = commentService;
    }

    @ApiOperation(value = "게시글의 댓글개수", notes = "게시글마다 댓글의 개수를 나타냄", response = CommentDto.class)
    @GetMapping("/{articleNo}/count")
    public int countComment(
            @PathVariable("articleNo") @ApiParam(value = "댓글 개수를 조회할 게시판 번호", required = true) int articleno
    ) throws Exception {
        int count = commentService.countComment(articleno);
        log.info("countComment - 호출 : " + count);

        return count;
    }

    @ApiOperation(value = "댓글 작성", notes = "새로운 댓글을 입력한다.")
    @PostMapping("/{articleNo}")
    public ResponseEntity<?> writeComment(
            @RequestBody @ApiParam(value = "게시글 정보.", required = true) CommentDto commentDto
    , @PathVariable("articleNo") @ApiParam(value = "게시글 번호") int articleno) {
        log.info("writeComment commentDto - {}", commentDto);
        try {
            commentDto.setArticleNo(articleno);
            commentService.writeComment(commentDto);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "댓글 리스트 호출", notes = "게시글의 댓글을 보여준다")
    @GetMapping("/{articleno}")
    public List<CommentDto> listComment(
            @PathVariable("articleno") @ApiParam(value = "게시글 번호") int articleno
    ) throws Exception {
        log.info("listCommet 호출 : " + articleno);
        HashMap<String, Object> map = new HashMap<String, Object>();
        List<CommentDto> comm = commentService.listComment(articleno);

        return comm;
    }

    @ApiOperation(value = "댓글수정", notes = "수정할 댓글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PutMapping
    public ResponseEntity<String> modifyComment(
            @RequestBody @ApiParam(value = "수정할 댓글정보.", required = true) CommentDto commentDto) throws Exception {
        log.info("modifyComment - 호출 {}", commentDto);

        commentService.modifyComment(commentDto);
        return ResponseEntity.ok().build();
    }



    @ApiOperation(value = "댓글 삭제", notes = "댓글번호에 해당하는 댓글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @DeleteMapping("/{commentid}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentid") @ApiParam(value = "삭제할 댓글의 댓글번호.", required = true) int commentid) throws Exception {
        log.info("deleteComment - 호출");
        commentService.deleteComment(commentid);
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
