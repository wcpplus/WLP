package com.farm.social.service.impl;

import com.farm.social.domain.Outsys;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.farm.social.dao.OutsysDaoInter;
import com.farm.social.service.OutsysServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：外部系统服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class OutsysServiceImpl implements OutsysServiceInter {
	@Resource
	private OutsysDaoInter outsysDaoImpl;

	private static final Logger log = Logger.getLogger(OutsysServiceImpl.class);

	@Override
	@Transactional
	public Outsys insertOutsysEntity(Outsys entity, LoginUser user) {
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		return outsysDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public Outsys editOutsysEntity(Outsys entity, LoginUser user) {
		Outsys entity2 = outsysDaoImpl.getEntity(entity.getId());
		entity2.setBaseurl(entity.getBaseurl());
		entity2.setPcontent(entity.getPcontent());
		entity2.setSysname(entity.getSysname());
		entity2.setPstate(entity.getPstate());
		outsysDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteOutsysEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		outsysDaoImpl.deleteEntity(outsysDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public Outsys getOutsysEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return outsysDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createOutsysSimpleQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		DataQuery dbQuery = DataQuery.init(query, "WLP_S_OUTSYS", "ID,BASEURL,PCONTENT,SYSNAME,PSTATE");
		return dbQuery;
	}

	@Override
	@Transactional
	public List<Outsys> getAllSys() {
		return outsysDaoImpl.selectEntitys(DBRuleList.getInstance().add(new DBRule("PSTATE", "1", "=")).toList());
	}

}
