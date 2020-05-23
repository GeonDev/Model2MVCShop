<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<script>
	
		var session ="<c:out value="${user.loginState}"/>"; 		
	
		if(session == 1){
			alert('입력하신 비밀번호가 다릅니다.');
		}else if(session == 2) {
			alert('일치하는 아이디가 없습니다.');				
		}else{
			alert('잘못된 요청입니다.');				
		}		
		
		 history.go(-1);
	 </script>";

</body>
</html>