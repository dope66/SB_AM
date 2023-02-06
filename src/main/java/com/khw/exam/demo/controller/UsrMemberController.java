package com.khw.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khw.exam.demo.service.MemberService;
import com.khw.exam.demo.util.Utility;
import com.khw.exam.demo.vo.Member;
import com.khw.exam.demo.vo.ResultData;

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
	public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {

//		 아이디가 빠지거나 공백인경우
		if (Utility.empty(loginId)) {
			return ResultData.from("F-1","아이디를 입력해주세요");
		}
//		비밀번호
		if (Utility.empty(loginPw)) {
			return ResultData.from("F-2","비밀번호를 입력해주세요");
		}

		if (Utility.empty(name)) {
			return ResultData.from("F-3","이름을 입력해주세요");
		}
		if (Utility.empty(nickname)) {
			return ResultData.from("F-4","닉네임을 입력해주세요");
		}
		if (Utility.empty(cellphoneNum)) {
			return ResultData.from("F-5","폰번을 입력해주세요");
		}

		if (Utility.empty(email)) {
			return ResultData.from("F-6","이멜을 입력해주세요");
		}
		
		ResultData<Integer> doJoinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		if (doJoinRd.isFail()) {
			return   ResultData.from(doJoinRd.getResultCode(),doJoinRd.getMsg());
		}

		Member member = memberService.getMemberById((int)doJoinRd.getData1());
		
		return ResultData.from(doJoinRd.getResultCode(),doJoinRd.getMsg(),member);
	}

}
