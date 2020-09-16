package com.dev.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dev.model.MemberDAO;
import com.dev.model.MemberVO;

public class MemberSearchController implements Controller {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("회원검색");
		//job에 따라서 포워드 페이지 지정
		String path = "memberSearch.jsp";
		String job = request.getParameter("job");
		if(job.equals("delete")) {
			path = "memberDelete.jsp";
		} else if (job.equals("update")){
			path = "memberUpdate.jsp";
		}
		//파라미터 변수에 저장
		String id = request.getParameter("id");
		
		//유효성 체크(id가 null인지 체크)
		if(id.isEmpty()) {
			request.setAttribute("error", "id를 입력해주세요");
			//search페이지로 포워드
			request.getRequestDispatcher("/member/"+path)
				   .forward(request, response);
			return;
		}
		
		//VO에 넣기
		MemberVO member = new MemberVO();
		member.setId(id);
		
		//서비스로직처리
		MemberVO resultVO = MemberDAO.getInstance().selectOne(member);
		
		
		//조회결과를 저장후에 결과 페이지로 포워드
		request.setAttribute("member", resultVO);
		if(job.equals("search")) {
			request.getRequestDispatcher("/member/memberSearchOutput.jsp")
			   .forward(request, response);
		} else {
			request.getRequestDispatcher("/member/"+path)
			   .forward(request, response);
		}
	}
}
