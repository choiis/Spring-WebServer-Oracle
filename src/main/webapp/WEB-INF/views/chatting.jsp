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
	var ws;

	function wsOpen(){
		ws = new WebSocket("ws://" + location.host + "/chat");
		ws.onopen = function(data){
		//소켓이 열리면 초기화 세팅하기
		}
	
		ws.onmessage = function(msg) {
			var data = msg.data;
			if (data != null && data.type != '') {
				$("#messageArea").append(data + "<br/>");		
			} else {
				var url = URL.createObjectURL(new Blob([data]));
				$("#messageArea").append("<div class='img'><img class='msgImg' src="+url+"></div><div class='clearBoth'></div><br/>");
			}
		}
	}

	function send() {
		ws.send("msg,"+$("#message").val());
		$("#message").val('');
	}

	function sendto() {
		ws.send("dm," + $("#sendto").val() + "," + $("#whisper").val());
		$('#whisper').val('');
	}

	function fileSend() {
		var file = document.querySelector("#fileUpload").files[0];
		var fileReader = new FileReader();
		fileReader.onload = function() {

		    arrayBuffer = this.result;
		    ws.binaryData = "blob";
			ws.send(arrayBuffer); //파일 소켓 전송
		};
		fileReader.readAsArrayBuffer(file);
	}

	wsOpen();
</script>
	<jsp:include page="banner.jsp" /> 
	<header>
	<h1 class="logo">
		<span class="word1">전국</span> <span class="word2">노래자랑</span>
	</h1>
	</header>
	<nav> <jsp:include page="sidebar.jsp" /> </nav>
	<section>
	<input type="text" id="message" />
	<button onclick="send()" id="sendBtn">보내기</button>
	<br>
	
	<input type="text" id="sendto" />
	<input type="text" id="whisper" />
	<button onclick="sendto()" id="sendBtn">귓속말</button>
	<br>
	<input type="file" id="fileUpload">
	<button onclick="fileSend()" id="sendFileBtn">파일올리기</button>
	<div id="messageArea"></div>
    
    <div id="message"></div>
		
	</section>
	<p>
	<footer> <jsp:include page="footer.jsp" /> </footer>
</body>
</html>
