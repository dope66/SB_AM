package com.khw.exam.demo;

import com.khw.exam.demo.interceptor.BeforeActionInterceptor;
import com.khw.exam.demo.interceptor.NeedLoginInterceptor;
import com.khw.exam.demo.interceptor.NeedLogoutInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

	private BeforeActionInterceptor beforeActionInterceptor;
	private NeedLoginInterceptor needLoginInterceptor;

	private NeedLogoutInterceptor needLogoutInterceptor;
	@Autowired
	public MyWebMvcConfigurer(BeforeActionInterceptor beforeActionInterceptor,
			NeedLoginInterceptor needLoginInterceptor,
							  NeedLogoutInterceptor needLogoutInterceptor) {
		this.beforeActionInterceptor = beforeActionInterceptor;
		this.needLoginInterceptor = needLoginInterceptor;
		this.needLogoutInterceptor = needLogoutInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		InterceptorRegistration ir;
		ir = registry.addInterceptor(beforeActionInterceptor);
		ir.addPathPatterns("/**");
		ir.addPathPatterns("/favicon.ico");
		ir.excludePathPatterns("/resource/**");
		ir.excludePathPatterns("/error");
		
		
		ir = registry.addInterceptor(needLoginInterceptor);
		ir.addPathPatterns("/favicon.ico");
		ir.addPathPatterns("/usr/article/write");
		ir.addPathPatterns("/usr/article/doWrite");
		ir.addPathPatterns("/usr/article/doDelete");
		ir.addPathPatterns("/usr/article/modify");
		ir.addPathPatterns("/usr/article/doModify");
		ir.addPathPatterns("/usr/reactionPoint/getReactionPoint");
		ir.addPathPatterns("/usr/reatcionPoint/doReactionPoint");
		ir.addPathPatterns("/usr/reatcionPoint/delReactionPoint");
		ir.addPathPatterns("/usr/reply/doWrite");
		ir.addPathPatterns("/usr/reply/doDelete");
		ir.addPathPatterns("/usr/reply/doModify");
		ir.addPathPatterns("/usr/reply/getReplyContent");
		ir.addPathPatterns("/usr/member/doLogout");
		ir.addPathPatterns("/usr/member/myPage");
		ir.addPathPatterns("/usr/member/checkPassword");
		ir.addPathPatterns("/usr/member/doCheckPassword");
		ir.addPathPatterns("/usr/member/doModify");
		ir.addPathPatterns("/usr/member/modify");
		ir.addPathPatterns("/usr/member/passWordModify");
		ir.addPathPatterns("/usr/member/doPassWordModify");

		ir = registry.addInterceptor(needLogoutInterceptor);
		ir.addPathPatterns("/usr/member/login");
		ir.addPathPatterns("/usr/member/doLogin");
		ir.addPathPatterns("/usr/member/join");
		ir.addPathPatterns("/usr/member/doJoin");
	}
}