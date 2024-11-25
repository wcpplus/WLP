package com.farm.category.service;

import com.farm.category.domain.ClassTypePop;
import com.farm.category.enums.FuncPOP;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：课程分类权限服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface ClasstypepopServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public ClassTypePop insertClasstypepopEntity(ClassTypePop entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public ClassTypePop editClasstypepopEntity(ClassTypePop entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteClasstypepopEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public ClassTypePop getClasstypepopEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createClasstypepopSimpleQuery(DataQuery query, FuncPOP type, String typeid);

	/**
	 * 添加一个用户权限
	 * 
	 * @param tid
	 * @param id
	 * @param enum1
	 */
	public void addUserTypePop(String typeid, String userid, FuncPOP funcPOP);

	/**
	 * 删除一个分类权限
	 * 
	 * @param typepopid
	 */
	public void delTypePop(String typepopid);

	/**
	 * 添加一个组织机构到 分类权限
	 * 
	 * @param typeid
	 * @param orgid
	 * @param read
	 */
	public void addOrgTypePop(String typeid, String orgid, FuncPOP funcPOP);

	/**
	 * 添加一个岗位到 分类权限
	 * 
	 * @param typeid
	 * @param orgid
	 * @param read
	 */
	public void addPostTypePop(String typeid, String postid, FuncPOP funcPOP);

}