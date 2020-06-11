<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>

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

	<script type="text/javascript" src="../javascript/calendar.js"></script>

	<script type="text/javascript">
	function fncAddProduct() {
		//Form 유효성 검증

		var name = $("input[name = 'prodName']").val(); 
		var detail = $("input[name = 'prodDetail']").val(); 
		var manuDate = $("input[name = 'manuDate']").val();
		var price = $("input[name = 'price']").val();
		
		
		if (name == null || name.length < 1) {
			alert("상품명은 반드시 입력하여야 합니다.");
			return;
		}
		
		if (detail == null || detail.length < 1) {
			return;
		}
		
		if (manuDate == null || manuDate.length < 1) {
			alert("제조일자는 반드시 입력하셔야 합니다.");
			return;
		}
		
		if (price == null || price.length < 1) {
			alert("가격은 반드시 입력하셔야 합니다.");
			return;
		}
		
		if(isNaN(price)){
			alert("가격에는 숫자를 입력해야 합니다.");
			return;
		}

		
		$("form").attr("method", "POST");
		$("form").attr("action", "/product/addProduct");
		$("form").submit();		
	}

	
	
	$(function(){
		
		$("button:contains('등록')").on("click", function() {
			fncAddProduct();
		});
		
		$("button:contains('취소')").on("click", function() {
			//$("form")[0].reset;
			self.location = document.referrer;
		});
		
		$("i").on("click", function() {	
			show_calendar('document.detailForm.manuDate', document.detailForm.manuDate.value);
		});
		
		
	});

	
	
</script>
</head>

	<body>
	
		<jsp:include page="/layout/toolbar.jsp" />
	
	
			<div class="container">
				
				<div class="page-header">
				       <h3 class=" text-info">상품 등록</h3>	
				</div>
				
				
				<form name="detailForm" enctype="multipart/form-data">
				<div class="row">
				  		<div class="col-xs-4 col-md-2"><strong>상품명</strong></div>
						<div class="col-xs-8 col-md-4">
							<input type="text" name="prodName" class="form-control" placeholder="상품명 입력"/>
						</div>
				</div>
				
				<hr/>
				
				<div class="row">
				  		<div class="col-xs-4 col-md-2"><strong>상품상세정보</strong></div>
						<div class="col-xs-8 col-md-4">
							<input type="text" name="prodDetail" class="form-control" placeholder="상세정보"/>
						</div>
				</div>
				
				<hr/>
				
				<div class="row">
				  		<div class="col-xs-4 col-md-2"><strong>제조일자</strong></div>
						<div class="col-xs-8 col-md-4">
							<input type="text" name="manuDate" class="form-control" placeholder="제조일자를 선택하세요" readonly/>			
						</div>			
						<i class="glyphicon glyphicon-calendar" ></i>			
				</div>
				
				<hr/>			
				
				<div class="row">
				  		<div class="col-xs-4 col-md-2"><strong>가격</strong></div>
						<div class="col-xs-8 col-md-4">
							<input type="text" name="price" class="form-control" placeholder="가격(원)"/>						
						</div>
						<span>원</span>					
				</div>
				
				<hr/>
				
				<div class="row">
				  		<div class="col-xs-4 col-md-2"><strong>상품이미지</strong></div>
						<div class="col-xs-8 col-md-4">
							<input type="file" class="form-control" name="uploadFile" placeholder="상품명 입력"  multiple/>
						</div>
				</div>
				
				<hr/>
				
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