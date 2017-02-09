package com.share.jack.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.share.jack.bean.BaseBean;
import com.share.jack.bean.RegisterBean;
import com.share.jack.db.DBUtils;
import com.share.jack.utils.Base64Utils;
import com.share.jack.utils.TimeUtils;
import com.share.jack.utils.TokenUtils;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
		super();
		// TODO Auto-generated constructor stub
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

		response.setHeader("Content-Type", "text/html;charset=UTF-8");   //设置头部的编码，防止中文乱码

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String userhead = request.getParameter("userhead");

		// 判空
		if (username == null || username.equals("") || password == null || password.equals("") || userhead == null
				|| userhead.equals("")) {
			return;
		}

		// 请求数据库
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect();
		BaseBean bean = new BaseBean();
		RegisterBean data = new RegisterBean();
		String imageName = TimeUtils.getNowTime() + ".jpg"; // 以当前时间作为图片名，具有唯一性
		System.out.println(getServletContext().getRealPath("/images"));
		String path = getServletContext().getRealPath("/images/" + imageName); // 图片的绝对路径(保存在apache服务器的某个文件夹目录下)

		if (!Base64Utils.GenerateImage(userhead, path)) { // 判断图片是否保存成功
			bean.setCode(-2);
			bean.setData(data);
			bean.setMsg("图片出错！！");
		} else if (dbUtils.isExistInDB(username)) {
			bean.setCode(-1);
			bean.setData(data);
			bean.setMsg("该账号已存在");
		} else if (!dbUtils.insertDataToDB(username, password, imageName)) {
			bean.setCode(0);
			bean.setMsg("注册成功！！");
			data.setUsername(username);
			data.setPassword(password);
			data.setToken(TokenUtils.getToken(username, password));
			bean.setData(data);
		} else {
			bean.setCode(500);
			bean.setData(data);
			bean.setMsg("数据库错误");
		}
		Gson gson = new Gson();
		String json = gson.toJson(bean);
		try {
			response.getWriter().println(json);// 将json数据传给客户端
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close(); // 关闭这个流，不然会发生错误的
		}
		dbUtils.closeConnect(); // 关闭数据库连接
	}
}
