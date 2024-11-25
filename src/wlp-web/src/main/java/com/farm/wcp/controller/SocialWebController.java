package com.farm.wcp.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.farm.authority.FarmAuthorityService;
import com.farm.authority.domain.User;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;
import com.farm.learn.service.MajorServiceInter;
import com.farm.learn.service.MajorchapterServiceInter;
import com.farm.learn.service.MajorclassServiceInter;
import com.farm.learn.service.MajorpopServiceInter;
import com.farm.learn.service.UserclassServiceInter;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.sfile.enums.FileModel;
import com.farm.social.domain.CommentsUser;
import com.farm.social.domain.EvaluationObj;
import com.farm.social.service.CommentsobjServiceInter;
import com.farm.social.service.EvaluationobjServiceInter;
import com.farm.social.service.FavoriteobjServiceInter;
import com.farm.util.web.HtmlUtils;
import com.farm.web.WebUtils;
import com.wcp.question.service.QuestionServiceInter;

/**
 * 社交功能
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/socialweb")
@Controller
public class SocialWebController extends WebUtils {
	@Resource
	private QuestionServiceInter questionServiceImpl;
	private static final Logger log = Logger.getLogger(SocialWebController.class);
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
	@Resource
	private CommentsobjServiceInter commentsObjServiceImpl;
	@Resource
	private EvaluationobjServiceInter evaluationObjServiceImpl;

	/**
	 * 收藏
	 * 
	 * @return
	 */
	@RequestMapping("/favorite")
	@ResponseBody
	public Map<String, Object> favorite(String appid, String apptype, HttpSession session) {
		try {
			favoriteObjServiceImpl.favorite(appid, apptype, getCurrentUser(session));

			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 取消收藏
	 * 
	 * @return
	 */
	@RequestMapping("/unfavorite")
	@ResponseBody
	public Map<String, Object> unfavorite(String appid, String apptype, HttpSession session) {
		try {
			favoriteObjServiceImpl.unFavorite(appid, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 发布评论
	 * 
	 * @param appid
	 * @param apptype
	 * @param parentid
	 * @param comments
	 * @param session
	 * @return
	 */
	@RequestMapping("/submitComments")
	@ResponseBody
	public Map<String, Object> submitComments(String appid, String apptype, String parentid, String comments,
			HttpSession session) {
		try {
			commentsObjServiceImpl.publicComments(appid, apptype, parentid, comments, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 加载评论(可多个appid用逗号分隔)
	 * 
	 * @param appid
	 * @param apptype
	 * @param session
	 * @return
	 */
	@RequestMapping("/PubloadComments")
	@ResponseBody
	public Map<String, Object> loadComments(String appids, Integer page, HttpSession session) {
		try {
			if (page == null) {
				page = 1;
			}
			DataResult result = commentsObjServiceImpl.loadComments(appids, page, 5);
			result.runformatTime("CTIME", "yyyy-MM-dd HH:mm");
			result.runHandle(new ResultsHandle() {
				@Override
				public void handle(Map<String, Object> row) {
					User user = (User) FarmAuthorityService.getInstance().getUserById((String) row.get("USERID"));
					row.put("IMGURL", wdapFileServiceImpl.getDownloadUrl(user.getImgid(), FileModel.PHOTO));
					row.put("user", user);
					row.put("NOTE", HtmlUtils.htmlEncode((String) row.get("NOTE")));
					if (row.get("PARENTID") != null) {
						CommentsUser parent = commentsObjServiceImpl.getUserComments((String) row.get("PARENTID"));
						if (parent != null) {
							row.put("parent", parent);
							row.put("parentNote", HtmlUtils.htmlEncode(parent.getNote()));
						}
					}
				}
			});
			return ViewMode.getInstance().putAttr("result", result).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 发布评价
	 * 
	 * @param appid
	 * @param apptype
	 * @param parentid
	 * @param comments
	 * @param session
	 * @return
	 */
	@RequestMapping("/submitEvaluation")
	@ResponseBody
	public Map<String, Object> submitEvaluation(String appid, String apptype, Integer score, String comments,
			HttpSession session) {
		try {
			evaluationObjServiceImpl.publicEvaluation(appid, apptype, score, comments, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 加载评价
	 * 
	 * @param appid
	 * @param apptype
	 * @param session
	 * @return
	 */
	@RequestMapping("/PubloadEvaluation")
	@ResponseBody
	public Map<String, Object> loadEvaluation(String appid, Integer page, HttpSession session) {
		try {
			if (page == null) {
				page = 1;
			}
			DataResult result = evaluationObjServiceImpl.loadEvaluation(appid, page, 10);
			result.runformatTime("CTIME", "yyyy-MM-dd HH:mm");
			result.runHandle(new ResultsHandle() {
				@Override
				public void handle(Map<String, Object> row) {
					User user = (User) FarmAuthorityService.getInstance().getUserById((String) row.get("USERID"));
					row.put("IMGURL", wdapFileServiceImpl.getDownloadUrl(user.getImgid(), FileModel.PHOTO));
					row.put("user", user);
					row.put("NOTE", HtmlUtils.htmlEncode((String) row.get("NOTE")));
				}
			});
			EvaluationObj obj = new EvaluationObj();
			if (result.getResultList().size() > 0) {
				obj = evaluationObjServiceImpl
						.getEvaluationobjEntity((String) result.getResultList().get(0).get("OBJID"));
			} else {
				obj.setScore(0f);
			}
			return ViewMode.getInstance().putAttr("result", result).putAttr("OBJ", obj).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}
}
