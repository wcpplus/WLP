package com.farm.learn.service.impl;

import com.farm.learn.domain.ClassChapter;
import com.farm.learn.domain.ClassClassType;
import com.farm.learn.domain.ClassHour;
import com.farm.learn.domain.ClassTag;
import com.farm.learn.domain.Classt;
import com.farm.learn.domain.ex.ClassView;
import com.farm.learn.domain.ex.HourView;
import com.farm.core.time.TimeTool;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.farm.learn.dao.ClasschapterDaoInter;
import com.farm.learn.dao.ClassclasstypeDaoInter;
import com.farm.learn.dao.ClasshourDaoInter;
import com.farm.learn.dao.ClasstDaoInter;
import com.farm.learn.dao.ClasstagDaoInter;
import com.farm.learn.dao.UserclassDaoInter;
import com.farm.learn.service.ClasschapterServiceInter;
import com.farm.learn.service.ClasshourServiceInter;
import com.farm.learn.service.ClasstServiceInter;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.sfile.domain.FileBase;
import com.farm.sfile.enums.FileModel;
import com.farm.sfile.exception.FileExNameException;
import com.farm.sfile.utils.FarmDocFiles;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.farm.category.domain.ClassType;
import com.farm.category.domain.Tag;
import com.farm.category.domain.TagType;
import com.farm.category.enums.FuncPOP;
import com.farm.category.service.ClasstypeServiceInter;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：课程服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明： 
 */
@Service
public class ClasstServiceImpl implements ClasstServiceInter {
	@Resource
	private ClasstDaoInter classtDaoImpl;
	@Resource
	private WdapFileServiceInter wdapFileServiceImpl;
	@Resource
	private ClasschapterDaoInter classchapterDaoImpl;
	@Resource
	private ClasshourDaoInter classhourDaoImpl;
	@Resource
	private ClasschapterServiceInter classChapterServiceImpl;
	@Resource
	private ClasshourServiceInter classHourServiceImpl;
	@Resource
	private ClassclasstypeDaoInter classclasstypeDaoImpl;
	@Resource
	private ClasstypeServiceInter classTypeServiceImpl;
	@Resource
	private ClasstagDaoInter classtagDaoImpl;
	@Resource
	private UserclassDaoInter userclassDaoImpl;
	private static final Logger log = Logger.getLogger(ClasstServiceImpl.class);

	@Override
	@Transactional
	public Classt insertClasstEntity(Classt entity, String classtypeid, LoginUser user) throws FileNotFoundException {
		if (StringUtils.isBlank(classtypeid)) {
			throw new RuntimeException("the classTypeid is not exist!");
		}
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(user.getName());
		entity.setEuser(user.getId());
		entity.setEusername(user.getName());
		entity.setEtime(TimeTool.getTimeDate14());
		entity.setPstate("0");
		if (entity.getAltime() == null) {
			entity.setAltime(0);
		}
		entity.setBookednum(0);
		entity.setLearnednum(0);
		entity.setEvaluation(0);
		entity.setHotscore(0);
		entity.setHournum(0);
		wdapFileServiceImpl.submitFile(entity.getImgid());
		entity = classtDaoImpl.insertEntity(entity);
		ClassClassType ctype = new ClassClassType();
		ctype.setClassid(entity.getId());
		ctype.setClasstypeid(classtypeid);
		classclasstypeDaoImpl.insertEntity(ctype);
		return entity;
	}

	@Override
	@Transactional
	public Classt editClasstEntity(Classt entity, String classtypeid, LoginUser user) throws FileNotFoundException {
		Classt entity2 = classtDaoImpl.getEntity(entity.getId());
		entity2.setEuser(user.getId());
		entity2.setEusername(user.getName());
		entity2.setEtime(TimeTool.getTimeDate14());
		if (entity.getIntroductionid() != null) {
			entity2.setIntroductionid(entity.getIntroductionid());
		}
		if (entity.getName() != null) {
			entity2.setName(entity.getName());
		}

		if (entity.getDifficulty() != null) {
			entity2.setDifficulty(entity.getDifficulty());
		}
		if (entity.getShortintro() != null) {
			entity2.setShortintro(entity.getShortintro());
		}
		if (entity.getOutauthor() != null) {
			entity2.setOutauthor(entity.getOutauthor());
		}
		if (entity.getAltime() != null) {
			entity2.setAltime(entity.getAltime());
		}
		if (entity.getEvaluation() != null) {
			entity2.setEvaluation(entity.getEvaluation());
		}
		if (entity.getLearnednum() != null) {
			entity2.setLearnednum(entity.getLearnednum());
		}
		if (entity.getImgid() != null) {
			wdapFileServiceImpl.freeFile(entity2.getImgid());
			entity2.setImgid(entity.getImgid());
			wdapFileServiceImpl.submitFile(entity.getImgid());
		}
		if (entity.getMinimgid() != null) {
			entity2.setMinimgid(entity.getMinimgid());
		}
		if (entity.getBookednum() != null) {
			entity2.setBookednum(entity.getBookednum());
		}
		if (entity.getHotscore() != null) {
			entity2.setHotscore(entity.getHotscore());
		}
		// entity2.setPcontent(entity.getPcontent());
		classtDaoImpl.editEntity(entity2);
		{
			classclasstypeDaoImpl
					.deleteEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", entity.getId(), "=")).toList());
			ClassClassType ctype = new ClassClassType();
			ctype.setClassid(entity.getId());
			ctype.setClasstypeid(classtypeid);
			classclasstypeDaoImpl.insertEntity(ctype);
		}
		return entity2;
	}

	@Override
	@Transactional
	public void deleteClasstEntity(String id, LoginUser user) {
		List<ClassHour> hours = classhourDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", id, "=")).toList());
		for (ClassHour hour : hours) {
			if (StringUtils.isNotBlank(hour.getFileid())) {
				wdapFileServiceImpl.freeFile(hour.getFileid());
			}
		}
		classhourDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", id, "=")).toList());
		classchapterDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", id, "=")).toList());
		Classt classt = classtDaoImpl.getEntity(id);
		if (StringUtils.isNotBlank(classt.getImgid())) {
			wdapFileServiceImpl.freeFile(classt.getImgid());
		}
		if (StringUtils.isNotBlank(classt.getMinimgid())) {
			wdapFileServiceImpl.freeFile(classt.getMinimgid());
		}
		classclasstypeDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", id, "=")).toList());
		classtDaoImpl.deleteEntity(classt);
	}

	@Override
	@Transactional
	public Classt getClasstEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return classtDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createClasstSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "WLP_L_CLASS a left join WLP_L_CLASSCLASSTYPE b on b.CLASSID=a.id left join WLP_C_CLASSTYPE c on c.id=b.CLASSTYPEID",
				"a.ID as ID,a.INTRODUCTIONID as INTRODUCTIONID,a.NAME as NAME,a.HOURNUM as HOURNUM,a.DIFFICULTY as DIFFICULTY,a.SHORTINTRO as SHORTINTRO,a.OUTAUTHOR as OUTAUTHOR,a.ALTIME as ALTIME,a.EVALUATION as EVALUATION,a.LEARNEDNUM as LEARNEDNUM,a.IMGID as IMGID,a.MINIMGID as MINIMGID,a.BOOKEDNUM as BOOKEDNUM,a.HOTSCORE as HOTSCORE,a.PSTATE as PSTATE,a.PCONTENT as PCONTENT,a.CUSERNAME as CUSERNAME,a.CUSER as CUSER,a.CTIME as CTIME,a.EUSERNAME as EUSERNAME,a.EUSER as EUSER,a.ETIME as ETIME,c.NAME as TYPENAME");
		return dbQuery;
	}

	@Override
	@Transactional
	public DataResult getTempClases(DataQuery query, LoginUser currentUser) throws SQLException {
		// query.addRule(new DBRule("CUSER", currentUser.getId(), "="));
		query.addRule(new DBRule("PSTATE", "0", "="));
		query.addSort(new DBSort("ETIME", "DESC"));
		DataQuery dbQuery = getClassListQuery(query, currentUser, FuncPOP.write);
		DataResult result = dbQuery.search();
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
	public DataResult getPublicClases(DataQuery query, LoginUser currentUser) throws SQLException {
		// query.addRule(new DBRule("CUSER", currentUser.getId(), "="));
		query.addRule(new DBRule("PSTATE", "1", "="));
		query.addSort(new DBSort("ETIME", "DESC"));
		DataQuery dbQuery = getClassListQuery(query, currentUser, FuncPOP.write);
		DataResult result = dbQuery.search();
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
	public ClassView getClassView(String classid) {
		ClassView classv = new ClassView();
		classv.setClasst(classtDaoImpl.getEntity(classid));
		String url = wdapFileServiceImpl.getDownloadUrl(classv.getClasst().getImgid(), FileModel.IMG);
		classv.setImgurl(url);
		if (StringUtils.isNotBlank(classv.getClasst().getIntroductionid())) {
			classv.setIntroText(wdapFileServiceImpl.readFileToText(classv.getClasst().getIntroductionid()));
		}
		List<ClassChapter> chapters = classChapterServiceImpl.getChapters(classid);
		List<ClassHour> hours = classHourServiceImpl.getHoursByClass(classid);
		classv.setChapters(chapters);
		classv.setHours(hours);
		List<ClassClassType> classtypes = classclasstypeDaoImpl.getEntityByClassId(classid);
		if (classtypes.size() > 0) {
			ClassType type = classTypeServiceImpl.getClasstypeEntity(classtypes.get(0).getClasstypeid());
			classv.setType(type);
			classv.setTypes(classTypeServiceImpl.getAllPathType(type));
		}
		// 查询tag存入课程中
		classv.setTags(classtDaoImpl.getClassTags(classid));
		return classv;
	}

	@Override
	@Transactional
	public Classt editIntroduction(String classid, String text, LoginUser currentUser) throws FileNotFoundException {
		Classt classt = classtDaoImpl.getEntity(classid);
		// 判斷是已經創建簡介
		FileBase filebase = null;
		if (StringUtils.isBlank(classt.getIntroductionid())
				|| wdapFileServiceImpl.getFileBase(classt.getIntroductionid()) == null) {
			// false：創建簡介
			filebase = wdapFileServiceImpl.initFile(currentUser, classt.getName() + "简介.html", text.length(),
					classt.getId(), "课程简介");
			classt.setIntroductionid(filebase.getId());
			classtDaoImpl.editEntity(classt);
		} else {
			filebase = wdapFileServiceImpl.getFileBase(classt.getIntroductionid());
		}
		{// 提交或取消附件
			String oldText = wdapFileServiceImpl.readFileToText(filebase.getId());
			List<String> oldFiles = FarmDocFiles.getFilesIdFromHtml(oldText);
			for (String fileid : oldFiles) {
				wdapFileServiceImpl.freeFile(fileid);
			}
			List<String> newFiles = FarmDocFiles.getFilesIdFromHtml(text);
			for (String fileid : newFiles) {
				wdapFileServiceImpl.submitFile(fileid);
			}
		}
		// 保存簡介
		wdapFileServiceImpl.writeFileByText(filebase.getId(), text);
		wdapFileServiceImpl.submitFile(filebase.getId());
		return classt;
	}

	@Override
	@Transactional
	public void publicClass(String classid, LoginUser currentUser) {
		List<ClassHour> hous = classHourServiceImpl.getHoursByClass(classid);
		int allTime = 0;
		for (ClassHour hour : hous) {
			allTime = allTime + hour.getAltime();
		}
		Classt Classt = classtDaoImpl.getEntity(classid);
		Classt.setPstate("1");
		Classt.setEtime(TimeTool.getTimeDate14());
		Classt.setAltime(allTime);
		Classt.setHournum(hous.size());
		classtDaoImpl.editEntity(Classt);
		classTypeServiceImpl.refreshNum(getTypeId(classid));
	}

	@Override
	@Transactional
	public void tempClass(String classid, LoginUser currentUser) {
		Classt Classt = classtDaoImpl.getEntity(classid);
		Classt.setPstate("0");
		classtDaoImpl.editEntity(Classt);
		classTypeServiceImpl.refreshNum(getTypeId(classid));
	}

	@Override
	@Transactional
	public DataResult getNewClasses(LoginUser currentUser, int num) throws SQLException {
		DataQuery query = DataQuery.getInstance();
		query.addRule(new DBRule("PSTATE", "1", "="));
		query.addSort(new DBSort("ETIME", "DESC"));
		query.setPagesize(num);
		query.setNoCount();
		DataQuery dbQuery = getClassListQuery(query, currentUser, FuncPOP.read);
		DataResult result = dbQuery.search();
		result.runformatTime("ETIME", "yyyy年MM月dd日");
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
					row.put("TYPE", type);
				}
			}
		});
		return result;
	}

	private DataQuery getClassListQuery(DataQuery query, LoginUser currentUser, FuncPOP funcpop) {
		query.addSqlRule("and B.CLASSTYPEID in ("
				+ DataQuerys.getWhereInSubVals(classTypeServiceImpl.getUserAllTypeIds(currentUser, funcpop)) + ")");
		query.setDistinct(true);
		DataQuery dbQuery = DataQuery.init(query,
				"WLP_L_CLASS a left join WLP_L_CLASSCLASSTYPE b on a.id=b.CLASSID left join WLP_L_CLASSTAG c on c.classid=a.id",
				"a.ID AS ID,a.etime as ETIME, a.INTRODUCTIONID as INTRODUCTIONID, a.NAME as NAME, a.DIFFICULTY as DIFFICULTY, a.SHORTINTRO as SHORTINTRO, a.OUTAUTHOR as OUTAUTHOR, a.ALTIME as ALTIME, a.EVALUATION as EVALUATION, a.LEARNEDNUM as LEARNEDNUM, a.IMGID as IMGID, a.MINIMGID as MINIMGID, a.BOOKEDNUM as BOOKEDNUM, a.HOTSCORE as HOTSCORE, b.CLASSTYPEID as CLASSTYPEID");
		return dbQuery;
	}

	@Override
	@Transactional
	public DataResult getClasses(DataQuery query, LoginUser currentUser) throws SQLException {
		query.addRule(new DBRule("PSTATE", "1", "="));
		query.addSort(new DBSort("ETIME", "DESC"));
		query = getClassListQuery(query, currentUser, FuncPOP.read);
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
	public HourView getFirstHourView(String classid) throws FileExNameException {
		return loadPdfImgsInfo(getHours(classid).get(0));
	}

	@Override
	@Transactional
	public HourView getHourView(String hourid) throws FileExNameException {
		ClassHour hour = classhourDaoImpl.getEntity(hourid);
		List<HourView> hourviews = getHours(hour.getClassid());
		for (HourView view : hourviews) {
			if (view.getHour().getId().equals(hourid)) {
				return loadPdfImgsInfo(view);
			}
		}
		return loadPdfImgsInfo(getHours(hour.getClassid()).get(0));
	}

	private HourView loadPdfImgsInfo(HourView hourView) {
		FileBase filebase = wdapFileServiceImpl.getFileBase(hourView.getHour().getFileid());
		if (filebase.getExname().toUpperCase().equals("PDF")) {
			File imgsDir = PdfToImgConvertor
					.getImgDir(wdapFileServiceImpl.getPersistFile(hourView.getHour().getFileid()).getFile());
			if (imgsDir.exists()) {
				List<File> files = Arrays.asList(imgsDir.listFiles());
				hourView.setPinum(files.size());
			}
		}
		return hourView;
	}

	private List<HourView> getHours(String classid) throws FileExNameException {
		ClassView classview = getClassView(classid);
		List<ClassHour> hours = classview.getHours();
		if (hours.size() <= 0) {
			throw new RuntimeException(" hour is not exist by the class!");
		}
		List<HourView> hourviews = new ArrayList<>();
		Map<String, ClassChapter> chapterMap = new HashMap<String, ClassChapter>();
		for (ClassChapter chapter : classview.getChapters()) {
			chapterMap.put(chapter.getId(), chapter);
		}
		for (ClassHour hour : hours) {
			HourView view = new HourView();
			view.setHour(hour);
			view.setClassview(classview);
			view.setViewurl(wdapFileServiceImpl.getDownloadUrl(hour.getFileid(),
					wdapFileServiceImpl.getFileModel(hour.getFileid())));
			view.setFilebase(wdapFileServiceImpl.getFileBase(hour.getFileid()));
			ClassChapter cchapter = chapterMap.get(hour.getChapterid());
			if (cchapter != null) {
				// 有章節的課時數據完整才添加到結果中
				view.setChapter(cchapter);
				view.setHours(hourviews);
				hourviews.add(view);
			}
		}
		Collections.sort(hourviews, new Comparator<HourView>() {
			@Override
			public int compare(HourView o1, HourView o2) {
				if (o1.getChapter().getSort() == o2.getChapter().getSort()) {
					return o1.getHour().getSort() - o2.getHour().getSort();
				} else {
					return o1.getChapter().getSort() - o2.getChapter().getSort();
				}
			}
		});
		return hourviews;
	}

	@Override
	@Transactional
	public boolean isReadAble(String classid, LoginUser currentUser) {
		// 管理员可读取
		if (currentUser != null && currentUser.getType().equals("3")) {
			return true;
		}
		List<ClassClassType> ctypes = classclasstypeDaoImpl.getEntityByClassId(classid);
		Set<String> ableTypeid = new HashSet<>();
		// 有查看权限可读
		ableTypeid.addAll(classTypeServiceImpl.getUserAllTypeIds(currentUser, FuncPOP.read));
		// 有編輯权限可读
		ableTypeid.addAll(classTypeServiceImpl.getUserAllTypeIds(currentUser, FuncPOP.write));
		for (ClassClassType node : ctypes) {
			if (ableTypeid.contains(node.getClasstypeid())) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public boolean isWriteAble(String classid, LoginUser currentUser) {
		// 管理员可編輯
		if (currentUser != null && currentUser.getType().equals("3")) {
			return true;
		}
		// 是否有分類編輯權限
		List<ClassClassType> ctypes = classclasstypeDaoImpl.getEntityByClassId(classid);
		Set<String> ableTypeid = new HashSet<>();
		ableTypeid.addAll(classTypeServiceImpl.getUserAllTypeIds(currentUser, FuncPOP.write));
		for (ClassClassType node : ctypes) {
			if (ableTypeid.contains(node.getClasstypeid())) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public void addTag(String classid, String tagid, LoginUser currentUser) {
		removeTag(classid, tagid, currentUser);
		ClassTag ctag = new ClassTag();
		ctag.setClassid(classid);
		ctag.setTagid(tagid);
		classtagDaoImpl.insertEntity(ctag);
	}

	@Override
	@Transactional
	public void removeTag(String classid, String tagid, LoginUser currentUser) {
		classtagDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", classid, "="))
				.add(new DBRule("TAGID", tagid, "=")).toList());
	}

	@Override
	@Transactional
	public List<TagType> getClassAllTags(List<TagType> allTags, String classid) {
		List<ClassTag> ctags = classtagDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", classid, "=")).toList());
		Set<String> ctagids = new HashSet<>();
		for (ClassTag ctag : ctags) {
			ctagids.add(ctag.getTagid());
		}
		for (TagType type : allTags) {
			for (Tag tag : type.getTags()) {
				if (ctagids.contains(tag.getId())) {
					tag.setCheck(true);
				}
			}
		}
		return allTags;
	}

	@Override
	@Transactional
	public List<Tag> getClassTags(String classid) {
		return classtDaoImpl.getClassTags(classid);
	}

	@Override
	@Transactional
	public String getTypeId(String classid) {
		List<ClassClassType> classtypes = classclasstypeDaoImpl.getEntityByClassId(classid);
		if (classtypes.size() > 0) {
			return classtypes.get(0).getClasstypeid();
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public ClassHour getFirstHour(String classid) {
		return getNextHour(classid, null, null);
	}

	@Override
	@Transactional
	public ClassHour getNextHour(String classid, String hourid, Set<String> completeHourids) {
		ClassView classview = getClassView(classid);
		List<ClassHour> hours = classview.getHours();
		if (hours.size() <= 0) {
			throw new RuntimeException(" hour is not exist by the class!");
		}
		List<HourView> hourviews = new ArrayList<>();
		Map<String, ClassChapter> chapterMap = new HashMap<String, ClassChapter>();
		for (ClassChapter chapter : classview.getChapters()) {
			chapterMap.put(chapter.getId(), chapter);
		}
		for (ClassHour hour : hours) {
			HourView view = new HourView();
			view.setHour(hour);
			view.setClassview(classview);
			ClassChapter cchapter = chapterMap.get(hour.getChapterid());
			if (cchapter != null) {
				// 有章節的課時數據完整才添加到結果中
				view.setChapter(cchapter);
				view.setHours(hourviews);
				hourviews.add(view);
			}
		}
		Collections.sort(hourviews, new Comparator<HourView>() {
			@Override
			public int compare(HourView o1, HourView o2) {
				if (o1.getChapter().getSort() == o2.getChapter().getSort()) {
					return o1.getHour().getSort() - o2.getHour().getSort();
				} else {
					return o1.getChapter().getSort() - o2.getChapter().getSort();
				}
			}
		});
		if (hourid == null) {
			// 无当前课时，直接返回第一个课时
			return hourviews.get(0).getHour();
		} else {
			// 第一轮查找当前课时之后为完成的课时
			boolean backAble = false;// 當課時大於當前課時時可以返回
			for (HourView view : hourviews) {
				if (backAble && (completeHourids == null || !completeHourids.contains(view.getHour().getId()))) {
					// 在当前课时之后，且未学习过的课时被返回
					return view.getHour();
				}
				if (view.getHour().getId().endsWith(hourid)) {
					// 找到當前課時
					backAble = true;
				}
			}
			// 第二轮查找未完成的课时
			for (HourView view : hourviews) {
				if (backAble && (completeHourids == null || !completeHourids.contains(view.getHour().getId()))) {
					// 未学习过的课时被返回
					return view.getHour();
				}
			}
			// 返回最后的课时
			return hourviews.get(hourviews.size() - 1).getHour();
		}
	}

	@Override
	@Transactional
	public int getAllHoursNum(String classid) {
		return classhourDaoImpl.getAllHoursNum(classid);
	}

	@Override
	@Transactional
	public void freshLearUserNum(String classid) {
		Classt classt = classtDaoImpl.getEntity(classid);
		int usernum = userclassDaoImpl
				.countEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", classid, "=")).toList());
		if (classt.getLearnednum() != usernum) {
			classt.setLearnednum(usernum);
			classtDaoImpl.editEntity(classt);
		}
	}

	@Override
	@Transactional
	public List<String> getHourids(String classtid) {
		List<ClassHour> list = classhourDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", classtid, "=")).toList());
		List<String> idList = new ArrayList<>();
		for (ClassHour hour : list) {
			idList.add(hour.getId());
		}
		return idList;
	}
}
