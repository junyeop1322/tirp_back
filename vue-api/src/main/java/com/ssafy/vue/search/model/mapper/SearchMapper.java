package com.ssafy.vue.search.model.mapper;

import com.ssafy.vue.search.model.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchMapper {

    List<SearchDto> topFive() throws Exception;

}
