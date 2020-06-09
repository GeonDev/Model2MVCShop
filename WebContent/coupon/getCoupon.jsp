<%@page import="com.model2.mvc.service.domain.*"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>



<html lang="ko">
	
<head>
	<meta charset="EUC-KR">
	
	<!-- ���� : http://getbootstrap.com/css/   ���� -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	<!-- Bootstrap Dropdown Hover CSS -->
   	<link href="/css/animate.min.css" rel="stylesheet">
   	<link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
   
    <!-- Bootstrap Dropdown Hover JS -->
   	<script src="/javascript/bootstrap-dropdownhover.min.js"></script>
	
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
 		body {
            padding-top : 50px;
        }
    </style>   
    
    
    <script type="text/javascript">		

		 $(function() {
			//==> DOM Object GET 3���� ��� ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
			
			$( "button:contains('Ȯ��')" ).on("click" , function() {
				history.go(-1);
			});			
			
		});
		
	</script>	   
    
</head>

<body>

	<jsp:include page="/layout/toolbar.jsp" />

	<div class="container">
	
		<div class="page-header">
	       <h3 class=" text-info">��������Ȯ��</h3>	
	    </div>
	    
	   	<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>����������ȣ</strong></div>
			<div class="col-xs-8 col-md-4">${coupon.couponNo}</div>
		</div>
	    
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>���� �̸�</strong></div>
			<div class="col-xs-8 col-md-4">${coupon.couponName}</div>
		</div>
		
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>������</strong></div>
			<div class="col-xs-8 col-md-4">${coupon.discountRatio} %</div>
		</div>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>�ִ����ΰ�</strong></div>
			<div class="col-xs-8 col-md-4">${coupon.maximumDiscount}</div>
		</div>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>�ּ����밡��</strong></div>
			<div class="col-xs-8 col-md-4">${coupon.minimum_price}</div>
		</div>
		
		
		<div class="row">
	  		<div class="col-md-12 text-center ">	  		
	  			<button type="button" class="btn btn-primary">Ȯ��</button>  					
	  		</div>
		</div>

	
	</div>

</body>
</html>