<%@page import="com.model2.mvc.service.domain.*"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.common.*" %>
<%@page import="com.model2.mvc.common.util.CommonUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
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
    
    <script type="text/javascript">
		function funcGetList(currentPage) {
			$("#currentPage").val(currentPage);
			$("form").attr("action", "/coupon/listCoupon");
			$("form").submit();
		}
    </script>
    
    
</head>

<body>

	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
	
	
	<div class="container">
		<div class="page-header text-info">
			<h3>쿠폰보유목록</h3>
		</div>
	
	
		<div class="row">
	    	<div class="col-md-6 text-left">
		    	<p class="text-primary">
		    		전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage}  페이지
		    	</p>
		    </div>   
	   </div>
	   
	   <form method="post">
	   
	   <table class="table table-hover table-striped" >
			<thead>
	          <tr>
	            <th align="center">No</th>
	            <th align="left" >쿠폰이름</th>
	            <th align="left">할인률</th>
	            <th align="left">최대할인가</th>
	            <th align="left">최소적용가격</th>
	            <th align="left">획득일자</th>
	            <th align="left">만료일자</th>
	            <th align="left">수정</th>
	          </tr>
	        </thead>  
	        
	        
	        <tbody>
			
					<c:set var="i" value="0" />
						<c:forEach var="discount" items="${list}">
							<c:set var="i" value="${ i+1 }" />
		
							<tr>
								<td align="center">	${i} </td>
								<td align="left">${discount.discountCoupon.couponName}</td>
								<td align="left">${discount.discountCoupon.discountRatio} %</td>
								<td align="left">${discount.discountCoupon.maximumDiscount} 원</td>	
								<td align="left">${discount.discountCoupon.minimum_price} 원</td>
								<td align="left">${discount.issuedDate}</td>
								<td align="left">${discount.expirationDate}</td>
								<td align="left">															
									<a href="/discount/deleteDiscount?discountNo=${discount.discountNo}">삭제</a>								
								</td>
							</tr>
							
						</c:forEach>	        
	        	</tbody> 	   	   
	  	 </table>
	  	 
		<!-- PageNavigation 선택 페이지 값을 보내는 부분 -->
		<input type="hidden" id="currentPage" name="currentPage" value=""/>		   
	   
	   </form>	
	</div>
	
	<jsp:include page="../common/pageNavigator_new.jsp"/>

</body>
</html>