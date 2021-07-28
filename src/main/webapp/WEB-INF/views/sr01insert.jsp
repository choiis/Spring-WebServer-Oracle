<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<title>전국노래자랑</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
<style>
#map_ma {width:100%; height:400px; clear:both; border:solid 1px red;}
</style>
<link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" />
<jsp:include page="googleMap.jsp" />
<script src="<c:url value='/resources/js/common.js'/>" charset="utf-8"></script>
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
</head>
<body>
	<script type="text/javascript">
	var map;
	var latitude;
	var longitude;
	// 페이지 로딩이 완료되고, jQuery 스크립트 실행된다
	$(document).ready(function() {

		fn_PreSave = function() {
			
			if(gfn_isNull($("#title").val())) {
				alert("제목을 입력하세요");
				return false;
			}
			
			if(gfn_isNull($("#text").val())) {
				alert("내용을 입력하세요");
				return false;
			}

			if(gfn_isNull($("#markertitle").val())) {
				alert("가게명을 입력하세요");
				return false;
			}
			
			if(gfn_isNull(latitude) || gfn_isNull(longitude)) {
				alert("위치를 선택해 주세요");
				return false;
			}

			if(parseInt($("#grade").val()) === -1) {
				alert("점수를 입력하세요");
				return false;
			}

			//if(gfn_isNull($("input[name=sharefile]")[0].files[0])) {
			//	alert("파일을 입력해 주세요");
			//	return false;
			//}
			
			return true;
		}
		
		
		// 저장 버튼을 클릭할때 이벤트 발생
		$("#btn_insert_button").on("click", function(e) {

			if(!fn_PreSave()) {
				return ;
			}
			
			$("#mapx").val(latitude);
			$("#mapy").val(longitude);
			var form = $("form")[0];        
	        var formData = new FormData(form);

	        $.ajax({
	            cache : false,
	            url : "sr01", // 요기에
	            processData: false,
	            contentType: false,
	            type : 'POST', 
	            data : formData, 
	            success : function(data) {
	            	if(confirm("맛집 등록되었습니다!")) {
						location.href='/sr01/page';
					}
	            }, // success 
	            error : function(data, status, error) {
			    	var errorData = JSON.parse(data.responseText);
			    	alert(errorData.errorCode + " " + errorData.errorMsg);
			    }
	        }); 
	        
		});
		
		var myLatlng = new google.maps.LatLng(37.565557,126.977931); // 
		var Y_point			= 37.565557;		// 
		var X_point			= 126.977931;		// 
		var zoomLevel		= 18;				//
		var markerTitle		= "기준점";		// 
		var markerMaxWidth	= 300;		

		var contentString	= '<div>' +
		'<h2>지도기준</h2>'+
		'<p>시청역.</p>' +
		
		'</div>';
		var myLatlng = new google.maps.LatLng(Y_point, X_point);
		var mapOptions = {
			zoom: zoomLevel,
			center: myLatlng,
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};
		map = new google.maps.Map(document.getElementById('map_ma'), mapOptions);
		
		var marker = new google.maps.Marker({
			position: myLatlng,
			map: map,
			title: markerTitle
		});
		var infowindow = new google.maps.InfoWindow({
			content: contentString,
			maxWizzzdth: markerMaxWidth
		});
		
		
		google.maps.event.addListener(map, 'click', function(event) {

			placeMarker(event.latLng);
		});
	});
	
	function placeMarker(location) {
		
		var marker = new google.maps.Marker({
		    position: location,
		    map: map,
		});

		latitude = location.lat();
		longitude = location.lng();
		
		var infowindow = new google.maps.InfoWindow({
		    content: 'Latitude: ' + location.lat() + '<br>Longitude: ' + location.lng()
		});
		infowindow.open(map,marker);
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
		<div id="container_demo">
			<div id="wrapper">
				<div id="sr01insert" class="animate form">
					<h4>등록할 파일 정보를 입력해 주세요</h4>
					<form id="form" method="post" enctype="multipart/form-data" action="/sf01">
						제목:<input type="text" id="title" name="title"> <br>
						가게명 : <input type="text" id="markertitle" name="markertitle"> <br>
						내용<textarea id="text" name="text" rows="10" cols="40"></textarea>
						<input multiple="multiple" id="fileInput" type="file" name="file" />
						<input id="mapx" type="hidden" name="mapx" />
						<input id="mapy" type="hidden" name="mapy" />
					<button id="btn_insert_button" type="button" >저장</button>
					<select id="grade" name="grade">
  						 <option value=-1 selected>선택하기</option>
    					 <option value=0>0점</option>
   						 <option value=1>1점</option>
    					 <option value=2>2점</option>
    					 <option value=3>3점</option>
    					 <option value=4>4점</option>
    					 <option value=5>5점</option>
					</select>
					</form>
				</div>
			</div>
		</div>
			<div id="map_ma">
			</div>
	</section>
	<p>
	<footer> <jsp:include page="footer.jsp" /> </footer>
</body>
</html>
