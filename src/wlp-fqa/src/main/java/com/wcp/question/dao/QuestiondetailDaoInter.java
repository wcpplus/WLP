package com.wcp.question.dao;

import com.wcp.question.domain.Questiondetail;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;


/* *
 *功能：问题用量明细数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20150707114057
 *说明：
 */
public interface QuestiondetailDaoInter  {
 /** 删除一个问题用量明细实体
 * @param entity 实体
 */
 public void deleteEntity(Questiondetail questiondetail) ;
 /** 由问题用量明细id获得一个问题用量明细实体
 * @param id
 * @return
 */
 public Questiondetail getEntity(String questiondetailid) ;
 /** 插入一条问题用量明细数据
 * @param entity
 */
 public  Questiondetail insertEntity(Questiondetail questiondetail);
 /** 获得记录数量
 * @return
 */
 public int getAllListNum();
 /**修改一个问题用量明细记录
 * @param entity
 */
 public void editEntity(Questiondetail questiondetail);
 /**获得一个session
 */
 public Session getSession();
 /**执行一条问题用量明细查询语句
 */
 public DataResult runSqlQuery(DataQuery query);
 /**
 * 条件删除问题用量明细实体，依据对象字段值(一般不建议使用该方法)
 * 
 * @param rules
 *            删除条件
 */
 public void deleteEntitys(List<DBRule> rules);

 /**
 * 条件查询问题用量明细实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
 * 
 * @param rules
 *            查询条件
 * @return
 */
 public List<Questiondetail> selectEntitys(List<DBRule> rules);

 /**
 * 条件修改问题用量明细实体，依据对象字段值(一般不建议使用该方法)
 * 
 * @param values
 *            被修改的键值对
 * @param rules
 *            修改条件
 */
 public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
 /**
 * 条件合计问题用量明细:count(*)
 * 
 * @param rules
 *            统计条件
 */
 public int countEntitys(List<DBRule> rules);
}