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


	public ResultData<Article> modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id,title,body);
		Article article = getArticle(id);
		return ResultData.from("S-1", Utility.f("%d 번게시물 수정하였습니다.",id),"article",article);
	}
	public List<Article> getArticles() {
		return articleRepository.getArticles();
	}

	public ResultData<Integer> writeArticle(int memberId, String title, String body) {
		articleRepository.writeArticle(memberId,title,body);
		
		int id =articleRepository.getLastInsertId(); 
		
		return ResultData.from("S-1",Utility.f("%d 번 게시물이 생성되었습니다.",id),"id",id);
	}
	public ResultData<Article> actorCanModify(int loginedMemberId, Article article) {
		// TODO Auto-generated method stub
		if(loginedMemberId != article.getMemberId()) {
			return ResultData.from("F-B","해당 게시물에 권한이 없습니다.");
		}
		
		return ResultData.from("S-1","수정 가능");
	}
	public Article getForPrintArticle(int id) {
		return articleRepository.getForPrintArticle(id);
	}
}
