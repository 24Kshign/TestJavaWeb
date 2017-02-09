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
import com.share.jack.bean.RegisterBean;
import com.share.jack.db.DBUtils;

/**
 * Servlet implementation class GetUserInfoServlet
 */
@WebServlet("/GetUserInfoServlet")
public class GetUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetUserInfoServlet() {
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

		String username = request.getParameter("username");

		// 判空
		if (username == null || username.equals("")) {
			return;
		}

		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect();
		ResultSet rs = dbUtils.getUserInfo();
		RegisterBean bean = new RegisterBean();
		BaseBean data = new BaseBean();

		if (rs != null) {
			try {
				while (rs.next()) {
					if (rs.getString("user_name").equals(username)) {
						bean.setUsername(username);
						bean.setHead(rs.getString("user_head"));
						bean.setPassword(rs.getString("user_pwd"));
						bean.setToken(rs.getString("token"));
						break;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		data.setCode(0);
		data.setMsg("获取信息成功");
		data.setData(bean);
		Gson gson = new Gson();
		String json = gson.toJson(data);
		try {
			response.getWriter().println(json);// 将json数据传给客户端
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close();
		}
		dbUtils.closeConnect();
	}
}
