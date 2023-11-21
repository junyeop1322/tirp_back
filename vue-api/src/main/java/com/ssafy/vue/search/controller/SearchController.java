package com.ssafy.vue.search.controller;

import com.ssafy.vue.search.model.SearchDto;
import com.ssafy.vue.search.model.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    private SearchService searchService;

    public SearchController(SearchService searchService) {
        super();
        this.searchService = searchService;
    }

    @ApiOperation(value = "검색 결과 중 상위 5개 구군", notes = "검색 결과 중 상위 5개의 구군을 반환함")
    @GetMapping("/rank")
    public ResponseEntity<?> topFive() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();

        List<SearchDto> search = searchService.topFive();
        if (search.size() == 0) {
            search = Collections.emptyList();
        }
        map.put("resultData", search);

        return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    }

}
