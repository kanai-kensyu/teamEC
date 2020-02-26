<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="./css/productDetails.css">
<link rel="stylesheet" type="text/css" href="./css/textStyle.css">
<link rel="stylesheet" type="text/css" href="./css/formStyle.css">
<title>商品詳細画面</title>
</head>
<body>

	<div id="header">
		<jsp:include page="header.jsp"></jsp:include>
	</div>

	<div id="main">
		<div id="top">
			<p>商品詳細画面</p>
		</div>

		<div>

			<s:if test="productDetailsDTO == null">
				<div id="message">
					<p>商品の詳細情報がありません。</p>
				</div>
			</s:if>

			<s:else>
				<div class="picture">
					<img
						src="<s:property value='productDetailsDTO.filePath' /><s:property value='productDetailsDTO.fileName' />" /><br>
				</div>
				<s:form action="AddCartAction">
					<table id="table-details">

						<tr>
							<th scope="row">商品名</th>
							<td class="td-right"><s:property
									value="productDetailsDTO.productName" /></td>
						</tr>
						<tr>
							<th scope="row">商品名ふりがな</th>
							<td class="td-right"><s:property
									value="productDetailsDTO.productKana" /></td>
						</tr>
						<tr>
							<th scope="row">値段</th>
							<td class="td-right"><s:property
									value="productDetailsDTO.price" /><span>円</span></td>
						</tr>

						<tr>
							<th scope="row">購入個数</th>
							<td class="td-right"><select name="productCount">
									<option value="1" selected="selected">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
							</select>個</td>
						<tr>
							<th scope="row">発売会社名</th>
							<td class="td-right"><s:property
									value="productDetailsDTO.releaseCompany" /></td>
						</tr>
						<tr>
							<th scope="row">発売年月日</th>
							<td class="td-right"><s:property
									value="productDetailsDTO.releaseDate" /></td>
						</tr>
						<tr>
							<th scope="row">商品詳細情報</th>
							<td class="td-right"><s:property
									value="productDetailsDTO.productDescription" /></td>
						</tr>

					</table>

					<br>
					<s:hidden name="productId" value="%{productDetailsDTO.productId}" />
					<div class="btn-box">
					<s:submit value="カートに追加" class="submit_btn" />
					</div>
				</s:form>

				<div id="bottom-box">

					<s:if
						test="!(relatedProductDTOList == null) && relatedProductDTOList.size()>0">

						<div class="recommened-box">
							<p>【関連商品】</p>
							<table>
								<tr>
									<s:iterator value="relatedProductDTOList">
										<td id="related-item"><a
											href='<s:url action="ProductDetailsAction"><s:param name="productId" value="%{productId}" /></s:url>'>
												<img
												src='<s:property value="filePath" /><s:property value="fileName" />'>
												<s:property value="productName" /> <br>
										</a></td>
									</s:iterator>
								</tr>
							</table>
						</div>
					</s:if>

				</div>
			</s:else>
		</div>
	</div>

</body>
</html>