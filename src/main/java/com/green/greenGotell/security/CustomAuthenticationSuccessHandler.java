package com.green.greenGotell.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//config내부에서 메소드빈으로 생성
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	
	//RequestCache(리퍼럴 역할): 사용자가 인증되지 않았던 페이지에 대한 정보를 저장하는 객체//인증성공 후, 저장된 요청을 복원하여 해당페이지로 전환
	private final RequestCache requestCache=new HttpSessionRequestCache();
	
	//RedirectStrategy: 리다이렉트로 로직을 구현하는데 사용:
	//인증, 인가 과정에서 특정 상황에 따라 사용자를 적절한 페이지로 리다이렉트 하기위해
	//로그인 성공(실패, 로그아웃)후 특정 페이지로 리다이렉트 할때
	private final RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();
	
	//private static final String DEFAULT_SECCESS_URL="/";

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
			response.sendRedirect("/");
		//로그인 페이지로 보내는 방법이 2가지가 있어요
		//클라이언트가 자의적으로 로그인 버튼을 눌러서 이동하는 방법
		//인증이 안되어 있는데 인증이 필요한 기능을 요청하면 -> 시큐리티가 리다이렉트 하여 페이지를 이동시킬때(인증이 필요하기 때문)
		//2번째의 경우 인증이 성공하면 최초 요청한 페이지로 이동하면 됩니다.
		
//		clearAuthenticationAttributes(request);//인증 실패 또는 인증과 관련된 메세지와 상태정보가 이후 요청에 영향을 미치지 않도록 제거
//		String targetUrl=getDefaultTargetUrl();// 기본 타겟 URL을 가져옵니다.
//		SavedRequest savedRequest=requestCache.getRequest(request, response); // 이전에 저장된 요청을 가져옵니다.
//		System.out.println("savedRequest 이거임:"+savedRequest);
//		
//	 	String prevPage=(String) request.getSession().getAttribute("prevPage"); //이전 페이지 URL을 세션에서 가져옵니다.
//	 	System.out.println("prevPage 이거임:"+prevPage);
//		
//		if(savedRequest !=null && !savedRequest.getRedirectUrl().contains("login")) {
//			targetUrl=savedRequest.getRedirectUrl().split("[?]")[0]; // 저장된 요청 URL을 타겟 URL로 설정합니다.
//		}else if(prevPage !=null) {
//			targetUrl=prevPage; // 이전 페이지를 타겟 URL로 설정합니다.
//			request.getSession().removeAttribute("prevPage"); // 세션에서 이전 페이지 정보를 제거합니다.
			
		}
		
//		System.out.println("targetUrl 최종 이동할 페이지: "+targetUrl);
//	 	
//	 	redirectStrategy.sendRedirect(request, response, targetUrl); // 최종 타겟 URL로 리다이렉트합니다.
//	}

}