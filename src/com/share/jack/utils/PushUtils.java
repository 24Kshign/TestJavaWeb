package com.share.jack.utils;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class PushUtils {

	private static final String APP_KEY = "196dd16451d0eaf922c92d13";
	private static final String MASTER_SECRET = "ea58ea592ada2097d1ea02f9";

	public static PushResult sendPushMessage(String alias, String title, String msg) {
		JPushClient jClient = new JPushClient(MASTER_SECRET, APP_KEY);
		// 构建推送的对象
		PushPayload pushPayload = buildByAlias(alias, title, msg);
		try {
			PushResult pr = jClient.sendPush(pushPayload);
			System.out.println("PushResult===" + pr);
			return pr;
		} catch (APIConnectionException | APIRequestException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 构建推送的对象：所有对象
	 * 
	 * @param alias
	 *            推送目标：别名为alias
	 * @param title
	 *            标题
	 * @param msg
	 *            内容
	 * @return
	 */
	public static PushPayload buildByAlias(String alias, String title, String msg) {
		Map<String, String> map = new HashMap<>();
		map.put("url", "http://www.baidu.com");
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))
				.setNotification(Notification.android(title, msg, map)).build();
	}
}