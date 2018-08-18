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
			</tr>
		</thead>
		<tbody>
		<!-- 목록이 반복될 영역 -->
		<c:forEach var="item" items="${list}" varStatus="status">
			<tr>
				<td><a href="/common/sm01show.do?userid=${item.userid}">${item.username}</a></td>
				<td>${item.brth}</td>
				<td>${item.regdate}</td>
				<td>${item.phone}</td>
				<td>${item.email}</td>
			</tr>
		</c:forEach>
		</tbody>
		</table>
	</section>
	<p>
	<footer> <jsp:include page="footer.jsp" /> </footer>
</body>
</html>
