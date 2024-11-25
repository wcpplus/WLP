package com.farm.learn.service;

import com.farm.learn.domain.ClassTag;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;
/* *
 *功能：课程标签服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface ClasstagServiceInter{
  /**
   *新增实体管理实体
   * 
   * @param entity
   */
  public ClassTag insertClasstagEntity(ClassTag entity,LoginUser user);
  /**
   *修改实体管理实体
   * 
   * @param entity
   */
  public ClassTag editClasstagEntity(ClassTag entity,LoginUser user);
  /**
   *删除实体管理实体
   * 
   * @param entity
   */
  public void deleteClasstagEntity(String id,LoginUser user);
  /**
   *获得实体管理实体
   * 
   * @param id
   * @return
   */
  public ClassTag getClasstagEntity(String id);
  /**
   * 创建一个基本查询用来查询当前实体管理实体
   * 
   * @param query
   *            传入的查询条件封装
   * @return
   */
  public DataQuery createClasstagSimpleQuery(DataQuery query);
}