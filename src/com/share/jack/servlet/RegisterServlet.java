package com.share.jack.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.share.jack.bean.BaseBean;
import com.share.jack.bean.UserBean;
import com.share.jack.db.DBUtils;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setHeader("Content-Type", "text/html;charset=UTF-8");
		
		String username = request.getParameter("username"); // 获取客户端传过来的参数
		String password = request.getParameter("password");

		if (username == null || username.equals("") || password == null || password.equals("")) {
			System.out.println("用户名或密码为空");
			return;
		}

		// 请求数据库
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect(); // 打开数据库连接
		BaseBean data = new BaseBean(); // 基类对象，回传给客户端的json对象
		UserBean userBean = new UserBean();

		if (dbUtils.isExistInDB(username, password)) { // 判断账号是否存在
			data.setCode(-1);
			data.setData(userBean);
			data.setMsg("该账号已存在");
		} else if (!dbUtils.insertDataToDB(username, password)) { // 注册成功
			data.setCode(0);
			data.setMsg("注册成功！！");
			ResultSet rs = dbUtils.getUser();
			int id = -1;
			if (rs != null) {
				try {
					while (rs.next()) {
						if (rs.getString("user_name").equals(username) && rs.getString("user_pwd").equals(password)) {
							id = rs.getInt("user_id");
						}
					}
					userBean.setId(id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			userBean.setUsername(username);
			userBean.setPassword(password);
			data.setData(userBean);
		} else { // 注册不成功，这里错误没有细分，都归为数据库错误
			data.setCode(500);
			data.setData(userBean);
			data.setMsg("数据库错误");
		}
		Gson gson = new Gson();
		String json = gson.toJson(data);
		System.out.println("json--------->" + json);
		try {
			response.getWriter().println(json); // 将json数据传给客户端
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close(); // 关闭这个流，不然会发生错误的
		}
		dbUtils.closeConnect(); // 关闭数据库连接
	}
}
