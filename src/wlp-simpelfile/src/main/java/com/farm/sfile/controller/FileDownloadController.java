package com.farm.sfile.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.farm.parameter.FarmParameterService;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.sfile.domain.FileBase;
import com.farm.sfile.domain.ex.PersistFile;
import com.farm.sfile.enums.FileModel;
import com.farm.sfile.exception.FileExNameException;
import com.farm.sfile.utils.DownloadUtils;
import com.farm.sfile.utils.HttpContentType;
import com.farm.web.WebUtils;

/**
 * 文件下載
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/download")
@Controller
public class FileDownloadController extends WebUtils {
	private static final Logger log = Logger.getLogger(FileDownloadController.class);

	@Resource
	private WdapFileServiceInter wdapFileServiceImpl;

	/**
	 * 下載文件夹中文件
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@RequestMapping("/PubDirFile")
	public void loadDirFileByDown(String fileid, int num, HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, IOException {
		PersistFile file = wdapFileServiceImpl.getDirImgFile(fileid, num);
		DownloadUtils.downloadFile(file.getFile(), file.getName(), response);
	}

	/**
	 * 下載文件(普通下载)
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@RequestMapping("/Pubfile")
	public void loadFileByDown(String id, String secret, HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, IOException {
		PersistFile file = wdapFileServiceImpl.getPersistFile(id);
		File backFile = null;
		String backFileName = null;
		if (file == null || !file.getFile().exists()) {
			String imgpath = FarmParameterService.getInstance().getParameter("config.doc.none.default.path");
			File defaultPhone = new File(FarmParameterService.getInstance().getParameter("farm.constant.webroot.path")
					+ File.separator + imgpath.replaceAll("\\\\", File.separator).replaceAll("//", File.separator));
			backFile = defaultPhone;
			backFileName = "未发现图片";
		} else {
			backFile = file.getFile();
			backFileName = file.getName();
			if (!file.getSecret().trim().equals(secret)) {
				response.sendError(405, "安全码错误!secret：" + secret);
				return;
			}
		}
		DownloadUtils.downloadFile(backFile, backFileName, response);
	}

	/**
	 * 下載头像
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws FileExNameException
	 */
	@RequestMapping("/PubPhoto")
	public void loadPhotoByDown(String id, HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, IOException, FileExNameException {
		FileBase fileb = wdapFileServiceImpl.getFileBase(id);
		PersistFile filep = wdapFileServiceImpl.getPersistFile(fileb);
		File backFile = null;
		String backFileName = null;
		if (fileb == null || !filep.getFile().exists()) {
			String imgpath = FarmParameterService.getInstance().getParameter("config.doc.none.photo.path");
			File defaultPhone = new File(FarmParameterService.getInstance().getParameter("farm.constant.webroot.path")
					+ File.separator + imgpath.replaceAll("\\\\", File.separator).replaceAll("//", File.separator));
			backFile = defaultPhone;
			backFileName = "默认头像";
		} else {
			backFile = filep.getFile();
			backFileName = filep.getName();
		}
		DownloadUtils.downloadFile(backFile, backFileName, response);
	}

	/**
	 * 加载文件（在线预览）
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@RequestMapping("/Pubload")
	public void loadFileByFile(String id, HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, IOException {
		PersistFile file = wdapFileServiceImpl.getPersistFile(id);
		DownloadUtils.sendVideoFile(request, response, file.getFile(), file.getName(),
				new HttpContentType().getContentType(wdapFileServiceImpl.getExName(file.getName())));
	}

	// ---------------------------------------------------------------
}
