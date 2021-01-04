<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<title>전국노래자랑</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.5/sockjs.min.js"></script>
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
	<input type="text" id="message" />
	<input type="button" id="sendBtn" value="submit"/>
	<br>
	<input type="text" id="sendto" />
	<input type="text" id="whisper" />
	<input type="button" id="sendtoBtn" value="sendto"/>
	
	<div id="messageArea"></div>
    
    <div id="message"></div>
		<video id="showVideo" width="640" height="360" controls="controls" class="video-js vjs-default-skin" data-setup="{}">
			<source src="/videoStreaming" type="video/mp4" />
		</video>
	</section>

    <!-- websocket javascript -->
    <script>
    $("#sendBtn").click(function() {
		sendMessage();
		$('#message').val('');
	});
    
    $("#sendtoBtn").click(function() {
    	sock.send("dm," + $("#sendto").val() + "," + $("#whisper").val());
		$('#whisper').val('');
	});

	let sock = new SockJS("/chat/");
	sock.onmessage = onMessage;
	sock.onclose = onClose;
	// 메시지 전송
	function sendMessage() {
		sock.send("msg,"+$("#message").val());
	}
	// 서버로부터 메시지를 받았을 때
	function onMessage(msg) {
		var data = msg.data;
		$("#messageArea").append(data + "<br/>");
	}
	// 서버와 연결을 끊었을 때
	function onClose(evt) {
		$("#messageArea").append("연결 끊김");

	}
    </script>
	
	<p>
	 
   <footer> <jsp:include page="footer.jsp" /> </footer>
</body>
</html>
