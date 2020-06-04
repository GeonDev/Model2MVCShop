<%@ page import="com.model2.mvc.service.domain.*"%>
<%@ page import="com.model2.mvc.common.*"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>


<%@ page import="java.util.*"%>
<%@ page import="com.model2.mvc.common.*"%>
<%@ page import="com.model2.mvc.common.util.CommonUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<!DOCTYPE html>
<html>
<head>
<title>상품 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
	function funcGetList(currentPage) {
		
		
		var url = "/product/listProduct?menu=";
		url = url.concat('${menu}');

		$("#currentPage").val(currentPage);
		$("form").attr("method", "POST").attr("action", url).submit();
	}

	function changeSearchCondition() {

		if ($("#Condition").val() == 2) {
			//document.getElementById("Keyword").style.width = "80px";
			//document.getElementById("optional").type = "text";
			$("#Keyword").css("width", "100px");
			$("#optional").attr("type", "text");

		} else {
			//document.getElementById("Keyword").style.width = "200px";
			//document.getElementById("optional").type = "hidden";
			$("#Keyword").css("width", "200px");
			$("#optional").attr("type", "hidden");
		}
	}
	
	
	function removePreView() {
		$("h3").remove();
	}
	
	
	

	$(function() {
		changeSearchCondition();

		$("td.ct_btn01:contains('검색')").on("click", function() {
			funcGetList(1);
		});

		
		
		$(".ct_list_pop td:nth-child(3)").on(
				"click",
				function() {
					var prodNo = $(this).text().trim();

					//alert(prodNo);

					$.ajax("/product/json/getProduct/" + prodNo, {
						method : "GET",
						dataType : "Json",
						headers : {
							"Accept" : "application/json",
							"Content-Type" : "application/json"
						},
						success : function(JSONData, status) {

							//<img src = "/images/uploadFiles/"+ JSONData.fileName  width="200"/>;

							var displayValue =
								"<h3 id='pre"+JSONData.prodNo +"'>"
								+ JSONData.prodName								
								+'<input type="button" value="닫기" onclick="javascript:removePreView()"/> <br/>'
								+"<img src = \"/images/uploadFiles/"+ JSONData.fileName + "\" width= \"200\"/>" +"<br/>"
								+ "</h3>";

							$("h3").remove();
							$("#" + prodNo + "").html(displayValue);
						}
					});
				});	
		
	});
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

	<div style="width: 98%; margin-left: 10px;">

		<form>

			<table width="100%" height="37" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"
						width="15" height="37" /></td>
					<td background="/images/ct_ttl_img02.gif" width="100%"
						style="padding-left: 10px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="93%" class="ct_ttl01"><c:choose>
										<c:when test="${menu == 'manage'}">
								판매 상품 관리
							</c:when>
										<c:otherwise>
								상품 목록
							</c:otherwise>
									</c:choose></td>
							</tr>
						</table>
					</td>
					<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"
						width="12" height="37" /></td>
				</tr>
			</table>


			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>


					<td align="left"><select name="searchOrder" class="ct_input_g"
						style="width: 90px">
							<option value="0"
								${ ! empty search.searchOrder && search.searchOrder==0 ? "selected" : "" }>기본순</option>
							<option value="1"
								${ ! empty search.searchOrder && search.searchOrder==1 ? "selected" : "" }>낮은가격순</option>
							<option value="2"
								${ ! empty search.searchOrder && search.searchOrder==2 ? "selected" : "" }>높은가격순</option>
							<option value="3"
								${ ! empty search.searchOrder && search.searchOrder==3 ? "selected" : "" }>최신등록순</option>
					</select></td>

					<td align="right"><select id="Condition"
						name="searchCondition" onclick="changeSearchCondition()"
						class="ct_input_g" style="width: 80px">
							<option value="0"
								${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
							<option value="1"
								${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품명</option>
							<option value="2"
								${ ! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>상품가격</option>
					</select> <input id="Keyword" type="text" name="searchKeyword"
						value="${search.searchKeyword}" class="ct_input_g"
						style="width: 200px; height: 15px" /> <input id="optional"
						type="hidden" name="searchKeywordOptional"
						value="${search.searchKeywordOptional}" class="ct_input_g"
						style="width: 100px; height: 15px" /></td>


					<td align="right" width="70">
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="17" height="23"><img
									src="/images/ct_btnbg01.gif" width="17" height="23"></td>
								<td background="/images/ct_btnbg02.gif" class="ct_btn01"
									style="padding-top: 3px;">검색</td>
								<td width="14" height="23"><img
									src="/images/ct_btnbg03.gif" width="14" height="23"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>


			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td colspan="11">전체 ${resultPage.totalCount} 건수, 현재
						${resultPage.currentPage} 페이지</td>
				</tr>
				<tr>
					<td class="ct_list_b" width="100">No</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">상품번호</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">상품명</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">가격</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">등록일</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">현재상태</td>
				</tr>
				<tr>
					<td colspan="11" bgcolor="808285" height="1"></td>
				</tr>


				<c:set var="i" value="0" />
				<c:forEach var="prod" items="${list}">
					<c:set var="i" value="${ i+1 }" />

					<tr class="ct_list_pop">
						<td align="center">${i}</td>
						<td></td>

						<td align="left">${prod.prodNo}</td>
						<td></td>

						<td align="left"><a
							href="/product/getProduct?prodNo=${prod.prodNo}&menu=${menu}">${prod.prodName}</a></td>


						<td></td>
						<td align="left">${prod.price}</td>
						<td></td>
						<td align="left">${prod.regDate}</td>
						<td></td>
						<td align="left"><c:choose>
								<c:when test="${prod.proTranCode == '0'}">
			판매중 
		</c:when>
								<c:when test="${prod.proTranCode == '1'}">		
			구매완료					
			<c:if test="${menu eq 'manage'}">
										<a
											href="/purchase/updateTranCodeByProd?prodNo=${prod.prodNo}&tranCode=2">배송하기</a>
									</c:if>
								</c:when>
								<c:when test="${prod.proTranCode == '2'}">
			배송중
		</c:when>
								<c:otherwise>
			배송완료
		</c:otherwise>
							</c:choose></td>
					</tr>
					<tr>
						<td id="${prod.prodNo}" colspan="11" bgcolor="D6D7D6" height="1"></td>
					</tr>

				</c:forEach>

			</table>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">

				<tr>
					<td align="center"><input type="hidden" id="currentPage"
						name="currentPage" value="" /> <jsp:include
							page="../common/pageNavigator.jsp" /></td>
				</tr>



			</table>
			<!--  페이지 Navigator 끝 -->

		</form>

	</div>
</body>
</html>