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
	/*
	gfn_paging = function(url, eventName, size) {
		var html2 = "";		   
		for (var i = 0; i < size ; i++) {
			html2 += '<a href="#" onclick="' + eventName + '(' + i + ');">' + (i + 1) + '</a>  ';    
		}
		$("#" + url).html(html2);    
	}
	*/
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
		array[0] = 1;
		array[1] = 2;
		
		if(obj == undefined) {
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
});
