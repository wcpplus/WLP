package com.farm.sfile;

import java.io.FileNotFoundException;

import org.springframework.web.multipart.MultipartFile;

import com.farm.core.auth.domain.LoginUser;
import com.farm.sfile.domain.FileBase;
import com.farm.sfile.domain.ex.PersistFile;
import com.farm.sfile.enums.FileModel;
import com.farm.sfile.exception.FileExNameException;
import com.farm.sfile.exception.FileSizeException;

/**
 * 附件应用服务对外接口
 * 
 * @author macpl
 *
 */
public interface WdapFileServiceInter {

	/**
	 * 持久化文件到本地磁盤
	 * 
	 * @param file
	 * @param currentUser
	 * @param model
	 *            文件模型:IMG, MEDIA, FILE
	 * @return
	 * @throws FileSizeException
	 * @throws FileExNameException
	 */
	public FileBase saveLocalFile(MultipartFile file, FileModel model, LoginUser currentUser, String fileProcesskey)
			throws FileSizeException, FileExNameException;

	/**
	 * 持久化文件到本地磁盤
	 * 
	 * @param file
	 * @param currentUser
	 * @param model
	 *            文件模型:IMG, MEDIA, FILE
	 * @return
	 * @throws FileSizeException
	 * @throws FileExNameException
	 */
	public FileBase saveLocalFile(byte[] data, FileModel fileModel, String filename, LoginUser currentUser)
			throws FileSizeException, FileExNameException;

	/**
	 * 获得文件的下载连接
	 * 
	 * @param filebase
	 * @return
	 */
	public String getDownloadUrl(String fileid, FileModel  filemodel );

	/**
	 * 獲得文件的擴展名
	 * 
	 * @param filename
	 * @return
	 */
	public String getExName(String filename);

	/**
	 * 獲得文件的一個圖標
	 * 
	 * @param id
	 * @return
	 */
	public PersistFile getFileIconFile(String fileid);

	/**
	 * 逻辑删除一个文件
	 * 
	 * @param fileid
	 */
	public void delFileByLogic(String fileid);

	/**
	 * 持久化一個文件，把文件狀態變爲使用中(小于200m的文件会把secret设置为md5校验码)
	 * 
	 * @param fileid
	 * @return 旧的Secret:新的Secret
	 * @throws FileNotFoundException
	 */
	public String submitFile(String fileid) throws FileNotFoundException;

	/**
	 * 释放一個文件为临时文件
	 * 
	 * @param id
	 */
	public void freeFile(String fileid);

	/**
	 * 获得一个物理文件
	 * 
	 * @param fileid
	 * @return
	 */
	public PersistFile getPersistFile(String fileid);
	public PersistFile getPersistFile(FileBase file);

	/**
	 * 獲得文件的絕對路徑
	 * 
	 * @param fileid
	 * @return
	 */
	public String getFileRealPath(String fileid);

	/**
	 * 创建一个文件的虚拟空间
	 * 
	 * @param user
	 *            创建人信息
	 * @param filename
	 *            文件名称带后缀
	 * @param length
	 *            文件长度
	 * @param sysname
	 *            来源系统名称
	 * @return
	 */
	public FileBase initFile(LoginUser user, String filename, long length, String appid, String sysname);

	/**
	 * 获得图标下载地址
	 * 
	 * @param fileid
	 * @return
	 */
	public String getIconUrl(String fileid);

	/**
	 * 获得文件模型
	 * 
	 * @param fileid
	 * @return
	 * @throws FileExNameException
	 */
	public FileModel getFileModel(String fileid) throws FileExNameException;


	/**
	 * 通过APPID换附件id
	 * 
	 * @param appid
	 * @return
	 */
	public String getFileIdByAppId(String appid);

	/**
	 * 读取文件
	 * 
	 * @param fileid
	 * @return
	 */
	public String readFileToText(String fileid);

	/**
	 * 更新文件大小
	 * 
	 * @param fileId
	 * @param length
	 */
	public void updateFilesize(String fileId, int length);

	/**
	 * 将字符串写入磁盘文件中
	 * 
	 * @param fileId
	 * @param text
	 */
	public void writeFileByText(String fileId, String text);

	/**
	 * 获得文件数据对象
	 * 
	 * @param fileid
	 * @return
	 */
	public FileBase getFileBase(String fileid);

	public PersistFile getDirImgFile(String fileid, int num);

}
