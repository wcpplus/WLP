package com.farm.category.service.impl;

import com.farm.category.domain.ClassType;
import com.farm.category.domain.ClassTypePop;
import com.farm.category.enums.FuncPOP;
import com.farm.core.time.TimeTool;
import com.farm.web.WebUtils;

import org.apache.log4j.Logger;

import com.farm.authority.FarmAuthorityService;
import com.farm.authority.service.OrganizationServiceInter;
import com.farm.category.dao.ClasstypeDaoInter;
import com.farm.category.dao.ClasstypepopDaoInter;
import com.farm.category.service.ClasstypeServiceInter;
import com.farm.category.service.ClasstypepopServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：课程分类权限服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class ClasstypepopServiceImpl implements ClasstypepopServiceInter {
	@Resource
	private ClasstypepopDaoInter classtypepopDaoImpl;
	@Resource
	private ClasstypeServiceInter classTypeServiceImpl;
	@Resource
	private OrganizationServiceInter organizationServiceImpl;
	@Resource
	private ClasstypeDaoInter classtypeDaoImpl;
	private static final Logger log = Logger.getLogger(ClasstypepopServiceImpl.class);

	@Override
	@Transactional
	public ClassTypePop insertClasstypepopEntity(ClassTypePop entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return classtypepopDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public ClassTypePop editClasstypepopEntity(ClassTypePop entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		ClassTypePop entity2 = classtypepopDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setTypeid(entity.getTypeid());
		entity2.setOname(entity.getOname());
		entity2.setOid(entity.getOid());
		entity2.setFuntype(entity.getFuntype());
		entity2.setPoptype(entity.getPoptype());
		entity2.setId(entity.getId());
		classtypepopDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteClasstypepopEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		classtypepopDaoImpl.deleteEntity(classtypepopDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public ClassTypePop getClasstypepopEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return classtypepopDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createClasstypepopSimpleQuery(DataQuery query, FuncPOP type, String typeid) {
		query = DataQuery.init(query,
				"WLP_C_CLASSTYPE_POP left join WLP_C_CLASSTYPE on WLP_C_CLASSTYPE_POP.TYPEID=WLP_C_CLASSTYPE.ID",
				"WLP_C_CLASSTYPE_POP.ID AS ID, WLP_C_CLASSTYPE_POP.POPTYPE AS POPTYPE, WLP_C_CLASSTYPE_POP.FUNTYPE AS FUNTYPE, WLP_C_CLASSTYPE_POP.OID AS OID, WLP_C_CLASSTYPE_POP.ONAME AS ONAME, WLP_C_CLASSTYPE_POP.TYPEID AS TYPEID, WLP_C_CLASSTYPE. NAME AS TYPENAME");
		@SuppressWarnings("static-access")
		List<String> ids = new WebUtils().parseIds(typeid);
		if (ids.size() <= 1) {
			query.addRule(new DBRule("TYPEID", typeid, "="));
		} else {
			String sql = null;
			for (String id : ids) {

				sql = sql == null ? "TYPEID='" + id + "'" : sql + " or TYPEID='" + id + "'";
			}
			query.addSqlRule(" and (" + sql + ")");
		}
		query.addRule(new DBRule("FUNTYPE", type.getVal(), "="));
		return query;
	}

	@Override
	@Transactional
	public void addUserTypePop(String typeid, String userid, FuncPOP funcPOP) {
		ClassTypePop doctypepop = new ClassTypePop();
		doctypepop.setFuntype(funcPOP.getVal());
		doctypepop.setOid(userid);
		doctypepop.setOname(FarmAuthorityService.getInstance().getUserById(userid).getName());
		doctypepop.setPoptype("1");
		doctypepop.setTypeid(typeid);
		classtypepopDaoImpl.deleteEntitys(new DBRule("funtype", funcPOP.getVal(), "=").addRule("TYPEID", typeid, "=")
				.addRule("OID", userid, "=").getDBRules());
		classtypepopDaoImpl.insertEntity(doctypepop);
		initParentAndChildPop("ADD", doctypepop);

	}

	/**
	 * 在添加和删除分类权限的时候，处理分类的上级和下级分类的权限
	 * 
	 * @param operate
	 *            操作类型 ：ADD添加，DEL删除
	 * @param pop
	 *            (typeid:分类id,popType:对象类型：1人、2组织机构、3岗位 ,oid:所有者ID/对象ID
	 *            ,funcPOP:1浏览、2编辑、3审核、4预览、5审核)
	 */
	private void initParentAndChildPop(String operate, ClassTypePop pop) {
		List<ClassType> childes = classTypeServiceImpl.getSubTypes(pop.getTypeid(),true);
		List<ClassType> parents = classTypeServiceImpl
				.getAllPathType(classTypeServiceImpl.getClasstypeEntity(pop.getTypeid()));
		if (operate.equals("ADD")) {
			// 增加权限，则子类也自动增加权限
			{
				for (ClassType type : childes) {
					if (!pop.getTypeid().equals(type.getId())) {
						addTypePop(pop.getFuntype(), pop.getOid(), pop.getOname(), type.getId(), pop.getPoptype());
					}
				}
			}
			// 增加权限，只针对，阅读和编辑权限,所有上级也要添加该权限（上级无权限时不添加）
			{
				if (FuncPOP.read.getVal().equals(pop.getFuntype()) || FuncPOP.write.getVal().equals(pop.getFuntype())) {
					for (ClassType type : parents) {
						// 不处理本节点
						if (!pop.getTypeid().equals(type.getId())) {
							// 如果上级节点无权限限制,且本节点也未配置过权限，则不需要添加到上级节点
							List<ClassTypePop> parentPops = getTypePops(pop.getFuntype(),
									classtypeDaoImpl.getEntity(type.getId()).getParentid());
							List<ClassTypePop> selfPops = getTypePops(pop.getFuntype(), type.getId());
							if (parentPops.size() > 0 || selfPops.size() > 0) {
								addTypePop(pop.getFuntype(), pop.getOid(), pop.getOname(), type.getId(),
										pop.getPoptype());
							}
						}
					}
				}
			}
		}
		if (operate.equals("DEL")) {
			// 删除权限，则子类也自动删除权限,只针对，阅读和编辑和管理权限
			{
				if (FuncPOP.read.getVal().equals(pop.getFuntype()) || FuncPOP.write.getVal().equals(pop.getFuntype())) {
					for (ClassType type : childes) {
						if (!pop.getTypeid().equals(type.getId())) {
							classtypepopDaoImpl.deleteEntitys(
									new DBRule("funtype", pop.getFuntype(), "=").addRule("TYPEID", type.getId(), "=")
											.addRule("OID", pop.getOid(), "=").getDBRules());
						}
					}
				}
			}
			// 删除权限，对上级分类均不作处理
			{
				// FarmDoctype type =
				// farmDocTypeServer.getType(pop.getTypeid());
				// 在freshTypePopState(type.getId(), pop.getFuntype());方法中处理
			}
		}
		{// 刷新分类状态，将分类设置为 无限制、限制、全部禁用
			childes.addAll(parents);
			for (ClassType type : childes) {
				freshTypePopState(type.getId(), pop.getFuntype());
			}
		}
	}

	/**
	 * 数据库添加权限记录
	 * 
	 * @param funtype
	 * @param oid
	 * @param oname
	 * @param typeid
	 * @return
	 */
	private ClassTypePop addTypePop(String funtype, String oid, String oname, String typeid, String poptype) {
		ClassTypePop doctypepop = new ClassTypePop();
		doctypepop.setFuntype(funtype);
		doctypepop.setOid(oid);
		doctypepop.setOname(oname);
		doctypepop.setPoptype(poptype);
		doctypepop.setTypeid(typeid);
		classtypepopDaoImpl.deleteEntitys(new DBRule("funtype", funtype, "=").addRule("TYPEID", typeid, "=")
				.addRule("OID", oid, "=").getDBRules());
		return classtypepopDaoImpl.insertEntity(doctypepop);
	}

	/**
	 * 获得分类权限
	 * 
	 * @param funtype
	 * @param typeid
	 * @return
	 */
	private List<ClassTypePop> getTypePops(String funtype, String typeid) {
		List<ClassTypePop> parentPops = classtypepopDaoImpl
				.selectEntitys(new DBRule("funtype", funtype, "=").addRule("TYPEID", typeid, "=").getDBRules());
		return parentPops;
	}

	/**
	 * 设置分类状态是否有权限限制
	 * 
	 * @param operate
	 *            操作类型 ：ADD添加，DEL删除
	 * @param typeid
	 * @param funcPOP
	 */
	private void freshTypePopState(String typeid, String funtype) {
		ClassType type = classtypeDaoImpl.getEntity(typeid);
		List<ClassTypePop> pops = classtypepopDaoImpl
				.selectEntitys(new DBRule("funtype", funtype, "=").addRule("TYPEID", typeid, "=").getDBRules());
		// 0所有人， 1限制， 3仅超管
		if (FuncPOP.read.getVal().equals(funtype)) {
			if (pops.size() > 0) {
				type.setReadpop("1");// 限制
			} else {
				// 是否有上级权限
				if (classtypeDaoImpl.isParentPopExist(typeid, funtype)) {
					type.setReadpop("3");// 仅超管
				} else {
					type.setReadpop("0");// 所有人
				}
			}
		}
		if (FuncPOP.write.getVal().equals(funtype)) {
			if (pops.size() > 0) {
				type.setWritepop("1");//  限制
			} else {
				// 是否有上级权限
				if (classtypeDaoImpl.isParentPopExist(typeid, funtype)) {
					type.setWritepop("3");// 仅超管
				} else {
					type.setWritepop("0");// 所有人
				}
			}
		}
		classtypeDaoImpl.editEntity(type);
	}

	@Override
	@Transactional
	public void delTypePop(String typepopid) {
		ClassTypePop pop = classtypepopDaoImpl.getEntity(typepopid);
		classtypepopDaoImpl.deleteEntity(pop);
		initParentAndChildPop("DEL", pop);
	}

	@Override
	@Transactional
	public void addOrgTypePop(String typeid, String orgid, FuncPOP funcPOP) {
		ClassTypePop doctypepop = new ClassTypePop();
		doctypepop.setFuntype(funcPOP.getVal());
		doctypepop.setOid(orgid);
		doctypepop.setOname(organizationServiceImpl.getOrganizationEntity(orgid).getName());
		doctypepop.setPoptype("2");
		doctypepop.setTypeid(typeid);
		classtypepopDaoImpl.deleteEntitys(new DBRule("funtype", funcPOP.getVal(), "=").addRule("TYPEID", typeid, "=")
				.addRule("OID", orgid, "=").getDBRules());
		classtypepopDaoImpl.insertEntity(doctypepop);
		initParentAndChildPop("ADD", doctypepop);
	}

	@Override
	@Transactional
	public void addPostTypePop(String typeid, String postid, FuncPOP funcPOP) {
		ClassTypePop doctypepop = new ClassTypePop();
		doctypepop.setFuntype(funcPOP.getVal());
		doctypepop.setOid(postid);
		doctypepop.setOname(organizationServiceImpl.getPostEntity(postid).getName());
		doctypepop.setPoptype("3");
		doctypepop.setTypeid(typeid);
		classtypepopDaoImpl.deleteEntitys(new DBRule("funtype", funcPOP.getVal(), "=").addRule("TYPEID", typeid, "=")
				.addRule("OID", postid, "=").getDBRules());
		classtypepopDaoImpl.insertEntity(doctypepop);
		initParentAndChildPop("ADD", doctypepop);
	}
}
