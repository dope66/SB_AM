<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="PASSWORD MODIFY" />
<%@ include file="../common/head.jsp"%>

<script>
	function MemberModify__submit(form) {
		form.loginPw.value = form.loginPw.value.trim();
		form.loginPwConfirm.value = form.loginPwConfirm.value.trim();

		if (form.loginPw.value.length == 0) {
			alert('새 비밀번호를 입력해주세요');
			form.loginPw.focus();

			return;

		}

		if (form.loginPwConfirm.value.length == 0) {
			alert('새 비밀번호 확인을 입력해주세요');
			form.loginPwConfirm.focus();

			return;
		}

		if (form.loginPw.value != form.loginPwConfirm.value) {
			alert('비밀번호가 일치하지 않습니다');
			form.loginPw.focus();

			return;
		}

		form.submit();
	}
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<form action="doPassWordModify" method="POST" onsubmit="passWordModify__submit(this); return false;">
			<div class="table-box-type-1">
				<table>
					<colgroup>
						<col width="200" />
					</colgroup>

					<tbody>
					<tr>
						<th>새 비밀번호</th>
						<td><input class="input input-bordered w-full max-w-xs" type="text" name="loginPw" placeholder="새 비밀번호를 입력해주세요" /></td>
					</tr>
					<tr>
						<th>새 비밀번호 확인</th>
						<td><input class="input input-bordered w-full max-w-xs" type="text" name="loginPwConfirm" placeholder="새 비밀번호 확인을 입력해주세요" /></td>
					</tr>
					<tr>
						<td colspan="2"><input class="btn btn-active btn-ghost" type="submit" value="변경"/></td>
					</tr>
					</tbody>
				</table>
			</div>
		</form>
		<div class="btns mt-2">
			<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
		</div>
	</div>
</section>
<%@ include file="../common/foot.jsp"%>