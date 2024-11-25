package com.farm.learn.service.impl;

import com.farm.learn.domain.ClassClassType;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.farm.learn.dao.ClassclasstypeDaoInter;
import com.farm.learn.service.ClassclasstypeServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;
/* *
 *功能：课程分类服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class ClassclasstypeServiceImpl implements ClassclasstypeServiceInter{
  @Resource
  private ClassclasstypeDaoInter  classclasstypeDaoImpl;

  private static final Logger log = Logger.getLogger(ClassclasstypeServiceImpl.class);
  @Override
  @Transactional
  public ClassClassType insertClassclasstypeEntity(ClassClassType entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    //entity.setCuser(user.getId());
    //entity.setCtime(TimeTool.getTimeDate14());
    //entity.setCusername(user.getName());
    //entity.setEuser(user.getId()); 
    //entity.setEusername(user.getName());
    //entity.setEtime(TimeTool.getTimeDate14());
    //entity.setPstate("1");
    return classclasstypeDaoImpl.insertEntity(entity);
  }
  @Override
  @Transactional
  public ClassClassType editClassclasstypeEntity(ClassClassType entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    ClassClassType entity2 = classclasstypeDaoImpl.getEntity(entity.getId());
    //entity2.setEuser(user.getId());
    //entity2.setEusername(user.getName());
    //entity2.setEtime(TimeTool.getTimeDate14()); 
    entity2.setClassid(entity.getClassid());
    entity2.setClasstypeid(entity.getClasstypeid());
    entity2.setId(entity.getId());
    classclasstypeDaoImpl.editEntity(entity2);
    return entity2;
  }
  @Override
  @Transactional
  public void deleteClassclasstypeEntity(String id,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    classclasstypeDaoImpl.deleteEntity(classclasstypeDaoImpl.getEntity(id));
  }
  @Override
  @Transactional
  public ClassClassType getClassclasstypeEntity(String id) {
    // TODO 自动生成代码,修改后请去除本注释
    if (id == null){return null;}
    return classclasstypeDaoImpl.getEntity(id);
  }
  @Override
  @Transactional
  public DataQuery createClassclasstypeSimpleQuery(DataQuery query) {
    // TODO 自动生成代码,修改后请去除本注释
    DataQuery dbQuery = DataQuery
        .init(
            query,
            "WLP_L_CLASSCLASSTYPE",
            "ID,CLASSID,CLASSTYPEID");
    return dbQuery;
  }

}
