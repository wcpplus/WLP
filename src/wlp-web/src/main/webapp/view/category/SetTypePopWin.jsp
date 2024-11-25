<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--设置阅读权限-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="dom_chooseSearchSetreadpop">
			<table class="editTable">
				<tr>
					<td class="title">分类名称:</td>
					<td>${typename}</td>
				</tr>
				<!-- <tr>
					<td class="title">继承权限:</td>
					<td><c:if test="${extendmsg=='0'}">所有人</c:if> <c:if
							test="${extendmsg=='1'}">限制</c:if><c:if
							test="${extendmsg=='3'}">无</c:if></td>
				</tr> -->
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table class="easyui-datagrid" id="dom_chooseGridSetreadpop">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="POPTYPE" data-options="sortable:true" width="80">对象类型</th>
					<th field="ONAME" data-options="sortable:true" width="80">名称</th>
					<th field="FUNTYPE" data-options="sortable:true" width="80">来源</th>
					<th field="TYPENAME" data-options="sortable:true" width="80">分类名称</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var chooseGridSetreadpop;
	var chooseSearchfarmSetreadpop;
	var toolbar_chooseSetreadpop = [ {
		text : '添加用户',
		iconCls : 'icon-hire-me',
		handler : function() {
			$.farm.openWindow({
				id : 'chooseUserWin',
				width : 600,
				height : 400,
				modal : true,
				url : 'user/chooseUser.do',
				title : '选择用户'
			});
		}
	}, {
		text : '添加组织机构',
		iconCls : 'icon-customers',
		handler : function() {
			$.farm.openWindow({
				id : 'chooseOrgWin',
				width : 300,
				height : 400,
				modal : true,
				url : 'organization/chooseOrg.do',
				title : '选择组织机构'
			});
		}
	}, {
		text : '添加岗位',
		iconCls : 'icon-credit-card',
		handler : function() {
			$.farm.openWindow({
				id : 'choosePostWin',
				width : 600,
				height : 400,
				modal : true,
				url : 'post/choosePost.do',
				title : '选择岗位'
			});
		}
	}, {
		text : '删除',
		iconCls : 'icon-remove',
		handler : delPop
	} ];
	//删除权限
	function delPop() {
		var selectedArray = $(chooseGridSetreadpop).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$.messager.progress({
						text : '处理中...'
					});
					$(chooseGridSetreadpop).datagrid('loading');
					$.post('classtypepop/delPop.do?ids='
							+ $.farm.getCheckedIds(chooseGridSetreadpop, 'ID'),
							{}, function(flag) {
								$.messager.progress('close');
								var jsonObject = JSON.parse(flag, null);
								$(chooseGridSetreadpop).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(chooseGridSetreadpop).datagrid('reload');
									$(gridClasstype).datagrid('reload');
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
	//添加组织机构到权限中
	function chooseOrgWindowCallBackHandle(node) {
		$.messager.progress({
			text : '处理中...'
		});
		$.post('classtypepop/addOrgToPop.do', {
			orgid : node.id,
			typeid : '${id}',
			type : '${type}'
		}, function(flag) {
			$.messager.progress('close');
			if (flag.STATE == '0') {
				$.messager.alert(MESSAGE_PLAT.PROMPT, "组织机构添加成功!", 'info');
				$(chooseGridSetreadpop).datagrid('reload');
				$(gridClasstype).datagrid('reload');
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, flag.MESSAGE, 'error');
			}
		}, 'json');
	}

	//添加用户到权限中
	function chooseWindowCallBackHandle(selectedArray) {
		$.messager.progress({
			text : '处理中...'
		});
		var userids;
		$(selectedArray).each(function(i, obj) {
			if (userids) {
				userids = userids + ',' + obj.ID;
			} else {
				userids = obj.ID;
			}
		});
		$.post('classtypepop/addUserToPop.do', {
			userid : userids,
			typeid : '${id}',
			type : '${type}'
		}, function(flag) {
			$.messager.progress('close');
			$("#chooseUserWin").window('close');
			if (flag.STATE == '0') {
				$(chooseGridSetreadpop).datagrid('reload');
				$(gridClasstype).datagrid('reload');
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, flag.MESSAGE, 'error');
			}
		}, 'json');
	}
	//添加岗位到权限中
	function chooseWindowCallBackHandleForPost(selectedArray) {
		$.messager.progress({
			text : '处理中...'
		});
		var postids;
		$(selectedArray).each(function(i, obj) {
			if (postids) {
				postids = postids + ',' + obj.ID;
			} else {
				postids = obj.ID;
			}
		});
		$.post('classtypepop/addPostToPop.do', {
			postid : postids,
			typeid : '${id}',
			type : '${type}'
		}, function(flag) {
			$.messager.progress('close');
			$("#choosePostWin").window('close');
			if (flag.STATE == '0') {
				$(chooseGridSetreadpop).datagrid('reload');
				$(gridClasstype).datagrid('reload');
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, flag.MESSAGE, 'error');
			}
		}, 'json');
	}

	$(function() {
		chooseGridSetreadpop = $('#dom_chooseGridSetreadpop').datagrid({
			url : 'classtypepop/SetTypePopQuery.do?id=${id}&type=${type}',
			fit : true,
			'toolbar' : toolbar_chooseSetreadpop,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		chooseSearchfarmSetreadpop = $('#dom_chooseSearchSetreadpop')
				.searchForm({
					gridObj : chooseGridSetreadpop
				});
	});
//-->
</script>