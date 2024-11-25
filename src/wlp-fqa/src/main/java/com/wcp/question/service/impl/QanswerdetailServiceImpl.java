package com.wcp.question.service.impl;

import com.wcp.question.domain.Qanswerdetail;
import org.apache.log4j.Logger;
import com.wcp.question.dao.QanswerdetailDaoInter;
import com.wcp.question.service.QanswerdetailServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;
/* *
 *功能：回答用量明细服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class QanswerdetailServiceImpl implements QanswerdetailServiceInter{
  @Resource
  private QanswerdetailDaoInter  qanswerdetailDaoImpl;

  @SuppressWarnings("unused")
private static final Logger log = Logger.getLogger(QanswerdetailServiceImpl.class);
  @Override
  @Transactional
  public Qanswerdetail insertQanswerdetailEntity(Qanswerdetail entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    //entity.setCuser(user.getId());
    //entity.setCtime(TimeTool.getTimeDate14());
    //entity.setCusername(user.getName());
    //entity.setEuser(user.getId()); 
    //entity.setEusername(user.getName());
    //entity.setEtime(TimeTool.getTimeDate14());
    //entity.setPstate("1");
    return qanswerdetailDaoImpl.insertEntity(entity);
  }
  @Override
  @Transactional
  public Qanswerdetail editQanswerdetailEntity(Qanswerdetail entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    Qanswerdetail entity2 = qanswerdetailDaoImpl.getEntity(entity.getId());
    //entity2.setEuser(user.getId());
    //entity2.setEusername(user.getName());
    //entity2.setEtime(TimeTool.getTimeDate14()); 
    entity2.setAnswerid(entity.getAnswerid());
    entity2.setVtype(entity.getVtype());
    entity2.setUserip(entity.getUserip());
    entity2.setPstate(entity.getPstate());
    entity2.setCuser(entity.getCuser());
    entity2.setCusername(entity.getCusername());
    entity2.setCtime(entity.getCtime());
    entity2.setId(entity.getId());
    qanswerdetailDaoImpl.editEntity(entity2);
    return entity2;
  }
  @Override
  @Transactional
  public void deleteQanswerdetailEntity(String id,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    qanswerdetailDaoImpl.deleteEntity(qanswerdetailDaoImpl.getEntity(id));
  }
  @Override
  @Transactional
  public Qanswerdetail getQanswerdetailEntity(String id) {
    // TODO 自动生成代码,修改后请去除本注释
    if (id == null){return null;}
    return qanswerdetailDaoImpl.getEntity(id);
  }
  @Override
  @Transactional
  public DataQuery createQanswerdetailSimpleQuery(DataQuery query) {
    // TODO 自动生成代码,修改后请去除本注释
    DataQuery dbQuery = DataQuery
        .init(
            query,
            "WCP_QANSWER_DETAIL",
            "ID,ANSWERID,VTYPE,USERIP,PSTATE,CUSER,CUSERNAME,CTIME");
    return dbQuery;
  }
  //----------------------------------------------------------------------------------
  public QanswerdetailDaoInter getQanswerdetailDaoImpl() {
    return qanswerdetailDaoImpl;
  }

  public void setQanswerdetailDaoImpl(QanswerdetailDaoInter dao) {
    this.qanswerdetailDaoImpl= dao;
  }

}
