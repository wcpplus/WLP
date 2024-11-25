package com.farm.social.service.impl;

import com.farm.social.domain.EvaluationUser;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.farm.social.dao.EvaluationuserDaoInter;
import com.farm.social.service.EvaluationuserServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;
/* *
 *功能：用户评价服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class EvaluationuserServiceImpl implements EvaluationuserServiceInter{
  @Resource
  private EvaluationuserDaoInter  evaluationuserDaoImpl;

  private static final Logger log = Logger.getLogger(EvaluationuserServiceImpl.class);
  @Override
  @Transactional
  public EvaluationUser insertEvaluationuserEntity(EvaluationUser entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    //entity.setCuser(user.getId());
    //entity.setCtime(TimeTool.getTimeDate14());
    //entity.setCusername(user.getName());
    //entity.setEuser(user.getId()); 
    //entity.setEusername(user.getName());
    //entity.setEtime(TimeTool.getTimeDate14());
    //entity.setPstate("1");
    return evaluationuserDaoImpl.insertEntity(entity);
  }
  @Override
  @Transactional
  public EvaluationUser editEvaluationuserEntity(EvaluationUser entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    EvaluationUser entity2 = evaluationuserDaoImpl.getEntity(entity.getId());
    //entity2.setEuser(user.getId());
    //entity2.setEusername(user.getName());
    //entity2.setEtime(TimeTool.getTimeDate14()); 
    entity2.setObjid(entity.getObjid());
    entity2.setScore(entity.getScore());
    entity2.setNote(entity.getNote());
    entity2.setCtime(entity.getCtime());
    entity2.setUserid(entity.getUserid());
    entity2.setId(entity.getId());
    evaluationuserDaoImpl.editEntity(entity2);
    return entity2;
  }
  @Override
  @Transactional
  public void deleteEvaluationuserEntity(String id,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    evaluationuserDaoImpl.deleteEntity(evaluationuserDaoImpl.getEntity(id));
  }
  @Override
  @Transactional
  public EvaluationUser getEvaluationuserEntity(String id) {
    // TODO 自动生成代码,修改后请去除本注释
    if (id == null){return null;}
    return evaluationuserDaoImpl.getEntity(id);
  }
  @Override
  @Transactional
  public DataQuery createEvaluationuserSimpleQuery(DataQuery query) {
    // TODO 自动生成代码,修改后请去除本注释
    DataQuery dbQuery = DataQuery
        .init(
            query,
            "WLP_S_EVALUATION_USER",
            "ID,OBJID,SCORE,NOTE,CTIME,USERID");
    return dbQuery;
  }

}
