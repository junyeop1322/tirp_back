package com.ssafy.vue.planner.model.mapper;

import com.ssafy.vue.planner.model.PlannerDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlannerMapper {

    List<PlannerDto> getPlan(String userId) throws Exception;

    PlannerDto getPlanDetail(int plannerId) throws Exception;

    int countPlanner() throws Exception;
}
