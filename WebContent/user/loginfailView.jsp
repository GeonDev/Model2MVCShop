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
			alert('�Է��Ͻ� ��й�ȣ�� �ٸ��ϴ�.');
		}else {
			alert('��ġ�ϴ� ���̵� �����ϴ�.');				
		}		
		
		 history.go(-1);
	 </script>";

</body>
</html>