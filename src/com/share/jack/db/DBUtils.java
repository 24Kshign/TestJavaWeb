package com.share.jack.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.share.jack.utils.TokenUtils;

public class DBUtils {
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

	public ResultSet getUserInfo() {
		// 创建 statement对象
		try {
			sta = conn.createStatement();
			// 执行SQL查询语句
			rs = sta.executeQuery("select * from userinfo");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getUser() {
		// 创建 statement对象
		try {
			sta = conn.createStatement();
			// 执行SQL查询语句
			rs = sta.executeQuery("select * from user");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public boolean insertDataToDB(String username, String password, String userhead) {
		String token = TokenUtils.getToken(username, password);
		System.out.println("path------->" + userhead);
		String imagePath = "http://192.168.1.101:8080/MyWeb/images/" + userhead;
		try {
			sta = conn.createStatement();
			String sql = " insert into userinfo ( user_name , user_pwd , token , user_head ) values ( " + "'" + username
					+ "', " + "'" + password + "', " + "'" + token + "', " + "'" + imagePath + "' )";
			return sta.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 注册 将用户名和密码插入到数据库(id设置的是自增长的，因此不需要插入)
	public boolean insertDataToDB(String username, String password) {
		String sql = " insert into user ( user_name , user_pwd ) values ( " + "'" + username + "', " + "'" + password
				+ "' )";
		try {
			sta = conn.createStatement();
			// 执行SQL查询语句
			return sta.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isExistInDB(String username, String password) {
		boolean isFlag = false;
		// 创建 statement对象
		try {
			sta = conn.createStatement();
			// 执行SQL查询语句
			rs = sta.executeQuery("select * from user");
			if (rs != null) {
				while (rs.next()) {
					if (rs.getString("user_name").equals(username) && rs.getString("user_pwd").equals(password)) {
						isFlag = true;
						break;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			isFlag = false;
		}
		return isFlag;
	}

	// 判断数据库中是否存在某个用户名,注册的时候判断
	public boolean isExistInDB(String username) {
		boolean isFlag = false;
		// 创建 statement对象
		try {
			sta = conn.createStatement();
			// 执行SQL查询语句
			rs = sta.executeQuery("select * from userinfo");
			if (rs != null) {
				while (rs.next()) {
					if (rs.getString("user_name").equals(username)) {
						isFlag = true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			isFlag = false;
		}
		return isFlag;
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