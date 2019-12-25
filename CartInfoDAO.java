package com.internousdev.laravel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.laravel.dto.CartInfoDTO;
import com.internousdev.laravel.util.DBConnector;

public class CartInfoDAO {
	
	//ユーザーIDで商品情報とカート情報を取得し、合計金額計算
	public List<CartInfoDTO> getUserCartInfo(String userId) {
		
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		List<CartInfoDTO> cartInfoDTO = new ArrayList<CartInfoDTO>();
		
		String sql = "SELECT"
				+ " ci.id as id,"
				+ " ci.user_id as user_id,"
				+ " ci.product_id as product_id,"
				+ " ci.product_count as product_count,"
				+ " pi.price as price,"
				+ " pi.product_name as product_name,"
				+ " pi.product_name_kana as product_name_kana,"
				+ " pi.image_file_path as image_file_path, "
				+ " pi.image_file_name as image_file_name, "
				+ " pi.release_date as release_date,"
				+ " pi.release_company as release_company,"
				+ " pi.status as status,"
				+ " (ci.product_count * pi.price) as total_fee,"
				+ " ci.regist_date as regist_date,"
				+ " ci.update_date as update_date"
				+ " FROM cart_info ci"
				+ " LEFT JOIN product_info pi"
				+ " ON ci.product_id = pi.product_id"
				+ " WHERE ci.user_id = ?"
				+ " order by update_date desc, regist_date desc";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				CartInfoDTO dto = new CartInfoDTO();
				dto.setId(rs.getInt("id"));
				dto.setUserId(rs.getString("user_id"));
				dto.setProductId(rs.getInt("product_id"));
				dto.setProductCount(rs.getInt("product_count"));
				dto.setProductName(rs.getString("product_name"));
				dto.setProductNameKana(rs.getString("product_name_kana"));
				dto.setPrice(rs.getInt("price"));
				dto.setImageFilePath(rs.getString("image_file_path"));
				dto.setImageFileName(rs.getString("image_file_name"));
				dto.setReleaseDate(rs.getDate("release_date"));
				dto.setReleaseCompany(rs.getString("release_company"));
				dto.setStatus(rs.getString("status"));
				dto.setTotalFee(rs.getInt("total_fee"));
				cartInfoDTO.add(dto);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return cartInfoDTO;
	}
	
	//新しくカートに情報を追加(返り値で登録されたか判断する)
	public int addCartInfo(String userId, int productId, int productCount) {
		
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		int result = 0;
		
		String sql = "INSERT INTO cart_info(user_id, product_id, product_count, regist_date, update_date)"
				+ "VALUES(?,?,?,now(),now())";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setInt(2, productId);
			ps.setInt(3, productCount);
			result = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//すでにカートに入っている商品の個数を更新する
	public int updateCartInfo(String userId, int productId, int productCount) {
		
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		int result = 0;
		
		String sql = "UPDATE cart_info "
				+ "SET product_count = (product_count + ?), update_date = now() "
				+ "WHERE user_id = ? AND product_id = ?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, productCount);
			ps.setString(2, userId);
			ps.setInt(3, productId);
			result = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//引数で送られてきたユーザーIDと商品IDが一致するカート情報を削除する
	public int delete(String userId, int productId) {
		
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		int result = 0;
		
		String sql = "DELETE "
				+ "FROM cart_info "
				+ "WHERE user_id = ? AND product_id = ?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setInt(2, productId);
			result = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//userIdのユーザーのカート情報を全部消去
	public int deleteAll(String userId) {
		
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		int result = 0;
		
		String sql = "DELETE "
				+ "FROM cart_info "
				+ "WHERE user_id = ?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			result = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//userIdのユーザーのカート情報がすでにあるかどうか
	public boolean checkCartInfo(String userId, int productId) {
		
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		boolean result = false;
		
		String sql = "SELECT COUNT(id) AS cnt "
				+ "FROM cart_info "
				+ "WHERE user_id = ? AND product_id = ?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setInt(2, productId);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt("cnt") > 0) {
					result = true;
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//カートに入っている商品全ての金額の合計
	public int cartTotalPrice(String userId) {
		
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		int result = 0;
		
		String sql = "SELECT sum(product_count * price) as cart_total_price "
				+ "FROM cart_info ci "
				+ "JOIN product_info pi "
				+ "ON ci.product_id = pi.product_id "
				+ "WHERE user_id = ? group by user_id";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt("cart_total_price");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//仮ユーザーのカート情報をuserIdのユーザーのカート情報と紐付け
	public int linkCartInfo(String userId, String tempUserId, int productId) {
		
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		int result = 0;
		
		String sql = "UPDATE cart_info "
				+ "SET user_id = ?, update_date = now() "
				+ "WHERE user_id = ? AND product_id = ?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, tempUserId);
			ps.setInt(3, productId);
			result = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
