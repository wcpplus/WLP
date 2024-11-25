<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>待发布专业- <PF:ParameterValue key="config.sys.title" /></title>
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
<style>
.wlp-v-list ul li {
	list-style-type: none;
	padding: 10px;
	color: #666666;
	padding-top: 20px;
	padding-bottom: 20px;
}
</style>
</head>
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
				<div class="col-md-3">
					<jsp:include page="commons/include-major-leftmenu.jsp"><jsp:param
							name="current" value="tempmajor" /></jsp:include>
				</div>
				<div class="col-md-9">
					<div style="margin-top: 20px;">
						<div class="wlp-userspace-h2">
							待发布专业 <span
								style="font-size: 12px; color: #aaaaaa; font-weight: 200; margin-left: 20px;">#专业只能由管理员创建和授权
							</span>
						</div>
						<hr class="wlp-userspace-hr">
						<div class="wlp-v-list">
							<ul>
								<c:forEach items="${result.resultList }" var="node">
									<li><div class="media">
											<div class="media-left hidden-xs hidden-sm">
												<a> <img style="height: 86px; width: 86px;"
													class="media-object"
													src="${node.IMGURL==null?'text/img/none.png':node.IMGURL}"
													alt="专业展示图">
												</a>
											</div>
											<div class="media-body"
												style="padding: 8px; padding-top: 0px;">
												<h4 class="media-heading" title="${node.NOTE}">${node.TITLE}</h4>
												<div style="padding: 4px; padding-left: 0px;">
													<div
														style="text-overflow: ellipsis; overflow: hidden; display: -webkit-box; -webkit-line-clamp: 1; -webkit-box-orient: vertical;">
														${node.NOTE}</div>
												</div>
												<div>
													<span class="label label-default hidden-xs hidden-sm"
														style="padding-top: 4px;">排序:${node.SORT}</span> <span
														class="label label-default hidden-xs hidden-sm"
														style="padding-top: 4px;">最近编辑发布:${node.ETIME}</span>
													<div style="float: right; margin-top: 0px;">
														<button onclick="mngMajor('${node.ID}')" type="button"
															class="btn btn-primary btn-sm">配置专业</button>
														<button onclick="publicMajor('${node.ID}')" type="button"
															class="btn btn-success btn-sm">发布</button>
														<a target="_blank"
															href="classweb/Pubview.do?classid=${node.ID}"
															class="btn btn-default btn-sm">预览</a>
													</div>
												</div>
											</div>
										</div></li>
								</c:forEach>
							</ul>
						</div>
						<jsp:include page="/view/web-simple/commons/dataResultPages.jsp">
							<jsp:param value="${result.currentPage}" name="current" />
							<jsp:param value="${result.totalPage}" name="total" />
							<jsp:param
								value="userspace/tempclass.do?page=${result.currentPage+1}"
								name="nexturl" />
							<jsp:param
								value="userspace/tempclass.do?page=${result.currentPage-1}"
								name="lasturl" />
						</jsp:include>
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
	//配置专业
	function mngMajor(majorid) {
		window.location = "<PF:basePath/>majorweb/mng.do?majorid=" + majorid;
	}

	//发布专业
	function publicMajor(majorid) {
		if (confirm("专业发布后将向用户开放，是否继续？")) {
			window.location = "<PF:basePath/>majorweb/public.do?majorid="
					+ majorid;
		}
	}
</script>
</html>