<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>收藏对象数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<form id="searchFavoriteobjForm">
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
		<table id="dataFavoriteobjGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="APPTYPE" data-options="sortable:true" width="30">对象类型</th>
					<th field="APPID" data-options="sortable:true" width="70">对象APPID</th>
					<th field="CTIME" data-options="sortable:true" width="80">收藏时间</th>
					<th field="USERID" data-options="sortable:true" width="50">用户ID</th>
					<th field="USERNAME" data-options="sortable:true" width="20">用户名称</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_delActionFavoriteobj = "favoriteobj/del.do";//删除URL
	var url_formActionFavoriteobj = "favoriteobj/form.do";//增加、修改、查看URL
	var url_searchActionFavoriteobj = "favoriteobj/query.do";//查询URL
	var title_windowFavoriteobj = "收藏对象管理";//功能名称
	var gridFavoriteobj;//数据表格对象
	var searchFavoriteobj;//条件查询组件对象
	var toolBarFavoriteobj = [
	//{
	//	id : 'view',
	//	text : '查看',
	//	iconCls : 'icon-tip',
	//	handler : viewDataFavoriteobj
	//}, {
	//	id : 'add',
	//	text : '新增',
	//	iconCls : 'icon-add',
	//	handler : addDataFavoriteobj
	//}, {
	//	id : 'edit',
	//	text : '修改',
	//	iconCls : 'icon-edit',
	//	handler : editDataFavoriteobj
	//}, 
	{
		id : 'del',
		text : '取消收藏',
		iconCls : 'icon-remove',
		handler : delDataFavoriteobj
	} ];
	$(function() {
		//初始化数据表格
		gridFavoriteobj = $('#dataFavoriteobjGrid').datagrid({
			url : url_searchActionFavoriteobj,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarFavoriteobj,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchFavoriteobj = $('#searchFavoriteobjForm').searchForm({
			gridObj : gridFavoriteobj
		});
	});
	//查看
	function viewDataFavoriteobj() {
		var selectedArray = $(gridFavoriteobj).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionFavoriteobj + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winFavoriteobj',
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
	function addDataFavoriteobj() {
		var url = url_formActionFavoriteobj + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winFavoriteobj',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataFavoriteobj() {
		var selectedArray = $(gridFavoriteobj).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionFavoriteobj + '?operateType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winFavoriteobj',
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
	function delDataFavoriteobj() {
		var selectedArray = $(gridFavoriteobj).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridFavoriteobj).datagrid('loading');
					$.post(url_delActionFavoriteobj + '?ids='
							+ $.farm.getCheckedIds(gridFavoriteobj, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridFavoriteobj).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridFavoriteobj).datagrid('reload');
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