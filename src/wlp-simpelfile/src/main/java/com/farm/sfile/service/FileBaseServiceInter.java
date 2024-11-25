package com.farm.sfile.service;

import com.farm.core.sql.query.DataQuery;
import com.farm.sfile.domain.FileBase;
import com.farm.sfile.enums.FileModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：文件服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface FileBaseServiceInter {
	/**
	 * 持久化一个文件
	 * 
	 * @param storeLocation
	 * @param originalFilename
	 * @param currentUser
	 * @return
	 */
	public FileBase saveFile(File file, String filename, LoginUser currentUser, String fileProcesskey);

	public FileBase saveFile(byte[] data, String filename, LoginUser currentUser);

	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public FileBase insertFilebaseEntity(FileBase entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public FileBase editFilebaseEntity(FileBase entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteFilebaseEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public FileBase getFilebaseEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createFilebaseSimpleQuery(DataQuery query);

	/**
	 * 逻辑删除附件
	 * 
	 * @param fileid
	 * @param currentUser
	 */
	public void deleteFileByLogic(String fileid, LoginUser currentUser);

	/**
	 * 判断文件大小是否超大
	 * 
	 * @param filesize
	 * @param model
	 * @return
	 */
	public boolean validateFileSize(long filesize, FileModel model);

	/**
	 * 判断文件扩展名是否合法
	 * 
	 * @param originalFilename
	 * @param model
	 * @return
	 */
	public boolean validateFileExname(String originalFilename, FileModel model);

	/**
	 * 初始化一个文件对象
	 * 
	 * @param currentUser
	 * @param filetitle
	 *            业务文件名称(本来的名称，不同于实际名称，上传时的文件名称)
	 * @param string
	 * @return
	 */
	public FileBase initFileBase(LoginUser currentUser, String filetitle, long filesize, String appid, String string);

	/**
	 * 獲得附件目录的實際絕對路徑
	 * 
	 * @param fileid
	 * @return
	 */
	public String getDirRealPath(String fileid);

	/**
	 * 獲得附件目录的實際絕對路徑
	 * 
	 * @param fileid
	 * @return
	 */
	public String getDirRealPath(FileBase filebase);

	/**
	 * 獲得附件的實際絕對路徑
	 * 
	 * @param fileid
	 * @return
	 */
	public String getFileRealPath(String fileid);

	/**
	 * 获得文件
	 * 
	 * @param filebase
	 * @return
	 */
	public File getFile(FileBase filebase);

	/**
	 * 獲得附件下載連接
	 * 
	 * @param fileid
	 * @param model
	 * @return
	 */
	public String getDownloadUrl(String fileid, FileModel model);

	/**
	 * 獲得附件圖標下載連接
	 * 
	 * @param fileid
	 * @param model
	 * @return
	 */
	public String getIconUrl(String fileid, FileModel model);

	/**
	 * 持久化一個文件，把文件狀態變爲使用中
	 * 
	 * @param fileid
	 * @return 旧的Secret:新的Secret
	 * @throws FileNotFoundException
	 */
	public String submitFile(String fileid) throws FileNotFoundException;

	/**
	 * 释放一个文件为临时文件
	 * 
	 * @param fileid
	 */
	public void freeFile(String fileid);

	/**
	 * 通过附件AppId兑换fileId
	 * 
	 * @param appid
	 * @return
	 */
	public String getFileIdByAppId(String appid);

	/**
	 * 修改文件大小
	 * 
	 * @param fileId
	 * @param length
	 */
	public void editFileSize(String fileId, int length);

	/**
	 * 将字符串写入磁盘文件中
	 * 
	 * @param fileId
	 * @param text
	 */
	public void WriteTextFile(String fileId, String text);

}