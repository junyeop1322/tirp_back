package com.ssafy.vue.planner.model.service;

import com.ssafy.vue.planner.model.PlannerDto;

import java.util.List;

public interface PlannerService {

    List<PlannerDto> getPlan(String userId) throws Exception;

    PlannerDto getPlanDetail(int plannerId) throws Exception;

    int countPlanner() throws Exception;

}
