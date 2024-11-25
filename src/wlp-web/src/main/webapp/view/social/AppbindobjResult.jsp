<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>应用绑定对象数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<form id="searchAppbindobjForm">
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
					<td class="title">相对地址:</td>
					<td><input name="A.RESOURCEURL:like" type="text"></td>
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
		<table id="dataAppbindobjGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="APPTYPE" data-options="sortable:true" width="30">对象类型</th>
					<th field="APPID" data-options="sortable:true" width="100">对象APPID</th>
					<th field="NUM" data-options="sortable:true" width="30">绑定资源数量</th>
					<th field="USERNAME" data-options="sortable:true" width="50">用户名称</th>
					<th field="USERID" data-options="sortable:true" width="20">用户ID</th>
					<th field="CTIME" data-options="sortable:true" width="80">绑定时间</th>
					<th field="SYSNAME" data-options="sortable:true" width="50">绑定系统</th>
					<th field="RESOURCEURL" data-options="sortable:true" width="150">相对地址</th>
					<th field="RESOURCETYPE" data-options="sortable:true" width="30">应用类型</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_delActionAppbindobj = "appbindobj/del.do";//删除URL
	var url_formActionAppbindobj = "appbindobj/form.do";//增加、修改、查看URL
	var url_searchActionAppbindobj = "appbindobj/query.do";//查询URL
	var title_windowAppbindobj = "应用绑定对象管理";//功能名称
	var gridAppbindobj;//数据表格对象
	var searchAppbindobj;//条件查询组件对象
	var toolBarAppbindobj = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDataAppbindobj
	}, {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDataAppbindobj
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDataAppbindobj
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataAppbindobj
	} ];
	$(function() {
		//初始化数据表格
		gridAppbindobj = $('#dataAppbindobjGrid').datagrid({
			url : url_searchActionAppbindobj,
			fit : true,
			fitColumns : true,
			//'toolbar' : toolBarAppbindobj,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchAppbindobj = $('#searchAppbindobjForm').searchForm({
			gridObj : gridAppbindobj
		});
	});
	//查看
	function viewDataAppbindobj() {
		var selectedArray = $(gridAppbindobj).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionAppbindobj + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winAppbindobj',
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
	function addDataAppbindobj() {
		var url = url_formActionAppbindobj + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winAppbindobj',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataAppbindobj() {
		var selectedArray = $(gridAppbindobj).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionAppbindobj + '?operateType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winAppbindobj',
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
	function delDataAppbindobj() {
		var selectedArray = $(gridAppbindobj).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridAppbindobj).datagrid('loading');
					$.post(url_delActionAppbindobj + '?ids='
							+ $.farm.getCheckedIds(gridAppbindobj, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridAppbindobj).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridAppbindobj).datagrid('reload');
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