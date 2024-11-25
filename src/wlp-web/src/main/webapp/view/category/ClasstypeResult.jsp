<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>课程分类数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
<style>
<!--
.feild_red {
	color: red;
}

.feild_green {
	color: green;
}
-->
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:true,border:false"
		style="width: 250px;">
		<div class="TREE_COMMON_BOX_SPLIT_DIV">
			<a id="classTypeTreeReload" href="javascript:void(0)"
				class="easyui-linkbutton" data-options="plain:true"
				iconCls="icon-reload">刷新</a> <a id="classTypeTreeOpenAll"
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="plain:true" iconCls="icon-sitemap">展开</a>
		</div>
		<ul id="classTypeTree"></ul>
	</div>
	<div class="easyui-layout" data-options="region:'center',border:false">
		<div data-options="region:'north',border:false">
			<form id="searchClasstypeForm">
				<table class="editTable">
					<tr>
						<td class="title">上级节点:</td>
						<td><input id="PARENTTITLE_RULE" type="text"
							readonly="readonly" style="background: #F3F3E8"> <input
							id="PARENTID_RULE" name="PARENTID:=" type="hidden"></td>
						<td class="title">名称:</td>
						<td><input name="NAME:like" type="text"></td>
					</tr>
					<tr style="text-align: center;">
						<td colspan="4"><a id="a_search" href="javascript:void(0)"
							class="easyui-linkbutton" iconCls="icon-search">查询</a> <a
							id="a_reset" href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-reload">清除条件</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataClasstypeGrid">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="NAME" data-options="sortable:true" width="40">名称</th>
						<th field="SORT" data-options="sortable:true" width="40">排序</th>
						<th field="TYPE" data-options="sortable:true" width="35">类型</th>
						<th field="READPOP" data-options="sortable:true" width="35">浏览权限</th>
						<th field="WRITEPOP" data-options="sortable:true" width="35">编辑权限</th>
						<th field="CLASSNUM" data-options="sortable:true" width="25">课程数</th>
						<th field="PCONTENT" data-options="sortable:true" width="80">备注</th>
						<th field="PSTATE" data-options="sortable:true" width="60">状态</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<div id="toolBarEditor">
		<a class="easyui-linkbutton"
			data-options="iconCls:'icon-tip',plain:true,onClick:viewDataClasstype">查看
		</a> <a class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true,onClick:addDataClasstype">新增
		</a> <a class="easyui-linkbutton"
			data-options="iconCls:'icon-edit',plain:true,onClick:editDataClasstype">修改
		</a> <a class="easyui-linkbutton"
			data-options="iconCls:'icon-remove',plain:true,onClick:delDataClasstype">删除
		</a> <a class="easyui-linkbutton"
			data-options="iconCls:'icon-communication',plain:true,onClick:moveTree">移动
		</a> <a class="easyui-linkbutton"
			data-options="iconCls:'icon-reload',plain:true,onClick:freshdocs">数量刷新
		</a> <a href="javascript:void(0)" class="easyui-menubutton"
			data-options="menu:'#mm2',iconCls:'icon-lock'">权限控制</a>
		<div id="mm2" style="width: 150px;">
			<div data-options="iconCls:'icon-zoom'" onclick="setTypePop('1','课程学习')">课程学习权限</div>
			<div data-options="iconCls:'icon-page_paintbrush'"
				onclick="setTypePop('2','课程维护')">课程维护权限</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var url_delActionClasstype = "classtype/del.do";//删除URL
	var url_formActionClasstype = "classtype/form.do";//增加、修改、查看URL
	var url_searchActionClasstype = "classtype/query.do";//查询URL
	var title_windowClasstype = "课程分类管理";//功能名称
	var gridClasstype;//数据表格对象
	var searchClasstype;//条件查询组件对象
	$(function() {
		//初始化数据表格
		gridClasstype = $('#dataClasstypeGrid').datagrid({
			url : url_searchActionClasstype,
			fit : true,
			fitColumns : true,
			'toolbar' : '#toolBarEditor',
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchClasstype = $('#searchClasstypeForm').searchForm({
			gridObj : gridClasstype
		});
		$('#classTypeTree').tree({
			url : 'classtype/classtypeTree.do',
			onSelect : function(node) {
				$('#PARENTID_RULE').val(node.id);
				$('#PARENTTITLE_RULE').val(node.text);
				searchClasstype.dosearch({
					'ruleText' : searchClasstype.arrayStr()
				});
			}
		});
		$('#classTypeTreeReload').bind('click', function() {
			$('#classTypeTree').tree('reload');
		});
		$('#classTypeTreeOpenAll').bind('click', function() {
			$('#classTypeTree').tree('expandAll');
		});
	});
	//权限配置
	function setTypePop(functype, poptitle) {
		var selectedArray = $(gridClasstype).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = 'classtypepop/settingPage.do?functype=' + functype
					+ '&typeid=' + $.farm.getCheckedIds(gridClasstype, 'ID');
			$.farm.openWindow({
				id : 'winDoctype',
				width : 600,
				height : 500,
				modal : true,
				url : url,
				title : '设置' + poptitle + '权限'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}

	//刷新课程数量
	function freshdocs() {
		var selectedArray = $(gridClasstype).datagrid('getSelections');
		var tipStr = "是否立即执行刷新操作?";
		if (selectedArray.length <= 0) {
			tipStr = "未选择分类将对所有分类进行计算,该操作有可能非常漫长,是否立即执行刷新操作?";
		}
		$.messager.confirm(MESSAGE_PLAT.PROMPT, tipStr, function(flag) {
			if (flag) {
				$(gridClasstype).datagrid('loading');
				$.post('classtype/freshnums.do', {
					ids : $.farm.getCheckedIds(gridClasstype, 'ID')
				}, function(flag) {
					$(gridClasstype).datagrid('loaded');
					if (flag.STATE == 0) {
						$(gridClasstype).datagrid('reload');
					} else {
						var str = MESSAGE_PLAT.ERROR_SUBMIT + flag.MESSAGE;
						$.messager.alert(MESSAGE_PLAT.ERROR, str, 'error');
					}
				}, 'json');
			}
		});
	}
	//查看
	function viewDataClasstype() {
		var selectedArray = $(gridClasstype).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionClasstype + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winClasstype',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '浏览'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//新增
	function addDataClasstype() {
		var parentID = $("#PARENTID_RULE").val();
		var url = url_formActionClasstype + '?operateType=' + PAGETYPE.ADD
				+ '&parentId=' + parentID;
		$.farm.openWindow({
			id : 'winClasstype',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataClasstype() {
		var selectedArray = $(gridClasstype).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionClasstype + '?operateType=' + PAGETYPE.EDIT
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winClasstype',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '修改'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//删除
	function delDataClasstype() {
		var selectedArray = $(gridClasstype).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridClasstype).datagrid('loading');
					$.post(url_delActionClasstype + '?ids='
							+ $.farm.getCheckedIds(gridClasstype, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridClasstype).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridClasstype).datagrid('reload');
									$.messager.confirm('确认对话框',
											'数据更新,是否重新加载左侧组织机构树？', function(r) {
												if (r) {
													$('#classTypeTree').tree(
															'reload');
												}
											});
								} else {
									var str = MESSAGE_PLAT.ERROR_SUBMIT
											+ jsonObject.MESSAGE;
									$.messager.alert(MESSAGE_PLAT.ERROR, str,
											'error');
								}
							});
				}
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
	//移动节点
	function moveTree() {
		var selectedArray = $(gridClasstype).datagrid('getSelections');
		if (selectedArray.length > 0) {
			$.farm.openWindow({
				id : 'classtypeTreeNodeWin',
				width : 250,
				height : 300,
				modal : true,
				url : "classtype/treeNodeTreeView.do",
				title : '移动分类'
			});
			chooseWindowCallBackHandle = function(node) {
				$.messager.confirm('确认对话框', '确定移动该节点么？', function(falg1) {
					if (falg1) {
						$.post('classtype/moveTreeNodeSubmit.do', {
							ids : $.farm.getCheckedIds(gridClasstype, 'ID'),
							id : node.id
						}, function(flag) {
							if (flag.STATE == 0) {
								$(gridClasstype).datagrid('reload');
								$('#classtypeTreeNodeWin').window('close');
								$.messager.confirm('确认对话框', '数据更新,是否重新加载左侧树？',
										function(r) {
											if (r) {
												$('#classTypeTree').tree(
														'reload');
											}
										});
							} else {
								var str = MESSAGE_PLAT.ERROR_SUBMIT
										+ flag.MESSAGE;
								$.messager.alert(MESSAGE_PLAT.ERROR, str,
										'error');
							}
						}, 'json');
					}
				});
			};
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
</script>
</html>