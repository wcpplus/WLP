package com.farm.social.service;

import com.farm.social.domain.EvaluationObj;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

import java.sql.SQLException;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：评论对象服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface EvaluationobjServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public EvaluationObj insertEvaluationobjEntity(EvaluationObj entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public EvaluationObj editEvaluationobjEntity(EvaluationObj entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteEvaluationobjEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public EvaluationObj getEvaluationobjEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createEvaluationobjSimpleQuery(DataQuery query);

	/**
	 * 发布一个评价
	 * 
	 * @param appid
	 * @param apptype
	 * @param score
	 *            得分
	 * @param comments
	 * @param currentUser
	 */
	public void publicEvaluation(String appid, String apptype, Integer score, String comments, LoginUser currentUser);

	/**
	 * 加载评价
	 * 
	 * @param appid
	 * @param page
	 * @param pagesize
	 * @return
	 * @throws SQLException
	 */
	public DataResult loadEvaluation(String appid, Integer page, int pagesize) throws SQLException;

	/**
	 * 刪除一条评论
	 * 
	 * @param evaluationUserid
	 */
	public void deleteEvaluation(String evaluationUserid);
}