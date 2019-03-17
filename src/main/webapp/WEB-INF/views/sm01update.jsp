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
	
		showSP01MyList();
		
		fn_PreSave = function() {
			
			if(!gfn_isKor($("#username").val())) {
				alert("이름엔 한글만 입력하세요");
				return false;
			}
			
			if(!gfn_isPhoneNumber($("#phone").val())) {
				alert("핸드폰번호 형식에 맞게 입력하세요");
				return false;
			}
			
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

			$("#form").submit();
	
 		});
		
		// 판매버튼
		$(document).on("click", "#sell" , function() {
			
			if(confirm("판매하시겠습니까 ??")) {
				var tr = $(this).parent().parent("tr");

				var sendData = {
					seq : tr.children("#seq").text(),
					title : tr.children("#title").text()
				};
				
				gfn_ajax("sp01sell.do","POST" , sendData , function(data) {
					if(data.result == 1){
						tr.children("#state").text("판매완료");
						$(this).hide(); // 버튼 hide
						tr.children("#cancel").hide();
					}
				});	
			}
		});
		
		$(document).on("click", "#cancel" , function() {
			
			if(confirm("취소하시겠습니까 ??")) {
				var tr = $(this).parent().parent("tr");

				var sendData = {
					seq : tr.children("#seq").text(),
					title : tr.children("#title").text()
				};
				
				gfn_ajax("sp01cancel.do","POST" , sendData , function(data) {
					if(data.result == 1){
						tr.children("#state").text("판매대기");
						$(this).hide(); // 버튼 hide
						tr.children("#sell").hide();
					}
				});	
			}
			
		});
		
		// 조회(페이지 버튼)
		$(document).on("click", "a[name='page_move']" , function(e) {
			e.preventDefault();
			var page = $(this).attr('value');
			showSP01MyList(page);
		});
		
		// 조회(이전 버튼)
		$(document).on("click", "a[name='page_prev']" , function(e) {
			e.preventDefault();
			var page = $("#startPage").attr('value');
			showSP01MyList(Number(page) - 10);
		});
		
		// 조회(다음 버튼)
		$(document).on("click", "a[name='page_next']" , function(e) {
			e.preventDefault();
			var page = $("#startPage").attr('value');
			var maxPage = $("#maxPage").attr('value');
			if(Number(page) + 10 > maxPage) {
				showSP01MyList(maxPage);
			} else {
				showSP01MyList(Number(page) + 10);
			}
		});
	});

	
	function showSP01MyList(nowPage) {
		console.log("showSP01MyList");
		
		if(!gfn_isNull(nowPage)) {
			page = nowPage;
		} else {
			page = 1;
		}
		
		var sendData = {
			"nowPage" : page
		};
		
		gfn_ajax("sp01showMyList.do","POST" , sendData , function(data) {

			var html = "";
			$.each(data.list, function(index, item) {
				html += '<tr>';
	            html += '<td scope="col" width="50" id="seq">' + item.seq + '</td>';
	            html += '<td scope="col" width="50" id="title"><a href="/common/sp01show_detail.do?seq='+
	            item.seq +'">' + 
	            item.title + '</a></td>';
	            html += '<td scope="col" width="30">' + item.reply + '</td>';
	            html += '<td scope="col" width="100">' + item.userid + '</td>';
				html += '<td scope="col" width="30">' + gfn_getCommCodeNm(product_code ,item.ptype) + '</td>';
	            html += '<td scope="col" width="30" id="state">' + gfn_getCommCodeNm(state_code , item.state) + '</td>';   
	            if(item.state == "02") {
	            	html += '<td scope="col" width="30"><input type="button" id="sell" name="sell" value="판매"> </td>';
	            	html += '<td scope="col" width="30"><input type="button" id="cancel" name="cancel" value="취소"> </td>';	
	            } else {
	            	html += '<td scope="col" width="30"></td>';	
	            	html += '<td scope="col" width="30"></td>';	
	            }
	            html += '<td scope="col" width="70">' + gfn_dateFormat(item.regdate) + '</td>';
	            html += '<td scope="col" width="70">' + item.registerid + '</td>';
	            html += '</tr>';
			});
			
	        $("#sp01viewTbody").html(html);
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
		<div id="container_demo">
			<div id="wrapper">
				<div id="sb01insert" class="animate form">
					<div id="login" class="animate form">
						<h4>회원 정보를 입력해 주세요</h4>
						<form id="form" name="updateform" method="post" enctype="multipart/form-data" action="/common/sm01update.do">
							<input type="hidden" id="userid" name="userid" value="${sm01Vo.userid}"> <br>
							<br> 이름:<input	type="text" id="username" name="username" value="${sm01Vo.username}" maxlength="6">
							<br> 전화번호:<input type="text" id="phone" name="phone" value="${sm01Vo.phone}" maxlength="14">
							<br> 생년월일:<input type="text" id="brth" name="brth" value="${sm01Vo.brth}" maxlength="8">
							<br> 이메일:<input type="text" id="email" name="email" value="${sm01Vo.email}" maxlength="20">
							<br>
							<button id="save" type="button" >저장</button>
							<input id="fileInput" type="file" name="imgFile" />				
						</form>
						<img id="showTempImage" alt="" name="photo" src="/common/selectPhoto.do?userid=${sm01Vo.userid}" height="170px" width="150px"/>
					</div>
				</div>
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
				<th scope="col">상품유형</th>
				<th scope="col">판매상태</th>
				<th scope="col">판매완료</th>
				<th scope="col">판매취소</th>
				<th scope="col">등록일자</th>
				<th scope="col">신청자</th>
			</tr>
			</thead>
			<tbody id="sp01viewTbody">
		
			</tbody>
		</table>
		<div id="pagenation">
		
		</div>
				
			</div>
		</div>
	</section>
	<p>
	<footer> <jsp:include page="footer.jsp" /> </footer>
</body>
</html>
