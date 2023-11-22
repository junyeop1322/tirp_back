package com.ssafy.vue.attr.model.service;

import com.ssafy.vue.attr.model.AttrDto;
import com.ssafy.vue.attr.model.ContentDto;
import com.ssafy.vue.attr.model.SidoGugunDto;

import java.util.List;

public interface AttrService {

    int countAttr() throws Exception;

    int countAttraction() throws Exception;

    List<SidoGugunDto> getSido() throws Exception;

    List<SidoGugunDto> getGugun(int sidoCode) throws Exception;

    List<AttrDto> getAttraction(int sidoCode, int gugunCode) throws Exception;

    List<ContentDto> getContentTypeId (ContentDto contentDto) throws Exception;

}