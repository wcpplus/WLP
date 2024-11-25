package com.farm.wcp.ekca;

import java.io.Serializable;

/**
 * 实体状态(如、可用、禁用、删除、审核等、问答完成状态)
 * 
 * @author wd
 *
 */
public class UpdateState implements Serializable {
	private String val;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4241189109674676843L;

	public UpdateState(String val) {
		this.val = val;
	}

	/**
	 * 禁用0
	 * 
	 * @return
	 */
	public static UpdateState getDisabled() {
		return new UpdateState("0");
	}

	/**
	 * 可用1
	 * 
	 * @return
	 */
	public static UpdateState getAble() {
		return new UpdateState("1");
	}

	/**
	 * 审核中3
	 * 
	 * @return
	 */
	public static UpdateState getAuditing() {
		return new UpdateState("3");
	}

	/**
	 * 已经删除2
	 * 
	 * @return
	 */
	public static UpdateState getDelete() {
		return new UpdateState("2");
	}

	/**
	 * 问答已经完成
	 * 
	 * @return
	 */
	public static UpdateState getComplete() {
		return new UpdateState("4");
	}

	public String getVal() {
		return val;
	}

}
