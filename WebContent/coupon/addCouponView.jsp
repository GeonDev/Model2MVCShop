<%@ page language="java" contentType="text/html; charset=EUC-KR"  pageEncoding="EUC-KR"%>
    
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


	
<script type="text/javascript">

	function funcAddCoupon(){
		//Form ��ȿ�� ����
	 	var name = $("input[name = 'couponName']").val(); 
		var discount = $("input[name = 'discountRatio']").val(); 
		var maximum = $("input[name = 'maximumDiscount']").val();
		var minimum =  $("input[name = 'minimum_price']").val();
		
		if(name == null || name.length<1){
			alert("�����̸��� �ݵ�� �Է��Ͽ��� �մϴ�.");
			return;
		}
		
		if(discount == null || discount.length<1){
			alert("�������� �ݵ�� �Է��Ͽ��� �մϴ�.");
			return;
		}
		
		if(isNaN(discount)){
			alert("���������� ���ڸ� �Է��� �ּ���");
			return;
		}
		
		if(discount < 0 || discount > 100){
			alert("�������� 1~100������ �Է� �����մϴ�.");
			return;
		}
		
		
		if(maximum == null || maximum.length<1){
			alert("�ִ����ΰ��� �ݵ�� �Է��Ͽ��� �մϴ�.");
			return;
		}
		
		if(isNaN(maximum)){
			alert("�ִ� ���ΰ��� ���ڸ� �Է��� �ּ���");
			return;
		}
		
		
		if(minimum == null || minimum.length<1){
			alert("�ּ����밡�� �ݵ�� �Է��Ͽ��� �մϴ�.");
			return;
		}
		
		if(isNaN(minimum)){
			alert("�ּ����밡���� ���ڸ� �Է��� �ּ���");
			return;
		}
		
		

		$("form").attr("action", "/coupon/addCoupon");
		$("form").submit();	

	}
	
	function resetData(){
		document.detailForm.reset();
	}
	
	
	$(function(){
		
		$("button:contains('���')").on("click", function() {
			funcAddCoupon();
		});
		
		$("button:contains('���')").on("click", function() {
			//window.history.back();
			self.location = document.referrer;
		});	
		
	});
	
	

</script>
</head>

<body>
	<jsp:include page="/layout/toolbar.jsp" />
	
	<div class="container">
	
		<div class="page-header">
			<h3 class=" text-info">���� ���</h3>	
		</div>
		
		<form  method="post">
		
			<div class="row">
				<div class="col-xs-4 col-md-2"><strong>������</strong></div>
					<div class="col-xs-8 col-md-4">
						<input type="text" name="couponName" class="form-control" placeholder="������ �Է�"/>
				</div>
			</div>
			
			<hr/>
			
			<div class="row">
				<div class="col-xs-4 col-md-2"><strong>������</strong></div>
					<div class="col-xs-8 col-md-4">
						<input type="text" name="discountRatio" class="form-control" placeholder="�ۼ�Ʈ(%)�� �Է����ּ���."/>					
					</div>
					<span>%</span>
			</div>
			
			<hr/>
			
			<div class="row">
				<div class="col-xs-4 col-md-2"><strong>�ִ� ���ΰ�</strong></div>
					<div class="col-xs-8 col-md-4">
						<input type="text" name="maximumDiscount" class="form-control" />						
					</div>
					<span>��</span>
			</div>
			
			<hr/>
			
			<div class="row">
				<div class="col-xs-4 col-md-2"><strong>�ּ����밡��</strong></div>
					<div class="col-xs-8 col-md-4">
						<input type="text" name="minimum_price" class="form-control" />						
					</div>
					<span>��</span>
			</div>			
		
		</form>
		
			<div class="row">
				<div class="col-md-12 text-right ">			  	
			  		<button type="button" class="btn btn-primary">���</button>	 
			  		<button type="button" class="btn btn-primary">���</button>	  			
				</div>
			</div>
		
	
	</div>	

	</body>
</html>