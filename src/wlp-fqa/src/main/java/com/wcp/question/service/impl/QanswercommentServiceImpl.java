package com.wcp.question.service.impl;

import com.wcp.question.domain.Qanswercomment;
import org.apache.log4j.Logger;
import com.wcp.question.dao.QanswercommentDaoInter;
import com.wcp.question.service.QanswercommentServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;
/* *
 *功能：回答评论服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class QanswercommentServiceImpl implements QanswercommentServiceInter{
  @Resource
  private QanswercommentDaoInter  qanswercommentDaoImpl;

  @SuppressWarnings("unused")
private static final Logger log = Logger.getLogger(QanswercommentServiceImpl.class);
  @Override
  @Transactional
  public Qanswercomment insertQanswercommentEntity(Qanswercomment entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    //entity.setCuser(user.getId());
    //entity.setCtime(TimeTool.getTimeDate14());
    //entity.setCusername(user.getName());
    //entity.setEuser(user.getId()); 
    //entity.setEusername(user.getName());
    //entity.setEtime(TimeTool.getTimeDate14());
    //entity.setPstate("1");
    return qanswercommentDaoImpl.insertEntity(entity);
  }
  @Override
  @Transactional
  public Qanswercomment editQanswercommentEntity(Qanswercomment entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    Qanswercomment entity2 = qanswercommentDaoImpl.getEntity(entity.getId());
    //entity2.setEuser(user.getId());
    //entity2.setEusername(user.getName());
    //entity2.setEtime(TimeTool.getTimeDate14()); 
    entity2.setDescription(entity.getDescription());
    entity2.setAnswerid(entity.getAnswerid());
    entity2.setPstate(entity.getPstate());
    entity2.setCuser(entity.getCuser());
    entity2.setCusername(entity.getCusername());
    entity2.setCtime(entity.getCtime());
    entity2.setId(entity.getId());
    qanswercommentDaoImpl.editEntity(entity2);
    return entity2;
  }
  @Override
  @Transactional
  public void deleteQanswercommentEntity(String id,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    qanswercommentDaoImpl.deleteEntity(qanswercommentDaoImpl.getEntity(id));
  }
  @Override
  @Transactional
  public Qanswercomment getQanswercommentEntity(String id) {
    // TODO 自动生成代码,修改后请去除本注释
    if (id == null){return null;}
    return qanswercommentDaoImpl.getEntity(id);
  }
  @Override
  @Transactional
  public DataQuery createQanswercommentSimpleQuery(DataQuery query) {
    // TODO 自动生成代码,修改后请去除本注释
    DataQuery dbQuery = DataQuery
        .init(
            query,
            "WCP_QANSWER_COMMENT",
            "ID,DESCRIPTION,ANSWERID,PSTATE,CUSER,CUSERNAME,CTIME");
    return dbQuery;
  }
  //----------------------------------------------------------------------------------
  public QanswercommentDaoInter getQanswercommentDaoImpl() {
    return qanswercommentDaoImpl;
  }

  public void setQanswercommentDaoImpl(QanswercommentDaoInter dao) {
    this.qanswercommentDaoImpl= dao;
  }

}
