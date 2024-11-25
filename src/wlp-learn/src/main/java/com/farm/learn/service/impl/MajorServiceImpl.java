package com.farm.learn.service.impl;

import com.farm.learn.domain.ClassClassType;
import com.farm.learn.domain.ClassHour;
import com.farm.learn.domain.Classt;
import com.farm.learn.domain.Major;
import com.farm.learn.domain.MajorChapter;
import com.farm.learn.domain.ex.MajorView;
import com.farm.learn.enums.MajorFuncPOP;
import com.farm.core.time.TimeTool;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.farm.learn.dao.MajorDaoInter;
import com.farm.learn.dao.MajorchapterDaoInter;
import com.farm.learn.dao.MajorclassDaoInter;
import com.farm.learn.dao.MajorpopDaoInter;
import com.farm.learn.service.ClassclasstypeServiceInter;
import com.farm.learn.service.MajorServiceInter;
import com.farm.learn.service.MajorchapterServiceInter;
import com.farm.learn.service.MajorclassServiceInter;
import com.farm.learn.service.MajorpopServiceInter;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.sfile.enums.FileModel;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.farm.category.domain.ClassType;
import com.farm.category.enums.FuncPOP;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：专业服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class MajorServiceImpl implements MajorServiceInter {
	@Resource
	private WdapFileServiceInter wdapFileServiceImpl;
	@Resource
	private MajorDaoInter majorDaoImpl;
	@Resource
	private MajorpopServiceInter majorPopServiceImpl;
	@Resource
	private MajorclassDaoInter majorclassDaoImpl;
	@Resource
	private MajorchapterDaoInter majorchapterDaoImpl;
	@Resource
	private MajorpopDaoInter majorpopDaoImpl;
	@Resource
	private ClassclasstypeServiceInter classClassTypeServiceImpl;
	@Resource
	private MajorchapterServiceInter majorChapterServiceImpl;
	@Resource
	private MajorclassServiceInter majorClassServiceImpl;
	private static final Logger log = Logger.getLogger(MajorServiceImpl.class);

	@Override
	@Transactional
	public Major insertMajorEntity(Major entity, LoginUser user) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(user.getName());
		entity.setEuser(user.getId());
		entity.setEusername(user.getName());
		entity.setEtime(TimeTool.getTimeDate14());
		entity.setPstate("0");
		entity.setChapternum(0);
		entity.setClassnum(0);
		entity.setEnjoynum(0);
		entity.setReadpop("0");
		entity.setWritepop("3");
		return majorDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public Major editMajorEntity(Major entity, LoginUser user) {
		Major entity2 = majorDaoImpl.getEntity(entity.getId());
		entity2.setEuser(user.getId());
		entity2.setEusername(user.getName());
		entity2.setEtime(TimeTool.getTimeDate14());
		if (entity.getImgid() != null) {
			entity2.setImgid(entity.getImgid());
		}
		if (StringUtils.isNotBlank(entity.getNote())) {
			entity2.setNote(entity.getNote());
		}
		if (entity.getChapternum() != null) {
			entity2.setChapternum(entity.getChapternum());
		}
		if (entity.getClassnum() != null) {
			entity2.setClassnum(entity.getClassnum());
		}
		if (StringUtils.isNotBlank(entity.getTitle())) {
			entity2.setTitle(entity.getTitle());
		}
		if (entity.getEnjoynum() != null) {
			entity2.setEnjoynum(entity.getEnjoynum());
		}
		if (entity.getSort() != null) {
			entity2.setSort(entity.getSort());
		}
		if (StringUtils.isNotBlank(entity.getPcontent())) {
			entity2.setPcontent(entity.getPcontent());
		}
		if (StringUtils.isNotBlank(entity.getPstate())) {
			entity2.setPstate(entity.getPstate());
		}
		majorDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteMajorEntity(String id, LoginUser user) {
		majorclassDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("MAJORID", id, "=")).toList());
		majorpopDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("MAJORID", id, "=")).toList());
		majorchapterDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("MAJORID", id, "=")).toList());
		majorDaoImpl.deleteEntity(majorDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public Major getMajorEntity(String id) {
		if (id == null) {
			return null;
		}
		return majorDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createMajorSimpleQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		DataQuery dbQuery = DataQuery.init(query, "WLP_L_MAJOR",
				"ID,IMGID,NOTE,CHAPTERNUM,CLASSNUM,SORT,TITLE,ENJOYNUM,PCONTENT,PSTATE,EUSER,READPOP,WRITEPOP,EUSERNAME,CUSER,CUSERNAME,ETIME,CTIME");
		return dbQuery;
	}

	@Override
	@Transactional
	public DataResult getTempMajor(DataQuery query, LoginUser currentUser) throws SQLException {
		query.addRule(new DBRule("PSTATE", "0", "="));
		query.addSort(new DBSort("ETIME", "DESC"));
		DataQuery dbQuery = getMajorListQuery(query, currentUser, MajorFuncPOP.write);
		DataResult result = dbQuery.search();
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
	public DataResult getPublicMajor(DataQuery query, LoginUser currentUser) throws SQLException {
		query.addRule(new DBRule("PSTATE", "1", "="));
		query.addSort(new DBSort("ETIME", "DESC"));
		DataQuery dbQuery = getMajorListQuery(query, currentUser, MajorFuncPOP.write);
		DataResult result = dbQuery.search();
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
	public MajorView getMajorView(String majorid) {
		MajorView view = new MajorView();
		view.setMajor(majorDaoImpl.getEntity(majorid));
		view.setChapters(majorChapterServiceImpl.getChapters(majorid));
		view.setClasses(majorClassServiceImpl.getClassesByMajor(majorid));
		view.setImgurl(wdapFileServiceImpl.getDownloadUrl(view.getMajor().getImgid(), FileModel.IMG));
		return view;
	}

	private DataQuery getMajorListQuery(DataQuery query, LoginUser currentUser, MajorFuncPOP funcpop) {
		query.addSqlRule("and a.ID in ("
				+ DataQuerys.getWhereInSubVals(majorPopServiceImpl.getPopMajorIds(currentUser, funcpop)) + ")");
		query.setDistinct(true);
		query.addDefaultSort(new DBSort("a.SORT", "ASC"));
		DataQuery dbQuery = DataQuery.init(query, "WLP_L_MAJOR a",
				"a.ID AS ID,a.SORT as SORT,a.ETIME AS ETIME,a.PSTATE AS PSTATE,a.TITLE AS TITLE,a.NOTE AS NOTE,a.CHAPTERNUM AS CHAPTERNUM,a.CLASSNUM AS CLASSNUM,a.IMGID as IMGID,a.ENJOYNUM as ENJOYNUM");
		return dbQuery;
	}

	@Override
	@Transactional
	public DataResult getNewMajors(LoginUser currentUser, int num) throws SQLException {
		DataQuery query = DataQuery.getInstance();
		query.addRule(new DBRule("PSTATE", "1", "="));
		query.addSort(new DBSort("ETIME", "DESC"));
		query.setPagesize(num);
		query.setNoCount();
		DataQuery dbQuery = getMajorListQuery(query, currentUser, MajorFuncPOP.read);
		DataResult result = dbQuery.search();
		result.runformatTime("ETIME", "yyyy年MM月dd日");
		result.runHandle(new ResultsHandle() {
			@Override
			public void handle(Map<String, Object> row) {
				String url = wdapFileServiceImpl.getDownloadUrl((String) row.get("IMGID"), FileModel.IMG);
				row.put("IMGURL", url);
			}
		});
		return result;
	}

	@Override
	@Transactional
	public void publicMajor(String majorid, LoginUser currentUser) {
		Major major = majorDaoImpl.getEntity(majorid);
		major.setPstate("1");
		major.setEtime(TimeTool.getTimeDate14());
		major.setChapternum(majorchapterDaoImpl
				.countEntitys(DBRuleList.getInstance().add(new DBRule("MAJORID", majorid, "=")).toList()));
		major.setClassnum(majorclassDaoImpl
				.countEntitys(DBRuleList.getInstance().add(new DBRule("MAJORID", majorid, "=")).toList()));
		majorDaoImpl.editEntity(major);
	}

	@Override
	@Transactional
	public void tempMajor(String majorid, LoginUser currentUser) {
		Major major = majorDaoImpl.getEntity(majorid);
		major.setPstate("0");
		majorDaoImpl.editEntity(major);
	}

	@Override
	@Transactional
	public DataResult searchMajorsQuery(DataQuery query, LoginUser currentUser) throws SQLException {
		query.addRule(new DBRule("PSTATE", "1", "="));
		query.addDefaultSort(new DBSort("ETIME", "DESC"));
		DataQuery dbQuery = getMajorListQuery(query, currentUser, MajorFuncPOP.read);
		DataResult result = dbQuery.search();
		result.runformatTime("ETIME", "yyyy年MM月dd日");
		result.runHandle(new ResultsHandle() {
			@Override
			public void handle(Map<String, Object> row) {
				String url = wdapFileServiceImpl.getDownloadUrl((String) row.get("IMGID"), FileModel.IMG);
				row.put("IMGURL", url);
			}
		});
		return result;
	}
}
