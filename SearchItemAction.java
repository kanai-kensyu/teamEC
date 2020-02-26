package com.internousdev.rose.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.rose.dao.MCategoryDAO;
import com.internousdev.rose.dao.ProductInfoDAO;
import com.internousdev.rose.dto.MCategoryDTO;
import com.internousdev.rose.dto.ProductInfoDTO;
import com.internousdev.rose.util.InputChecker;
import com.opensymphony.xwork2.ActionSupport;

public class SearchItemAction extends ActionSupport implements SessionAware{

	private Map<String, Object> session;
	private String searchItem;
	private String categoryId;
	private List<String> searchErrorList;
	private List<ProductInfoDTO> productIntoDTOList;
	private List<MCategoryDTO> categoryList;

	private String[] keywordsList;

	List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();

	//検索はスペースのみでも大丈夫なので、まずスペースのみを除外して正規表現。

	public String execute() throws SQLException{

		productInfoDTOList = null;

		// (1)入力がされているかをチェック。
		if (StringUtils.isBlank(searchItem)) {
			searchItem = "";
		} else {
			InputChecker inputChecker = new InputChecker();
			//空欄でないものに正規表現をメソッドから使用。
			searchErrorList = inputChecker.doCheck("検索ワード", searchItem, 0, 50, true, true, true, true, true, true);
			if (searchErrorList.size() > 0) {
				return SUCCESS;
			}
		}
		searchErrorList = null;

		// (2)検索ワードを編集。
		searchItem = searchItem.replaceAll("　", " ").replaceAll("\\s{2,}", " ").trim();

		// (3)スペース区切りで複数のキーワードがある場合は分割。
		ProductInfoDAO productInfoDAO = new ProductInfoDAO();

		//分割した検索ワードをkeywordsListに入れる。
		String[] keywordsList = searchItem.split(" ");

		if (categoryId.equals("1")) {
			//全てのカテゴリーを指定したメソッド
			productInfoDTOList = productInfoDAO.allCategoryProductInfo(keywordsList);

		} else {
			// カテゴリー、キーワードを条件にするメソッド
			productInfoDTOList = productInfoDAO.CategoryProductInfo(keywordsList, categoryId);
		}
		if (!(productInfoDTOList.size() > 0)){
			productInfoDTOList = null;
		}

		// (4)カテゴリーを保持していない場合は、カテゴリーを保持する。
		if (!session.containsKey("categoryList")) {
			MCategoryDAO mCategoryDAO = new MCategoryDAO();
			List <MCategoryDTO> categoryList = new ArrayList<MCategoryDTO>();
			categoryList = mCategoryDAO.selectCategoryList();
			//カテゴリーリストをsessionに保持
			session.put("categoryList", categoryList);

			// カテゴリー(List)の要素数と同じ回数分、繰り返してセッションに入れる

			int n = 0;
			while (n <= categoryList.size()) {
				session.put("categoryId", categoryList.get(n).getCategoryId());
				session.put("categoryName", categoryList.get(n).getCategoryName());
				n++;
			}
		}
		return SUCCESS;

	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getSearchItem() {
		return searchItem;
	}

	public void setSearchItem(String searchItem) {
		this.searchItem = searchItem;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public List<String> getSearchErrorList() {
		return searchErrorList;
	}

	public void setSearchErrorList(List<String> searchErrorList) {
		this.searchErrorList = searchErrorList;
	}

	public List<ProductInfoDTO> getProductIntoDTOList() {
		return productIntoDTOList;
	}

	public void setProductIntoDTOList(List<ProductInfoDTO> productIntoDTOList) {
		this.productIntoDTOList = productIntoDTOList;
	}

	public List<MCategoryDTO> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<MCategoryDTO> categoryList) {
		this.categoryList = categoryList;
	}

	public String[] getKeywordsList() {
		return keywordsList;
	}

	public void setKeywordsList(String[] keywordsList) {
		this.keywordsList = keywordsList;
	}

	public List<ProductInfoDTO> getProductInfoDTOList() {
		return productInfoDTOList;
	}

	public void setProductInfoDTOList(List<ProductInfoDTO> productInfoDTOList) {
		this.productInfoDTOList = productInfoDTOList;
	}
}
