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

	$(document).ready(function() {
		
		selectGrpList("code_grp");
		
		// 저장 버튼을 클릭할때 이벤트 발생
		$("#btn_code_insert").on("click", function(e) {
			if(!fn_PreSave()) {
				return;
			}
			insertCodeList();
		});
		
		// code_grp change 이벤트
		$(document).on("change", "#code_grp" , function() {
			var code_grp = $(this).val();
			var code_list = gfn_getCommCode(code_grp);
			var html = "";
			for(var i = 0 ; i < code_list.length ;i++) {
				html += '<tr>';
	            html += '<td id="codecd">' + code_list[i].codecd + '</td>';
	            html += '<td id="nm"><input type="text" id="codenm" value="' + code_list[i].codenm +'" maxlength="10"></td>';
	            html += '<td><input type="button" id="updateCode" value="수정"></td>';
	            html += '<td><input type="button" id="deleteCode" value="삭제"></td>';
	           	html += '</tr>';	
			}
			$("#codeviewTbody").empty();
		    $("#codeviewTbody").append(html);
		});
		
		// 수정버튼
		$(document).on("click", "#updateCode" , function() {
			
			if(confirm("수정하시겠습니까 ??")) {
				var tr = $(this).parent().parent("tr");

				var sendData = JSON.stringify({
					codecd : tr.children("td#codecd").text(),
					codenm : tr.children("td#nm").children("input#codenm").val(),
					codegrp :$("#code_grp").val()
				});
				gfn_ajax("updateCode.do","POST" , sendData , function(data) {
						
				});	
			}
		});
		
		// 삭제버튼
		$(document).on("click", "#deleteCode" , function() {
			
			if(confirm("삭제하시겠습니까 ??")) {
				var tr = $(this).parent().parent("tr");

				var sendData = JSON.stringify({
					codecd : tr.children("td#codecd").text(),
					codegrp :$("#code_grp").val()
				});
				gfn_ajax("deleteCode.do","POST" , sendData , function(data) {
					var html = "";
			       	
			        $.each(data.commList, function(index, item) {
			        	html += '<tr>';
			            html += '<td id="codecd">' + item.codecd + '</td>';
			            html += '<td id="nm"><input type="text" id="codenm" value="' + item.codenm +'" maxlength="10"></td>';
			            html += '<td><input type="button" id="updateCode" value="수정"></td>';
			            html += '<td><input type="button" id="deleteCode" value="삭제"></td>';
			           	html += '</tr>';	
			        });
			        $("#codeviewTbody").empty();
			        $("#codeviewTbody").append(html);
				});	
			}
		});
	});


	/** select box 셋팅*/
	selectGrpList = function(id) {
		var combo ='<option value="" selected>선택</option>';
		var formData = JSON.stringify({});
		$.ajax({
		    url : '/common/commCodeGrp.do',
		    contentType:"application/json;charset=UTF-8",
		    type : 'post',
		    dataType : 'json',
		    data : formData,
		    success : function(data) {
		    	if(!gfn_isNull(data.commList)){
		    		if(data.commList.length > 0) {
		    			for(var i = 0 ; i < data.commList.length ;i++) {
		    				combo += '<option value=' + data.commList[i].codegrp + '>' + data.commList[i].codegrpnm + '</option>';
		    			}
		    			$("#" + id + "").append(combo);
		    		}
		    	}
		    },
		    error : function(request, status, error) {
		        console.log("code:" + request.status + "\n" + "error:" + error);
		    }
		});
	}
	
	function fn_PreSave() {
		
		if(gfn_isNull($("#code_grp").val())) {
			alert("코드구분을 선택하세요");
			return false;
		}

		if(gfn_isNull($("#input_codecd").val())) {
			alert("코드번호를 입력하세요");
			return false;
		}
		
		if(gfn_isNull($("#input_codenm").val())) {
			alert("코드이름을 입력하세요");
			return false;
		}

		return true;
	}
	
	function insertCodeList() {
		console.log("insertCodeList");
		
		var sendData = JSON.stringify({
			codegrp :$("#code_grp").val(),
			codecd :$("#input_codecd").val(),
			codenm :$("#input_codenm").val()
		});
		
		gfn_ajax("insertCode.do","POST" , sendData , function(data) {
			var html = "";
	       	
	        $.each(data.commList, function(index, item) {
	        	html += '<tr>';
	            html += '<td id="codecd">' + item.codecd + '</td>';
	            html += '<td id="nm"><input type="text" id="codenm" value="' + item.codenm +'" maxlength="10"></td>';
	            html += '<td><input type="button" id="updateCode" value="수정"></td>';
	            html += '<td><input type="button" id="deleteCode" value="삭제"></td>';
	           	html += '</tr>';	
	        });
	        $("#codeviewTbody").empty();
	        $("#codeviewTbody").append(html);
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
	<select id="code_grp">
	</select>
	<form id="menu_form">
		<button id="btn_code_insert" type="button" >코드 등록</button>
		코드번호:<input type="text" id="input_codecd" name="input_codecd" maxlength="2"><br>
		코드이름:<input type="text" id="input_codenm" name="input_codenm" maxlength="10"><br>
	</form>
		<div class="container">
		<table border = "1">
			<colgroup>
				<col width="100">
				<col width="100">
				<col width="50">
				<col width="50">
			</colgroup>
			<thead>
			<tr>
				<th scope="col">코드번호</th>
				<th scope="col">코드이름</th>
				<th scope="col">수정</th>
				<th scope="col">삭제</th>
			</tr>
			</thead>
			<tbody id="codeviewTbody">
		
			</tbody>
		</table>
		</div>
	</section>
	<p>
	<footer> <jsp:include page="footer.jsp" /> </footer>
</body>
</html>
