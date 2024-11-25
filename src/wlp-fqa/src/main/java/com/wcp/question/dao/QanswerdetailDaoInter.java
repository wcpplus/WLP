package com.wcp.question.dao;

import com.wcp.question.domain.Qanswerdetail;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;


/* *
 *功能：回答用量明细数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20150707114057
 *说明：
 */
public interface QanswerdetailDaoInter  {
 /** 删除一个回答用量明细实体
 * @param entity 实体
 */
 public void deleteEntity(Qanswerdetail qanswerdetail) ;
 /** 由回答用量明细id获得一个回答用量明细实体
 * @param id
 * @return
 */
 public Qanswerdetail getEntity(String qanswerdetailid) ;
 /** 插入一条回答用量明细数据
 * @param entity
 */
 public  Qanswerdetail insertEntity(Qanswerdetail qanswerdetail);
 /** 获得记录数量
 * @return
 */
 public int getAllListNum();
 /**修改一个回答用量明细记录
 * @param entity
 */
 public void editEntity(Qanswerdetail qanswerdetail);
 /**获得一个session
 */
 public Session getSession();
 /**执行一条回答用量明细查询语句
 */
 public DataResult runSqlQuery(DataQuery query);
 /**
 * 条件删除回答用量明细实体，依据对象字段值(一般不建议使用该方法)
 * 
 * @param rules
 *            删除条件
 */
 public void deleteEntitys(List<DBRule> rules);

 /**
 * 条件查询回答用量明细实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
 * 
 * @param rules
 *            查询条件
 * @return
 */
 public List<Qanswerdetail> selectEntitys(List<DBRule> rules);

 /**
 * 条件修改回答用量明细实体，依据对象字段值(一般不建议使用该方法)
 * 
 * @param values
 *            被修改的键值对
 * @param rules
 *            修改条件
 */
 public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
 /**
 * 条件合计回答用量明细:count(*)
 * 
 * @param rules
 *            统计条件
 */
 public int countEntitys(List<DBRule> rules);
}