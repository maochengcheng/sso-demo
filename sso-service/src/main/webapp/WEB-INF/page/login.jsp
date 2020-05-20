<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>单点登录系统登录页面</title>
<script type="text/javascript" src="${ctx}/js/jquery-1.6.4.js"></script>
</head>
<body>
用户名：<input type="text" id="appkey"/> <br>
密码：<input type="text" id="password"/> 
<br>
<input type="button" value="登录" onclick="login()"/> 
<script type="text/javascript">

	var redirectUrl = "${redirect}";
	
	function getPageParams() {
	    var params = {
	        "appkey":$("#appkey").val(),
	        "password":$("#password").val(),
	    };
	    return params;
	}
	
	function login(){
		var params = getPageParams();
		$.ajax({
			type:"post",
			url:"/sso/login",
			contentType:"application/json;charset=UTF-8",
			dataType:"json",
			data:JSON.stringify(params),
			success:function(result){
				if(result.code==2000){
					window.location = redirectUrl;
				}else{
					alert("登录失败，原因是：" + result.data);
				}
				
			}
		})
	}
</script>
</body>
</html>