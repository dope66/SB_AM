package com.khw.exam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khw.exam.demo.service.ArticleService;
import com.khw.exam.demo.util.Utility;
import com.khw.exam.demo.vo.Article;
import com.khw.exam.demo.vo.ResultData;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsrArticleController {
	
	
	private ArticleService articleService;
	
	// 의존성 주입
	@Autowired
	public UsrArticleController (ArticleService articleService) {
		this.articleService=articleService;
		
	}
	//액션메서드
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData<Article> doAdd(HttpSession httpSession ,String title, String body) {
		// 로그인 안했는데 게시판 작성 안되게 하는거
		if(httpSession.getAttribute("loginedMemberId") ==null) {
			return ResultData.from("F-A", "로그인 후 이용해주세요");
		}
		int loginedMemberId = (int)httpSession.getAttribute("loginedMemberId");
		
		if(Utility.empty(title)){
			return ResultData.from("F-1","제목을 입력해주세요");
		}
		if(Utility.empty(body)){
			return ResultData.from("F-2","내용을 입력해주세요");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(loginedMemberId,title, body);
		int id =(int)writeArticleRd.getData1();
		Article article = articleService.getArticle(id);
		
		return ResultData.from(writeArticleRd.getResultCode(),writeArticleRd.getMsg(),article);
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public ResultData<List<Article>> getArticles() {
		List<Article> articles = articleService.getArticles();
		
		return ResultData.from("S-1","게시물 리스트",articles);
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(HttpSession httpSession,int id) {
		
		if(httpSession.getAttribute("loginedMemberId") ==null) {
			return ResultData.from("F-A", "로그인 후 이용해주세요");
		}
		
		Article article = articleService.getArticle(id);
		if (article == null) {
			return ResultData.from("F-1",Utility.f("%d 번 게시글은 존재하지 않습니다.", id));
		}
//		articles.remove(article);
		articleService.deleteArticle(id);
		return ResultData.from("S-1",Utility.f("%d 번 게시글을 삭제했습니다.", id),id);
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public Object doModify(HttpSession httpSession , int id, String title, String body) {
		if(httpSession.getAttribute("loginedMemberId") ==null) {
			return ResultData.from("F-A", "로그인 후 이용해주세요");
		}
		Article article = articleService.getArticle(id);
		if (article == null) {
			return id + "번 게시글은 존재하지 않습니다.";
		}
		articleService.modifyArticle(id, title, body);
		return article;
	}
	// 상세보기
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(int id) {
		/// 전에는 Object 였음 result 전에는
		Article article = articleService.getArticle(id);
		if (article == null) {
			return ResultData.from("F-1",Utility.f("%d번 게시글은 존재하지 않습니다.", id));
//			return id + "번 게시글은 존재하지 않습니다.";
		}
		return ResultData.from("S-1",Utility.f("%d번 게시글 입니다.", id),article);
	}
}
