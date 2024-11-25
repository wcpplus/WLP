<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!-- Modal -->
<div class="modal fade" id="commentsForm" tabindex="-1" role="dialog"
	aria-labelledby="commentsOpLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="commentsOpLabel">评论</h4>
			</div>
			<div class="modal-body">
				<form method="post" id="submitcommentsFormId"
					action="classweb/savebase.do">
					<input name="appid" id="commentsAppidId" type="hidden"
						value="${param.appid}"> <input name="apptype"
						id="commentsApptypeId" type="hidden" value="${param.apptype}">
					<input name="parentid" id="commentsParentidId" type="hidden">
					<fieldset id="commentsfieldsetId">
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group" id="noteGroupId">
									<textarea class="form-control" id="commentsNoteId" name="note"
										placeholder="请录入评论内容......" rows="3"></textarea>
									<span id="noteErrorId" class="help-block"></span>
								</div>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" id="commentsSubmitButtonId"
					onclick="submitcommentsForm();" class="btn btn-success">发表评论</button>
			</div>
		</div>
	</div>
</div>
<script>
	$(function() {
		validateInput('noteId', function(id, val, obj) {
			// 课程简介
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			if (valid_maxLength(val, 512)) {
				return {
					valid : false,
					msg : '长度不能大于' + 512
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'noteErrorId', 'noteGroupId');
	});
	//提交表单
	function submitcommentsForm() {
		if (!validate('submitcommentsFormId')) {
			return;
		}
		if (confirm("是否立即发布评论？")) {
			$('#commentsSubmitButtonId').attr('disabled', 'disabled');
			$('#commentsfieldsetId,fieldset').attr('disabled', 'disabled');
			$.post('socialweb/submitComments.do', {
				'appid' : $('#commentsAppidId').val(),
				'apptype' : $('#commentsApptypeId').val(),
				'parentid' : $('#commentsParentidId').val(),
				'comments' : $('#commentsNoteId').val()
			}, function(flag) {
				if (flag.STATE == 0) {
					$('#commentsForm').modal('hide')
					$('#commentsfieldsetId,fieldset').removeAttr("disabled");
					$('#commentsSubmitButtonId').removeAttr("disabled");
					loadComments(1);
				} else {
					alert(flag.MESSAGE);
					$('#commentsfieldsetId,fieldset').removeAttr("disabled");
					$('#commentsSubmitButtonId').removeAttr("disabled");
				}
			}, 'json');
		}
	}
</script>