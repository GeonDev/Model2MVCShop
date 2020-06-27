<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>

<!--  ///////////////////////// JSTL  ////////////////////////// -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="ko">
	
<head>
	<meta charset="EUC-KR">
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--   jQuery , Bootstrap CDN  -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	<!-- Bootstrap Dropdown Hover CSS -->
   <link href="/css/animate.min.css" rel="stylesheet">
   <link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
   
    <!-- Bootstrap Dropdown Hover JS -->
   <script src="/javascript/bootstrap-dropdownhover.min.js"></script>
   
   <!-- lazyload CDN  -->
   <script src="https://cdn.jsdelivr.net/npm/lazyload@2.0.0-rc.2/lazyload.js"></script>
	
	<!--  CSS 추가 : 툴바에 화면 가리는 현상 해결 :  주석처리 전, 후 확인-->
	<style>
        body {
            padding-top : 70px;
        }
        
        .block{	        	
        	 border-bottom-style: solid;
        	 color : WhiteSmoke;      	        	         	          	 
        	 height: 320px;      	        	        	
        	 overflow: hidden;
        }
        
        .borad{
        	border : 1px solid;        	
        	color : WhiteSmoke;
        	margin: 10px 10px;
        	height: 400px;
        	text-align: center;
        }
        
   	</style>
   	
   	<script type="text/javascript">    		
   	
   		//최초 페이지 지정
   		var page = 1;   
   		
   		function getProdList(page) {
			$.ajax( 
					{
						url : "/product/json/listProduct/"+page ,
						method : "GET" ,
						dataType : "json" ,
						cache : false,
						headers : {
							"Accept" : "application/json",
							"Content-Type" : "application/json"
						},
						success : function(JSONData , status) {
							$.each(JSONData, function(index,prod) {
								
							
								var displayValue =	"<div class='col-md-6' >"													
											  + "<div class='borad' value='"+prod.prodNo+"'>"
											  + "<div class = 'block'>"
											  + "<img class='lazyload'  data-src='/images/others/loading.gif' src='/images/uploadFiles/"+ prod.fileName[0]["fileName"] +"' style='width: 300px;'/>"	
								              +"</div>"
								              +"<h4 style='color:Black;'>"+prod.prodName +"</h4>";
								              
								  if(prod.proTranCode=='0'){
									  displayValue = displayValue	
									  +"<h5 style='color:red;'>(판매중)</h5>"										            
						              +"</div></div>";											  
								  }else{
									  displayValue = displayValue
									  +"</div></div>";
								  }            
								              
								$(".row:last").append(displayValue);
							});										
						}
				});
		} 
   		
   		
   		$(function() {   			
   			getProdList(1);
   			
   			$(window).scroll(function() {
   			    if ($(window).scrollTop() == $(document).height() - $(window).height()) {
   			     page++;   			     
   			  	 getProdList(page);
   			    }
   			});			   		

			
   			
   			$(document).on("click", ".borad", function() {				
   				var prodNo = $(this).attr("value");    			
   				self.location =  "/product/getProduct?prodNo="+prodNo+"&menu=search"
   			});
   			
   			$(".carousel-caption").on("click", function() {				
   				var prodNo = $(this).attr("value");    			
   				self.location =  "/product/getProduct?prodNo="+prodNo+"&menu=search"
   			});   			
   		  			
   			
		});
   	
   	</script>
   	
     	
	
</head>
	
<body>
	<jsp:include page="/layout/toolbar.jsp" />


	<div class="container">
	
		<div id="myCarousel" class="carousel slide" data-ride="carousel">
	      <!-- Indicators -->
	      <ol class="carousel-indicators">
	        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
	        <li data-target="#myCarousel" data-slide-to="1"></li>
	        <li data-target="#myCarousel" data-slide-to="2"></li>
	      </ol>
	      <div class="carousel-inner" role="listbox">
	        <div class="item active">
	          <img class="first-slide" src="/images/benner01.jpg" style="width: 1140px; height:400px;">
	          <div class="container">
	            <div class="carousel-caption" value="1000">
	              <h1>창의력에 날개를 달다.</h1>
	              <p>α7R III는 견고하고 컴팩트한 바디에 다양한 전문가급 조작성을 담아 어떤 상황에서도 만족스러운 촬영을 할 수 있도록 더욱 뛰어난 유연성을 제공합니다.</p>
	          
	            </div>
	          </div>
	        </div>
	        <div class="item">
	          <img class="second-slide" src="/images/benner02.jpg" style="width: 1140px; height:400px;">
	          <div class="container">
	            <div class="carousel-caption" value="1026">
	              <h1>No Limits, Break Free</h1>
	              <p>타의 추종을 불허하는 기동력과 신뢰성으로 프로 사진작가들의 절대적인 지지를 받고 있는 프로페셔널 모델 OM-D E-M1시리즈가 더욱 진화했습니다.</p>
	             
	            </div>
	          </div>
	        </div>
	        <div class="item">
	          <img class="third-slide" src="/images/benner03.jpg" style="width: 1140px; height:400px;">
	          <div class="container">
	            <div class="carousel-caption" value="1027">
	              <h1>Photography in Motion</h1>
	              <p>사진과 영상의 경계를 허물다. X-T4의 컴팩트한 바디에 담긴 강력한 하드웨어는, 후지필름이 80년 이상에 걸쳐 노하우를 쌓아 온 ‘색’을 공통 언어로써 사진과 영상의 경계가 없는 작업 플로우를 실현합니다.</p>
	             
	            </div>
	          </div>
	        </div>
	      </div>
	      <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
	        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
	        <span class="sr-only">Previous</span>
	      </a>
	      <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
	        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
	        <span class="sr-only">Next</span>
	      </a>
	    </div>
	
	</div>

	<!-- 참조 : http://getbootstrap.com/css/   : container part..... -->
	<div class="container">
	
		<div class="row" >				
			<div class="page-header">
		       <h3 class=" text-info">등록된 상품</h3>	
		    </div>		 	
		</div>	
	 
	 </div> 

</body>

</html>