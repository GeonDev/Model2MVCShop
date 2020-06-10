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
	
	<!--  CSS 추가 : 툴바에 화면 가리는 현상 해결 :  주석처리 전, 후 확인-->
	<style>
        body {
            padding-top : 70px;
        }
        
        #block{	        	
        	 border-bottom-style: solid;
        	 color : WhiteSmoke;      	
        	 margin: 5px 5px;        	          	 
        	 height: 300px;      	
        	 text-align: center;
        }
        
        #borad{
        	border : 1px solid;        	
        	color : WhiteSmoke;
        	margin: 10px 10px;
        	height: 350px;
        	text-align: center;
        }
        
   	</style>
   	
   	<script type="text/javascript"> 
   		
   		//최초 페이지 지정
   		var page = 1;
   		var displayValue ="";
   		
   		$(function() {
   			
   			$(window).scroll(function() {
   			    if ($(window).scrollTop() == $(document).height() - $(window).height()) {
   			     page++;
   			     
   			     
					$.ajax( 
							{
								url : "/product/json/listProduct/"+page ,
								method : "GET" ,
								dataType : "json" ,
								headers : {
									"Accept" : "application/json",
									"Content-Type" : "application/json"
								},
								success : function(JSONData , status) {
									$.each(JSONData, function(index,prod) {
										
									
										displayValue =	"<div class='col-md-6' >"
													  + "<div id='borad' value="+prod.prodNo+">"
													  + "<div class='panel-body' id='block'>"
													  + "<img src='/images/uploadFiles/"+ prod.fileName[0]["fileName"] +"' width='250px'/>"	
										              +"</div>"
										              +"<h4 style='color:Black;''>"+prod.prodName +"</h4>"
										              +"</div></div>"; 
									});									
									
									$(".col-md-6:last").append(displayValue);
								}
						});

   			      
   			      
/*    			      $("#enters").append("<h1>Page " + page + "</h1><BR/>So<BR/>MANY<BR/>BRS<BR/>YEAHHH~<BR/>So<BR/>MANY<BR/>BRS<BR/>YEAHHH~<BR/>So<BR/>MANY<BR/>BRS<BR/>YEAHHH~<BR/>So<BR/>MANY<BR/>BRS<BR/>YEAHHH~<BR/>So<BR/>MANY<BR/>BRS<BR/>YEAHHH~<BR/>So<BR/>MANY<BR/>BRS<BR/>YEAHHH~<BR/>So<BR/>MANY<BR/>BRS<BR/>YEAHHH~<BR/>So<BR/>MANY<BR/>BRS<BR/>YEAHHH~<BR/>So<BR/>MANY<BR/>BRS<BR/>YEAHHH~<BR/>So<BR/>MANY<BR/>BRS<BR/>YEAHHH~<BR/>So<BR/>MANY<BR/>BRS<BR/>YEAHHH~<BR/>So<BR/>MANY<BR/>BRS<BR/>YEAHHH~"); */
   			      
   			    }
   			});
   			
   			
   			
		});
   	
   	</script>
   	
     	
	
</head>
	
<body>
	<jsp:include page="/layout/toolbar.jsp" />


   	<div class="container ">
      <div class="jumbotron">
        <h1>Model2MVCShop </h1>
        <p>J2SE, DBMS, JDBC, Servlet & JSP, Spring Framework, HTML5, BootStrap </p>
     </div>
    </div>

	<!-- 참조 : http://getbootstrap.com/css/   : container part..... -->
	<div class="container">
	
	<div class="row" >
	
	
		<div class="page-header">
	       <h3 class=" text-info">등록된 상품</h3>	
	    </div>
	
	
		<c:forEach var = "prod" items="${list}">
			<div class="col-md-6" >
				<div id="borad" value="${prod.prodNo}">
			 		<div class="panel-body" id="block">	 		
			 			<img src="/images/uploadFiles/${prod.fileName.get(0).fileName}" width="250px"/>	 				 			
			 		</div>			 				 		
			 		<h4 style="color:Black;">${prod.prodName}</h4>
			 	</div>
		 	</div>
		 </c:forEach>	
	 	
	</div>
	
	
	

	 	

	 
	 </div> 

</body>

</html>