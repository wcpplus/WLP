package com.farm.learn.dao;

import com.farm.learn.domain.Top;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;


/* *
 *功能：推荐阅读数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20150707114057
 *说明：
 */
public interface TopDaoInter  {
 /** 删除一个推荐阅读实体
 * @param entity 实体
 */
 public void deleteEntity(Top top) ;
 /** 由推荐阅读id获得一个推荐阅读实体
 * @param id
 * @return
 */
 public Top getEntity(String topid) ;
 /** 插入一条推荐阅读数据
 * @param entity
 */
 public  Top insertEntity(Top top);
 /** 获得记录数量
 * @return
 */
 public int getAllListNum();
 /**修改一个推荐阅读记录
 * @param entity
 */
 public void editEntity(Top top);
 /**获得一个session
 */
 public Session getSession();
 /**执行一条推荐阅读查询语句
 */
 public DataResult runSqlQuery(DataQuery query);
 /**
 * 条件删除推荐阅读实体，依据对象字段值(一般不建议使用该方法)
 * 
 * @param rules
 *            删除条件
 */
 public void deleteEntitys(List<DBRule> rules);

 /**
 * 条件查询推荐阅读实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
 * 
 * @param rules
 *            查询条件
 * @return
 */
 public List<Top> selectEntitys(List<DBRule> rules);

 /**
 * 条件修改推荐阅读实体，依据对象字段值(一般不建议使用该方法)
 * 
 * @param values
 *            被修改的键值对
 * @param rules
 *            修改条件
 */
 public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
 /**
 * 条件合计推荐阅读:count(*)
 * 
 * @param rules
 *            统计条件
 */
 public int countEntitys(List<DBRule> rules);
}