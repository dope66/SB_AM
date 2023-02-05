package com.khw.exam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khw.exam.demo.repository.ArticleRepository;
import com.khw.exam.demo.util.Utility;
import com.khw.exam.demo.vo.Article;
import com.khw.exam.demo.vo.ResultData;
@Service
public class ArticleService {
	private ArticleRepository articleRepository;
	
	
	
	// 생성자
	@Autowired
	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository =articleRepository;
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

	public ResultData<Integer> writeArticle(String title, String body) {
		articleRepository.writeArticle(title,body);
		
		int id =articleRepository.getLastInsertId(); 
		
		return ResultData.from("S-1",Utility.f("%d 번 게시물이 생성되었습니다.",id),id);
	}
}
