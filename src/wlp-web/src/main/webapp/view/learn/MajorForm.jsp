<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--专业表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary"> <c:if
				test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</span>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formMajor">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">专业名称:</td>
					<td ><input type="text" style="width: 120px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[128]']"
						id="entity_title" name="title" value="${entity.title}"></td>
					<td class="title">排序:</td>
					<td ><input type="text" style="width: 120px;"
						class="easyui-validatebox"
						data-options="required:true,validType:['integer','maxLength[5]']"
						id="entity_sort" name="sort" value="${entity.sort}"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityMajor" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityMajor" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formMajor" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionMajor = 'major/add.do';
	var submitEditActionMajor = 'major/edit.do';
	var currentPageTypeMajor = '${pageset.operateType}';
	var submitFormMajor;
	$(function() {
		//表单组件对象
		submitFormMajor = $('#dom_formMajor').SubmitForm({
			pageType : currentPageTypeMajor,
			grid : gridMajor,
			currentWindowId : 'winMajor'
		});
		//关闭窗口
		$('#dom_cancle_formMajor').bind('click', function() {
			$('#winMajor').window('close');
		});
		//提交新增数据
		$('#dom_add_entityMajor').bind('click', function() {
			submitFormMajor.postSubmit(submitAddActionMajor);
		});
		//提交修改数据
		$('#dom_edit_entityMajor').bind('click', function() {
			submitFormMajor.postSubmit(submitEditActionMajor);
		});
	});
//-->
</script>