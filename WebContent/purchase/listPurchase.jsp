<%@page import="com.model2.mvc.service.domain.*"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.common.*" %>
<%@page import="com.model2.mvc.common.util.CommonUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>


<html>
<head>
<title>구매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">


	var tranNo = '${purchase.tranNo}';

	function funcGetList(currentPage) {
		//document.getElementById("currentPage").value = currentPage;
		$("#currentPage").val(currentPage);
		$("form").attr("action", "/purchase/listPurchase");
		$("form").submit();
	}
	
	
	$(function(){
		$(".ct_list_pop td:nth-child(1)").on("click",function(){
		
			
			var url = "/purchase/getPurchase?tranNo=";
			url = url.concat(tranNo);
			
			alert(tranNo);
			
			//self.location.href = url;
			
		});
		
		
		$(".ct_list_pop:nth-child(4n+6)" ).css("background-color" , "whitesmoke");
		
	});
	
	
	
	
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">구매 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품이름</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">구매자</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">전화번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">도착확인</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>

	<c:set var="i" value="0" />	
	<c:forEach var="purchase" items="${list}">
		<c:set var="i" value="${ i+1 }" />		
	
	<tr class="ct_list_pop">
		<td align="center">
			<a href="/purchase/getPurchase?tranNo=${purchase.tranNo}">${i}</a>			
		</td>
		<td></td>
		<td align="left">
			<a href="/product/getProduct?prodNo=${(purchase.purchaseProd).prodNo}&menu=search">${(purchase.purchaseProd).prodName}</a>
		</td>
		<td></td>
		<td align="left">${purchase.receiverName}</td>
		<td></td>
		<td align="left">${purchase.receiverPhone}</td>
		<td></td>
		<td align="left">현재
	 	<c:choose>

		<c:when test="${purchase.tranCode == '1'}">		
			구매완료(배송 준비)
			</c:when>
		<c:when test="${purchase.tranCode == '2'}">
			배송중
		</c:when>
		<c:when test="${purchase.tranCode == '3'}">
			배송완료 
		</c:when>

		</c:choose>
			상태 입니다.</td>
		<td></td>
		
		<td align="left" >
		
			<c:choose>
				<c:when test="${purchase.tranCode =='3'}">
					상품 수령 완료
				</c:when>
			
				<c:when test="${purchase.tranCode =='2'}">
					<a href="/purchase/updateTranCode?tranNo=${purchase.tranNo}&tranCode=3">도착 확인</a>
				</c:when>
				<c:when test="${purchase.tranCode =='1'}">
					배송 대기중
				</c:when>		
			</c:choose>	

		</td>
		
		</c:forEach>
		
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value=""/>
			<jsp:include page="../common/pageNavigator.jsp"/>	
		
    	</td>
	</tr>
</table>

<!--  페이지 Navigator 끝 -->
</form>

</div>

</body>
</html>