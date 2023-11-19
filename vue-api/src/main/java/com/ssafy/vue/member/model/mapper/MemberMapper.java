package com.ssafy.vue.member.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.member.model.MemberDto;

@Mapper
public interface MemberMapper {

	MemberDto login(MemberDto memberDto) throws SQLException;
	MemberDto userInfo(String userId) throws SQLException;
	void saveToken(Map<String, String> map) throws SQLException;
	Object getToken(String userid) throws SQLException;
	void deleteToken(Map<String, String> map) throws SQLException;

	void join(MemberDto memberDto) throws SQLException;

	int idDuplicatedCheck(String userId) throws SQLException;
	
}
