package com.farm.social.service.impl;

import com.farm.social.domain.CommentsUser;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.farm.social.dao.CommentsuserDaoInter;
import com.farm.social.service.CommentsuserServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;
/* *
 *功能：用户评论服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class CommentsuserServiceImpl implements CommentsuserServiceInter{
  @Resource
  private CommentsuserDaoInter  commentsuserDaoImpl;

  private static final Logger log = Logger.getLogger(CommentsuserServiceImpl.class);
  @Override
  @Transactional
  public CommentsUser insertCommentsuserEntity(CommentsUser entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    //entity.setCuser(user.getId());
    //entity.setCtime(TimeTool.getTimeDate14());
    //entity.setCusername(user.getName());
    //entity.setEuser(user.getId()); 
    //entity.setEusername(user.getName());
    //entity.setEtime(TimeTool.getTimeDate14());
    //entity.setPstate("1");
    return commentsuserDaoImpl.insertEntity(entity);
  }
  @Override
  @Transactional
  public CommentsUser editCommentsuserEntity(CommentsUser entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    CommentsUser entity2 = commentsuserDaoImpl.getEntity(entity.getId());
    //entity2.setEuser(user.getId());
    //entity2.setEusername(user.getName());
    //entity2.setEtime(TimeTool.getTimeDate14()); 
    entity2.setObjid(entity.getObjid());
    entity2.setParentid(entity.getParentid());
    entity2.setNote(entity.getNote());
    entity2.setCtime(entity.getCtime());
    entity2.setUserid(entity.getUserid());
    entity2.setId(entity.getId());
    commentsuserDaoImpl.editEntity(entity2);
    return entity2;
  }
  @Override
  @Transactional
  public void deleteCommentsuserEntity(String id,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    commentsuserDaoImpl.deleteEntity(commentsuserDaoImpl.getEntity(id));
  }
  @Override
  @Transactional
  public CommentsUser getCommentsuserEntity(String id) {
    // TODO 自动生成代码,修改后请去除本注释
    if (id == null){return null;}
    return commentsuserDaoImpl.getEntity(id);
  }
  @Override
  @Transactional
  public DataQuery createCommentsuserSimpleQuery(DataQuery query) {
    // TODO 自动生成代码,修改后请去除本注释
    DataQuery dbQuery = DataQuery
        .init(
            query,
            "WLP_S_COMMENTS_USER",
            "ID,OBJID,PARENTID,NOTE,CTIME,USERID");
    return dbQuery;
  }

}
