package com.ssafy.vue.attr.controller;

import com.ssafy.vue.attr.model.AttrDto;
import com.ssafy.vue.attr.model.ContentDto;
import com.ssafy.vue.attr.model.SidoGugunDto;
import com.ssafy.vue.attr.model.service.AttrService;
import com.ssafy.vue.board.controller.BoardController;
import com.ssafy.vue.map.model.SidoGugunCodeDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.vue.map.model.SidoGugunCodeDto;

@RestController
@RequestMapping("/attr")
@Api("메인 페이지 컨트롤러 API V1")
@Slf4j
public class AttrController {

    private AttrService attrService;

    public AttrController(AttrService attrService) {
        super();
        this.attrService = attrService;
    }


//    @ApiOperation(value= "시도 데이터", notes ="검색 가능한 관광지 수")
//    @GetMapping("/")
//    public ResponseEntity<?> countAttr() throws Exception {
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        int count = attrService.countAttr();
//        log.info("countAttr - 호출 : " + count);
//        map.put("resultData", count);
//
//        return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
//    }

    @ApiOperation(value = "시도 데이터", notes = "검색 가능한 총 관광지 수")
    @GetMapping("/count")
    public ResponseEntity<HashMap<String, Object>> countAttraction() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        int count = attrService.countAttraction();
        log.info("countAttraction - 호출 :" + count);
        map.put("resultData", count);

        return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "시도 목록 보기", notes = "전체 시도 코드와 이름을 반환함", response = SidoGugunDto.class)
    @GetMapping("/sido")
    public ResponseEntity<?> getSido() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        log.info("getSido 호출");
        List<SidoGugunDto> sido = attrService.getSido();

        map.put("resultData", sido);

        return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "구군 목록 보기", notes = "시도 코드에 맞는 구군코드와 구군이름을 반환함", response = SidoGugunDto.class)
    @GetMapping("/{sidocode}/gugun")
    public ResponseEntity<?> getGugun(
            @PathVariable("sidocode") @ApiParam(value = "얻어올 시도 코드", required = true) int sidocode
    ) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        log.info("getGugun 호출 : " + sidocode);
        List<SidoGugunDto> gugun = attrService.getGugun(sidocode);

        map.put("resultData", gugun);

        return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    }

    public ResponseEntity<?> getAttraction(
            @PathVariable("sidocode") @ApiParam(value = "얻이올 시도 코드", required = true) int sidocode,
            @PathVariable("guguncode") @ApiParam(value = "얻이올 구군 코드", required = true) int guguncode
    ) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        log.info("getAttraction 호출 시도코드 : " + sidocode + ", 구군코드 : " + guguncode);
        List<AttrDto> attr = attrService.getAttraction(sidocode, guguncode);
        map.put("resultData", attr);

        return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "관광종류 보기", notes = "시도 코드와 구군코드에 맞는 관광종류 반환함", response = SidoGugunDto.class)
    @PostMapping("/search")
    public ResponseEntity<?> getContentTypeId(
            @RequestBody ContentDto contentDto
    ) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        log.info("getContentTypeId 호출 ");
        List<ContentDto> attr = attrService.getContentTypeId(contentDto);

        if (attr.size() == 0) {
            attr = Collections.emptyList();
        }

        map.put("resultData", attr);

        return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    }


//    @ApiOperation(value= "시도 코드에 따른 구군 데이터", notes ="시도 코드에 따른 구군 데이터를 반환함", response = SidoGugunCodeDto.class)
//    @GetMapping("/gugun/{sidoCode}")
//    public ResponseEntity<SidoGugunCodeDto> getGugun() {
//
//
//
//        return new ResponseEntity<SidoGugunCodeDto>();
//    }

}
