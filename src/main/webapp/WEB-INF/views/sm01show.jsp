<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<title>전국노래자랑</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
<link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" />
<script src="<c:url value='/resources/js/common.js'/>" charset="utf-8"></script>
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
</head>
<body>
<script type="text/javascript">
	// 페이지 로딩이 완료되고, jQuery 스크립트 실행된다
	$(document).ready(function() {

		// 메일 전송을 클릭할때 이벤트 발생
		$("#mail_btn").on("click", function(e) {
		
			var sendData = {
	        	"email" : $("#email").val(),
	        	"title" : $("#title").val(),
	        	"contents" : $("#contents").val()
	        };
		
			gfn_ajax("sendMail.do","POST" , sendData , function(data) {
				if(data == 1011) {
					alert("메일 전송 실패!");
				} else {
					alert($("#email").val() + " 님께 메일이 전송되었습니다!");	
				}
			});
		});
	});
</script>
<jsp:include page="banner.jsp" /> 
	<header>
	<h1 class="logo">
		<span class="word1">전국</span> <span class="word2">노래자랑</span>
	</h1>
	</header>
	<nav> <jsp:include page="sidebar.jsp" /> </nav>
	<section>
	<div class="container">
		<header>
			<h1>
				회원정보 상세
			</h1>
		</header>
			<p>${sM01Vo.userid}</p>
		<div>
			<p>이름 : ${sM01Vo.username}</p>
			<p>생년월일 : ${sM01Vo.brth}</p>
		</div>
	
		<div>
			<p>가입일 : ${sM01Vo.regdate}</p>
			<p>전화번호 : ${sM01Vo.phone}</p>
			
			<img id="showTempImage" alt="" src="/selectPhoto/${sM01Vo.userid}" height="170px" width="150px"/>
			<form id="form" method="post" action="/sendMail.do">
			이메일 :<input type="text" id="email" name="email" value='${sM01Vo.email}' disabled><br>
			이메일 제목 : <input	type="text" id="title" name="title"><br>
			이메일 내용 : <textarea id="contents" name="contents" rows="10" cols="60"></textarea>
			<button type="button" id="mail_btn">메일전송</button>
			</form>

		</div>
	</div>
	</section>
	<p>
	<footer> <jsp:include page="footer.jsp" /> </footer>
</body>
</html>
