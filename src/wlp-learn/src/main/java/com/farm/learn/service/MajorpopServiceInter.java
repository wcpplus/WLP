package com.farm.learn.service;

import com.farm.learn.domain.MajorPop;
import com.farm.learn.enums.MajorFuncPOP;
import com.farm.core.sql.query.DataQuery;

import java.util.List;

import com.farm.authority.domain.User;
import com.farm.category.enums.FuncPOP;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：专业权限服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface MajorpopServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public MajorPop insertMajorpopEntity(MajorPop entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public MajorPop editMajorpopEntity(MajorPop entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteMajorpopEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public MajorPop getMajorpopEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createMajorpopSimpleQuery(DataQuery query, MajorFuncPOP poptype, String majorid);

	public void delPop(String id);

	public void addOrgPop(String majorid, String orgid, MajorFuncPOP funcPOP);

	public void addPostPop(String majorid, String postid, MajorFuncPOP funcPOP);

	public void addUserPop(String majorid, String userid, MajorFuncPOP funcPOP);

	/**
	 * 获得用户有權限的专业
	 * 
	 * @param currentUser
	 * @param write
	 * @return
	 */
	public List<String> getPopMajorIds(LoginUser user, MajorFuncPOP funcPOP);

	/**
	 * 用戶是否可以編輯专业
	 * 
	 * @param majorid
	 * @param currentUser
	 * @return
	 */
	public boolean isEditMajor(String majorid, LoginUser currentUser);

	/**
	 * 用戶是否可以查看专业
	 * 
	 * @param majorid
	 * @param currentUser
	 * @return
	 */
	public boolean isReadAble(String majorid, LoginUser currentUser);
}