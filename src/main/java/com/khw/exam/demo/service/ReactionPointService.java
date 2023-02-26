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

	public ReactionPoint getReactionPoint(int loginedMemberId, String relTypeCode, int id) {
		return reactionPointRepository.getReactionPoint(loginedMemberId, relTypeCode, id);
	}

	public void doReactionPoint(int loginedMemberId, int id, String relTypeCode, int point) {
		reactionPointRepository.doReactionPoint(loginedMemberId, id, relTypeCode, point);
	}

	public void delReactionPoint(int loginedMemberId, String relTypeCode, int id) {
		reactionPointRepository.delReactionPoint(loginedMemberId, relTypeCode, id);
	}


}