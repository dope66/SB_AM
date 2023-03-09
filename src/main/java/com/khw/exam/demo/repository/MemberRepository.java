package com.khw.exam.demo.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.khw.exam.demo.vo.Member;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MemberRepository {
	
	@Insert("""
			INSERT INTO `member`
			SET regDate = NOW(),
			updateDate =NOW(),
			loginId =#{loginId},
			loginPw =#{loginPw},
			`name`=#{name},
			nickname =#{nickname},
			cellphoneNum =#{cellphoneNum},
			email =#{email}
			""")
	public void doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);
	
	@Select("SELECT LAST_INSERT_ID()")
	public int getLastInsertId();
	@Select("""
			SELECT *
			FROM `member`
			WHERE id = #{id}
			
			""")
	public Member getMemberById(int id);
	@Select("""
			
			SELECT *
			FROM `member`
			WHERE loginId=#{loginId}
			
			""")
	public Member getMemberByLoginId(String loginId);
	@Select("""
			
			SELECT*
			FROM `member`
			WHERE name=#{name}
			AND email =#{email}
			""")
	public Member getMemberByNameEmailnId(String name, String email);
	@Update("""
			<script>
				UPDATE `member`
					<set>
						updateDate = NOW(),
						<if test="nickname != null">
							nickname = #{nickname},
						</if>
						<if test="cellphoneNum != null">
							cellphoneNum = #{cellphoneNum},
						</if>
						<if test="email != null">
							email = #{email}
						</if>
					</set>
					WHERE id = #{loginedMemberId}
				</script>
			""")
    void doModify(int loginedMemberId, String nickname, String cellphoneNum, String email);
	@Update("""
			UPDATE `member`
				SET loginPw = #{loginPw}
				WHERE id = #{loginedMemberId}
			""")
	void doPassWordModify(int loginedMemberId, String loginPw);
}
