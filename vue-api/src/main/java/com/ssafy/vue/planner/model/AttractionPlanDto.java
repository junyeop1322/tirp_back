package com.ssafy.vue.planner.model;

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
public class AttractionPlanDto {

    @ApiModelProperty(value = "플래너 정보 리스트")
    private List<PlannerItemDto> plannerItemDto;
    @ApiModelProperty(value = "여행지 정보")
    private List<AttractionDto> attractionDto;

}
