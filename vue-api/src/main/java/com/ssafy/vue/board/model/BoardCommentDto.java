package com.ssafy.vue.board.model;

import com.ssafy.vue.comment.model.CommentDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@ApiModel(value = "댓글이 있는 게시물 정보", description = "댓글이 존재하는 게시글의 정보를 나타냄")
public class BoardCommentDto {

    @ApiModelProperty(value = "글번호")
    private int articleNo;
    @ApiModelProperty(value = "작성자 아이디")
    private String userId;
    @ApiModelProperty(value = "작성자 이름")
    private String userName;
    @ApiModelProperty(value = "글제목")
    private String subject;
    @ApiModelProperty(value = "글내용")
    private String content;
    @ApiModelProperty(value = "조회수")
    private int hit;
    @ApiModelProperty(value = "작성일")
    private String date;
    @ApiModelProperty(value = "글종류")
    private int category;
    @ApiModelProperty(value = "업로드 파일정보")
    private List<FileInfoDto> fileInfos;
    @ApiModelProperty(value = "플래너 아이디")
    private String plannerId;
    @ApiModelProperty(value = "댓글 정보")
    private List<CommentBDto> commentBDto;
    
}
