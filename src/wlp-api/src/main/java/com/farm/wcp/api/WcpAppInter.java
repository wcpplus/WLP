package com.farm.wcp.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import com.farm.wcp.api.exception.DocCreatErrorExcepiton;
import com.farm.wcp.domain.Results;

public interface WcpAppInter extends Remote {

	/**
	 * 上传图片到服务器，然后返回一个图片的URL
	 * 
	 * @param filename
	 *            文件名
	 * @param file
	 *            文件
	 * @param loginname
	 *            用户登录名
	 * @param password
	 *            用户密码
	 * @param secret
	 * @return 然后返回一个图片的URL
	 * @throws RemoteException
	 * @throws DocCreatErrorExcepiton
	 */
	public String uploadImgForUrl(String filename, byte[] file, String loginname, String password, String secret)
			throws RemoteException;

	/**
	 * 上传图片到服务器，然后返回一个图片的ID
	 * 
	 * @param filename
	 *            文件名
	 * @param file
	 *            文件
	 * @param loginname
	 *            用户登录名
	 * @param password
	 *            用户密码
	 * @param secret
	 * @return 然后返回一个图片的URL
	 * @throws RemoteException
	 * @throws DocCreatErrorExcepiton
	 */
	public String uploadImgForId(String filename, byte[] file, String loginname, String password, String secret)
			throws RemoteException;

	/**
	 * 创建文档知识
	 * 
	 * @param knowtitle
	 *            标题
	 * @param knowtypeId
	 *            分类id
	 * @param text
	 *            正文
	 * @param knowtag
	 *            知识标签
	 * @param loginname
	 *            登陆名
	 * @param password
	 *            登陆密码
	 * @param secret
	 *            密钥
	 * @return
	 * @throws RemoteException
	 * @throws DocCreatErrorExcepiton
	 */
	public String creatHTMLKnow(String knowtitle, String knowtypeId, String text, String knowtag, String imgid,
			String loginname, String password, String secret) throws RemoteException, DocCreatErrorExcepiton;

	/**
	 * 创建问题
	 * 
	 * @param knowtitle
	 *            标题
	 * @param knowtypeId
	 *            分类
	 * @param text
	 *            内容
	 * @param loginname
	 *            登陆名
	 * @param password
	 *            登陆密码
	 * @return
	 * @throws RemoteException
	 * @throws DocCreatErrorExcepiton
	 */
	public String creatHTMLQuestion(String title, String typeId, String text, String loginname, String password,
			String secret) throws RemoteException, DocCreatErrorExcepiton;

	/**
	 * 对附件做索引(同时会保存附件文本内容在数据库中方便后续重建索引)
	 * 
	 * @param fileid
	 *            附件id
	 * @param docid
	 *            知识id
	 * @param text
	 *            索引文字
	 * @throws ErrorTypeException
	 * @throws RemoteException
	 */
	public void runLuceneIndex(String fileid, String docid, String text, String secret) throws RemoteException;

	/**
	 * 获得分类下的知识
	 * 
	 * @param typeid
	 * @param pagesize
	 * @param pagenum
	 * @return
	 */
	public Results getTypeDocs(String typeid, int pagesize, int currentpage, String loginname, String password,
			String secret) throws RemoteException;

	/**
	 * 获得所有知识分类
	 * 
	 * @param typeid
	 * @param pagesize
	 * @param currentpage
	 * @return
	 */
	public Results getAllTypes(String loginname, String password, String secret) throws RemoteException;

	/**
	 * 获得用户信息
	 * 
	 * @param loginname
	 * @return
	 * @throws RemoteException
	 */
	public Map<String, Object> getUserInfo(String loginname, String password, String secret) throws RemoteException;

	/**
	 * 预览附件转换开始事件（附件预览转换开始时调用）
	 */
	public void FileConvertStartingEvent(String fileid, String docid, String secret) throws RemoteException;

	/**
	 * 预览附件转换成功事件（附件预览转换成功时调用）
	 */
	public void FileConvertSuccessEvent(String fileid, String secret) throws RemoteException;

	/**
	 * 预览附件转换失败事件（附件预览转换失败时调用）
	 */
	public void FileConvertErrorEvent(String fileid, String message, String secret) throws RemoteException;
}
