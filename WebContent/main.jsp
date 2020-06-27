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
	
	<!--  CSS �߰� : ���ٿ� ȭ�� ������ ���� �ذ� :  �ּ�ó�� ��, �� Ȯ��-->
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
   	
   		//���� ������ ����
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
									  +"<h5 style='color:red;'>(�Ǹ���)</h5>"										            
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
	              <h1>â�Ƿ¿� ������ �޴�.</h1>
	              <p>��7R III�� �߰��ϰ� ����Ʈ�� �ٵ� �پ��� �������� ���ۼ��� ��� � ��Ȳ������ ���������� �Կ��� �� �� �ֵ��� ���� �پ �������� �����մϴ�.</p>
	          
	            </div>
	          </div>
	        </div>
	        <div class="item">
	          <img class="second-slide" src="/images/benner02.jpg" style="width: 1140px; height:400px;">
	          <div class="container">
	            <div class="carousel-caption" value="1026">
	              <h1>No Limits, Break Free</h1>
	              <p>Ÿ�� ������ �����ϴ� �⵿�°� �ŷڼ����� ���� �����۰����� �������� ������ �ް� �ִ� ������ų� �� OM-D E-M1�ø�� ���� ��ȭ�߽��ϴ�.</p>
	             
	            </div>
	          </div>
	        </div>
	        <div class="item">
	          <img class="third-slide" src="/images/benner03.jpg" style="width: 1140px; height:400px;">
	          <div class="container">
	            <div class="carousel-caption" value="1027">
	              <h1>Photography in Motion</h1>
	              <p>������ ������ ��踦 �㹰��. X-T4�� ����Ʈ�� �ٵ� ��� ������ �ϵ�����, �����ʸ��� 80�� �̻� ���� ���Ͽ츦 �׾� �� �������� ���� ���ν� ������ ������ ��谡 ���� �۾� �÷ο츦 �����մϴ�.</p>
	             
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

	<!-- ���� : http://getbootstrap.com/css/   : container part..... -->
	<div class="container">
	
		<div class="row" >				
			<div class="page-header">
		       <h3 class=" text-info">��ϵ� ��ǰ</h3>	
		    </div>		 	
		</div>	
	 
	 </div> 

</body>

</html>