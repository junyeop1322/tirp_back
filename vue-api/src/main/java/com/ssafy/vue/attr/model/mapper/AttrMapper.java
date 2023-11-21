package com.ssafy.vue.attr.model.mapper;

import com.ssafy.vue.attr.model.AttrDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttrMapper {

    int countAttr() throws Exception;

    int countAttraction() throws Exception;


}
