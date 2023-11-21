package com.ssafy.vue.attr.model.service;

import com.ssafy.vue.attr.model.AttrDto;
import com.ssafy.vue.attr.model.mapper.AttrMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttrServiceImpl implements AttrService {

    private AttrMapper attrMapper;

    @Autowired
    public AttrServiceImpl(AttrMapper attrMapper) {
        super();
        this.attrMapper = attrMapper;
    }

    @Override
    public int countAttr() throws Exception {
        return attrMapper.countAttr();
    }

    @Override
    public int countAttraction() throws Exception {
        return attrMapper.countAttraction();
    }


}
