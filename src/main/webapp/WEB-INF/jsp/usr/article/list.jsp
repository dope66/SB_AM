<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.name } 게시판" />
<%@ include file="../common/head.jsp"%>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="mb-2 flex justify-between ">
			<div>
				<span>${articlesCount } 개</span>
			</div>
			<form>
				<input type="hidden" name="boardId" value="${boardId }"/>
				<select data-value="${searchKeywordTypeCode }" class="select select-bordered" name="searchKeywordTypeCode">
					<option value="title">제목</option>
					<option value="body">내용</option>
					<option value="title,body">제목 + 내용</option>
				</select> <input class="ml-2 w-84 input input-bordered" type="text" name="searchKeyword" 
				placeholder="검색어를 입력해주세요" maxlength="20" value="${searchKeyword }"/>
				<button class="ml-2 btn btn-active btn-ghost">검색</button>

			</form>
		</div>
		<div class="table-box-type-1">
			<table class="table w-full">
			<colgroup>
				<col width="60"/>
				<col width="140"/>
				<col />
				<col width="120"/>
				<col width="50"/>
			</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>날짜</th>
						<th>제목</th>
						<th>작성자</th>
						<th>조회수</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="article" items="${articles}">
						<tr class="hover">
							<td>${article.id}</td>
							<td>${article.regDate.substring(2,16)}</td>
							<td><a class="hover:underline"
								href="detail?id=${article.id}">${article.title}</a></td>
							<td>${article.writerName}</td>
							<td>${article.hitCount}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="mt-2 flex justify-end">
			<c:if test="${rq.getLoginedMemberId() != 0 }">
				<a class="btn-text-link btn btn-active btn-ghost"
					href="/usr/article/write">WRITE</a>
			</c:if>
		</div>
		<div class="page-menu mt-2 flex justify-center">
			<div class="btn-group">
				<c:set var="pageMenuLen" value="5" />
				<c:set var="startPage"
					value="${page - pageMenuLen >= 1 ? page - pageMenuLen : 1}" />
				<c:set var="endPage"
					value="${page + pageMenuLen <= pagesCount ? page + pageMenuLen : pagesCount}" />
				<c:set var="pageBaseUri" value="?boardId=${baordId }&searchKeywordTypeCode=${searchKeywordTypeCode }&searchKeyword=${searchKeyword }"/>
				<c:if test="${page == 1 }">
					<a class="btn btn-sm btn-disabled">«</a>
					<a class="btn btn-sm btn-disabled">&lt;</a>
				</c:if>
				<c:if test="${page > 1 }">
					<a class="btn btn-sm" href="${pageBaseUri }&page=1">«</a>
					<a class="btn btn-sm" href="${pageBaseUri }&page=${page - 1 }">&lt;</a>
				</c:if>
				<c:forEach begin="${startPage }" end="${endPage }" var="i">
					<a class="btn btn-sm ${page == i ? 'btn-active' : ''}"
						href="${pageBaseUri }&page=${i }">${i }</a>
				</c:forEach>
				<c:if test="${page < pagesCount }">
					<a class="btn btn-sm" href="${pageBaseUri }&page=${page + 1 }">&gt;</a>
					<a class="btn btn-sm"
						href="${pageBaseUri }&page=${pagesCount }">»</a>
				</c:if>
				<c:if test="${page == pagesCount }">
					<a class="btn btn-sm btn-disabled">&gt;</a>
					<a class="btn btn-sm btn-disabled">»</a>
				</c:if>

			</div>

		</div>
	</div>
</section>
<%@ include file="../common/foot.jsp"%>