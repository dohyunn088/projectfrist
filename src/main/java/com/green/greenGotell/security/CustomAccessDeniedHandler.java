package com.green.greenGotell.security;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setContentType("text/html;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head>");
        out.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css'>");
        out.println("<style>");
        out.println(".modal { display: flex; align-items: center; justify-content: center; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); }");
        out.println(".modal-content { background: #fff; padding: 20px; border-radius: 15px; text-align: center; width: 50%; max-width: 400px;}");
        out.println(".close { cursor: pointer; float: right; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='modal'>");
        out.println("<div class='modal-content'>");
        out.println("<span class='close' onclick='closeModal()'>&times;</span>");
        out.println("<h2>접근 권한이 없습니다.</h2>");
        out.println("<p>잠시 후 메인 페이지로 이동합니다.</p>");
        out.println("</div>");
        out.println("</div>");
        out.println("<script>");
        out.println("function closeModal() { document.querySelector('.modal').style.display = 'none'; setTimeout(function(){ window.location.href = '/'; }, 500); }");
        out.println("document.addEventListener('DOMContentLoaded', function() { setTimeout(closeModal, 500); });");
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
	}

}
