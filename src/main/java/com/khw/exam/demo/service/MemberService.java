package com.khw.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khw.exam.demo.repository.MemberRepository;
@Service
public class MemberService {
	private MemberRepository memberRepository;
	
	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	public void doJoin(String loginId, String loginPw, String name, String nickname, String cellphone, String email) {
//		memberRepository.doJoin( loginId,  loginPw, name ,  nickname,  cellphone,  email);
	}


}
