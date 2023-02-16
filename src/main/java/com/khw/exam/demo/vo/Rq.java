	package com.khw.exam.demo.vo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.khw.exam.demo.util.Utility;

import lombok.Getter;
@Component 
@Scope(value="request", proxyMode =ScopedProxyMode.TARGET_CLASS)
public class Rq {
	@Getter
	private int loginedMemberId;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;

	public Rq(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		this.session = req.getSession();

		int loginedMemberId = 0;
		if (session.getAttribute("loginedMemberId") != null) {
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
		}

		this.loginedMemberId = loginedMemberId;
		
		this.req.setAttribute("rq", this);
	}

	public void jsPrintHistoryBack(String msg)  {
		resp.setContentType("text/html; charset=UTF-8");

		print(Utility.jsHistoryBack(msg));
	}

	private void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId());
	}

	public void logout() {
		session.removeAttribute("loginedMemberId");
	}

	public String jsReturnOnView(String msg, boolean historyBack ) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", historyBack);
		return "usr/common/js";
	}
	// 해당 메서드는 Rq객체의 생성을 유도한다.
	// 편의를 위해서 BeforeActionInterceptor에서 호출해줘야함
	public void initOnBeforeActionInterceptor() {
		
	}

}
