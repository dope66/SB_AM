package com.khw.exam.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khw.exam.demo.vo.Article;

@Controller
public class UsrArticleController {

	private int lastArticleId;
	private List<Article> articles;

	public UsrArticleController() {
		this.lastArticleId = 0;
		this.articles = new ArrayList<>();
		makeTestData();

	}

	private Article writeArticle(String title, String body) {
		int id = lastArticleId + 1;
		Article article = new Article(id, title, body);
		articles.add(article);
		lastArticleId = id;
		return article;
	}

	private void makeTestData() {
		for (int i = 1; i <= 10; i++) {
			String title = "title" + i;
			String body = "body" + i;
			
			writeArticle(title, body);

		}

	}

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		Article article = writeArticle(title, body);
		return article;
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {

		return articles;
	}
}
