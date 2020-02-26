package com.internousdev.rose.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.rose.dto.ProductInfoDTO;
import com.internousdev.rose.util.DBConnector;

public class ProductInfoDAO {

	//商品詳細機能から呼ばれるメソッド、商品検索機能から
	//呼ばれるメソッドの順で記述しています。

	//---- 商品詳細機能から呼ばれるメソッド------
	//-----productIdから商品情報を取得。--------
	public ProductInfoDTO getProductInfo(int productId) throws SQLException{

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		ProductInfoDTO productInfoDTO = new ProductInfoDTO();

		String sql = "SELECT * FROM product_info WHERE product_id = ?";
		try {

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, productId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				productInfoDTO.setCategoryId(rs.getInt("category_id"));
				productInfoDTO.setProductId(rs.getInt("product_id"));
				productInfoDTO.setProductName(rs.getString("product_name"));
				productInfoDTO.setProductKana(rs.getString("product_name_kana"));
				productInfoDTO.setFilePath(rs.getString("image_file_path"));
				productInfoDTO.setFileName(rs.getString("image_file_name"));
				productInfoDTO.setPrice(rs.getInt("price"));
				productInfoDTO.setReleaseDate(rs.getDate("release_date"));
				productInfoDTO.setReleaseCompany(rs.getString("release_company"));
				productInfoDTO.setProductDescription(rs.getString("product_description"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return productInfoDTO;
	}

	//関連商品情報を取得するためのメソッド
	public List<ProductInfoDTO> relatedProductInfo(int categoryId, int productId, int limitOffset, int limitRowCount) throws SQLException{

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();

		String sql = "SELECT * FROM product_info WHERE category_id=? and product_id not in(?) order by rand() limit ?,?";
		try {

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, categoryId);
			ps.setInt(2, productId);
			ps.setInt(3, limitOffset);
			ps.setInt(4, limitRowCount);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				ProductInfoDTO productInfoDTO = new ProductInfoDTO();

				productInfoDTO.setCategoryId(rs.getInt("category_id"));
				productInfoDTO.setProductId(rs.getInt("product_id"));
				productInfoDTO.setProductName(rs.getString("product_name"));
				productInfoDTO.setProductKana(rs.getString("product_name_kana"));
				productInfoDTO.setFilePath(rs.getString("image_file_path"));
				productInfoDTO.setFileName(rs.getString("image_file_name"));
				productInfoDTO.setPrice(rs.getInt("price"));
				productInfoDTO.setReleaseDate(rs.getDate("release_date"));
				productInfoDTO.setReleaseCompany(rs.getString("release_company"));
				productInfoDTO.setProductDescription(rs.getString("product_description"));

				productInfoDTOList.add(productInfoDTO);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return productInfoDTOList;
	}

	//---------------ここまで--------------------------------

	//----------以下、商品検索機能から呼ばれるメソッド-------

	//--全てのカテゴリーを指定したメソッド-----

	public List<ProductInfoDTO> allCategoryProductInfo(String[] keywordsList) throws SQLException{

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();

		String sql = "SELECT * FROM product_info product_id";

		for (int i = 0; i < keywordsList.length; i++) {
			if (i == 0) {
				sql += " WHERE (product_name LIKE '%" + keywordsList[i] + "%' OR product_name_kana LIKE '%"
						+ keywordsList[i] + "%')";
			} else {
				sql += " OR (product_name LIKE '%" + keywordsList[i] + "%' OR product_name_kana LIKE '%" + keywordsList[i]
						+ "%')";
			}
		}
		sql += " order by product_id asc";
		try {

			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				ProductInfoDTO productInfoDTO = new ProductInfoDTO();

				productInfoDTO.setCategoryId(rs.getInt("category_id"));
				productInfoDTO.setProductId(rs.getInt("product_id"));
				productInfoDTO.setProductName(rs.getString("product_name"));
				productInfoDTO.setProductKana(rs.getString("product_name_kana"));
				productInfoDTO.setFilePath(rs.getString("image_file_path"));
				productInfoDTO.setFileName(rs.getString("image_file_name"));
				productInfoDTO.setPrice(rs.getInt("price"));

				productInfoDTOList.add(productInfoDTO);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return productInfoDTOList;

	}

	//---特定のカテゴリーを指定したメソッド----
	public List<ProductInfoDTO> CategoryProductInfo(String[] keywordsList, String categoryId) throws SQLException{

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();

		String sql = "SELECT * FROM product_info WHERE category_id = ? AND (";

		for (int i = 0; i < keywordsList.length; i++) {
			if (i == 0) {
				sql += " (product_name LIKE '%" + keywordsList[i] + "%' OR product_name_kana LIKE '%"
						+ keywordsList[i] + "%')";
			} else {
				sql += " OR (product_name LIKE '%" + keywordsList[i] + "%' OR product_name_kana LIKE '%" + keywordsList[i]
						+ "%')";
			}
		}
		sql += " ) order by product_id asc";
		try {

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, categoryId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				ProductInfoDTO productInfoDTO = new ProductInfoDTO();

				productInfoDTO.setCategoryId(rs.getInt("category_id"));
				productInfoDTO.setProductId(rs.getInt("product_id"));
				productInfoDTO.setProductName(rs.getString("product_name"));
				productInfoDTO.setProductKana(rs.getString("product_name_kana"));
				productInfoDTO.setFilePath(rs.getString("image_file_path"));
				productInfoDTO.setFileName(rs.getString("image_file_name"));
				productInfoDTO.setPrice(rs.getInt("price"));

				productInfoDTOList.add(productInfoDTO);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return productInfoDTOList;
	}
}
