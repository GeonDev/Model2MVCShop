<%@page import="com.model2.mvc.service.domain.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>

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
    
    <script type="text/javascript" src="../javascript/calendar.js"></script>


<script type="text/javascript">


	function fncEditProduct() {

		var name = $("input[name = 'prodName']").val();
		var detail = $("input[name = 'prodDetail']").val();
		var manuDate = $("input[name = 'manuDate']").val();
		var price = $("input[name = 'price']").val();

		if (name == null || name.length < 1) {
			alert("��ǰ���� �ݵ�� �Է��Ͽ��� �մϴ�.");
			return;
		}
		if (detail == null || detail.length < 1) {
			alert("��ǰ�������� �ݵ�� �Է��Ͽ��� �մϴ�.");
			return;
		}
		if (manuDate == null || manuDate.length < 1) {
			alert("�������ڴ� �ݵ�� �Է��ϼž� �մϴ�.");
			return;
		}
		if (price == null || price.length < 1) {
			alert("������ �ݵ�� �Է��ϼž� �մϴ�.");
			return;
		}
		if (isNaN(price)) {
			alert("���ݿ��� ���ڸ� �Է��Ͽ��� �մϴ�.");
			return;
		}

		$("form").attr("action", "/product/updateProduct");
		$("form").submit();

	}
	
	
	$(function() {
		 $("button:contains('����')").on("click",function(){
			 fncEditProduct();
		 });		
		
		 $("button:contains('���')").on("click",function(){
			 $(self.location).attr("href","/product/listProduct?menu=manage");
		 });
		 
		$("i").on("click", function() {				
			/* show_calendar(document.detailForm.manuDate, document.detailForm.manuDate.value); */
			show_calendar('document.detailForm.manuDate', document.detailForm.manuDate.value)
		});
		 
	});	
	
	
	
</script>
</head>

<body>

<jsp:include page="/layout/toolbar.jsp" />


<div class="container">

	<div class="page-header">
		<h3 class=" text-info">��ǰ ����</h3>	
	</div>

	<form name="detailForm" method="post" enctype="multipart/form-data" >
	
	<input type="hidden" name="prodNo" value="${product.prodNo}"/>

	<div class="row">
		<div class="col-xs-4 col-md-2"><strong>��ǰ��</strong></div>
			<div class="col-xs-8 col-md-4">
			<input type="text" name="prodName" class="form-control" value="${product.prodName}"/>
			</div>
	</div>
			
	<hr/>
	
	<div class="row">
		<div class="col-xs-4 col-md-2"><strong>��ǰ������</strong></div>
			<div class="col-xs-8 col-md-4">
			<input type="text" name="prodDetail" class="form-control"  value="${product.prodDetail}"/>
			</div>
	</div>
	
	<hr/>
	
	
	<div class="row">
		<div class="col-xs-4 col-md-2"><strong>��������</strong></div>
			<div class="col-xs-8 col-md-4">
				<input type="text" name="manuDate" class="form-control" placeholder="�������ڸ� �����ϼ���" readonly/>			
			</div>			
			<i class="glyphicon glyphicon-calendar" ></i>			
	</div>
	
	<hr/>
	
	
	
	
	
	<div class="row">
		<div class="col-xs-4 col-md-2"><strong>����</strong></div>
			<div class="col-xs-8 col-md-4">
			<input type="text" name="price" class="form-control"  value="${product.price}"/>
			</div>
	</div>
	
	<hr/>
	
	<div class="row">
		<div class="col-xs-4 col-md-2"><strong>��ǰ�̹���</strong></div>
			<div class="col-xs-8 col-md-4">
			<input type="file" name="uploadFile" class="form-control"  multiple/>
			</div>
	</div>
</form>

			<div class="row">
				<div class="col-md-12 text-right ">			  	
			  		<button type="button" class="btn btn-primary">����</button>	 
			  		<button type="button" class="btn btn-primary">���</button>	  			
				</div>
			</div>



</body>
</html>