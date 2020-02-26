package com.internousdev.rose.action;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.rose.dao.CartInfoDAO;
import com.internousdev.rose.dto.CartInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class AddCartAction extends ActionSupport implements SessionAware{

	private String productId;
	private String productCount;
	private int totalPrice;
	private List<CartInfoDTO> cartItemList;

	private Map<String, Object> session;

	public String execute() throws SQLException {
		//ユーザーIDまたは仮ユーザーIDが存在するか確認
		if (!session.containsKey("proUserId") && !session.containsKey("userId")) {
			return "sessionTimeout";
		}

		String result = ERROR;
		String userId = null;

		boolean loginFlg = Boolean.valueOf(session.get("loginFlg").toString());
		if(loginFlg) {
			userId = session.get("userId").toString();
		} else {
			userId = String.valueOf(session.get("proUserId"));
		}

		//カートに商品を新規登録 or 情報を更新する
		CartInfoDAO cartInfoDAO = new CartInfoDAO();
		int count = 0;


		//追加しようとしている商品と同じ商品のデータがすでにDBに存在するかをチェックする
		if (cartInfoDAO.isExistCartInfo(productId, userId)) {
			//存在する場合は、商品の個数を更新する。
			count = cartInfoDAO.updateProductCount(productCount, userId, productId);
		} else {
			//存在しない場合は、新規登録を行う。
			count = cartInfoDAO.addCartItem(userId, productId, productCount);
		}

		//カートに商品を追加・更新できた場合
		if(count > 0) {
			cartItemList = cartInfoDAO.getCartInfo(userId);

			totalPrice = cartInfoDAO.getTotalPrice(userId);
			result = SUCCESS;
		}
		return result;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductCount() {
		return productCount;
	}

	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<CartInfoDTO> getCartItemList() {
		return cartItemList;
	}

	public void setCartItemList(List<CartInfoDTO> cartItemList) {
		this.cartItemList = cartItemList;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
