package com.khw.exam.demo.vo;

import lombok.Getter;

public class ResultData<DT> {
	// S-1 성공, F-1 실패
	@Getter
	private String resultCode;
	@Getter
	private String msg;
	@Getter
	private DT data1;
	// ResultData 오버로딩
	public static <DT> ResultData<DT> from(String resultCode, String msg) {
		return from(resultCode, msg, null);
	}

	public static <DT> ResultData<DT> from(String resultCode, String msg, DT data1) {
		ResultData<DT> rd = new ResultData<>();
		rd.resultCode = resultCode;
		rd.msg = msg;
		rd.data1 = data1;

		return rd;
	}

	public boolean isSuccess() {
		return this.resultCode.startsWith("S-");
	}

	public boolean isFail() {
		return isSuccess() == false;
	}

}
