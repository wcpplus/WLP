package com.wcp.question.dao;

import com.wcp.question.domain.Qanswercomment;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;


/* *
 *功能：回答评论数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20150707114057
 *说明：
 */
public interface QanswercommentDaoInter  {
 /** 删除一个回答评论实体
 * @param entity 实体
 */
 public void deleteEntity(Qanswercomment qanswercomment) ;
 /** 由回答评论id获得一个回答评论实体
 * @param id
 * @return
 */
 public Qanswercomment getEntity(String qanswercommentid) ;
 /** 插入一条回答评论数据
 * @param entity
 */
 public  Qanswercomment insertEntity(Qanswercomment qanswercomment);
 /** 获得记录数量
 * @return
 */
 public int getAllListNum();
 /**修改一个回答评论记录
 * @param entity
 */
 public void editEntity(Qanswercomment qanswercomment);
 /**获得一个session
 */
 public Session getSession();
 /**执行一条回答评论查询语句
 */
 public DataResult runSqlQuery(DataQuery query);
 /**
 * 条件删除回答评论实体，依据对象字段值(一般不建议使用该方法)
 * 
 * @param rules
 *            删除条件
 */
 public void deleteEntitys(List<DBRule> rules);

 /**
 * 条件查询回答评论实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
 * 
 * @param rules
 *            查询条件
 * @return
 */
 public List<Qanswercomment> selectEntitys(List<DBRule> rules);

 /**
 * 条件修改回答评论实体，依据对象字段值(一般不建议使用该方法)
 * 
 * @param values
 *            被修改的键值对
 * @param rules
 *            修改条件
 */
 public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
 /**
 * 条件合计回答评论:count(*)
 * 
 * @param rules
 *            统计条件
 */
 public int countEntitys(List<DBRule> rules);
}