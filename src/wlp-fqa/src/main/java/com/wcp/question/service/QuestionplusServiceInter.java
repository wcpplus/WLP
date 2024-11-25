package com.wcp.question.service;

import com.wcp.question.domain.Questionplus;
import com.farm.core.sql.query.DataQuery;

import java.util.List;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：追加提问服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface QuestionplusServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public Questionplus insertQuestionplusEntity(Questionplus entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public Questionplus editQuestionplusEntity(Questionplus entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteQuestionplusEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public Questionplus getQuestionplusEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createQuestionplusSimpleQuery(DataQuery query);

	/**
	 * 获得问题的所有追加问题
	 * 
	 * @param questionid
	 * @return
	 */
	public List<Questionplus> getQuestionplusByQuestionId(String questionid);
}