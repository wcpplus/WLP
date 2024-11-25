package com.farm.learn.service.impl;

import com.farm.learn.domain.UserHour;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.farm.learn.dao.UserhourDaoInter;
import com.farm.learn.service.UserhourServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;
/* *
 *功能：用户课时服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class UserhourServiceImpl implements UserhourServiceInter{
  @Resource
  private UserhourDaoInter  userhourDaoImpl;

  private static final Logger log = Logger.getLogger(UserhourServiceImpl.class);
  @Override
  @Transactional
  public UserHour insertUserhourEntity(UserHour entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    //entity.setCuser(user.getId());
    //entity.setCtime(TimeTool.getTimeDate14());
    //entity.setCusername(user.getName());
    //entity.setEuser(user.getId()); 
    //entity.setEusername(user.getName());
    //entity.setEtime(TimeTool.getTimeDate14());
    //entity.setPstate("1");
    return userhourDaoImpl.insertEntity(entity);
  }
  @Override
  @Transactional
  public UserHour editUserhourEntity(UserHour entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    UserHour entity2 = userhourDaoImpl.getEntity(entity.getId());
    //entity2.setEuser(user.getId());
    //entity2.setEusername(user.getName());
    //entity2.setEtime(TimeTool.getTimeDate14()); 
    entity2.setEtime(entity.getEtime());
    entity2.setStime(entity.getStime());
    entity2.setClassid(entity.getClassid());
    entity2.setUserid(entity.getUserid());
    entity2.setPstate(entity.getPstate());
    entity2.setHourid(entity.getHourid());
    entity2.setId(entity.getId());
    userhourDaoImpl.editEntity(entity2);
    return entity2;
  }
  @Override
  @Transactional
  public void deleteUserhourEntity(String id,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    userhourDaoImpl.deleteEntity(userhourDaoImpl.getEntity(id));
  }
  @Override
  @Transactional
  public UserHour getUserhourEntity(String id) {
    // TODO 自动生成代码,修改后请去除本注释
    if (id == null){return null;}
    return userhourDaoImpl.getEntity(id);
  }
  @Override
  @Transactional
  public DataQuery createUserhourSimpleQuery(DataQuery query) {
    // TODO 自动生成代码,修改后请去除本注释
    DataQuery dbQuery = DataQuery
        .init(
            query,
            "WLP_U_USERHOUR",
            "ID,ETIME,STIME,CLASSID,USERID,PSTATE,HOURID");
    return dbQuery;
  }

}
