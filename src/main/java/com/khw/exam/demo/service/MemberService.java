package com.khw.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khw.exam.demo.repository.MemberRepository;
import com.khw.exam.demo.util.Utility;
import com.khw.exam.demo.vo.Member;
import com.khw.exam.demo.vo.ResultData;

@Service
public class MemberService {
	private MemberRepository memberRepository;

	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public ResultData doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		// 로그인 아이디 중복 체크
		Member existsMember = getMemberByLoginId(loginId);

		if (existsMember != null) {
			return ResultData.from("F-7",Utility.f("이미 사용중인 아이디%s입니다.",loginId));
		}
		// 이름 이메일 중복체크
		existsMember = getMemberByNameEmailnId(nickname, email);
		if (existsMember != null) {
			return ResultData.from("F-8",Utility.f("이미 사용중인 이름(%s), 이메일(%s) 입니다.",loginId,email));
		}
		
		memberRepository.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		int id =memberRepository.getLastInsertId(); 
		return ResultData.from("S-1","회원가입 완료",id);
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
