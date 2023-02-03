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
	public Article doAdd(String title, String body) {
		int id = articleService.writeArticle(title, body);
		Article article = articleService.getArticle(id);
		return article;
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {

		return articleService.getArticles();
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = articleService.getArticle(id);
		if (article == null) {
			return id + "번 게시글은 존재하지 않습니다.";
		}
//		articles.remove(article);
		articleService.deleteArticle(id);
		return id + "게시물을 삭제하였습니다.";
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public Object doModify(int id, String title, String body) {

		Article article = articleService.getArticle(id);
		if (article == null) {
			return id + "번 게시글은 존재하지 않습니다.";
		}
		articleService.modifyArticle(id, title, body);
		return article;
	}
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData getArticle(int id) {
		/// 전에는 Object 였음 result 전에는
		Article article = articleService.getArticle(id);
		if (article == null) {
			return ResultData.from("F-1",Utility.f("%d번 게시글은 존재하지 않습니다.", id));
//			return id + "번 게시글은 존재하지 않습니다.";
		}
		return ResultData.from("S-1",Utility.f("%d번 게시글 입니다.", id),article);
	}
}
