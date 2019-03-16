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

		showSM02List();
		
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
		
		// 저장 버튼을 클릭할때 이벤트 발생
		$("#save").on("click", function(e) {

			if(!fn_PreSave()) {
				return ;
			}

			var sendData = {
				"title" : $("#title").val(),
			    "text" : $("#text").val(),
			    "nowPage" : 1
		    };

			gfn_ajax("sm02insert.do","POST" , sendData , function(data) {
				var html = "";

				// jQuery 유틸리티 메서드 
				$.each(data.list, function(index, item){
				
					html += '<tr>';
		            html += '<td scope="col" width="100">' + item.title + '</td>';
		            html += '<td id="text' + index + '" scope="col" width="340">' + item.text + '</td>';
		            html += '<td id="regdate' + index + '" scope="col" width="115">' + gfn_dateFormat(item.regdate) + '</td>';
		            html += '<td id="seq' + index + '" style="display:none;">' + item.seq + '</td>';
		            html += '<td scope="col" width="50"><input type="button" value="삭제" onclick="deletesm02('+ index +')"></td>';
					html += '</tr>';
					
				});
				$("#sm02viewTbody").html(html);
				 
				// 페이징 함수 호출
				gfn_paging(data.nowPage, data.totCnt , "#pagenation", "page_move");
			});
		});
		
		// 조회(페이지 버튼)
		$(document).on("click", "a[name='page_move']" , function(e) {
			e.preventDefault();
			var page = $(this).attr('value');
			showSM02List(page);
		});
		
		// 조회(이전 버튼)
		$(document).on("click", "a[name='page_prev']" , function(e) {
			e.preventDefault();
			var page = $("#startPage").attr('value');
			showSM02List(Number(page) - 10);
		});
		
		// 조회(다음 버튼)
		$(document).on("click", "a[name='page_next']" , function(e) {
			e.preventDefault();
			var page = $("#startPage").attr('value');
			var maxPage = $("#maxPage").attr('value');
			if(Number(page) + 10 > maxPage) {
				showSM02List(maxPage);
			} else {
				showSM02List(Number(page) + 10);
			}
		});
	});
	
	function deletesm02(idx) {
		
		if(confirm("삭제할까요?")) {
			
			var sendData = {
			    "seq" : $("#seq" + idx).text(),
			    "nowPage" : 1
		    };

			gfn_ajax("sm02delete.do","POST" , sendData , function(data) {
				var html = "";
				// jQuery 유틸리티 메서드 
				$.each(data.list, function(index, item){
				
					html += '<tr>';
		            html += '<td scope="col" width="100">' + item.title + '</td>';
		            html += '<td id="text' + index + '" scope="col" width="340">' + item.text + '</td>';
		            html += '<td id="regdate' + index + '" scope="col" width="115">' + gfn_dateFormat(item.regdate) + '</td>';
		            html += '<td id="seq' + index + '" style="display:none;">' + item.seq + '</td>';
		            html += '<td scope="col" width="50"><input type="button" value="삭제" onclick="deletesm02('+ index +')"></td>';
					html += '</tr>';
					
				});
				$("#sm02viewTbody").html(html);
				 
				// 페이징 함수 호출
				gfn_paging(data.nowPage, data.totCnt , "#pagenation", "page_move");
			});
		}
	}
	
	function showSM02List(nowPage) {
		
		if(!gfn_isNull(nowPage)) {
			page = nowPage;
		} else {
			page = 1;
		}
		
		var sendData = {
			"nowPage" : page
		};
		
		gfn_ajax("sm02show.do","POST" , sendData , function(data) {
			var html = "";
			// jQuery 유틸리티 메서드 
			$.each(data.list, function(index, item){
			
				html += '<tr>';
	            html += '<td scope="col" width="100">' + item.title + '</td>';
	            html += '<td id="text' + index + '" scope="col" width="340">' + item.text + '</td>';
	            html += '<td id="regdate' + index + '" scope="col" width="115">' + gfn_dateFormat(item.regdate) + '</td>';
	            html += '<td id="seq' + index + '" style="display:none;">' + item.seq + '</td>';
	            html += '<td scope="col" width="50"><input type="button" value="삭제" onclick="deletesm02('+ index +')"></td>';
				html += '</tr>';
				
			});
			
			$("#sm02viewTbody").html(html);
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
		<div class="container">
		<form id="form" method="post" action="/common/sm02insert.do">
			<input type="hidden" id="userid" name="userid" value="<%=session.getAttribute("userid")%>">
			제목:<input type="text" id="title" name="title"> <br>
			내용<textarea id="text" name="text" rows="5" cols="70"></textarea>
			<button id="save" type="button" >저장</button>
		</form>
		<header>
			<h1>
				개인 메모장
			</h1>
		</header>
		<table border = "1">
		<colgroup>
		<col width="100">
		<col width="340">
		<col width="115">
		<col width="50">
		</colgroup>
		<thead>
			<tr>
				<th scope="col">타이틀</th>
				<th scope="col">내용</th>
				<th scope="col">메모일자</th>
				<th scope="col"></th>
			</tr>
		</thead>
		<tbody id="sm02viewTbody">
		
		</tbody>
	</table>
	<div id="pagenation">
		
	</div>
	</div>
	
	</section>
	<p>
	<footer> <jsp:include page="footer.jsp" /> </footer>
</body>
</html>
