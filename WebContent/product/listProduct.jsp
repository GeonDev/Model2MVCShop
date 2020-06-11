<%@ page import="com.model2.mvc.service.domain.*"%>
<%@ page import="com.model2.mvc.common.*"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



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
   
   
   <!-- jQuery UI toolTip 사용 CSS-->
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
			var keywordOptional = $("input[name = 'searchKeywordOptional']").val(); 
			var condition = $("#Condition").val();
			var url = "/product/listProduct?menu=";
			
			
	 		if(condition == 0){
				if(isNaN(keyword)){
					alert("상품 번호는 숫자로 입력해 주세요.");
					return;
				}				
			}
			
			if(condition == 2){
				if(isNaN(keyword) || isNaN(keywordOptional)){
					alert("가격(숫자)을 입력해 주세요.");
					return;	
				}			
				
				if(keyword >= keywordOptional){
					alert("최소값이 최대 값보다 큽니다.");
					return;	
				}				
			}	  								
		
			
			url = url.concat('${menu}');
	
			$("#currentPage").val(currentPage);
			$("form").attr("method", "POST").attr("action", url).submit();
		}
	
		function changeSearchCondition() {
	
			if ($("#Condition").val() == 2) {				
				$("#optional").attr("type", "text");
	
			} else {			
				$("#optional").attr("type", "hidden");
			}
		}
		
		
		function removePreView() {
			$("#preView").remove();
		}		
		
	
		$(function() {
			changeSearchCondition();
	
			$("button:contains('검색')").on("click", function() {			
				funcGetList(1);
			});
	
			
			
			$("#Condition").on("change",function(){
				changeSearchCondition();
				
			});		
			
				
			$(document).on("click", "#preView", function(){			
				removePreView();
				
			});	
		
			
			$("td:nth-child(2)").on( "click", function() {				
				
						var prodNo = $(this).text().trim();						
						
						if('${menu}' == 'manage'){	
						
							
							$.ajax("/purchase/json/getPurchase/"+prodNo, {
								method : "GET",
								dataType : "Json",
								headers : {
									"Accept" : "application/json",
									"Content-Type" : "application/json"
								},
								success : function(JSONData, status) {					
									
									var displayValue =
										"<h4 id='preView'>"
										+ "구매자 이름   : "+	JSONData.receiverName +"<br/>"						
										+ "구매자 연락처 : "+	JSONData.receiverPhone +"<br/>"
										+ "구매자 주소   : "+	JSONData.divyAddr +"<br/>"
										+ "배송 희망일   : "+	JSONData.divyDate +"<br/>"
										+ "배송 요구사항 : "+	JSONData.divyRequest +"<br/>"
										+ "</h4>";
	
									$("#preView").remove();
									$("#" + prodNo + "").append(displayValue);
								}							
							});						
							
							
						}else{
							
							$.ajax("/product/json/getProduct/"+prodNo, {
								method : "GET",
								dataType : "Json",
								headers : {
									"Accept" : "application/json",
									"Content-Type" : "application/json"
								},
								success : function(JSONData, status) {
	
									//<img src = "/images/uploadFiles/"+ JSONData.fileName  width="200"/>;
	
									var displayValue =
										"<h4 id='preView'>"
										+"<img src = \"/images/uploadFiles/"+ JSONData.fileName[0]["fileName"] + "\" width= \"200\"/>" +"<br/>"
										+ JSONData.prodName										
										+ "</h4>";
	
									$("#preView").remove();
									/* $("#" + prodNo + "").html(displayValue); */
									$("#" + prodNo + "").append(displayValue);
								}
							});
							
						}
	
				});	
			
		});
	</script>
</head>

<body>

		<jsp:include page="/layout/toolbar.jsp" />
		<div class="container">
		
		<div class="page-header text-info">
		
			<c:choose>
	       		<c:when test="${menu == 'manage'}">
					<h3>판매 상품 관리</h3>
				</c:when>
				<c:otherwise>
					<h3>상품 목록</h3>
				</c:otherwise>
			</c:choose>
	    </div>
	    
	    <div class="row">	    
		    <form class="form-inline" name="detailForm">
		    	<div class="col-md-3 text-left">
		    		<select id ="searchOrder" name="searchOrder" class="form-control" >
						<option value="0"
							${ ! empty search.searchOrder && search.searchOrder==0 ? "selected" : "" }>기본순</option>
						<option value="1"
							${ ! empty search.searchOrder && search.searchOrder==1 ? "selected" : "" }>낮은가격순</option>
						<option value="2"
							${ ! empty search.searchOrder && search.searchOrder==2 ? "selected" : "" }>높은가격순</option>
						<option value="3"
							${ ! empty search.searchOrder && search.searchOrder==3 ? "selected" : "" }>최신등록순</option>
					</select>
		    	</div>
		    	
		    	<div class="col-md-9 text-right">
		    		<select id="Condition" name="searchCondition" class="form-control">
						<option value="0"
							${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
						<option value="1"
							${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품명</option>
						<option value="2"
							${ ! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>상품가격</option>
					</select> 											    	
		    	
		    		<input id="Keyword" type="text" name="searchKeyword" value="${search.searchKeyword}" class="form-control"/>
		    		<input id="optional" type="hidden" name="searchKeywordOptional" value="${search.searchKeywordOptional}" class="form-control" />
		    	
 		    		<button type="button" class="btn btn-default">검색</button>
		    	</div>
		    	
		    	<!-- PageNavigation 선택 페이지 값을 보내는 부분 -->
				<input type="hidden" id="currentPage" name="currentPage" value=""/>
		    	
		    </form>	    
		</div>
		
		<br/>
		
		
		<div class="row">
	    	<div class="col-md-6 text-left">
		    	<p class="text-primary">
		    		전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage}  페이지
		    	</p>
		    </div>   
	   </div>
		
		
		
		<table class="table table-hover table-striped" >
      
	        <thead>
	          <tr>
	            <th align="center">No</th>
	            <th align="left" >상품번호</th>
	            <th align="left">상품명</th>
	            <th align="left">가격</th>
	            <th align="left">등록일</th>
	            <th align="left">현재상태</th>
	          </tr>
	        </thead>
	       
			<tbody>
			
			<c:set var="i" value="0" />
				<c:forEach var="prod" items="${list}">
					<c:set var="i" value="${ i+1 }" />

					<tr >
						<td align="center">${i}</td>
						<td align="left" >${prod.prodNo}
							<span id="${prod.prodNo}" > </span>
						</td>
						<td align="left"><a href="/product/getProduct?prodNo=${prod.prodNo}&menu=${menu}">${prod.prodName}</a></td>
						<td align="left">${prod.price}</td>
						<td align="left">${prod.regDate}</td>						
						<td align="left">
						<c:choose>
							<c:when test="${prod.proTranCode == '0'}">판매중</c:when>
							<c:when test="${prod.proTranCode == '1'}">구매완료 					
								<c:if test="${menu eq 'manage'}">
										<a	href="/purchase/updateTranCodeByProd?prodNo=${prod.prodNo}&tranCode=2">배송하기</a>
								</c:if>
							</c:when>
							
							<c:when test="${prod.proTranCode == '2'}">배송중</c:when>
						<c:otherwise>배송완료</c:otherwise>
						</c:choose>
						</td>			
					</tr>
					
				</c:forEach>
	        
	        </tbody>
      
      </table>	
		
		
		</div>
		
		<jsp:include page="../common/pageNavigator_new.jsp"/>		
	
	</body>
	
</html>