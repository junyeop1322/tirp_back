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
@ApiModel(value = "AttrDto : 관광지 정보", description = "관광지의 정보를 나타냄")
public class AttrDto {

    @ApiModelProperty(value = "관광지 번호")
    private int contentId;
    @ApiModelProperty(value = "컨텐트 타입 아이디")
    private int contentTypeId;
    @ApiModelProperty(value = "여행 장소")
    private String title;
    @ApiModelProperty(value = "메인주소")
    private String addr1;
    @ApiModelProperty(value = "상세주소")
    private String addr2;
    @ApiModelProperty(value = "우편번호")
    private String zipcode;
    @ApiModelProperty(value = "전화번호")
    private String tel;
    @ApiModelProperty(value = "사진1")
    private String firstImage;
    @ApiModelProperty(value = "사진2")
    private String firstImage2;
    @ApiModelProperty(value = "조회수")
    private int readcount;
    @ApiModelProperty(value = "시도 코드")
    private int sidoCode;
    @ApiModelProperty(value = "구군 코드")
    private int gugunCode;
    @ApiModelProperty(value = "위도")
    private double latitude;
    @ApiModelProperty(value = "경도")
    private double longitude;
    @ApiModelProperty(value = "M선")
    private String mlevel;

}
