package com.internousdev.rose.action;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.rose.dao.ProductInfoDAO;
import com.internousdev.rose.dto.ProductInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class ProductDetailsAction extends ActionSupport implements SessionAware{

	private int categoryId;
	private int productId;
	private ProductInfoDTO productDetailsDTO;
	private List<ProductInfoDTO> relatedProductDTOList;

	private Map<String, Object> session;

	public String execute() throws SQLException {

		//productList.jspからはproductIdのみを取得。

		String result = SUCCESS;

		//productDetails.jspに格納する値を取得。
		ProductInfoDAO dao = new ProductInfoDAO();
		productDetailsDTO = dao.getProductInfo(productId);

		//関連商品情報をdao.getProductInfoで取得した、categoryIdとproductIdを引数として取得。

		relatedProductDTOList = dao.relatedProductInfo(productDetailsDTO.getCategoryId(), productDetailsDTO.getProductId(), 0, 3);

		//詳細情報を得る商品のIDが0の場合は、詳細情報と関連商品情報の値をnullとする。
		if(productDetailsDTO.getProductId() == 0) {
			productDetailsDTO = null;
			relatedProductDTOList = null;
		}

		return result;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public ProductInfoDTO getProductDetailsDTO() {
		return productDetailsDTO;
	}

	public void setProductDetailsDTO(ProductInfoDTO productDetailsDTO) {
		this.productDetailsDTO = productDetailsDTO;
	}

	public List<ProductInfoDTO> getRelatedProductDTOList() {
		return relatedProductDTOList;
	}

	public void setRelatedProductDTOList(List<ProductInfoDTO> relatedProductDTOList) {
		this.relatedProductDTOList = relatedProductDTOList;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
