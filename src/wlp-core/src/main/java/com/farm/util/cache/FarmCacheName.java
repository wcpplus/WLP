package com.farm.util.cache;

/**
 * 缓存名称
 * 
 * @author macpl
 *
 */
public enum FarmCacheName {
	// 知识缓存
	wlp_file_persist("wlp_file_persist"),
	// 单点登陆的客户端clientId缓存
	SsoSessionCache("wcp-sso-clientids");
	/**
	 * 持久缓存
	 */
	private String permanentCacheName;

	FarmCacheName(String permanentCacheName) {
		this.permanentCacheName = permanentCacheName;
	}

	/**
	 * 如果只有一个缓存就是这个持久缓缓存
	 * 
	 * @return
	 */
	public String getPermanentCacheName() {
		return permanentCacheName;
	}
}
