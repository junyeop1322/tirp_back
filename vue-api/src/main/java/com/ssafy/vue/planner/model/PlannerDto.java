package com.ssafy.vue.planner.model;

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
@ApiModel(value = "PlannerDto : 여행 계획 정보", description = "여행 계획 정보를 나타냄")
public class PlannerDto {

    @ApiModelProperty(value = "플래너 번호")
    private int plannerId;
    @ApiModelProperty(value = "유저 아이디")
    private String userId;
    @ApiModelProperty(value = "게시판 번호")
    private int articleNo;
    @ApiModelProperty(value = "지도 링크")
    private String mapLink;
    @ApiModelProperty(value = "플래너 제목")
    private String plannerTitle;
    @ApiModelProperty(value = "여행 시작 날짜")
    private String startDate;
    @ApiModelProperty(value = "여행 종료 날짜")
    private String endDate;
    @ApiModelProperty(value = "계획 생성 날짜")
    private String date;
    @ApiModelProperty(value = "검색어")
    private String searchWord;

}
