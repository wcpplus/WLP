package com.farm.category.controller;

import com.farm.category.domain.ClassType;
import com.farm.category.domain.ClassTypePop;
import com.farm.category.enums.FuncPOP;
import com.farm.category.service.ClasstypeServiceInter;
import com.farm.category.service.ClasstypepopServiceInter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import com.farm.web.easyui.EasyUiUtils;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import com.farm.core.page.RequestMode;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

/* *
 *功能：课程分类权限控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/classtypepop")
@Controller
public class ClasstypepopController extends WebUtils {
	private final static Logger log = Logger.getLogger(ClasstypepopController.class);
	@Resource
	private ClasstypepopServiceInter classTypePopServiceImpl;
	@Resource
	private ClasstypeServiceInter classTypeServiceImpl;

	@RequestMapping("/delPop")
	@ResponseBody
	public Map<String, Object> delPop(String ids, HttpServletRequest request) {
		try {
			for (String id : parseIds(ids)) {
				classTypePopServiceImpl.delTypePop(id);
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/addOrgToPop")
	@ResponseBody
	public Map<String, Object> addOrgToPop(String orgid, String typeid, String type, HttpServletRequest request) {
		try {
			if (orgid == null || orgid.equals("NONE")) {
				throw new RuntimeException("请选择一个可用的组织机构");
			}
			for (String tid : parseIds(typeid)) {
				for (String id : parseIds(orgid)) {
					classTypePopServiceImpl.addOrgTypePop(tid, id, FuncPOP.getEnum(type));
				}
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/addPostToPop")
	@ResponseBody
	public Map<String, Object> addPostToPop(String postid, String typeid, String type, HttpServletRequest request) {
		try {
			for (String tid : parseIds(typeid)) {
				for (String id : parseIds(postid)) {
					classTypePopServiceImpl.addPostTypePop(tid, id, FuncPOP.getEnum(type));
				}
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 在分类权限中加入用户
	 * 
	 * @param userid
	 *            用户id
	 * @param typeid
	 *            分类id
	 * @param type
	 *            类型READ、WRITE、AUDIT
	 * @param request
	 * @return
	 */
	@RequestMapping("/addUserToPop")
	@ResponseBody
	public Map<String, Object> addUserToPop(String userid, String typeid, String type, HttpServletRequest request) {
		try {
			for (String tid : parseIds(typeid)) {
				for (String id : parseIds(userid)) {
					classTypePopServiceImpl.addUserTypePop(tid, id, FuncPOP.getEnum(type));
				}
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 查询分类权限规则
	 * 
	 * @param query
	 * @param id
	 * @param type
	 * @param request
	 * @return
	 */
	@RequestMapping("/SetTypePopQuery")
	@ResponseBody
	public Map<String, Object> SetTypePopQuery(DataQuery query, String id, String type, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			FuncPOP poptype = FuncPOP.getEnum(type);
			DataResult result = classTypePopServiceImpl.createClasstypepopSimpleQuery(query, poptype, id).search()
					.runDictionary("1:用户,2:组织机构,3:岗位", "POPTYPE").runDictionary("1:学习课程,2:维护课程", "FUNTYPE");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 预览权限设置
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/settingPage")
	public ModelAndView viewsetPage(String typeid, String functype, HttpSession session) {
		return getPopPageView(typeid, FuncPOP.getEnum(functype));
	}

	/**
	 * 配合readsetPage等方法，用来返回分类的页面变量集合
	 * 
	 * @param typeid
	 *            分类id
	 * @param popstate
	 *            权限类型
	 * @return
	 */
	private ModelAndView getPopPageView(String typeid, FuncPOP popstate) {
		List<String> ids = parseIds(typeid);
		String typename = null;
		String pop = null;
		String type = null;
		if (ids.size() > 1) {
			// 选择多个分类
			for (String id : ids) {
				ClassType entity = classTypeServiceImpl.getClasstypeEntity(id);
				typename = typename == null ? entity.getName() : typename + "," + entity.getName();
			}
			type = popstate.getVal();

		} else {
			// 选择一个分类
			ClassType entity = classTypeServiceImpl.getClasstypeEntity(typeid);
			pop = FuncPOP.getTypePopState(entity, popstate);
			typename = entity != null ? entity.getName() : null;
			type = popstate.getVal();
		}
		return ViewMode.getInstance().putAttr("id", typeid).putAttr("type", type).putAttr("typename", typename)
				.putAttr("extendmsg", pop).returnModelAndView("category/SetTypePopWin");
	}

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, HttpServletRequest request) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			DataResult result = null;// classTypePopServiceImpl.createClasstypepopSimpleQuery(query).search();
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交修改数据
	 * 
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> editSubmit(ClassTypePop entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = classTypePopServiceImpl.editClasstypepopEntity(entity, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();

		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交新增数据
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(ClassTypePop entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = classTypePopServiceImpl.insertClasstypepopEntity(entity, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.ADD).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.ADD).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 删除数据
	 * 
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public Map<String, Object> delSubmit(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				classTypePopServiceImpl.deleteClasstypepopEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("category/ClasstypepopResult");
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 *
	 * @return
	 */
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String ids) {
		try {
			switch (pageset.getOperateType()) {
			case (0): {// 查看
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", classTypePopServiceImpl.getClasstypepopEntity(ids))
						.returnModelAndView("category/ClasstypepopForm");
			}
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.returnModelAndView("category/ClasstypepopForm");
			}
			case (2): {// 修改
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", classTypePopServiceImpl.getClasstypepopEntity(ids))
						.returnModelAndView("category/ClasstypepopForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("category/ClasstypepopForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e)
					.returnModelAndView("category/ClasstypepopForm");
		}
	}
}
