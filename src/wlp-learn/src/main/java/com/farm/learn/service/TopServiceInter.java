package com.farm.learn.service;

import com.farm.learn.domain.Top;
import com.farm.core.sql.query.DataQuery;

import java.io.FileNotFoundException;
import java.util.List;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：推荐阅读服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface TopServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 * @throws FileNotFoundException
	 */
	public Top insertTopEntity(Top entity, LoginUser user) throws FileNotFoundException;

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 * @throws FileNotFoundException
	 */
	public Top editTopEntity(Top entity, LoginUser user) throws FileNotFoundException;

	/**
	 * 更新排序号
	 * 
	 * @param id
	 * @param i
	 */
	public void updateSort(String id, int i);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteTopEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public Top getTopEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createTopSimpleQuery(DataQuery query);

	/**
	 * 獲得所有置顶推荐阅读
	 * 
	 * @param currentUser
	 * @return
	 */
	public List<Top> allTops(LoginUser currentUser);
}