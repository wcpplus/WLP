package com.farm.social.service;

import com.farm.social.domain.CommentsObj;
import com.farm.social.domain.CommentsUser;
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
public interface CommentsobjServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public CommentsObj insertCommentsobjEntity(CommentsObj entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public CommentsObj editCommentsobjEntity(CommentsObj entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteCommentsobjEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public CommentsObj getCommentsobjEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createCommentsobjSimpleQuery(DataQuery query);

	/**
	 * 发布评论
	 * 
	 * @param appid
	 * @param apptype
	 * @param parentid
	 * @param comments
	 * @param currentUser
	 */
	public void publicComments(String appid, String apptype, String parentid, String comments, LoginUser currentUser);

	/**
	 * 加载评论
	 * 
	 * @param appids
	 * @return
	 * @throws SQLException
	 */
	public DataResult loadComments(String appids, int page, int size) throws SQLException;

	/**
	 * 获得用户评论
	 * 
	 * @param commentsUserId
	 * @return
	 */
	public CommentsUser getUserComments(String commentsUserId);

	/**
	 * 刪除评论
	 * 
	 * @param evaluationUserid
	 */
	public void deleteComments(String evaluationUserid);
}