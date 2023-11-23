package com.ssafy.vue.planner.model;

import com.ssafy.vue.board.model.CommentBDto;
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
public class PlanItemDto {

    @ApiModelProperty(value = "유저 아이디")
    private int userId;
    @ApiModelProperty(value = "플래너 번호")
    private int plannerId;
    @ApiModelProperty(value = "여행 시작 날짜")
    private String startDate;
    @ApiModelProperty(value = "여행 종료 날짜")
    private String endDate;
    @ApiModelProperty(value = "플래너 제목")
    private String plannerTitle;
    @ApiModelProperty(value = "여행지 정보")
    private List<AttractionDto> attractionDto;

}
