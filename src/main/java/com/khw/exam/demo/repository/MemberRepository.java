package com.khw.exam.demo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {

	public void doJoin(String loginId, String loginPw, String name, String nickname, String cellphone, String email);
	
}
