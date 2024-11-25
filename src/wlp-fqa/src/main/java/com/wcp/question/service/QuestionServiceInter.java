package com.wcp.question.service;

import com.wcp.question.domain.Qanswer;
import com.wcp.question.domain.Question;
import com.wcp.question.domain.Questionplus;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

import java.util.Map;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：问题服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface QuestionServiceInter {

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public Question editQuestion(Question entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteQuestionEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public Question getQuestionEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createQuestionSimpleQuery(DataQuery query);

	/**
	 * 创建一个提问
	 * 
	 * @param title
	 *            题目
	 * @param typeid
	 *            分类id
	 * @param text
	 *            内容
	 * @param knowtag
	 *            标签
	 * @param fileId
	 *            预览图
	 * @param price
	 *            悬赏分
	 * @param isanonymous
	 *            是否匿名
	 * @param currentUser
	 *            当前用户
	 * @return
	 */
	public Question creatQuestion(String title, String typeid, String text, String knowtag, String fileId, int price,
			boolean isanonymous, LoginUser currentUser);


	/**
	 * 获得某分类下的问答
	 * 
	 * @param user
	 * @param typeid
	 * @param i
	 * @param pagenum
	 * @return
	 */
	public DataResult getTypeFqas(LoginUser user, String typeid, int pagesize, Integer pagenum);

	/**
	 * 提交一个问题的回答
	 * 
	 * @param questionid
	 *            问题id
	 * @param content
	 *            回答内容
	 * @param isAnonymous
	 *            是否匿名
	 * @param currentUser
	 *            当前用户
	 * @return
	 */
	public Qanswer addQanswer(String questionid, String content,boolean isAnonymous, LoginUser currentUser);

	/**
	 * 追加提问
	 * 
	 * @param questionid
	 * @param text
	 * @param loginUser
	 */
	public void addQuestionClosely(String questionid, String text, LoginUser loginUser);

	/**
	 * 获得追加提问
	 * 
	 * @param questionid
	 * @return
	 */
	public Questionplus getQuestionClosely(String closelyid);

	/**
	 * 修改一个问题追加
	 * 
	 * @param closelyid
	 * @param text
	 * @param currentUser
	 * @return
	 */
	public Questionplus editQuestionClosely(String closelyid, String text, LoginUser currentUser);

	/**
	 * 有人访问问答
	 * 
	 * @param questionid
	 * @param currentUser
	 * @param currentIp
	 */
	public void visit(String questionid, LoginUser currentUser, String currentIp);

	/**
	 * 刷新问题的回答数
	 * 
	 * @param questionid
	 */
	public void refreshAnswersNum(String questionid);


	/**
	 * 获得我提的问题
	 * 
	 * @param userid
	 *            用户id
	 * @param pagesize
	 *            每页显示的数量
	 * @param pagenum
	 *            页数
	 * @return
	 */
	public DataResult getUserQuestions(String userid, int pagesize, Integer pagenum);


	/**
	 * 最热已完成问答
	 * 
	 * @param size
	 *            查询条数
	 * @return
	 */
	public DataResult getHotQuestionByFinish(int size);

	/**
	 * 最热待完成问答
	 * 
	 * @param size
	 *            查询条数
	 * @return
	 */
	public DataResult getHotQuestionByWaiting(int size,LoginUser user);

	/**
	 * 最热问答
	 * 
	 * @param size
	 *            查询条数
	 * @return
	 */
	public DataResult getHotQuestion(int size,LoginUser user);

	/**
	 * 问答统计
	 * 
	 * @return
	 */
	public Map<String, Integer> getStatNum();

	/**
	 * 逻辑删除
	 * 
	 * @param questionid
	 *            问题id
	 * @param currentUser
	 */
	public void deleteQuestionEntityByLogic(String questionid, LoginUser currentUser);

	/**
	 * 选择一个最佳答案
	 * 
	 * @param qanswerid
	 *            回答id
	 * @param currentUser
	 *            當前用戶
	 */
	public void chooseBestAnswer(String qanswerid, LoginUser currentUser);

	/**
	 * 移动问答到指定分类
	 * 
	 * @param docIds
	 *            文档ID（多个ID用英文逗号分割）
	 * @param typeId
	 *            分类ID void
	 */
	public void move2Type(String questionids, String typeId, LoginUser currentUser);

	/**
	 * 修改一个问题
	 * 
	 * @param id
	 * @param title
	 * @param typeid
	 * @param text
	 * @param fqatag
	 * @param fileId
	 * @param isanonymous
	 * @param currentUser
	 * @return
	 */
	public Question editQuestion(String id, String title, String typeid, String text, String fqatag, String fileId,
			boolean isanonymous, LoginUser currentUser);


}