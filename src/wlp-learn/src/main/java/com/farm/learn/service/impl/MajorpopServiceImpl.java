package com.farm.learn.service.impl;

import com.farm.learn.domain.Major;
import com.farm.learn.domain.MajorPop;
import com.farm.learn.enums.MajorFuncPOP;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.farm.learn.dao.MajorDaoInter;
import com.farm.learn.dao.MajorpopDaoInter;
import com.farm.learn.service.MajorpopServiceInter;
import com.farm.web.WebUtils;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.farm.authority.FarmAuthorityService;
import com.farm.authority.domain.Organization;
import com.farm.authority.domain.Post;
import com.farm.authority.service.OrganizationServiceInter;
import com.farm.authority.service.UserServiceInter;
import com.farm.category.enums.FuncPOP;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：专业权限服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class MajorpopServiceImpl implements MajorpopServiceInter {
	@Resource
	private MajorpopDaoInter majorpopDaoImpl;
	@Resource
	private MajorDaoInter majorDaoImpl;
	@Resource
	private OrganizationServiceInter organizationServiceImpl;
	private static final Logger log = Logger.getLogger(MajorpopServiceImpl.class);
	@Resource
	private UserServiceInter userServiceImpl;

	@Override
	@Transactional
	public MajorPop insertMajorpopEntity(MajorPop entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return majorpopDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public MajorPop editMajorpopEntity(MajorPop entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		MajorPop entity2 = majorpopDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setMajorid(entity.getMajorid());
		entity2.setOname(entity.getOname());
		entity2.setOid(entity.getOid());
		entity2.setFuntype(entity.getFuntype());
		entity2.setPoptype(entity.getPoptype());
		entity2.setId(entity.getId());
		majorpopDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteMajorpopEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		majorpopDaoImpl.deleteEntity(majorpopDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public MajorPop getMajorpopEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return majorpopDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createMajorpopSimpleQuery(DataQuery query, MajorFuncPOP poptype, String majorid) {
		query = DataQuery.init(query, "WLP_L_MAJORPOP left join WLP_L_MAJOR on WLP_L_MAJORPOP.MAJORID=WLP_L_MAJOR.ID",
				"WLP_L_MAJORPOP.ID AS ID, WLP_L_MAJORPOP.POPTYPE AS POPTYPE, WLP_L_MAJORPOP.FUNTYPE AS FUNTYPE, WLP_L_MAJORPOP.OID AS OID, WLP_L_MAJORPOP.ONAME AS ONAME, WLP_L_MAJORPOP.MAJORID AS MAJORID, WLP_L_MAJOR.TITLE AS MAJORTITLE");
		@SuppressWarnings("static-access")
		List<String> ids = new WebUtils().parseIds(majorid);
		if (ids.size() <= 1) {
			query.addRule(new DBRule("MAJORID", majorid, "="));
		} else {
			String sql = null;
			for (String id : ids) {

				sql = sql == null ? "MAJORID='" + id + "'" : sql + " or MAJORID='" + id + "'";
			}
			query.addSqlRule(" and (" + sql + ")");
		}
		query.addRule(new DBRule("FUNTYPE", poptype.getVal(), "="));
		return query;
	}

	@Override
	@Transactional
	public void delPop(String id) {
		MajorPop pop = majorpopDaoImpl.getEntity(id);
		majorpopDaoImpl.deleteEntity(pop);
		freshTypePopState(pop.getMajorid(), pop.getFuntype());
	}

	@Override
	@Transactional
	public void addOrgPop(String majorid, String orgid, MajorFuncPOP funcPOP) {
		MajorPop majorpop = new MajorPop();
		majorpop.setFuntype(funcPOP.getVal());
		majorpop.setOid(orgid);
		majorpop.setOname(organizationServiceImpl.getOrganizationEntity(orgid).getName());
		majorpop.setPoptype("2");
		majorpop.setMajorid(majorid);
		majorpopDaoImpl.deleteEntitys(new DBRule("funtype", funcPOP.getVal(), "=").addRule("MAJORID", majorid, "=")
				.addRule("OID", orgid, "=").getDBRules());
		majorpopDaoImpl.insertEntity(majorpop);
		freshTypePopState(majorpop.getMajorid(), majorpop.getFuntype());
	}

	@Override
	@Transactional
	public void addPostPop(String majorid, String postid, MajorFuncPOP funcPOP) {
		MajorPop majorpop = new MajorPop();
		majorpop.setFuntype(funcPOP.getVal());
		majorpop.setOid(postid);
		majorpop.setOname(organizationServiceImpl.getPostEntity(postid).getName());
		majorpop.setPoptype("3");
		majorpop.setMajorid(majorid);
		majorpopDaoImpl.deleteEntitys(new DBRule("funtype", funcPOP.getVal(), "=").addRule("MAJORID", majorid, "=")
				.addRule("OID", postid, "=").getDBRules());
		majorpopDaoImpl.insertEntity(majorpop);
		freshTypePopState(majorpop.getMajorid(), majorpop.getFuntype());
	}

	@Override
	@Transactional
	public void addUserPop(String majorid, String userid, MajorFuncPOP funcPOP) {
		MajorPop majorpop = new MajorPop();
		majorpop.setFuntype(funcPOP.getVal());
		majorpop.setOid(userid);
		majorpop.setOname(FarmAuthorityService.getInstance().getUserById(userid).getName());
		majorpop.setPoptype("1");
		majorpop.setMajorid(majorid);
		majorpopDaoImpl.deleteEntitys(new DBRule("funtype", funcPOP.getVal(), "=").addRule("MAJORID", majorid, "=")
				.addRule("OID", userid, "=").getDBRules());
		majorpopDaoImpl.insertEntity(majorpop);
		freshTypePopState(majorpop.getMajorid(), majorpop.getFuntype());
	}

	/**
	 * 设置专业状态是否有权限限制
	 * 
	 * @param operate
	 *            操作类型 ：ADD添加，DEL删除
	 * @param typeid
	 * @param funcPOP
	 */
	private void freshTypePopState(String majorId, String funtype) {
		Major type = majorDaoImpl.getEntity(majorId);
		List<MajorPop> pops = majorpopDaoImpl
				.selectEntitys(new DBRule("funtype", funtype, "=").addRule("MAJORID", majorId, "=").getDBRules());
		if (FuncPOP.read.getVal().equals(funtype)) {
			if (pops.size() > 0) {
				type.setReadpop("1");// 所有人
			} else {
				type.setReadpop("0");// 限制
			}
		}
		if (FuncPOP.write.getVal().equals(funtype)) {
			if (pops.size() > 0) {
				type.setWritepop("1");// 所有人
			} else {
				type.setWritepop("3");// 限制
			}
		}
		majorDaoImpl.editEntity(type);
	}

	@Override
	@Transactional
	public List<String> getPopMajorIds(LoginUser user, MajorFuncPOP funcPOP) {
		List<String> ids = new ArrayList<>();
		if (user != null) {
			// 獲得用戶的被分配權限
			{
				// 所有用户分类
				Set<String> idset = new HashSet<String>();
				// 获得所有用户分配到的分类权限（不包括公开的分类）
				{
					Organization org = null;
					List<Post> posts = null;
					String postSql = "";
					String orgSql = "";
					org = userServiceImpl.getUserOrganization(user.getId());
					posts = userServiceImpl.getPost(user.getId());
					{// 构造用户岗位序列，用户的所有岗位获得分类
						if (posts != null) {
							for (Post post : posts) {
								if (postSql.equals("")) {
									postSql = " or OID='" + post.getId() + "'";
								} else {
									postSql = postSql + " or OID='" + post.getId() + "'";
								}
							}
						}
					}
					{// 构造组织机构序列，用户的所有上级机构，只要上级机构有权限该用户就有权限
						if (org != null) {
							List<Organization> orgparents = organizationServiceImpl.getParentOrgs(org.getId());
							String orgPs = "";
							for (Organization orgp : orgparents) {
								orgPs = orgPs + "'" + orgp.getId() + "',";
							}
							orgPs = orgPs + "'" + org.getId() + "'";
							orgSql = "or OID in (" + orgPs + ")";
						}
					}
					DataQuery query = DataQuery.getInstance("1", "MAJORID,ID", "WLP_L_MAJORPOP");
					query.setPagesize(10000);
					query.setNoCount();
					query.addSqlRule("and FUNTYPE='" + funcPOP.getVal() + "' ");
					query.addSqlRule("and (OID ='" + user.getId() + "' " + orgSql + postSql + ")");
					try {
						for (Map<String, Object> node : query.search().getResultList()) {
							idset.add((String) node.get("MAJORID"));
						}
					} catch (SQLException e) {
						log.error(e + e.getMessage(), e);
						return new ArrayList<>();
					}
				}
				for (String majorid : idset) {
					ids.add(majorid);
				}
			}
			// 編輯权限特殊处理： 管理員可以訪問僅管理員權限的專業
			if (funcPOP.equals(MajorFuncPOP.write) && user.getType().equals("3")) {
				List<Major> majors = majorDaoImpl.selectEntitys(new DBRule("WRITEPOP", "3", "=").getDBRules());
				for (Major major : majors) {
					ids.add(major.getId());
				}
			}
		}
		// 用戶為空或不为空，公开权限的都可以獲得
		if (funcPOP.equals(MajorFuncPOP.read)) {
			List<Major> majors = majorDaoImpl.selectEntitys(new DBRule("READPOP", "0", "=").getDBRules());
			for (Major major : majors) {
				ids.add(major.getId());
			}
		}
		return ids;
	}

	@Override
	@Transactional
	public boolean isEditMajor(String majorid, LoginUser user) {
		if (StringUtils.isBlank(majorid)) {
			return false;
		}
		// 当前只要是管理员都可以編輯课程
		if (user != null && user.getType().equals("3")) {
			return true;
		}
		List<String> userWriteTypeids = getPopMajorIds(user, MajorFuncPOP.write);
		if (userWriteTypeids.contains(majorid)) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean isReadAble(String majorid, LoginUser user) {
		if (StringUtils.isBlank(majorid)) {
			return false;
		}
		// 当前只要是管理员都可以查看课程
		if (user != null && user.getType().equals("3")) {
			return true;
		}
		List<String> userWriteTypeids = getPopMajorIds(user, MajorFuncPOP.read);
		if (userWriteTypeids.contains(majorid)) {
			return true;
		}
		return false;
	}
}
