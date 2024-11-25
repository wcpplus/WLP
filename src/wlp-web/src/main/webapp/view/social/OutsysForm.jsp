<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--外部系统表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary"> <c:if
				test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</span>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formOutsys">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">系统名称:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32]']"
						id="entity_sysname" name="sysname" value="${entity.sysname}">
					</td>
				</tr>
				<tr>
					<td class="title" rowspan="2">根地址:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[256]']"
						id="entity_baseurl" name="baseurl" value="${entity.baseurl}">
					</td>
				</tr>
				<tr>
					<td colspan="3">此处只填写地址根路径，等綁定具体页面时再填写相对地址</td>
				</tr>
				<tr>
					<td class="title">备注:</td>
					<td colspan="3"><textarea rows="2" style="width: 360px;"
							class="easyui-validatebox"
							data-options="validType:[,'maxLength[64]']" id="entity_pcontent"
							name="pcontent">${entity.pcontent}</textarea></td>
				</tr>
				<tr>
					<td class="title">状态:</td>
					<td colspan="3"><select style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[1]']"
						id="entity_pstate" name="pstate" val="${entity.pstate}"><option
								value="1">可用</option>
							<option value="0">禁用</option></select></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityOutsys" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityOutsys" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formOutsys" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionOutsys = 'outsys/add.do';
	var submitEditActionOutsys = 'outsys/edit.do';
	var currentPageTypeOutsys = '${pageset.operateType}';
	var submitFormOutsys;
	$(function() {
		//表单组件对象
		submitFormOutsys = $('#dom_formOutsys').SubmitForm({
			pageType : currentPageTypeOutsys,
			grid : gridOutsys,
			currentWindowId : 'winOutsys'
		});
		//关闭窗口
		$('#dom_cancle_formOutsys').bind('click', function() {
			$('#winOutsys').window('close');
		});
		//提交新增数据
		$('#dom_add_entityOutsys').bind('click', function() {
			submitFormOutsys.postSubmit(submitAddActionOutsys);
		});
		//提交修改数据
		$('#dom_edit_entityOutsys').bind('click', function() {
			submitFormOutsys.postSubmit(submitEditActionOutsys);
		});
	});
//-->
</script>