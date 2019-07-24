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
<link href="<c:url value="/resources/css/indexstyle.css" />" rel="stylesheet">
</head>
<body>
<script type="text/javascript">
	// 페이지 로딩이 완료되고, jQuery 스크립트 실행된다
	$(document).ready(function() {
		
		fn_PreLogin = function() {
			
			if(gfn_isNull($("#userid").val())) {
				alert("아이디를 입력하세요");
				return false;
			}
			
			if(gfn_isNull($("#passwd").val())) {
				alert("비밀번호를 입력하세요");
				return false;
			}
			
			if($("#passwd").val().length < 4) {
				alert("비밀번호는 네자리 이상 입력해 주세요");
				return false;
			}
			
			return true;
		};
		
		// 로그인 버튼을 클릭할때 이벤트 발생
		$("#passwd").on("keyup", function(key) {
			if(key.keyCode == 13) {//키가 13이면 실행 (엔터는 13)
				// 로그인 함수
				login();
			}
		});
		
		// 로그인 버튼을 클릭할때 이벤트 발생
		$("#login_button").on("click", function(e) {
			
			login();
		});
	});
	
	// 로그인 함수
	function login() {
		
		if(!fn_PreLogin()) {
			return ;
		}
		
		var sendData = {
        	"userid" : $("#userid").val(),
        	// "passwd" : gfn_AES256_Encode($("#passwd").val()),
        	"passwd" : $("#passwd").val(),
        	"browser" : gfn_getBrowser(),
        	"device" : gfn_getDevice()
        };
		
		gfn_ajax("login","POST" , sendData , function(data) {
			if(data.code == 1){
				location.href='/main';
			} else {
				alert("아이디와 비밀번호를 확인해 주세요");
			}
			
		});	
	}
</script>
	<div class="container">
		<header>
			<h1>
				전국노래자랑에 오신 것을 환영합니다.<span><br>당신의 꿈을 노래하세요</span>
			</h1>
		</header>
		<section>
			<div id="container_demo">
				<div id="wrapper">
					<div id="login" class="animate form">
						<form id="form" action="/login" method="post">
							<h1>Log in</h1>
							<p>
								<label for="userId" class="userId" data-icon="u"> 아이디를
									입력하세요 </label> <input id="userid" name="userid" required="required"
									type="text" placeholder="id 입력" />
							</p>
							<p>
								<label for="password" class="youpasswd" data-icon="p">
									비밀번호를 입력하세요 </label> <input id="passwd" name="passwd"
									required="required" type="password" placeholder="pw 입력" />
							</p>

							<p class="login button">
								<button id="login_button" type="button" >로그인</button>
							</p>
							<div id=jhw align="right">
								아직 회원이 아니신가요? &nbsp;&nbsp;&nbsp; 
								<a href="/sm01joinPage">회원가입</a>
								<br> 아이디를 잊으셨나요? &nbsp;&nbsp;&nbsp; <a
									href="Join/findId.jsp">아이디 찾기</a> <br> 비밀번호를
								잊으셨나요?&nbsp;&nbsp; <a href="Join/findPw.jsp">비밀번호 찾기</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</section>
	</div>
</body>
</html>
