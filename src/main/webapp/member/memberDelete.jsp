<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>memberDelete.jsp</title>
</head>
<body>
<h3>회원삭제</h3>
${error}
<form action="${pageContext.request.contextPath}/memberSearch.do"> <!-- http://localhost/memberSearch.do -->
	<input type="hidden" name="job" value="delete">
	id : <input name="id">
	<button>검색</button>	
</form>
<!-- 검색결과 출력 -->
<c:if test="${not empty member}">
<h3>검색결과 출력</h3>
	${member.job}<br>
	${member.gender}<br>
	${member.id}
	<form action="${pageContext.request.contextPath}/memberDelete.do">
		id: <input name="id" value="${member.id}" type="hidden">
		<button>삭제</button>
	</form>
</c:if>
</body>
</html>