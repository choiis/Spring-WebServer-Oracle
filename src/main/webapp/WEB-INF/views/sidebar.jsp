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
<link href="<c:url value="/resources/css/sidebar.css" />" rel="stylesheet">
</head>
<body>
<script type="text/javascript">
	// 페이지 로딩이 완료되고, jQuery 스크립트 실행된다
	$(document).ready(function() {
		
		// 메뉴 불러오기
		selectMenu();
		// 로그아웃 클릭
		$("#logout").on("click",function(e) {
			e.preventDefault();
			
			if(confirm("로그아웃 하시겠습니까?")) {
				location.href = "/common/logout.do";
			} 
		});
	});
	
	function selectMenu() {
		
		var sendData = JSON.stringify({});
		
		gfn_ajax("commMenu.do","POST" , sendData , function(data) {
			var html = "";
	       	
	        $.each(data.commList, function(index, item) {
	        	html += '<li><a href = "' + item.menuurl + '" >' + item.menunm + '</a></li>';
	        });
	        $("#menuList").empty();
	        $("#menuList").append(html);
			
		});
	}
	
</script>
</body>
  <ul id="navi">
        <li class="group">
        <ul>회원 이름 : <%=session.getAttribute("username")%></ul><br>
        <ul>회원 아아디 :<%=session.getAttribute("userid")%></ul><br>
        <a href = '/common/sm01selectOne.do'>회원정보 변경</a>
        <br>
        <a href="#" id="logout">로그아웃</a>
        <div class="title">게시판 이용</div>
            <ul id="menuList" class="sub">
            </ul>
      	</li>
    </ul>

</html>