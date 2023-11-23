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
@ApiModel(value = "AttrDto : 관광지 정보", description = "관광지의 정보를 나타냄")
public class AttractionDto {

    @ApiModelProperty(value = "여행 장소")
    private String title;
    @ApiModelProperty(value = "메인주소")
    private String addr1;
    @ApiModelProperty(value = "위도")
    private double latitude;
    @ApiModelProperty(value = "경도")
    private double longitude;

}
