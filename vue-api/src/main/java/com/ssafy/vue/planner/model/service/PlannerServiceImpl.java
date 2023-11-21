package com.ssafy.vue.planner.model.service;

import com.ssafy.vue.planner.model.PlannerDto;
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
    public List<PlannerDto> getPlan(String userId) throws Exception {
        return plannerMapper.getPlan(userId);
    }

    @Override
    public PlannerDto getPlanDetail(int plannerId) throws Exception {
        return plannerMapper.getPlanDetail(plannerId);
    }

    public int countPlanner() throws Exception {
        return plannerMapper.countPlanner();
    }

}
