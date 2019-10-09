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

		showSF01List();
		
		// 저장 버튼을 클릭할때 이벤트 발생
		$("#btn_write_button").on("click", function(e) {
			$("#write_form").submit();
			
		});
		
		// 찾기 버튼 클릭
		$("#btn_findText_button").on("click", function(e) {
			// 검색 함수
			findSF01();
		});
		
		
		$("#findText").on("keyup", function(key) {
			
			if(key.keyCode == 13) {//키가 13이면 실행 (엔터는 13)
				// 검색 함수
				findSF01();
			}
		});
		
		// 조회(페이지 버튼)
		$(document).on("click", "a[name='page_move']" , function(e) {
			e.preventDefault();
			var page = $(this).attr('value');
			showSF01List(page);
		});
		
		// 조회(이전 버튼)
		$(document).on("click", "a[name='page_prev']" , function(e) {
			e.preventDefault();
			var page = $("#startPage").attr('value');
			showSF01List(Number(page) - 10);
		});
		
		// 조회(다음 버튼)
		$(document).on("click", "a[name='page_next']" , function(e) {
			e.preventDefault();
			var page = $("#startPage").attr('value');
			var maxPage = $("#maxPage").attr('value');
			if(Number(page) + 10 > maxPage) {
				showSF01List(maxPage);
			} else {
				showSF01List(Number(page) + 10);
			}
		});
	});
	
	// 검색 함수
	function findSF01() {
		
		if(gfn_isNull($("#findText").val())) {
			alert("검색조건 입력하세요");
			return false;
		}
		
		var sendData = {
        	"findText" : $("#findText").val(),
        	"selection" : $("#likeSelect").val(),
        	"nowPage" : 0
        };
		
		gfn_ajax("sf01showFind.do","POST" , sendData , function(data) {
			var html = "";
			
			$.each(data.list, function(index, item) {
				html += '<tr>';
	            html += '<td scope="col" width="50">' + item.seq + '</td>';
	            html += '<td scope="col" width="50"><a href="/sf01show_detail/'+
	            item.seq +'">' + 
	            item.title + '</a></td>';
	            html += '<td scope="col" width="30">' + item.reply + '</td>';
	            html += '<td scope="col" width="100">' + item.userid + '</td>';
	            html += '<td scope="col" width="30">' + item.good + '</td>';
	            html += '<td scope="col" width="30">' + item.hit + '</td>';
	            html += '<td scope="col" width="70">' + gfn_dateFormat(item.regdate) + '</td>';
	            html += '</tr>';
			});
			
	        $("#SF01viewTbody").html(html);
	     	// 페이징 함수 호출
	        gfn_paging(data.nowPage, data.totCnt , "#pagenation", "page_move");
		});
	}
	
	function showSF01List(nowPage) {
		console.log("showSF01List");
		
		var page = 1;
		if(!gfn_isNull(nowPage)) {
			page = nowPage;
		}
		
		gfn_ajaxRest("sf01/" + page, "GET" , function(data) {

			var html = "";
			$.each(data.list, function(index, item) {
				html += '<tr>';
	            html += '<td scope="col" width="50">' + item.seq + '</td>';
	            html += '<td scope="col" width="50"><a href="/sf01show_detail/'+
	            item.seq +'">' + 
	            item.title + '</a></td>';
	            html += '<td scope="col" width="30">' + item.reply + '</td>';
	            html += '<td scope="col" width="100">' + item.userid + '</td>';
	            html += '<td scope="col" width="30">' + item.good + '</td>';
	            html += '<td scope="col" width="30">' + item.hit + '</td>';
	            html += '<td scope="col" width="70">' + gfn_dateFormat(item.regdate) + '</td>';
	            html += '</tr>';
			});
			
	        $("#SF01viewTbody").html(html);
	     	// 페이징 함수 호출
	        gfn_paging(data.nowPage, data.totCnt , "#pagenation", "page_move");
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
	<select id="likeSelect">
		<option value="1">제목</option>
		<option value="2">글쓴이</option>
	</select>
	<input type="text" id="findText" name="findText" maxlength="20">
	<input type="hidden" id="startPage" name="startPage">
	<input type="hidden" id="maxPage" name="maxPage">
	<button id="btn_findText_button" type="button" >게시물 검색</button>
		<div class="container">
		<table border = "1">
			<colgroup>
				<col width="50">
				<col width="100">
				<col width="30">
				<col width="100">
				<col width="30">
				<col width="30">
				<col width="70">
			</colgroup>
			<thead>
			<tr>
				<th scope="col">번호</th>
				<th scope="col">제목</th>
				<th scope="col">댓글</th>
				<th scope="col">글쓴이</th>
				<th scope="col">좋아요</th>
				<th scope="col">조회수</th>
				<th scope="col">등록일자</th>
			</tr>
			</thead>
			<tbody id="SF01viewTbody">
		
			</tbody>
		</table>
		<div id="pagenation">
		
		</div>
			<form id="write_form" action="/sf01insertPage" method="get">
			<button id="btn_write_button" type="button" >게시물 등록</button>
			</form>
		</div>
		
	</section>
	<p>
	<footer> <jsp:include page="footer.jsp" /> </footer>
</body>
</html>
