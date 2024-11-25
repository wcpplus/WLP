package com.farm.learn.service.impl;

import com.farm.learn.domain.MajorChapter;
import com.farm.learn.domain.MajorClass;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.farm.learn.dao.MajorchapterDaoInter;
import com.farm.learn.dao.MajorclassDaoInter;
import com.farm.learn.service.MajorchapterServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：专业步骤服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class MajorchapterServiceImpl implements MajorchapterServiceInter {
	@Resource
	private MajorchapterDaoInter majorchapterDaoImpl;
	@Resource
	private MajorclassDaoInter majorclassDaoImpl;
	private static final Logger log = Logger.getLogger(MajorchapterServiceImpl.class);

	@Override
	@Transactional
	public MajorChapter insertMajorchapterEntity(MajorChapter entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return majorchapterDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public MajorChapter editMajorchapterEntity(MajorChapter entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		MajorChapter entity2 = majorchapterDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setSort(entity.getSort());
		entity2.setMajorid(entity.getMajorid());
		entity2.setNote(entity.getNote());
		entity2.setTitle(entity.getTitle());
		entity2.setId(entity.getId());
		majorchapterDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteMajorchapterEntity(String id, LoginUser user) {
		majorclassDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("CHAPTERID", id, "=")).toList());
		majorchapterDaoImpl.deleteEntity(majorchapterDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public MajorChapter getMajorchapterEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return majorchapterDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createMajorchapterSimpleQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		DataQuery dbQuery = DataQuery.init(query, "WLP_L_MAJORCHAPTER", "ID,SORT,MAJORID,NOTE,TITLE");
		return dbQuery;
	}

	@Override
	@Transactional
	public List<MajorChapter> getChapters(String majorid) {
		List<MajorChapter> list = majorchapterDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("MAJORID", majorid, "=")).toList());
		Collections.sort(list, new Comparator<MajorChapter>() {
			@Override
			public int compare(MajorChapter o1, MajorChapter o2) {
				return o1.getSort() - o2.getSort();
			}
		});
		return list;
	}

	@Override
	@Transactional
	public void addClasst(String chapterid, String classtid) {
		MajorChapter chapter = majorchapterDaoImpl.getEntity(chapterid);
		majorclassDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("CHAPTERID", chapterid, "="))
				.add(new DBRule("MAJORID", chapter.getMajorid(), "=")).add(new DBRule("CLASSID", classtid, "="))
				.toList());
		MajorClass mclass = new MajorClass();
		int n = majorclassDaoImpl.countEntitys(DBRuleList.getInstance().add(new DBRule("CHAPTERID", chapterid, "="))
				.add(new DBRule("MAJORID", chapter.getMajorid(), "=")).toList());
		mclass.setChapterid(chapterid);
		mclass.setClassid(classtid);
		mclass.setMajorid(chapter.getMajorid());
		mclass.setSort(n + 1);
		majorclassDaoImpl.insertEntity(mclass);
	}

}
