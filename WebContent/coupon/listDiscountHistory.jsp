<%@ page import="com.model2.mvc.service.domain.*"%>
<%@ page import="com.model2.mvc.common.*"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



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
		function funcGetList(currentPage){
						
			var keyword = $("input[name = 'searchKeyword']").val(); 		
			var condition = $("#Condition").val();
			var url = "/product/listProduct?menu=";
			
			
	 		if(condition == 0){
				
			}
			
			if(condition == 2){
				
			}		
		
	
			$("#currentPage").val(currentPage);
			$("form").attr("method", "POST").attr("action", url).submit();
		}
		
		
	
		$(function() {

	
			$("button:contains('검색')").on("click", function() {			
				funcGetList(1);
			});					
			
			
			$("i").on("click",function(){					
				var url = "/purchase/updateTranCodeByProd?prodNo=";
				url = url.concat($(this).children('span').text(),"&tranCode=2"); 		
				self.location.href = url;				
			});
			
			
		});
	</script>
</head>

<body>

		<jsp:include page="/layout/toolbar.jsp" />
		<div class="container">
		
		<div class="page-header text-info">
			<h3>쿠폰사용현황</h3>
	    </div>
	    
	    <div class="row">	    
		    <form class="form-inline" name="detailForm">
		    	<div class="col-md-3 text-left">
		    		<select id ="searchOrder" name="searchOrder" class="form-control" >
						<option value="0"
							${ ! empty search.searchOrder && search.searchOrder==0 ? "selected" : "" }>전체</option>
						<option value="1"
							${ ! empty search.searchOrder && search.searchOrder==1 ? "selected" : "" }>미사용</option>
						<option value="2"
							${ ! empty search.searchOrder && search.searchOrder==1 ? "selected" : "" }>사용</option>
					</select>
		    	</div>
		    	
		    	<div class="col-md-9 text-right">
		    		<select id="Condition" name="searchCondition" class="form-control">
						<option value="0"
							${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>쿠폰번호</option>
						<option value="1"
							${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>보유자ID</option>		
					</select> 											    	
		    	
		    		<input id="Keyword" type="text" name="searchKeyword" value="${search.searchKeyword}" class="form-control"/>
		    		<input id="optional" type="hidden" name="searchKeywordOptional" value="${search.searchKeywordOptional}" class="form-control" />
		    	
 		    		<button type="button" class="btn btn-default">검색</button>
		    	</div>
		    	
		    	<!-- PageNavigation 선택 페이지 값을 보내는 부분 -->
				<input type="hidden" id="currentPage" name="currentPage" value=""/>
		    	
		    </form>	    
		</div>
		
		<br/>
		
		
		<div class="row">
	    	<div class="col-md-6 text-left">
		    	<p class="text-primary">
		    		전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage}  페이지
		    	</p>
		    </div>   
	   </div>
				
		
		<table class="table table-hover table-striped" >
      
	        <thead>
	          <tr>
	            <th align="center">No</th>
	            <th align="left">보유자ID</th>	
	            <th align="left">쿠폰번호</th>
	            <th align="left">쿠폰이름</th>	       
	            <th align="left">쿠폰만료일</th>
	            <th align="left">사용여부</th>
	            <th align="left">사용상품</th>
	            <th align="left">사용일</th>	            
	          </tr>
	        </thead>
	       
			<tbody>
			
			<c:set var="i" value="0" />
				<c:forEach var="discount" items="${list}">
					<c:set var="i" value="${ i+1 }" />
					<tr >
						<td align="center">${i}</td>
						<td align="left" >${discount.owner.userId}</td>
						<td align="left" >${discount.discountCoupon.couponNo}</td>
						<td align="left" >${discount.discountCoupon.couponName}</td>
						<td align="left" >${discount.expirationDate}</td>
						<td align="left" >
							<c:if test="${empty discount.purchaseDate}">
								미사용
							</c:if>
							<c:if test="${!empty discount.purchaseDate}">
								사용
							</c:if>
						
						</td>		
						<td align="left" >${discount.purchaseProduct.prodName}</td>
						<td align="left" >${discount.purchaseDate}</td>								
					</tr>					
				</c:forEach>
	        
	        </tbody>
      
      </table>	
	  <h5 class="text-muted">사용기간 만료 쿠폰은 <strong class="text-danger">사용한 쿠폰</strong>으로 취급합니다.</h5>
		
		</div>
		
		<jsp:include page="../common/pageNavigator_new.jsp"/>		
	
	</body>
	
</html>