package com.khw.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khw.exam.demo.service.ArticleService;
import com.khw.exam.demo.service.ReactionPointService;
import com.khw.exam.demo.util.Utility;
import com.khw.exam.demo.vo.Article;
import com.khw.exam.demo.vo.ReactionPoint;
import com.khw.exam.demo.vo.ResultData;
import com.khw.exam.demo.vo.Rq;


@Controller
public class UsrReactionPointController {

	private ReactionPointService reactionPointService;
	private ArticleService articleService;
	private Rq rq;

	@Autowired
	public UsrReactionPointController(ReactionPointService reactionPointService, ArticleService articleService, Rq rq) {
		this.reactionPointService = reactionPointService;
		this.articleService = articleService;
		this.rq = rq;
	}
	
	@RequestMapping("/usr/reactionPoint/getReactionPoint")
	@ResponseBody
	public ResultData<ReactionPoint> getReactionPoint(int id, String relTypeCode) {
		
		Article article = articleService.getArticle(id);
		
		if(article == null) {
			return ResultData.from("F-1", "해당 게시물은 존재하지 않습니다");
		}
		
		ReactionPoint reactionPoint = reactionPointService.getReactionPoint(rq.getLoginedMemberId(), relTypeCode, id);
		
		return ResultData.from("S-1", "리액션 정보 조회 성공", "reactionPoint", reactionPoint);
	}
	
	@RequestMapping("/usr/reactionPoint/doReactionPoint")
	@ResponseBody
	public String doReactionPoint(int id, String relTypeCode, int point) {
		
		Article article = articleService.getArticle(id);
		
		if(article == null) {
			return Utility.jsHistoryBack("해당 게시물은 존재하지 않습니다");
		}
		
		ReactionPoint reactionPoint = reactionPointService.getReactionPoint(rq.getLoginedMemberId(), relTypeCode, id);
		
		if(reactionPoint.getSumReactionPoint() != 0) {
			reactionPointService.delReactionPoint(rq.getLoginedMemberId(), relTypeCode, id);
		}
		
		reactionPointService.doReactionPoint(rq.getLoginedMemberId(), id, relTypeCode, point);
		
		if(point == 1) {
			return Utility.jsReplace(Utility.f("%d번 글에 좋아요", id), Utility.f("../article/detail?id=%d", id));
		} else {
			return Utility.jsReplace(Utility.f("%d번 글에 싫어요", id), Utility.f("../article/detail?id=%d", id));
		}
	}
	
	@RequestMapping("/usr/reactionPoint/delReactionPoint")
	@ResponseBody
	public String delReactionPoint(int id, String relTypeCode, int point) {
		
		Article article = articleService.getArticle(id);
		
		if(article == null) {
			return Utility.jsHistoryBack("해당 게시물은 존재하지 않습니다");
		}
		
		ReactionPoint reactionPoint = reactionPointService.getReactionPoint(rq.getLoginedMemberId(), relTypeCode, id);
		
		if(reactionPoint.getSumReactionPoint() == 0) {
			return Utility.jsHistoryBack("취소할거 없음");
		}
		
		reactionPointService.delReactionPoint(rq.getLoginedMemberId(), relTypeCode, id);
		
		if(point == 1) {
			return Utility.jsReplace(Utility.f("%d번 글에 좋아요 취소", id), Utility.f("../article/detail?id=%d", id));
		} else {
			return Utility.jsReplace(Utility.f("%d번 글에 싫어요 취소", id), Utility.f("../article/detail?id=%d", id));
		}
	}
}