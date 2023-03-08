<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="ARTICLE DETAIL"/>
<%@ include file="../common/head.jsp" %>

<script>
    const params = {};
    params.id = parseInt('${param.id}');

    function ArticleDetail__increaseHitCount() {

        const localStorageKey = 'article__' + params.id + '__alreadyView';

        if (localStorage.getItem(localStorageKey)) {
            return;
        }
        localStorage.setItem(localStorageKey, true);

        $.get('doIncreaseHitCountRd', {
            id: params.id,
            ajaxMode: 'Y'
        }, function (data) {
            $('.article-detail__hit-count').empty().html(data.data1);
        }, 'json');
    }

    function ReactionPoint__getReactionPoint() {

        $.get('../reactionPoint/getReactionPoint', {
            id: params.id,
            relTypeCode: 'article',
            ajaxMode: 'Y'
        }, function (data) {
            if (data.data1.sumReactionPoint > 0) {
                let goodBtn = $('#goodBtn');
                goodBtn.removeClass('btn-outline');
                goodBtn.prop('href', '../reactionPoint/delReactionPoint?id=${article.id}&relTypeCode=article&point=1')
            } else if (data.data1.sumReactionPoint < 0) {
                let badBtn = $('#badBtn');
                badBtn.removeClass('btn-outline');
                badBtn.prop('href', '../reactionPoint/delReactionPoint?id=${article.id}&relTypeCode=article&point=-1')
            }
        }, 'json');
    }

    $(function () {
// 		실전코드
        ArticleDetail__increaseHitCount();
        ReactionPoint__getReactionPoint();

// 		연습코드
// 		setTimeout(ArticleDetail__increaseHitCount, 2000);
    })

</script>

<section class="mt-8 text-xl">
    <div class="container mx-auto px-3 pb-5 border-bottom-line">
        <div class="table-box-type-1">
            <table>
                <colgroup>
                    <col width="200"/>
                </colgroup>

                <tbody>
                <tr>
                    <th>번호</th>
                    <td>
                        <div class="badge">${article.id}</div>
                    </td>
                </tr>
                <tr>
                    <th>작성날짜</th>
                    <td>${article.regDate}</td>
                </tr>
                <tr>
                    <th>수정날짜</th>
                    <td>${article.updateDate}</td>
                </tr>
                <tr>
                    <th>조회수</th>
                    <td><span class="badge article-detail__hit-count">${article.hitCount}</span></td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${article.writerName}</td>
                </tr>
                <tr>
                    <th>추천</th>
                    <td>
                        <c:if test="${rq.getLoginedMemberId() == 0 }">
                            <span class="badge">좋아요 : ${article.goodReactionPoint}개</span>
                            <br/>
                            <span class="badge">싫어요 : ${article.badReactionPoint * -1}개</span>
                        </c:if>
                        <c:if test="${rq.getLoginedMemberId() != 0 }">
                            <a id="goodBtn" class="btn btn-xs btn-outline"
                               href="../reactionPoint/doReactionPoint?id=${article.id }&relTypeCode=article&point=1">좋아요
                                👍</a>
                            <span class="badge">좋아요 : ${article.goodReactionPoint}개</span>
                            <br/>
                            <a id="badBtn" class="btn btn-xs btn-outline"
                               href="../reactionPoint/doReactionPoint?id=${article.id }&relTypeCode=article&point=-1">싫어요
                                👎</a>
                            <span class="badge">싫어요 : ${article.badReactionPoint * -1}개</span>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <th>제목</th>
                    <td>${article.title}</td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td>${article.getForPrintBody()}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="btns mt-2">
            <button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="location.href='/usr/article/list'">뒤로가기</button>
            <c:if test="${article.actorCanChangeData }">
                <a class="btn-text-link btn btn-active btn-ghost" href="modify?id=${article.id }">수정</a>
                <a class="btn-text-link btn btn-active btn-ghost"
                   onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;" href="doDelete?id=${article.id }">삭제</a>
            </c:if>
        </div>
    </div>
</section>

<script>
    function ReplyWrite__submitForm(form) {

        form.body.value = form.body.value.trim();

        if (form.body.value.length < 2) {
            alert('2글자 이상 입력해주세요');
            form.body.focus();
            return;
        }

        form.submit();
    }

    originalForm = null;
    originalId = null;


    function ReplyModify__getForm(replyId, i) {
        if (originalForm != null) {
            ReplyModify__cancel(originalId);
        }
        $.get('../reply/getReplyContent', {
            id: replyId,
            ajaxMode: 'Y'
        }, function (data) {
            let replyContent = $('#' + i);
            originalId  = i;
            originalForm = replyContent.html();
            let addHtml = `
				<form action="../reply/doModify" method="POST" onsubmit="ReplyWrite__submitForm(this); return false;">
					<input type="hidden" name="id" value="\${data.data1.id}" />
					<div class="mt-2 p-4 rounded-lg border border-gray-400 text-base">
						<div class="mb-2"><span>\${data.data1.writerName}</span></div>
						<textarea class="textarea textarea-bordered w-full" name="body" rows="2" placeholder="댓글을 남겨보세요">\${data.data1.body}</textarea>
						<div class="flex justify-end">
							<a onclick="ReplyModify__cancel(\${i})" class="btn btn-active btn-ghost btn-sm mr-2">취소</a>
							<button class="btn btn-active btn-ghost btn-sm">등록</button>
						</div>
					</div>
				</form>`;

            replyContent.empty().html("");
            replyContent.append(addHtml);

        }, 'json');
    }

    function ReplyModify__cancel(i) {
        let replyContent = $('#' + i);
        replyContent.html(originalForm);

        originalForm = null;
        originalId = null;

    }
</script>

<section class="mt-5 text-xl mb-5">
    <div class="container mx-auto px-3">
        <h2>댓글<span class="text-base">(${replies.size() }개)</span></h2>

        <c:forEach var="reply" items="${replies}" varStatus="status">
            <div id="${status.count}" class="py-2 pl-16 border-bottom-line text-base">
                <div class="py-2 pl-16 border-bottom-line text-base">
                    <div class="flex justify-between">
                        <div class="font-semibold"><span>${reply.writerName }</span></div>

                            <%--                    <c:if test="${rq.loginedMember.id == reply.memberId}">--%>
                        <c:if test="${reply.actorCanChangeData}">
                            <!-- 여기서부터 -->
                            <div class="dropdown">
                                <button class="btn btn-circle btn-ghost btn-sm">
                                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                         class="inline-block w-5 h-5 stroke-current">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                              d="M5 12h.01M12 12h.01M19 12h.01M6 12a1 1 0 11-2 0 1 1 0 012 0zm7 0a1 1 0 11-2 0 1 1 0 012 0zm7 0a1 1 0 11-2 0 1 1 0 012 0z"></path>
                                    </svg>
                                </button>
                                <ul tabindex="0"
                                    class="menu menu-compact dropdown-content mt-3 p-2 shadow bg-base-100 rounded-box w-20">
                                    <li><a onclick="ReplyModify__getForm(${reply.id }, ${status.count });">수정</a></li>
                                    <li><a onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;"
                                           href="../reply/doDelete?id=${reply.id }">삭제</a></li>
                                </ul>
                            </div>
                            <!-- 여기까지 -->
                        </c:if>

                    </div>
                    <div><span>${reply.getForPrintBody() }</span></div>
                    <div class="text-sm text-gray-400"><span>${reply.updateDate }</span></div>

                </div>
            </div>
        </c:forEach>

        <c:if test="${rq.getLoginedMemberId() != 0 }">
            <form action="../reply/doWrite" method="POST" onsubmit="ReplyWrite__submitForm(this); return false;">
                <input type="hidden" name="relTypeCode" value="article"/>
                <input type="hidden" name="relId" value="${article.id }"/>
                <div class="mt-4 p-4 rounded-lg border border-gray-400 text-base">
                    <div class="mb-2"><span>${rq.loginedMember.nickname }</span></div>
                    <textarea class="textarea textarea-bordered w-full" name="body" rows="2"
                              placeholder="댓글을 남겨보세요"></textarea>
                    <div class="flex justify-end">
                        <button class="btn btn-active btn-ghost btn-sm">등록</button>
                    </div>
                </div>
            </form>
        </c:if>
    </div>
</section>

<%@ include file="../common/foot.jsp" %>