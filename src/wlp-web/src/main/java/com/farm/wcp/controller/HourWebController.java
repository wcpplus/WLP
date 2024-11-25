package com.farm.wcp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.farm.category.service.ClasstypeServiceInter;
import com.farm.core.page.ViewMode;
import com.farm.learn.domain.ex.ClassLearnView;
import com.farm.learn.domain.ex.HourView;
import com.farm.learn.service.ClasschapterServiceInter;
import com.farm.learn.service.ClasshourServiceInter;
import com.farm.learn.service.ClasstServiceInter;
import com.farm.learn.service.TopServiceInter;
import com.farm.learn.service.UserclassServiceInter;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.social.domain.ex.ResourceView;
import com.farm.social.service.AppbindobjServiceInter;
import com.farm.web.WebUtils;
import com.wcp.question.service.QuestionServiceInter;

/**
 * 课时创建
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/hourweb")
@Controller
public class HourWebController extends WebUtils {
	private static final Logger log = Logger.getLogger(HourWebController.class);
	@Resource
	private QuestionServiceInter questionServiceImpl;
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
	private UserclassServiceInter userClassServiceImpl;
	@Resource
	private AppbindobjServiceInter appbindObjServiceImpl;

	/**
	 * 继续学习
	 * 
	 * @return
	 */
	@RequestMapping("/PubContinue")
	public ModelAndView view(String classid, String hourid, String type, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			HourView hourview = null;
			if (!classTServiceImpl.isReadAble(classid, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无学习权限!");
			}
			if (getCurrentUser(session) == null) {
				// 未登錄
				if (StringUtils.isBlank(hourid)) {
					hourview = classTServiceImpl.getFirstHourView(classid);
				} else {
					hourview = classTServiceImpl.getHourView(hourid);
				}
			} else {
				if (type != null && type.equals("finish")) {
					hourid = userClassServiceImpl.finishCurrentHour(classid, getCurrentUser(session));
				}
				// 已登錄(继续学习课程)
				hourview = userClassServiceImpl.startLearnClass(classid, hourid, getCurrentUser(session));
				ClassLearnView learnView = userClassServiceImpl.getClassLearnView(classid, getCurrentUser(session));
				classTServiceImpl.freshLearUserNum(classid);
				hourview.getClassview().bind(learnView);
				view.putAttr("learnView", learnView);
			}
			List<ResourceView> resources = appbindObjServiceImpl.getAppResource(hourview.getHour().getId());
			view.putAttr("resources", resources);
			return view.putAttr("hourview", hourview).returnModelAndView("web-simple/hour/hour-view");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

}
