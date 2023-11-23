package com.ssafy.vue.planner.model.service;

import com.ssafy.vue.planner.model.AttractionDto;
import com.ssafy.vue.planner.model.PlanItemDto;
import com.ssafy.vue.planner.model.PlannerDto;
import com.ssafy.vue.planner.model.PlannerItemDto;
import com.ssafy.vue.planner.model.mapper.PlannerMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlannerServiceImpl implements PlannerService {

    private PlannerMapper plannerMapper;

    public PlannerServiceImpl(PlannerMapper plannerMapper) {
        super();
        this.plannerMapper = plannerMapper;
    }

    @Override
    public List<PlanItemDto> getPlan(String userId) throws Exception {
        return plannerMapper.getPlan(userId);
    }

    @Override
    public List<AttractionDto> getPlanAttraction(int plannerId) throws Exception {
        return plannerMapper.getPlanAttraction(plannerId);
    }

    @Override
    public PlannerDto getPlanDetail(int plannerId) throws Exception {
        return plannerMapper.getPlanDetail(plannerId);
    }

    public int countPlanner() throws Exception {
        return plannerMapper.countPlanner();
    }

    @Override
    public List<PlannerDto> searchPlan(PlannerDto plannerDto) throws Exception {
        return plannerMapper.searchPlan(plannerDto);
    }

    @Override
    public List<PlannerItemDto> getPlanItem(int plannerId) throws Exception {
        return plannerMapper.getPlanItem(plannerId);
    }

    @Override
    public void writePlanner(PlannerDto plannerDto) throws Exception {
        plannerMapper.writePlanner(plannerDto);
    }

    @Override
    public void modifyPlanner(PlannerDto plannerDto) throws Exception {
        plannerMapper.modifyPlanner(plannerDto);
    }

    @Override
    public void deletePlanner(int planner_id) throws Exception {
        plannerMapper.deletePlanner(planner_id);
    }

    @Override
    public void writerPlannerItem(int plannerId, int contentId) {
        plannerMapper.writerPlannerItem(plannerId, contentId);
    }

    @Override
    public void deletePlannerItem(int plannerId, int contentId) {
        plannerMapper.deletePlannerItem(plannerId, contentId);
    }

}
