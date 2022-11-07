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

		showSV01One(0);
		
		showSV04List(1);
		
		// 저장 버튼을 클릭할때 이벤트 발생
		$("#btn_insert_button").on("click", function(e) {
			
			if(!fn_PreSaveReply()) {
				return ;
			}
			
			insertSV04();
		});
		
		// 좋아요 버튼을 클릭할때 이벤트 발생
		$("#button_like").on("click", function(e) {
			if(confirm("좋아요 할까요?")) {
				like_sv01();
			}
		});
		
		// 싫어요 버튼을 클릭할때 이벤트 발생
		$("#button_hate").on("click", function(e) {
			if(confirm("싫어요 할까요?")) {
				hate_sv01();
			}
		});
		
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
				
				sendData.list = list;
				
				gfn_ajaxRequestBody("sv01/sv03", "POST", sendData, function() {
					if(confirm("투표 완료!")) {
						showSV01One(1);
					}	
					
				});
			}
		});
		
		// 삭제 버튼을 클릭할때 이벤트 발생
		$("#button_delete").on("click", function(e) {
			if(confirm("삭제할까요?")) {
				
				gfn_ajaxRest("sv01/" + parseInt($("#seq").val()), "DELETE" , function() {
					location.href='/sv01/page';
				});		
			}
		});
		
		
	});

	function fn_PreSaveReply() {

		if(gfn_isNull($("#form #text").val().trim())) {
			alert("댓글 내용을 입력하세요");
			return false;
		}

		return true;
	}	
	
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
		
		gfn_ajaxRest("sv01/one/" + parseInt($("#seq").val()) + "/" + recall, "GET" , function(data) {
		
			$("#writer").text(data.userid);
			$("#title").text(data.title);
			$("#text").text(data.text);
			$("#hits").text(data.hit);
			$("#multiselect").val(data.multiselect);
			$("#good").text(data.good);

			if(!data.deleteYn) {
				$("#button_delete").hide();
			}
			
			if(data.hatelog === "Y") {
				$("#button_hate").attr("disabled" , true);
			}
			if(data.goodlog === "Y") {
				$("#button_like").attr("disabled" , true);
			}
			
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
	
	function deleteSV04(seq, seq01) {
		
		if(confirm("삭제할까요?")) {
			
			gfn_ajaxRest("sv01/sv04/" + seq + "/" + seq01 + "/" + seq , "DELETE" , function() {
				showSV04List(1);
			});
		}
	}
	
	
	function showSV04List(page) {
		
		gfn_ajaxRest("sv01/sv04/" + parseInt($("#seq01").val()) + "/" + 0 + "/" + page , "GET" , function(data) {
			var html = drawTable(data);

			html += '<tr>';
			html += '<td><input type="button" value="더보기" onclick="showSV04ListMore('+ (data.nowPage + 1) +')"></td>';
			html += '</tr>';
			$("#sv04viewTbody").empty();
	        $("#sv04viewTbody").append(html);
			$("#totCnt").val(data.totCnt);
		});
	};
	
	function insertSV04() {

		var sendData = {
			"seq01" : parseInt($("#seq01").val()),
        	"text" : $("#form #text").val().trim(),
        	"nowPage" : 1
        };

		gfn_ajaxRequestBody("sv01/sv04","POST" , sendData , function(data) {
			showSV04List(1);
		});
	};
	
	function showSV04ListMore(page) {
		
		if($("#totCnt").val() <=$("#sv04viewTbody tr").length - 1) {
			return;
		}
		
		gfn_ajaxRest("sv01/sv04/" + parseInt($("#seq01").val()) + "/" + 0 + "/" + page , "GET" , function(data) {
			var html = drawTable(data);
			
			$('#sv04viewTbody > tr:last').remove();
			html += '<tr>';
			html += '<td><input type="button" value="더보기" onclick="showSV04ListMore('+ (data.nowPage + 1) +')"></td>';
			html += '</tr>';
			$('#sv04viewTbody').append(html);
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
		            '<input type="button" value="삭제" onclick="deleteSV04('+ data.list[i].seq + ',' + data.list[i].seq01 +')">'
		            + '</td>';
	         } else {
	          	html += '<td scope="col" width="50"></td>';
	         }   
	         html += '<td scope="col" width="50">';
	         html += '<input id="replyButton" type="button" value="댓글" onclick="showSV04ReplyList('+ data.list[i].seq + ',' + data.list[i].seq01 +')">';
	         html += '</td>';
	         html += '</tr>';
        }
		return html;
	}
	
	function showSV04ReplyList(seq, seq01) {
		gfn_ajaxRest("sv01/sv04/" + seq01 + "/" + seq + "/" + 0, "GET" , function(data) {
			
			for (var i = 0; i < $("#sv04viewTbody").find("tr").length; i++) {
				var tr = $("#sv04viewTbody").find("tr").eq(i);
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
					        '<input type="button" value="삭제" onclick="deleteSV04(' + data.list[j].seq + ',' + data.list[j].seq01 +')">'
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
					html += '<input type="button" value="입력" onclick="insertSV04Reply(' + data.parents + ')"></td>';
					html += '<td scope="col" width="50"></td></tr>';
					tr.after(html);
					var id = "#seq" + data.parents;
					$(id).parent().find("#replyButton").attr('disabled', true);
					break;
				}
			}
		});	
	}
	
	function insertSV04Reply(seq) {

		var sendData = {
			"seq01" : parseInt($("#seq01").val()),
			"parents" : parseInt(seq),
        	"text" : $("#input" + seq).val().trim(),
        	"nowPage" : 1
        };

		gfn_ajaxRequestBody("sv01/sv04","POST" , sendData , function(data) {
			for (var i = 0; i < $("#sv04viewTbody").find("tr").length; i++) {
				var tr = $("#sv04viewTbody").find("tr").eq(i);
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
					        '<input type="button" value="삭제" onclick="deleteSV04(' + data.list[j].seq + ',' + data.list[j].seq01 +')">'
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
					html += '<input type="button" value="입력" onclick="insertSV04Reply(' + data.parents + ')"></td>';
					html += '<td scope="col" width="50"></td></tr>';
					$("#sv04viewTbody").find("tr").eq(i + 1).remove();
					tr.after(html);
					break;
				}
			}
		});
	};
	
	function like_sv01() {
		
		gfn_ajaxRest("sv01/like/" + parseInt($("#seq").val()), "PATCH", function(data) {
			if(data.result == 1) {
				$("#good").text(data.good);
				$("#button_like").attr('disabled', true);
			
			}
		});
	};
	
	function hate_sv01() {
		
		gfn_ajaxRest("sv01/hate/" + parseInt($("#seq").val()), "PATCH" , function(data) {
			if(data.result == 1) {
				$("#good").text(data.good);
				$("#button_hate").attr('disabled', true);
			}
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
			<div>좋아요 :<span id="good"></span></div>
			<input id="button_delete" type="button" value="삭제">
			<input id="button_like" type="button" value="좋아요">
			<input id="button_hate" type="button" value="싫어요">
    		<div id="voteTable">
    		</div>
    	</div>

		<div class="container">
			<input id="button_vote" type="button" value="투표">
		</div>
		
		<div class="container">
		<form id="form" method="post">
				댓글 내용<textarea id="text" name="text" rows="2" cols="40">
				</textarea>
				<input type="hidden" id="seq01" name="seq01" value="${seq}" />
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
			<tbody id="sv04viewTbody">
		
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
