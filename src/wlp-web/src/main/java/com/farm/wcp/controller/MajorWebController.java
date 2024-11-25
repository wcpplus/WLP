package com.farm.wcp.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.learn.dao.MajorchapterDaoInter;
import com.farm.learn.domain.ClassChapter;
import com.farm.learn.domain.ClassHour;
import com.farm.learn.domain.Classt;
import com.farm.learn.domain.Major;
import com.farm.learn.domain.MajorChapter;
import com.farm.learn.domain.MajorClass;
import com.farm.learn.domain.UserClass;
import com.farm.learn.domain.ex.ClassLearnView;
import com.farm.learn.domain.ex.ClassView;
import com.farm.learn.domain.ex.MajorView;
import com.farm.learn.service.ClasschapterServiceInter;
import com.farm.learn.service.ClasshourServiceInter;
import com.farm.learn.service.ClasstServiceInter;
import com.farm.learn.service.MajorServiceInter;
import com.farm.learn.service.MajorchapterServiceInter;
import com.farm.learn.service.MajorclassServiceInter;
import com.farm.learn.service.MajorpopServiceInter;
import com.farm.learn.service.TopServiceInter;
import com.farm.learn.service.UserPopServiceInter;
import com.farm.learn.service.UserclassServiceInter;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.social.domain.FavoriteObj;
import com.farm.social.service.FavoriteobjServiceInter;
import com.farm.web.WebUtils;
import com.wcp.question.service.QuestionServiceInter;

/**
 * 专业
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/majorweb")
@Controller
public class MajorWebController extends WebUtils {
	@Resource
	private QuestionServiceInter questionServiceImpl;
	private static final Logger log = Logger.getLogger(MajorWebController.class);
	@Resource
	private WdapFileServiceInter wdapFileServiceImpl;
	@Resource
	private UserclassServiceInter userClassServiceImpl;
	@Resource
	private MajorServiceInter majorServiceImpl;
	@Resource
	private MajorclassServiceInter majorClassServiceImpl;
	@Resource
	private MajorchapterServiceInter majorChapterServiceImpl;
	@Resource
	private MajorpopServiceInter majorPopServiceImpl;
	@Resource
	private FavoriteobjServiceInter favoriteObjServiceImpl;

	/**
	 * 全部课程查看
	 * 
	 * @return
	 */
	@RequestMapping("/Puball")
	public ModelAndView Puball(Integer page, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			DataQuery query = DataQuery.getInstance();
			query.addSort(new DBSort("SORT", "ASC"));
			query.setPagesize(10);
			query.setCurrentPage(page == null ? 1 : page);
			DataResult result = majorServiceImpl.searchMajorsQuery(query, getCurrentUser(session));
			return view.putAttr("majors", result).returnModelAndView("web-simple/majorall/all-major");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/**
	 * 课程查看
	 * 
	 * @return
	 */
	@RequestMapping("/Pubview")
	public ModelAndView view(String majorid, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			if (!majorPopServiceImpl.isReadAble(majorid, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无查看权限!");
			}
			MajorView majorview = majorServiceImpl.getMajorView(majorid);
			userClassServiceImpl.bingUserClasses(majorview, getCurrentUser(session));
			boolean isFavorite = favoriteObjServiceImpl.isFavorite(majorid, getCurrentUser(session));
			FavoriteObj favoriteObj = favoriteObjServiceImpl.loadFavoriteObj(majorid, "1");
			view.putAttr("favoriteObj", favoriteObj);
			view.putAttr("isFavorite", isFavorite);
			view.putAttr("majorview", majorview);
			return view.returnModelAndView("web-simple/majorform/major-view-home");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/**
	 * 发布专业
	 * 
	 * @param classid
	 * @param session
	 * @return
	 */
	@RequestMapping("/public")
	public ModelAndView publicMajor(String majorid, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			if (!majorPopServiceImpl.isEditMajor(majorid, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			majorServiceImpl.publicMajor(majorid, getCurrentUser(session));
			return view.returnRedirectUrl("/userspace/tempmajor.do");
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
	public Map<String, Object> tempClass(String majorid, HttpSession session) {
		try {
			if (!majorPopServiceImpl.isEditMajor(majorid, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			majorServiceImpl.tempMajor(majorid, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 专业編輯
	 * 
	 * @return
	 */
	@RequestMapping("/mng")
	public ModelAndView mng(String majorid, String type, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			if (!majorPopServiceImpl.isEditMajor(majorid, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			MajorView majorview = majorServiceImpl.getMajorView(majorid);
			view.putAttr("majorview", majorview);
			if (type == null || type.equals("base")) {
				return view.returnModelAndView("web-simple/majorform/major-form-base");
			}
			if (type.equals("chapter")) {
				List<MajorChapter> chapters = majorChapterServiceImpl.getChapters(majorid);
				List<MajorClass> classes = majorClassServiceImpl.getClassesByMajor(majorid);
				return view.putAttr("chapters", chapters).putAttr("classes", classes)
						.returnModelAndView("web-simple/majorform/major-form-chapter");
			}
			return view.returnModelAndView("web-simple/majorform/major-form-base");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/**
	 * 提交base数据
	 * 
	 * @return
	 */
	@RequestMapping("/savebase")
	public ModelAndView addSubmit(Major entity, String classtypeid, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			if (!majorPopServiceImpl.isEditMajor(entity.getId(), getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			entity = majorServiceImpl.editMajorEntity(entity, getCurrentUser(session));
			return view.returnRedirectUrl("/majorweb/mng.do?majorid=" + entity.getId());
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
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
			MajorChapter chapter = majorChapterServiceImpl.getMajorchapterEntity(chapterid);
			return ViewMode.getInstance().putAttr("entity", chapter).returnObjMode();

		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/addClasst")
	@ResponseBody
	public Map<String, Object> addClasst(String classtid, String chapterid, HttpSession session) {
		try {
			majorChapterServiceImpl.addClasst(chapterid, classtid);
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交学习步骤
	 * 
	 * @return
	 */
	@RequestMapping("/savechapter")
	@ResponseBody
	public Map<String, Object> editSubmit(MajorChapter entity, HttpSession session) {
		try {
			if (!majorPopServiceImpl.isEditMajor(entity.getMajorid(), getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			if (StringUtils.isNotBlank(entity.getId())) {
				entity = majorChapterServiceImpl.editMajorchapterEntity(entity, getCurrentUser(session));
			} else {
				entity = majorChapterServiceImpl.insertMajorchapterEntity(entity, getCurrentUser(session));
			}
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
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
			MajorChapter chapter = majorChapterServiceImpl.getMajorchapterEntity(chapterid);
			if (!majorPopServiceImpl.isEditMajor(chapter.getMajorid(), getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			majorChapterServiceImpl.deleteMajorchapterEntity(chapterid, getCurrentUser(session));
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
	@RequestMapping("/delclasst")
	@ResponseBody
	public Map<String, Object> delclasst(String majorclassId, HttpSession session) {
		try {
			MajorClass mclass = majorClassServiceImpl.getMajorclassEntity(majorclassId);
			if (!majorPopServiceImpl.isEditMajor(mclass.getMajorid(), getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			majorClassServiceImpl.deleteMajorclassEntity(majorclassId, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}
}
