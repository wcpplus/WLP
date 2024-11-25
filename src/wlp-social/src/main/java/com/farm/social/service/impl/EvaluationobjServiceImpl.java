package com.farm.social.service.impl;

import com.farm.social.domain.EvaluationObj;
import com.farm.social.domain.EvaluationUser;
import com.farm.core.time.TimeTool;

import org.apache.log4j.Logger;
import com.farm.social.dao.EvaluationobjDaoInter;
import com.farm.social.dao.EvaluationuserDaoInter;
import com.farm.social.service.EvaluationobjServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
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
public class EvaluationobjServiceImpl implements EvaluationobjServiceInter {
	@Resource
	private EvaluationobjDaoInter evaluationobjDaoImpl;
	@Resource
	private EvaluationuserDaoInter evaluationuserDaoImpl;
	private static final Logger log = Logger.getLogger(EvaluationobjServiceImpl.class);

	@Override
	@Transactional
	public EvaluationObj insertEvaluationobjEntity(EvaluationObj entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return evaluationobjDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public EvaluationObj editEvaluationobjEntity(EvaluationObj entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		EvaluationObj entity2 = evaluationobjDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setScore(entity.getScore());
		entity2.setNum(entity.getNum());
		entity2.setApptype(entity.getApptype());
		entity2.setAppid(entity.getAppid());
		entity2.setPcontent(entity.getPcontent());
		entity2.setId(entity.getId());
		evaluationobjDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteEvaluationobjEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		evaluationobjDaoImpl.deleteEntity(evaluationobjDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public EvaluationObj getEvaluationobjEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return evaluationobjDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createEvaluationobjSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query,
				"WLP_S_EVALUATION_USER a left join WLP_S_EVALUATION_OBJ b on a.OBJID=B.ID left join ALONE_AUTH_USER c on c.id=a.userid",
				"A.ID as ID,B.APPTYPE as APPTYPE,B.NUM as NUM,B.SCORE as ALLSCORE,B.APPID as APPID,A.USERID as USERID,A.CTIME as CTIME,A.NOTE as NOTE,A.SCORE as SCORE,C.NAME as USERNAME");
		return dbQuery;
	}

	private EvaluationObj initObj(String appid, String apptype) {
		List<EvaluationObj> objs = evaluationobjDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("APPID", appid, "=")).toList());
		if (objs.size() > 0 || apptype == null) {
			if (objs.size() == 0) {
				return null;
			}
			return objs.get(0);
		} else {
			EvaluationObj Ojb = new EvaluationObj();
			Ojb.setAppid(appid);
			Ojb.setApptype(apptype);
			Ojb.setNum(0);
			Ojb.setScore(0f);
			Ojb = evaluationobjDaoImpl.insertEntity(Ojb);
			return Ojb;
		}
	}

	private void updateNum(String objid) {
		evaluationuserDaoImpl.getSession().flush();
		int num = evaluationuserDaoImpl
				.countEntitys(DBRuleList.getInstance().add(new DBRule("OBJID", objid, "=")).toList());
		int allscore = evaluationuserDaoImpl.sumScore(objid);
		if (num == 0) {
			num = 1;
		}
		EvaluationObj obj = evaluationobjDaoImpl.getEntity(objid);
		obj.setNum(num);
		obj.setScore((float)allscore / num);
		evaluationobjDaoImpl.editEntity(obj);
	}

	@Override
	@Transactional
	public void publicEvaluation(String appid, String apptype, Integer score, String comments, LoginUser currentUser) {
		EvaluationObj obj = initObj(appid, apptype);
		evaluationuserDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("USERID", currentUser.getId(), "="))
				.add(new DBRule("OBJID", obj.getId(), "=")).toList());
		EvaluationUser cuser = new EvaluationUser();
		cuser.setCtime(TimeTool.getTimeDate14());
		cuser.setNote(comments);
		cuser.setObjid(obj.getId());
		cuser.setScore(score);
		cuser.setUserid(currentUser.getId());
		evaluationuserDaoImpl.insertEntity(cuser);
		updateNum(obj.getId());
	}

	@Override
	@Transactional
	public DataResult loadEvaluation(String appid, Integer page, int pagesize) throws SQLException {
		DataQuery query = DataQuery.getInstance();
		query.setPagesize(pagesize);
		query.setCurrentPage(page);
		query.addRule(new DBRule("B.APPID", appid, "="));
		query.addSort(new DBSort("CTIME", "DESC"));
		DataQuery dbQuery = DataQuery.init(query,
				"WLP_S_EVALUATION_USER A LEFT JOIN WLP_S_EVALUATION_OBJ B ON A.OBJID=B.ID",
				"A.USERID AS USERID, A.CTIME AS CTIME, A.NOTE AS NOTE, A.SCORE AS SCORE, A.ID AS ID,B.ID as OBJID");
		return dbQuery.search();
	}

	@Override
	@Transactional
	public void deleteEvaluation(String evaluationUserid) {
		EvaluationUser euser = evaluationuserDaoImpl.getEntity(evaluationUserid);
		evaluationuserDaoImpl.deleteEntity(euser);
		updateNum(euser.getObjid());
	}
}
