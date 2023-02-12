package com.khw.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khw.exam.demo.service.ArticleService;
import com.khw.exam.demo.util.Utility;
import com.khw.exam.demo.vo.Article;
import com.khw.exam.demo.vo.ResultData;
import com.khw.exam.demo.vo.Rq;

@Controller
public class UsrArticleController {

	private ArticleService articleService;

	// 의존성 주입
	@Autowired
	public UsrArticleController(ArticleService articleService) {
		this.articleService = articleService;

	}

	// 액션메서드
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData<Article> doAdd(HttpServletRequest req, String title, String body) {
		// 로그인 안했는데 게시판 작성 안되게 하는거
		System.out.println("doAdd");
//		Rq rq = new Rq(req);
		Rq rq = (Rq)req.getAttribute("rq");

		if (Utility.empty(title)) {
			return ResultData.from("F-1", "제목을 입력해주세요");
		}
		if (Utility.empty(body)) {
			return ResultData.from("F-2", "내용을 입력해주세요");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body);
		int id = (int) writeArticleRd.getData1();
		Article article = articleService.getArticle(id);

		return ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), "article", article);
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model) {
		List<Article> articles = articleService.getArticles();
		model.addAttribute("articles", articles);
		return "usr/article/list";
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {
		Rq rq = (Rq) req.getAttribute("rq");
;
		if(rq.getLoginedMemberId()==0) {
			return Utility.jsHistoryBack("로그인 후 이용해주세요.");
		}
		// 아이디 받아와서 대조해서 맞으면 넘기는걸로
		Article article = articleService.getArticle(id);
		if (article == null) {
			return Utility.jsHistoryBack(Utility.f("%d 번 게시글은 존재하지 않습니다.", id));
		}
		if (rq.getLoginedMemberId() != article.getMemberId()) {
			return Utility.jsHistoryBack("해당 게시물에 권한이 없습니다.");
		}
//		articles.remove(article);
		articleService.deleteArticle(id);
		return Utility.jsReplace(Utility.f("%d 번 게시글을 삭제했습니다.", id), "list");
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(HttpServletRequest req, int id, String title, String body) {
		Rq rq = (Rq) req.getAttribute("rq");

		if(rq.getLoginedMemberId()==0){
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}
		
		Article article = articleService.getArticle(id);
//		if (article == null) {
//			return ResultData.from("F-1",Utility.f("%d번 게시물은 존재하지 않습니다.", id));
//		}
		ResultData actorCanModifyRd = articleService.actorCanMD(rq.getLoginedMemberId(), article);
		if (actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}

		return articleService.modifyArticle(id, title, body);
	}

	// 상세보기
	@RequestMapping("/usr/article/detail")
	public String showDetail(HttpServletRequest req, Model model, int id) {
		Rq rq = (Rq) req.getAttribute("rq");
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		model.addAttribute("article", article);
		return "usr/article/detail";
	}
}
