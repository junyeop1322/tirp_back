package com.ssafy.vue.attr.model.service;

import com.ssafy.vue.attr.model.AttrDto;
import com.ssafy.vue.attr.model.ContentDto;
import com.ssafy.vue.attr.model.SidoGugunDto;
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

    @Override
    public List<SidoGugunDto> getSido() throws Exception {
        return attrMapper.getSido();
    }

    @Override
    public List<SidoGugunDto> getGugun(int sidoCode) throws Exception {
        return attrMapper.getGugun(sidoCode);
    }

    @Override
    public List<AttrDto> getAttraction(int sidoCode, int gugunCode) throws Exception {
        return attrMapper.getAttraction(sidoCode, gugunCode);
    }

    @Override
    public List<ContentDto> getContentTypeId(ContentDto contentDto) throws Exception {
        if (contentDto.getGugunCode() == 0) {
            return attrMapper.getContentTypeId2(contentDto);
        } else {
            return attrMapper.getContentTypeId(contentDto);
        }
    }


}
