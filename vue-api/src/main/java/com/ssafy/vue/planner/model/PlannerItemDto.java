package com.ssafy.vue.planner.model;

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
@ApiModel(value = "PlannerItemDto : 플래너 아이템 정보", description = "플래너 아이템의 상세 정보를 나타낸다")
public class PlannerItemDto {

    @ApiModelProperty(value = "플래너 콘텐트 아이디")
    private int contentId;
    @ApiModelProperty(value = "플래너 아이디")
    private int plannerId;
    @ApiModelProperty(value = "작성 날짜")
    private String date;
    @ApiModelProperty(value = "여행지 정보")
    private List<AttractionDto> attractionDto;

}
