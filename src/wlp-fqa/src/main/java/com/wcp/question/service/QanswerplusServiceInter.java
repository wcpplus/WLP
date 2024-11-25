package com.wcp.question.service;

import com.wcp.question.domain.Qanswerplus;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;
/* *
 *功能：追加回答服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface QanswerplusServiceInter{
  /**
   *新增实体管理实体
   * 
   * @param entity
   */
  public Qanswerplus insertQanswerplusEntity(Qanswerplus entity,LoginUser user);
  /**
   *修改实体管理实体
   * 
   * @param entity
   */
  public Qanswerplus editQanswerplusEntity(Qanswerplus entity,LoginUser user);
  /**
   *删除实体管理实体
   * 
   * @param entity
   */
  public void deleteQanswerplusEntity(String id,LoginUser user);
  /**
   *获得实体管理实体
   * 
   * @param id
   * @return
   */
  public Qanswerplus getQanswerplusEntity(String id);
  /**
   * 创建一个基本查询用来查询当前实体管理实体
   * 
   * @param query
   *            传入的查询条件封装
   * @return
   */
  public DataQuery createQanswerplusSimpleQuery(DataQuery query);
}