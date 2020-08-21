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

		showSF02List(1);
		
		// 저장 버튼을 클릭할때 이벤트 발생
		$("#btn_insert_button").on("click", function(e) {
			
			if(!fn_PreSave()) {
				return ;
			}
			
			insertSF02();
		});
		
		// 삭제 버튼을 클릭할때 이벤트 발생
		$("#button_delete").on("click", function(e) {
			if(confirm("삭제할까요?")) {
				
				gfn_ajaxRest("sf01/" + parseInt($("#seq01").val()), "DELETE" , function(data) {
					if(data.result) {
						location.href='/sf01page';		
					}
				});		
			}
		});
		
		// 좋아요 버튼을 클릭할때 이벤트 발생
		$("#button_like").on("click", function(e) {
			if(confirm("좋아요 할까요?")) {
				like_sf01();
			}
		});
		
		// 싫어요 버튼을 클릭할때 이벤트 발생
		$("#button_hate").on("click", function(e) {
			if(confirm("싫어요 할까요?")) {
				hate_sf01();
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
	
	function deleteSF02(seq, seq01) {
		
		if(confirm("삭제할까요?")) {
			
			gfn_ajaxRest("sf02/" + seq + "/" + seq01 + "/" + seq , "DELETE" , function(data) {
				showSF02List(1);
			});
		}
	}
	
	function showSF02List(page) {
		
		gfn_ajaxRest("sf02/" + parseInt($("#seq01").val()) + "/" + 0 + "/" + page , "GET" , function(data) {
			var html = drawTable(data);

			html += '<tr>';
			html += '<td><input type="button" value="더보기" onclick="showSF02ListMore('+ (data.nowPage + 1) +')"></td>';
			html += '</tr>';
			$("#sf02viewTbody").empty();
	        $("#sf02viewTbody").append(html);
			$("#totCnt").val(data.totCnt);
		});
	};
	
	function insertSF02() {

		var sendData = {
			"seq01" : parseInt($("#seq01").val()),
        	"text" : $("#form #text").val().trim(),
        	"nowPage" : 1
        };

		gfn_ajaxRequestBody("sf02","POST" , sendData , function(data) {
			showSF02List(1);
		});
	};
	
	function showSF02ListMore(page) {
		if($("#totCnt").val() <=$("#sf02viewTbody tr").length - 1) {
			return;
		}
		
		gfn_ajaxRest("sf02/" + parseInt($("#seq01").val()) + "/" + 0 + "/" + page , "GET" , function(data) {
			var html = drawTable(data);
			
			$('#sf02viewTbody > tr:last').remove();
			html += '<tr>';
			html += '<td><input type="button" value="더보기" onclick="showSF02ListMore('+ (data.nowPage + 1) +')"></td>';
			html += '</tr>';
			$('#sf02viewTbody').append(html);
		});
	}

	function drawTable(data) {
		var html = "";
		for (var i = 0; i < data.list.length; i++) {
       		 html += '<tr>';
          	 html += '<td id="seq' + data.list[i].seq + '" style="display:none;">' + data.list[i].seq + '</td>';
	         html += '<td scope="col" width="50">' + data.list[i].userid + '</td>';
	         html += '<td scope="col" width="100">' + data.list[i].text + '</td>';
	         html += '<td scope="col" width="20">' + data.list[i].reply + '</td>';
	         html += '<td scope="col" width="30">' + data.list[i].good + '</td>';
	         html += '<td scope="col" width="70">' + gfn_dateFormat(data.list[i].regdate) + '</td>';
	         if(data.list[i].deleteYn) {
	        	 html += '<td scope="col" width="50">' + 
		            '<input type="button" value="삭제" onclick="deleteSF02('+ data.list[i].seq + ',' + data.list[i].seq01 +')">'
		            + '</td>';
	         } else {
	          	html += '<td scope="col" width="50"></td>';
	         }   
	         html += '<td scope="col" width="50">';
	         html += '<input id="replyButton" type="button" value="댓글" onclick="showSF02ReplyList('+ data.list[i].seq + ',' + data.list[i].seq01 +')">';
	         html += '</td>';
	         html += '</tr>';
        }
		return html;
	}
	
	function showSF02ReplyList(seq, seq01) {
		gfn_ajaxRest("sf02/" + seq01 + "/" + seq + "/" + 0, "GET" , function(data) {
			
			for (var i = 0; i < $("#sf02viewTbody").find("tr").length; i++) {
				var tr = $("#sf02viewTbody").find("tr").eq(i);
				if(tr.find("td").eq(0).text() == data.parents) {
					var html = "";
					for(var j = 0 ; j < data.list.length; j++) {
						html += '<tr>'; 
				        html += '<td scope="col" width="50">' + data.list[j].userid + '</td>';
				        html += '<td scope="col" width="100">' + data.list[j].text + '</td>';
						html += '<td scope="col" width="20">' + '</td>';
				        html += '<td scope="col" width="30">' + '</td>';
				        html += '<td scope="col" width="70">' + gfn_dateFormat(data.list[j].regdate) + '</td>';
				        if(data.list[j].deleteYn) {
				        	html += '<td scope="col" width="50">' + 
					        '<input type="button" value="삭제" onclick="deleteSF02(' + data.list[j].seq + ',' + data.list[j].seq01 +')">'
					        + '</td>';
				        } else {
				          	html += '<td scope="col" width="50"></td>';
				        }   
				        html += '<td scope="col" width="50">';
				        html += '</td>';
				        html += '</tr>';
					}
					
					html += '<tr><td scope="col" width="50"></td>';
					html += '<td scope="col" width="200">';
					html += '<input id="input' + data.parents + '" type="text"></td>';
					html += '<td scope="col" width="20"></td><td scope="col" width="30"></td><td scope="col" width="70"></td>';
					html += '<td scope="col" width="50">';
					html += '<input type="button" value="입력" onclick="insertSF02Reply(' + data.parents + ')"></td>';
					html += '<td scope="col" width="50"></td></tr>';
					tr.after(html);
					var id = "#seq" + data.parents;
					$(id).parent().find("#replyButton").attr('disabled', true);
					break;
				}
			}
		});	
	}
	
	function insertSF02Reply(seq) {

		var sendData = {
			"seq01" : parseInt($("#seq01").val()),
			"parents" : parseInt(seq),
        	"text" : $("#input" + seq).val().trim(),
        	"nowPage" : 1
        };

		gfn_ajaxRequestBody("sf02","POST" , sendData , function(data) {
			for (var i = 0; i < $("#sf02viewTbody").find("tr").length; i++) {
				var tr = $("#sf02viewTbody").find("tr").eq(i);
				if(tr.find("td").eq(0).text() == data.parents) {
					var html = "";
					for(var j = 0 ; j < data.list.length; j++) {
						html += '<tr>'; 
				        html += '<td scope="col" width="50">' + data.list[j].userid + '</td>';
				        html += '<td scope="col" width="100">' + data.list[j].text + '</td>';
						html += '<td scope="col" width="20">' + '</td>';
				        html += '<td scope="col" width="30">' + '</td>';
				        html += '<td scope="col" width="70">' + gfn_dateFormat(data.list[j].regdate) + '</td>';
				        if(data.list[j].deleteYn) {
				        	html += '<td scope="col" width="50">' + 
					        '<input type="button" value="삭제" onclick="deleteSF02(' + data.list[j].seq + ',' + data.list[j].seq01 +')">'
					        + '</td>';
				        } else {
				          	html += '<td scope="col" width="50"></td>';
				        }   
				        html += '<td scope="col" width="50">';
				        html += '</td>';
				        html += '</tr>';
					}
					
					html += '<tr><td scope="col" width="50"></td>';
					html += '<td scope="col" width="200">';
					html += '<input id="input' + data.parents + '" type="text"></td>';
					html += '<td scope="col" width="20"></td><td scope="col" width="30"></td><td scope="col" width="70"></td>';
					html += '<td scope="col" width="50">';
					html += '<input type="button" value="입력" onclick="insertSF02Reply(' + data.parents + ')"></td>';
					html += '<td scope="col" width="50"></td></tr>';
					$("#sf02viewTbody").find("tr").eq(i + 1).remove();
					tr.after(html);
					break;
				}
			}
		});
	};
	
	function like_sf01() {
		
		gfn_ajaxRest("sf01like/" + parseInt($("#seq01").val()), "PUT" , function(data) {
			if(data.result == 1) {
				$("#good").text(data.good);
				$("#button_like").attr('disabled', true);
			
			}
		});
	}
	
	function hate_sf01() {
		
		gfn_ajaxRest("sf01hate/" + parseInt($("#seq01").val()),"PUT" , function(data) {
			if(data.result == 1) {
				$("#good").text(data.good);
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
						
			<input type="hidden" id="seq01" name="seq" value="${sf01Vo.seq}" />
			<input type="hidden" id="title" name="title" value="${sf01Vo.title}" />
			<p>작성자 : ${sf01Vo.userid}</p>
			<p>제목 : ${sf01Vo.title}</p>
			<p>등록일자 : ${sf01Vo.showDate}</p>
			<c:if test="${sf01Vo.deleteYn}" var="result" scope="page">
       			<input id="button_delete" type="button" value="삭제">
    		</c:if>
    	</form>
		</div>
		<div>
			<div>내용 :<p>${sf01Vo.text}</p></div>
			<div>조회수 :<p>${sf01Vo.hit}</p></div>
			<div>좋아요 :<p id="good">${sf01Vo.good}</p></div>
			
			<c:choose>
				<c:when test="${sf01Vo.goodlog!='Y'}">
					<input id="button_like" type="button" value="좋아요">
				</c:when>
				<c:otherwise>
					<input id="button_like" type="button" value="좋아요" disabled>
				</c:otherwise>
			</c:choose>
			
			<c:choose>
				<c:when test="${sf01Vo.hatelog!='Y'}">
					<input id="button_hate" type="button" value="싫어요">
				</c:when>
				<c:otherwise>
					<input id="button_hate" type="button" value="싫어요" disabled>
				</c:otherwise>
			</c:choose>
			
			<p>파일명 : ${sf01Vo.filename}</p>
			<a href="/sf01File/${sf01Vo.seq}/${sf01Vo.regdate}">파일 받기</a>
		</div>
		
		<div class="container">
		<form id="form" method="post">
				댓글 내용<textarea id="text" name="text" rows="2" cols="40">
				</textarea>
				<input type="hidden" id="seq01" name="seq01" value="${sf01Vo.seq}" />
				<button id="btn_insert_button" type="button" >저장</button>
		</form>
		<table border = "1">
			<colgroup>
				<col width="50">
				<col width="200">
				<col width="20">
				<col width="30">
				<col width="70">
				<col width="50">
				<col width="50">
			</colgroup>
			<thead>
			<tr>
				<th scope="col">글쓴이</th>
				<th scope="col">내용</th>
				<th scope="col">댓글</th>
				<th scope="col">좋아요</th>
				<th scope="col">등록일자</th>
				<th scope="col">삭제</th>
				<th scope="col">더보기</th>
			</tr>
			</thead>
			<tbody id="sf02viewTbody">
		
			</tbody>
		</table>
		<div id="pagenation">
			<input type="hidden" id="totCnt" name="totCnt"/>
		</div>
		</div>
	</section>
	<p>
	<footer> <jsp:include page="footer.jsp" /> </footer>
</body>
</html>
