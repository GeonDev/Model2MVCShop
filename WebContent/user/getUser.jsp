<%@ page contentType="text/html; charset=EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>회원정보조회</title>

<script type="text/javascript">

function funcAddCouponInventory(){
	//document.getElementById("value").value = selectCoupon;
	document.detailForm.submit();
}


</script>



<link rel="stylesheet" href="/css/admin.css" type="text/css">

</head>

<body bgcolor="#ffffff" text="#000000">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif" width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">회원정보조회</td>
					<td width="20%" align="right">&nbsp;</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:13px;">
	
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	
	<tr>
		<td width="104" class="ct_write">
			아이디 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${user.userId}</td>
	</tr>

	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	
	<tr>
		<td width="104" class="ct_write">
			이름 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle" />
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${user.userName}</td>
	</tr>
	
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	
	<tr>
		<td width="104" class="ct_write">주소</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${user.addr}</td>
	</tr>
	
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	
	<tr>
		<td width="104" class="ct_write">휴대전화번호</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${ !empty user.phone ? user.phone : ''}	</td>
	</tr>

	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	
	<tr>
		<td width="104" class="ct_write">이메일 </td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${user.email}</td>
	</tr>

	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	
	<tr>
		<td width="104" class="ct_write">가입일자</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${user.regDate}</td>
	</tr>
	
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	
</table>


<!-- 어드민 계정인 경우 유저에게 쿠폰을 줄수 있다.  -->
<c:if test="${sessionScope.user.role == 'admin'}">
	<form name="detailForm" action="/inventory/addCoupon" method="post">
	
		<input type="hidden" name="ownerId" value="${user.userId}" />
	
		<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top:10px;">
		
			<tr>
				<td height="1" colspan="3" bgcolor="D6D6D6"></td>
			</tr>
		
			<tr>
				<td width="104" class="ct_write">
					지급 가능 쿠폰 
				</td>
				<td bgcolor="D6D6D6" width="1"></td>
				<td class="ct_write01">
				 
					<select name="selectCoupon" class="ct_input_g" style="width:150px">
						<option value="none" >======= 선택 =======</option>	
						<c:forEach var="coupon" items="${list}">				
						<option value="${coupon.couponNo}" >${coupon.couponName}</option>
						</c:forEach>				
					</select>	
				 
				</td>
			</tr>
		
			<tr>
				<td height="1" colspan="3" bgcolor="D6D6D6"></td>
			</tr>
		
		</table>
	</form>

</c:if>


<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top:10px;">
	<tr>
		<td width="53%"></td>
		<td align="right">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<c:if test="${sessionScope.user.role == 'admin'}">
						<td width="17" height="23"><img src="/images/ct_btnbg01.gif" width="17" height="23"></td>
						<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:funcAddCouponInventory();">쿠폰지급</a>
						</td>
						<td width="14" height="23"><img src="/images/ct_btnbg03.gif" width="14" height="23"></td>
						<td width="10"></td>					
					</c:if>				
				
					
					<!-- 내정보는 로그인한 유저 정보와 조회하는 ID가 같을 때만 수정가능  -->
					<c:if test="${requestScope.user.userId == sessionScope.user.userId}">
						<td width="17" height="23"><img src="/images/ct_btnbg01.gif" width="17" height="23"></td>
						<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
							<!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////
							<a href="/updateUserView.do?userId=${user.userId}">수정</a>
							////////////////////////////////////////////////////////////////////////////////////////////////////////// -->
								<a href="/user/updateUser?userId=${user.userId}">수정</a>							
						</td>
						<td width="14" height="23"><img src="/images/ct_btnbg03.gif" width="14" height="23"></td>
						<td width="10"></td>
					</c:if>
					
										
					<td width="17" height="23"><img src="/images/ct_btnbg01.gif" width="17" height="23"></td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:history.go(-1);">확인</a>
					</td>
					<td width="14" height="23"><img src="/images/ct_btnbg03.gif" width="14" height="23"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</body>
</html>