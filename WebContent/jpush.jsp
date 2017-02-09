<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发送JPush自定义消息</title>
</head>
<body>

	<form action="PushServlet" method="post">
		积分限制：<input type="text" name="count" /><br /> 消息内容：<input type="text"
			name="message" /><br /> <input type="submit" value="推送" />
	</form>

</body>
</html>