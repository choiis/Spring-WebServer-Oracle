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
var user_code = {};
	$(document).ready(function() {

		user_code = gfn_getCommCode("U001");
		gfn_selectList("U001" , "input_authlevel");
		showMenuList();

		// 저장 버튼을 클릭할때 이벤트 발생
		$("#btn_menu_insert").on("click", function(e) {
			if(!fn_PreSave()) {
				return;
			}
			insertMenuList();
		});
		
		// 수정버튼
		$(document).on("click", "#updateMenu" , function() {
			
			if(confirm("수정하시겠습니까 ??")) {
				var tr = $(this).parent().parent("tr");

				var sendData = JSON.stringify({
					menucd : tr.children("td#menucd").text(),
					menunm : tr.children("td#nm").children("input#menunm").val(),
					menuurl : tr.children("td#url").children("input#menuurl").val(),
					authlevel : tr.children("td#level").children("select#authlevel").val()
				});
				gfn_ajax("updateMenu.do","POST" , sendData , function(data) {
					if(data.result == 1){
					}
				});	
			}
		});
		
		// 삭제버튼
		$(document).on("click", "#deleteMenu" , function() {
			
			if(confirm("삭제하시겠습니까 ??")) {
				var tr = $(this).parent().parent("tr");

				var sendData = JSON.stringify({
					menucd : tr.children("td#menucd").text(),
					menunm : tr.children("td#nm").children("input#menunm").val()
				});
				gfn_ajax("deleteMenu.do","POST" , sendData , function(data) {
					var html = "";
			       	
			        $.each(data.commList, function(index, item) {
			        	html += '<tr>';
			            html += '<td id="menucd">' + item.menucd + '</td>';
			            html += '<td id="nm"><input type="text" id="menunm" value="' + item.menunm +'" maxlength="10"></td>';
			            html += '<td id="url"><input type="text" id="menuurl"  value="' + item.menuurl +'" maxlength="20"></td>';
			            html += '<td id="level">' + gfn_selectedBox(user_code ,item.authlevel , "authlevel") + '</td>';
			            html += '<td><input type="button" id="updateMenu" value="수정"></td>';
			            html += '<td><input type="button" id="deleteMenu" value="삭제"></td>';
			           	html += '</tr>';
			        });
			        $("#menuviewTbody").empty();
			        $("#menuviewTbody").append(html);
				});	
			}
		});
	});
	
	function fn_PreSave() {
		

		if(gfn_isNull($("#input_menucd").val())) {
			alert("메뉴코드를 입력하세요");
			return false;
		}
		
		if(gfn_isNull($("#input_menunm").val())) {
			alert("메뉴이름을 입력하세요");
			return false;
		}

		if(gfn_isNull($("#input_menuurl").val())) {
			alert("메뉴URL을 입력하세요");
			return false;
		}
		
		if(!gfn_isKor($("#input_menuurl").val())) {
			alert("메뉴URL엔 한글을 입력할 수 없습니다");
			return false;
		}
		
		if(gfn_isNull($("#input_authlevel").val())) {
			alert("권한레벨을 입력하세요");
			return false;
		}
		
		return true;
	}
	
	function insertMenuList() {
		console.log("insertMenuList");
		
		var sendData = JSON.stringify({
			menucd :$("#input_menucd").val(),
			menunm :$("#input_menunm").val(),
			menuurl :$("#input_menuurl").val(),
			authlevel :$("#input_authlevel").val()
		});
		
		gfn_ajax("insertMenu.do","POST" , sendData , function(data) {
			var html = "";
	       	
	        $.each(data.commList, function(index, item) {
	        	html += '<tr>';
	            html += '<td id="menucd">' + item.menucd + '</td>';
	            html += '<td id="nm"><input type="text" id="menunm" value="' + item.menunm +'" maxlength="10"></td>';
	            html += '<td id="url"><input type="text" id="menuurl"  value="' + item.menuurl +'" maxlength="20"></td>';
	            html += '<td id="level">' + gfn_selectedBox(user_code ,item.authlevel , "authlevel") + '</td>';
	            html += '<td><input type="button" id="updateMenu" value="수정"></td>';
	            html += '<td><input type="button" id="deleteMenu" value="삭제"></td>';
	           	html += '</tr>';
	        });
	        $("#menuviewTbody").empty();
	        $("#menuviewTbody").append(html);
		});
	}
	
	function showMenuList() {
		console.log("showMenuList");
		
		var sendData = JSON.stringify({});
		gfn_ajax("commMenu.do","POST" , sendData , function(data) {
			var html = "";
	       	
	        $.each(data.commList, function(index, item) {
	        	html += '<tr>';
	            html += '<td id="menucd">' + item.menucd + '</td>';
	            html += '<td id="nm"><input type="text" id="menunm" value="' + item.menunm +'" maxlength="10"></td>';
	            html += '<td id="url"><input type="text" id="menuurl"  value="' + item.menuurl +'" maxlength="20"></td>';
	            html += '<td id="level">' + gfn_selectedBox(user_code ,item.authlevel , "authlevel") + '</td>';
	            html += '<td><input type="button" id="updateMenu" value="수정"></td>';
	            html += '<td><input type="button" id="deleteMenu" value="삭제"></td>';
	           	html += '</tr>';
	        });
	        $("#menuviewTbody").empty();
	        $("#menuviewTbody").append(html);
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
	<form id="menu_form">
		<button id="btn_menu_insert" type="button" >메뉴 등록</button>
		메뉴코드:<input type="text" id="input_menucd" name="input_menucd" maxlength="2"><br>
		메뉴이름:<input type="text" id="input_menunm" name="input_menunm" maxlength="10"><br>
		메뉴URL:<input type="text" id="input_menuurl" name="input_menuurl" maxlength="20"><br>
		<select id="input_authlevel" name="input_authlevel" >
		</select>
	</form>
		<div class="container">
		<table border = "1">
			<colgroup>
				<col width="100">
				<col width="100">
				<col width="200">
				<col width="100">
				<col width="50">
				<col width="50">
			</colgroup>
			<thead>
			<tr>
				<th scope="col">메뉴코드</th>
				<th scope="col">메뉴이름</th>
				<th scope="col">메뉴URL</th>
				<th scope="col">권한레벨</th>
				<th scope="col">수정</th>
				<th scope="col">삭제</th>
			</tr>
			</thead>
			<tbody id="menuviewTbody">
		
			</tbody>
		</table>
		</div>
	</section>
	<p>
	<footer> <jsp:include page="footer.jsp" /> </footer>
</body>
</html>
