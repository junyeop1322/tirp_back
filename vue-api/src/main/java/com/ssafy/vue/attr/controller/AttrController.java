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

//    private AttrService attrService;
//
//    public AttrController(AttrService attrService) {
//        super();
//        this.attrService = attrService;
//    }


//    @ApiOperation(value= "시도 데이터", notes ="검색 가능한 관광지 수")
//    @GetMapping("/attr/sido")
//    public ResponseEntity<Map<String, Object>> getSido() {
////        log.debug("sido data {}");
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        HttpStatus status = HttpStatus.ACCEPTED;
//
//
//
//        return new ResponseEntity<Map<String, Object>>(resultMap, status);
//    }

//    @ApiOperation(value = "게시글 총 개수", notes = "게시글의 총 개수를 나타냄", response = AttrDto.class)
//	@GetMapping("/count")
//	public ResponseEntity<Integer> countArticle() throws Exception {
//		log.info("countArtice - 호출");
//		return new ResponseEntity<Integer>(attrService.countArticle(), HttpStatus.OK);
//	}

//    @ApiOperation(value= "시도 코드에 따른 구군 데이터", notes ="시도 코드에 따른 구군 데이터를 반환함", response = SidoGugunCodeDto.class)
//    @GetMapping("/attr/gugun/{sidoCode}")
//    public ResponseEntity<SidoGugunCodeDto> getGugun() {
//
//
//
//        return new ResponseEntity<SidoGugunCodeDto>();
//    }

}
