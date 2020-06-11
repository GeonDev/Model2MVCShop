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
	
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
 		body {
            padding-top : 50px;
        }
    </style>


	
<script type="text/javascript">

	function funcAddCoupon(){
		//Form 유효성 검증
	 	var name = $("input[name = 'couponName']").val(); 
		var discount = $("input[name = 'discountRatio']").val(); 
		var maximum = $("input[name = 'maximumDiscount']").val();
		var minimum =  $("input[name = 'minimum_price']").val();
		
		if(name == null || name.length<1){
			alert("쿠폰이름은 반드시 입력하여야 합니다.");
			return;
		}
		
		if(discount == null || discount.length<1){
			alert("할인율은 반드시 입력하여야 합니다.");
			return;
		}
		
		if(isNaN(discount)){
			alert("할인율에는 숫자를 입력해 주세요");
			return;
		}
		
		if(discount < 0 || discount > 100){
			alert("할인율은 1~100까지만 입력 가능합니다.");
			return;
		}
		
		
		if(maximum == null || maximum.length<1){
			alert("최대할인가는 반드시 입력하여야 합니다.");
			return;
		}
		
		if(isNaN(maximum)){
			alert("최대 할인가는 숫자를 입력해 주세요");
			return;
		}
		
		
		if(minimum == null || minimum.length<1){
			alert("최소적용가는 반드시 입력하여야 합니다.");
			return;
		}
		
		if(isNaN(minimum)){
			alert("최소적용가에는 숫자를 입력해 주세요");
			return;
		}
		
		

		$("form").attr("action", "/coupon/addCoupon");
		$("form").submit();	

	}
	
	function resetData(){
		document.detailForm.reset();
	}
	
	
	$(function(){
		
		$("button:contains('등록')").on("click", function() {
			funcAddCoupon();
		});
		
		$("button:contains('취소')").on("click", function() {
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
			<h3 class=" text-info">쿠폰 등록</h3>	
		</div>
		
		<form  method="post">
		
			<div class="row">
				<div class="col-xs-4 col-md-2"><strong>쿠폰명</strong></div>
					<div class="col-xs-8 col-md-4">
						<input type="text" name="couponName" class="form-control" placeholder="쿠폰명 입력"/>
				</div>
			</div>
			
			<hr/>
			
			<div class="row">
				<div class="col-xs-4 col-md-2"><strong>할인율</strong></div>
					<div class="col-xs-8 col-md-4">
						<input type="text" name="discountRatio" class="form-control" placeholder="퍼센트(%)로 입력해주세요."/>					
					</div>
					<span>%</span>
			</div>
			
			<hr/>
			
			<div class="row">
				<div class="col-xs-4 col-md-2"><strong>최대 할인가</strong></div>
					<div class="col-xs-8 col-md-4">
						<input type="text" name="maximumDiscount" class="form-control" />						
					</div>
					<span>원</span>
			</div>
			
			<hr/>
			
			<div class="row">
				<div class="col-xs-4 col-md-2"><strong>최소적용가격</strong></div>
					<div class="col-xs-8 col-md-4">
						<input type="text" name="minimum_price" class="form-control" />						
					</div>
					<span>원</span>
			</div>			
		
		</form>
		
			<div class="row">
				<div class="col-md-12 text-right ">			  	
			  		<button type="button" class="btn btn-primary">등록</button>	 
			  		<button type="button" class="btn btn-primary">취소</button>	  			
				</div>
			</div>
		
	
	</div>	

	</body>
</html>