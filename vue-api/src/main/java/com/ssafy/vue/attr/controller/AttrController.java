package com.ssafy.vue.attr.controller;

import com.ssafy.vue.attr.model.AttrDto;
import com.ssafy.vue.attr.model.service.AttrService;
import com.ssafy.vue.board.controller.BoardController;
import com.ssafy.vue.map.model.SidoGugunCodeDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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


    @ApiOperation(value= "시도 데이터", notes ="검색 가능한 관광지 수")
    @GetMapping("/sido")
    public ResponseEntity<?> countAttr() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        int count = attrService.countAttr();
        log.info("countAttr - 호출 : " + count);
        map.put("resultData", count);

        return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    }

    @ApiOperation(value= "시도 데이터", notes ="검색 가능한 총 관광지 수")
	@GetMapping("/count")
	public ResponseEntity<HashMap<String, Object>> countAttraction() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        int count = attrService.countAttraction();
		log.info("countAttraction - 호출 :" + count);
        map.put("resultData", count);

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
