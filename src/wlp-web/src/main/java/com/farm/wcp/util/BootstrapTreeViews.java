package com.farm.wcp.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.farm.category.domain.ClassType;

public class BootstrapTreeViews {
	private static final Logger log = Logger.getLogger(BootstrapTreeViews.class);

	/**
	 * 构造树控件的数据对象
	 * 
	 * @param types
	 *            原始分类集合
	 * @param isShowNum
	 *            是否显示课程数量（真实数量）
	 * @param isEditType
	 *            是否編輯模式（区别对待分类类型：结构，内容）
	 * @return
	 */
	public static List<Map<String, Object>> initData(List<ClassType> types, boolean isShowNum, boolean isWriteType) {
		Collections.sort(types, new Comparator<ClassType>() {
			@Override
			public int compare(ClassType o1, ClassType o2) {
				int n = o1.getTreecode().length() - o2.getTreecode().length();
				if (n == 0) {
					return o1.getSort() - o2.getSort();
				} else {
					return n;
				}
			}
		});
		List<Map<String, Object>> treeData = new ArrayList<>();
		Map<String, Map<String, Object>> treeDataMap = new HashMap<>();
		for (ClassType type : types) {
			Map<String, Object> node = null;
			// 构造当前节点
			if (treeDataMap.get(type.getId()) == null) {
				node = new HashMap<>();
				// 1结构，3内容
				String text = type.getType().equals("1") && isWriteType
						? ("<span style='color:#999999;'>" + type.getName() + "</span>") : type.getName();
				node.put("text", text);
				node.put("name", type.getName());
				node.put("type", type.getType());
				node.put("id", type.getId());
				node.put("num", type.getClassnum());
				treeDataMap.put(type.getId(), node);
			} else {
				node = (Map<String, Object>) treeDataMap.get(type.getId());
			}
			// ---------------------------
			// 挂载当前节点到数据结构中
			if (type.getParentid().equals("NONE")) {
				// 根节点
				treeData.add(node);
			} else {
				// 非根节点
				String parentid = type.getParentid();
				Map<String, Object> parentNode = (Map<String, Object>) treeDataMap.get(parentid);
				if (parentNode != null) {
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> nodes = (List<Map<String, Object>>) parentNode.get("nodes");
					if (nodes == null) {
						// 没有子节点则构造子节点序列
						nodes = new ArrayList<>();
						parentNode.put("nodes", nodes);
					}
					nodes.add(node);
				} else {
					log.warn("父机构为空，可能是组织机构中有父机构为禁用状态");
				}
			}
		}
		// 重新计算分类下课程数量
		for (Map<String, Object> node : treeData) {
			int num = countNums(node, isShowNum);
			node.put("num", num);
			if (isShowNum&&num>0) {
				node.put("text", node.get("text") + "<span class='wlp-type-falg'>" + num + "<span>");
			}
		}
		return treeData;
	}

	/**
	 * 计算当前分类实际知识数据（从叶子节点倒推来）
	 * 
	 * @param node
	 * @return
	 */
	private static int countNums(Map<String, Object> node, boolean isShowNum) {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> subNodes = (List) node.get("nodes");
		if (subNodes != null && subNodes.size() > 0) {
			int allnum = 0;
			// 有子分類
			for (Map<String, Object> subnode : subNodes) {
				int theNum = countNums(subnode, isShowNum);
				subnode.put("num", theNum);
				if (isShowNum&&theNum>0) {
					subnode.put("text", subnode.get("text") + "<span class='wlp-type-falg'>" + theNum + "<span>");
				}
				allnum = allnum + theNum;
			}
			return allnum;
		} else {
			// 無子分類
			return (int) node.get("num");
		}
	}
}
