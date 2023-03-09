package com.khw.exam.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khw.exam.demo.service.MemberService;
import com.khw.exam.demo.util.Utility;
import com.khw.exam.demo.vo.Member;
import com.khw.exam.demo.vo.ResultData;
import com.khw.exam.demo.vo.Rq;

@Controller
public class UsrMemberController {

    private MemberService memberService;
    private Rq rq;

    // 의존성 주입
    @Autowired
    public UsrMemberController(MemberService memberService, Rq rq) {
        this.memberService = memberService;
        this.rq = rq;

    }

    @RequestMapping("/usr/member/login")
    public String showLogin() {
        return "usr/member/login";
    }

    @RequestMapping("/usr/member/doJoin")
    @ResponseBody
    public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
                                     String email) {

        if (Utility.empty(loginId)) {
            return ResultData.from("F-1", "아이디를 입력해주세요");
        }
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
            return ResultData.from("F-5", "전화번호를 입력해주세요");
        }
        if (Utility.empty(email)) {
            return ResultData.from("F-6", "이메일을 입력해주세요");
        }

        ResultData<Integer> doJoinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);

        if (doJoinRd.isFail()) {
            return ResultData.from(doJoinRd.getResultCode(), doJoinRd.getMsg());
        }

        Member member = memberService.getMemberById((int) doJoinRd.getData1());

        return ResultData.from(doJoinRd.getResultCode(), doJoinRd.getMsg(), "member", member);
    }

    @RequestMapping("/usr/member/doLogin")
    @ResponseBody
    public String doLogin(String loginId, String loginPw) {

        if (Utility.empty(loginId)) {
            return Utility.jsHistoryBack("아이디를 입력해주세요");
        }
//		비밀번호
        if (Utility.empty(loginPw)) {
            return Utility.jsHistoryBack("비밀번호를 입력해주세요");
        }

        Member member = memberService.getMemberByLoginId(loginId);

        if (member == null) {
            return Utility.jsHistoryBack("존재하지 않는 아이디 입니다..");
        }
        if (member.getLoginPw().equals(loginPw) == false) {
            return Utility.jsHistoryBack("비밀번호가 일치 하지 않습니다.");
        }
        // 위를 다 뚫고 내려오면 로그인이 된거지

        // 세션에 회원 번호를 저장
        rq.login(member);
//		
        return Utility.jsReplace(Utility.f("%s님 환영합니다.", member.getNickname()), "/");
    }

    @RequestMapping("/usr/member/doLogout")
    @ResponseBody
    public String doLogout() {


        rq.logout();

        return Utility.jsReplace("로그아웃 되었습니다.", "/");
    }

    @RequestMapping("/usr/member/myPage")
    public String showMyPage() {
        return "usr/member/myPage";
    }

    @RequestMapping("/usr/member/checkPassword")
    public String showCheckPassword() {
        return "usr/member/checkPassword";
    }

    @RequestMapping("/usr/member/doCheckPassword")
    public String doCheckPassword(String loginPw) {
        if (Utility.empty(loginPw)) {
            return rq.jsReturnOnView("비밀번호를 입력해주세요",true);
        }
        if(rq.getLoginedMember().getLoginPw().equals(loginPw) ==false){
            return rq.jsReturnOnView("일치 하지 않습니다.",true);
        }
        return "usr/member/modify";
    }
    @RequestMapping("/usr/member/doModify")
    @ResponseBody
    public String doModify(String nickname, String cellphoneNum, String email) {

        if (Utility.empty(nickname)) {
            return Utility.jsHistoryBack("닉네임을 입력해주세요");
        }
        if (Utility.empty(cellphoneNum)) {
            return Utility.jsHistoryBack("전화번호를 입력해주세요");
        }
        if (Utility.empty(email)) {
            return Utility.jsHistoryBack("이메일을 입력해주세요");
        }

        memberService.doModify(rq.getLoginedMemberId(), nickname, cellphoneNum, email);

        return Utility.jsReplace("회원정보가 수정되었습니다", "/");
    }
    @RequestMapping("/usr/member/passWordModify")
    public String passWordModify() {
        return "usr/member/passWordModify";
    }

    @RequestMapping("/usr/member/doPassWordModify")
    @ResponseBody
    public String doPassWordModify(String loginPw, String loginPwConfirm) {

        if (Utility.empty(loginPw)) {
            return Utility.jsHistoryBack("새 비밀번호를 입력해주세요");
        }
        if (Utility.empty(loginPwConfirm)) {
            return Utility.jsHistoryBack("새 비밀번호 확인을 입력해주세요");
        }
        if (loginPw.equals(loginPwConfirm) == false) {
            return Utility.jsHistoryBack("비밀번호가 일치하지 않습니다");
        }

        memberService.doPassWordModify(rq.getLoginedMemberId(), loginPw);

        return Utility.jsReplace("비밀번호가 수정되었습니다", "/");
    }

}
