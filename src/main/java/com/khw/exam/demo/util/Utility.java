package com.khw.exam.demo.util;

import com.khw.exam.demo.vo.ResultData;

import java.text.SimpleDateFormat;

public class Utility {

	public static boolean empty(Object obj) {
//	
		if (obj == null) {
			return true;
		}
//		if(obj instanceof String == false) {
//			return true;
//		}
		String str = (String) obj;

		return str.trim().length() == 0;
	}

	public static String f(String format, Object... args) {
		return String.format(format, args);
	}
	/** 바로 뒤의 페이지로 이동*/
	public static String jsHistoryBack(String msg) {
		if (msg == null) {
			msg = "";
		}
		return Utility.f("""
							<script>
								const msg = '%s'.trim();
								if(msg.length > 0){
									alert(msg);
								}
								history.back()
							</script>
				""", msg);
	}
	/** 메세지 확인후 특정 주소로 이동*/
	public static String jsReplace(String msg, String uri) {
		if (msg == null) {
			msg = "";
		}
		if (uri == null) {
			uri = "";
		}
		return Utility.f("""
								<script>
									const msg = '%s'.trim();
									if(msg.length > 0){
										alert(msg);
									}
									location.replace('%s');
								</script>
				""", msg, uri);
	}
	public static String getDateStrLater(long seconds) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String dateStr = format.format(System.currentTimeMillis() + seconds * 1000);

		return dateStr;
	}
	// 난수 생성기( 암호화 )
	public static String getTempPassword(int length) {
		int index = 0;
		char[] charArr = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
				'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++) {
			index = (int) (charArr.length * Math.random());
			sb.append(charArr[index]);
		}

		return sb.toString();
	}
	public static String sha256(String base) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(base.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();

		} catch (Exception ex) {
			return "";
		}
	}


}
