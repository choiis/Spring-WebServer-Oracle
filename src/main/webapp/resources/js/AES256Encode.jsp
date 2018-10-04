<%@ page import="com.singer.common.AES256Util" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String data = (String) request.getParameter("password");
	String result;
	if(data!=null && !"".equals(data)) {
		AES256Util aes256Util = new AES256Util();
		result = aes256Util.aesEncode(data);
	} else {
		result = "";
	}
%>
{"enc" : <%=result%>}
