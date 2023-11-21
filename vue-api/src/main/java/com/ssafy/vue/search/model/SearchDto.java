package com.ssafy.vue.search.model;

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
@ApiModel(value = "SearchDto : 검색 정보", description = "검색 정보를 나타냄")
public class SearchDto {

    @ApiModelProperty(value = "컨텐츠타입")
    private int contentTypeId;
    @ApiModelProperty(value = "시도코드")
    private int sidoCode;
    @ApiModelProperty(value = "구군코드")
    private int gugunCode;
    @ApiModelProperty(value = "검색내용")
    private String word;
    @ApiModelProperty(value = "검색날짜")
    private String date;

}
