package com.khw.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khw.exam.demo.service.ArticleService;
import com.khw.exam.demo.service.ReactionPointService;
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
	public ResultData<ReactionPoint> getReactionPoint(int id) {

		Article article = articleService.getArticle(id);

		if(article == null) {
			return ResultData.from("F-1", "해당 게시물은 존재하지 않습니다");
		}

		ReactionPoint reactionPoint = reactionPointService.getReactionPoint(rq.getLoginedMemberId(), id);

		return ResultData.from("S-1", "리액션 정보 조회 성공", "reactionPoint", reactionPoint);
	}

	@RequestMapping("/usr/reactionPoint/doGoodReactionPoint")
	@ResponseBody
	public ResultData<Integer> doGoodReactionPoint(int id) {

		Article article = articleService.getArticle(id);

		if(article == null) {
			return ResultData.from("F-1", "해당 게시물은 존재하지 않습니다");
		}

		int affectedRow = reactionPointService.doGoodReactionPoint(rq.getLoginedMemberId(), id);

		return ResultData.from("S-1", "좋아요 성공", "affectedRow", affectedRow);
	}

	@RequestMapping("/usr/reactionPoint/doBadReactionPoint")
	@ResponseBody
	public ResultData<Integer> doBadReactionPoint(int id) {

		Article article = articleService.getArticle(id);

		if(article == null) {
			return ResultData.from("F-1", "해당 게시물은 존재하지 않습니다");
		}

		int affectedRow = reactionPointService.doBadReactionPoint(rq.getLoginedMemberId(), id);

		return ResultData.from("S-1", "싫어요 성공", "affectedRow", affectedRow);
	}
}