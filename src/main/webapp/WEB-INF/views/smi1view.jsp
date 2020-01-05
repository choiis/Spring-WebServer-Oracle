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

	$(document).ready(function() {

		$("#username").show();
		$("#brth").hide();
		$("#cellpbnum").hide();
		// 찾기 버튼 클릭
		$("#btn_find").on("click", function(e) {
			// 검색 함수
			findSM01();
		});
		
		$("#username").on("keyup", function(key) {
			if(key.keyCode == 13) {//키가 13이면 실행 (엔터는 13)
				// 검색 함수
				findSM01();
			}
		});
		
		$("#brth").on("keyup", function(key) {
			if(key.keyCode == 13) {//키가 13이면 실행 (엔터는 13)
				// 검색 함수
				findSM01();
			}
		});
		
		$("#cellpbnum").on("keyup", function(key) {
			if(key.keyCode == 13) {//키가 13이면 실행 (엔터는 13)
				// 검색 함수
				findSM01();
			}
		});
		
		$("#selection").on("change",function() {
			
			if($(this).val() == 0) {

				$("#username").show();
				$("#brth").hide();
				$("#cellpbnum").hide();
			} else if ($(this).val() == 1) {
				
				$("#username").hide();
				$("#brth").show();
				$("#cellpbnum").hide();
			} else if ($(this).val() == 2) {
				
				$("#username").hide();
				$("#brth").hide();
				$("#cellpbnum").show();
			}
			
		});
		
		// 팝업오픈
		$(document).on("click", "#sm01show", function(e) {
			e.preventDefault();
			var tr = $(this).parent().parent();
			var userid = tr.children("#userid").text();

			$("#userId").val(userid);
			var popTitle = "회원정보상세";
			var url = "sm01One/" + userid;	
			window.open(url, "", popTitle);
			
		});
		
	});
	
	// 검색 함수
	function findSM01() {
		
		var sendData = {
		};
		var selection = $("#selection").val();
		
		if(selection == 0) {
			sendData.username = $("#username").val();
		} else if (selection == 1) {
			sendData.brth = $("#brth").val();
		} else if (selection == 2) {
			sendData.cellpbnum = $("#cellpbnum").val();
		}
		
		gfn_ajaxRequestBody("smi1","POST" , sendData , function(data) {
			var html = "";
			$.each(data.list, function(index, item) {
				html += '<tr>';
				html += '<td><a id="sm01show">' +  item.username + '</a></td>';
	            html += '<td id="userid" style="display:none;">' + item.userid + '</td>';
	            html += '<td >' + item.brth + '</td>';
	            html += '<td >' + item.regdate + '</td>';
	            html += '<td >' + item.pfnum + "-" + item.pcnum + "-" + item.pbnum + '</td>';
	            html += '<td >' + item.email + '</td>';
	            html += '</tr>';
			});
			
	        $("#smi1viewTbody").html(html);
		});
	}

</script>
	<jsp:include page="banner.jsp" /> 
	<header>
	<h1 class="logo">
		<span class="word1">전국</span> <span class="word2">노래자랑</span>
	</h1>
	</header>
	<nav> <jsp:include page="sidebar.jsp" /> </nav>
	<section>
		<form id="form">
			<select id="selection">
				<option value="0" selected>이름</option>
				<option value="1">생일</option>
				<option value="2">전화번호뒷자리</option>
			</select>
			<input id="username" name="username" />
			<input id="brth" name="brth" />
			<input id="cellpbnum" name="cellpbnum" />
			<button id="btn_find" type="button" >검색</button>
    	</form>
		<table border = "1">
		<colgroup>
		<col width="85">
		<col width="85">
		<col width="115">
		<col width="85">
		</colgroup>
		
		<thead>
			<tr>
				<th scope="col">이름</th>
				<th scope="col">생년월일</th>
				<th scope="col">가입일자</th>
				<th scope="col">핸드폰</th>
				<th scope="col">이메일</th>
			</tr>
		</thead>
		<tbody id="smi1viewTbody">
		
		</tbody>
		</table>
	</section>
	<p>
	<footer> <jsp:include page="footer.jsp" /> </footer>
</body>
</html>
