package com.farm.social.controller;

import com.farm.social.domain.FavoriteObj;
import com.farm.social.domain.FavoriteUser;
import com.farm.social.service.FavoriteobjServiceInter;
import com.farm.social.service.FavoriteuserServiceInter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import com.farm.web.easyui.EasyUiUtils;
import java.util.Map;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import com.farm.core.page.RequestMode;
import com.farm.authority.FarmAuthorityService;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

/* *
 *功能：收藏对象控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/favoriteobj")
@Controller
public class FavoriteobjController extends WebUtils {
	private final static Logger log = Logger.getLogger(FavoriteobjController.class);
	@Resource
	private FavoriteobjServiceInter favoriteObjServiceImpl;
	@Resource
	private FavoriteuserServiceInter favoriteUserServiceImpl;

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			DataResult result = favoriteObjServiceImpl.createFavoriteobjSimpleQuery(query).search();
			result.runDictionary("1:专业,2:课程,3:课时", "APPTYPE");
			result.runformatTime("CTIME", "yyyy-MM-dd HH:mm");
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
	public Map<String, Object> editSubmit(FavoriteObj entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = favoriteObjServiceImpl.editFavoriteobjEntity(entity, getCurrentUser(session));
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
	public Map<String, Object> addSubmit(FavoriteObj entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = favoriteObjServiceImpl.insertFavoriteobjEntity(entity, getCurrentUser(session));
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
				FavoriteUser fuser = favoriteUserServiceImpl.getFavoriteuserEntity(id);
				FavoriteObj fobj = favoriteObjServiceImpl.getFavoriteobjEntity(fuser.getObjid());
				favoriteObjServiceImpl.unFavorite(fobj.getAppid(),
						FarmAuthorityService.getInstance().getUserById(fuser.getUserid()));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("social/FavoriteobjResult");
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
						.putAttr("entity", favoriteObjServiceImpl.getFavoriteobjEntity(ids))
						.returnModelAndView("social/FavoriteobjForm");
			}
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset).returnModelAndView("social/FavoriteobjForm");
			}
			case (2): {// 修改
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", favoriteObjServiceImpl.getFavoriteobjEntity(ids))
						.returnModelAndView("social/FavoriteobjForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("social/FavoriteobjForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e).returnModelAndView("social/FavoriteobjForm");
		}
	}
}
