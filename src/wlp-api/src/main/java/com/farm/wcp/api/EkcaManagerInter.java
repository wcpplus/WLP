package com.farm.wcp.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import com.farm.wcp.ekca.OperateEvent.EVENT_TYPE;

/**
 * 知识统计分析系统，管理接口
 * 
 *
 */
public interface EkcaManagerInter extends Remote {
	/**
	 * 用户预登陆，用户在wcp系统中跳转到EKCA系统前先注册要登录的用户信息，当注册完成后通过url带注册码过来，ekca中自动登录
	 * 
	 * @param key
	 * @param loginName
	 * @param secret
	 * @throws RemoteException
	 */
	public void loginReady(String key, String loginName, String secret) throws RemoteException;

	/**
	 * 获得用户当前积分
	 * 
	 * @param userid
	 * @param secret
	 * @return
	 */
	public int getUserPoint(String userid, String secret) throws RemoteException;

	/**
	 * 在ekca中把用户id注册为一组随机字符串，在用户访问积分明细时，这组字符串可以起到加密的作用(在用户访问积分明细时可以用这个串换取用户id)
	 * 
	 * @param userid
	 * @param secret
	 * @return
	 */
	public String registUserCode(String userid, String secret) throws RemoteException;

	/**
	 * 获得系统当前积分靠前的用户
	 * 
	 * @param pagesize
	 *            顯示靠前多少條
	 * @return ID,NAME,POINT
	 * @throws RemoteException
	 */
	public List<Map<String, Object>> getMostPointUsers(int pagesize, String secret) throws RemoteException;

	/**
	 * 查询知识问答操作记录 // 01:创建知识 // 02:创建问答 // 03:修改知识 // 04:修改问答 // 05:刪除知识 //
	 * 06:刪除问答 // // 21:关注知识 // 22:关注问答 // 23:评价知识 // 24:评论知识 // 25:评论问答 //
	 * 26:知识推送 // // 31:回答问题 // 32:追加提问 // 33:追加回答// 41:小组访问 // 42:专题访问 //
	 * 43:知识访问 // 44:问答访问 // 46:下载附件 ///12:普通检索/ 13:条件检索
	 * 
	 * @param viewDocFileEvent
	 * @param currentUser
	 * @return USERID,KNOWID,FQAID,TEXTVAL1,KNOWTITLE,FQATITLE,OID
	 */
	public List<Map<String, Object>> getNewOperate(EVENT_TYPE event_type, int size, String userid, String secret)
			throws RemoteException;

}
