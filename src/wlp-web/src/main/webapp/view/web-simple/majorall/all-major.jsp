<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>全部专业-<PF:ParameterValue key="config.sys.title" /></title>
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
<script
	src="<PF:basePath/>text/lib/bootstrap-treeview/bootstrap-treeview.min.js"></script>
</head>
<style>
.wlp-majorShow-box {
	cursor: pointer;
	padding: 10px;
}

.wlp-majorShow-box img {
	border-radius: 4px;
}

.wlp-majorShow-box h4 {
	font-weight: 700;
}

.wlp-majorShow-box .media {
	background-color: #ffffff;
	border-radius: 5px;
	padding: 20px;
}

.wlp-majorShow-box:hover img {
	-moz-opacity: 0.7;
	filter: alpha(opacity = 70);
	opacity: 0.7;
	cursor: pointer;
}

.wlp-majorShow-box:hover h4 {
	color: #f20d0d;
}
</style>
<body>
	<div class="container-fluid">
		<div class="row">
			<!-- 导航 -->
			<jsp:include page="../commons/head.jsp"></jsp:include>
		</div>
	</div>
	<div class="container-fluid "
		style="padding-top: 20px; background-color: #f3f5f7; padding-left: 0px; padding-right: 0px;">
		<div class="container-fluid "
			style="padding-top: 60px; background-color: #ffffff; box-shadow: 0 4px 8px 0 rgba(28, 31, 33, .1);">
			<div class="container">
				<div class="row">
					<div class="col-xs-12" style="padding-bottom: 30px;">
						<div class="wlp-userspace-h2" style="margin-left: -20px;">
							<i style="position: relative; top: 3px;"
								class="glyphicon glyphicon-education"></i>&nbsp; 全部专业:<span
								style="color: #999999; font-size: 14px; font-weight: 200; margin-left: 20px;">共${majors.totalSize}门专业可以学习</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="row" style="margin-top: 20px;">
				<div class="col-md-12">
					<div class="row">
						<c:if test="${empty majors.resultList}">
							<div class="col-md-12">
								<div
									style="text-align: center; margin: 50px; color: #aaaaaa; border: dashed 1px #ccc; padding: 50px; padding-top: 100px; padding-bottom: 100px; background-color: #ffffff;">当前无任何可学习的专业...</div>
							</div>
						</c:if>
						<c:forEach items="${majors.resultList}" var="node">
							<div class="col-md-6 wlp-majorShow-box"
								onclick="openMajorView('${node.ID}')"
								style="padding: 20px; padding-top: 20px;">
								<div class="media">
									<div class="media-left hidden-xs hidden-sm">
										<a> <img style="height: 86px; width: 86px;"
											class="media-object"
											src="${node.IMGURL==null?'text/img/none.png':node.IMGURL}"
											alt="专业展示图">
										</a>
									</div>
									<div class="media-body" style="padding: 8px; padding-top: 0px;">
										<h4 class="media-heading" title="${node.NOTE}">${node.TITLE}</h4>
										<div style="padding: 4px; padding-left: 0px;">
											<div
												style="text-overflow: ellipsis; overflow: hidden; display: -webkit-box; -webkit-line-clamp: 1; -webkit-box-orient: vertical;">
												${node.NOTE}</div>
										</div>
										<div style="margin-top: 8px;">
											<span class="label label-default hidden-xs hidden-sm"
												style="padding-top: 4px;">步骤:${node.CHAPTERNUM}</span> <span
												class="label label-default hidden-xs hidden-sm"
												style="padding-top: 4px;">课程:${node.CLASSNUM}</span>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
					<div>
						<jsp:include page="/view/web-simple/commons/dataResultPages.jsp">
							<jsp:param value="${majors.currentPage}" name="current" />
							<jsp:param value="${majors.totalPage}" name="total" />
							<jsp:param
								value="majorweb/Puball.do?page=${majors.currentPage+1}"
								name="nexturl" />
							<jsp:param
								value="majorweb/Puball.do?page=${majors.currentPage-1}"
								name="lasturl" />
						</jsp:include>
						<script type="text/javascript">
							function openMajorView(majorid) {
								window.location = "<PF:basePath/>majorweb/Pubview.do?majorid="
										+ majorid;
							}
						</script>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid" style="padding: 0px;">
		<!-- 页脚 -->
		<jsp:include page="../commons/foot.jsp"></jsp:include>
	</div>
</body>
<script type="text/javascript">
	
</script>
</html>