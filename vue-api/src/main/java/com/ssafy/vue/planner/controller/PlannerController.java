package com.ssafy.vue.planner.controller;

import com.ssafy.vue.planner.model.PlannerDto;
import com.ssafy.vue.planner.model.service.PlannerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/plan")
@Slf4j
public class PlannerController {

    private PlannerService plannerService;

    public PlannerController(PlannerService plannerService) {
        super();
        this.plannerService = plannerService;
    }

    @ApiOperation(value = "플래너 목록 보기", notes = "유저 아이디에 맞는 플래너의 목록을 반환함", response = PlannerDto.class)
    @GetMapping("/list/{userid}")
    public ResponseEntity<?> getPlan(
            @PathVariable("userid") @ApiParam(value = "얻어올 플래너의 유저아이디", required = true) String userid
    ) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        log.info("getPlan - 호출 : " + userid);
        List<PlannerDto> planner = plannerService.getPlan(userid);
        if (planner.size() == 0) {
            planner = Collections.emptyList();
        }
        map.put("resultData", planner);

        return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "플래너 정보 반환", notes = "플래너 아이디에 맞는 플래너의 정보를 반환함", response = PlannerDto.class)
    @GetMapping("/{plannerid}")
    public ResponseEntity<?> getPlanDetail(
            @PathVariable("plannerid") @ApiParam(value = "얻어올 플래너 아이디", required = true) int plannerid
    ) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        log.info("getPlanDetail - 호출 : " + plannerid);
        PlannerDto plannerDto = plannerService.getPlanDetail(plannerid);
        if (plannerDto == null) {
            plannerDto = new PlannerDto();
        }
        map.put("resultData", plannerDto);

        return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "플래너 개수", notes = "검색할 수 있는 모든 플래너의 수 반환", response = PlannerDto.class)
    @GetMapping("/count")
    public ResponseEntity<?> countPlanner() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        int count = plannerService.countPlanner();
        log.info("countPlanner - 호출 : " + count);
        map.put("resultData", count);

        return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    }



}
