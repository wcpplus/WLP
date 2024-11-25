package com.farm.core.auth.domain;

public interface LoginUser {

	public String getId();

	public String getName();

	public String getLoginname();

	/**
	 * 1:系统用户:2其他3超级用户、4：只读用户、5：知识用户、6：问答用户
	 * 
	 * @return
	 */
	public String getType();

	public String getIp();
}
