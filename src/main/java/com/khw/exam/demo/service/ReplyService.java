package com.khw.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khw.exam.demo.repository.ReplyRepository;
import com.khw.exam.demo.util.Utility;
import com.khw.exam.demo.vo.ResultData;

@Service
public class ReplyService {
	private ReplyRepository replyRepository;
	@Autowired
	public ReplyService(ReplyRepository replyRepository) {
		this.replyRepository = replyRepository;
	}

	public ResultData<Integer> writeReply(int loginedMemberId, String relTypeCode, int relId, String body) {
		// TODO Auto-generated method stub
		
		replyRepository.writeReply(loginedMemberId,relTypeCode,relId,body);
		
		int id =replyRepository.getLastInsertId();
		
		return ResultData.from("S-1", Utility.f("%d번 댓글생성", id),"id",id);
	}
}
