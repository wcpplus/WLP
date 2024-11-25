<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>课程课时数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
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
			<form id="searchClasshourForm">
				<table class="editTable">
					<tr>
						<td class="title">课程分类:</td>
						<td><input id="PARENTTITLE_RULE" type="text"
							readonly="readonly" style="background: #F3F3E8"> <input
							id="PARENTID_RULE" name="TYPEID:=" type="hidden"></td>
						<td class="title">课程标题:</td>
						<td><input name="A.NAME:like" type="text"></td>
						<td class="title">章节标题:</td>
						<td><input name="E.TITLE:like" type="text"></td>
					</tr>
					<tr>
						<td class="title">课时标题:</td>
						<td><input name="D.TITLE:like" type="text"></td>
						<td class="title">课时ID:</td>
						<td><input name="D.ID:like" type="text"></td>
						<td class="title"></td>
						<td></td>
					</tr>
					<tr style="text-align: center;">
						<td colspan="6"><a id="a_search" href="javascript:void(0)"
							class="easyui-linkbutton" iconCls="icon-search">查询</a> <a
							id="a_reset" href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-reload">清除条件</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataClasshourGrid">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="TYPENAME" data-options="sortable:true" width="40">课程分类</th>
						<th field="CLASSNAME" data-options="sortable:true" width="40">课程名称</th>
						<th field="CHAPTERNAME" data-options="sortable:true" width="40">章节名称</th>
						<th field="NAME" data-options="sortable:true" width="40">课时名称</th>
						<th field="ID" data-options="sortable:true" width="40">课时ID</th>
						<th field="ALTIME" data-options="sortable:true" width="20">学习时长(分)</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">
	var url_delActionClasshour = "classhour/del.do";//删除URL
	var url_formActionClasshour = "classhour/form.do";//增加、修改、查看URL
	var url_searchActionClasshour = "classhour/query.do";//查询URL
	var title_windowClasshour = "课程课时管理";//功能名称
	var gridClasshour;//数据表格对象
	var searchClasshour;//条件查询组件对象
	var toolBarClasshour = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDataClasshour
	}, {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDataClasshour
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDataClasshour
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataClasshour
	} ];
	$(function() {
		//初始化数据表格
		gridClasshour = $('#dataClasshourGrid').datagrid({
			url : url_searchActionClasshour,
			fit : true,
			fitColumns : true,
			//'toolbar' : toolBarClasshour,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchClasshour = $('#searchClasshourForm').searchForm({
			gridObj : gridClasshour
		});
		$('#classTypeTree').tree({
			url : 'classtype/classtypeTree.do',
			onSelect : function(node) {
				$('#PARENTID_RULE').val(node.id);
				$('#PARENTTITLE_RULE').val(node.text);
				searchClasshour.dosearch({
					'ruleText' : searchClasshour.arrayStr()
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
	//查看
	function viewDataClasshour() {
		var selectedArray = $(gridClasshour).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionClasshour + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winClasshour',
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
	function addDataClasshour() {
		var url = url_formActionClasshour + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winClasshour',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataClasshour() {
		var selectedArray = $(gridClasshour).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionClasshour + '?operateType=' + PAGETYPE.EDIT
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winClasshour',
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
	function delDataClasshour() {
		var selectedArray = $(gridClasshour).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridClasshour).datagrid('loading');
					$.post(url_delActionClasshour + '?ids='
							+ $.farm.getCheckedIds(gridClasshour, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridClasshour).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridClasshour).datagrid('reload');
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
</script>
</html>