package com.khw.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.khw.exam.demo.vo.Reply;

@Mapper
public interface ReplyRepository {
	@Insert("""
			INSERT INTO reply
				SET regDate = NOW(),
					updateDate = NOW(),
					memberId = #{loginedMemberId},
					relTypeCode = #{relTypeCode},
					relId = #{relId},
					`body` = #{body}
			""")
	void writeReply(int loginedMemberId, String relTypeCode, int relId, String body);
	@Select("""
			SELECT LAST_INSERT_ID();
			""")
	int getLastInsertId();
	@Select("""
			SELECT R.*, M.nickname AS writerName
				FROM reply AS R
				INNER JOIN `member` AS M
				ON R.memberId = M.id
				WHERE R.relTypeCode = #{relTypeCode}
				AND R.relId = #{id}
				ORDER BY R.id 
			""")
	List<Reply> getForPrintReplies(String relTypeCode, int id);

	@Select("""
			SELECT *
				FROM reply
				WHERE id = #{id}
			""")
	Reply getReply(int id);

	@Delete("""
			DELETE FROM reply
				WHERE id = #{id}
			""")
	void deleteReply(int id);
	@Select("""
   			SELECT R.*,M.nickname AS writerName
   				FROM reply AS R
   				INNER JOIN `member` AS M
   				ON R.memberId = M.id
   				WHERE R.id = #{id}
			""")
	Reply getReplyContent(int id);
	@Update("""
			UPDATE reply
				SET updateDate = NOW(),
					`body` = #{body}
				WHERE id = #{id}
			""")
	void modifyReply(int id, String body);
}
