package com.farm.wcp.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.category.domain.TagType;
import com.farm.category.service.ClasstypeServiceInter;
import com.farm.category.service.TagServiceInter;
import com.farm.core.page.OperateType;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;
import com.farm.learn.domain.ClassChapter;
import com.farm.learn.domain.ClassHour;
import com.farm.learn.domain.Classt;
import com.farm.learn.domain.ex.ClassLearnView;
import com.farm.learn.domain.ex.ClassView;
import com.farm.learn.service.ClasschapterServiceInter;
import com.farm.learn.service.ClasshourServiceInter;
import com.farm.learn.service.ClasstServiceInter;
import com.farm.learn.service.TopServiceInter;
import com.farm.learn.service.UserPopServiceInter;
import com.farm.learn.service.UserclassServiceInter;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.sfile.domain.ex.PersistFile;
import com.farm.social.domain.AppbindObj;
import com.farm.social.domain.AppbindResource;
import com.farm.social.domain.Outsys;
import com.farm.social.domain.ex.ResourceView;
import com.farm.social.service.AppbindobjServiceInter;
import com.farm.social.service.OutsysServiceInter;
import com.farm.web.WebUtils;
import com.wcp.question.service.QuestionServiceInter;

/**
 * 课程创建
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/classweb")
@Controller
public class ClassWebController extends WebUtils {
	@Resource
	private QuestionServiceInter questionServiceImpl;
	private static final Logger log = Logger.getLogger(ClassWebController.class);
	@Resource
	private ClasstypeServiceInter classTypeServiceImpl;
	@Resource
	private ClasstServiceInter classTServiceImpl;
	@Resource
	private ClasschapterServiceInter classChapterServiceImpl;
	@Resource
	private ClasshourServiceInter classHourServiceImpl;
	@Resource
	private WdapFileServiceInter wdapFileServiceImpl;
	@Resource
	private TopServiceInter topServiceImpl;
	@Resource
	private UserPopServiceInter userPopServiceImpl;
	@Resource
	private TagServiceInter tagServiceImpl;
	@Resource
	private UserclassServiceInter userClassServiceImpl;
	@Resource
	private OutsysServiceInter outsysServiceImpl;
	@Resource
	private AppbindobjServiceInter appbindObjServiceImpl;

	/**
	 * 课程查看
	 * 
	 * @return
	 */
	@RequestMapping("/Pubview")
	public ModelAndView view(String classid, String type, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			if (!classTServiceImpl.isReadAble(classid, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无学习权限!");
			}
			ClassView classview = classTServiceImpl.getClassView(classid);
			ClassLearnView learnView = userClassServiceImpl.getClassLearnView(classid, getCurrentUser(session));
			classview.bind(learnView);
			view.putAttr("learnView", learnView);
			view.putAttr("classview", classview);
			view.putAttr("writeAble", classTServiceImpl.isWriteAble(classid, getCurrentUser(session)));
			List<ResourceView> resources = appbindObjServiceImpl.getAppResource(classid);
			view.putAttr("resources", resources);
			if (StringUtils.isNotBlank(type) && type.equals("chapter")) {
				return view.returnModelAndView("web-simple/classform/class-view-chapter");
			}
			if (StringUtils.isNotBlank(type) && type.equals("userinfo")) {
				return view.returnModelAndView("web-simple/classform/class-view-userinfo");
			}
			if (StringUtils.isNotBlank(type) && type.equals("comments")) {
				return view.returnModelAndView("web-simple/classform/class-view-comments");
			}
			if (StringUtils.isNotBlank(type) && type.equals("evaluation")) {
				return view.returnModelAndView("web-simple/classform/class-view-evaluation");
			}
			if (StringUtils.isBlank(classview.getIntroText())) {
				return view.returnModelAndView("web-simple/classform/class-view-chapter");
			}
			return view.returnModelAndView("web-simple/classform/class-view-home");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/**
	 * 推荐阅读（置顶）查看
	 * 
	 * @return
	 */
	@RequestMapping("/PubTopview")
	public ModelAndView PubTopview(String topid, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			String url = topServiceImpl.getTopEntity(topid).getUrl();
			if (url.indexOf("://") > 0) {
				return view.returnRedirectUrl(url);
			}
			if (url.startsWith("/")) {
				return view.returnRedirectUrl(url);
			}
			return view.returnRedirectUrl("/" + url);
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/***
	 * 创建课程
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/create")
	public ModelAndView create(HttpServletRequest request, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		return view.returnModelAndView("web-simple/classform/class-form-base");
	}

	/**
	 * 提交课程base数据
	 * 
	 * @return
	 */
	@RequestMapping("/savebase")
	public ModelAndView addSubmit(Classt entity, String classtypeid, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {

			if (StringUtils.isNotBlank(entity.getId())) {
				if (!userPopServiceImpl.isEditClassByTypeid(classtypeid, getCurrentUser(session))) {
					throw new RuntimeException("当前用户无此权限!");
				}
				entity = classTServiceImpl.editClasstEntity(entity, classtypeid, getCurrentUser(session));
			} else {
				if (!userPopServiceImpl.isEditClassByTypeid(classtypeid, getCurrentUser(session))) {
					throw new RuntimeException("当前用户无此权限!");
				}
				entity = classTServiceImpl.insertClasstEntity(entity, classtypeid, getCurrentUser(session));
			}
			return view.returnRedirectUrl("/classweb/mng.do?classid=" + entity.getId());
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/**
	 * 提交课程章节
	 * 
	 * @return
	 */
	@RequestMapping("/savechapter")
	@ResponseBody
	public Map<String, Object> editSubmit(ClassChapter entity, HttpSession session) {
		try {
			if (!userPopServiceImpl.isEditClass(entity.getClassid(), getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			if (StringUtils.isNotBlank(entity.getId())) {
				entity = classChapterServiceImpl.editClasschapterEntity(entity, getCurrentUser(session));
			} else {
				entity = classChapterServiceImpl.insertClasschapterEntity(entity, getCurrentUser(session));
			}
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交课程课时
	 * 
	 * @return
	 */
	@RequestMapping("/savehour")
	@ResponseBody
	public Map<String, Object> savehour(ClassHour entity, HttpSession session) {
		try {
			ClassChapter chapter = classChapterServiceImpl.getClasschapterEntity(entity.getChapterid());
			if (!userPopServiceImpl.isEditClass(chapter.getClassid(), getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			if (StringUtils.isNotBlank(entity.getId())) {
				entity = classHourServiceImpl.editClasshourEntity(entity, getCurrentUser(session));
			} else {
				entity = classHourServiceImpl.insertClasshourEntity(entity, getCurrentUser(session));
			}
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/saveResource")
	@ResponseBody
	public Map<String, Object> saveResource(String id, String majorid, String classid, String hourid, String outsysid,
			String type, String url, HttpSession session) {
		try {
			if (!userPopServiceImpl.isEditClass(classid, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			AppbindResource entity = new AppbindResource();
			String appid = null;
			String apptype = null;
			// 1:专业,2:课程,3:课时
			if (StringUtils.isNotBlank(majorid)) {
				apptype = "1";
				appid = majorid;
			}
			if (StringUtils.isNotBlank(classid)) {
				apptype = "2";
				appid = classid;
			}
			if (StringUtils.isNotBlank(hourid)) {
				apptype = "3";
				appid = hourid;
			}
			if (StringUtils.isNotBlank(id)) {
				entity = appbindObjServiceImpl.getResource(id);
				entity = appbindObjServiceImpl.editResource(id, appid, apptype, url, type, outsysid,
						getCurrentUser(session));
			} else {
				entity = appbindObjServiceImpl.addResource(appid, apptype, url, type, outsysid,
						getCurrentUser(session));
			}
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 加载课程资源
	 * 
	 * @return
	 */
	@RequestMapping("/loadResource")
	@ResponseBody
	public Map<String, Object> loadResource(String resourceid, HttpSession session) {
		try {
			ViewMode view = ViewMode.getInstance();
			AppbindResource resource = appbindObjServiceImpl.getResource(resourceid);
			AppbindObj bindobj = appbindObjServiceImpl.getAppbindobjEntity(resource.getObjid());
			if (bindobj.getApptype().equals("1")) {
				view.putAttr("majorid", bindobj.getAppid());
			}
			if (bindobj.getApptype().equals("2")) {
				view.putAttr("classid", bindobj.getAppid());
			}
			if (bindobj.getApptype().equals("3")) {
				view.putAttr("hourid", bindobj.getAppid());
			}
			return view.putAttr("resource", resource).putAttr("bindobj", bindobj).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 课程的资源信息
	 * 
	 * @return
	 */
	@RequestMapping("/loadClassResources")
	@ResponseBody
	public Map<String, Object> loadClassResources(String classtid, Integer page, HttpSession session) {
		try {
			DataQuery query = DataQuery.getInstance();
			List<String> hourids = classTServiceImpl.getHourids(classtid);
			hourids.add(classtid);
			query.addSqlRule("and B.APPID in (" + DataQuerys.getWhereInSubVals(hourids) + ")");
			if (page == null) {
				page = 1;
			}
			query.addDefaultSort(new DBSort("A.CTIME", "DESC"));
			query.setPagesize(20);
			query.setCurrentPage(page);
			DataResult result = DataQuery
					.init(query,
							"WLP_S_APPBIND_RESOURCE a LEFT JOIN WLP_S_APPBIND_OBJ b ON a.OBJID = b.ID LEFT JOIN wlp_s_outsys c ON c.ID = a.SYSID LEFT JOIN WLP_L_CLASSHOUR d ON d.ID = b.APPID",
							"a.RESOURCETYPE as RESOURCETYPE,A.ID AS ID,a.RESOURCEURL as RESOURCEURL,c.SYSNAME as SYSNAME,c.BASEURL as BASEURL,b.APPTYPE as APPTYPE,d.TITLE as TITLE")
					.search();
			result.runDictionary("1:知识库,2:问答,3:测验,4:考试", "RESOURCETYPE");
			result.runDictionary("1:专业,2:课程,3:课时", "APPTYPE");
			result.runHandle(new ResultsHandle() {
				@Override
				public void handle(Map<String, Object> row) {
					row.put("URL", (String) row.get("BASEURL") + row.get("RESOURCEURL"));
				}
			});
			return ViewMode.getInstance().putAttr("result", result).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 删除课程资源
	 * 
	 * @return
	 */
	@RequestMapping("/delresource")
	@ResponseBody
	public Map<String, Object> delresource(String resourceid, String classid, HttpSession session) {
		try {
			if (!userPopServiceImpl.isEditClass(classid, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			appbindObjServiceImpl.deleteResource(resourceid, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 加载课程章节数据
	 * 
	 * @return
	 */
	@RequestMapping("/loadchapter")
	@ResponseBody
	public Map<String, Object> loadchapter(String chapterid, HttpSession session) {
		try {
			ClassChapter chapter = classChapterServiceImpl.getClasschapterEntity(chapterid);
			return ViewMode.getInstance().putAttr("entity", chapter).returnObjMode();

		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 加载课程课时数据
	 * 
	 * @return
	 */
	@RequestMapping("/loadhour")
	@ResponseBody
	public Map<String, Object> loadhour(String hourid, HttpSession session) {
		try {
			ClassHour hour = classHourServiceImpl.getClasshourEntity(hourid);
			PersistFile persist = wdapFileServiceImpl.getPersistFile(hour.getFileid());
			return ViewMode.getInstance().putAttr("entity", hour).putAttr("persist", persist).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 删除章节数据
	 * 
	 * @return
	 */
	@RequestMapping("/delchapter")
	@ResponseBody
	public Map<String, Object> delchapter(String chapterid, HttpSession session) {
		try {
			ClassChapter chapter = classChapterServiceImpl.getClasschapterEntity(chapterid);
			if (!userPopServiceImpl.isEditClass(chapter.getClassid(), getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			classChapterServiceImpl.deleteClasschapterEntity(chapterid, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 删除课时数据
	 * 
	 * @return
	 */
	@RequestMapping("/delhour")
	@ResponseBody
	public Map<String, Object> delhour(String hourid, HttpSession session) {
		try {
			ClassHour hour = classHourServiceImpl.getClasshourEntity(hourid);
			if (!userPopServiceImpl.isEditClass(hour.getClassid(), getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			classHourServiceImpl.deleteClasshourEntity(hourid, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 保存课程介绍
	 * 
	 * @param id
	 * @param text
	 * @param session
	 * @return
	 */
	@RequestMapping("/saveintroduction")
	public ModelAndView saveintroduction(String id, String text, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		Classt entity = null;
		try {
			if (!userPopServiceImpl.isEditClass(id, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			entity = classTServiceImpl.editIntroduction(id, text, getCurrentUser(session));
			return view.returnRedirectUrl("/classweb/mng.do?type=introduction&classid=" + entity.getId());
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/**
	 * 刪除课程
	 * 
	 * @return
	 */
	@RequestMapping("/del")
	public ModelAndView del(String classid, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			if (!userPopServiceImpl.isEditClass(classid, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			classTServiceImpl.deleteClasstEntity(classid, getCurrentUser(session));
			return view.returnRedirectUrl("/userspace/tempclass.do");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/**
	 * 发布课程
	 * 
	 * @param classid
	 * @param session
	 * @return
	 */
	@RequestMapping("/public")
	public ModelAndView publicClass(String classid, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			if (!userPopServiceImpl.isEditClass(classid, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			classTServiceImpl.publicClass(classid, getCurrentUser(session));
			return view.returnRedirectUrl("/userspace/tempclass.do");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/**
	 * 取消发布课程
	 * 
	 * @param classid
	 * @param session
	 * @return
	 */
	@RequestMapping("/temp")
	@ResponseBody
	public Map<String, Object> tempClass(String classid, HttpSession session) {
		try {
			if (!userPopServiceImpl.isEditClass(classid, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			classTServiceImpl.tempClass(classid, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 课程編輯
	 * 
	 * @return
	 */
	@RequestMapping("/mng")
	public ModelAndView mng(String classid, String type, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			if (!userPopServiceImpl.isEditClass(classid, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			ClassView classview = classTServiceImpl.getClassView(classid);
			view.putAttr("classview", classview);
			if (type == null || type.equals("base")) {
				return view.returnModelAndView("web-simple/classform/class-form-base");
			}
			if (type.equals("tags")) {
				// 加载一下系统标签
				List<TagType> tags = classTServiceImpl.getClassAllTags(tagServiceImpl.getAllTags(),
						classview.getClasst().getId());
				return view.putAttr("tags", tags).returnModelAndView("web-simple/classform/class-form-tags");
			}
			if (type.equals("introduction")) {
				return view.returnModelAndView("web-simple/classform/class-form-introduction");
			}
			if (type.equals("resource")) {
				// 查询全部课时
				List<ClassHour> hours = classHourServiceImpl.getSortHoursByClass(classid);
				List<Outsys> sysses = outsysServiceImpl.getAllSys();
				return view.putAttr("hours", hours).putAttr("sysses", sysses)
						.returnModelAndView("web-simple/classform/class-form-resource");
			}
			if (type.equals("chapter")) {
				List<ClassChapter> chapters = classChapterServiceImpl.getChapters(classid);
				List<ClassHour> hours = classHourServiceImpl.getHoursByClass(classid);
				return view.putAttr("chapters", chapters).putAttr("hours", hours)
						.returnModelAndView("web-simple/classform/class-form-chapter");
			}
			return view.returnModelAndView("web-simple/classform/class-form-base");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	@RequestMapping("/addTag")
	@ResponseBody
	public Map<String, Object> addTag(String classid, String tagid, HttpSession session) {
		try {
			if (!userPopServiceImpl.isEditClass(classid, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			classTServiceImpl.addTag(classid, tagid, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/removeTag")
	@ResponseBody
	public Map<String, Object> removeTag(String classid, String tagid, HttpSession session) {
		try {
			if (!userPopServiceImpl.isEditClass(classid, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			classTServiceImpl.removeTag(classid, tagid, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 课程的学员信息
	 * 
	 * @return
	 */
	@RequestMapping("/learUsers")
	@ResponseBody
	public Map<String, Object> learUsers(String classtid, Integer page, HttpSession session) {
		try {
			DataQuery query = DataQuery.getInstance();
			query.addRule(new DBRule("CLASSID", classtid, "="));
			if (page == null) {
				page = 1;
			}
			query.addDefaultSort(new DBSort("A.LTIME", "DESC"));
			query.setPagesize(20);
			query.setCurrentPage(page);
			DataResult result = userClassServiceImpl.learUsersQuery(query);
			result.runformatTime("STIME", "yyyy-MM-dd HH:mm");
			result.runformatTime("ETIME", "yyyy-MM-dd HH:mm");
			result.runformatTime("LTIME", "yyyy-MM-dd HH:mm");
			result.runDictionary("2:学习中,3:完成", "LEARNSTATE");
			return ViewMode.getInstance().putAttr("result", result).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 查询用户可见课程
	 * 
	 * @param word
	 * @param typeid
	 * @param page
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/PubClassSearch")
	@ResponseBody
	public Map<String, Object> classSearch(String word, String typeid, Integer page, HttpServletRequest request,
			HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			DataQuery query = DataQuery.getInstance();
			if (page == null) {
				page = 1;
			}
			if (StringUtils.isNotBlank(typeid)) {
				// 按照分類查詢
				List<String> subtypeids = classTypeServiceImpl.getSubTypeids(typeid);
				String subWere = DataQuerys.getWhereInSubVals(subtypeids);
				query.addSqlRule(" and b.CLASSTYPEID in (" + subWere + ")");
			}
			query.setPagesize(5);
			query.setCurrentPage(page);
			query.addRule(new DBRule("A.NAME", word, "like"));
			DataResult newClasses = classTServiceImpl.getClasses(query, getCurrentUser(session));
			return view.putAttr("newClasses", newClasses).returnObjMode();
		} catch (Exception e) {
			return view.returnObjMode();
		}
	}

}
