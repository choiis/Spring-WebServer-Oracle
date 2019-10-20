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

		fn_PreSave = function() {
			
			if(gfn_isNull($("#title").val())) {
				alert("제목을 입력하세요");
				return false;
			}
			
			if(gfn_isNull($("#text").val())) {
				alert("내용을 입력하세요");
				return false;
			}

			if(gfn_isNull($("input[name=sharefile]")[0].files[0])) {
				alert("파일을 입력해 주세요");
				return false;
			}
			
			return true;
		}
		
		// 저장 버튼을 클릭할때 이벤트 발생
		$("#btn_insert_button").on("click", function(e) {
			
			if(!fn_PreSave()) {
				return ;
			}
						
			// $("#form").submit();
        	var form = $('#form')[0];
 
        	// Create an FormData object 
        	var formdata = new FormData(form);
        
	        $.ajax({
	        	url:"/sf01",
	            type:"POST",
	            data: formdata,
	            processData: false,
	            contentType: false,
	            success:function(data) {
	            	if(data.result === 1) {
	            		alert("게시물 입력 완료");
		        		location.href = "/sf01page";		
	            	}
	            },
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
					<h4>등록할 파일 정보를 입력해 주세요</h4>
					<form id="form" method="post" enctype="multipart/form-data" action="/sf01">
						제목:<input type="text" id="title" name="title"> <br>
						내용<textarea id="text" name="text" rows="10" cols="40"></textarea>
						<button id="btn_insert_button" type="button" >저장</button>
						<input id="fileInput" type="file" name="sharefile" />
					</form>
				</div>
			</div>
		</div>
	</section>
	<p>
	<footer> <jsp:include page="footer.jsp" /> </footer>
</body>
</html>
