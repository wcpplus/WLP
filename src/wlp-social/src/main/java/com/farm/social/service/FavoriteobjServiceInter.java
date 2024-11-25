package com.farm.social.service;

import com.farm.social.domain.FavoriteObj;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：收藏对象服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface FavoriteobjServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public FavoriteObj insertFavoriteobjEntity(FavoriteObj entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public FavoriteObj editFavoriteobjEntity(FavoriteObj entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteFavoriteobjEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public FavoriteObj getFavoriteobjEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createFavoriteobjSimpleQuery(DataQuery query);

	/**
	 * 執行用戶收藏
	 * 
	 * @param appid
	 *            对象id
	 * @param apptype
	 *            对象类型
	 * @param currentUser
	 *            收藏用户
	 */
	public void favorite(String appid, String apptype, LoginUser currentUser);

	/**
	 * 取消收藏
	 * 
	 * @param appid
	 * @param currentUser
	 */
	public void unFavorite(String appid, LoginUser currentUser);

	/**
	 * 是否收藏對象
	 * 
	 * @param majorid
	 * @param currentUser
	 * @return
	 */
	public boolean isFavorite(String appid, LoginUser currentUser);

	/**
	 * 获得收藏对象(沒有就创建)
	 * 
	 * @param appid
	 * @return
	 */
	public FavoriteObj loadFavoriteObj(String appid, String apptype);

	/**创建用户收藏查询a.USERID as USERID,a.CTIME as CTIME,a.DOTYPE as DOTYPE,b.APPID as APPID,b.APPTYPE as APPTYPE,b.NUM as NUM
	 * @param query
	 * @return
	 */
	public DataQuery createUserFavoriteQuery(DataQuery query);
}