package com.ssafy.vue.member.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.vue.member.model.MemberDto;
import com.ssafy.vue.member.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	private MemberMapper memberMapper;

	public MemberServiceImpl(MemberMapper memberMapper) {
		super();
		this.memberMapper = memberMapper;
	}

	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		return memberMapper.login(memberDto);
	}
	
	@Override
	public MemberDto userInfo(String userId) throws Exception {
		return memberMapper.userInfo(userId);
	}

	@Override
	public void saveToken(String userId, String accessToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", accessToken);
		memberMapper.saveToken(map);
	}

	@Override
	public Object getToken(String userId) throws Exception {
		return memberMapper.getToken(userId);
	}

	@Override
	public void deleToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		memberMapper.deleteToken(map);
	}

	@Override
	public void join(MemberDto memberDto) throws Exception {
		memberMapper.join(memberDto);
	}

	@Override
	public boolean idDuplicatedCheck(String userId) throws Exception {
		int res = memberMapper.idDuplicatedCheck(userId);
		return res == 0;
	}
}
