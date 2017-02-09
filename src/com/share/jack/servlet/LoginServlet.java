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
import com.share.jack.bean.LoginData;
import com.share.jack.db.MyDBTools;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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
		String password = request.getParameter("password");
		int id = -1;
		String count = "";
		String alias = "";
		String tag = "";

		MyDBTools myDBTools = new MyDBTools();
		myDBTools.openConnect();
		ResultSet rs = myDBTools.getUserInfo(username, password);
		if (rs != null) {
			try {
				while (rs.next()) {
					id = rs.getInt("id");
					count = rs.getString("count");
					alias = rs.getString("alias");
					tag = rs.getString("tag");
					LoginData loginData = new LoginData(id, username, password, count, alias, tag);
					Gson gson = new Gson();
					String json = gson.toJson(loginData);
					System.out.println("json-------->" + json);
					response.getWriter().println(json);// 将json数据传给客户端
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				response.getWriter().close();
			}
		}
		myDBTools.closeConnect();
	}
}