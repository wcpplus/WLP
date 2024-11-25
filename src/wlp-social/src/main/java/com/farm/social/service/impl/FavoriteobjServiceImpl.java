package com.farm.social.service.impl;

import com.farm.social.domain.FavoriteObj;
import com.farm.social.domain.FavoriteUser;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.farm.social.dao.FavoriteobjDaoInter;
import com.farm.social.dao.FavoriteuserDaoInter;
import com.farm.social.service.FavoriteobjServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：收藏对象服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class FavoriteobjServiceImpl implements FavoriteobjServiceInter {
	@Resource
	private FavoriteobjDaoInter favoriteobjDaoImpl;
	@Resource
	private FavoriteuserDaoInter favoriteuserDaoImpl;
	private static final Logger log = Logger.getLogger(FavoriteobjServiceImpl.class);

	@Override
	@Transactional
	public FavoriteObj insertFavoriteobjEntity(FavoriteObj entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return favoriteobjDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public FavoriteObj editFavoriteobjEntity(FavoriteObj entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		FavoriteObj entity2 = favoriteobjDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setNum(entity.getNum());
		entity2.setApptype(entity.getApptype());
		entity2.setAppid(entity.getAppid());
		entity2.setPcontent(entity.getPcontent());
		entity2.setId(entity.getId());
		favoriteobjDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteFavoriteobjEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		favoriteobjDaoImpl.deleteEntity(favoriteobjDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public FavoriteObj getFavoriteobjEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return favoriteobjDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createFavoriteobjSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query,
				"WLP_S_FAVORITE_USER a left join WLP_S_FAVORITE_OBJ b on a.OBJID=B.ID left join ALONE_AUTH_USER c on c.id=a.userid",
				"A.ID as ID,B.APPTYPE as APPTYPE,B.APPID as APPID,A.USERID as USERID,A.CTIME as CTIME,C.NAME as USERNAME");
		return dbQuery;
	}

	private FavoriteObj initObj(String appid, String apptype) {
		List<FavoriteObj> objs = favoriteobjDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("APPID", appid, "=")).toList());
		if (objs.size() > 0 || apptype == null) {
			if (objs.size() == 0) {
				return null;
			}
			return objs.get(0);
		} else {
			FavoriteObj Ojb = new FavoriteObj();
			Ojb.setAppid(appid);
			Ojb.setApptype(apptype);
			Ojb.setNum(0);
			// Ojb.setPcontent(pcontent);
			Ojb = favoriteobjDaoImpl.insertEntity(Ojb);
			return Ojb;
		}
	}

	private void updateNum(String objid) {
		favoriteuserDaoImpl.getSession().flush();
		int num = favoriteuserDaoImpl
				.countEntitys(DBRuleList.getInstance().add(new DBRule("OBJID", objid, "=")).toList());
		FavoriteObj obj = favoriteobjDaoImpl.getEntity(objid);
		obj.setNum(num);
		favoriteobjDaoImpl.editEntity(obj);
	}

	@Override
	@Transactional
	public void favorite(String appid, String apptype, LoginUser currentUser) {
		FavoriteObj obj = initObj(appid, apptype);
		favoriteuserDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("USERID", currentUser.getId(), "="))
				.add(new DBRule("OBJID", obj.getId(), "=")).toList());
		FavoriteUser fuser = new FavoriteUser();
		fuser.setCtime(TimeTool.getTimeDate14());
		fuser.setDotype("1");
		fuser.setObjid(obj.getId());
		fuser.setUserid(currentUser.getId());
		favoriteuserDaoImpl.insertEntity(fuser);
		updateNum(obj.getId());
	}

	@Override
	@Transactional
	public void unFavorite(String appid, LoginUser currentUser) {
		FavoriteObj obj = initObj(appid, null);
		favoriteuserDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("USERID", currentUser.getId(), "="))
				.add(new DBRule("OBJID", obj.getId(), "=")).toList());
		updateNum(obj.getId());
	}

	@Override
	@Transactional
	public boolean isFavorite(String appid, LoginUser currentUser) {
		if (currentUser == null) {
			return false;
		}
		FavoriteObj obj = initObj(appid, null);
		if (obj == null) {
			return false;
		}
		int num = favoriteuserDaoImpl.countEntitys(DBRuleList.getInstance().add(new DBRule("OBJID", obj.getId(), "="))
				.add(new DBRule("USERID", currentUser.getId(), "=")).toList());
		return num > 0;
	}

	@Override
	@Transactional
	public FavoriteObj loadFavoriteObj(String appid, String apptype) {
		return initObj(appid, apptype);
	}

	@Override
	public DataQuery createUserFavoriteQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query,
				"WLP_S_FAVORITE_USER a left join WLP_S_FAVORITE_OBJ b on a.OBJID=b.ID",
				"a.USERID as USERID,a.CTIME as CTIME,a.DOTYPE as DOTYPE,b.APPID as APPID,b.APPTYPE as APPTYPE,b.NUM as NUM");
		return dbQuery;
	}

}
