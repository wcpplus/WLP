<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!-- Modal -->
<div class="modal fade" id="classResourceForm" tabindex="-1"
	role="dialog" aria-labelledby="ResourceOpLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="ResourceOpLabel">课程资源</h4>
			</div>
			<div class="modal-body">
				<form method="post" id="submitResourceFormId"
					action="classweb/savebase.do">
					<input name="classid" id="classidId" type="hidden"
						value="${classview.classt.id}"> <input name="id"
						id="resourceId" type="hidden">
					<fieldset id="ResourcefieldsetId">
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group" id="hourGroupId">
									<label for="hourId" class="control-label">资源绑定到&nbsp; <span
										class="text-danger">*</span> 
									</label> <select name="hourid" id="hourId" val="" class="form-control">
										<option value="">本课程</option>
										<c:forEach items="${hours }" var="node">
											<option value="${node.id}">课时:${node.title}</option>
										</c:forEach>
									</select> <span id="hourErrorId" class="help-block"></span>
								</div>
								<div class="form-group" id="outsysGroupId">
									<label for="outsysid" class="control-label">外部系统&nbsp;
										<span class="text-danger">*</span><span
										style="font-size: 12px; color: #999999;">由系统管理员后台维护</span>
									</label> 
									<select name="outsysid" id="outsysid" val=""
										class="form-control">
										<option value=""></option>
										<c:forEach items="${sysses}" var="node">
											<option value="${node.id}">${node.sysname}</option>
										</c:forEach>
									</select> <span id="outsysErrorId" class="help-block"></span>
								</div>
								<div class="form-group" id="typeGroupId">
									<label for="typeId" class="control-label">资源类型&nbsp; <span
										class="text-danger">*</span>
									</label> <select name="resourcetype" id="typeId" val=""
										class="form-control">
										<option value=""></option>
										<option value="1">知识库</option>
										<option value="2">问答</option>
										<option value="3">测验</option>
										<option value="4">考试</option>
									</select> <span id="typeErrorId" class="help-block"></span>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group" id="urlGroupId">
									<label for="urlId" class="control-label">资源地址（相对地址）&nbsp;<span
										class="text-danger">*</span></label>
									<textarea class="form-control" id="urlId" name="note"
										placeholder="外部系统的相对路径,如：/webtype/view2c902a8d6dc8c1da016dc9035b900043/Pub1.html?typeDomainId=2c902a8d6dc8c1da016dc9035b900043"
										rows="3"></textarea>
									<span id="urlErrorId" class="help-block"></span>
								</div>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" id="ResourceSubmitButtonId"
					onclick="submitResourceForm();" class="btn btn-success">提交资源</button>
				<p class="bg-danger"
					style="padding: 20px; margin-top: 20px; color: #f01414; display: none;"
					id="ResourceErrorsShowboxId"></p>
			</div>
		</div>
	</div>
</div>
<script>
	$(function() {
		$('select', '#submitResourceFormId').each(function(i, obj) {
			var val = $(obj).attr('val');
			$(obj).val(val);
		});
		validateInput('hourId', function(id, val, obj) {
			// 标题
			return {
				valid : true,
				msg : '正确'
			};
		}, 'hourErrorId', 'hourGroupId');
		validateInput('outsysid', function(id, val, obj) {
			// 外部系统
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'outsysErrorId', 'outsysGroupId');
		validateInput('typeId', function(id, val, obj) {
			// 资源类型
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'typeErrorId', 'typeGroupId');
		validateInput('urlId', function(id, val, obj) {
			// 资源地址
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			if (valid_maxLength(val, 256)) {
				return {
					valid : false,
					msg : '长度不能大于' + 256
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'urlErrorId', 'urlGroupId');
	});
	//初始化表单
	function initResourceForm(id) {
		if (id) {
			$('#ResourceOpLabel').text("編輯资源");
			$('#ResourcefieldsetId,fieldset').attr('disabled', 'disabled');
			$('#ResourceSubmitButtonId').attr('disabled', 'disabled');
			$.post('classweb/loadResource.do', {
				'resourceid' : id
			}, function(flag) {
				if (flag.STATE == 0) {
					$('#ResourcefieldsetId,fieldset').removeAttr("disabled");
					$('#ResourceSubmitButtonId').removeAttr("disabled");
					$('#ResourcefieldsetId,#urlId').val(flag.resource.resourceurl);
					$('#ResourcefieldsetId,#typeId').val(flag.resource.resourcetype);
					$('#ResourcefieldsetId,#outsysid').val(flag.resource.sysid);
					$('#ResourcefieldsetId,#hourId').val(flag.hourid);
					$('#ResourcefieldsetId,#resourceId').val(id);
				} else {
					alert(flag.MESSAGE);
				}
			}, 'json');
		} else {
			$('#ResourcefieldsetId,#urlId').val('');
			$('#ResourcefieldsetId,#typeId').val('');
			$('#ResourcefieldsetId,#outsysid').val('');
			$('#ResourcefieldsetId,#hourId').val('');
			$('#ResourcefieldsetId,#resourceId').val('');
			$('#ResourceOpLabel').text("创建资源");
		}
	}

	//提交表单
	function submitResourceForm() {
		if (!validate('submitResourceFormId')) {
			$('#ResourceErrorsShowboxId').text('信息录入有误，请检查！');
			$('#ResourceErrorsShowboxId').show();
			return;
		}
		$('#ResourceErrorsShowboxId').text('');
		$('#ResourceErrorsShowboxId').hide();
		if (confirm("是否提交本资源？")) {
			$('#ResourceSubmitButtonId').attr('disabled', 'disabled');
			$.post('classweb/saveResource.do', {
				'url' : $('#urlId').val(),
				'type' : $('#typeId').val(),
				'outsysid' : $('#outsysid').val(),
				'classid' : $('#classidId').val(),
				'hourid' : $('#hourId').val(),
				'id' : $('#resourceId').val()
			}, function(flag) {
				if (flag.STATE == 0) {
					//alert("提交成功");
					$('#myModal').modal('hide')
					location.reload();
				} else {
					alert(flag.MESSAGE);
					$('#ResourcefieldsetId,fieldset').removeAttr("disabled");
					$('#ResourceSubmitButtonId').removeAttr("disabled");
				}
			}, 'json');
		}
	}
</script>