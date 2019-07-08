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

		showSB02List(1);
		
		// 저장 버튼을 클릭할때 이벤트 발생
		$("#btn_insert_button").on("click", function(e) {
			
			if(!fn_PreSave()) {
				return ;
			}
			
			re_showSB02List(1);
		});
		
		// 삭제 버튼을 클릭할때 이벤트 발생
		$("#button_delete").on("click", function(e) {
			if(confirm("삭제할까요?")) {
				
				gfn_ajaxRest("sb01/" + parseInt($("#seq01").val()), "DELETE" , function(data) {
					if(data.result) {
						location.href='/sb01.do';		
					}
				});
			}
		});
		
		// 좋아요 버튼을 클릭할때 이벤트 발생
		$("#button_like").on("click", function(e) {
			if(confirm("좋아요 할까요?")) {
				like_sb01();
			}
		});
		
		// 싫어요 버튼을 클릭할때 이벤트 발생
		$("#button_hate").on("click", function(e) {
			if(confirm("싫어요 할까요?")) {
				hate_sb01();
			}
		});
		
		// 조회(페이지 버튼)
		$(document).on("click", "a[name='page_move']", function(e) {
			e.preventDefault();
			var page = $(this).attr('value');
			showSB02List(page);
		});
		
		// 조회(이전 버튼)
		$(document).on("click", "a[name='page_prev']", function() {
			e.preventDefault();
			var page = $("#startPage").attr('value');
			showSB02List(Number(page) - 10);
		});
		
		// 조회(다음 버튼)
		$(document).on("click", "a[name='page_next']", function() {
			e.preventDefault();
			var page = $("#startPage").attr('value');
			var maxPage = $("#maxPage").attr('value');
			if(Number(page) + 10 > maxPage) {
				showSB02List(maxPage);
			} else {
				showSB02List(Number(page) + 10);
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
			
			gfn_ajaxRest("sb02/" + $("#seq" + idx).text() + "/" + $("#seq01" + idx).text(),"DELETE" , function(data) {
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
		        $("#sb02viewTbody").empty();
		        $("#sb02viewTbody").append(html);
		     	// 페이징 함수 호출
		        gfn_paging(data.nowPage, data.totCnt , "#pagenation", "page_move");
			});
		}
	}
	
	function showSB02List(nowPage) {
		
		gfn_ajaxRest("sb02/" + parseInt($("#seq01").val()) + "/" + nowPage,"GET" , function(data) {
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
	        $("#sb02viewTbody").empty();
	        $("#sb02viewTbody").append(html);
	     	// 페이징 함수 호출
	        gfn_paging(data.nowPage, data.totCnt , "#pagenation", "page_move");
		});
	};
	
	function re_showSB02List() {
	
		var sendData = {
			"seq01" : parseInt($("#seq01").val()),
        	"text" : $("#form #text").val().trim(),
        	"nowPage" : 1
        };

		gfn_ajax("sb02","POST" , sendData , function(data) {
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
	        $("#sb02viewTbody").empty();
	        $("#sb02viewTbody").append(html);
	     	// 페이징 함수 호출
	        gfn_paging(data.nowPage, data.totCnt , "#pagenation", "page_move");
		});
	};
	
	function like_sb01() {
	
		gfn_ajaxRest("sb01like/" + parseInt($("#seq01").val()), "PUT", function(data) {
			if(data.result == 1) {
				$("#good").text(data.like);
				$("#button_like").attr('disabled', true);
			
			}
		});
	}
	
	function hate_sb01() {
		
		gfn_ajaxRest("sb01hate/" + parseInt($("#seq01").val()), "PUT" , function(data) {
			if(data.result == 1) {
				$("#good").text(data.like);
				$("#button_hate").attr('disabled', true);
			}
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
		<div>
		<form id="form_delete">
						
			<input type="hidden" id="seq01" name="seq" value="${sb01Vo.seq}" />
			<input type="hidden" id="title" name="title" value="${sb01Vo.title}" />
			<input type="hidden" id="regdate" name="regdate" value="${sb01Vo.regdate}" />
			<p>작성자 : ${sb01Vo.userid}</p>
			<p>제목 : ${sb01Vo.title}</p>
			<p>등록일자 : ${sb01Vo.showDate}</p>
			<c:if test="${sb01Vo.deleteYn}" var="result" scope="page">
       			<input id="button_delete" type="button" value="삭제">
    		</c:if>
    	</form>
		</div>
		<div>
			<div>내용 :<p>${sb01Vo.text}</p></div>
			<div>조회수 :<p>${sb01Vo.hit}</p></div>
			<div>좋아요 :<p id="good">${sb01Vo.good}</p></div>
			<c:choose>
				<c:when test="${sb01Vo.goodlog!='Y'}">
					<input id="button_like" type="button" value="좋아요">
				</c:when>
				<c:otherwise>
					<input id="button_like" type="button" value="좋아요" disabled>
				</c:otherwise>
			</c:choose>
				
    		<c:choose>
				<c:when test="${sb01Vo.hatelog!='Y'}">
					<input id="button_hate" type="button" value="싫어요">
				</c:when>
				<c:otherwise>
					<input id="button_hate" type="button" value="싫어요" disabled>
				</c:otherwise>
			</c:choose>
    		
			<video id="showVideo" width="640" height="360" controls="controls" class="video-js vjs-default-skin" data-setup="{}">
			    <source src="/selectVideo/${sb01Vo.seq}/${sb01Vo.title}" type="video/mp4" />
			</video>
		</div>
		
		<div class="container">
		<form id="form" method="post"  action="/sb02insert.do">
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
