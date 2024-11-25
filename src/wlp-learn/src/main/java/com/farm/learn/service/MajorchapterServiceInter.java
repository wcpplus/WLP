package com.farm.learn.service;

import com.farm.learn.domain.MajorChapter;
import com.farm.core.sql.query.DataQuery;

import java.util.List;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：专业步骤服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface MajorchapterServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public MajorChapter insertMajorchapterEntity(MajorChapter entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public MajorChapter editMajorchapterEntity(MajorChapter entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteMajorchapterEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public MajorChapter getMajorchapterEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createMajorchapterSimpleQuery(DataQuery query);

	public List<MajorChapter> getChapters(String majorid);

	/**
	 * 步驟中添加課程
	 * 
	 * @param chapterid
	 * @param classtid
	 */
	public void addClasst(String chapterid, String classtid);
}