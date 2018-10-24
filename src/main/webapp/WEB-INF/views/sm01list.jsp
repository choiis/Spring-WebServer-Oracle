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
var user_code = {};
	$(document).ready(function() {

		user_code = gfn_getCommCode("U001");
		
		showSM01List();
		// 찾기 버튼 클릭
		$("#btn_findText_button").on("click", function(e) {
			// 검색 함수
			findSM01();
		});
		
		$("#findText").on("keyup", function(key) {
			if(key.keyCode == 13) {//키가 13이면 실행 (엔터는 13)
				// 검색 함수
				findSM01();
			}
		});
		
		// 조회(페이지 버튼)
		$(document).on("click", "a[name='page_move']", function(e) {
			e.preventDefault();
			var page = $(this).attr('value');
			showSM01List(page);
		});
		
		// 조회(이전 버튼)
		$(document).on("click", "a[name='page_prev']", function(e) {
			e.preventDefault();
			var page = $("#startPage").attr('value');
			showSM01List(Number(page) - 10);
		});
		
		// 조회(다음 버튼)
		$(document).on("click", "a[name='page_next']", function(e) {
			e.preventDefault();
			var page = $("#startPage").attr('value');
			var maxPage = $("#maxPage").attr('value');
			if(Number(page) + 10 > maxPage) {
				showSM01List(maxPage);
			} else {
				showSM01List(Number(page) + 10);
			}
		});
	});
	
	// 검색 함수
	function findSM01() {
		
		if(gfn_isNull($("#findText").val())) {
			alert("검색조건 입력하세요");
			return false;
		}
		
		var sendData = {
        	"findText" : $("#findText").val(),
        	"selection" : $("#likeSelect").val(),
        	"nowPage" : 0
        };
		
		gfn_ajax("sm01showFind.do","POST" , sendData , function(data) {
			var html = "";
			
			$.each(data.list, function(index, item) {
				html += '<tr>';
	            html += '<td><a href="/common/sm01show.do?userid='+
	            item.userid +'">' +  item.username + '</a></td>';
	            html += '<td >' + item.brth + '</td>';
	            html += '<td >' + item.regdate + '</td>';
	            html += '<td >' + item.phone + '</td>';
	            html += '<td >' + item.email + '</td>';
	            html += '<td >' + gfn_getCommCodeNm(user_code,item.usertype) + '</td>';
	            html += '</tr>';
			});
			
	        $("#sb01viewTbody").html(html);
	    	 // 페이징 함수 호출
	        gfn_paging(data.nowPage, data.size , "#pagenation", "page_move");
		});
	}
	
	function showSM01List(nowPage) {
		console.log("showSM01List");
		
		if(!gfn_isNull(nowPage)) {
			page = nowPage;
		} else {
			page = 1;
		}
		
		var sendData = {
			"nowPage" : page
		};
		
		gfn_ajax("sm01select.do","POST" , sendData , function(data) {

			var html = "";
			$.each(data.list, function(index, item) {
				html += '<tr>';
	            html += '<td><a href="/common/sm01show.do?userid='+
	            item.userid +'">' +  item.username + '</a></td>';
	            html += '<td >' + item.brth + '</td>';
	            html += '<td >' + item.regdate + '</td>';
	            html += '<td >' + item.phone + '</td>';
	            html += '<td >' + item.email + '</td>';
	            html += '<td >' + gfn_getCommCodeNm(user_code,item.usertype) + '</td>';
	            html += '</tr>';
			});
			
	        $("#sm01viewTbody").html(html);
	        // 페이징 함수 호출
	        gfn_paging(data.nowPage, data.size , "#pagenation", "page_move");
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
		<table border = "1">
		<colgroup>
		<col width="85">
		<col width="85">
		<col width="115">
		<col width="85">
		<col width="150">
		</colgroup>
		
		<thead>
			<tr>
				<th scope="col">이름</th>
				<th scope="col">생년월일</th>
				<th scope="col">가입일자</th>
				<th scope="col">핸드폰</th>
				<th scope="col">이메일</th>
				<th scope="col">등급</th>
			</tr>
		</thead>
		<tbody id="sm01viewTbody">
		
		</tbody>
		</table>
		<div id="pagenation">
		
		</div>
	</section>
	<p>
	<footer> <jsp:include page="footer.jsp" /> </footer>
</body>
</html>
