package com.farm.category.enums;

import com.farm.category.domain.ClassType;

/**
 * 分类权限类型(1浏览、2编辑)
 */
public enum FuncPOP {
	read("1"), write("2");
	private String type;

	FuncPOP(String val) {
		type = val;
	}

	public static String getTitle(String val) {
		if (val.equals("1")) {
			return "浏览";
		}
		if (val.equals("2")) {
			return "编辑";
		}
		return "异常";
	}

	public String getVal() {
		return type;
	}

	public static FuncPOP getEnum(String val) {
		if (val.equals("1")) {
			return FuncPOP.read;
		}
		if (val.equals("2")) {
			return FuncPOP.write;
		}
		return null;
	}

	/**
	 * @param type
	 * @param funcPOP
	 * @return
	 */
	public static String getTypePopState(ClassType type, FuncPOP funcPOP) {
		initTypePopState(type);
		if (funcPOP.equals(FuncPOP.read)) {
			return type.getReadpop();
		}
		if (funcPOP.equals(FuncPOP.write)) {
			return type.getWritepop();
		}

		throw new RuntimeException("未知的权限类型FuncPOP:" + funcPOP.getVal());
	}

	/**
	 * 初始分类实体中的权限字段如果为空则默认为“0”
	 * 
	 * @param type
	 * @return
	 */
	public static ClassType initTypePopState(ClassType type) {
		if (type == null) {
			throw new RuntimeException("type is not null!");
		}
		if (type.getReadpop() == null) {
			type.setReadpop("0");
		}
		if (type.getWritepop() == null) {
			type.setWritepop("3");
		}
		return type;
	}
}
