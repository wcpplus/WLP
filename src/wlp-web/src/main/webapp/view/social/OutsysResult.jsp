<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>外部系统数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<form id="searchOutsysForm">
			<table class="editTable">
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
		<table id="dataOutsysGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="SYSNAME" data-options="sortable:true" width="50">系统名称</th>
					<th field="BASEURL" data-options="sortable:true" width="100">根地址</th>
					<th field="PCONTENT" data-options="sortable:true" width="100">备注</th>
					<th field="PSTATE" data-options="sortable:true" width="30">状态</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_delActionOutsys = "outsys/del.do";//删除URL
	var url_formActionOutsys = "outsys/form.do";//增加、修改、查看URL
	var url_searchActionOutsys = "outsys/query.do";//查询URL
	var title_windowOutsys = "外部系统管理";//功能名称
	var gridOutsys;//数据表格对象
	var searchOutsys;//条件查询组件对象
	var toolBarOutsys = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDataOutsys
	}, {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDataOutsys
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDataOutsys
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataOutsys
	} ];
	$(function() {
		//初始化数据表格
		gridOutsys = $('#dataOutsysGrid').datagrid({
			url : url_searchActionOutsys,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarOutsys,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchOutsys = $('#searchOutsysForm').searchForm({
			gridObj : gridOutsys
		});
	});
	//查看
	function viewDataOutsys() {
		var selectedArray = $(gridOutsys).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionOutsys + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winOutsys',
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
	function addDataOutsys() {
		var url = url_formActionOutsys + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winOutsys',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataOutsys() {
		var selectedArray = $(gridOutsys).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionOutsys + '?operateType=' + PAGETYPE.EDIT
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winOutsys',
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
	function delDataOutsys() {
		var selectedArray = $(gridOutsys).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridOutsys).datagrid('loading');
					$.post(url_delActionOutsys + '?ids='
							+ $.farm.getCheckedIds(gridOutsys, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridOutsys).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridOutsys).datagrid('reload');
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