<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.singer.vo.SP01Vo"%>
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

		showSV01One();
	});

	function showSV01One() {
		
		var sendData =  {
	    	"seq" : parseInt($("#seq").val()),
	    	"hit" : parseInt($("#hit").val())
	    };
		
		gfn_ajax("sv01selectOne.do","POST" , sendData , function(data) {
			debugger;
		});
	};
	
</script>
 <jsp:include page="banner.jsp" /> 
	<header>
	<h1 class="logo">
		<span class="word1">전국</span> <span class="word2">노래자랑</span>
	</h1>
	</header>
	<nav> <jsp:include page="sidebar.jsp" /> </nav>
	<section>
		<div>
		<form id="form_delete" method="post" action="/common/sp01delete.do">
			<input type="hidden" id="seq" name="seq" value="${seq}" />
			<input type="hidden" id="hit" name="hit" value="${hit}" />
    	</form>
		</div>
		<div id="showDiv">
			
				
    	
    	</div>
		
		<div class="container">
		
		
		</div>
	</section>
	<p>
	<footer> <jsp:include page="footer.jsp" /> </footer>
</body>
</html>
