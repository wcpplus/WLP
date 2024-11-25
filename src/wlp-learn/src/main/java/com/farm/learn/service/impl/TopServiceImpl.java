package com.farm.learn.service.impl;

import com.farm.learn.domain.Top;
import com.farm.core.time.TimeTool;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.farm.learn.dao.TopDaoInter;
import com.farm.learn.service.ClasstServiceInter;
import com.farm.learn.service.TopServiceInter;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.sfile.enums.FileModel;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.farm.category.enums.FuncPOP;
import com.farm.category.service.ClasstypeServiceInter;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：推荐阅读服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class TopServiceImpl implements TopServiceInter {
	@Resource
	private TopDaoInter topDaoImpl;
	@Resource
	private WdapFileServiceInter wdapFileServiceImpl;
	@Resource
	private ClasstypeServiceInter classTypeServiceImpl;
	@Resource
	private ClasstServiceInter classTServiceImpl;
	private static final Logger log = Logger.getLogger(TopServiceImpl.class);

	@Override
	@Transactional
	public Top insertTopEntity(Top entity, LoginUser user) throws FileNotFoundException {
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCuser(user.getId());
		entity.setCusername(user.getName());
		entity.setPstate("1");
		entity.setVisitnum(0);
		wdapFileServiceImpl.submitFile(entity.getImgid());
		return topDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public void updateSort(String topid, int i) {
		Top top = topDaoImpl.getEntity(topid);
		top.setSort(i);
		topDaoImpl.editEntity(top);
	}

	@Override
	@Transactional
	public Top editTopEntity(Top entity, LoginUser user) throws FileNotFoundException {
		Top entity2 = topDaoImpl.getEntity(entity.getId());
		entity2.setSort(entity.getSort());
		entity2.setPcontent(entity.getPcontent());
		entity2.setType(entity.getType());
		entity2.setTitle(entity.getTitle());
		entity2.setUrl(entity.getUrl());
		entity2.setPstate(entity.getPstate());
		entity2.setImgid(entity.getImgid());
		entity2.setAppid(entity.getAppid());
		entity2.setPcontent(entity.getPcontent());
		entity2.setModel(entity.getModel());
		wdapFileServiceImpl.submitFile(entity2.getImgid());
		topDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteTopEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		topDaoImpl.deleteEntity(topDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public Top getTopEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return topDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createTopSimpleQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		DataQuery dbQuery = DataQuery.init(query, "WLP_L_TOP",
				"ID,MODEL,VISITNUM,IMGID,URL,TITLE,TYPE,SORT,PCONTENT,PSTATE,CUSER,CUSERNAME,CTIME");
		return dbQuery;
	}

	@Override
	@Transactional
	public List<Top> allTops(LoginUser currentUser) {
		List<String> ableTypeids = classTypeServiceImpl.getUserAllTypeIds(currentUser, FuncPOP.read);
		List<Top> tops = topDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("PSTATE", "1", "=")).toList());
		DataQuery topClassquery = DataQuery.getInstance(1,
				"a.ID AS TOPID, b.ID AS ClASSID, b.PSTATE AS CLASSSTATE, c.CLASSTYPEID as CLASSTYPEID",
				"wlp_l_top a LEFT JOIN wlp_l_class b ON a.APPID = b.ID AND a.MODEL = '1' LEFT JOIN wlp_l_classclasstype c ON c.CLASSID = a.APPID");
		topClassquery.setPagesize(50);
		topClassquery.setNoCount();
		Map<String, Map<String, Object>> topDics = new HashMap<String, Map<String, Object>>();
		try {
			DataResult topInfos = topClassquery.search();
			for (Map<String, Object> node : topInfos.getResultList()) {
				topDics.put((String) node.get("TOPID"), node);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Top> topsback = new ArrayList<>();
		int maxNum = 5;// 最多顯示多少條大圖
		int prNum = 8;// 最多顯示多少條推薦課程
		int cmax = 0;
		int cpr = 0;
		for (Top top : tops) {
			if (top.getModel().equals("1") && topDics.get(top.getId()) != null) {
				// 课程类型
				String state = (String) topDics.get(top.getId()).get("CLASSSTATE");
				if (state != null && state.equals("0")) {
					continue;
				}
				// 处理分类权限String classtype = (String)
				// topDics.get(top.getId()).get("CLASSTYPEID");
				if (top.getModel().equals("1") && StringUtils.isNotBlank(top.getAppid())) {
					String typeid = classTServiceImpl.getTypeId(top.getAppid());
					if (!ableTypeids.contains(typeid)) {
						continue;
					}
				}
			}
			if (top.getType().equals("2") && cmax++ < maxNum) {
				top.setImgurl(wdapFileServiceImpl.getDownloadUrl(top.getImgid(), FileModel.IMG));
				topsback.add(top);
			}
			if (top.getType().equals("1") && cpr++ < prNum) {
				top.setImgurl(wdapFileServiceImpl.getDownloadUrl(top.getImgid(), FileModel.IMG));
				topsback.add(top);
			}
		}
		return topsback;
	}

}
