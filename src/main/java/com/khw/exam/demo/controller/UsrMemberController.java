package com.khw.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khw.exam.demo.service.MemberService;

@Controller
public class UsrMemberController {
	
	private MemberService memberService;
	// 의존성 주입
	@Autowired
	public UsrMemberController (MemberService memberService) {
		this.memberService=memberService;
		
	}
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw,String name , String nickname, String cellphone, String email) {
		
		memberService.doJoin( loginId,  loginPw, name ,  nickname,  cellphone,  email);
		
		return "가입!";
	}
	
}
