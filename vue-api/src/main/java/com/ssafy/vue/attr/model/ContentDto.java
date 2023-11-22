package com.ssafy.vue.attr.model;

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
@ApiModel(value = "ContentDto : 관광지 정보", description = "관광지의 정보를 나타냄")
public class ContentDto {

    @ApiModelProperty(value = "컨텐트 타입 아이디")
    private int contentTypeId;
    @ApiModelProperty(value = "여행 장소")
    private String title;
    @ApiModelProperty(value = "메인주소")
    private String addr1;
    @ApiModelProperty(value = "사진1")
    private String firstImage;
    @ApiModelProperty(value = "시도 코드")
    private int sidoCode;
    @ApiModelProperty(value = "구군 코드")
    private int gugunCode;
    @ApiModelProperty(value = "위도")
    private double latitude;
    @ApiModelProperty(value = "경도")
    private double longitude;
    @ApiModelProperty(value = "검색어")
    private String searchWord;

}
