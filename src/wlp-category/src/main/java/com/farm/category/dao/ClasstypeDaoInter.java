package com.farm.category.dao;

import com.farm.category.domain.ClassType;
import com.farm.category.enums.FuncPOP;

import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

/* *
 *功能：课程分类数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20150707114057
 *说明：
 */
public interface ClasstypeDaoInter {
	/**
	 * 删除一个课程分类实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(ClassType classtype);

	/**
	 * 由课程分类id获得一个课程分类实体
	 * 
	 * @param id
	 * @return
	 */
	public ClassType getEntity(String classtypeid);

	/**
	 * 插入一条课程分类数据
	 * 
	 * @param entity
	 */
	public ClassType insertEntity(ClassType classtype);

	/**
	 * 获得记录数量
	 * 
	 * @return
	 */
	public int getAllListNum();

	/**
	 * 修改一个课程分类记录
	 * 
	 * @param entity
	 */
	public void editEntity(ClassType classtype);

	/**
	 * 获得一个session
	 */
	public Session getSession();

	/**
	 * 执行一条课程分类查询语句
	 */
	public DataResult runSqlQuery(DataQuery query);

	/**
	 * 条件删除课程分类实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            删除条件
	 */
	public void deleteEntitys(List<DBRule> rules);

	/**
	 * 条件查询课程分类实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            查询条件
	 * @return
	 */
	public List<ClassType> selectEntitys(List<DBRule> rules);

	/**
	 * 条件修改课程分类实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param values
	 *            被修改的键值对
	 * @param rules
	 *            修改条件
	 */
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	/**
	 * 条件合计课程分类:count(*)
	 * 
	 * @param rules
	 *            统计条件
	 */
	public int countEntitys(List<DBRule> rules);

	public List<ClassType> getAllSubNodes(String typeid);

	/**
	 * 獲得分類下课程数量
	 * 
	 * @param typeid
	 * @return
	 */
	public int getTypeClassNum(String typeid);

	/**
	 * 上层分类中是否有设置过权限（所有上层，不含本级）
	 * 
	 * @param typeid
	 * @return
	 */
	public boolean isParentPopExist(String typeid, String functype);

	/**
	 * 获得公开阅读的分类
	 * 
	 * @param funcPOP
	 * @return
	 */
	public List<String> getPubReadTypeIds();

	/**
	 * 获得公开編輯的分类
	 * 
	 * @param funcPOP
	 * @return
	 */
	public List<String> getPubWriteTypeIds();

	/**
	 * 刪除分类的关联表
	 * 
	 * @param typeid
	 */
	public void deleteRfTable(String typeid);

}