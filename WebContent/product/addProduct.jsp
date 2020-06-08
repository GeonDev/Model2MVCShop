<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
<html lang="ko">

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
			 $("button:contains('확인')").on("click",function(){
					self.location =  "/product/listProduct?menu=manage";
			 });		
			
			 $("button:contains('추가등록')").on("click",function(){
					self.location =  "../product/addProductView.jsp";
			 });
			 
		});
	
	</script>


</head>

<body bgcolor="#ffffff" text="#000000">


	<jsp:include page="/layout/toolbar.jsp" />
	
	<div class="container">
	
	
		<div class="page-header">
	       <h3 class=" text-info">상품 정보 조회</h3>
	       <h5 class="text-muted">등록된 상품의 상세 정보 입니다.</h5>
	    </div>
	    
	    <div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>상품명</strong></div>
			<div class="col-xs-8 col-md-4">${product.prodName}</div>
		</div>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>상품상세정보</strong></div>
			<div class="col-xs-8 col-md-4">${product.prodDetail}</div>
		</div>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>제조일자</strong></div>
			<div class="col-xs-8 col-md-4">${product.manuDate}</div>
		</div>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>가격</strong></div>
			<div class="col-xs-8 col-md-4">${product.price}</div>
		</div>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>상품이미지</strong></div>
			<div class="col-xs-8 col-md-4">
					<td height="26">	
						<c:set var = "tempSrc" value = "/images/uploadFiles/" />
						<img src = "${tempSrc}${product.fileName}"  width="200"/>
					</td>			
			</div>
		</div>
		
		
		<div class="row">
	  		<div class="col-md-12 text-right ">
	  		
	  			<button type="button" class="btn btn-primary">확인</button>	 
	  			<button type="button" class="btn btn-primary">추가등록</button>	  			

	  		</div>
		</div>
		
		
		
		
	
	
	</div>


</body>
</html>
