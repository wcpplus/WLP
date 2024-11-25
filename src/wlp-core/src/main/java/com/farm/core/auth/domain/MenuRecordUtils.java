package com.farm.core.auth.domain;

import java.util.Set;

/**
 * 一个菜单权限（用来做权限验证）
 * 
 * @author macpl
 *
 */
public class MenuRecordUtils {
	/**
	 * 菜单集合中是否包含一个菜单
	 * 
	 * @param menu
	 * @param records
	 * @return
	 */
	public static boolean contains(WebMenu menu, Set<MenuRecord> records) {
		for (MenuRecord record : records) {
			if ((menu.getId() != null && record.getId() != null && menu.getId().equals(record.getId()))
					|| menu.getUrl() != null && record.getKey() != null && menu.getUrl().equals(record.getKey())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 菜单集合中是否包含一个URI
	 * 
	 * @param menu
	 * @param records
	 * @return
	 */
	public static boolean contains(String uri, Set<MenuRecord> records) {
		for (MenuRecord record : records) {
			if (uri != null && record.getKey() != null && uri.equals(record.getKey())) {
				return true;
			}
		}
		return false;
	}

}
