package com.internousdev.laravel.action;


import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.laravel.dao.CartInfoDAO;
import com.internousdev.laravel.dto.CartInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class CartAction extends ActionSupport implements SessionAware {
	
	private List<CartInfoDTO> cartInfoList;
	private Map<String, Object> session;
	private int totalPrice;
	
	public String execute() {
		
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
		
		//カート情報とカート合計金額情報を取得
		cartInfoList = cartInfoDAO.getUserCartInfo(userId);
		totalPrice = cartInfoDAO.cartTotalPrice(userId);
		
		//セッションタイムアウト
		if (!session.containsKey("user_id") && !session.containsKey("temp_user_id")) {
			return "sessionTimeout";
		}
		
		//必要ないセッション情報を削除
		session.remove("family_name");
		session.remove("first_name");
		session.remove("family_name_kana");
		session.remove("first_name_kana");
		session.remove("email");
		session.remove("tel_number");
		session.remove("user_address");

		return SUCCESS;
	}
	
	public int getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
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
}
