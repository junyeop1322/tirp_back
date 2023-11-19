package com.ssafy.vue.member.model.service;

import com.ssafy.vue.member.model.MemberDto;

public interface MemberService {

	MemberDto login(MemberDto memberDto) throws Exception;

	MemberDto userInfo(String userId) throws Exception;

	void saveToken(String userId, String accessToken) throws Exception;

	Object getToken(String userId) throws Exception;

	void deleToken(String userId) throws Exception;

	void join(MemberDto memberDto) throws Exception;

	boolean idDuplicatedCheck(String userId) throws Exception;
}
