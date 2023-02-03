package com.khw.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khw.exam.demo.repository.MemberRepository;
import com.khw.exam.demo.vo.Member;

@Service
public class MemberService {
	private MemberRepository memberRepository;

	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public int doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		// 로그인 아이디 중복 체크
		Member existsMember = getMemberByLoginId(loginId);

		if (existsMember != null) {
			return -1;
		}
		// 이름 이메일 중복체크
		existsMember = getMemberByNameEmailnId(nickname, email);
		if (existsMember != null) {
			return -2;
		}
		
		memberRepository.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		return memberRepository.getLastInsertId();
	}

	public Member getMemberById(int id) {

		return memberRepository.getMemberById(id);
	}

	private Member getMemberByLoginId(String loginId) {

		return memberRepository.getMemberByLoginId(loginId);
	}
	private Member getMemberByNameEmailnId(String name, String email) {

		return memberRepository.getMemberByNameEmailnId(name,email);
	}

}
