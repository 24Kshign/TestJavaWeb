package com.share.jack.bean;

public class LoginData {

	private int id;
	private String username;
	private String password;
	private String count;
	private String alias;
	private String tag;

	public LoginData(int id, String username, String password, String count, String alias, String tag) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.count = count;
		this.alias = alias;
		this.tag = tag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}