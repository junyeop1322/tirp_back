package com.ssafy.vue.board.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@ApiModel(value = "CommentDto : 댓글 정보", description = "댓글의 정보를 나타냄")
public class CommentBDto {

    @ApiModelProperty(value = "게시판 번호")
    private int articleNo;
    @ApiModelProperty(value = "댓글 내용")
    private String content;
    @ApiModelProperty(value = "사용자 아이디")
    private String userId;
    @ApiModelProperty(value = "부모 댓글 번호")
    private int parentId;
    @ApiModelProperty(value = "댓글 생성 날짜")
    private String date;
    @ApiModelProperty(value = "댓글 고유")
    private int commentId;

}
