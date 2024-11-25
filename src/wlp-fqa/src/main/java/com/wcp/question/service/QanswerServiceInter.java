package com.wcp.question.service;

import com.wcp.question.domain.Qanswer;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

import java.util.List;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：问答答案服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface QanswerServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public Qanswer insertQanswerEntity(Qanswer entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public Qanswer editQanswerEntity(Qanswer entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteQanswerEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public Qanswer getQanswerEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createQanswerSimpleQuery(DataQuery query);

	/**
	 * 查询问题的回答(不含采纳回答)
	 * 
	 * @param questionid
	 *            问题id
	 * @param pagesize
	 *            每页数量
	 * @param pagenum
	 *            当前页数
	 * @return
	 */
	public List<Qanswer> getQanswersWaitByQuestion(String questionid);
	
	/**查询问题的回答(全部)
	 * @param questionid 问题id
	 * @return
	 */
	public List<Qanswer> getQanswersAllByQuestion(String questionid);
	/**
	 * 查詢問題的回答數
	 * 
	 * @param questionid
	 * @return
	 */
	public int getQanswerNumByQuestionid(String questionid);

	/**
	 * 赞同问题回答
	 * 
	 * @param qanswerid
	 * @param currentUser
	 * @return
	 */
	public int approveOf(String qanswerid, LoginUser currentUser);

	/**
	 * 反对问题回答
	 * 
	 * @param qanswerid
	 * @param currentUser
	 * @return
	 */
	public int oppose(String qanswerid, LoginUser currentUser);

	/**
	 * 回覆一個回答
	 * 
	 * @param questionid
	 *            問題id
	 * @param text
	 *            回覆內容
	 * @param qanswerid
	 *            回答id
	 * @param currentUser
	 */
	public void reply(String questionid, String text, String qanswerid, LoginUser currentUser);

	/**
	 * 删除一个未被採納的回答
	 * 
	 * @param qanswerid
	 *            回答id
	 * @param currentUser
	 */
	public void delAnswer(String qanswerid, LoginUser currentUser);

	/**
	 * 删除一个回答(完成的问题也可删除,非本人也可删除)
	 * 
	 * @param qanswerid
	 *            回答id
	 * @param currentUser
	 */
	public void delAnswerSuper(String qanswerid, LoginUser currentUser);


	/**
	 * 选择一个最佳答案,后台最小权限控制
	 * 
	 * @param id
	 * @param currentUser
	 */
	public void chooseBestAnswerSuper(String qanswerid, LoginUser currentUser);

	/**
	 * 我的回答
	 * 
	 * @param userid
	 *            用户id
	 * @param pagesize
	 *            每页显示的数量
	 * @param pagenum
	 *            页数
	 * @return
	 */
	public DataResult getUserAnswers(String userid, int pagesize, Integer pagenum);

}