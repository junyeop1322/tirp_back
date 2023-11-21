package com.ssafy.vue.search.model.service;

import com.ssafy.vue.search.model.SearchDto;

import java.util.List;

public interface SearchService {

    List<SearchDto> topFive() throws Exception;

}
