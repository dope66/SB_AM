package com.khw.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khw.exam.demo.service.MemberService;
import com.khw.exam.demo.util.Utility;
import com.khw.exam.demo.vo.Member;
import com.khw.exam.demo.vo.ResultData;

import jakarta.servlet.http.HttpSession;

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
			return ResultData.from("F-1", "아이디를 입력해주세요");
		}
//		비밀번호
		if (Utility.empty(loginPw)) {
			return ResultData.from("F-2", "비밀번호를 입력해주세요");
		}

		if (Utility.empty(name)) {
			return ResultData.from("F-3", "이름을 입력해주세요");
		}
		if (Utility.empty(nickname)) {
			return ResultData.from("F-4", "닉네임을 입력해주세요");
		}
		if (Utility.empty(cellphoneNum)) {
			return ResultData.from("F-5", "폰번을 입력해주세요");
		}

		if (Utility.empty(email)) {
			return ResultData.from("F-6", "이멜을 입력해주세요");
		}

		ResultData<Integer> doJoinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		if (doJoinRd.isFail()) {
			return ResultData.from(doJoinRd.getResultCode(), doJoinRd.getMsg());
		}

		Member member = memberService.getMemberById((int) doJoinRd.getData1());

		return ResultData.from(doJoinRd.getResultCode(), doJoinRd.getMsg(), member);
	}

	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public ResultData<Member> doLogin(HttpSession httpSession, String loginId, String loginPw) {

		// 중복 체크 
		if(httpSession.getAttribute("loginedMemberId")!=null) {
			return ResultData.from("F-1", "이미 로그인 되어있습니다.");
		}
		if (Utility.empty(loginId)) {
			return ResultData.from("F-2", "아이디를 입력해주세요");
		}
//		비밀번호
		if (Utility.empty(loginPw)) {
			return ResultData.from("F-3", "비밀번호를 입력해주세요");
		}
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if(member ==null) {
			return ResultData.from("F-4", "존재하지 않는 아이디 입니다..");
		}
		if(member.getLoginPw().equals(loginPw) == false) {
			return ResultData.from("F-5", "비밀번호가 일치 하지 않습니다.");
		}
		//위를 다 뚫고 내려오면 로그인이 된거지 
		
		// 세션에 회원 번호를 저장 
		httpSession.setAttribute("loginedMemberId", member.getId());
		return ResultData.from("S-1", Utility.f("%s님 환영합니다.",member.getNickname()));
	}
	
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public ResultData doLogout(HttpSession httpSession) {

		if(httpSession.getAttribute("loginedMemberId")==null) {
			return ResultData.from("F-1", "로그인 상태가 아닙니다.");
		}
		httpSession.removeAttribute("loginedMemberId");
		return ResultData.from("S-1", "로그아웃 되었습니다.");
	}
	
	
}
