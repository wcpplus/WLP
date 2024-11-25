package com.farm.social.service.impl;

import com.farm.social.domain.AppbindObj;
import com.farm.social.domain.AppbindResource;
import com.farm.social.domain.ex.ResourceView;
import com.farm.core.time.TimeTool;
import com.farm.parameter.FarmParameterService;

import org.apache.log4j.Logger;
import com.farm.social.dao.AppbindobjDaoInter;
import com.farm.social.dao.AppbindresourceDaoInter;
import com.farm.social.service.AppbindobjServiceInter;
import com.farm.util.web.FarmFormatUnits;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.ResultsHandle;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：应用绑定对象服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class AppbindobjServiceImpl implements AppbindobjServiceInter {
	@Resource
	private AppbindobjDaoInter appbindobjDaoImpl;
	@Resource
	private AppbindresourceDaoInter appbindresourceDaoImpl;
	private static final Logger log = Logger.getLogger(AppbindobjServiceImpl.class);

	@Override
	@Transactional
	public AppbindObj insertAppbindobjEntity(AppbindObj entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return appbindobjDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public AppbindObj editAppbindobjEntity(AppbindObj entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		AppbindObj entity2 = appbindobjDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setApptype(entity.getApptype());
		entity2.setAppid(entity.getAppid());
		entity2.setPcontent(entity.getPcontent());
		entity2.setId(entity.getId());
		appbindobjDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteAppbindobjEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		appbindobjDaoImpl.deleteEntity(appbindobjDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public AppbindObj getAppbindobjEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return appbindobjDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createAppbindobjSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query,
				"WLP_S_APPBIND_RESOURCE a left join WLP_S_APPBIND_OBJ b on a.objid=b.id left join WLP_S_OUTSYS c on a.sysid=c.id left join ALONE_AUTH_USER d on a.userid=d.id",
				"a.ID as ID,b.APPTYPE as APPTYPE,b.APPID as APPID,b.NUM as NUM,d.NAME as USERNAME,a.USERID as USERID,a.CTIME as CTIME,c.SYSNAME as SYSNAME,a.RESOURCEURL as RESOURCEURL,a.RESOURCETYPE as RESOURCETYPE");
		return dbQuery;
	}

	@Override
	@Transactional
	public AppbindResource getResource(String appbindresourceid) {
		return appbindresourceDaoImpl.getEntity(appbindresourceid);
	}

	private AppbindObj initObj(String appid, String apptype) {
		List<AppbindObj> objs = appbindobjDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("APPID", appid, "=")).toList());
		if (objs.size() > 0 || apptype == null) {
			if (objs.size() == 0) {
				return null;
			}
			return objs.get(0);
		} else {
			AppbindObj Ojb = new AppbindObj();
			Ojb.setAppid(appid);
			Ojb.setApptype(apptype);
			Ojb.setNum(0);
			Ojb = appbindobjDaoImpl.insertEntity(Ojb);
			return Ojb;
		}
	}

	private void updateNum(String objid) {
		appbindresourceDaoImpl.getSession().flush();
		int num = appbindresourceDaoImpl
				.countEntitys(DBRuleList.getInstance().add(new DBRule("OBJID", objid, "=")).toList());
		AppbindObj obj = appbindobjDaoImpl.getEntity(objid);
		obj.setNum(num);
		appbindobjDaoImpl.editEntity(obj);
	}

	@Override
	@Transactional
	public AppbindResource editResource(String resourceId, String appid, String apptype, String resourceurl,
			String resourcetype, String sysid, LoginUser currentUser) {
		AppbindObj obj = initObj(appid, apptype);
		AppbindResource resource = appbindresourceDaoImpl.getEntity(resourceId);
		String objid1 = obj.getId();
		String objid2 = resource.getObjid();
		resource.setCtime(TimeTool.getTimeDate14());
		resource.setObjid(obj.getId());
		resource.setUserid(currentUser.getId());
		resource.setSysid(sysid);
		resource.setResourcetype(resourcetype);
		resource.setResourceurl(resourceurl);
		appbindresourceDaoImpl.editEntity(resource);
		updateNum(objid1);
		if (!objid1.equals(objid2)) {
			updateNum(objid2);
		}
		return resource;
	}

	@Override
	@Transactional
	public AppbindResource addResource(String appid, String apptype, String resourceurl, String resourcetype,
			String sysid, LoginUser currentUser) {
		AppbindObj Ojb = initObj(appid, apptype);
		AppbindResource resource = new AppbindResource();
		resource.setCtime(TimeTool.getTimeDate14());
		resource.setObjid(Ojb.getId());
		resource.setUserid(currentUser.getId());
		resource.setSysid(sysid);
		resource.setResourcetype(resourcetype);
		resource.setResourceurl(resourceurl);
		appbindresourceDaoImpl.insertEntity(resource);
		updateNum(Ojb.getId());
		return resource;
	}

	@Override
	@Transactional
	public void deleteResource(String resourceid, LoginUser currentUser) {
		AppbindResource resource = appbindresourceDaoImpl.getEntity(resourceid);
		appbindresourceDaoImpl.deleteEntity(resource);
		updateNum(resource.getObjid());
	}

	@Override
	@Transactional
	public List<ResourceView> getAppResource(String appid) throws SQLException {
		DataQuery dbQuery = DataQuery.getInstance();
		dbQuery.setPagesize(10);
		dbQuery.addDefaultSort(new DBSort("b.RESOURCETYPE", "ASC"));
		dbQuery.addRule(new DBRule("c.PSTATE", "1", "="));
		dbQuery.addSqlRule("and C.ID is not null");
		dbQuery.addRule(new DBRule("a.APPID", appid, "="));
		dbQuery = DataQuery.init(dbQuery,
				"WLP_S_APPBIND_OBJ a left join WLP_S_APPBIND_RESOURCE b on a.id=b.objid left join WLP_S_OUTSYS c on b.sysid=c.id",
				"a.APPID as APPID,a.APPTYPE as APPTYPE,b.RESOURCEURL as RESOURCEURL,b.RESOURCETYPE as RESOURCETYPE,c.BASEURL as BASEURL");
		final List<ResourceView> list = new ArrayList<>();
		dbQuery.search().runHandle(new ResultsHandle() {
			@Override
			public void handle(Map<String, Object> row) {
				ResourceView view = new ResourceView();
				view.setAppid((String) row.get("APPID"));
				view.setApptype((String) row.get("APPTYPE"));
				view.setResourceurl((String) row.get("RESOURCEURL"));
				view.setResourcetype((String) row.get("RESOURCETYPE"));
				view.setResourceicon(ResourceType.getDicMap().get((String) row.get("RESOURCETYPE")).getIcon());
				view.setApptypename(FarmFormatUnits.getDicMap("1:专业,2:课程,3:课时").get((String) row.get("APPTYPE")));
				view.setResourcename(ResourceType.getDicMap().get((String) row.get("RESOURCETYPE")).getTitle());
				view.setBaseurl((String) row.get("BASEURL"));
				list.add(view);
			}
		});
		return list;
	}
}
