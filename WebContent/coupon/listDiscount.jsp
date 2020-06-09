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
			<h3>�����������</h3>
		</div>
	
	
		<div class="row">
	    	<div class="col-md-6 text-left">
		    	<p class="text-primary">
		    		��ü  ${resultPage.totalCount } �Ǽ�, ���� ${resultPage.currentPage}  ������
		    	</p>
		    </div>   
	   </div>
	   
	   <form method="post">
	   
	   <table class="table table-hover table-striped" >
			<thead>
	          <tr>
	            <th align="center">No</th>
	            <th align="left" >�����̸�</th>
	            <th align="left">���η�</th>
	            <th align="left">�ִ����ΰ�</th>
	            <th align="left">�ּ����밡��</th>
	            <th align="left">ȹ������</th>
	            <th align="left">��������</th>
	            <th align="left">����</th>
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
								<td align="left">${discount.discountCoupon.maximumDiscount} ��</td>	
								<td align="left">${discount.discountCoupon.minimum_price} ��</td>
								<td align="left">${discount.issuedDate}</td>
								<td align="left">${discount.expirationDate}</td>
								<td align="left">															
									<a href="/discount/deleteDiscount?discountNo=${discount.discountNo}">����</a>								
								</td>
							</tr>
							
						</c:forEach>	        
	        	</tbody> 	   	   
	  	 </table>
	  	 
		<!-- PageNavigation ���� ������ ���� ������ �κ� -->
		<input type="hidden" id="currentPage" name="currentPage" value=""/>		   
	   
	   </form>	
	</div>
	
	<jsp:include page="../common/pageNavigator_new.jsp"/>

</body>
</html>