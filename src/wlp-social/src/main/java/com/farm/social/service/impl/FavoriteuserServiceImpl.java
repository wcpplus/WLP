package com.farm.social.service.impl;

import com.farm.social.domain.FavoriteUser;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.farm.social.dao.FavoriteuserDaoInter;
import com.farm.social.service.FavoriteuserServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;
/* *
 *功能：用户收藏服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class FavoriteuserServiceImpl implements FavoriteuserServiceInter{
  @Resource
  private FavoriteuserDaoInter  favoriteuserDaoImpl;

  private static final Logger log = Logger.getLogger(FavoriteuserServiceImpl.class);
  @Override
  @Transactional
  public FavoriteUser insertFavoriteuserEntity(FavoriteUser entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    //entity.setCuser(user.getId());
    //entity.setCtime(TimeTool.getTimeDate14());
    //entity.setCusername(user.getName());
    //entity.setEuser(user.getId()); 
    //entity.setEusername(user.getName());
    //entity.setEtime(TimeTool.getTimeDate14());
    //entity.setPstate("1");
    return favoriteuserDaoImpl.insertEntity(entity);
  }
  @Override
  @Transactional
  public FavoriteUser editFavoriteuserEntity(FavoriteUser entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    FavoriteUser entity2 = favoriteuserDaoImpl.getEntity(entity.getId());
    //entity2.setEuser(user.getId());
    //entity2.setEusername(user.getName());
    //entity2.setEtime(TimeTool.getTimeDate14()); 
    entity2.setObjid(entity.getObjid());
    entity2.setCtime(entity.getCtime());
    entity2.setDotype(entity.getDotype());
    entity2.setUserid(entity.getUserid());
    entity2.setId(entity.getId());
    favoriteuserDaoImpl.editEntity(entity2);
    return entity2;
  }
  @Override
  @Transactional
  public void deleteFavoriteuserEntity(String id,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    favoriteuserDaoImpl.deleteEntity(favoriteuserDaoImpl.getEntity(id));
  }
  @Override
  @Transactional
  public FavoriteUser getFavoriteuserEntity(String id) {
    // TODO 自动生成代码,修改后请去除本注释
    if (id == null){return null;}
    return favoriteuserDaoImpl.getEntity(id);
  }
  @Override
  @Transactional
  public DataQuery createFavoriteuserSimpleQuery(DataQuery query) {
    // TODO 自动生成代码,修改后请去除本注释
    DataQuery dbQuery = DataQuery
        .init(
            query,
            "WLP_S_FAVORITE_USER",
            "ID,OBJID,CTIME,DOTYPE,USERID");
    return dbQuery;
  }

}
