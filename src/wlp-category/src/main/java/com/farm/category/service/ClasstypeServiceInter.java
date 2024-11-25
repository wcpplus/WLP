package com.farm.category.service;

import java.util.List;

import com.farm.category.domain.ClassType;
import com.farm.category.enums.FuncPOP;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：课程分类服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface ClasstypeServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public ClassType insertClasstypeEntity(ClassType entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public ClassType editClasstypeEntity(ClassType entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteClasstypeEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public ClassType getClasstypeEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createClasstypeSimpleQuery(DataQuery query);

	public void moveTreeNode(String ids, String targetId, LoginUser currentUser);

	/**
	 * 获得所有分类
	 * 
	 * @return
	 */
	public List<ClassType> getAllTypes();

	/**
	 * 获得分类路径上全部分类
	 * 
	 * @param type
	 * @return
	 */
	public List<ClassType> getAllPathType(ClassType type);

	/**
	 * 获得当前分类和所有子分类的id集合
	 * 
	 * @param classtype
	 * @return
	 */
	public List<String> getSubTypeids(String classtypeId);

	/**
	 * 获得当前分类和所有子分类集合
	 * 
	 * @param rootTypeid
	 * @param isAll true包含全部，false只含可用分类
	 * @return
	 */
	public List<ClassType> getSubTypes(String rootTypeid,boolean isAll);

	/**
	 * 刷新分类下的内容（课程）数量
	 * 
	 * @param typeid
	 */
	public void refreshNum(String typeid);

	/**
	 * 获得用户拥有某种分类权限的分类id(不含公开分类)
	 * 
	 * @param user
	 * @param pop_type
	 * @return
	 */
	public List<String> getPopTypeIds(LoginUser user, FuncPOP funcPOP);

	/**
	 * 获得种分类权限的公开分类id(公开分类)
	 * 
	 * @param user
	 * @param funcPOP
	 * @return
	 */
	public List<String> getPubTypeIds(FuncPOP funcPOP);

	/**
	 * 用户拥有权限的所有分类id集合（含授权的和公开的）
	 * 
	 * @param user
	 * @param funcPOP
	 * @return
	 */
	public List<String> getUserAllTypeIds(LoginUser user, FuncPOP funcPOP);

	/**
	 * 获得权限允许的全部分类(带权限控制)
	 * 
	 * @param currentUser
	 * @return
	 */
	public List<ClassType> getAllTypesByPop(LoginUser currentUser,FuncPOP funcPOP);

	/**获得全部子分类(带权限控制)
	 * @param rootTypeid
	 * @param currentUser
	 * @param enum1
	 * @return
	 */
	public List<ClassType> getSubTypesByPop(String rootTypeid, LoginUser currentUser, FuncPOP enum1);
}