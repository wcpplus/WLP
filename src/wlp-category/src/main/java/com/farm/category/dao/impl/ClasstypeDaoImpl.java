package com.farm.category.dao.impl;

import java.math.BigInteger;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.AbstractEntityPersister;

import com.farm.category.domain.ClassType;
import com.farm.category.enums.FuncPOP;
import com.farm.category.dao.ClasstypeDaoInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;

/* *
 *功能：课程分类持久层实现
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Repository
public class ClasstypeDaoImpl extends HibernateSQLTools<ClassType>implements ClasstypeDaoInter {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFatory;

	@Override
	public List<ClassType> getAllSubNodes(String typeid) {
		Session session = sessionFatory.getCurrentSession();
		Query sqlquery = session.createQuery("from ClassType where TREECODE like ? order by ctime ");
		sqlquery.setString(0, getEntity(typeid).getTreecode() + "%");
		return sqlquery.list();
	}

	@Override
	public void deleteEntity(ClassType classtype) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.delete(classtype);
	}

	@Override
	public int getAllListNum() {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery("select count(*) from farm_code_field");
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	@Override
	public ClassType getEntity(String classtypeid) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		return (ClassType) session.get(ClassType.class, classtypeid);
	}

	@Override
	public ClassType insertEntity(ClassType classtype) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.save(classtype);
		return classtype;
	}

	@Override
	public void editEntity(ClassType classtype) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.update(classtype);
	}

	@Override
	public Session getSession() {
		// TODO 自动生成代码,修改后请去除本注释
		return sessionFatory.getCurrentSession();
	}

	@Override
	public DataResult runSqlQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			return query.search(sessionFatory.getCurrentSession());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void deleteEntitys(List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释
		deleteSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public List<ClassType> selectEntitys(List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释
		return selectSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释
		updataSqlFromFunction(sessionFatory.getCurrentSession(), values, rules);
	}

	@Override
	public int countEntitys(List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释
		return countSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	@Override
	protected Class<?> getTypeClass() {
		return ClassType.class;
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFatory;
	}

	@Override
	public int getTypeClassNum(String typeid) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery("SELECT COUNT(B1.ID) "
				+ "FROM WLP_L_CLASS B1 LEFT JOIN WLP_L_CLASSCLASSTYPE B2 ON B1.ID = B2.CLASSID LEFT JOIN WLP_C_CLASSTYPE B3 ON B3.ID = B2.CLASSTYPEID "
				+ "WHERE B1.PSTATE = '1' AND B3.TREECODE LIKE CONCAT(?, '%') ");
		sqlquery.setString(0, getEntity(typeid).getTreecode());
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	@Override
	public boolean isParentPopExist(String typeid, String functype) {
		String id = typeid;
		List<ClassType> types = new ArrayList<ClassType>();
		while (id != null) {
			ClassType centity = getEntity(id);
			if (centity == null || centity.getParentid() == null || centity.getParentid().trim().length() <= 0) {
				id = null;
			} else {
				id = centity.getParentid();

			}
			if (centity != null) {
				if (!id.equals(typeid)) {
					types.add(centity);
				}
			}
		}
		Collections.reverse(types);
		int n = 0;
		for (ClassType type : types) {
			Session session = sessionFatory.getCurrentSession();
			SQLQuery sqlquery = session.createSQLQuery("select OID,ONAME from WLP_C_CLASSTYPE_POP where TYPEID='"
					+ type.getId() + "' and FUNTYPE='" + functype + "'");
			n = n + sqlquery.list().size();
		}
		return n > 0;
	}

	@Override
	public List<String> getPubReadTypeIds() {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery("select id from WLP_C_CLASSTYPE where READPOP='0' and PSTATE='1' ");
		return sqlquery.list();
	}

	@Override
	public List<String> getPubWriteTypeIds() {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery("select id from WLP_C_CLASSTYPE where WRITEPOP='0' and PSTATE='1'");
		return sqlquery.list();
	}

	@Override
	public void deleteRfTable(String typeid) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery("DELETE FROM WLP_L_CLASSCLASSTYPE WHERE CLASSTYPEID=?");
		sqlquery.setString(0, typeid);
		sqlquery.executeUpdate();
	}
}
