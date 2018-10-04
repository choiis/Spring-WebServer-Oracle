$(document).ready(function() {

	/** ajax 공통 함수*/
	gfn_ajax = function(url, type, sendData, callback) {
		$.ajax({
		    url : "/common/" + url,
		    type : type,
		    dataType : "json",
		    cache : false,
		    data : sendData,
		    contentType:"application/json;charset=UTF-8",
		    success : callback,
		    error : function(request, status, error) {
		        console.log("code:" + request.status + "\n" + "error:" + error);
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
		var regex= /[0-9]/;
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
	
	/** select box 셋팅*/
	gfn_selectList = function(codeGrpCd , id) {
		var combo ='<option value="" selected>선택</option>';
		var formData = JSON.stringify({codegrp:codeGrpCd});
		$.ajax({
		    url : '/common/commCode.do',
		    contentType:"application/json;charset=UTF-8",
		    type : 'post',
		    dataType : 'json',
		    data : formData,
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
		    error : function(request, status, error) {
		        console.log("code:" + request.status + "\n" + "error:" + error);
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
		var formData = JSON.stringify({codegrp:codeGrpCd});
		var common_code;
		$.ajax({
		    url : '/common/commCode.do',
		    contentType:"application/json;charset=UTF-8",
		    type : 'post',
		    dataType : 'json',
		    async : false,
		    data : formData,
		    success : function(data) {
		    	common_code = data.commList;
		    },
		    error : function(request, status, error) {
		        console.log("code:" + request.status + "\n" + "error:" + error);
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
});
