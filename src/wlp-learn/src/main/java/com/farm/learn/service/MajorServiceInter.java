package com.farm.learn.service;

import com.farm.learn.domain.Major;
import com.farm.learn.domain.ex.MajorView;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

import java.sql.SQLException;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：专业服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface MajorServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public Major insertMajorEntity(Major entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public Major editMajorEntity(Major entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteMajorEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public Major getMajorEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createMajorSimpleQuery(DataQuery query);

	/**
	 * 获得未发布的专业（編輯专业）
	 * 
	 * @param query
	 * @param currentUser
	 * @return
	 * @throws SQLException
	 */
	public DataResult getTempMajor(DataQuery query, LoginUser currentUser) throws SQLException;

	/**
	 * 获得已发布的专业（編輯专业）
	 * 
	 * @param query
	 * @param currentUser
	 * @return
	 * @throws SQLException
	 */
	public DataResult getPublicMajor(DataQuery query, LoginUser currentUser) throws SQLException;

	/**
	 * 发布专业
	 * 
	 * @param majorid
	 * @param currentUser
	 */
	public void publicMajor(String majorid, LoginUser currentUser);

	/**
	 * 返回专业封装对象
	 * 
	 * @param majorid
	 * @return
	 */
	public MajorView getMajorView(String majorid);

	/**
	 * 最新專業
	 * 
	 * @param currentUser
	 * @return
	 * @throws SQLException 
	 */
	public DataResult getNewMajors(LoginUser currentUser,int num) throws SQLException;

	/**取消发布专业
	 * @param majorid
	 * @param currentUser
	 */
	public void tempMajor(String majorid, LoginUser currentUser);

	/**专业查询
	 * @param query
	 * @param currentUser
	 * @return
	 * @throws SQLException 
	 */
	public DataResult searchMajorsQuery(DataQuery query, LoginUser currentUser) throws SQLException;
}