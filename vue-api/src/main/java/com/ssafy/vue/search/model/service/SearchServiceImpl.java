package com.ssafy.vue.search.model.service;

import com.ssafy.vue.search.model.SearchDto;
import com.ssafy.vue.search.model.mapper.SearchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private SearchMapper searchMapper;

    @Autowired
    public SearchServiceImpl(SearchMapper searchMapper) {
        super();
        this.searchMapper = searchMapper;
    }


    @Override
    public List<SearchDto> topFive() throws Exception {
        return searchMapper.topFive();
    }

}
