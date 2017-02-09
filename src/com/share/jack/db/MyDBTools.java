package com.share.jack.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDBTools {
	private Connection conn;
	private String url = "jdbc:mysql://127.0.0.1:3306/Login"; // 指定连接数据库的URL
	private String user = "root"; // 指定连接数据库的用户名
	private String password = "1002"; // 指定连接数据库的密码

	private Statement sta;
	private ResultSet rs;

	// 打开数据库连接
	public void openConnect() {
		try {
			// 加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);// 创建数据库连接
			if (conn != null) {
				System.out.println("数据库连接成功"); // 连接成功的提示信息
			}
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

	public ResultSet getUserInfo(String username, String password) {
		try {
			PreparedStatement ps = null;
			String sql = "select * from jpush_userinfo where username=? and password=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			// 执行SQL查询语句
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getUserCountInfo(int count) {
		try {
			PreparedStatement ps = null;
			String sql = "select * from jpush_userinfo where count >= ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, count);
			// 执行SQL查询语句
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// 关闭数据库连接
	public void closeConnect() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (sta != null) {
				sta.close();
			}
			if (conn != null) {
				conn.close();
			}
			System.out.println("关闭数据库连接成功");
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}