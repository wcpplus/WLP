package com.farm.learn.service;

import com.farm.learn.domain.ClassHour;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：课程课时服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface ClasshourServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 * @throws FileNotFoundException
	 */
	public ClassHour insertClasshourEntity(ClassHour entity, LoginUser user) throws FileNotFoundException;

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 * @throws FileNotFoundException
	 */
	public ClassHour editClasshourEntity(ClassHour entity, LoginUser user) throws FileNotFoundException;

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteClasshourEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public ClassHour getClasshourEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createClasshourSimpleQuery(DataQuery query);

	/**
	 * 获得课程下所有课时
	 * 
	 * @param classid
	 * @return
	 */
	public List<ClassHour> getHoursByClass(String classid);

	/**
	 * 查询课时
	 * 
	 * @param query
	 * @param currentUser
	 * @return
	 * @throws SQLException
	 */
	public DataResult searchHoursQuery(DataQuery query, LoginUser currentUser) throws SQLException;

	/**
	 * 获得排序的课时
	 * 
	 * @param classid
	 * @return
	 */
	public List<ClassHour> getSortHoursByClass(String classid);
}