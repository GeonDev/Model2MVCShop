<%@page import="com.model2.mvc.service.domain.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
    
    
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

	<script type="text/javascript" src="../javascript/calendar.js"></script>

	<script type="text/javascript">
		
		$(function() {	
			
			$("button:contains('���')").on("click", function() {				
				history.go(-1);
			});			
		
			
			$("button:contains('����')").on("click", function() {									
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
	       <h3 class=" text-info">������������</h3>
	       <h5 class="text-muted">���� �߼����� ���� ��ǰ�� ���� ���� �Ϻθ� ������ �� �ֽ��ϴ�.</h5>
	    </div>
	    
	    
	    <input type="hidden" name="tranNo" value="${purchase.tranNo}">
	    
	    <div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>�����ھ��̵�</strong></div>
			<div class="col-xs-8 col-md-4">${purchase.buyer.userId}</div>
		</div>
		
		<hr/>
		
		<div class="row">
			<div class="col-xs-4 col-md-2"><strong>���Ź��</strong></div>
			<div class="col-xs-8 col-md-4">
				<c:choose>
					<c:when test="${purchase.paymentOption == '0'}">					
						���ݱ���
					</c:when>
					<c:otherwise>			
						�ſ뱸��
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>������ �̸�</strong></div>
			<div class="col-xs-8 col-md-4">
				<input 	type="text" name="receiverName" class="form-control"  value="${purchase.receiverName}" />
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>������ ����ó</strong></div>
			<div class="col-xs-8 col-md-4">
				<input 	type="text" name="receiverPhone" class="form-control" value="${purchase.receiverPhone}" />
			</div>
		</div>
		
		<hr/>
		
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>����� �ּ�</strong></div>
			<div class="col-xs-8 col-md-4">
				<input 	type="text" name="divyAddr" class="form-control" value="${purchase.divyAddr}" />
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>���ſ�û����</strong></div>
			<div class="col-xs-8 col-md-4">
				<input 	type="text" name="divyAddr" class="form-control" value="${purchase.divyRequest}" />
			</div>
		</div>
		
		<hr/>
		
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>����������</strong></div>
			<div class="col-xs-8 col-md-4">
				<input 	type="text" name="divyDate" class="form-control" placeholder="���������� �����ϼ���" readonly />
			</div>
			<i class="glyphicon glyphicon-calendar" ></i>
		</div>		
		
		<hr/>
		
		
		</form>
		
		<div class="row">
	  			<div class="col-md-12 text-right ">
	  		
	  			<button type="button" class="btn btn-primary">����</button>	 
	  			<button type="button" class="btn btn-primary">���</button>	  			

	  			</div>
			</div>	    
	
	</div>

</body>
</html>