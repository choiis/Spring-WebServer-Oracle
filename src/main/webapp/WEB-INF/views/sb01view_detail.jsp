<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.singer.vo.SB01Vo"%>
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

		showSB02List();
		
		// 저장 버튼을 클릭할때 이벤트 발생
		$("#btn_insert_button").on("click", function(e) {
			
			if(!fn_PreSave()) {
				return ;
			}
			
			re_showSB02List();
		});
		
		// 삭제 버튼을 클릭할때 이벤트 발생
		$("#button_delete").on("click", function(e) {
			if(confirm("삭제할까요?")) {
				$("#form_delete").submit();	
			}
		});
		
		
		fn_PreSave = function() {

			if(gfn_isNull($("#form #text").val().trim())) {
				alert("댓글 내용을 입력하세요");
				return false;
			}
	
			return true;
		}	
	});
	
	function deletesb02(idx) {
		
		if(confirm("삭제할까요?")) {
			
			var sendData = JSON.stringify({
		    	"seq" : $("#seq" + idx).text(),
		    	"seq01" : $("#seq01" + idx).text()
		    });
			
			gfn_ajax("sb02delete.do","POST" , sendData , function(data) {
				var html = "";
		        for (var i = 0; i < data.list.length; i++) {
		            html += '<tr>';
		            html += '<td scope="col" width="50">' + data.list[i].userid + '</td>';
		            html += '<td scope="col" width="100">' + data.list[i].text + '</td>';
		            html += '<td scope="col" width="30">' + data.list[i].good + '</td>';
		            html += '<td scope="col" width="70">' + gfn_dateFormat(data.list[i].regdate) + '</td>';
		            if(data.list[i].deleteYn) {
		            	html += '<td scope="col" width="50">' + 
		            	'<input type="button" value="삭제" onclick="deletesb02('+ i +')">'
		            	+ '</td>';
		            	 html += '<td id="seq' + i + '" style="display:none;">' + data.list[i].seq + '</td>';
		            	 html += '<td id="seq01' + i + '" style="display:none;">' + data.list[i].seq01 + '</td>';
		            } else {
		            	html += '<td scope="col" width="50"></td>';
		            }
		            
		            html += '</tr>';
		        }

		        $("#sb02viewTbody").html(html);
		    	 // 페이징 함수 호출
		        gfn_paging("pagenation", "showSB02List" , data.size);
			});
		}
	}
	
	function showSB02List(nowPage) {
		
		if(!gfn_isNull(nowPage)) {
			page = nowPage;
		} else {
			page = 0;
		}
		
		var sendData =  JSON.stringify({
	    	"seq01" : parseInt($("#seq01").val()),
	    	"nowPage" : page
	    });
		
		
		gfn_ajax("sb02show.do","POST" , sendData , function(data) {
			var html = "";
	        for (var i = 0; i < data.list.length; i++) {
	        	 html += '<tr>';
		            html += '<td scope="col" width="50">' + data.list[i].userid + '</td>';
		            html += '<td scope="col" width="100">' + data.list[i].text + '</td>';
		            html += '<td scope="col" width="30">' + data.list[i].good + '</td>';
		            html += '<td scope="col" width="70">' + gfn_dateFormat(data.list[i].regdate) + '</td>';
		            if(data.list[i].deleteYn) {
		            	html += '<td scope="col" width="50">' + 
		            	'<input type="button" value="삭제" onclick="deletesb02('+ i +')">'
		            	+ '</td>';
		            	 html += '<td id="seq' + i + '" style="display:none;">' + data.list[i].seq + '</td>';
		            	 html += '<td id="seq01' + i + '" style="display:none;">' + data.list[i].seq01 + '</td>';
		            } else {
		            	html += '<td scope="col" width="50"></td>';
		            }
		            
		            html += '</tr>';
	        }

	        $("#sb02viewTbody").html(html);
	        
	     	// 페이징 함수 호출
	        gfn_paging("pagenation", "showSB02List" , data.size);
		});
	};
	
	function re_showSB02List() {
	
		
		var sendData = JSON.stringify({
			"seq01" : parseInt($("#seq01").val()),
        	"text" : $("#form #text").val().trim()
        });

		gfn_ajax("sb02insert.do","POST" , sendData , function(data) {
			var html = "";
	        for (var i = 0; i < data.list.length; i++) {
	            html += '<tr>';
	            html += '<td scope="col" width="50">' + data.list[i].userid + '</td>';
	            html += '<td scope="col" width="100">' + data.list[i].text + '</td>';
	            html += '<td scope="col" width="30">' + data.list[i].good + '</td>';
	            html += '<td scope="col" width="70">' + gfn_dateFormat(data.list[i].regdate) + '</td>';
	            if(data.list[i].deleteYn) {
	            	html += '<td scope="col" width="50">' + 
	            	'<input type="button" value="삭제" onclick="deletesb02('+ i +')">'
	            	+ '</td>';
	            	 html += '<td id="seq' + i + '" style="display:none;">' + data.list[i].seq + '</td>';
	            	 html += '<td id="seq01' + i + '" style="display:none;">' + data.list[i].seq01 + '</td>';
	            } else {
	            	html += '<td scope="col" width="50"></td>';
	            }
	            
	            html += '</tr>';
	        }
	        $("#sb02viewTbody").html(html);
	    	 // 페이징 함수 호출
	        gfn_paging("pagenation", "showSB02List" , data.size);
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
		<form id="form_delete" method="post" action="/common/sb01delete.do">
						
			<input type="hidden" id="seq01" name="seq" value="${sb01Vo.seq}" />
			<input type="hidden" id="title" name="title" value="${sb01Vo.title}" />
			<p>작성자 : ${sb01Vo.userid}</p>
			<p>제목 : ${sb01Vo.title}</p>
			<p>등록일자 : ${sb01Vo.regdate}</p>
			<c:if test="${sb01Vo.deleteYn}" var="result" scope="page">
       			<input id="button_delete" type="button" value="삭제">
    		</c:if>
    	</form>
		</div>
		<div>
			<p>내용 : ${sb01Vo.text}</p>
			<p>조회수 : ${sb01Vo.hit}</p>
			<p>좋아요 : ${sb01Vo.good}</p>
			<video id="showVideo" width="640" height="360" controls="controls" class="video-js vjs-default-skin" data-setup="{}">
			    <source src="/common/selectVideo.do?seq=${sb01Vo.seq}&title=${sb01Vo.title}" type="video/mp4" />
			</video>
		</div>
		
		<div class="container">
		<form id="form" method="post"  action="/common/sb02insert.do">
				댓글 내용<textarea id="text" name="text" rows="2" cols="40">
				</textarea>
				<input type="hidden" id="seq01" name="seq01" value="${sb01Vo.seq}" />
				<button id="btn_insert_button" type="button" >저장</button>
		</form>
		<table border = "1">
			<colgroup>
				<col width="50">
				<col width="200">
				<col width="30">
				<col width="70">
				<col width="50">
			</colgroup>
			<thead>
			<tr>
				<th scope="col">글쓴이</th>
				<th scope="col">내용</th>
				<th scope="col">좋아요</th>
				<th scope="col">등록일자</th>
				<th scope="col">삭제</th>
			</tr>
			</thead>
			<tbody id="sb02viewTbody">
		
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
