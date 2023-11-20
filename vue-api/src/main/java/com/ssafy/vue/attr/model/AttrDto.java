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
@ApiModel(value = "AttrDto : 메인화면 정보", description = "메인페이지의 정보를 나타냄")
public class AttrDto {

    @ApiModelProperty(value = "관광지 번호")
    private int contentId;
    @ApiModelProperty(value = "구군 코드")
    private String gugunCode;
    @ApiModelProperty(value = "구군 이름")
    private String gugunName;
    @ApiModelProperty(value = "플래너 아이디")
    private int plannerId;
    @ApiModelProperty(value = "글번호")
    private int articleNo;

}
