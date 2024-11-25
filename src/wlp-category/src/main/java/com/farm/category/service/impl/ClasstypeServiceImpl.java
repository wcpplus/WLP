package com.farm.category.service.impl;

import com.farm.category.domain.ClassType;
import com.farm.category.domain.ClassTypePop;
import com.farm.category.enums.FuncPOP;
import com.farm.core.time.TimeTool;
import com.farm.util.web.FarmFormatUnits;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.farm.authority.domain.Organization;
import com.farm.authority.domain.Post;
import com.farm.authority.service.OrganizationServiceInter;
import com.farm.authority.service.UserServiceInter;
import com.farm.category.dao.ClasstypeDaoInter;
import com.farm.category.dao.ClasstypepopDaoInter;
import com.farm.category.service.ClasstypeServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：课程分类服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class ClasstypeServiceImpl implements ClasstypeServiceInter {
	@Resource
	private ClasstypeDaoInter classtypeDaoImpl;
	@Resource
	private UserServiceInter userServiceImpl;
	@Resource
	private ClasstypepopDaoInter classtypepopDaoImpl;
	@Resource
	private OrganizationServiceInter organizationServiceImpl;
	private static final Logger log = Logger.getLogger(ClasstypeServiceImpl.class);

	@Override
	@Transactional
	public void moveTreeNode(String ids, String targetId, LoginUser currentUser) {
		String[] idArray = ids.split(",");
		ClassType target = getClasstypeEntity(targetId);
		for (int i = 0; i < idArray.length; i++) {
			// 移动节点
			ClassType node = getClasstypeEntity(idArray[i]);
			if (target != null && target.getTreecode().indexOf(node.getTreecode()) >= 0) {
				throw new RuntimeException("不能够移动到其子节点下!");
			}
			if (target == null) {
				node.setParentid("NONE");
			} else {
				node.setParentid(targetId);
			}
			classtypeDaoImpl.editEntity(node);
			// 构造所有树TREECODE
			List<ClassType> list = classtypeDaoImpl.getAllSubNodes(idArray[i]);
			for (ClassType org : list) {
				initTreeCode(org.getId());
			}
		}
	}

	@Override
	@Transactional
	public ClassType insertClasstypeEntity(ClassType entity, LoginUser user) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(user.getName());
		entity.setEuser(user.getId());
		entity.setEusername(user.getName());
		entity.setEtime(TimeTool.getTimeDate14());
		entity.setPstate("1");
		entity.setClassnum(0);
		entity.setWritepop("0");
		entity.setReadpop("0");
		if (StringUtils.isBlank(entity.getParentid())) {
			entity.setParentid("NONE");
		}
		entity.setTreecode("NONE");

		// 如果上级分类为设置权限则或集成权限则本分类为继承
		ClassType parentType = classtypeDaoImpl.getEntity(entity.getParentid());
		if (parentType != null) {
			// 如果父分类是禁用的子分类必须被禁用
			if (parentType.getPstate().equals("0")) {
				entity.setPstate("0");
			}
			entity.setReadpop(parentType.getReadpop());
			entity.setWritepop(parentType.getWritepop());
		}
		entity = classtypeDaoImpl.insertEntity(entity);
		initTreeCode(entity.getId());
		initTheTypePops(entity);
		return entity;
	}

	/**
	 * 初始化分类禁用状态的相关分类
	 */
	private void initFieldState(String oldState, String newState, ClassType ctype) {
		// 处理修改状态
		{
			// 如果是从启用变为禁用,禁用所有子类
			if (oldState.equals("1") && newState.equals("0")) {
				List<ClassType> types = getSubTypes(ctype.getId(),true);
				for (ClassType type : types) {
					type = getClasstypeEntity(type.getId());
					if (!type.getId().equals(ctype.getId())) {
						type.setPstate("0");
						classtypeDaoImpl.editEntity(type);
					}
				}
			}
			// 如果是从禁用变为启用,启用所有父类和子类
			if (oldState.equals("0") && newState.equals("1")) {
				List<ClassType> types1 = getAllPathType(ctype);
				for (ClassType type : types1) {
					type = getClasstypeEntity(type.getId());
					if (!type.getId().equals(ctype.getId())) {
						type.setPstate("1");
						classtypeDaoImpl.editEntity(type);
					}
				}
				List<ClassType> types2 = getSubTypes(ctype.getId(),true);
				for (ClassType type : types2) {
					type = getClasstypeEntity(type.getId());
					if (!type.getId().equals(ctype.getId())) {
						type.setPstate("1");
						classtypeDaoImpl.editEntity(type);
					}
				}
			}
		}
	}

	/**
	 * 获得上级分类的权限，然后将权限赋予子类
	 * 
	 * @param entity
	 */
	private void initTheTypePops(ClassType entity) {
		if (entity.getParentid() == null) {
			return;
		}
		List<DBRule> rules = new ArrayList<>();
		rules.add(new DBRule("TYPEID", entity.getParentid(), "="));
		List<ClassTypePop> pops = classtypepopDaoImpl.selectEntitys(rules);
		for (ClassTypePop pop : pops) {
			ClassTypePop newPop = new ClassTypePop();
			try {
				BeanUtils.copyProperties(newPop, pop);
				newPop.setTypeid(entity.getId());
				classtypepopDaoImpl.insertEntity(newPop);
			} catch (IllegalAccessException | InvocationTargetException e) {
				log.error("获得上级分类的权限，然后将权限赋予子类:", e);
			}
		}
	}

	/**
	 * 初始化treecode
	 * 
	 * @param treeNodeId
	 */
	private void initTreeCode(String treeNodeId) {
		ClassType node = getClasstypeEntity(treeNodeId);
		if (node.getParentid().equals("NONE")) {
			node.setTreecode(node.getId());
		} else {
			node.setTreecode(classtypeDaoImpl.getEntity(node.getParentid()).getTreecode() + node.getId());
		}
		classtypeDaoImpl.editEntity(node);
	}

	@Override
	@Transactional
	public ClassType editClasstypeEntity(ClassType entity, LoginUser user) {
		ClassType entity2 = classtypeDaoImpl.getEntity(entity.getId());
		String oldState = entity2.getPstate();
		String newState = entity.getPstate();
		if (StringUtils.isBlank(newState)) {
			newState = oldState;
		}
		entity2.setEuser(user.getId());
		entity2.setEusername(user.getName());
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setName(entity.getName());
		entity2.setType(entity.getType());
		// entity2.setTreecode(entity.getTreecode());
		// entity2.setParentid(entity.getParentid());
		entity2.setSort(entity.getSort());
		entity2.setPcontent(entity.getPcontent());
		entity2.setPstate(entity.getPstate());
		classtypeDaoImpl.editEntity(entity2);
		initTreeCode(entity.getId());
		initFieldState(oldState, newState, entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteClasstypeEntity(String id, LoginUser user) {
		if (classtypeDaoImpl.selectEntitys(DBRule.addRule(new ArrayList<DBRule>(), "parentid", id, "=")).size() > 0) {
			throw new RuntimeException("不能删除该节点，请先删除其子节点");
		}
		ClassType type = classtypeDaoImpl.getEntity(id);
		classtypepopDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("TYPEID", id, "=")).toList());
		classtypeDaoImpl.deleteEntity(type);
		classtypeDaoImpl.deleteRfTable(type.getId());
	}

	@Override
	@Transactional
	public ClassType getClasstypeEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return classtypeDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createClasstypeSimpleQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		DataQuery dbQuery = DataQuery.init(query, "WLP_C_CLASSTYPE",
				"ID,NAME,TREECODE,PARENTID,SORT,PCONTENT,PSTATE,EUSER,EUSERNAME,CUSER,CUSERNAME,ETIME,CTIME,TYPE,WRITEPOP,READPOP,CLASSNUM");
		return dbQuery;
	}

	@Override
	@Transactional
	public List<ClassType> getAllTypes() {
		List<ClassType> types = classtypeDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("PSTATE", "1", "=")).toList());
		Collections.sort(types, new Comparator<ClassType>() {
			@Override
			public int compare(ClassType o1, ClassType o2) {
				return o1.getSort() - o2.getSort();
			}
		});
		return types;
	}

	@Override
	@Transactional
	public List<ClassType> getAllPathType(ClassType type) {
		List<String> ids = FarmFormatUnits.SplitStringByLen(type.getTreecode(), 32);
		List<ClassType> list = new ArrayList<>();
		for (String typeid : ids) {
			list.add(getClasstypeEntity(typeid));
		}
		return list;
	}

	@Override
	@Transactional
	public List<String> getSubTypeids(String classtypeId) {
		ClassType ctype = classtypeDaoImpl.getEntity(classtypeId);
		List<ClassType> types = classtypeDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("PSTATE", "1", "="))
						.add(new DBRule("TREECODE", ctype.getTreecode(), "like-")).toList());
		List<String> ids = new ArrayList<>();
		for (ClassType type : types) {
			ids.add(type.getId());
		}
		return ids;
	}

	@Override
	@Transactional
	public List<ClassType> getSubTypes(String rootTypeid, boolean isAll) {
		ClassType ctype = classtypeDaoImpl.getEntity(rootTypeid);
		DBRuleList dbList = DBRuleList.getInstance().add(new DBRule("TREECODE", ctype.getTreecode(), "like-"));
		if (!isAll) {
			// 只查可用分類
			dbList.add(new DBRule("PSTATE", "1", "="));
		}
		List<ClassType> types = classtypeDaoImpl.selectEntitys(dbList.toList());
		return types;
	}

	@Override
	@Transactional
	public List<ClassType> getSubTypesByPop(String rootTypeid, LoginUser currentUser, FuncPOP funcpop) {
		List<ClassType> types = getSubTypes(rootTypeid,false);
		Set<String> userAbleTypeids = new HashSet<>(getPubTypeIds(funcpop));
		if (currentUser != null && currentUser.getId() != null) {
			userAbleTypeids.addAll(getPopTypeIds(currentUser, funcpop));
		}
		List<ClassType> backTypes = new ArrayList<>();
		for (ClassType node : types) {
			if (userAbleTypeids.contains(node.getId())) {
				backTypes.add(node);
			}
		}
		return backTypes;
	}

	@Override
	@Transactional
	public List<ClassType> getAllTypesByPop(LoginUser currentUser, FuncPOP funcPOP) {
		Set<String> userAbleTypeids = new HashSet<>(getPubTypeIds(funcPOP));
		if (currentUser != null && currentUser.getId() != null) {
			userAbleTypeids.addAll(getPopTypeIds(currentUser, funcPOP));
		}
		List<ClassType> types = classtypeDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("PSTATE", "1", "=")).toList());
		Collections.sort(types, new Comparator<ClassType>() {
			@Override
			public int compare(ClassType o1, ClassType o2) {
				return o1.getSort() - o2.getSort();
			}
		});
		List<ClassType> backTypes = new ArrayList<>();
		for (ClassType node : types) {
			if (userAbleTypeids.contains(node.getId())) {
				backTypes.add(node);
			}
		}
		return backTypes;
	}

	@Override
	@Transactional
	public void refreshNum(String typeid) {
		classtypeDaoImpl.getSession().flush();
		List<ClassType> types = getAllPathType(getClasstypeEntity(typeid));
		for (ClassType type : types) {
			ClassType ctype = getClasstypeEntity(type.getId());
			// 查询出该分类的知识数
			ctype.setClassnum(classtypeDaoImpl.getTypeClassNum(type.getId()));
			// 修改分类实体
			classtypeDaoImpl.editEntity(ctype);
		}
	}

	@Override
	@Transactional
	public List<String> getPubTypeIds(FuncPOP funcPOP) {
		if (funcPOP.equals(FuncPOP.read)) {
			return classtypeDaoImpl.getPubReadTypeIds();
		}
		if (funcPOP.equals(FuncPOP.write)) {
			return classtypeDaoImpl.getPubWriteTypeIds();
		}
		return new ArrayList<>();
	}

	@Override
	@Transactional
	public List<String> getPopTypeIds(LoginUser user, FuncPOP funcPOP) {
		if (user == null || user.getId() == null) {
			return new ArrayList<>();
		}
		List<String> ids = new ArrayList<>();
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
			DataQuery query = DataQuery.getInstance("1", "TYPEID,ID", "WLP_C_CLASSTYPE_POP");
			query.setPagesize(10000);
			query.setNoCount();
			query.addSqlRule("and FUNTYPE='" + funcPOP.getVal() + "' ");
			query.addSqlRule("and (OID ='" + user.getId() + "' " + orgSql + postSql + ")");
			try {
				for (Map<String, Object> node : query.search().getResultList()) {
					idset.add((String) node.get("TYPEID"));
				}
			} catch (SQLException e) {
				log.error(e + e.getMessage(), e);
				return new ArrayList<>();
			}
		}

		// 編輯权限特殊处理： 管理員可以訪問僅管理員權限的專業
		if (funcPOP.equals(FuncPOP.write) && user.getType().equals("3")) {
			List<ClassType> types = classtypeDaoImpl.selectEntitys(new DBRule("WRITEPOP", "3", "=").getDBRules());
			for (ClassType type : types) {
				ids.add(type.getId());
			}
		}

		for (String typeid : idset) {
			ids.add(typeid);
		}
		return ids;
	}

	@Override
	@Transactional
	public List<String> getUserAllTypeIds(LoginUser user, FuncPOP funcPOP) {
		Set<String> userAbleTypeids = new HashSet<>(getPubTypeIds(funcPOP));
		if (user != null && user.getId() != null) {
			userAbleTypeids.addAll(getPopTypeIds(user, funcPOP));
		}
		return new ArrayList<>(userAbleTypeids);
	}

}
