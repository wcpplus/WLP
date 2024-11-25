package com.farm.learn.dao;

import com.farm.learn.domain.ClassHour;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

/* *
 *功能：课程课时数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20150707114057
 *说明：
 */
public interface ClasshourDaoInter {
	/**
	 * 删除一个课程课时实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(ClassHour classhour);

	/**
	 * 由课程课时id获得一个课程课时实体
	 * 
	 * @param id
	 * @return
	 */
	public ClassHour getEntity(String classhourid);

	/**
	 * 插入一条课程课时数据
	 * 
	 * @param entity
	 */
	public ClassHour insertEntity(ClassHour classhour);

	/**
	 * 获得记录数量
	 * 
	 * @return
	 */
	public int getAllListNum();

	/**
	 * 修改一个课程课时记录
	 * 
	 * @param entity
	 */
	public void editEntity(ClassHour classhour);

	/**
	 * 获得一个session
	 */
	public Session getSession();

	/**
	 * 执行一条课程课时查询语句
	 */
	public DataResult runSqlQuery(DataQuery query);

	/**
	 * 条件删除课程课时实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            删除条件
	 */
	public void deleteEntitys(List<DBRule> rules);

	/**
	 * 条件查询课程课时实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            查询条件
	 * @return
	 */
	public List<ClassHour> selectEntitys(List<DBRule> rules);

	/**
	 * 条件修改课程课时实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param values
	 *            被修改的键值对
	 * @param rules
	 *            修改条件
	 */
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	/**
	 * 条件合计课程课时:count(*)
	 * 
	 * @param rules
	 *            统计条件
	 */
	public int countEntitys(List<DBRule> rules);

	/**
	 * 总的课时数量
	 * 
	 * @param classid
	 * @return
	 */
	public int getAllHoursNum(String classid);
}