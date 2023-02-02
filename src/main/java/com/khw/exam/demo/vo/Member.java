package com.khw.exam.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	private String loginId;
	private String loginPw;
	private String name;
	private String nickname;
	private String cellphoneNum;
	private String email;
}
