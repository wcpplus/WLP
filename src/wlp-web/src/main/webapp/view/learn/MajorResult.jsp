<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>专业数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
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
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<form id="searchMajorForm">
			<table class="editTable">
				<tr>
					<td class="title">专业名称:</td>
					<td><input name="TITLE:like" type="text"></td>
					<td class="title">状态:</td>
					<td><select name="PSTATE:=">
							<option value=""></option>
							<option value="1">发布</option>
							<option value="0">待发布</option>
					</select></td>
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
		<table id="dataMajorGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="TITLE" data-options="sortable:true" width="150">专业名称</th>
					<th field="SORT" data-options="sortable:true" width="40">排序</th>
					<th field="CHAPTERNUM" data-options="sortable:true" width="40">章节数量</th>
					<th field="CLASSNUM" data-options="sortable:true" width="40">课程数量</th>
					<th field="ENJOYNUM" data-options="sortable:true" width="40">收藏数量</th>
					<th field="EUSERNAME" data-options="sortable:true" width="40">編輯人</th>
					<th field="ETIME" data-options="sortable:true" width="50">編輯时间</th>
					<th field="READPOP" data-options="sortable:true" width="40">学习权限</th>
					<th field="WRITEPOP" data-options="sortable:true" width="40">维护权限</th>
					<th field="PSTATE" data-options="sortable:true" width="40">状态</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolBarEditor">
	<!-- <a class="easyui-linkbutton"
			data-options="iconCls:'icon-tip',plain:true,onClick:viewDataMajor">查看
		</a>  -->	
		<a class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true,onClick:addDataMajor">新增
		</a>
		<a class="easyui-linkbutton"
			data-options="iconCls:'icon-edit',plain:true,onClick:editDataMajor">修改
		</a>
		<a class="easyui-linkbutton"
			data-options="iconCls:'icon-remove',plain:true,onClick:delDataMajor">删除
		</a>
		<a href="javascript:void(0)" class="easyui-menubutton"
			data-options="menu:'#mm2',iconCls:'icon-lock'">权限控制</a>
		<div id="mm2" style="width: 150px;">
			<div data-options="iconCls:'icon-zoom'" onclick="setPop('1','学习')">学习权限</div>
			<div data-options="iconCls:'icon-page_paintbrush'"
				onclick="setPop('2','维护')">维护权限</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var url_delActionMajor = "major/del.do";//删除URL
	var url_formActionMajor = "major/form.do";//增加、修改、查看URL
	var url_searchActionMajor = "major/query.do";//查询URL
	var title_windowMajor = "专业管理";//功能名称
	var gridMajor;//数据表格对象
	var searchMajor;//条件查询组件对象
	$(function() {
		//初始化数据表格
		gridMajor = $('#dataMajorGrid').datagrid({
			url : url_searchActionMajor,
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
		searchMajor = $('#searchMajorForm').searchForm({
			gridObj : gridMajor
		});
	});
	//查看
	function viewDataMajor() {
		var selectedArray = $(gridMajor).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionMajor + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winMajor',
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
	function addDataMajor() {
		var url = url_formActionMajor + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winMajor',
			width : 600,
			height : 200,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataMajor() {
		var selectedArray = $(gridMajor).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionMajor + '?operateType=' + PAGETYPE.EDIT
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winMajor',
				width : 600,
				height : 200,
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
	function delDataMajor() {
		var selectedArray = $(gridMajor).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridMajor).datagrid('loading');
					$.post(url_delActionMajor + '?ids='
							+ $.farm.getCheckedIds(gridMajor, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridMajor).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridMajor).datagrid('reload');
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
	//权限配置
	function setPop(functype, poptitle) {
		var selectedArray = $(gridMajor).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = 'majorpop/settingPage.do?functype=' + functype
					+ '&majorid=' + $.farm.getCheckedIds(gridMajor, 'ID');
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
</script>
</html>