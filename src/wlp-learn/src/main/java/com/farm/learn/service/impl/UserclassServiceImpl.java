package com.farm.learn.service.impl;

import com.farm.learn.domain.ClassHour;
import com.farm.learn.domain.Classt;
import com.farm.learn.domain.MajorClass;
import com.farm.learn.domain.UserClass;
import com.farm.learn.domain.UserHour;
import com.farm.learn.domain.ex.ClassLearnView;
import com.farm.learn.domain.ex.HourView;
import com.farm.learn.domain.ex.MajorView;
import com.farm.core.time.TimeTool;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.farm.learn.dao.UserclassDaoInter;
import com.farm.learn.dao.UserhourDaoInter;
import com.farm.learn.service.ClasstServiceInter;
import com.farm.learn.service.UserclassServiceInter;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.sfile.enums.FileModel;
import com.farm.sfile.exception.FileExNameException;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：用户课程服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class UserclassServiceImpl implements UserclassServiceInter {
	private static final Logger log = Logger.getLogger(UserclassServiceImpl.class);
	@Resource
	private UserclassDaoInter userclassDaoImpl;
	@Resource
	private UserhourDaoInter userhourDaoImpl;
	@Resource
	private ClasstServiceInter classTServiceImpl;
	@Resource
	private WdapFileServiceInter wdapFileServiceImpl;

	@Override
	@Transactional
	public UserClass insertUserclassEntity(UserClass entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return userclassDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public UserClass editUserclassEntity(UserClass entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		UserClass entity2 = userclassDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setNameback(entity.getNameback());
		entity2.setClassid(entity.getClassid());
		entity2.setPstate(entity.getPstate());
		entity2.setEtime(entity.getEtime());
		entity2.setStime(entity.getStime());
		entity2.setChoursid(entity.getChoursid());
		entity2.setUsetime(entity.getUsetime());
		entity2.setProcess(entity.getProcess());
		entity2.setId(entity.getId());
		userclassDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteUserclassEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		userclassDaoImpl.deleteEntity(userclassDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public UserClass getUserclassEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return userclassDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createUserclassSimpleQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		DataQuery dbQuery = DataQuery.init(query, "WLP_U_USERCLASS",
				"ID,NAMEBACK,CLASSID,PSTATE,ETIME,STIME,CHOURSID,USETIME,PROCESS");
		return dbQuery;
	}

	@Override
	@Transactional
	public ClassLearnView getClassLearnView(String classid, LoginUser currentUser) {
		if (currentUser == null) {
			return ClassLearnView.getNone();
		}
		List<UserClass> userclasss = userclassDaoImpl.selectEntitys(DBRuleList.getInstance()
				.add(new DBRule("CLASSID", classid, "=")).add(new DBRule("USERID", currentUser.getId(), "=")).toList());
		if (userclasss.size() <= 0) {
			return ClassLearnView.getNone();
		}

		ClassLearnView view = ClassLearnView.getNone();
		view.setLearned(true);
		view.setUserclass(userclasss.get(0));
		view.setUserhours(
				userhourDaoImpl.selectEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", classid, "="))
						.add(new DBRule("USERID", currentUser.getId(), "=")).toList()));

		return view;
	}

	@Override
	@Transactional
	public HourView startLearnClass(String classid, String hourid, LoginUser currentUser) throws FileExNameException {
		if (currentUser == null) {
			throw new RuntimeException("學習課程時,用戶不能爲空!");
		}
		UserClass userClass = userclassDaoImpl.getEntity(currentUser.getId(), classid);
		// 是否之前学习过课程
		if (userClass == null) {
			// 如果沒有学习过就加载第一个课时，同时生成学习记录
			Classt classt = classTServiceImpl.getClasstEntity(classid);
			userClass = new UserClass();
			userClass.setClassid(classid);
			if (StringUtils.isBlank(hourid)) {
				ClassHour hour = classTServiceImpl.getFirstHour(classid);
				userClass.setChoursid(hour.getId());
			} else {
				userClass.setChoursid(hourid);
			}
			// userClass.setEtime(etime);
			// userClass.setId(id);
			userClass.setNameback(classt.getName());
			userClass.setProcess(0);
			userClass.setLtime(TimeTool.getTimeDate14());
			userClass.setPstate("2");
			userClass.setStime(TimeTool.getTimeDate14());
			userClass.setUsetime(0);
			userClass.setUserid(currentUser.getId());
			userclassDaoImpl.insertEntity(userClass);
		} else {
			userClass = userclassDaoImpl.getEntity(userClass.getId());
			userClass.setLtime(TimeTool.getTimeDate14());
			if (StringUtils.isNotBlank(hourid)) {
				// 如果用户指定了课程就将该课程作为当前课程
				userClass.setChoursid(hourid);
			}
			userclassDaoImpl.editEntity(userClass);
		}
		UserHour userHour = userhourDaoImpl.getEntity(currentUser.getId(), userClass.getChoursid());
		// 如果学习过就加载之前未完成的课时，或者已经完成的下一节课时同时生成学习记录
		if (userHour == null) {
			userHour = new UserHour();
			userHour.setClassid(classid);
			// userHour.setEtime(etime);
			userHour.setHourid(userClass.getChoursid());
			userHour.setPstate("2");
			userHour.setStime(TimeTool.getTimeDate14());
			userHour.setLtime(TimeTool.getTimeDate14());
			userHour.setUserid(currentUser.getId());
			userhourDaoImpl.insertEntity(userHour);
		}
		HourView hourview = classTServiceImpl.getHourView(userClass.getChoursid());
		return hourview;
	}

	@Override
	@Transactional
	public DataResult getUserClasses(DataQuery query, LoginUser currentUser) throws SQLException {
		query.addRule(new DBRule("b.PSTATE", "1", "="));
		query.addSort(new DBSort("a.LTIME", "DESC"));
		query.addRule(new DBRule("a.USERID", currentUser.getId(), "="));
		DataQuery dbQuery = DataQuery.init(query,
				"WLP_U_USERCLASS a LEFT JOIN WLP_L_CLASS b ON a.CLASSID = b.id LEFT JOIN wlp_l_classhour c ON a.CHOURSID = c.id",
				"a.id as id,b.id as classid,c.id as hourid,b.IMGID AS IMGID, b. NAME AS NAME, a.NAMEBACK AS BNAME, a.PROCESS AS PROCESS, a.USETIME AS USETIME, c.TITLE AS HOURTITLE");
		DataResult result = dbQuery.search();
		result.runDictionary("1:入门,2:初级,3:中级,4:高级", "DIFFICULTY");
		result.runHandle(new ResultsHandle() {
			@Override
			public void handle(Map<String, Object> row) {
				if (row.get("IMGID") != null) {
					String url = wdapFileServiceImpl.getDownloadUrl((String) row.get("IMGID"), FileModel.IMG);
					row.put("IMGURL", url);
				}
			}
		});
		return result;
	}

	@Override
	@Transactional
	public String finishCurrentHour(String classid, LoginUser currentUser) {
		// 1获取当前课时
		UserClass userClass = userclassDaoImpl.getEntity(currentUser.getId(), classid);
		String currentHourid = userClass.getChoursid();
		// 2更新当前课时信息
		UserHour currenthour = userhourDaoImpl.getEntity(currentUser.getId(), currentHourid);
		currenthour.setPstate("1");
		currenthour.setEtime(TimeTool.getTimeDate14());
		currenthour.setLtime(TimeTool.getTimeDate14());
		userhourDaoImpl.editEntity(currenthour);
		// 3计算出下一个课时
		Set<String> completeHourids = userhourDaoImpl.getCompleteHourids(classid, currentUser.getId());
		ClassHour hour = classTServiceImpl.getNextHour(classid, currentHourid, completeHourids);
		// 4更新课程当前课时
		userClass.setChoursid(hour.getId());
		// 5计算课时完成百分比
		int allHournum = classTServiceImpl.getAllHoursNum(classid);
		int completeHournum = completeHourids.size();
		userClass.setProcess(completeHournum * 100 / allHournum);
		if (allHournum == completeHournum) {
			userClass.setPstate("3");
			userClass.setEtime(TimeTool.getTimeDate14());
		}
		userclassDaoImpl.editEntity(userClass);
		return hour.getId();
	}

	@Override
	@Transactional
	public void delUserClass(String classid, String userId) {
		userhourDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", classid, "="))
				.add(new DBRule("USERID", userId, "=")).toList());
		userclassDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", classid, "="))
				.add(new DBRule("USERID", userId, "=")).toList());
	}

	@Override
	@Transactional
	public int getUserCurrentClassNum(String userid) {
		return userclassDaoImpl.countEntitys(DBRuleList.getInstance().add(new DBRule("PSTATE", "2", "="))
				.add(new DBRule("USERID", userid, "=")).toList());
	}

	@Override
	@Transactional
	public DataResult learUsersQuery(DataQuery query) throws SQLException {
		query = DataQuery.init(query,
				"WLP_U_USERCLASS a LEFT JOIN ALONE_AUTH_USER b ON a.USERID = b.id LEFT JOIN ALONE_AUTH_USERORG c ON c.USERID = a.USERID LEFT JOIN alone_auth_organization d ON d.id = c.ORGANIZATIONID",
				"b. NAME AS USERNAME,d.NAME AS ORGNAME,a.PROCESS as PROCESS,a.STIME as STIME,a.ETIME as ETIME,a.LTIME as LTIME,a.PSTATE as LEARNSTATE");
		return query.search();
	}

	@Override
	@Transactional
	public void bingUserClasses(MajorView majorview, LoginUser currentUser) {
		for (MajorClass mclass : majorview.getClasses()) {
			if (currentUser != null) {
				mclass.setUserclass(userclassDaoImpl.getEntity(currentUser.getId(), mclass.getClassid()));
			}
			mclass.setReadable(classTServiceImpl.isReadAble(mclass.getClassid(), currentUser));
		}
	}
}