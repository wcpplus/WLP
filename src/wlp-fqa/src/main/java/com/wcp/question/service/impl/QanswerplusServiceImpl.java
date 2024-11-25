package com.wcp.question.service.impl;

import com.wcp.question.domain.Qanswerplus;
import org.apache.log4j.Logger;
import com.wcp.question.dao.QanswerplusDaoInter;
import com.wcp.question.service.QanswerplusServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;
/* *
 *功能：追加回答服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class QanswerplusServiceImpl implements QanswerplusServiceInter{
  @Resource
  private QanswerplusDaoInter  qanswerplusDaoImpl;

  @SuppressWarnings("unused")
private static final Logger log = Logger.getLogger(QanswerplusServiceImpl.class);
  @Override
  @Transactional
  public Qanswerplus insertQanswerplusEntity(Qanswerplus entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    //entity.setCuser(user.getId());
    //entity.setCtime(TimeTool.getTimeDate14());
    //entity.setCusername(user.getName());
    //entity.setEuser(user.getId()); 
    //entity.setEusername(user.getName());
    //entity.setEtime(TimeTool.getTimeDate14());
    //entity.setPstate("1");
    return qanswerplusDaoImpl.insertEntity(entity);
  }
  @Override
  @Transactional
  public Qanswerplus editQanswerplusEntity(Qanswerplus entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    Qanswerplus entity2 = qanswerplusDaoImpl.getEntity(entity.getId());
    //entity2.setEuser(user.getId());
    //entity2.setEusername(user.getName());
    //entity2.setEtime(TimeTool.getTimeDate14()); 
    entity2.setCtime(entity.getCtime());
    entity2.setDescription(entity.getDescription());
    entity2.setAnswerid(entity.getAnswerid());
    entity2.setId(entity.getId());
    qanswerplusDaoImpl.editEntity(entity2);
    return entity2;
  }
  @Override
  @Transactional
  public void deleteQanswerplusEntity(String id,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    qanswerplusDaoImpl.deleteEntity(qanswerplusDaoImpl.getEntity(id));
  }
  @Override
  @Transactional
  public Qanswerplus getQanswerplusEntity(String id) {
    // TODO 自动生成代码,修改后请去除本注释
    if (id == null){return null;}
    return qanswerplusDaoImpl.getEntity(id);
  }
  @Override
  @Transactional
  public DataQuery createQanswerplusSimpleQuery(DataQuery query) {
    // TODO 自动生成代码,修改后请去除本注释
    DataQuery dbQuery = DataQuery
        .init(
            query,
            "WCP_QANSWER_PLUS",
            "ID,CTIME,DESCRIPTION,ANSWERID");
    return dbQuery;
  }
  //----------------------------------------------------------------------------------
  public QanswerplusDaoInter getQanswerplusDaoImpl() {
    return qanswerplusDaoImpl;
  }

  public void setQanswerplusDaoImpl(QanswerplusDaoInter dao) {
    this.qanswerplusDaoImpl= dao;
  }

}
