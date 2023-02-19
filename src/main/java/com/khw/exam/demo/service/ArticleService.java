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
		this.articleRepository = articleRepository;
	}

	// 서비스 메서드
	public Article getArticle(int id) {
		return articleRepository.getArticle(id);
	}

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public void modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
	}

	public List<Article> getArticles(int boardId, String searchKeywordTypeCode, String searchKeyword, int itemsInAPage,
			int page) {

		int limitStart = (page - 1) * itemsInAPage;
		return articleRepository.getArticles(boardId, searchKeywordTypeCode, searchKeyword, limitStart, itemsInAPage);
	}

	public ResultData<Integer> writeArticle(int memberId, int boardId, String title, String body) {
		articleRepository.writeArticle(memberId, boardId, title, body);

		int id = articleRepository.getLastInsertId();

		return ResultData.from("S-1", Utility.f("%d 번 게시물이 생성되었습니다.", id), "id", id);
	}

//	public ResultData<Article> actorCanModify(int loginedMemberId, Article article) {
//		if(loginedMemberId != article.getMemberId()) {
//			return ResultData.from("F-B","해당 게시물에 권한이 없습니다.");
//		}
//		
//		return ResultData.from("S-1","수정 가능");
//	}
//	
//	public ResultData<Article> actorCanDelete(int loginedMemberId, Article article) {
//		if(article== null) {
//			return ResultData.from("F-1",Utility.f("%d번 게시물은 존재하지않습니다."));
//		}
//		
//		
//		if(loginedMemberId != article.getMemberId()) {
//			return ResultData.from("F-B","해당 게시물에 권한이 없습니다.");
//		}
//		
//		return ResultData.from("S-1","삭제");
//	}
	public ResultData actorCanMD(int loginedMemberId, Article article) {
		if (article == null) {
			return ResultData.from("F-1", Utility.f("%d번 게시물은 존재하지않습니다."));
		}
		if (loginedMemberId != article.getMemberId()) {
			return ResultData.from("F-B", "해당 게시물에 권한이 없습니다.");
		}

		return ResultData.from("S-1", "수정가능");
	}

	public Article getForPrintArticle(int loginedMemberId, int id) {
		Article article = articleRepository.getForPrintArticle(id);
		actorCanChangeData(loginedMemberId, article);
		return article;
	}

	private void actorCanChangeData(int loginedMemberId, Article article) {
		if (article == null) {
			return;
		}

		ResultData actorCanChangeDataRd = actorCanMD(loginedMemberId, article);
		article.setActorCanChangeData(actorCanChangeDataRd.isSuccess());

	}

	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword) {

		return articleRepository.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);
	}

	public ResultData<Integer> increaseHitCount(int id) {
		int affectedRowsCount =articleRepository.increaseHitCount(id);
		
		if(affectedRowsCount ==0) {
			return ResultData.from("F-1", "해당 게시물은 존재하지않습니다.","affectedRowsCount",affectedRowsCount);
		}
		return ResultData.from("S-1", "조회수 증가.","affectedRowsCount",affectedRowsCount);
	}
}
