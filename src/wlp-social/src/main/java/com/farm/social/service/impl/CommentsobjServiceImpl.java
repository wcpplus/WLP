package com.farm.social.service.impl;

import com.farm.social.domain.CommentsObj;
import com.farm.social.domain.CommentsUser;
import com.farm.social.domain.EvaluationUser;
import com.farm.social.domain.FavoriteObj;
import com.farm.core.time.TimeTool;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.sfile.enums.FileModel;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.farm.social.dao.CommentsobjDaoInter;
import com.farm.social.dao.CommentsuserDaoInter;
import com.farm.social.service.CommentsobjServiceInter;
import com.farm.web.WebUtils;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
import com.farm.core.sql.result.DataResult;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.farm.authority.FarmAuthorityService;
import com.farm.authority.domain.User;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：评论对象服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class CommentsobjServiceImpl implements CommentsobjServiceInter {
	@Resource
	private CommentsobjDaoInter commentsobjDaoImpl;
	@Resource
	private CommentsuserDaoInter commentsuserDaoImpl;
	@Resource
	private WdapFileServiceInter wdapFileServiceImpl;
	private static final Logger log = Logger.getLogger(CommentsobjServiceImpl.class);

	@Override
	@Transactional
	public CommentsObj insertCommentsobjEntity(CommentsObj entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return commentsobjDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public CommentsObj editCommentsobjEntity(CommentsObj entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		CommentsObj entity2 = commentsobjDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setNum(entity.getNum());
		entity2.setApptype(entity.getApptype());
		entity2.setAppid(entity.getAppid());
		entity2.setPcontent(entity.getPcontent());
		entity2.setId(entity.getId());
		commentsobjDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteCommentsobjEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		commentsobjDaoImpl.deleteEntity(commentsobjDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public CommentsObj getCommentsobjEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return commentsobjDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createCommentsobjSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query,
				"WLP_S_COMMENTS_USER a left join WLP_S_COMMENTS_OBJ b on a.OBJID=B.ID left join ALONE_AUTH_USER c on c.id=a.userid",
				"A.ID as ID,B.APPTYPE as APPTYPE,B.NUM as NUM,B.APPID as APPID,A.USERID as USERID,A.CTIME as CTIME,A.NOTE as NOTE,C.NAME as USERNAME");
		return dbQuery;
	}

	private CommentsObj initObj(String appid, String apptype) {
		List<CommentsObj> objs = commentsobjDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("APPID", appid, "=")).toList());
		if (objs.size() > 0 || apptype == null) {
			if (objs.size() == 0) {
				return null;
			}
			return objs.get(0);
		} else {
			CommentsObj Ojb = new CommentsObj();
			Ojb.setAppid(appid);
			Ojb.setApptype(apptype);
			Ojb.setNum(0);
			Ojb = commentsobjDaoImpl.insertEntity(Ojb);
			return Ojb;
		}
	}

	private void updateNum(String objid) {
		commentsuserDaoImpl.getSession().flush();
		int num = commentsuserDaoImpl
				.countEntitys(DBRuleList.getInstance().add(new DBRule("OBJID", objid, "=")).toList());
		CommentsObj obj = commentsobjDaoImpl.getEntity(objid);
		obj.setNum(num);
		commentsobjDaoImpl.editEntity(obj);
	}

	@Override
	@Transactional
	public void publicComments(String appid, String apptype, String parentid, String comments, LoginUser currentUser) {
		CommentsObj Ojb = initObj(appid, apptype);
		CommentsUser cuser = new CommentsUser();
		cuser.setCtime(TimeTool.getTimeDate14());
		cuser.setNote(comments);
		cuser.setObjid(Ojb.getId());
		if (StringUtils.isNotBlank(parentid)) {
			cuser.setParentid(parentid);
		}
		cuser.setUserid(currentUser.getId());
		commentsuserDaoImpl.insertEntity(cuser);
		updateNum(Ojb.getId());
	}

	@Override
	@Transactional
	public DataResult loadComments(String appids, int page, int size) throws SQLException {
		DataQuery query = DataQuery.getInstance();
		query.setPagesize(size);
		query.setCurrentPage(page);
		query.addSqlRule(" AND B.APPID IN (" + DataQuerys.getWhereInSubVals(WebUtils.parseIds(appids)) + ")");
		query.addSort(new DBSort("CTIME", "DESC"));
		DataQuery dbQuery = DataQuery.init(query,
				"WLP_S_COMMENTS_USER A LEFT JOIN WLP_S_COMMENTS_OBJ B ON A.OBJID=B.ID",
				"A.USERID AS USERID, A.CTIME AS CTIME, A.NOTE AS NOTE, A.PARENTID AS PARENTID, A.ID AS ID");
		return dbQuery.search();
	}

	@Override
	@Transactional
	public CommentsUser getUserComments(String commentsUserId) {
		CommentsUser cuser = commentsuserDaoImpl.getEntity(commentsUserId);
		if (cuser != null) {
			cuser.setUser((User) FarmAuthorityService.getInstance().getUserById(cuser.getUserid()));
			cuser.setImgUrl(wdapFileServiceImpl.getDownloadUrl(cuser.getUser().getImgid(), FileModel.PHOTO));
		}
		return cuser;
	}

	@Override
	@Transactional
	public void deleteComments(String evaluationUserid) {
		CommentsUser euser = commentsuserDaoImpl.getEntity(evaluationUserid);
		commentsuserDaoImpl.deleteEntity(euser);
		updateNum(euser.getObjid());

	}
}
