package com.ssafy.vue.planner.controller;

import com.ssafy.vue.planner.model.PlannerDto;
import com.ssafy.vue.planner.model.PlannerItemDto;
import com.ssafy.vue.planner.model.service.PlannerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "플래너 검색", notes = "날짜에 맞는 플래너를 반환함")
    @PostMapping("/search")
    public ResponseEntity<?> searchPlan(
            @RequestBody PlannerDto plannerDto
    ) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        log.info("searchPlan 호출");
        List<PlannerDto> plan = plannerService.searchPlan(plannerDto);

        if(plan.size() == 0) {
            plan = Collections.emptyList();
        }

        map.put("resultData", plan);

        return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "플래너 아이템", notes = "플래너 번호에 맞는 플래너 아이템을 반환함")
    @GetMapping("/{plannerid}/items")
    public ResponseEntity<?> getPlanItem(
            @PathVariable("plannerid") @ApiParam(value = "얻어올 플래너 아이디", required = true) int plannerid
    ) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        log.info("getPlanItem 호출");
        List<PlannerItemDto> planItem = plannerService.getPlanItem(plannerid);
        map.put("resultData", planItem);

        return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    }

}
