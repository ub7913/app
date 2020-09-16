package com.dev.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dev.model.MemberDAO;
import com.dev.model.MemberVO;

public class MemberInsertController implements Controller {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("회원등록");
		//파라미터를 VO에 담기
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String gender = request.getParameter("gender");
		String job = request.getParameter("job");
		String reason = request.getParameter("reason");
		String mailyn = request.getParameter("mailyn");
		String[] hobby = request.getParameterValues("hobby");
		//유효성 체크(필요시 넣기)
		
		MemberVO member = new MemberVO();
		member.setId(id);
		member.setPw(pw);
		member.setGender(gender);
		member.setJob(job);
		member.setReason(reason);
		member.setMailyn(mailyn);
		member.setHobby(Arrays.toString(hobby));
		
		//서비스 처리
		int r = MemberDAO.getInstance().insert(member);
		
		//결과저장
		request.setAttribute("cnt", r);
		
		//뷰페이지로 이동
		request.getRequestDispatcher("/member/memberInsertOutput.jsp")
		   .forward(request, response);
		
	}

}
