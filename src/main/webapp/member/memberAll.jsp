<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>memberAll</title>

</head>
<body>
	<h3>회원 전체조회</h3>
	<ul class="search">
		<li>메일수신여부</li>
		<li>성별</li>
		<li><button type="button">검색</button></li>
	</ul>
	<table border="1" id="members">
		<thead>
			<tr>
				<th>ID</th>
				<th>PW</th>
				<th>JOB</th>
				<th>MAILYN</th>
				<th>GENDER</th>
				<th>REASON</th>
				<th>HOBBY</th>
				<th>REGDATE</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="member"> 
			<tr>
				<td><a href="memberSelect.jsp">${member.getId()}</a></td>
				<td>${member.getPw()}</td>
				<td>${member.job}</td>
				<td>
					<c:if test="${member.mailyn == 'y'}">
						<button type="button">메일발송</button>
					</c:if>
				</td>
				<td>${member.gender}</td>
				<td>${member.reason}</td>
				<td>${member.hobby}</td>
				<td>
					<fmt:parseDate value="${member.regdate}" pattern="yyyy-MM" var="parseDate"/>
					<fmt:formatDate value="${parseDate}" pattern="yyyy/MM"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>