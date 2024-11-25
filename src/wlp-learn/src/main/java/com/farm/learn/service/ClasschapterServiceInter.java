package com.farm.learn.service;

import com.farm.learn.domain.ClassChapter;
import com.farm.core.sql.query.DataQuery;

import java.util.List;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：课程章节服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface ClasschapterServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public ClassChapter insertClasschapterEntity(ClassChapter entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public ClassChapter editClasschapterEntity(ClassChapter entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteClasschapterEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public ClassChapter getClasschapterEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createClasschapterSimpleQuery(DataQuery query);

	/**
	 * 獲得課程的所有章節
	 * 
	 * @param classid
	 * @return
	 */
	public List<ClassChapter> getChapters(String classid);
}