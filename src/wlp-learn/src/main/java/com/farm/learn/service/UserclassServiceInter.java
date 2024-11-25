package com.farm.learn.service;

import com.farm.learn.domain.UserClass;
import com.farm.learn.domain.ex.ClassLearnView;
import com.farm.learn.domain.ex.HourView;
import com.farm.learn.domain.ex.MajorView;
import com.farm.sfile.exception.FileExNameException;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.sql.SQLException;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：用户课程服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface UserclassServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public UserClass insertUserclassEntity(UserClass entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public UserClass editUserclassEntity(UserClass entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteUserclassEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public UserClass getUserclassEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createUserclassSimpleQuery(DataQuery query);

	/**
	 * 获得用户的课程学习进度
	 * 
	 * @param classid
	 * @param currentUser
	 * @return
	 */
	public ClassLearnView getClassLearnView(String classid, LoginUser currentUser);

	/**
	 * 开始学习一个课程
	 * 
	 * @param classid
	 * @param hourid
	 *            可以为空，为空时继续上次的学习
	 * @param currentUser
	 * @return
	 * @throws FileExNameException
	 */
	public HourView startLearnClass(String classid, String hourid, LoginUser currentUser) throws FileExNameException;

	/**
	 * 获得用户课程
	 * 
	 * @param query
	 * @param currentUser
	 * @return
	 * @throws SQLException
	 */
	public DataResult getUserClasses(DataQuery query, LoginUser currentUser) throws SQLException;

	/**
	 * 完成当前课时学习，并返回下一节课时
	 * 
	 * @param classid
	 * @return
	 */
	public String finishCurrentHour(String classid, LoginUser currentUser);

	/**
	 * 刪除用户课程
	 * 
	 * @param classid
	 * @param userId
	 */
	public void delUserClass(String classid, String userId);

	/**
	 * 获得用户学习中的课程
	 * 
	 * @param userid
	 * @return
	 */
	public int getUserCurrentClassNum(String userid);

	/**
	 * 课程的学员信息
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public DataResult learUsersQuery(DataQuery query) throws SQLException;

	/**
	 * 专业中绑定用户的学习进度(同时绑定当前用户的可读状态)
	 * 
	 * @param majorview
	 * @param currentUser
	 */
	public void bingUserClasses(MajorView majorview, LoginUser currentUser);
}