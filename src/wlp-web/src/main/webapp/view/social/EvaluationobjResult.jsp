<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>评论对象数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<form id="searchEvaluationobjForm">
			<table class="editTable">
				<tr>
					<td class="title">对象APPID:</td>
					<td><input name="B.APPID:like" type="text"></td>
					<td class="title">对象类型:</td>
					<td><select name="B.APPTYPE:="><option value=""></option>
							<option value="1">专业</option>
							<option value="2">课程</option>
							<option value="3">课时</option></select></td>
					<td class="title">人员ID:</td>
					<td><input name="C.ID:like" type="text"></td>
					<td class="title">评价内容:</td>
					<td><input name="A.NOTE:like" type="text"></td>
				</tr>
				<tr style="text-align: center;">
					<td colspan="8"><a id="a_search" href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-search">查询</a> <a
						id="a_reset" href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-reload">清除条件</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataEvaluationobjGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="APPTYPE" data-options="sortable:true" width="30">对象类型</th>
					<th field="APPID" data-options="sortable:true" width="70">对象APPID</th>
					<th field="NUM" data-options="sortable:true" width="30">评价人数</th>
					<th field="ALLSCORE" data-options="sortable:true" width="30">平均得分</th>
					<th field="USERNAME" data-options="sortable:true" width="50">用户名称</th>
					<th field="USERID" data-options="sortable:true" width="70">用户ID</th>
					<th field="CTIME" data-options="sortable:true" width="50">评价时间</th>
					<th field="NOTE" data-options="sortable:true" width="150">评价内容</th>
					<th field="SCORE" data-options="sortable:true" width="30">评价分数</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_delActionEvaluationobj = "evaluationobj/del.do";//删除URL
	var url_formActionEvaluationobj = "evaluationobj/form.do";//增加、修改、查看URL
	var url_searchActionEvaluationobj = "evaluationobj/query.do";//查询URL
	var title_windowEvaluationobj = "评论对象管理";//功能名称
	var gridEvaluationobj;//数据表格对象
	var searchEvaluationobj;//条件查询组件对象
	var toolBarEvaluationobj = [
	// {
	//	id : 'view',
	//	text : '查看',
	//	iconCls : 'icon-tip',
	//	handler : viewDataEvaluationobj
	//}, {
	//	id : 'add',
	//	text : '新增',
	//	iconCls : 'icon-add',
	//	handler : addDataEvaluationobj
	//}, {
	//	id : 'edit',
	//	text : '修改',
	//	iconCls : 'icon-edit',
	//	handler : editDataEvaluationobj
	//},
	{
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataEvaluationobj
	} ];
	$(function() {
		//初始化数据表格
		gridEvaluationobj = $('#dataEvaluationobjGrid').datagrid({
			url : url_searchActionEvaluationobj,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarEvaluationobj,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchEvaluationobj = $('#searchEvaluationobjForm').searchForm({
			gridObj : gridEvaluationobj
		});
	});
	//查看
	function viewDataEvaluationobj() {
		var selectedArray = $(gridEvaluationobj).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionEvaluationobj + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winEvaluationobj',
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
	function addDataEvaluationobj() {
		var url = url_formActionEvaluationobj + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winEvaluationobj',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataEvaluationobj() {
		var selectedArray = $(gridEvaluationobj).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionEvaluationobj + '?operateType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winEvaluationobj',
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
	function delDataEvaluationobj() {
		var selectedArray = $(gridEvaluationobj).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridEvaluationobj).datagrid('loading');
					$.post(url_delActionEvaluationobj + '?ids='
							+ $.farm.getCheckedIds(gridEvaluationobj, 'ID'),
							{}, function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridEvaluationobj).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridEvaluationobj).datagrid('reload');
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