package com.farm.learn.service.impl;

import com.farm.learn.dao.ClassclasstypeDaoInter;
import com.farm.learn.dao.ClasstDaoInter;
import com.farm.learn.domain.ClassClassType;
import com.farm.learn.service.UserPopServiceInter;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farm.category.enums.FuncPOP;
import com.farm.category.service.ClasstypeServiceInter;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：课程章节服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class UserPopServiceImpl implements UserPopServiceInter {
	@Resource
	private ClassclasstypeDaoInter classclasstypeDaoImpl;
	@Resource
	private ClasstDaoInter classtDaoImpl;
	@Resource
	private ClasstypeServiceInter classTypeServiceImpl;

	@Override
	@Transactional
	public boolean isEditClassByTypeid(String typeid, LoginUser user) {
		// 当前只要是管理员都可以編輯课程
		if (user != null && user.getType().equals("3")) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean isEditClass(String classid, LoginUser user) {
		if (StringUtils.isBlank(classid)) {
			return false;
		}
		// 当前只要是管理员都可以編輯课程
		if (user != null && user.getType().equals("3")) {
			return true;
		}
		List<ClassClassType> classtypes = classclasstypeDaoImpl.getEntityByClassId(classid);
		if (classtypes.size() > 0) {
			ClassClassType type = classtypes.get(0);
			List<String> userWriteTypeids = classTypeServiceImpl.getUserAllTypeIds(user, FuncPOP.write);
			if (userWriteTypeids.contains(type.getClasstypeid())) {
				return true;
			}
		}
		return false;
	}

}
