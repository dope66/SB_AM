package com.khw.exam.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khw.exam.demo.repository.ArticleRepository;
import com.khw.exam.demo.vo.Article;
@Service
public class ArticleService {
	private ArticleRepository articleRepository;
	
	
	
	// 생성자
	@Autowired
	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository =articleRepository;
		articleRepository.makeTestData();
		
		
	}

	// 서비스 메서드
	public Article getArticle(int id) {
		return articleRepository.getArticle(id);
	}

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}


	public void modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id,title,body);

	}
	public List<Article> getArticles() {
		return articleRepository.getArticles();
	}

	public Article writeArticle(String title, String body) {
		
		return articleRepository.writeArticle(title,body);
	}
}
