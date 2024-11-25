package com.farm.wcp.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.farm.wcp.ekca.UpdateState;
import com.farm.wcp.ekca.UpdateType;
import com.farm.wcp.ekca.OperateEvent;

/**
 * 知识统计分析系统，对外接口
 * 
 *
 */
public interface EkcaAppInter extends Remote {
	/**
	 * 注册用户（没有就新建，有就更新）
	 * 
	 * @param oid
	 * @param name
	 * @param type
	 * @param loginname
	 * @param imgid
	 * @param updateState
	 * @param updateType
	 */
	public void updateUser(String oid, String name, String type, String loginname, String imgid,
			UpdateState updateState, UpdateType updateType, String secret) throws RemoteException;

	/**
	 * 更新用户机构关系
	 * 
	 * @param oid
	 * @param orgid
	 * @param updateState
	 * @param updateType
	 */
	public void updateUserOrgid(String oid, String orgid, UpdateType updateType, String secret) throws RemoteException;

	/**
	 * 更新用户岗位关系
	 * 
	 * @param oid
	 * @param postid
	 * @param updateState
	 * @param updateType
	 */
	public void updateUserPostid(String oid, List<String> postids, UpdateType updateType, String secret)
			throws RemoteException;

	/**
	 * 注册组织机构（没有就新建，有就更新）
	 * 
	 * @param oid
	 * @param treecode
	 * @param name
	 * @param parentid
	 * @param sort
	 * @param updateState
	 * @param updateType
	 */
	public void updateOrg(String oid, String treecode, String name, String parentid, int sort, UpdateState updateState,
			UpdateType updateType, String secret) throws RemoteException;

	/**
	 * 注册岗位（没有就新建，有就更新）
	 * 
	 * @param oid
	 * @param name
	 * @param orgid
	 * @param updateState
	 * @param updateType
	 */
	public void updatePost(String oid, String name, String orgid, UpdateState updateState, UpdateType updateType,
			String secret) throws RemoteException;

	/**
	 * 注册问题（没有就新建，有就更新）
	 * 
	 * @param oid
	 * @param title
	 * @param typeid
	 * @param anonymous
	 * @param updateState
	 * @param updateType
	 */
	public void updateQuestion(String oid, String title, String typeid, String anonymous, UpdateState updateState,
			UpdateType updateType, String secret) throws RemoteException;

	/**
	 * 注册分类（没有就新建，有就更新）
	 * 
	 * @param oid
	 * @param name
	 * @param sort
	 * @param type
	 * @param parentid
	 * @param treecode
	 * @param knowshow
	 * @param fqashow
	 * @param updateState
	 * @param updateType
	 */
	public void updateType(String oid, String name, int sort, String type, String parentid, String treecode,
			String knowshow, String fqashow, UpdateState updateState, UpdateType updateType, String secret)
			throws RemoteException;

	/**
	 * 注册知识（没有就新建，有就更新）
	 * 
	 * @param oid
	 * @param title
	 * @param domtype
	 * @param version
	 * @param typeid
	 * @param updateState
	 * @param updateType
	 * @param secret
	 *            预留权限key
	 * @throws RemoteException
	 */
	public void updateKnow(String oid, String title, String domtype, String version, String typeid,
			UpdateState updateState, UpdateType updateType, String secret) throws RemoteException;

	/**
	 * 记录一个操作
	 * 
	 * @param optype
	 *            操作类型
	 * @param optime
	 *            操作时间
	 * @param userid
	 *            用户id
	 * @param userip
	 *            用户ip
	 * @param knowId
	 *            知识id
	 * @param QuestId
	 *            问题id
	 * @param versionid
	 *            版本id
	 * @param versionname
	 *            版本名称
	 * @param oid
	 *            操作对象（如知识的评论等非知识和问答id）
	 * @param pid
	 *            对象所有人id（如知识的创建者）
	 * @param Stringval
	 *            字符串值
	 * @param intval
	 *            整数值
	 * @param floatVal
	 *            小数值
	 * @param secret
	 *            预留权限key
	 * @throws RemoteException
	 */
	public void recordOperate(OperateEvent operateEvent, String userid, String userip, String knowId, String QuestId,
			String versionid, String oid, String pid, String secret) throws RemoteException;
}
