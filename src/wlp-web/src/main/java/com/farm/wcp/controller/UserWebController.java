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

import com.farm.authority.service.UserServiceInter;
import com.farm.category.domain.ClassType;
import com.farm.category.service.ClasstypeServiceInter;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;
import com.farm.learn.domain.Major;
import com.farm.learn.service.ClasstServiceInter;
import com.farm.learn.service.MajorServiceInter;
import com.farm.learn.service.UserclassServiceInter;
import com.farm.parameter.FarmParameterService;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.sfile.enums.FileModel;
import com.farm.social.service.FavoriteobjServiceInter;
import com.farm.web.WebUtils;
import com.wcp.question.service.QuestionServiceInter;

/**
 * 用户空间操作
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/userspace")
@Controller
public class UserWebController extends WebUtils {
	@Resource
	private QuestionServiceInter questionServiceImpl;
	private static final Logger log = Logger.getLogger(UserWebController.class);
	@Resource
	private ClasstypeServiceInter classTypeServiceImpl;
	@Resource
	private ClasstServiceInter classTServiceImpl;
	@Resource
	private UserServiceInter userServiceImpl;
	@Resource
	private WdapFileServiceInter wdapFileServiceImpl;
	@Resource
	private UserclassServiceInter userClassServiceImpl;
	@Resource
	private MajorServiceInter majorServiceImpl;
	@Resource
	private FavoriteobjServiceInter favoriteObjServiceImpl;

	/**
	 * 更新当前登录用户信息
	 * 
	 * @param id
	 *            用户ID
	 * @param name
	 *            用户名称
	 * @param photoid
	 *            头像ID
	 * @return ModelAndView
	 */
	@RequestMapping("/editCurrentUser")
	public ModelAndView editCurrentUser(String name, String photoid, HttpSession session) {
		try {
			// 修改用户信息
			LoginUser currentUser = getCurrentUser(session);
			String oldimgid = userServiceImpl.getUserEntity(currentUser.getId()).getImgid();
			wdapFileServiceImpl.freeFile(oldimgid);
			userServiceImpl.editCurrentUser(currentUser.getId(), name, photoid, null);
			wdapFileServiceImpl.submitFile(photoid);
			session.setAttribute("USEROBJ", userServiceImpl.getUserEntity(currentUser.getId()));
			return ViewMode.getInstance().putAttr("OK", "OK")
					.returnModelAndView("web-simple/userspace/user-setting-userinfo");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}

	}

	@RequestMapping("/editCurrentUserPwdCommit")
	public ModelAndView editCurrentUserPwdCommit(String password, String newPassword, String checkcode,
			HttpSession session, HttpServletRequest request) {
		try {
			// 是否启用登录验证码
			if (FarmParameterService.getInstance().getParameter("config.sys.verifycode.able").equals("true")) {
				boolean ischeck = checkcode.toUpperCase().equals(session.getAttribute("verCode"));
				session.removeAttribute("verCode");
				if (!ischeck) {
					throw new RuntimeException("验证码错误");
				}
			}
			userServiceImpl.editUserPassword(getCurrentUser(session).getId(), password, newPassword);
			return ViewMode.getInstance().putAttr("OK", "OK")
					.returnModelAndView("web-simple/userspace/user-setting-password");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e)
					.returnModelAndView("web-simple/userspace/user-setting-password");
		}

	}

	/***
	 * 未发布课程
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/tempclass")
	public ModelAndView index(HttpServletRequest request, Integer page, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			DataQuery query = DataQuery.getInstance();
			DataResult result;
			query.setPagesize(5);
			if (page != null) {
				query.setCurrentPage(page);
			}
			result = classTServiceImpl.getTempClases(query, getCurrentUser(session));
			return view.putAttr("result", result).returnModelAndView("web-simple/userspace/user-class-temp");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/***
	 * 已发布课程
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/publicclass")
	public ModelAndView publicclass(HttpServletRequest request, String classtype, Integer page, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			DataQuery query = DataQuery.getInstance();
			if (StringUtils.isNotBlank(classtype)) {
				ClassType ctype = classTypeServiceImpl.getClasstypeEntity(classtype);
				List<String> subtypeids = classTypeServiceImpl.getSubTypeids(classtype);
				String subWere = DataQuerys.getWhereInSubVals(subtypeids);
				query.addSqlRule(" and b.CLASSTYPEID in (" + subWere + ")");
				view.putAttr("ctype", ctype);
				view.putAttr("classtype", classtype);
			}
			DataResult result;
			query.setPagesize(5);
			if (page != null) {
				query.setCurrentPage(page);
			}
			result = classTServiceImpl.getPublicClases(query, getCurrentUser(session));
			return view.putAttr("result", result).returnModelAndView("web-simple/userspace/user-class-public");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/***
	 * 信息修改
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/settinginfo")
	public ModelAndView settinginfo(HttpServletRequest request, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			return view.returnModelAndView("web-simple/userspace/user-setting-userinfo");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/***
	 * 密码修改
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/settingpsd")
	public ModelAndView settingpsd(HttpServletRequest request, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			return view.returnModelAndView("web-simple/userspace/user-setting-password");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/***
	 * 查看学习中课程
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/currentClass")
	public ModelAndView currentClass(HttpServletRequest request, Integer page, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			DataQuery query = DataQuery.getInstance();
			query.addRule(new DBRule("a.PSTATE", "3", "!="));
			DataResult result;
			query.setPagesize(5);
			if (page != null) {
				query.setCurrentPage(page);
			}
			result = userClassServiceImpl.getUserClasses(query, getCurrentUser(session));
			loadUserClassToSession(session, userClassServiceImpl);
			return view.putAttr("result", result).returnModelAndView("web-simple/userspace/user-learn-current");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/**
	 * 将用户当前待学课程存入session中
	 * 
	 * @param session
	 * @param userClassService
	 */
	public static void loadUserClassToSession(HttpSession session, UserclassServiceInter userClassService) {
		if (getCurrentUser(session) != null) {
			session.setAttribute("USERLEARNNUM",
					userClassService.getUserCurrentClassNum(getCurrentUser(session).getId()));
		}
	}

	/***
	 * 查看学习完成课程
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/completeClass")
	public ModelAndView completeClass(HttpServletRequest request, Integer page, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			DataQuery query = DataQuery.getInstance();
			query.addRule(new DBRule("a.PSTATE", "3", "="));
			DataResult result;
			query.setPagesize(5);
			if (page != null) {
				query.setCurrentPage(page);
			}
			result = userClassServiceImpl.getUserClasses(query, getCurrentUser(session));
			return view.putAttr("result", result).returnModelAndView("web-simple/userspace/user-learn-complete");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/**
	 * 用户收藏的专业
	 * 
	 * @param request
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping("/favoriteMajor")
	public ModelAndView favoriteMajor(HttpServletRequest request, Integer page, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			DataQuery query = DataQuery.getInstance();
			query.addRule(new DBRule("a.USERID", getCurrentUser(session).getId(), "="));
			query.addRule(new DBRule("b.APPTYPE", "1", "="));
			query.setPagesize(5);
			if (page != null) {
				query.setCurrentPage(page);
			}
			query = favoriteObjServiceImpl.createUserFavoriteQuery(query);
			DataResult result = query.search();
			result.runHandle(new ResultsHandle() {
				@Override
				public void handle(Map<String, Object> row) {
					Major major = majorServiceImpl.getMajorEntity((String) row.get("APPID"));
					row.put("major", major);
					row.put("IMGURL", wdapFileServiceImpl.getDownloadUrl(major.getImgid(), FileModel.IMG));
				}
			});
			return view.putAttr("result", result).returnModelAndView("web-simple/userspace/user-learn-major");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/**
	 * 用户取消学习课程
	 * 
	 * @return
	 */
	@RequestMapping("/delLearnClass")
	@ResponseBody
	public Map<String, Object> delLearnClass(String classid, HttpSession session) {
		try {
			userClassServiceImpl.delUserClass(classid, getCurrentUser(session).getId());
			classTServiceImpl.freshLearUserNum(classid);
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/***
	 * 未发布专业
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/tempmajor")
	public ModelAndView tempMajor(HttpServletRequest request, Integer page, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			DataQuery query = DataQuery.getInstance();
			DataResult result;
			query.setPagesize(5);
			if (page != null) {
				query.setCurrentPage(page);
			}
			result = majorServiceImpl.getTempMajor(query, getCurrentUser(session));
			result.runformatTime("ETIME", " yyyy-MM-dd HH:mm");
			return view.putAttr("result", result).returnModelAndView("web-simple/userspace/user-major-temp");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/***
	 * 已发布专业
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/publicmajor")
	public ModelAndView publicMajor(HttpServletRequest request, Integer page, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			DataQuery query = DataQuery.getInstance();
			DataResult result;
			query.setPagesize(5);
			if (page != null) {
				query.setCurrentPage(page);
			}
			result = majorServiceImpl.getPublicMajor(query, getCurrentUser(session));
			return view.putAttr("result", result).returnModelAndView("web-simple/userspace/user-major-public");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}
}
