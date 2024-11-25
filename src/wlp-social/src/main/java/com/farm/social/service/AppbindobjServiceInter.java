package com.farm.social.service;

import com.farm.social.domain.AppbindObj;
import com.farm.social.domain.AppbindResource;
import com.farm.social.domain.ex.ResourceView;
import com.farm.core.sql.query.DataQuery;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：应用绑定对象服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface AppbindobjServiceInter {

	/**
	 * 资源类型
	 * 
	 * @author macpl
	 *
	 */
	enum ResourceType {
		// 1.知识库,2.问答,3.测验,4:考试
		KNOW("1", "知识库", "glyphicon-folder-open"), QUESTION("2", "问答", "glyphicon-question-sign"), TEST("3", "测验",
				"glyphicon-pencil"), EXAM("4", "考试", "glyphicon-time");
		String type;
		String title;
		String icon;

		ResourceType(String type, String title, String icon) {
			this.type = type;
			this.title = title;
			this.icon = icon;
		}

		public static Map<String, ResourceType> getDicMap() {
			Map<String, ResourceType> dic = new HashMap<String, AppbindobjServiceInter.ResourceType>();
			for (ResourceType node : values()) {
				dic.put(node.type, node);
			}
			return dic;
		}

		public static Map<String, String> getDicStrMap() {
			Map<String, String> dic = new HashMap<String, String>();
			for (ResourceType node : values()) {
				dic.put(node.type, node.getTitle());
			}
			return dic;
		}

		public String getType() {
			return type;
		}

		public String getTitle() {
			return title;
		}

		public String getIcon() {
			return icon;
		}
	}

	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public AppbindObj insertAppbindobjEntity(AppbindObj entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public AppbindObj editAppbindobjEntity(AppbindObj entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteAppbindobjEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public AppbindObj getAppbindobjEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createAppbindobjSimpleQuery(DataQuery query);

	/**
	 * 获得资源
	 * 
	 * @param id
	 * @return
	 */
	public AppbindResource getResource(String appbindresourceid);

	/**
	 * 編輯资源
	 * 
	 * @param entity
	 * @param currentUser
	 * @return
	 */
	public AppbindResource editResource(String resourceId, String appid, String apptype, String resourceurl,
			String resourcetype, String sysid, LoginUser currentUser);

	/**
	 * 添加资源
	 * 
	 * @param entity
	 * @param currentUser
	 * @return
	 */
	public AppbindResource addResource(String appid, String apptype, String resourceurl, String resourcetype,
			String sysid, LoginUser currentUser);

	public void deleteResource(String resourceid, LoginUser currentUser);

	/**
	 * 获得对象的绑定资源
	 * 
	 * @param classid
	 * @return
	 * @throws SQLException
	 */
	public List<ResourceView> getAppResource(String appid) throws SQLException;
}