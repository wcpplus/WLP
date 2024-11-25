package com.farm.social.service.impl;

import com.farm.social.domain.AppbindResource;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.farm.social.dao.AppbindresourceDaoInter;
import com.farm.social.service.AppbindresourceServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;
/* *
 *功能：应用绑定资源服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class AppbindresourceServiceImpl implements AppbindresourceServiceInter{
  @Resource
  private AppbindresourceDaoInter  appbindresourceDaoImpl;

  private static final Logger log = Logger.getLogger(AppbindresourceServiceImpl.class);
  @Override
  @Transactional
  public AppbindResource insertAppbindresourceEntity(AppbindResource entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    //entity.setCuser(user.getId());
    //entity.setCtime(TimeTool.getTimeDate14());
    //entity.setCusername(user.getName());
    //entity.setEuser(user.getId()); 
    //entity.setEusername(user.getName());
    //entity.setEtime(TimeTool.getTimeDate14());
    //entity.setPstate("1");
    return appbindresourceDaoImpl.insertEntity(entity);
  }
  @Override
  @Transactional
  public AppbindResource editAppbindresourceEntity(AppbindResource entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    AppbindResource entity2 = appbindresourceDaoImpl.getEntity(entity.getId());
    //entity2.setEuser(user.getId());
    //entity2.setEusername(user.getName());
    //entity2.setEtime(TimeTool.getTimeDate14()); 
    entity2.setObjid(entity.getObjid());
    entity2.setSysid(entity.getSysid());
    entity2.setResourcetype(entity.getResourcetype());
    entity2.setResourceurl(entity.getResourceurl());
    entity2.setCtime(entity.getCtime());
    entity2.setUserid(entity.getUserid());
    entity2.setId(entity.getId());
    appbindresourceDaoImpl.editEntity(entity2);
    return entity2;
  }
  @Override
  @Transactional
  public void deleteAppbindresourceEntity(String id,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    appbindresourceDaoImpl.deleteEntity(appbindresourceDaoImpl.getEntity(id));
  }
  @Override
  @Transactional
  public AppbindResource getAppbindresourceEntity(String id) {
    // TODO 自动生成代码,修改后请去除本注释
    if (id == null){return null;}
    return appbindresourceDaoImpl.getEntity(id);
  }
  @Override
  @Transactional
  public DataQuery createAppbindresourceSimpleQuery(DataQuery query) {
    // TODO 自动生成代码,修改后请去除本注释
    DataQuery dbQuery = DataQuery
        .init(
            query,
            "WLP_S_APPBIND_RESOURCE",
            "ID,OBJID,SYSID,RESOURCETYPE,RESOURCEURL,CTIME,USERID");
    return dbQuery;
  }

}
