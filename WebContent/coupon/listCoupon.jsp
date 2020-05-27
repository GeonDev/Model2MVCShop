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
<title>쿠폰 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function funcGetList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/coupon/listCoupon" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">쿠폰 목록조회</td>
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
		<td class="ct_list_b" width="150">쿠폰이름</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">할인률</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">최대할인가</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">최소 적용가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">발급 개수</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보 수정</td>
	</tr>
	<tr>
		<td colspan="13" bgcolor="808285" height="1"></td>
	</tr>

	<c:set var="i" value="0" />	
	<c:forEach var="coupon" items="${list}">
		<c:set var="i" value="${ i+1 }" />		
	
	<tr class="ct_list_pop">
		<td align="center">	${i} </td>
		<td></td>
		<td align="left">${coupon.couponName}</td>
		<td></td>
		<td align="left">${coupon.discountRatio} %</td>
		<td></td>
		<td align="left">${coupon.maximumDiscount} 원</td>
		<td></td>
		<td align="left">${coupon.minimum_price} 원</td>
		<td></td>
		<td align="left">${coupon.couponCount}</td>
		<td></td>
		<td align="left">
			<c:if test="${coupon.couponCount == 0}">
				<a href="/coupon/updateCoupon?couponNo=${coupon.couponNo}">수정</a>
				&nbsp;&nbsp;&nbsp;
				<a href="/coupon/deleteCoupon?couponNo=${coupon.couponNo}">삭제</a>
			</c:if>

		</td>
		
		</c:forEach>
		
	</tr>
	<tr>
		<td colspan="13" bgcolor="D6D7D6" height="1"></td>
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