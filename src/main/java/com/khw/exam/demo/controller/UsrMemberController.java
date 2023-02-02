package com.khw.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khw.exam.demo.service.MemberService;
import com.khw.exam.demo.vo.Member;

@Controller
public class UsrMemberController {

	private MemberService memberService;

	// 의존성 주입
	@Autowired
	public UsrMemberController(MemberService memberService) {
		this.memberService = memberService;

	}

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public Object doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
//		 아이디가 빠지거나 공백인경우
		if (loginId == null || loginId.trim().length() == 0) {
			return "아이디를 입력해 주세요";
		}
//		비밀번호
		if (loginPw == null || loginPw.trim().length() == 0) {
			return "비밀번호를 입력해 주세요";
		}

		if (name == null || name.trim().length() == 0) {
			return "이름을 입력해 주세요";
		}
		if (nickname == null || nickname.trim().length() == 0) {
			return "닉네임을 입력해 주세요";
		}
		if (cellphoneNum == null || cellphoneNum.trim().length() == 0) {
			return "폰번를 입력해 주세요";
		}
		
		if (email == null || email.trim().length() == 0) {
			return "이메일를 입력해 주세요";
		}
		
		int id = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		if (id == -1) {
//			이게 Member랑 타입이 안맞아서 Object로 바꿔준다.
			return "이미 사용중인 아이디입니다.";
		}

		Member member = memberService.getMemberById(id);
		return member;
	}

}
