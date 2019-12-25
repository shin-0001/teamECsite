package com.internousdev.laravel.action;


import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.laravel.dao.CartInfoDAO;
import com.internousdev.laravel.dto.CartInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class AddCartAction extends ActionSupport implements SessionAware {

	private int productId;
	private int count;
	private List<CartInfoDTO> cartInfoList;
	private Map<String, Object> session;
	private int totalPrice;

	public String execute() {

		String result = ERROR;
		String userId = null;
		CartInfoDAO cartInfoDAO = new CartInfoDAO();

		//sessionをObject型からString型にしてから、ログインフラグが"0"か"1"か調べる
		//ログインフラグが"0"だったら、ログインせずに買い物しているので変数userIdにsessionにあるtemp_user_idをいれる
		//ログインフラグが"1"だったら、ログイン済みユーザーなので変数userIdにsessionにあるuser_idをいれる
		//ログインフラグがあったら、ユーザーIDで情報取得
		//ログインフラグがなかったら、仮ユーザーIDで情報取得
		String loginFlg = String.valueOf(session.get("login_flg"));

		int logined = "null".equals(loginFlg) ? 0 : Integer.parseInt(loginFlg);

		//ログインフラグあるとき
		if (logined == 1) {
			userId = session.get("user_id").toString();
		//ログインフラグないとき
		} else {
			userId = String.valueOf(session.get("temp_user_id"));
		}

		int cnt = 0;
		//カート情報の存在チェック
		if (cartInfoDAO.checkCartInfo(userId, productId)) {
		//カート情報ある場合カートの情報更新
			cnt = cartInfoDAO.updateCartInfo(userId, productId, count);
		//カート情報ない場合カートに情報追加
		} else {
			cnt = cartInfoDAO.addCartInfo(userId, productId, count);
		}

		//カート情報に更新または追加があるとき、カート情報と合計金額を取得
		if (cnt > 0) {
			cartInfoList = cartInfoDAO.getUserCartInfo(userId);
			totalPrice = cartInfoDAO.cartTotalPrice(userId);
			result = SUCCESS;
		}

		//セッションタイムアウト
		if (!session.containsKey("user_id") && !session.containsKey("temp_user_id")) {
			return "sessionTimeout";
		}

		return result;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<CartInfoDTO> getCartInfoList() {
		return cartInfoList;
	}

	public void setCartInfoList(List<CartInfoDTO> cartInfoList) {
		this.cartInfoList = cartInfoList;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
}
