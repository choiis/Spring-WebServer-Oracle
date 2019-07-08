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

		showSV01One(0);
		
		// 삭제 버튼을 클릭할때 이벤트 발생
		$("#button_vote").on("click", function(e) {
			if(!Pre_Save()) {
				alert("투표해주세요!");
				return;
			}
			
			if(confirm("투표할까요?")) {
				
				var sendData = {};
					
				var list = [];
					
				if($("#multiselect").val() == 0) { // 단일선택
					for(var i = 0 ; i < $('input:radio').length ;i++) {
						if($('input:radio').eq(i).is(':checked')) {
							var vo = {
								"seq" : $("#seq").val(),
								"idx" : $('input:radio').eq(i).val()
							};
							list.push(vo);
							break;
						}
					}
				
				} else { // 중복선택
					for(var i = 0 ; i < $('input:checkbox').length ;i++) {
						if($('input:checkbox').eq(i).is(':checked')) {
							var vo = {
								"seq" : $("#seq").val(),
								"idx" : $('input:checkbox').eq(i).val()
							};
							list.push(vo);
						}
					}
				}
				
				sendData.sv02Vos = list;
				
				gfn_ajaxRequestBody("sv03insert.do", "POST", sendData, function(data) {
					if(confirm("투표 완료!")) {
						showSV01One(1);
					}	
					
				});
			}
		});
		
		// 삭제 버튼을 클릭할때 이벤트 발생
		$("#button_delete").on("click", function(e) {
			if(confirm("삭제할까요?")) {
				
				gfn_ajaxRest("sv01/" + parseInt($("#seq").val()), "DELETE" , function(data) {
					if(data.result) {
						location.href='/sv01.do';		
					}
				});		
			}
		});
		
	});

	function Pre_Save() {
		
		if($("#multiselect").val() == 0) { // 단일선택
			for(var i = 0 ; i < $('input:radio').length ;i++) {
				if($('input:radio').eq(i).is(':checked')) {
					return true;
				}
			}
		
			return false;
		} else { // 중복선택
			for(var i = 0 ; i < $('input:checkbox').length ;i++) {
				if($('input:checkbox').eq(i).is(':checked')) {
					return true;
				}
			}
			return false;
		}
		
	}
	
	function showSV01One(recall) {
		
		gfn_ajaxRest("sv01One/" + parseInt($("#seq").val()) + "/" + recall, "GET" , function(data) {
		
			$("#writer").text(data.userid);
			$("#title").text(data.title);
			$("#text").text(data.text);
			$("#hits").text(data.hit);
			$("#multiselect").val(data.multiselect);

			var html = "";
			if(data.votedYn == 0 ) { // 투표안함
				if(data.multiselect == 0) { // 단일선택
					for(var i = 0 ; i < data.sv02Vos.length ;i++) {
						html += "<input type='radio' id='rdo' name='rdo' value=" + data.sv02Vos[i].idx + " /> " +
						data.sv02Vos[i].content + " ";
					}
				} else { // 중복선택
					for(var i = 0 ; i < data.sv02Vos.length ;i++) {
						html += "<input type='checkbox' id='chk' name='chk' value=" + data.sv02Vos[i].idx + " /> " +
						data.sv02Vos[i].content + " ";
					}
				}
				
				
			
			} else { // 투표함
				$("#button_vote").hide();
				
				var totCnt = data.totCnt;
				html += "<p>전체 표 ";
				html += totCnt;
				html += "</p><br>";
				for(var i = 0 ; i < data.sv02Vos.length ;i++) {
					html += "<div>";
					html += data.sv02Vos[i].content;
					html += "  <span>";
					html += data.sv02Vos[i].voted;
					html += "표 </span>";
					html += "<div>";
				}
				
			}
			$("#voteTable").append(html);
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
		<form id="form_delete">
			<input type="hidden" id="seq" name="seq" value="${seq}" />
			<input type="hidden" id="multiselect" name="multiselect" />
			
    	</form>
		</div>
		<div id="showDiv">
			<div>작성자 :<span id="writer"></span></div>
			<div>제목 :<span id="title"></span></div>
			<div>내용 :<span id="text"></span></div>
			<div>조회수 :<span id="hits"></span></div>
    		<div id="voteTable">
    		</div>
    	</div>

		<div class="container">
			<input id="button_vote" type="button" value="투표">
		</div>
	</section>
	<p>
	<footer> <jsp:include page="footer.jsp" /> </footer>
</body>
</html>
