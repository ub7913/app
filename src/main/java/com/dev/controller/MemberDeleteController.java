package com.dev.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dev.model.MemberDAO;
import com.dev.model.MemberVO;

public class MemberDeleteController implements Controller {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("회원삭제");
		
		//파라미터 변수에 저장
		String id = request.getParameter("id");
		
		//유효성 체크
		if(id.isEmpty()) {
			request.setAttribute("error", "id를 입력해주세요");
			//search페이지로 포워드
			request.getRequestDispatcher("/member/memberDelete.jsp")
				   .forward(request, response);
		}
		
		//VO에 넣기
		MemberVO member = new MemberVO();
		member.setId(id);
		
		//service로직처리
		MemberDAO.getInstance().delete(member);
		
		//조회 결과 저장
		//결과 페이지로 포워드
		request.getRequestDispatcher("/member/memberDeleteOutput.jsp")
		   .forward(request, response);
	}

}
