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
var state_code = {};
var product_code = {};
	$(document).ready(function() {

		state_code = gfn_getCommCode("P001");
		product_code = gfn_getCommCode("P002");

		$('#brth').datepicker({
			dateFormat: 'yymmdd',	
			numberOfMonths : 1,
			changeMonth : true,
			changeYear : true
		});
	
		
		fn_PreSave = function() {
			
			if(!gfn_isKor($("#username").val())) {
				alert("이름엔 한글만 입력하세요");
				return false;
			}
			/*
			if(!gfn_isPhoneNumber($("#phone").val())) {
				alert("핸드폰번호 형식에 맞게 입력하세요");
				return false;
			}
			*/
			if(!gfn_isEmail($("#email").val())) {
				alert("이메일 형식에 맞게 입력하세요");
				return false;
			}
			
			if(!gfn_isNumber($("#brth").val())) {
				alert("생년월일에 숫자만 입력하세요");
				return false;
			}
			
			if(!gfn_IsImage($("#fileInput").val())) {
				alert("이미지 파일만 입력하세요");
				return false;
			}
		
			return true;
		}
		
		// 저장 버튼을 클릭할때 이벤트 발생
		$("#save").on("click", function(e) {

			if(!fn_PreSave()) {
				return ;
			}

			var form = $("#form")[0];  
			var formData = new FormData(form);

		    $.ajax({
		    	cache : false,
		        url : "/sm01update", // 요기에
		        processData: false,
		        contentType: false,
		        type : 'POST', 
		        data : formData, 
		        success : function(data) {

				}, // success 
		        error : function(data, status, error) {
		        	var errorData = JSON.parse(data.responseText);
			    	alert(errorData.errorCode + " " + errorData.errorMsg);
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
		<div id="container_demo">
			<div id="wrapper">
				<div id="sb01insert" class="animate form">
					<div id="login" class="animate form">
						<h4>회원 정보를 입력해 주세요</h4>
						<form id="form" name="updateform" method="post" enctype="multipart/form-data" action="/sm01update">
							<input type="hidden" id="userid" name="userid" value="${sm01Vo.userid}"> <br>
							<br> 이름:<input	type="text" id="username" name="username" value="${sm01Vo.username}" maxlength="6">
							<br> 전화번호:<input type="text" id="phone" name="phone" value="${sm01Vo.phone}" maxlength="14">
							<br> 생년월일:<input type="text" id="brth" name="brth" value="${sm01Vo.brth}" maxlength="8">
							<br> 이메일:<input type="text" id="email" name="email" value="${sm01Vo.email}" maxlength="20">
							<br>
							<button id="save" type="button" >저장</button>
							<input id="fileInput" type="file" name="imgFile" />				
						</form>
						<img id="showTempImage" alt="" name="photo" src="/selectPhoto/${sm01Vo.userid}" height="170px" width="150px"/>
					</div>
				</div>
			</div>
		</div>
	</section>
	<p>
	<footer> <jsp:include page="footer.jsp" /> </footer>
</body>
</html>
