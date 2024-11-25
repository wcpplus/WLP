<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<style>
.wlp-evaluation-form {
	color: #999999;
}

.wlp-evaluation-form .active {
	color: #ff9900;
}

.wlp-evaluation-form i {
	cursor: pointer;
}

.wlp-evaluation-form i:HOVER {
	color: #fed18c;
}
</style>
<div class="modal fade" id="evaluationForm" tabindex="-1" role="dialog"
	aria-labelledby="evaluationOpLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="evaluationOpLabel">评价</h4>
			</div>
			<div class="modal-body">
				<form method="post" id="submitevaluationFormId"
					action="classweb/savebase.do">
					<input name="appid" id="evaluationAppidId" type="hidden"
						value="${param.appid}"> <input name="apptype"
						id="evaluationApptypeId" type="hidden" value="${param.apptype}">
					<input name="score" id="evaluationScoreId" type="hidden">
					<fieldset id="evaluationfieldsetId">
						<div class="row">
							<div class="col-xs-12" style="text-align: center;">
								<div>请点击星星进行评价</div>
								<span style="font-size: 32px;" class="wlp-evaluation-form"
									id="starFormId"> <i
									onclick="chooseStart('starFormId',1)"
									class="glyphicon glyphicon-star active"></i><i
									onclick="chooseStart('starFormId',2)"
									class="glyphicon glyphicon-star"></i><i
									onclick="chooseStart('starFormId',3)"
									class="glyphicon glyphicon-star"></i><i
									onclick="chooseStart('starFormId',4)"
									class="glyphicon glyphicon-star"></i><i
									onclick="chooseStart('starFormId',5)"
									class="glyphicon glyphicon-star"></i></span>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group" id="noteGroupId">
									<textarea class="form-control" id="evaluationNoteId"
										name="note" placeholder="可录入评价描述......" rows="2"></textarea>
									<span id="noteErrorId" class="help-block"></span>
								</div>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" id="evaluationSubmitButtonId"
					onclick="submitevaluationForm();" class="btn btn-success">发表评价</button>
			</div>
		</div>
	</div>
</div>
<script>
	//用户打分
	function chooseStart(startFormId, num) {
		$('#' + startFormId + " i").each(function(i, obj) {
			if (i < num) {
				$(obj).addClass("active");
			} else {
				$(obj).removeClass("active");
			}
		});
		if (num > 0) {
			$('#evaluationScoreId').val(num);
		} else {
			$('#evaluationScoreId').val("");
		}
	}

	$(function() {
		validateInput('noteId', function(id, val, obj) {
			// 课程简介
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
	function submitevaluationForm() {
		if (!$('#evaluationScoreId').val()) {
			alert("请点击星星进行评价！");
			return;
		}
		if (!validate('submitevaluationFormId')) {
			return;
		}
		if (confirm("是否立即发布评价？")) {
			$('#evaluationSubmitButtonId').attr('disabled', 'disabled');
			$('#evaluationfieldsetId,fieldset').attr('disabled', 'disabled');
			$.post('socialweb/submitEvaluation.do', {
				'appid' : $('#evaluationAppidId').val(),
				'apptype' : $('#evaluationApptypeId').val(),
				'score' : $('#evaluationScoreId').val(),
				'comments' : $('#evaluationNoteId').val()
			}, function(flag) {
				if (flag.STATE == 0) {
					$('#evaluationForm').modal('hide')
					$('#evaluationfieldsetId,fieldset').removeAttr("disabled");
					$('#evaluationSubmitButtonId').removeAttr("disabled");
					loadEvaluation(1);
				} else {
					alert(flag.MESSAGE);
					$('#evaluationfieldsetId,fieldset').removeAttr("disabled");
					$('#evaluationSubmitButtonId').removeAttr("disabled");
				}
			}, 'json');
		}
	}
</script>