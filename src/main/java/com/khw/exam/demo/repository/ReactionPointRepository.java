package com.khw.exam.demo.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.khw.exam.demo.vo.ReactionPoint;

@Mapper
public interface ReactionPointRepository {

	@Select("""
			SELECT IFNULL(SUM(`point`), 0) AS sumReactionPoint,
					IFNULL(SUM(IF(`point` > 0, `point`, 0)), 0) AS goodReactionPoint,
					IFNULL(SUM(IF(`point` < 0, `point`, 0)), 0) AS badReactionPoint
				FROM reactionPoint
				WHERE relTypeCode = 'article'
				AND memberId = #{loginedMemberId}
				AND relId = #{id}
			""")
	ReactionPoint getReactionPoint(int loginedMemberId, int id);

	@Insert("""
			INSERT INTO reactionPoint
				SET regDate = NOW(),
					updateDate = NOW(),
					memberId = #{loginedMemberId},
					relTypeCode = 'article', 
					relId = #{id},
					`point` = 1;
			""")
	int doGoodReactionPoint(int loginedMemberId, int id);

	@Insert("""
			INSERT INTO reactionPoint
				SET regDate = NOW(),
					updateDate = NOW(),
					memberId = #{loginedMemberId},
					relTypeCode = 'article', 
					relId = #{id},
					`point` = -1;
			""")
	int doBadReactionPoint(int loginedMemberId, int id);

}