<%@ page import="com.model2.mvc.service.domain.*"%>
<%@ page import="com.model2.mvc.common.*"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



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
   
   
   <!-- jQuery UI toolTip ��� CSS-->
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

	
			$("button:contains('�˻�')").on("click", function() {			
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
			<h3>���������Ȳ</h3>
	    </div>
	    
	    <div class="row">	    
		    <form class="form-inline" name="detailForm">
		    	<div class="col-md-3 text-left">
		    		<select id ="searchOrder" name="searchOrder" class="form-control" >
						<option value="0"
							${ ! empty search.searchOrder && search.searchOrder==0 ? "selected" : "" }>��ü</option>
						<option value="1"
							${ ! empty search.searchOrder && search.searchOrder==1 ? "selected" : "" }>�̻��</option>
						<option value="2"
							${ ! empty search.searchOrder && search.searchOrder==1 ? "selected" : "" }>���</option>
					</select>
		    	</div>
		    	
		    	<div class="col-md-9 text-right">
		    		<select id="Condition" name="searchCondition" class="form-control">
						<option value="0"
							${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>������ȣ</option>
						<option value="1"
							${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>������ID</option>		
					</select> 											    	
		    	
		    		<input id="Keyword" type="text" name="searchKeyword" value="${search.searchKeyword}" class="form-control"/>
		    		<input id="optional" type="hidden" name="searchKeywordOptional" value="${search.searchKeywordOptional}" class="form-control" />
		    	
 		    		<button type="button" class="btn btn-default">�˻�</button>
		    	</div>
		    	
		    	<!-- PageNavigation ���� ������ ���� ������ �κ� -->
				<input type="hidden" id="currentPage" name="currentPage" value=""/>
		    	
		    </form>	    
		</div>
		
		<br/>
		
		
		<div class="row">
	    	<div class="col-md-6 text-left">
		    	<p class="text-primary">
		    		��ü  ${resultPage.totalCount } �Ǽ�, ���� ${resultPage.currentPage}  ������
		    	</p>
		    </div>   
	   </div>
				
		
		<table class="table table-hover table-striped" >
      
	        <thead>
	          <tr>
	            <th align="center">No</th>
	            <th align="left">������ID</th>	
	            <th align="left">������ȣ</th>
	            <th align="left">�����̸�</th>	       
	            <th align="left">����������</th>
	            <th align="left">��뿩��</th>
	            <th align="left">����ǰ</th>
	            <th align="left">�����</th>	            
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
								�̻��
							</c:if>
							<c:if test="${!empty discount.purchaseDate}">
								���
							</c:if>
						
						</td>		
						<td align="left" >${discount.purchaseProduct.prodName}</td>
						<td align="left" >${discount.purchaseDate}</td>								
					</tr>					
				</c:forEach>
	        
	        </tbody>
      
      </table>	
	  <h5 class="text-muted">���Ⱓ ���� ������ <strong class="text-danger">����� ����</strong>���� ����մϴ�.</h5>
		
		</div>
		
		<jsp:include page="../common/pageNavigator_new.jsp"/>		
	
	</body>
	
</html>