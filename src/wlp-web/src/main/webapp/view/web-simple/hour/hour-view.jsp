<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${hourview.classview.classt.name }-<PF:ParameterValue
		key="config.sys.title" /></title>
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
<link rel="stylesheet"
	href="<PF:basePath/>text/lib/kindeditor/editInner.css" />
<script charset="utf-8"
	src="<PF:basePath/>text/lib/super-validate/validate.js"></script>
</head>
<style>
.wlp-userspace-h1 {
	font-size: 36px;
	text-align: center;
}

.picLUp {
	width: 100%;
	background-size: 100% 100%;
	color: white;
	background-color: #2a2f32;
}

.wpl-class-tab .nav  li a {
	color: #ffffff;
}

.wpl-class-tab .nav  li a:HOVER {
	color: #666666;
}

.wpl-class-tab .nav .active a {
	color: #666666;
}

.wlp-a-nounderline a:HOVER {
	text-decoration: none;
	font-weight: 200;
}
</style>
<body>
	<div class="container-fluid">
		<div class="row">
			<!-- 导航 -->
			<jsp:include page="../commons/head.jsp"></jsp:include>
		</div>
	</div>
	<div class="container picLUp">
		<div class="row" id="hourViewId"
			style="color: #ffffff; margin-top: 70px;">
			<div class="col-sm-9" style="color: #ffffff; font-size: 14px;">
				<div style="padding: 20px; font-size: 16px; color: #dddddd;">
					<a style="font-weight: 700; color: #ffffff;"
						href="classweb/Pubview.do?classid=${hourview.classview.classt.id }">
						${hourview.classview.classt.name}</a> &nbsp;/&nbsp;<span>${hourview.chapter.title}</span>
					&nbsp;/&nbsp;${hourview.hour.title}
				</div>
				<c:if
					test="${hourview.filebase.exname=='mp4'||hourview.filebase.exname=='MP4'}">
					<jsp:include page="commons/dplayer.jsp"></jsp:include>
				</c:if>
				<c:if
					test="${hourview.filebase.exname=='pdf'||hourview.filebase.exname=='PDF'}">
					<c:if test="${hourview.pinum>0}">
						<jsp:include page="commons/imgs.jsp"></jsp:include>
					</c:if>
					<c:if test="${hourview.pinum==0}">
						<jsp:include page="commons/pdf.jsp"></jsp:include>
					</c:if>
				</c:if>
			</div>
			<div class="col-sm-3 wlp-a-nounderline  hidden-xs"
				style="color: #ffffff;">
				<div style="height: 100%; overflow: auto;" id="hourViewSiderightId">
					<div style="padding: 20px; padding-left: 0px; font-size: 28px;">
						<a style="color: #ffffff; text-decoration: none;"
							href="classweb/Pubview.do?classid=${hourview.classview.classt.id }">
							${hourview.classview.classt.name}</a>
					</div>
					<div
						style="padding-bottom: 20px; padding-left: 0px; font-size: 12px; color: #cccccc;">${hourview.classview.classt.shortintro }</div>
					<div style="text-align: center;">
						<img class="img-rounded"
							style="width: 70%; margin-top: 0px; margin-bottom: 20px;"
							src="${hourview.classview.imgurl}">
					</div>
					<hr
						style="margin-bottom: 10px; margin-top: 10px; color: #666666; border-color: #666666;"
						class="wlp-userspace-hr">
					<jsp:include page="commons/chapter.jsp"></jsp:include>
				</div>
				<c:if test="${USEROBJ!=null}">
					<div class="btn-group btn-group-justified  btn-group-lg"
						role="group" style="margin-top: 8px;"
						aria-label="Justified button group with nested dropdown">
						<!-- 分割綫 <a href="#" class="btn btn-default" role="button">Left</a> -->
						<a href="javascript:openOperatorWin()" class="btn btn-danger"
							role="button">完成本课时学习</a>
					</div>
				</c:if>
			</div>
		</div>
	</div>
	<div class="container picLUp" style="min-height: 60px;">
		<c:if test="${USEROBJ!=null}">
			<div
				class="btn-group btn-group-justified  btn-group-lg hidden-lg hidden-md hidden-sm"
				role="group" style="margin-top: 80px;"
				aria-label="Justified button group with nested dropdown">
				<!-- 分割綫 <a href="#" class="btn btn-default" role="button">Left</a> -->
				<a href="javascript:openOperatorWin()" class="btn btn-danger"
					role="button">完成本课时学习</a>
			</div>
		</c:if>
		<div class="container wpl-class-tab">
			<ul class="nav nav-tabs" style="margin-top: 20px;">
				<li role="presentation" class="active"><a
					style="font-weight: 700;">&nbsp;&nbsp;<i
						class="glyphicon glyphicon-comment"></i>&nbsp;&nbsp;课时评论&nbsp;&nbsp;&nbsp;
				</a></li>
				<c:forEach items="${resources}" var="node">
					<li role="presentation"><a target="_blank"
						style="cursor: pointer;"
						onclick="goOutsys('${node.baseurl}${node.resourceurl}')">&nbsp;&nbsp;<i
							class="glyphicon ${node.resourceicon}"></i>
							&nbsp;&nbsp;${node.apptypename}${node.resourcename }&nbsp;&nbsp;&nbsp;
					</a></li>
				</c:forEach>
				<li role="presentation"><a target="_blank"
					onclick="goOutsys('<PF:basePath/>classweb/Pubview.do?type=evaluation&classid=${hourview.classview.classt.id}')"
					style="font-weight: 700; cursor: pointer;">&nbsp;&nbsp;<i
						class="glyphicon glyphicon-heart"></i>&nbsp;&nbsp;课程评价&nbsp;&nbsp;&nbsp;
				</a></li>
			</ul>
		</div>
	</div>
	<div class="container" style="padding-bottom: 100px;">
		<div class="row" style="margin: 20px;">
			<div class="col-md-9">
				<!-- 1:专业,2:课程,3:课时 -->
				<jsp:include page="/view/web-simple/commons/commentsBox.jsp">
					<jsp:param name="appid" value="${hourview.hour.id}" />
					<jsp:param name="apptype" value="3" />
				</jsp:include>
			</div>
			<div class="col-md-3 hidden-xs"></div>
		</div>
	</div>
	<jsp:include page="commons/hourOperator.jsp"></jsp:include>
	<div class="container-fluid" style="padding: 0px;">
		<!-- 页脚 -->
		<jsp:include page="../commons/foot.jsp"></jsp:include>
	</div>
</body>
<script type="text/javascript">
	function getClientHeight() {
		return document.documentElement.clientHeight - 184;
	}
	$(function() {
		setWindowHeight();
	});
	function setWindowHeight() {
		$('#hourViewId').height(getClientHeight());
		$('#hourViewSiderightId').height(getClientHeight());
	}
	function openOperatorWin() {
		$('#hourOperatorWin').modal('show')
	}
	function goOutsys(url) {
		window.open(url);
	}
</script>
</html>