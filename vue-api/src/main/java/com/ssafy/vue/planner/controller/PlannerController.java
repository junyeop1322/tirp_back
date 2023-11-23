package com.ssafy.vue.planner.controller;

import com.ssafy.vue.planner.model.*;
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
    @GetMapping("/user/{userid}")
    public ResponseEntity<?> getPlan(
            @PathVariable("userid") @ApiParam(value = "얻어올 플래너의 유저아이디", required = true) String userid
    ) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        log.info("getPlan - 호출 : " + userid);
        List<PlanItemDto> planner = plannerService.getPlan(userid);
        if (planner.size() == 0) {
            planner = Collections.emptyList();
        } else {
            for (int i = 0; i < planner.size(); i++) {
                int plannerid = planner.get(i).getPlannerId();
                planner.get(i).setAttractionDto(getPlanAttraction(plannerid));
            }
        }
        map.put("resultData", planner);

        return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    }

    public List<AttractionDto> getPlanAttraction (int plannerId) throws Exception {
        List<AttractionDto> attr = plannerService.getPlanAttraction(plannerId);

        if (attr.size() == 0) {
            attr = Collections.emptyList();
        }

        return attr;
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
        AttractionPlanDto attractionPlanDto = new AttractionPlanDto();
        attractionPlanDto.setPlannerItemDto(planItem);
        attractionPlanDto.setAttractionDto(getPlanAttraction(plannerid));

        map.put("resultData", attractionPlanDto);

        return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    }



    @ApiOperation(value = "플래너 글작성", notes = "새로운 플래너 정보를 입력함")
    @PostMapping("/{plannerid}")
    public ResponseEntity<?> writePlanner(
            @RequestBody @ApiParam(value = "플래너 정보", required = true) PlannerDto plannerDto,
            @PathVariable("plannerid") @ApiParam(value = "플래너 번호") int plannerid)
     {
        log.info("writePlanner 호출 : " + plannerDto);
        try {
            plannerDto.setPlannerId(plannerid);
            plannerService.writePlanner(plannerDto);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "플래너수정", notes = "수정할 플래너 정보를 입력함", response = String.class)
    @PutMapping
    public ResponseEntity<?> modifyPlanner(
            @RequestBody @ApiParam(value = "수정할 플래너 정보", required = true) PlannerDto plannerDto
    ) throws Exception {
        log.info("modifyPlanner 호출 : " + plannerDto);

        plannerService.modifyPlanner(plannerDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "플래너 삭제", notes = "플래너 번호에 해당하는 플래너를 삭제함")
    @DeleteMapping
    public ResponseEntity<?> deletePlanner(
            @PathVariable("plannerid") @ApiParam(value = "삭제할 플래너 아이디") int plannerid
    ) throws Exception {
        log.info("deletePlanner 호출 : " + plannerid);
        plannerService.deletePlanner(plannerid);

        return ResponseEntity.ok().build();
    }

//    @ApiOperation(value = "플래너 아이템 글작성", notes = "새로운 플래너 아이템 정보를 입력함")
//    @PostMapping("/item/{plannerid}/{contentid}")
//    public ResponseEntity<?> writePlannerItem(
//            @PathVariable("plannerid") @ApiParam(value = "플래너 번호") int plannerid,
//            @PathVariable("contentid") @ApiParam(value = "콘텐트 번호") int contentid)
//    {
//        log.info("writePlannerItem 호출");
//        try {
//            plannerService.writerPlannerItem(plannerid, contentid);
//            return new ResponseEntity<Void>(HttpStatus.CREATED);
//        } catch (Exception e) {
//            return exceptionHandling(e);
//        }
//    }

//    @ApiOperation(value = "플래너 아이템 삭제", notes = "플래너 번호에 해당하는 플래너를 삭제함")
//    @DeleteMapping
//    public ResponseEntity<?> deletePlannerItem(
//            @PathVariable("plannerid") @ApiParam(value = "삭제할 플래너 아이디") int plannerid,
//            @PathVariable("contentid") @ApiParam(value = "콘텐트 번호") int contentid
//    ) throws Exception {
//        log.info("deletePlannerItem 호출");
//        plannerService.deletePlannerItem(plannerid, contentid);
//
//        return ResponseEntity.ok().build();
//    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
