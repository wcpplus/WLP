package com.farm.core.auth.domain;

/**
 * 一个菜单权限（用来做权限验证）
 * 
 * @author macpl
 *
 */
public class MenuRecord {
	private String id;
	private String key;

	public MenuRecord(String id, String key) {
		this.id = id;
		this.key = key;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
