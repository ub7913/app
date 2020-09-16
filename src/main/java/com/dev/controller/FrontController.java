package com.dev.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FrontController
 */

/* @WebServlet(name="front", 
			urlPatterns = "*.do", //.do가 나올때는 /있으면 안됨
			initParams = 
			{
				@WebInitParam(name = "charset", value="UTF-8")
			})
*/
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	String charset = null;
	HashMap<String, Controller> list = null;
	
    @Override
	public void init(ServletConfig config) throws ServletException {
    	charset = config.getInitParameter("charset");
    	list = new HashMap<String, Controller>();
    	list.put("/memberInsert.do", new MemberInsertController());
    	list.put("/memberUpdate.do", new MemberUpdateController());
    	list.put("/memberSearch.do", new MemberSearchController());
    	list.put("/memberDelete.do", new MemberDeleteController());
    	list.put("/memberList.do", new MemberListController());
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(charset);
		String uri = request.getRequestURI();				// frontWeb/memberInsert.do
		String contextPath = request.getContextPath(); 		// frontWeb
		String path = uri.substring(contextPath.length()); 	// /memberInsert.do
		Controller subController = list.get(path);			// /memberInsert.do받아서 변수 하나 지정해서 리스트에 넣기 
		subController.execute(request, response);			// 지정된 변수를 실행 시키기(execute는 추상메소드라서 새로운 클래스에서 실행 시킬 수 있다)
	}

}
