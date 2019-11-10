<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<title>전국노래자랑</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
<jsp:include page="googleMap.jsp" />
<style>
#map_ma {width:100%; height:400px; clear:both; border:solid 1px red;}
</style>
<link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" />
<script src="<c:url value='/resources/js/common.js'/>" charset="utf-8"></script>
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
</head>
<body>
	<script type="text/javascript">
	// 페이지 로딩이 완료되고, jQuery 스크립트 실행된다

	$(document).ready(function() {

		showSR01One();
		
		showSR02One();
		
		showSR03List(1);
		
		// 저장 버튼을 클릭할때 이벤트 발생
		$("#btn_insert_button").on("click", function(e) {
			
			if(!fn_PreSaveReply()) {
				return ;
			}
			
			insertSR03();
		});
		
		// 좋아요 버튼을 클릭할때 이벤트 발생
		$("#button_like").on("click", function(e) {
			if(confirm("좋아요 할까요?")) {
				like_sr01();
			}
		});
		
		// 싫어요 버튼을 클릭할때 이벤트 발생
		$("#button_hate").on("click", function(e) {
			if(confirm("싫어요 할까요?")) {
				hate_sr01();
			}
		});
		
		// 삭제 버튼을 클릭할때 이벤트 발생
		$("#button_grade").on("click", function(e) {
			if(parseInt($("#grade").val()) === -1) {
				alert("점수를 주세요!");
				return;
			}
			
			var sendData = {
				"seq" : $("#seq").val(),
				"grade"  : $("#grade").val()
			};
			gfn_ajaxRequestBody("sr02", "POST", sendData, function(data) {
				if(confirm("점수 주기 완료!")) {
					$("#avggrade").text(data.avggrade);	
					$("#button_grade").attr('disabled', true);
				}	

			});
		});
		
		// 삭제 버튼을 클릭할때 이벤트 발생
		$("#button_delete").on("click", function(e) {
			if(confirm("삭제할까요?")) {
				
				gfn_ajaxRest("sr01/" + parseInt($("#seq").val()), "DELETE" , function(data) {
					if(data.result) {
						location.href='/sr01page';		
					}
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
	
	
	function showSR01One() {
		
		gfn_ajaxRest("sr01One/" + parseInt($("#seq").val()), "GET" , function(data) {
		
			$("#writer").text(data.userid);
			$("#title").text(data.title);
			$("#text").text(data.text);
			$("#markertitle").text(data.markertitle);
			$("#hits").text(data.hit);
			$("#good").text(data.good);
			$("#avggrade").text(data.avggrade);
			// data.mapx
			// data.mapy
			if(!data.deleteYn) {
				$("#button_delete").hide();
			}
			var myLatlng = new google.maps.LatLng(data.mapx, data.mapy); // 
			var Y_point			= data.mapx;		// 
			var X_point			= data.mapy;		// 
			var zoomLevel		= 18;				//
			var markerTitle		= data.markertitle;		// 
			var markerMaxWidth	= 300;		

			var contentString	= '<div>' +
			'<h2>' + data.markertitle + '</h2>'+
			'</div>';
			var myLatlng = new google.maps.LatLng(Y_point, X_point);
			var mapOptions = {
				zoom: zoomLevel,
				center: myLatlng,
				mapTypeId: google.maps.MapTypeId.ROADMAP
			};
			var map = new google.maps.Map(document.getElementById('map_ma'), mapOptions);
			
			var marker = new google.maps.Marker({
				position: myLatlng,
				map: map,
				title: markerTitle
			});
			var infowindow = new google.maps.InfoWindow({
				content: contentString,
				maxWizzzdth: markerMaxWidth
			});
			
			if(data.photocnt > 0) {
				var html = "";
				for(var i = 0 ; i < data.photocnt; i++) {
					html += '<img id="showPhoto" alt="" name="photo" src="/sr01photo/'  + data.seq + '/' + i + '" height="200px" width="170px"/>';
					html += '<br>';
				}
				$("#photoDiv").empty();
		        $("#photoDiv").append(html);
			}
		});
	};
	

	function showSR02One() {
		
		gfn_ajaxRest("sr02/" + parseInt($("#seq").val()), "GET" , function(data) {
			
			if(data.result === 1) {
				$("#button_grade").attr('disabled', true);
				$("#grade").attr('disabled', true);
			}
		});
	};
	
	function deleteSR03(seq, seq01) {
		
		if(confirm("삭제할까요?")) {
			
			gfn_ajaxRest("sr03/" + seq + "/" + seq01 + "/" + seq , "DELETE" , function(data) {
				var html = drawTable(data);

				html += '<tr>';
				html += '<td><input type="button" value="더보기" onclick="showSR03ListMore('+ (data.nowPage + 1) +')"></td>';
				html += '</tr>';
		        $("#sr03viewTbody").empty();
		        $("#sr03viewTbody").append(html);
				$("#totCnt").val(data.totCnt);
			});
		}
	}
	
	
	function showSR03List(page) {
		
		gfn_ajaxRest("sr03/" + parseInt($("#seq01").val()) + "/" + 0 + "/" + page , "GET" , function(data) {
			var html = drawTable(data);

			html += '<tr>';
			html += '<td><input type="button" value="더보기" onclick="showSR03ListMore('+ (data.nowPage + 1) +')"></td>';
			html += '</tr>';
			$("#sr03viewTbody").empty();
	        $("#sr03viewTbody").append(html);
			$("#totCnt").val(data.totCnt);
		});
	};
	
	function insertSR03() {

		var sendData = {
			"seq01" : parseInt($("#seq01").val()),
        	"text" : $("#form #text").val().trim(),
        	"nowPage" : 1
        };

		gfn_ajaxRequestBody("sr03","POST" , sendData , function(data) {
			var html = drawTable(data);

	        html += '<tr>';
			html += '<td><input type="button" value="더보기" onclick="showSR03ListMore('+ (data.nowPage + 1) +')"></td>';
			html += '</tr>';
	        $("#sr03viewTbody").empty();
	        $("#sr03viewTbody").append(html);
			$("#totCnt").val(data.totCnt);
		});
	};
	
	function showSR03ListMore(page) {
		
		if($("#totCnt").val() <=$("#sr03viewTbody tr").length - 1) {
			return;
		}
		
		gfn_ajaxRest("sr03/" + parseInt($("#seq01").val()) + "/" + 0 + "/" + page , "GET" , function(data) {
			var html = drawTable(data);
			
			$('#sr03viewTbody > tr:last').remove();
			html += '<tr>';
			html += '<td><input type="button" value="더보기" onclick="showSR03ListMore('+ (data.nowPage + 1) +')"></td>';
			html += '</tr>';
			$('#sr03viewTbody').append(html);
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
		            '<input type="button" value="삭제" onclick="deleteSR03('+ data.list[i].seq + ',' + data.list[i].seq01 +')">'
		            + '</td>';
	         } else {
	          	html += '<td scope="col" width="50"></td>';
	         }   
	         html += '<td scope="col" width="50">';
	         html += '<input id="replyButton" type="button" value="댓글" onclick="showSR03ReplyList('+ data.list[i].seq + ',' + data.list[i].seq01 +')">';
	         html += '</td>';
	         html += '</tr>';
        }
		return html;
	}
	
	function showSR03ReplyList(seq, seq01) {
		gfn_ajaxRest("sr03/" + seq01 + "/" + seq + "/" + 0, "GET" , function(data) {
			
			for (var i = 0; i < $("#sr03viewTbody").find("tr").length; i++) {
				var tr = $("#sr03viewTbody").find("tr").eq(i);
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
					        '<input type="button" value="삭제" onclick="deleteSR03(' + data.list[j].seq + ',' + data.list[j].seq01 +')">'
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
					html += '<input type="button" value="입력" onclick="insertSR03Reply(' + data.parents + ')"></td>';
					html += '<td scope="col" width="50"></td></tr>';
					tr.after(html);
					var id = "#seq" + data.parents;
					$(id).parent().find("#replyButton").attr('disabled', true);
					break;
				}
			}
		});	
	}
	
	function insertSR03Reply(seq) {

		var sendData = {
			"seq01" : parseInt($("#seq01").val()),
			"parents" : parseInt(seq),
        	"text" : $("#input" + seq).val().trim(),
        	"nowPage" : 1
        };

		gfn_ajaxRequestBody("sr03","POST" , sendData , function(data) {
			for (var i = 0; i < $("#sr03viewTbody").find("tr").length; i++) {
				var tr = $("#sr03viewTbody").find("tr").eq(i);
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
					        '<input type="button" value="삭제" onclick="deleteSR03(' + data.list[j].seq + ',' + data.list[j].seq01 +')">'
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
					html += '<input type="button" value="입력" onclick="insertSR03Reply(' + data.parents + ')"></td>';
					html += '<td scope="col" width="50"></td></tr>';
					$("#sr03viewTbody").find("tr").eq(i + 1).remove();
					tr.after(html);
					break;
				}
			}
		});
	};
	
	function like_sr01() {
		
		gfn_ajaxRest("sr01like/" + parseInt($("#seq").val()), "PUT", function(data) {
			if(data.result == 1) {
				$("#good").text(data.good);
				$("#button_like").attr('disabled', true);
			
			}
		});
	};
	
	function hate_sr01() {
		
		gfn_ajaxRest("sr01hate/" + parseInt($("#seq").val()), "PUT" , function(data) {
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
			
    	</form>
		</div>
		<div id="showDiv">
			<div>작성자 :<span id="writer"></span></div>
			<div>제목 :<span id="title"></span></div>
			<div>가게이름 :<span id="markertitle"></span></div>
			<div>내용 :<span id="text"></span></div>
			<div>조회수 :<span id="hits"></span></div>
			<div>좋아요 :<span id="good"></span></div>
			<div>점수 :<span id="avggrade"></span></div>
			<input id="button_delete" type="button" value="삭제">
			<input id="button_like" type="button" value="좋아요">
			<input id="button_hate" type="button" value="싫어요">

    	</div>

		<div class="container">
		<select id="grade" name="grade">
  		 <option value=-1 selected>선택하기</option>
    	 <option value=0>0점</option>
   		 <option value=1>1점</option>
    	 <option value=2>2점</option>
    	 <option value=3>3점</option>
    	 <option value=4>4점</option>
    	 <option value=5>5점</option>
		</select>
			<input id="button_grade" type="button" value="점수주기">
		</div>
		<div id="map_ma">
		</div>
		<div id="photoDiv">
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
			<tbody id="sr03viewTbody">
		
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
