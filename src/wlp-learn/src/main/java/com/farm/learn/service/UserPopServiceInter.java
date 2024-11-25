package com.farm.learn.service;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：课程章节服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface UserPopServiceInter {
	public boolean isEditClassByTypeid(String typeid, LoginUser user);

	public boolean isEditClass(String classid, LoginUser user);
}