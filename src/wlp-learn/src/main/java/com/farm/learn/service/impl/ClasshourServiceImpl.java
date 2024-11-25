package com.farm.learn.service.impl;

import com.farm.learn.domain.ClassChapter;
import com.farm.learn.domain.ClassClassType;
import com.farm.learn.domain.ClassHour;
import com.farm.core.time.TimeTool;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.farm.learn.dao.ClasschapterDaoInter;
import com.farm.learn.dao.ClassclasstypeDaoInter;
import com.farm.learn.dao.ClasshourDaoInter;
import com.farm.learn.service.ClasschapterServiceInter;
import com.farm.learn.service.ClasshourServiceInter;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.sfile.domain.FileBase;
import com.farm.sfile.enums.FileModel;
import com.farm.sfile.utils.PdfToImgConvertor;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.farm.category.domain.ClassType;
import com.farm.category.enums.FuncPOP;
import com.farm.category.service.ClasstypeServiceInter;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：课程课时服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class ClasshourServiceImpl implements ClasshourServiceInter {
	@Resource
	private ClasshourDaoInter classhourDaoImpl;
	@Resource
	private ClasschapterDaoInter classchapterDaoImpl;
	@Resource
	private WdapFileServiceInter wdapFileServiceImpl;
	@Resource
	private ClassclasstypeDaoInter classclasstypeDaoImpl;
	@Resource
	private ClasstypeServiceInter classTypeServiceImpl;
	@Resource
	private ClasschapterServiceInter classChapterServiceImpl;
	private static final Logger log = Logger.getLogger(ClasshourServiceImpl.class);

	@Override
	@Transactional
	public ClassHour insertClasshourEntity(ClassHour entity, LoginUser user) throws FileNotFoundException {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(user.getName());
		entity.setEuser(user.getId());
		entity.setEusername(user.getName());
		entity.setEtime(TimeTool.getTimeDate14());
		entity.setAltime(entity.getAltime());
		entity.setPstate("1");
		ClassChapter chapter = classchapterDaoImpl.getEntity(entity.getChapterid());
		entity.setClassid(chapter.getClassid());
		if (entity.getFileid() != null) {
			wdapFileServiceImpl.submitFile(entity.getFileid());
			FileBase fbase = wdapFileServiceImpl.getFileBase(entity.getFileid());
			if (fbase.getExname().toUpperCase().equals("PDF")) {
				PdfToImgConvertor.waitingConvert(wdapFileServiceImpl.getPersistFile(entity.getFileid()).getFile());
			}
		}
		return classhourDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public ClassHour editClasshourEntity(ClassHour entity, LoginUser user) throws FileNotFoundException {
		ClassHour entity2 = classhourDaoImpl.getEntity(entity.getId());
		entity2.setEuser(user.getId());
		entity2.setEusername(user.getName());
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setNote(entity.getNote());
		// entity2.setChapterid(entity.getChapterid());
		// entity2.setClassid(entity.getClassid());
		entity2.setTitle(entity.getTitle());
		entity2.setSort(entity.getSort());
		entity2.setAltime(entity.getAltime());
		// entity2.setAuthorid(entity.getAuthorid());
		// entity2.setPcontent(entity.getPcontent());
		// entity2.setPstate(entity.getPstate());
		// entity2.setId(entity.getId());
		if (entity.getFileid() != null) {
			if (StringUtils.isNotBlank(entity2.getFileid())) {
				wdapFileServiceImpl.freeFile(entity2.getFileid());
			}
			entity2.setFileid(entity.getFileid());
			wdapFileServiceImpl.submitFile(entity.getFileid());
			FileBase fbase = wdapFileServiceImpl.getFileBase(entity.getFileid());
			if (fbase.getExname().toUpperCase().equals("PDF")) {
				PdfToImgConvertor.waitingConvert(wdapFileServiceImpl.getPersistFile(entity.getFileid()).getFile());
			}
		}
		classhourDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteClasshourEntity(String id, LoginUser user) {
		ClassHour hour = classhourDaoImpl.getEntity(id);
		wdapFileServiceImpl.freeFile(hour.getFileid());
		classhourDaoImpl.deleteEntity(hour);
	}

	@Override
	@Transactional
	public ClassHour getClasshourEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return classhourDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createClasshourSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query,
				"WLP_L_CLASSHOUR D LEFT JOIN  WLP_L_CLASSCHAPTER E ON D.CHAPTERID=E.ID LEFT JOIN WLP_L_CLASS A ON D.CLASSID=A.ID LEFT JOIN WLP_L_CLASSCLASSTYPE B ON B.CLASSID=A.ID LEFT JOIN WLP_C_CLASSTYPE C ON C.ID=B.CLASSTYPEID",
				"D.ID AS ID,C.NAME AS TYPENAME,A.NAME AS CLASSNAME,E.TITLE AS CHAPTERNAME,D.TITLE AS NAME,D.ALTIME as ALTIME");
		return dbQuery;
	}

	@Override
	@Transactional
	public List<ClassHour> getHoursByClass(String classid) {
		List<ClassHour> list = classhourDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", classid, "=")).toList());
		Collections.sort(list, new Comparator<ClassHour>() {
			@Override
			public int compare(ClassHour o1, ClassHour o2) {
				return o1.getSort() - o2.getSort();
			}
		});
		return list;
	}

	@Override
	@Transactional
	public DataResult searchHoursQuery(DataQuery query, LoginUser currentUser) throws SQLException {
		query.addRule(new DBRule("a.PSTATE", "1", "="));
		query.addSort(new DBSort("a.ETIME", "DESC"));
		query.addSqlRule("and B.CLASSTYPEID in ("
				+ DataQuerys.getWhereInSubVals(classTypeServiceImpl.getUserAllTypeIds(currentUser, FuncPOP.read))
				+ ")");
		query.setDistinct(true);
		query = DataQuery.init(query,
				"WLP_L_CLASS a left join WLP_L_CLASSCLASSTYPE b on a.id=b.CLASSID left join WLP_L_CLASSTAG c on c.classid=a.id left join WLP_L_CLASSHOUR d on d.CLASSID=a.ID",
				"a.ID AS ID,a.etime as ETIME, a.INTRODUCTIONID as INTRODUCTIONID, a.NAME as NAME, a.DIFFICULTY as DIFFICULTY, d.NOTE as NOTE,d.TITLE as TITLE, a.OUTAUTHOR as OUTAUTHOR,d.ID as HOURID, d.ALTIME as ALTIME, a.EVALUATION as EVALUATION, a.LEARNEDNUM as LEARNEDNUM, a.IMGID as IMGID, a.MINIMGID as MINIMGID, a.BOOKEDNUM as BOOKEDNUM, a.HOTSCORE as HOTSCORE, b.CLASSTYPEID as CLASSTYPEID");
		DataResult result = query.search();
		result.runDictionary("1:入门,2:初级,3:中级,4:高级", "DIFFICULTY");
		result.runHandle(new ResultsHandle() {
			@Override
			public void handle(Map<String, Object> row) {
				if (row.get("IMGID") != null) {
					String url = wdapFileServiceImpl.getDownloadUrl((String) row.get("IMGID"), FileModel.IMG);
					row.put("IMGURL", url);
				}
				List<ClassClassType> classtypes = classclasstypeDaoImpl.getEntityByClassId((String) row.get("ID"));
				if (classtypes.size() > 0) {
					ClassType type = classTypeServiceImpl.getClasstypeEntity(classtypes.get(0).getClasstypeid());
					List<ClassType> types = classTypeServiceImpl.getAllPathType(type);
					row.put("TYPES", types);
				}
			}
		});
		return result;
	}

	@Override
	@Transactional
	public List<ClassHour> getSortHoursByClass(String classid) {
		List<ClassHour> hours= getHoursByClass(classid);
		final Map<String, ClassChapter> chapters = new HashMap<String, ClassChapter>();
		for (ClassChapter chapter : classChapterServiceImpl.getChapters(classid)) {
			chapters.put(chapter.getId(), chapter);
		}
		Collections.sort(hours, new Comparator<ClassHour>() {
			@Override
			public int compare(ClassHour o1, ClassHour o2) {
				if (chapters.get(o1.getChapterid()).getSort() - chapters.get(o2.getChapterid()).getSort() == 0) {
					return o1.getSort() - o2.getSort();
				} else {
					return chapters.get(o1.getChapterid()).getSort() - chapters.get(o2.getChapterid()).getSort();
				}
			}
		});
		return hours;
	}

}
