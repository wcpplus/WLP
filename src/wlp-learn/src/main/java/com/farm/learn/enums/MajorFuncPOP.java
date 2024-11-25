package com.farm.learn.enums;

import com.farm.learn.domain.Major;

/**
 * 专业权限类型(1浏览、2编辑)
 */
public enum MajorFuncPOP {
	read("1"), write("2");
	private String type;

	MajorFuncPOP(String val) {
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

	public static MajorFuncPOP getEnum(String val) {
		if (val.equals("1")) {
			return MajorFuncPOP.read;
		}
		if (val.equals("2")) {
			return MajorFuncPOP.write;
		}
		return null;
	}

	/**权限状态码
	 * @param type
	 * @param funcPOP
	 * @return
	 */
	public static String getPopState(Major type, MajorFuncPOP funcPOP) {
		initPopState(type);
		if (funcPOP.equals(MajorFuncPOP.read)) {
			return type.getReadpop();
		}
		if (funcPOP.equals(MajorFuncPOP.write)) {
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
	public static Major initPopState(Major type) {
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
