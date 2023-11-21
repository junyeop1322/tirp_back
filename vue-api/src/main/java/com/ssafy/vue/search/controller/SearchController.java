package com.ssafy.vue.search.controller;

import com.ssafy.vue.search.model.service.SearchService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@Api("검색 컨트롤러 API V1")
@Slf4j
public class SearchController {

    private SearchService searchService;

    public SearchController(SearchService searchService) {
        super();
        this.searchService = searchService;
    }

}
