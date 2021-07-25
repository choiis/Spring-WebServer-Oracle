$(document).ready(function() {

	/** ajax 공통 함수*/
	gfn_ajax = function(url, type, sendData, callback) {

		$.ajax({
		    url : "/" + url,
		    type : type,
		    dataType : "json",
		    cache : false,
		    data : gfn_jsonSerialize(sendData),
		    contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		    success : callback,
		    error : function(data, status, error) {
		    	var errorData = JSON.parse(data.responseText);
		    	alert(errorData.errorCode + " " + errorData.errorMsg);
		    }
		});
	}
	
	/** ajax_requestBody 공통 함수*/
	gfn_ajaxRequestBody = function(url, type, sendData, callback) {
		
		$.ajax({
		    url : "/" + url,
		    type : type,
		    dataType : "json",
		    cache : false,
		    data : JSON.stringify(sendData),
		    contentType:"application/json;charset=UTF-8",
		    success : callback,
		    error : function(data, status, error) {
		    	var errorData = JSON.parse(data.responseText);
		    	alert(errorData.errorCode + " " + errorData.errorMsg);
		    }
		});
	}
	
	/** ajax_requestBody 공통 함수*/
	gfn_ajaxRest = function(url, type, callback) {
		
		$.ajax({
		    url : "/" + url,
		    type : type,
		    success : callback,
		    error : function(data, status, error) {
		    	var errorData = JSON.parse(data.responseText);
		    	alert(errorData.errorCode + " " + errorData.errorMsg);
		    }
		});
	}
	
	/** paging 공통 함수*/
	gfn_paging = function(pStartPage, pMaxPage, pLoc, pName) {
		var pagination ="";
		
		$("#startPage").attr('value',pStartPage);
		$("#maxPage").attr('value',pMaxPage);
		
		var forstart = 0;
		var forend = 0;
		
		var currPage = Math.floor((pStartPage - 1) / 10 + 1);
		
		if(currPage > 1) {
			forstart = (currPage - 1) * 10 + 1;
		} else {
			forstart = 1;
		}
		
		forend = forstart + 9;
		if(pMaxPage < forend) {
			forend = pMaxPage;
		}
		
		if(pStartPage > 10) {
			pagination += '<a href="# class="btn_prev_fisrt" name="page_start"></a>';
			pagination += '<a href="# class="btn_prev" name="page_prev"></a>';
		}
		
		pagination += '<span id="pageing" name="' + pName + '">';
		for(var i = forstart; i<= forend; i++) {
			if(pStartPage == i) {
				pagination += '<a href="#" id="page_move' + i + '" name="' + pName + '" value="' + i + '" "class="on" >' + i + '</a>  ';
			} else {
				pagination += '<a href="#" id="page_move' + i + '" name="' + pName + '" value="' + i + '" >' + i + '</a>';
			}
		}
		pagination += '</span>'
	
		if(forend < pMaxPage) {
			pagination += '<a href="# class="btn_next_fisrt" name="page_next"></a>';
			pagination += '<a href="# class="btn_next" name="page_last"></a>';
		}
		
		$(pLoc).empty();
		$(pLoc).append(pagination);
	}
	
	
	/** null 체크*/
	gfn_isNull = function(obj) {

		var array = new Array();

		if(obj == undefined) {
			return true;
		} else if(obj == null) {
			return true;
		} else if(obj === null) {
			return true;
		} else if(typeof obj == "string") {
			if(obj === "") {
				return true;
			}
		} else if(typeof obj == "object") {
			if(obj.length == 0) {
				return true;
			}
		}
		
		return false;
	}
	
	/** 숫자포맷 체크*/	
	gfn_isNumber = function(obj) {
		var regex= /^[0-9]*$/
		return regex.test(obj);
	}
	
	/** 전화번호 체크*/	
	gfn_isPhoneNumber = function(obj) {
		var regex=  /^\d{3}-\d{3,4}-\d{4}$/;
		return regex.test(obj);
	}
	
	/** 이메일 체크*/	
	gfn_isEmail = function(obj) {
		var regex=  /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		return regex.test(obj);
	}
	
	/** 파일 확장자 */	
	gfn_getFileType = function(filename) {
		var len = filename.length;
		var lastDot = filename.lastIndexOf('.');
		// 확장자 명만 추출한 후 소문자로 변경
		var fileType = filename.substring(lastDot, len).toLowerCase();
		return fileType;
	}
	
	/** 이미지 파일 확인*/	
	gfn_IsImage = function(filename) {
		if(gfn_isNull(filename)) {
			return false;
		} else {
			var fileType = gfn_getFileType(filename);
			
			if(fileType == '.png' || fileType == '.bmp' || fileType == '.jpg' 
				|| fileType == '.jpeg' || fileType == '.jpe' || fileType == '.jfif' 
				|| fileType == '.gif' || fileType == '.tif' || fileType == '.tiff' ) {
				return true;
			}
			return false;
		}
	}
	
	/** 동영상 파일 확인*/	
	gfn_IsVideo = function(filename) {
		if(gfn_isNull(filename)) {
			return false;
		} else {
			var fileType = gfn_getFileType(filename);
			
			if(fileType == '.avi' || fileType == '.mp4' || fileType == '.wmv' 
				|| fileType == '.asf' || fileType == 'mpe' || fileType == '.asx' 
				|| fileType == '.mpeg' || fileType == '.rm' || fileType == '.mov'
				|| fileType == '.dat' || fileType == '.flv' ) {
				return true;
			}
			return false;
		}
	}
	
	/** 동영상 파일 확인*/	
	gfn_IsAudio = function(filename) {
		if(gfn_isNull(filename)) {
			return false;
		} else {
			var fileType = gfn_getFileType(filename);
			
			if(fileType == '.mp3' || fileType == '.ogg' || fileType == '.wma' 
				|| fileType == '.wav' || fileType == 'au' || fileType == '.rm' 
				|| fileType == '.mid') {
				return true;
			}
			return false;
		}
	}
	
	/** 날짜로 변환*/
	gfn_dateFormat = function(str) {
		return str.substr(0, 4) + "/" + str.substr(4, 2) + "/" + str.substr(6, 2);
	}
	
	/** 아이디 체크*/	
	gfn_isId = function(obj) {
		var regex= /^[a-z]+[a-z0-9]{5,19}$/g;
		return regex.test(obj);
	}
	
	/** 한글 체크*/	
	gfn_isKor = function(obj) {
		var regex= /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
		return regex.test(obj);
	}

	/** Date 반환*/	
	gfn_toDate = function(dateId) {
		var date = dateId;
		var year = date.substr(0, 4);
		var month = date.substr(4, 2);
		var day = date.substr(6, 2);
		return new Date(year, month - 1, day);
	}

	/** 달의 첫째날 반환*/
	gtn_firstDate = function(dateId) {
		var date = dateId;
		var year = date.substr(0, 4);
		var month = date.substr(4, 2);
		var day = "01";
		return new Date(year, month - 1, day);
	}
	
	/** 이전 날짜 반환*/
	gtn_beforeDate = function(dateId, sub_date) {
		var dayOfMonth = dateId.getDate();
		dateId.setDate(dayOfMonth - sub_date);
		dateId = gfn_parseDate(dateId);
		return dateId;
	}

	/** Date 파싱*/
	gfn_parseDate = function(dateId) {

		var year = "" + (dateId.getFullYear());
		var month = "" + (dateId.getMonth() + 1);
		var day = "" + (dateId.getDate());
		
		if(month.length == 1) {
			month = "0" + month;
		}
		if(day.length == 1) {
			day = "0" + day;
		}
		
		return "" + year + month + day;
	}

	/** Date 차이*/
	gfn_diffDate = function(fromDate, toDate){
		if(gfn_isNull(fromDate) || gfn_isNull(toDate)) {
			return;
		}
		if(fromDate.length == 10) {
			fromDate = fromDate.replace(/[\-./]/gi , "");
		}
		if(toDate.length == 10) {
			toDate = toDate.replace(/[\-./]/gi , "");
		}
		
		var frYear = fromDate.substring(0, 4);
		var frMonth = fromDate.substring(4, 6);
		var frDate = fromDate.substring(6, 8);
		
		var toYear = toDate.substring(0, 4);
		var toMonth = toDate.substring(4, 6);
		var toDate = toDate.substring(6, 8);
		
		var fromDt = new Date(frYear , frMonth , frDate);
		var toDt = new Date(toYear , toMonth , toDate);
		
		var diff = toDt - fromDt;
		var currDay = 25 * 60 * 60 * 1000;
		
		return parseInt(diff / currDay);
	}
	

	/** 특수문자 체크*/
	gfn_isSpecialChar = function(str) {
		if(str.length > 0 ){
			for (var i = 0 ; i < str.length ; i++) {
				var chCode = str.charCodeAt(i);
				if(chCode < 128 && !(chCode >= 48 && chCode <=57) && 
					!(chCode >= 65 && chCode <=90) && (chCode >= 97 && chCode <=122)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	/** 날짜 유효성 체크*/
	gfn_valDate = function(date) {
		var nYear = Number(date.substr(0,4));
	    var nMonth = Number(date.substr(4,2));
	    var nDay = Number(date.substr(6,2));

	    if (nYear < 1900 || nYear > 3000) { // 사용가능 하지 않은 년도 체크
	    	return false;
	    }

	    if (nMonth < 1 || nMonth > 12) { // 사용가능 하지 않은 달 체크
	    	return false;
	    }

	    // 해당달의 마지막 일자 구하기
	    var nMaxDay = new Date(new Date(nYear, nMonth, 1) - 86400000).getDate();
	    if (nDay < 1 || nDay > nMaxDay) { // 사용가능 하지 않은 날자 체크
	    	return false;
	    }
	    
	    return true;
	}
	
	/** 미래 날짜 선택 방지*/
	gfn_NotAfterToday = function(date) {
		if(date.length == 10) {
			date = date.replace(/[\-./]/gi , "");
		}
		
		var compYear = date.substring(0, 4);
		var compMonth = date.substring(4, 6) - 1;
		var compDate = date.substring(6, 8);
		
		var toDay = new Date();
		var compDay = new Date(compYear , compMonth , compDate);
		
		var diff = toDay - compDay;
		
		if(diff < 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/** select box 셋팅*/
	gfn_selectList = function(codeGrpCd , id) {
		var combo ='<option value="" selected>선택</option>';

		$.ajax({
		    url : "/comm/code/" + codeGrpCd,
		    type : "GET",
		    async: false,
		    success : function(data) {
		    	if(!gfn_isNull(data.commList)){
		    		if(data.commList.length > 0) {
		    			for(var i = 0 ; i < data.commList.length ;i++) {
		    				combo += '<option value=' + data.commList[i].codecd + '>' + data.commList[i].codenm + '</option>';
		    			}
		    			$("#" + id + "").append(combo);
		    		}
		    	}
		    },
		    error : function(data, status, error) {
		    	var errorData = JSON.parse(data.responseText);
		    	alert(errorData.errorCode + " " + errorData.errorMsg);
		    }
		});
	
	}
	
	/** select box 셋팅*/
	gfn_selectedBox = function(codeList , selectedCode , id) {
		var select ='<select id=' + id + '><option value="">선택</option>';
		
		for(var i = 0 ; i < codeList.length ;i++) {
			if(codeList[i].codecd == selectedCode) {
				select += '<option value=' + codeList[i].codecd + ' selected>' + codeList[i].codenm + '</option>';
			} else {
				select += '<option value=' + codeList[i].codecd + '>' + codeList[i].codenm + '</option>';
			}
		}
		select += '</select>';
		return select;
	}
	
	/** 공통 코드 조회*/	
	gfn_getCommCode = function(codeGrpCd) {
		var common_code;
		
		$.ajax({
		    url : "/comm/code/" + codeGrpCd,
		    type : "GET",
		    async: false,
		    success : function(data) {
		    	common_code = data.commList;
		    },
		    error : function(data, status, error) {
		    	var errorData = JSON.parse(data.responseText);
		    	alert(errorData.errorCode + " " + errorData.errorMsg);
		    }
		});
		
		return common_code;
	}
	
	/** 공통 코드 사용 */
	gfn_getCommCodeNm = function(common_code, code) {
		var result = "";
		
		$.each(common_code , function(idx, value) {
			if(code == value.codecd) {
				result = value.codenm;
			}
		});
		return result;
	} 
	
	gfn_alert = function (message, title) {
	    if (!gfn_isNull(title))
	        title = 'Alert';

	    if (!gfn_isNull(message))
	        message = 'No Message to Display.';

	    $('<div></div>').html( message ).dialog({
	        title: title,
	        resizable: false,
	        modal: true,
	        buttons: {
	            'Ok': function()  {
	                $( this ).dialog( 'close' );
	            }
	        }
	    });
	}

	/** AES256 암호화 */
	gfn_AES256_Encode = function (str) {
		var result = "";
		$.ajax({
			type : "POST",
		    url : '/common/resources/js/AES256Encode.jsp',
		    async : false,
		    data : {"password": str},
		    success : function(data) {
		    	
		    	result = data.password;
		    }
		});
		return result;
	}

	/** AES256 복호화 */
	gfn_AES256_Decode = function (str) {
		var result = "";
		$.ajax({
			type : "POST",
		    url : '/common/resources/js/AES256Decode.jsp',
		    async : false,
		    data : {"password": str},
		    success : function(data) {
		    	
		    	result = json.password;
		    }
		});
		return result;
	}

	/** Json 수동 직렬화 */
	gfn_jsonSerialize = function (json) {
		var result = "";
		for (var key in json) {
			if(!gfn_isNull(json[key])) {
				result += key;
				result += "=";
				result += encodeURIComponent(json[key]);
				result += "&";
			}
		}
		
		return result.slice(0, -1);
	}
	
	/** 브라우저 가져오기 */
	gfn_getBrowser = function () {
		//userAgent값을 모두 소문자로 변환하여 변수에 대입
	    var agt = navigator.userAgent.toLowerCase();
	    
	    if(agt.indexOf("chrome") != -1) {
	        return 'Chrome';
	    } else if(agt.indexOf("opera") != -1) {
	        return 'Opera';
	    } else if(agt.indexOf("firefox") != -1) {
	        return 'Firefox';
	    } else if(agt.indexOf("safari") != -1) {
	        return 'Safari';
	    }  else if(agt.indexOf("skipstone") != -1) {
	        return 'Skipstone';
	    } else if(agt.indexOf("msie") != -1 || agt.indexOf("trident") != -1) {
	    	//msie는 Expolrer 11d이전 버전, trident는 Explorer 11버전  
	    	return 'Internet Explorer';
	    } else if(agt.indexOf("netscape") != -1) {
	        return 'Netscape';
	    } else {
	        return 'Unknown';
	    }
	}
	
	/** 기기정보 가져오기 */
	gfn_getDevice = function () {
		var tempUser = navigator.userAgent;
		  
		// userAgent 값에 iPhone, iPad, ipot, Android 라는 문자열이 하나라도 존재한다면 모바일로 간주됨.
		if (tempUser.indexOf("iPhone") != -1) {
			return 'iPhone';
		} else if (tempUser.indexOf("iPad") != -1) {
			return 'iPad';
		} else if (tempUser.indexOf("iPot") != -1) {
			return 'iPot';
		} else if (tempUser.indexOf("Android") != -1) {
			return 'Android';
		} else {
			return 'PC';
		}
	}
});
