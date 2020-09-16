package com.dev.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dev.model.MemberDAO;
import com.dev.model.MemberVO;

public class MemberUpdateController implements Controller {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("회원수정");
		
		//파라미터 변수에 저장
		String id = request.getParameter("id");
		
		//유효성 체크
		
		//VO에 넣기
		MemberVO member = new MemberVO();
		member.setId(id);
		
		//service
		MemberVO resultVO = MemberDAO.getInstance().selectOne(member);
		
		//조회결과를 저장후에 결과 페이지로 포워드
		request.setAttribute("update", resultVO);
		request.getRequestDispatcher("/member/memberUpdateOutput.jsp")
		       .forward(request, response);
	}

}
