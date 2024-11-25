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
		<form id="searchCommentsobjForm">
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
					<td class="title">评论内容:</td>
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
		<table id="dataCommentsobjGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="APPTYPE" data-options="sortable:true" width="30">对象类型</th>
					<th field="APPID" data-options="sortable:true" width="70">对象APPID</th>
					<th field="NUM" data-options="sortable:true" width="30">评论人数</th>
					<th field="USERNAME" data-options="sortable:true" width="40">用户名称</th>
					<th field="USERID" data-options="sortable:true" width="70">用户ID</th>
					<th field="CTIME" data-options="sortable:true" width="50">评论时间</th>
					<th field="NOTE" data-options="sortable:true" width="200">评论内容</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_delActionCommentsobj = "commentsobj/del.do";//删除URL
	var url_formActionCommentsobj = "commentsobj/form.do";//增加、修改、查看URL
	var url_searchActionCommentsobj = "commentsobj/query.do";//查询URL
	var title_windowCommentsobj = "评论对象管理";//功能名称
	var gridCommentsobj;//数据表格对象
	var searchCommentsobj;//条件查询组件对象
	var toolBarCommentsobj = [ //{
	//	id : 'view',
	//	text : '查看',
	//	iconCls : 'icon-tip',
	//	handler : viewDataCommentsobj
	//}, {
	//	id : 'add',
	//	text : '新增',
	//	iconCls : 'icon-add',
	//	handler : addDataCommentsobj
	//}, {
	//	id : 'edit',
	//	text : '修改',
	//	iconCls : 'icon-edit',
	//	handler : editDataCommentsobj
	//}, 
	{
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataCommentsobj
	} ];
	$(function() {
		//初始化数据表格
		gridCommentsobj = $('#dataCommentsobjGrid').datagrid({
			url : url_searchActionCommentsobj,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarCommentsobj,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchCommentsobj = $('#searchCommentsobjForm').searchForm({
			gridObj : gridCommentsobj
		});
	});
	//查看
	function viewDataCommentsobj() {
		var selectedArray = $(gridCommentsobj).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionCommentsobj + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winCommentsobj',
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
	function addDataCommentsobj() {
		var url = url_formActionCommentsobj + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winCommentsobj',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataCommentsobj() {
		var selectedArray = $(gridCommentsobj).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionCommentsobj + '?operateType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winCommentsobj',
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
	function delDataCommentsobj() {
		var selectedArray = $(gridCommentsobj).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridCommentsobj).datagrid('loading');
					$.post(url_delActionCommentsobj + '?ids='
							+ $.farm.getCheckedIds(gridCommentsobj, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridCommentsobj).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridCommentsobj).datagrid('reload');
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