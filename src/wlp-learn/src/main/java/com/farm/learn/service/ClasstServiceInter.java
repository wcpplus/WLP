package com.farm.learn.service;

import com.farm.learn.domain.ClassHour;
import com.farm.learn.domain.Classt;
import com.farm.learn.domain.ex.ClassView;
import com.farm.learn.domain.ex.HourView;
import com.farm.sfile.exception.FileExNameException;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.farm.category.domain.Tag;
import com.farm.category.domain.TagType;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：课程服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface ClasstServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 * @throws FileNotFoundException
	 */
	public Classt insertClasstEntity(Classt entity, String classtypeid, LoginUser user) throws FileNotFoundException;

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 * @param classtypeid
	 * @throws FileNotFoundException
	 */
	public Classt editClasstEntity(Classt entity, String classtypeid, LoginUser user) throws FileNotFoundException;

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteClasstEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public Classt getClasstEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createClasstSimpleQuery(DataQuery query);

	/**
	 * 获得用户的待发布课程（編輯課程）
	 * 
	 * @param query
	 * @param currentUser
	 * @return
	 * @throws SQLException
	 */
	public DataResult getTempClases(DataQuery query, LoginUser currentUser) throws SQLException;

	/**
	 * 获得用户以发布课程（編輯課程）
	 * 
	 * @param query
	 * @param currentUser
	 * @return
	 * @throws SQLException
	 */
	public DataResult getPublicClases(DataQuery query, LoginUser currentUser) throws SQLException;

	/**
	 * 获得课程封装
	 * 
	 * @param classid
	 * @param currentUser
	 * @return
	 */
	public ClassView getClassView(String classid);

	/**
	 * 修改課程簡介
	 * 
	 * @param classid
	 * @param text
	 * @param currentUser
	 * @return
	 * @throws FileNotFoundException
	 */
	public Classt editIntroduction(String classid, String text, LoginUser currentUser) throws FileNotFoundException;

	/**
	 * 发布课程
	 * 
	 * @param classid
	 * @param currentUser
	 */
	public void publicClass(String classid, LoginUser currentUser);

	/**
	 * 取消发布课程
	 * 
	 * @param classid
	 * @param currentUser
	 */
	public void tempClass(String classid, LoginUser currentUser);

	/**
	 * 获得最新发布知识
	 * 
	 * @param currentUser
	 * @return
	 * @throws SQLException
	 */
	public DataResult getNewClasses(LoginUser currentUser, int num) throws SQLException;

	/**
	 * 查询课程
	 * 
	 * @param query
	 * @param currentUser
	 * @return
	 * @throws SQLException
	 */
	public DataResult getClasses(DataQuery query, LoginUser currentUser) throws SQLException;

	/**
	 * 获得课程第一个课时
	 * 
	 * @param classid
	 * @param currentUser
	 * @return
	 * @throws FileExNameException
	 */
	public HourView getFirstHourView(String classid) throws FileExNameException;

	/**
	 * 獲得课时展示学习对象
	 * 
	 * @param hourid
	 * @param currentUser
	 * @return
	 * @throws FileExNameException
	 */
	public HourView getHourView(String hourid) throws FileExNameException;

	/**
	 * 用户是否可以查看课程
	 * 
	 * @param classid
	 * @param currentUser
	 * @return
	 */
	public boolean isReadAble(String classid, LoginUser currentUser);

	/**
	 * 用户是否有权限編輯课程
	 * 
	 * @param classid
	 * @param currentUser
	 * @return
	 */
	public boolean isWriteAble(String classid, LoginUser currentUser);

	/**
	 * 给课程添加标签
	 * 
	 * @param classid
	 * @param tagid
	 * @param currentUser
	 */
	public void addTag(String classid, String tagid, LoginUser currentUser);

	/**
	 * 给课程刪除标签
	 * 
	 * @param classid
	 * @param tagid
	 * @param currentUser
	 */
	public void removeTag(String classid, String tagid, LoginUser currentUser);

	/**
	 * 获得课程标签（返回全部标签，但是课程含有的标签为选中状态即tag的check属性为true）
	 * 
	 * @param allTags
	 * @param id
	 * @return
	 */
	public List<TagType> getClassAllTags(List<TagType> allTags, String classid);

	/**
	 * 获得课程标签
	 * 
	 * @param classid
	 * @return
	 */
	public List<Tag> getClassTags(String classid);

	/**
	 * 獲得課程分類id
	 * 
	 * @param appid
	 * @return
	 */
	public String getTypeId(String classid);

	/**
	 * 获得课程第一个课时
	 * 
	 * @param classid
	 * @return
	 */
	public ClassHour getFirstHour(String classid);

	/**
	 * 获得下一个课时
	 * 
	 * @param classid
	 * @param hourid
	 *            当前课时
	 * @param completeHourids
	 *            完成的课时
	 * @return
	 */
	public ClassHour getNextHour(String classid, String hourid, Set<String> completeHourids);

	/**
	 * 獲得課程中課時數量
	 * 
	 * @param classid
	 * @return
	 */
	public int getAllHoursNum(String classid);

	/**
	 * 獲得課程的學習人數
	 * 
	 * @param classid
	 */
	public void freshLearUserNum(String classid);

	/**
	 * 获得课程中所有课时的ID集合
	 * 
	 * @param classtid
	 * @return
	 */
	public List<String> getHourids(String classtid);

}