<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>专业編輯- <PF:ParameterValue key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="index,follow">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<jsp:include page="../atext/include-web.jsp"></jsp:include>
<script charset="utf-8"
	src="<PF:basePath/>text/lib/super-validate/validate.js"></script>
<style>
</style>
</head>
<style>
<!--
form .help-block {
	font-size: 12px;
}
-->
</style>
<body>
	<div class="container-fluid">
		<div class="row">
			<!-- 导航 -->
			<jsp:include page="../commons/head.jsp"></jsp:include>
		</div>
	</div>
	<div class="container-fluid"
		style="padding-top: 20px; padding-bottom: 20px;">
		<div class="container" style="margin-top: 50px;">
			<div class="row">
				<div class="col-xs-3">
					<jsp:include page="commons/majorLeftInfo.jsp">
						<jsp:param value="base" name="type" />
					</jsp:include>
				</div>
				<div class="col-xs-9">
					<div style="margin-top: 20px;">
						<form method="post" id="submitFormId"
							action="majorweb/savebase.do">
							<input value="${majorview.major.id}" name="id" type="hidden">
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group" id="classnameGroupId">
										<label for="majorTitleId" class="control-label">专业名称&nbsp;<span
											class="text-danger">*</span></label> <input name="title" type="text"
											class="form-control" id="majorTitleId" placeholder="专业名称"
											value="${majorview.major.title}"> <span
											id="classnameErrorId" class="help-block"></span>
									</div>

								</div>
							</div>
							<div class="row">
								<div class="col-xs-6">
									<div class="form-group" id="noteGroupId">
										<label for="noteId" class="control-label">专业简述&nbsp;<span
											class="text-danger">*</span></label>
										<textarea class="form-control" id="noteId"
											name="note" placeholder="简述" rows="6">${majorview.major.note}</textarea>
										<span id="noteErrorId" class="help-block"></span>
									</div>
								</div>
								<div class="col-xs-6"
									style="text-align: center; padding-top: 20px;">
									<img style="width: 96px; height: 96px;" alt=""
										id="majorviewImgId" class="img-thumbnail"
										src="${majorview==null||majorview.major==null||majorview.major.imgid==null?'text/img/demo/colors-a4.jpg':majorview.imgurl}">
									<div
										style="font-size: 12px; font-weight: 700; color: #666666; margin-top: 4px;">上传专业预览图</div>
									<input type="hidden" name="imgid" id="imgidId"
										value="${majorview.major.imgid}">
									<jsp:include
										page="/view/web-simple/commons/fileUploadCommons.jsp">
										<jsp:param value="classimg" name="appkey" />
										<jsp:param value="IMG" name="type" />
										<jsp:param value="上传图片" name="title" />
									</jsp:include>
								</div>
							</div>
 							<a onclick="submitForm();" id="submitButtonId"
								class="btn btn-success">提交保存</a> <a
								href="userspace/tempmajor.do" class="btn btn-default">返回</a>
							<p class="bg-danger"
								style="padding: 20px; margin-top: 20px; color: #f01414; display: none;"
								id="errormessageShowboxId"></p>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid" style="padding: 0px;margin-top: 150px;">
		<!-- 页脚 -->
		<jsp:include page="../commons/foot.jsp"></jsp:include>
	</div>
</body>
<script>
	$(function() {
		$('select', '#submitFormId').each(function(i, obj) {
			var val = $(obj).attr('val');
			$(obj).val(val);
		});
		validateInput('majorTitleId', function(id, val, obj) {
			// 标题
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			if (valid_maxLength(val, 128)) {
				return {
					valid : false,
					msg : '长度不能大于' + 128
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'classnameErrorId', 'classnameGroupId');
		validateInput('noteId', function(id, val, obj) {
			// 专业简介
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
	function submitForm() {
		if (!validate('submitFormId')) {
			$('#errormessageShowboxId').text('信息录入有误，请检查！');
			$('#errormessageShowboxId').show();
			return;
		}
		if (!$('#imgidId').val()) {
			$('#errormessageShowboxId').text('请上传专业预览图片！');
			$('#errormessageShowboxId').show();
			return;
		}
		$('#errormessageShowboxId').text('');
		$('#errormessageShowboxId').hide();
		if (confirm("是否提交本专业？")) {
			$('#submitButtonId').attr('disabled', 'disabled');
			$('#submitFormId').submit();
		}
	}
	//图片提交成功
	function fileUploadHandle(appkey, url, id, fileName) {
		if ('classimg' == appkey) {
			$('#majorviewImgId').attr('src', url);
			$('#imgidId').val(id);
		}
	}
</script>
</html>