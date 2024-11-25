package com.farm.social.service;

import com.farm.social.domain.Outsys;
import com.farm.core.sql.query.DataQuery;

import java.util.List;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：外部系统服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface OutsysServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public Outsys insertOutsysEntity(Outsys entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public Outsys editOutsysEntity(Outsys entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteOutsysEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public Outsys getOutsysEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createOutsysSimpleQuery(DataQuery query);

	/**获得全部外部系统
	 * @return
	 */
	public List<Outsys> getAllSys();
}