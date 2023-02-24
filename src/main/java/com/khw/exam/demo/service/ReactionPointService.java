package com.khw.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khw.exam.demo.repository.ReactionPointRepository;
import com.khw.exam.demo.vo.ReactionPoint;

@Service
public class ReactionPointService {
	private ReactionPointRepository reactionPointRepository;

	@Autowired
	public ReactionPointService(ReactionPointRepository reactionPointRepository) {
		this.reactionPointRepository = reactionPointRepository;
	}

	public ReactionPoint getReactionPoint(int loginedMemberId, int id) {
		return reactionPointRepository.getReactionPoint(loginedMemberId, id);
	}

	public int doGoodReactionPoint(int loginedMemberId, int id) {
		return reactionPointRepository.doGoodReactionPoint(loginedMemberId, id);
	}

	public int doBadReactionPoint(int loginedMemberId, int id) {
		return reactionPointRepository.doBadReactionPoint(loginedMemberId, id);
	}


}