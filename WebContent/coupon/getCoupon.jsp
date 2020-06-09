<%@page import="com.model2.mvc.service.domain.*"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>



<html lang="ko">
	
<head>
	<meta charset="EUC-KR">
	
	<!-- 참조 : http://getbootstrap.com/css/   참조 -->
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
			
			$("button.btn btn-primary").on("click", function() {
				self.location = "/coupon/listCoupon";
			});			
			
		});
		
	</script>	   
    
</head>

<body>

	<jsp:include page="/layout/toolbar.jsp" />

	<div class="container">
	
		<div class="page-header">
	       <h3 class=" text-info">쿠폰정보확인</h3>	
	    </div>	   	
	    
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>쿠폰 이름</strong></div>
			<div class="col-xs-8 col-md-4">${coupon.couponName}</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>할인율</strong></div>
			<div class="col-xs-8 col-md-4">${coupon.discountRatio} %</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>최대할인가</strong></div>
			<div class="col-xs-8 col-md-4">${coupon.maximumDiscount}</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>최소적용가격</strong></div>
			<div class="col-xs-8 col-md-4">${coupon.minimum_price}</div>
		</div>
		
		<hr/>
		
		
		<div class="row">
	  		<div class="col-md-12 text-center ">	  		
	  			<button type="button" class="btn btn-primary">확인</button>  					
	  		</div>
		</div>

	
	</div>

</body>
</html>