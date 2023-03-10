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
    private AttrService attrService;

    @Autowired
    public MemberService(MemberRepository memberRepository ,AttrService attrService) {
        this.memberRepository = memberRepository;
        this.attrService = attrService;
    }

    public ResultData<Integer> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
        // 로그인 아이디 중복 체크
        Member existsMember = getMemberByLoginId(loginId);

        if (existsMember != null) {
            return ResultData.from("F-7", Utility.f("이미 사용중인 아이디%s입니다.", loginId));
        }
        // 이름 이메일 중복체크
        existsMember = getMemberByNameEmailnId(nickname, email);
        if (existsMember != null) {
            return ResultData.from("F-8", Utility.f("이미 사용중인 이름(%s), 이메일(%s) 입니다.", loginId, email));
        }

        memberRepository.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);

        int id = memberRepository.getLastInsertId();
        return ResultData.from("S-1", "회원가입 완료", "id", id);
    }

    public Member getMemberById(int id) {

        return memberRepository.getMemberById(id);
    }

    public Member getMemberByLoginId(String loginId) {

        return memberRepository.getMemberByLoginId(loginId);
    }

    private Member getMemberByNameEmailnId(String name, String email) {

        return memberRepository.getMemberByNameEmailnId(name, email);
    }

    public void doModify(int loginedMemberId, String nickname, String cellphoneNum, String email) {
        memberRepository.doModify(loginedMemberId, nickname, cellphoneNum, email);
    }

    public void doPassWordModify(int loginedMemberId, String loginPw) {
        memberRepository.doPassWordModify(loginedMemberId, loginPw);
    }


    public String genMemberModifyAuthKey(int loginedMemberId) {
        // 난수 발생후 받기
        String memberModifyAuthKey = Utility.getTempPassword(10);
        //5 분 제한 시한
        attrService.setValue("member", loginedMemberId, "extra", "memberModifyAuthKey", memberModifyAuthKey, Utility.getDateStrLater(60 * 5));

        return memberModifyAuthKey;
    }

    public ResultData checkMemberModifyAuthKey(int loginedMemberId, String memberModifyAuthKey) {
        String saved=attrService.getValue("member",loginedMemberId,"extra","memberModifyAuthKey");
        if(!saved.equals(memberModifyAuthKey)){
            return ResultData.from("F-1","일치하지 않거나 만료된 인증코드 입니다.");
        }
        return ResultData.from("S-1","인증이 완료 되었습니다.");
    }
}
