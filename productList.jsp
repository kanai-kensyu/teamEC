<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="./css/productList.css">
<link rel="stylesheet" type="text/css" href="./css/textStyle.css">
<link rel="stylesheet" type="text/css" href="./css/formStyle.css">
<title>商品一覧画面</title>
</head>
<body>
	<div id="header">
		<jsp:include page="header.jsp" />
	</div>

	<div id="main">
		<div id="top">
			<p>商品一覧画面</p>
		</div>

		<div id="main">

			<s:if test="!(searchErrorList == null) && searchErrorList.size() > 0">
				<div id="error">
					<s:iterator value="searchErrorList">
						<s:property />
						<br>
					</s:iterator>
				</div>
			</s:if>

			<s:elseif test="productInfoDTOList == null">
				<div id="message">
					<p>検索結果がありません。</p>
				</div>
			</s:elseif>

			<s:elseif
				test="!(productInfoDTOList == null) && productInfoDTOList.size() > 0">

				<table>

					<s:iterator value="productInfoDTOList" status="st">
						<s:if test="#st.index%3 == 0">
							<tr>
						</s:if>
						<td><a
							href='<s:url action="ProductDetailsAction"><s:param name="productId" value="%{productId}" /></s:url>'>

								<s:hidden name="productId" value="productId" /> <img
								src='<s:property value="filePath" /><s:property value="fileName" />'><br>

								<s:property value="productName" /><br> <s:property
									value="productKana" /><br> <s:property value="Price" />
								<span>円</span>

						</a></td>

						<!-- </tr>の警告はifタグに囲まれていて開始タグが見つからなく、文法上問題ないため、無視する -->
						<s:if test="#st.index%3 == 2">
							</tr>
						</s:if>
					</s:iterator>
				</table>
			</s:elseif>
		</div>
	</div>
</body>
</html>