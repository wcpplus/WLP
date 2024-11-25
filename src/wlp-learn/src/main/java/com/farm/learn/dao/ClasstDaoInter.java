package com.farm.learn.dao;

import com.farm.learn.domain.Classt;
import org.hibernate.Session;

import com.farm.category.domain.Tag;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

/* *
 *功能：课程数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20150707114057
 *说明：
 */
public interface ClasstDaoInter {
	/**
	 * 删除一个课程实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(Classt classt);

	/**
	 * 由课程id获得一个课程实体
	 * 
	 * @param id
	 * @return
	 */
	public Classt getEntity(String classtid);

	/**
	 * 插入一条课程数据
	 * 
	 * @param entity
	 */
	public Classt insertEntity(Classt classt);

	/**
	 * 获得记录数量
	 * 
	 * @return
	 */
	public int getAllListNum();

	/**
	 * 修改一个课程记录
	 * 
	 * @param entity
	 */
	public void editEntity(Classt classt);

	/**
	 * 获得一个session
	 */
	public Session getSession();

	/**
	 * 执行一条课程查询语句
	 */
	public DataResult runSqlQuery(DataQuery query);

	/**
	 * 条件删除课程实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            删除条件
	 */
	public void deleteEntitys(List<DBRule> rules);

	/**
	 * 条件查询课程实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            查询条件
	 * @return
	 */
	public List<Classt> selectEntitys(List<DBRule> rules);

	/**
	 * 条件修改课程实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param values
	 *            被修改的键值对
	 * @param rules
	 *            修改条件
	 */
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	/**
	 * 条件合计课程:count(*)
	 * 
	 * @param rules
	 *            统计条件
	 */
	public int countEntitys(List<DBRule> rules);

	/**
	 * 获得课程标签
	 * 
	 * @param classid
	 * @return
	 */
	public List<Tag> getClassTags(String classid);
}