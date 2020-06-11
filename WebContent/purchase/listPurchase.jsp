<%@page import="com.model2.mvc.service.domain.*"%>
<%@ page import="com.model2.mvc.common.*" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR"  pageEncoding="EUC-KR"%>
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
   
   
   <!-- jQuery UI toolTip 사용 CSS-->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
	  body {
            padding-top : 50px;
        }
    </style>
    
	<script type="text/javascript">


	function funcGetList(currentPage) {
		$("#currentPage").val(currentPage);
		$("form").attr("action", "/purchase/listPurchase");
		$("form").submit();
	}
	
	
	$(function(){

		
		$("td:nth-child(2)").on("click",function(){		
			
			var url = "/product/getProduct?prodNo=";
			url = url.concat($(this).children('span').text(),"&menu=search"); 		
			self.location.href = url;
			
		});
		
		$("td:nth-child(6)").on("click",function(){		
			
			if($(this).children('span').length){
				var url = "/purchase/updateTranCode?tranNo=";
				url = url.concat($(this).children('span').text(),"&tranCode=3");				
				self.location.href = url;
			}
			
		});	
		
		
		$("td:nth-child(7)").on("click",function(){		
			
			var url = "/purchase/getPurchase?tranNo=";
			url = url.concat($(this).children('span').text()); 		
			self.location.href = url;
			
		});

		
	});
	
	
	
	
</script>
</head>

<body>


	<jsp:include page="/layout/toolbar.jsp" />	
	
	<div class="container">
		<div class="page-header text-info">
			<h3>구매목록조회</h3>
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
		            <th align="left" >상품이름</th>
		            <th align="left">구매자</th>
		            <th align="left">전화번호</th>
		            <th align="left">배송현황</th>
		            <th align="left">도착확인</th>
		            <th align="left">구매정보 수정</th>
		          </tr>
		        </thead>
			
				<tbody>
			
					<c:set var="i" value="0" />
						<c:forEach var="purchase" items="${list}">
							<c:set var="i" value="${ i+1 }" />
		
							<tr>
								<td align="center">${i}	</td>
								<td align="left" >${(purchase.purchaseProd).prodName}
									<span style="display:none" >${(purchase.purchaseProd).prodNo} </span>
								</td>
								<td align="left">${purchase.receiverName}</td>
								<td align="left">${purchase.receiverPhone}</td>												
								<td align="left">
								현재
								 	<c:choose>
							
									<c:when test="${purchase.tranCode == '1'}">		
										구매완료(배송 준비)
										</c:when>
									<c:when test="${purchase.tranCode == '2'}">
										배송중
									</c:when>
									<c:when test="${purchase.tranCode == '3'}">
										배송완료 
									</c:when>
							
									</c:choose>
								상태 입니다.					
								</td>
								<td align="left">
									<c:choose>			
										<c:when test="${purchase.tranCode =='2'}">				
											<i class="glyphicon glyphicon-ok" ></i>
											<span style="display:none" >${purchase.tranNo} </span>
										</c:when>					
									</c:choose>									
								</td>
								
								<td align="left">
								<c:if test="${purchase.tranCode == '1'}">
									<i class="glyphicon glyphicon-ok" ></i>
									<span style="display:none" >${purchase.tranNo} </span>
								</c:if>	
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