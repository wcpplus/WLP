package com.farm.authority.password;

/**
 * 密码相关实现类
 * 
 * @author macpl
 *
 */
public interface PasswordProviderInter {

	/**
	 * 获得一个数据库的用户密码
	 * 
	 * @param loginname
	 *            用戶登錄名
	 * @param plaintextPassword
	 *            用戶明文密码
	 * @return
	 */
	public String getDBPasswordByPlaint(String loginname, String plaintextPassword);

	/**
	 * 获得一个数据库的用户密码
	 * 
	 * @param loginname
	 *            用戶登錄名
	 * @param clientPassword
	 *            客戶端來的密碼
	 * @return
	 */
	public String getDBPasswordByClient(String loginname, String clientPassword);

	/**
	 * 获得一个客户端密码
	 * 
	 * @param plaintextPassword
	 * @return
	 */
	public String getClientPassword(String plaintextPassword);
}
