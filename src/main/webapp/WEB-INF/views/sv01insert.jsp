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

		
			return true;
		}
		
		$("#btn_add_selection").on("click", function(e) {
			$('#voteTable > tbody:first').append('<tr><td><input width="300" id="content" name="content"></td><td><button name="delVote">삭제</button></td></tr>');
		});
		
		 //삭제 버튼
	    $(document).on("click","button[name=delVote]",function(){
	        var trTag = $(this).parent().parent();
	        trTag.remove(); //tr 테그 삭제  
	    });
		
		$("#btn_delete_selection").on("click", function(e) {
			$('#voteTable > tbody:last > tr:last').remove();
		});
		
		// 저장 버튼을 클릭할때 이벤트 발생
		$("#btn_insert_button").on("click", function(e) {
			
			if(!fn_PreSave()) {
				return ;
			}
						
			var sendData = {
				"title" : $("#title").val(),
				"text" : $("#text").val(),
				"multiselect" : $("#multiselect").val()
			};
			
			var list = [];
			
			var size = $("#voteTable tbody tr").find("#content").length;
			for(var i = 0 ; i < size ; i++) {
				var vo = {
					"idx" : i,
					"content" : $("#voteTable tbody tr").find("#content").eq(i).val() 
				};
				list.push(vo);
			}
			sendData.sv02Vos = list;
			
			gfn_ajaxRequestBody("sv01", "POST", sendData, function(data) {
				
				if(confirm("투표가 등록되었습니다!")) {
					location.href='/sv01page';
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
					<form id="form" method="post" action="/sv01insert.do">
						제목:<input type="text" id="title" name="title"> <br>
						내용<textarea id="text" name="text" rows="10" cols="40"></textarea>
						<br>
						<select id="multiselect" name="multiselect">
 							<option value = 0>단일선택</option>
  							<option value = 1>중복투표</option>
						</select>
						<br>
						<button id="btn_add_selection" type="button" >행 추가</button>
						<table id="voteTable" border="1" >
							<colgroup>
							<col width="250">
							<col width="50">
							</colgroup>
  							<tr>
					   		<th>내용</th>
					   		<th>삭제</th>
  							</tr>
  						<tbody>
  						</tbody>
						</table>
						
						<button id="btn_insert_button" type="button" >저장</button>
					</form>
				</div>
			</div>
		</div>
	</section>
	<p>
	<footer> <jsp:include page="footer.jsp" /> </footer>
</body>
</html>
