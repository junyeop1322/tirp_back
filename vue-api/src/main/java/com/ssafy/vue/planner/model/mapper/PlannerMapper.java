package com.ssafy.vue.planner.model.mapper;

import com.ssafy.vue.planner.model.PlannerDto;
import com.ssafy.vue.planner.model.PlannerItemDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlannerMapper {

    List<PlannerDto> getPlan(String userId) throws Exception;

    PlannerDto getPlanDetail(int plannerId) throws Exception;

    int countPlanner() throws Exception;

    List<PlannerDto> searchPlan(PlannerDto plannerDto) throws Exception;

    List<PlannerItemDto> getPlanItem(int plannerId) throws Exception;

    void writePlanner(PlannerDto plannerDto) throws Exception;

    void modifyPlanner(PlannerDto plannerDto) throws Exception;

    void deletePlanner(int planner_id) throws Exception;

}
