<%@page import="com.model2.mvc.service.domain.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
    
    
<!DOCTYPE html>
<html>
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

	<script type="text/javascript" src="../javascript/calendar.js"></script>

	<script type="text/javascript">
		
		$(function() {	
			
			$("button:contains('취소')").on("click", function() {				
				history.go(-1);
			});			
		
			
			$("button:contains('수정')").on("click", function() {									
				$("form").attr("action", "/purchase/updatePurchase");
				$("form").submit();
				
			});
			
			$(".glyphicon glyphicon-calendar").on("click", function() {			
				show_calendar('document.detailForm.manuDate', document.detailForm.manuDate.value);
			});
			
				
		});
		
		
	</script>

</head>

<body>

	<jsp:include page="/layout/toolbar.jsp" />


	<form name ="updatePurchase"  method="post">

	
	<div class="container">
	
		<div class="page-header">
	       <h3 class=" text-info">구매정보수정</h3>
	       <h5 class="text-muted">아직 발송하지 않은 상품의 구매 정보 일부를 수정할 수 있습니다.</h5>
	    </div>
	    
	    
	    <input type="hidden" name="tranNo" value="${purchase.tranNo}">
	    
	    <div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>구매자아이디</strong></div>
			<div class="col-xs-8 col-md-4">${purchase.buyer.userId}</div>
		</div>
		
		<hr/>
		
		<div class="row">
			<div class="col-xs-4 col-md-2"><strong>구매방법</strong></div>
			<div class="col-xs-8 col-md-4">
				<c:choose>
					<c:when test="${purchase.paymentOption == '0'}">					
						현금구매
					</c:when>
					<c:otherwise>			
						신용구매
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>구매자 이름</strong></div>
			<div class="col-xs-8 col-md-4">
				<input 	type="text" name="receiverName" class="form-control"  value="${purchase.receiverName}" />
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>구매자 연락처</strong></div>
			<div class="col-xs-8 col-md-4">
				<input 	type="text" name="receiverPhone" class="form-control" value="${purchase.receiverPhone}" />
			</div>
		</div>
		
		<hr/>
		
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>배송지 주소</strong></div>
			<div class="col-xs-8 col-md-4">
				<input 	type="text" name="divyAddr" class="form-control" value="${purchase.divyAddr}" />
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>구매요청사항</strong></div>
			<div class="col-xs-8 col-md-4">
				<input 	type="text" name="divyAddr" class="form-control" value="${purchase.divyRequest}" />
			</div>
		</div>
		
		<hr/>
		
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>배송희망일자</strong></div>
			<div class="col-xs-8 col-md-4">
				<input 	type="text" name="divyDate" class="form-control" placeholder="배송희망일을 선택하세요" readonly />
			</div>
			<i class="glyphicon glyphicon-calendar" ></i>
		</div>		
		
		<hr/>
		
		
		</form>
		
		<div class="row">
	  			<div class="col-md-12 text-right ">
	  		
	  			<button type="button" class="btn btn-primary">수정</button>	 
	  			<button type="button" class="btn btn-primary">취소</button>	  			

	  			</div>
			</div>	    
	
	</div>

</body>
</html>