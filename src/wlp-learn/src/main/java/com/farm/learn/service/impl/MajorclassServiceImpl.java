package com.farm.learn.service.impl;

import com.farm.learn.domain.MajorClass;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;

import com.farm.learn.dao.ClasstDaoInter;
import com.farm.learn.dao.MajorclassDaoInter;
import com.farm.learn.service.MajorclassServiceInter;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.sfile.enums.FileModel;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：专业课程服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class MajorclassServiceImpl implements MajorclassServiceInter {
	@Resource
	private MajorclassDaoInter majorclassDaoImpl;
	@Resource
	private ClasstDaoInter classtDaoImpl;
	@Resource
	private WdapFileServiceInter wdapFileServiceImpl;
	private static final Logger log = Logger.getLogger(MajorclassServiceImpl.class);

	@Override
	@Transactional
	public MajorClass insertMajorclassEntity(MajorClass entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return majorclassDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public MajorClass editMajorclassEntity(MajorClass entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		MajorClass entity2 = majorclassDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setChapterid(entity.getChapterid());
		entity2.setClassid(entity.getClassid());
		entity2.setSort(entity.getSort());
		entity2.setMajorid(entity.getMajorid());
		entity2.setId(entity.getId());
		majorclassDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteMajorclassEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		majorclassDaoImpl.deleteEntity(majorclassDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public MajorClass getMajorclassEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return majorclassDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createMajorclassSimpleQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		DataQuery dbQuery = DataQuery.init(query, "WLP_L_MAJORCLASS", "ID,CHAPTERID,CLASSID,SORT,MAJORID");
		return dbQuery;
	}

	@Override
	@Transactional
	public List<MajorClass> getClassesByMajor(String majorid) {
		List<MajorClass> classes = majorclassDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("MAJORID", majorid, "=")).toList());
		for (MajorClass mclass : classes) {
			mclass.setClasst(classtDaoImpl.getEntity(mclass.getClassid()));
			mclass.setImgUrl(wdapFileServiceImpl.getDownloadUrl(mclass.getClasst().getImgid(), FileModel.IMG));
		}
		return classes;
	}

}
