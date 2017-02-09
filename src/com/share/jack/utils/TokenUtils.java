package com.share.jack.utils;

public class TokenUtils {
	public static String getToken(String username, String password) {
		StringBuffer sb = new StringBuffer();
		if (username.length() > password.length()) {
			for (int i = 0; i < username.length(); i++) {
				if (i >= password.length()) {
					sb.append(username.charAt(i));
				} else {
					sb.append(username.charAt(i));
					sb.append(password.charAt(i));
				}
			}
		} else {
			for (int i = 0; i < password.length(); i++) {
				if (i >= username.length()) {
					sb.append(password.charAt(i));
				} else {
					sb.append(username.charAt(i));
					sb.append(password.charAt(i));
				}
			}
		}
		return sb.toString();
	}
}