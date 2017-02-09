package com.share.jack.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.share.jack.db.MyDBTools;
import com.share.jack.utils.PushUtils;

/**
 * Servlet implementation class PushServlet
 */
@WebServlet("/PushServlet")
public class PushServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PushServlet() {
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
		request.setCharacterEncoding("UTF-8");
		int count = Integer.valueOf(request.getParameter("count"));
		String message = request.getParameter("message");
		MyDBTools myDBTools = new MyDBTools();
		ResultSet rs = null;
		if (count > -1 && !message.isEmpty()) {
			// 从数据库中获取所有的积分大于count的用户，从而进行推送
			myDBTools.openConnect();
			rs = myDBTools.getUserCountInfo(count);
			try {
				while (rs.next()) {
					// 获取这些用户的别名
					String alias = rs.getString("alias");
					// 告诉极光服务器，这个用户需要推送
					PushUtils.sendPushMessage(alias, "涨价通知", message);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		myDBTools.closeConnect();
	}
}
