package com.farm.wcp.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.farm.category.domain.TagType;
import com.farm.category.service.ClasstypeServiceInter;
import com.farm.category.service.TagServiceInter;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;
import com.farm.learn.service.ClasshourServiceInter;
import com.farm.learn.service.ClasstServiceInter;
import com.farm.learn.service.MajorServiceInter;
import com.farm.web.WebUtils;
import com.wcp.question.service.QuestionServiceInter;

/**
 * 检索服务
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/search")
@Controller
public class SearchWebController extends WebUtils {
	@Resource
	private QuestionServiceInter questionServiceImpl;
	private static final Logger log = Logger.getLogger(SearchWebController.class);
	@Resource
	private ClasstypeServiceInter classTypeServiceImpl;
	@Resource
	private ClasstServiceInter classTServiceImpl;
	@Resource
	private TagServiceInter tagServiceImpl;
	@Resource
	private MajorServiceInter majorServiceImpl;
	@Resource
	private ClasshourServiceInter classHourServiceImpl;

	/***
	 * 课程检索
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/Pubsearch")
	public ModelAndView Pubsearch(final String word, String tagids, String model, Integer page, HttpSession session) {
		if (page == null) {
			page = 1;
		}
		ViewMode view = ViewMode.getInstance();
		view.putAttr("word", word).putAttr("page", page).putAttr("tagids", tagids).putAttr("model", model);
		DataResult result = null;
		try {
			// 检索专业
			if (StringUtils.isNotBlank(model) && model.equals("major")) {
				result = searchMajor(word, page, session);
				return view.putAttr("result", result).returnModelAndView("web-simple/search/search-major");
			}
			// 检索课时
			if (StringUtils.isNotBlank(model) && model.equals("hour")) {
				result = searchHour(word, page, session);
				return view.putAttr("result", result).returnModelAndView("web-simple/search/search-hour");
			}
			{// 检索课程
				List<TagType> tags = tagServiceImpl.getAllTags();
				view.putAttr("tags", tags);
				view.putAttr("tagids", tagids);
				result = searchClass(word, page, tagids, session);
				return view.putAttr("result", result).returnModelAndView("web-simple/search/search-class");
			}
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/**
	 * 检索专业
	 * 
	 * @param word
	 * @param page
	 * @param session
	 * @return
	 * @throws SQLException
	 */
	private DataResult searchMajor(final String word, Integer page, HttpSession session) throws SQLException {
		if (StringUtils.isBlank(word)) {
			return DataResult.getInstance();
		}
		DataQuery query = DataQuery.getInstance();
		query.setCurrentPage(page);
		query.setPagesize(10);
		query.addRule(new DBRule("a.TITLE", word, "like"));
		DataResult result = majorServiceImpl.searchMajorsQuery(query, getCurrentUser(session));
		result.runHandle(new ResultsHandle() {
			@Override
			public void handle(Map<String, Object> row) {
				row.put("TITLE",
						((String) row.get("TITLE")).replace(word, "<span class='wlp-mark-red'>" + word + "</span>"));
			}
		});
		return result;
	}

	/**
	 * 检索课时
	 * 
	 * @param word
	 * @param page
	 * @param session
	 * @return
	 * @throws SQLException
	 */
	private DataResult searchHour(final String word, Integer page, HttpSession session) throws SQLException {
		if (StringUtils.isBlank(word)) {
			return DataResult.getInstance();
		}
		DataQuery query = DataQuery.getInstance();
		query.setCurrentPage(page);
		query.setPagesize(10);
		query.addRule(new DBRule("d.TITLE", word, "like"));
		DataResult result = classHourServiceImpl.searchHoursQuery(query, getCurrentUser(session));
		result.runHandle(new ResultsHandle() {
			@Override
			public void handle(Map<String, Object> row) {
				row.put("TITLE",
						((String) row.get("TITLE")).replace(word, "<span class='wlp-mark-red'>" + word + "</span>"));
			}
		});
		return result;
	}

	/**
	 * 检索课程
	 * 
	 * @param word
	 * @param typeid
	 * @param page
	 * @param difficulty
	 * @param tagids
	 * @param session
	 * @return
	 * @throws SQLException
	 */
	private DataResult searchClass(final String word, Integer page, String tagids, HttpSession session)
			throws SQLException {
		if (StringUtils.isBlank(word) && StringUtils.isBlank(tagids)) {
			return DataResult.getInstance();
		}
		DataQuery query = DataQuery.getInstance();
		if (StringUtils.isNotBlank(tagids)) {
			String tagidSubWere = DataQuerys.getWhereInSubVals(parseIds(tagids));
			query.addSqlRule(" and c.tagid in (" + tagidSubWere + ")");
		}
		query.setPagesize(10);
		query.setCurrentPage(page);
		query.addRule(new DBRule("A.NAME", word, "like"));
		DataResult newClasses = classTServiceImpl.getClasses(query, getCurrentUser(session));
		newClasses.runHandle(new ResultsHandle() {
			@Override
			public void handle(Map<String, Object> row) {
				row.put("NAME",
						((String) row.get("NAME")).replace(word, "<span class='wlp-mark-red'>" + word + "</span>"));
				row.put("TAGS", classTServiceImpl.getClassTags((String) row.get("ID")));
			}
		});
		return newClasses;
	}

}
