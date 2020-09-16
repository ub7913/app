<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script>
function inputCheck() {
	//id, pw 필수입력 체크
	if(frm.id.value == "") {
		window.alert("id 입력");
		frm.id.focus();
		return false;
	}
	if(frm.pw.value == "") {
		alert("pw 입력");
		frm.pw.focus();
		return false;
	}
	//job(select 태그) 선택되었는지 확인
	if(frm.job.value == "") {
	//if(frm.job.selectedIndex > 0) {}
		alert("job선택");
		frm.job.focus();
		return false;
	}
	//radio, checkbox
	var gender =
		document.querySelectorAll("[name='gender']:checked").length;
	if(gender == 0) {
		alert("성별 적어도 하나는 선택");
		return false;
	}
	//회원가입폼 제출
	//frm.submit();
	return true;
}
</script>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script>
	$(function(){
		//초기화
		$("[name=gender]").val(["${member.gender}"]);//el은 알아서 null 체크 하기 때문에 null이면 에러 없이 null값이 들어 간다.
		$("[name=hobby]").val("${member.hobby}".replace("[","").replace("]","").split("/")); //read, ski, ..이렇게 나오게 해야 함 , 그래서 배열로 바꿔주는 함수 split을 쓴다.()안에는 배열 안에서 구분 할 수 있게끔 하는 구분자를 쓴다.
														  				//arr = "ski,read,ddd".replace("[","").replace("]","").split(",")  == arr = "ski,read,ddd".replace(/\[|\]/g,"").split(",")	
		$("[name=mailyn]").val(["${member.mailyn}"]);
		$("#frm [name=job]").val("${member.job}");
	});
</script>
</head>
<body>
<h3 class="page_title">회원수정</h3>

${error}
<form action="${pageContext.request.contextPath}/memberSearch.do"> <!-- http://localhost/memberSearch.do -->
	<input type="hidden" name="job" value="update">
	id : <input name="id">
	<button>검색</button>	
</form>

<c:if test="${not empty member}">
<h3>검색결과</h3>
<div class="regist">
 	<!-- /frontWeb/memberInsert.do or ../memberInsert.do -->
	<form method="post" name="frm" id="frm" 
		  action="${pageContext.request.contextPath}/memberUpdate.do" 
		  onsubmit="return inputCheck()">
	<div>
		<label>ID </label>
		<input type="text" name="id" value="${member.id}" readonly="readonly">
	</div>
	<div>
		<label>PW </label>
		<input type="password" name="pw" value="${member.pw}">
	</div>
	<div>
		<label>성별</label>
		<input type="radio" name="gender" value="male">남
		<input type="radio" name="gender" value="female">여
	</div>
	<div>
		<label>직업</label>
		<select name="job" id="job" size="4">
			<option value="">선택</option>
			<option value="professor">교수</option>
			<option value="doctor">의사</option>
			<option value="salesperson">판매원</option>
			<option value="student">학생</option>
		</select>	
	</div>
	<div>
		<label for="reason">가입동기</label>
		<textarea id="reason" name="reason">${member.reason}</textarea>
	</div>
	<div>
		<label>메일수신여부</label>
		<input type="checkbox" name="mailyn" value="y"></input>
	</div>
	<div>
		<label for="hobby">취미</label>
		<input type="checkbox" name="hobby" value="read">독서
		<input type="checkbox" name="hobby" value="game">게임
		<input type="checkbox" name="hobby" value="ski">스키
	</div>
	<div>
		<button type="reset" name="reset">초기화</button>
		<button>등록</button>
		<!-- <button type="button" name="submit" onclick="inputCheck()">등록</button> -->
	</div>
</form>
</div>
</c:if>
</body>
</html>