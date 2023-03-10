<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MEMBER MODIFY" />
<%@ include file="../common/head.jsp"%>

<script>
    function MemberModify__submit(form) {
        form.nickname.value = form.nickname.value.trim();

        if (form.nickname.value.length == 0) {
            alert('닉네임을 입력해주세요');
            form.nickname.focus();

            return;
        }

        form.cellphoneNum.value = form.cellphoneNum.value.trim();

        if (form.cellphoneNum.value.length == 0) {
            alert('전화번호를 입력해주세요');
            form.cellphoneNum.focus();

            return;
        }

        form.email.value = form.email.value.trim();

        if (form.email.value.length == 0) {
            alert('이메일을 입력해주세요');
            form.email.focus();

            return;
        }

        form.submit();

    }
</script>

<section class="mt-8 text-xl">
    <div class="container mx-auto px-3">
        <form action="doModify" method="POST" onsubmit="MemberModify__submit(this); return false;">
            <input type="text" name="memberModifyAuthKey", value="${param.memberModifyAuthKey}">
            <div class="table-box-type-1">
                <table class="table table-zebra w-full">
                    <colgroup>
                        <col width="200" />
                    </colgroup>

                    <tbody>
                    <tr>
                        <th>가입일</th>
                        <td>${rq.loginedMember.regDate}</td>
                    </tr>
                    <tr>
                        <th>로그인 아이디</th>
                        <td>${rq.loginedMember.loginId}</td>
                    </tr>
                    <tr>
                        <th>이름</th>
                        <td>${rq.loginedMember.name}</td>
                    </tr>
                    <tr>
                        <th>닉네임</th>
                        <td><input class="input input-bordered w-full max-w-xs" type="text" name="nickname" placeholder="닉네임을 입력해주세요" value="${rq.loginedMember.nickname }"/></td>
                    </tr>
                    <tr>
                        <th>전화번호</th>
                        <td><input class="input input-bordered w-full max-w-xs" type="text" name="cellphoneNum" placeholder="전화번호를 입력해주세요" value="${rq.loginedMember.cellphoneNum }"/></td>
                    </tr>
                    <tr>
                        <th>이메일</th>
                        <td><input class="input input-bordered w-full max-w-xs" type="text" name="email" placeholder="이메일을 입력해주세요" value="${rq.loginedMember.email }"/></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="flex justify-between">
                <div class="mt-2">
                    <button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
                </div>
                <div class="btns mt-2">
                    <a class="btn btn-active btn-ghost mr-2" href="passWordModify?memberModifyAuthKey=${param.memberModifyAuthKey}">비밀번호 변경</a>
                    <input class="btn-text-link btn btn-active btn-ghost" type="submit" value="수정"/>
                </div>
            </div>
        </form>
    </div>
</section>
<%@ include file="../common/foot.jsp"%>