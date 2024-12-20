package com.farm.authority.service;

import com.farm.authority.domain.Outuser;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：外部账户服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface OutuserServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public Outuser insertOutuserEntity(Outuser entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public Outuser editOutuserEntity(Outuser entity);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteOutuserEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public Outuser getOutuserEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createOutuserSimpleQuery(DataQuery query);


	/**
	 * 通过外部用户id获得外部账户对象
	 * 
	 * @param outuserid
	 *            外部用户id(ACCOUNTID)
	 * @return
	 */
	public Outuser getOutuserByAccountId(String accountId);

	/**
	 * 通过用户id获得外部账户对象
	 * 
	 * @param readUserId
	 * @return
	 */
	public Outuser getOutuserByUserid(String readUserId, String contentLimitlike);

	/**通過外部id獲得一個本地用戶
	 * @param outuserid
	 * @return
	 */
	public LoginUser getLocalUserByAccountId(String accountId);

	/**創建一個外表用戶 
	 * @param outuserid
	 * @param name
	 * @param content
	 */
	public Outuser creatOutUser(String accountId, String name, String content);
}