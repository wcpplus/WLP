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

.wlp-hour-box:HOVER {
	background-color: #eeeeee;
	border: 1px dashed #aaaaaa;
}

.wlp-hour-box {
	border: 1px dashed #ffffff;
	padding: 4px;
	padding-top: 8px;
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
						<jsp:param value="chapter" name="type" />
					</jsp:include>
				</div>
				<div class="col-xs-9">
					<div style="margin-top: 20px;">
						<div class="wlp-userspace-h2">专业学习步骤</div>
						<hr style="margin-bottom: 10px;" class="wlp-userspace-hr">
						<c:forEach items="${chapters }" var="node">
							<div style="margin: 40px; margin-bottom: 12px;">
								<div class="media">
									<div class="media-left"
										style="padding: 20px; padding-top: 0px; padding-left: 0px;">
										<div style="width: 30px; text-align: center;">
											<span style="font-size: 26px; color: #666666;"><i
												class=" glyphicon glyphicon-bookmark"></i></span> <span
												style="font-size: 14px; font-weight: 700; color: #aaaaaa;">NO.${node.sort }</span>
										</div>
									</div>
									<div class="media-body">
										<h4 class="media-heading"
											style="font-weight: 700; font-size: 18px;">${node.title }</h4>
										<div
											style="padding-top: 6px; font-size: 14px; color: #999999;">${node.note }</div>
									</div>
								</div>
								<div class="row "
									style="background-color: #eceef1; padding: 20px; padding-top: 10px;border-radius: 12px;">
									<c:forEach items="${classes}" var="mclasst">
										<c:if test="${mclasst.chapterid ==node.id}">
											<div class="col-md-6" style="padding: 0px; padding-top: 20px"
												onclick="clickPraiseTop('${top.id}')">
												<div class="media wlp-praise-box">
													<div class="media-left">
														<img class="media-object maxImg" src="${mclasst.imgUrl}"
															alt="...">
													</div>
													<div class="media-body">
														<div class="media-heading"
															style="font-size: 14px; height: 42px; overflow: hidden;"
															title="${mclasst.classt.name}">${mclasst.classt.name}</div>
														<div style="color: #999999;">
															<span style="padding-top: 4px;">课时数量:${mclasst.classt.hournum}</span>&nbsp;/&nbsp;<span
																style="padding-top: 4px;">学习时长:${mclasst.classt.altime}分</span>
															<button type="button"
																style="float: right; position: relative; top: -4px;"
																onclick="delMajorClasst('${mclasst.id}')"
																class="btn btn-danger btn-xs">
																移除&nbsp;<i class="glyphicon glyphicon-remove"></i>
															</button>
														</div>
													</div>
													<div
														style="height: 32px; font-size: 12px; color: #999999; overflow: hidden;">
														<hr class="wlp-userspace-hr">
														<div title="${mclasst.classt.shortintro}"
															style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">${mclasst.classt.shortintro}</div>
													</div>
												</div>
											</div>
										</c:if>
									</c:forEach>
								</div>
								<div style="text-align: right; padding-top: 8px;">
									<div class="btn-group btn-group-sm" role="group"
										aria-label="...">
										<button type="button" class="btn btn-info"
											onclick="openChooseClasstFormWin('${node.id}')">添加课程</button>
										<button type="button"
											onclick="openChapterFormWin('${node.id}')"
											class="btn btn-primary">編輯学习步骤</button>
										<button type="button" onclick="delChapter('${node.id}')"
											class="btn btn-danger">刪除学习步骤</button>
									</div>
								</div>
							</div>
							<hr style="margin-bottom: 10px;" class="wlp-userspace-hr">
						</c:forEach>
						<a onclick="openChapterFormWin()" class="btn btn-info">添加新学习步骤</a>
						<a href="userspace/tempmajor.do" class="btn btn-default">返回</a>
						<p class="bg-danger"
							style="padding: 20px; margin-top: 20px; color: #f01414; display: none;"
							id="errormessageShowboxId"></p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid" style="padding: 0px;">
		<!-- 页脚 -->
		<jsp:include page="../commons/foot.jsp"></jsp:include>
	</div>
	<jsp:include page="commons/includeChapterForm.jsp"></jsp:include>
	<jsp:include
		page="/view/web-simple/commons/includeChooseClasstForm.jsp"></jsp:include>
</body>
<script>
	$(function() {

	});
	//編輯或添加学习步骤
	function openChapterFormWin(id) {
		$('#classChapterForm').modal({
			keyboard : false
		//,backdrop : false
		});
		//初始化章節表單
		initChapterForm(id);
	}
	var currentChapterId;
	//添加课时
	function openChooseClasstFormWin(chapterid) {
		currentChapterId = chapterid;
		$('#chooseClassForm').modal({
			keyboard : false
		});
	}
	//选中一个课程
	function chooseClassHandle(classtid, name) {
		if (confirm("是否添加课程\"" + name + "\",到步骤中？")) {
			$.post('majorweb/addClasst.do', {
				'chapterid' : currentChapterId,
				'classtid' : classtid
			}, function(flag) {
				if (flag.STATE == 0) {
					if (confirm("当前课程添加完成，是否刷新页面回显课程？（如要继续添加，请执行“取消”操作）")) {
						location.reload();
					}
				} else {
					alert(flag.MESSAGE);
				}
			}, 'json');
		}
	}
	//刪除学习步骤
	function delChapter(chapterid) {
		if (confirm("是否刪除本学习步骤？")) {
			$.post('majorweb/delchapter.do', {
				'chapterid' : chapterid
			}, function(flag) {
				if (flag.STATE == 0) {
					location.reload();
				} else {
					alert(flag.MESSAGE);
				}
			}, 'json');
		}
	}

	//刪除课时
	function delMajorClasst(mclasst) {
		if (confirm("是否刪除本学习步骤？")) {
			$.post('majorweb/delclasst.do', {
				'majorclassId' : mclasst
			}, function(flag) {
				if (flag.STATE == 0) {
					location.reload();
				} else {
					alert(flag.MESSAGE);
				}
			}, 'json');
		}
	}
</script>
</html>