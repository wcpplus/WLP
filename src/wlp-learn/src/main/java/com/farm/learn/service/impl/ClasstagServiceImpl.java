package com.farm.learn.service.impl;

import com.farm.learn.domain.ClassTag;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.farm.learn.dao.ClasstagDaoInter;
import com.farm.learn.service.ClasstagServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;
/* *
 *功能：课程标签服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class ClasstagServiceImpl implements ClasstagServiceInter{
  @Resource
  private ClasstagDaoInter  classtagDaoImpl;

  private static final Logger log = Logger.getLogger(ClasstagServiceImpl.class);
  @Override
  @Transactional
  public ClassTag insertClasstagEntity(ClassTag entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    //entity.setCuser(user.getId());
    //entity.setCtime(TimeTool.getTimeDate14());
    //entity.setCusername(user.getName());
    //entity.setEuser(user.getId()); 
    //entity.setEusername(user.getName());
    //entity.setEtime(TimeTool.getTimeDate14());
    //entity.setPstate("1");
    return classtagDaoImpl.insertEntity(entity);
  }
  @Override
  @Transactional
  public ClassTag editClasstagEntity(ClassTag entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    ClassTag entity2 = classtagDaoImpl.getEntity(entity.getId());
    //entity2.setEuser(user.getId());
    //entity2.setEusername(user.getName());
    //entity2.setEtime(TimeTool.getTimeDate14()); 
    entity2.setClassid(entity.getClassid());
    entity2.setTagid(entity.getTagid());
    entity2.setId(entity.getId());
    classtagDaoImpl.editEntity(entity2);
    return entity2;
  }
  @Override
  @Transactional
  public void deleteClasstagEntity(String id,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    classtagDaoImpl.deleteEntity(classtagDaoImpl.getEntity(id));
  }
  @Override
  @Transactional
  public ClassTag getClasstagEntity(String id) {
    // TODO 自动生成代码,修改后请去除本注释
    if (id == null){return null;}
    return classtagDaoImpl.getEntity(id);
  }
  @Override
  @Transactional
  public DataQuery createClasstagSimpleQuery(DataQuery query) {
    // TODO 自动生成代码,修改后请去除本注释
    DataQuery dbQuery = DataQuery
        .init(
            query,
            "WLP_L_CLASSTAG",
            "ID,CLASSID,TAGID");
    return dbQuery;
  }

}
