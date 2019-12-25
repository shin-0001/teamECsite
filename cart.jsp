<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="./css/cart.css">
<link rel="stylesheet" type="text/css" href="./css/header.css">
<title>カート画面</title>
</head>
<body>
	<script src="./js/laravel.js"></script>
	<div id="header">
		<jsp:include page="header.jsp" />
	</div>

	<div id="main">
		<div id="top">
			<h1>カート画面</h1>
		</div>
		<div id="contents">
			<s:if test="cartInfoList.size() > 0">
				<s:form action="DeleteCartAction">
					<table border=1>
						<thead>
							<tr>
								<th><label>#</label></th>
								<th><label>商品名</label></th>
								<th><label>商品名ふりがな</label></th>
								<th><label>商品画像</label></th>
								<th><label>値段</label></th>
								<th><label>発売会社名</label></th>
								<th><label>発売年月日</label></th>
								<th><label>購入個数</label></th>
								<th><label>合計金額</label></th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="cartInfoList">
								<tr>
									<td><input type="checkbox" name="checkList"
										class="checkList" value='<s:property value="productId" />'
										onchange="checkValue()"></td>
									<td><s:property value="productName" /></td>
									<td><s:property value="productNameKana" /></td>
									<td><img
										src="<s:property value='imageFilePath'/><s:property value ='imageFileName'/>"
										width="50px" height="50px" /></td>
									<td><s:property value="price" />円</td>
									<td><s:property value="releaseCompany" /></td>
									<td><s:property value="releaseDate" /></td>
									<td><s:property value="productCount" /></td>
									<td><s:property value="totalFee" />円</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>

					<p class="totalPrice">
						カート合計金額:
						<s:property value="totalPrice" />
						円
					</p>
					<s:submit value="削除" id="deleteButton" class="submit"
						disabled="true" />
				</s:form>

				<s:form id="form">
					<s:if test="#session.login_flg == 1">
						<s:submit value="決済" class="submit"
							onclick="setAction('SettlementConfirmAction')" />
					</s:if>
					<s:else>
						<s:submit value="決済" class="submit"
							onclick="setAction('GoLoginAction')" />
						<s:hidden name="cartFlg" value="1" />
					</s:else>
				</s:form>
			</s:if>
			<s:else>
				<div class="info">
					カート情報がありません。
				</div>
			</s:else>
		</div>
	</div>
</body>
</html>