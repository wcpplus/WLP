<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>已发布专业- <PF:ParameterValue key="config.sys.title" /></title>
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
							name="current" value="publicmajor" /></jsp:include>
				</div>
				<div class="col-md-9">
					<div style="margin-top: 20px;">
						<div class="wlp-userspace-h2">
							<div class="row">
								<div class="col-xs-6">已发布专业</div>
								<div class="col-xs-6"></div>
							</div>
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
														style="padding-top: 4px;">步骤:${node.CHAPTERNUM}</span> <span
														class="label label-default hidden-xs hidden-sm"
														style="padding-top: 4px;">课程:${node.CLASSNUM}</span>
													<div style="float: right;">
														<button onclick="tempClass('${node.ID}')" type="button"
															class="btn btn-warning btn-sm">取消发布</button>
														<a target="_blank"
															href="majorweb/Pubview.do?majorid=${node.ID}"
															class="btn btn-default btn-sm">查看</a>
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
								value="userspace/publicclass.do?page=${result.currentPage+1}&classtype=${classtype}"
								name="nexturl" />
							<jsp:param
								value="userspace/publicclass.do?page=${result.currentPage-1}&classtype=${classtype}"
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
	<jsp:include page="/view/web-simple/commons/classTypeChoose.jsp">
		<jsp:param value="classtype" name="appkey" />
		<jsp:param value="write" name="poptype" />
		<jsp:param value="true" name="isShowNum" />
	</jsp:include>
</body>
<script type="text/javascript">
	//发布课程
	function tempClass(majorid) {
		if (confirm("课程取消发布后将转移到待发布界面，是否继续？")) {
			$.post('majorweb/temp.do', {
				'majorid' : majorid
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