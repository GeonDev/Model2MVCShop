<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

	function funcUpdateCoupon() {
		// Form ��ȿ�� ����
		var discount=document.detailForm.discountRatio.value;
		
		if(discount == null || discount < 0){
			alert("�������� �Է��� �ּ���.");
			return;
		}	
		
		$("form").attr("action", "/coupon/updateCoupon");
		$("form").submit();
	}
	
	function resetData() {
		//$("form")[0].reset();
		self.location = "/coupon/listCoupon";
	}



	
	$(function() {
		 $("button:contains('����')").on("click",function(){
			 fncEditProduct();
		 });		
		
		 $("button:contains('���')").on("click",function(){
			 resetData();
		 });
		 

		 
	});	

</script>
</head>

<body >

	<jsp:include page="/layout/toolbar.jsp" />	
	
	<div class="container">
	
	
		<div class="page-header">
			<h3 class=" text-info">��ǰ ����</h3>	
		</div>
		
		
		<form method="post">
		
		<input type="hidden" name="couponNo" value="${coupon.couponNo}">
		
		
		<div class="row">
			<div class="col-xs-4 col-md-2"><strong>���� �̸�</strong></div>
			<div class="col-xs-8 col-md-4">${coupon.couponName}</div>
		</div>
		
		<hr/>
		
		<div class="row">
			<div class="col-xs-4 col-md-2"><strong>������</strong></div>
			<div class="col-xs-8 col-md-4">
			<input type="text" name="discountRatio" class="form-control"  value="${coupon.discountRatio}"/>
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
			<div class="col-xs-4 col-md-2"><strong>�ִ� ���� ����</strong></div>
			<div class="col-xs-8 col-md-4">
			<input type="text" name="maximumDiscount" class="form-control"  value="${coupon.maximumDiscount}"/>
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
			<div class="col-xs-4 col-md-2"><strong>�ּ� ���� ����</strong></div>
			<div class="col-xs-8 col-md-4">
			<input type="text" name="minimum_price" class="form-control"  value="${coupon.minimum_price}"/>
			</div>
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
