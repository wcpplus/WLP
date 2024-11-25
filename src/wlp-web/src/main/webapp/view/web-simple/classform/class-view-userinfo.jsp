<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${classview.classt.name }-<PF:ParameterValue
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
<script charset="utf-8"
	src="<PF:basePath/>text/javascript/wlp.bootstrap.gride.js"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<!-- 导航 -->
			<jsp:include page="../commons/head.jsp"></jsp:include>
		</div>
	</div>
	<div class="container-fluid  picLUp" style="padding-top: 20px;">
		<div class="container">
			<div class="row">
				<div class="col-xs-12">
					<jsp:include page="commons/viewClassHeadInfo.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<div class="container wpl-class-tab">
			<jsp:include page="commons/viewClassTabs.jsp"><jsp:param
					name="current" value="userinfo" /></jsp:include>
		</div>
	</div>
	<div class="container-fluid">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div style="margin-top: 50px; margin-bottom: 100px;"
						class="table-responsive">
						<!-- 学习用户，学习进度，开始时间，最近学习时间，结束时间，学习状态 -->
						<table class="table table-striped" id="learUserGrideId"
							action="classweb/learUsers.do?classtid=${classview.classt.id}">
							<thead>
								<tr>
									<th class="NO">#</th>
									<th class="DATA" data-field="USERNAME">用户名称</th>
									<th class="DATA" data-field="ORGNAME">组织机构</th>
									<th class="DATA" data-field="STIME">开始时间</th>
									<th class="DATA" data-field="ETIME">结束时间</th>
									<th class="DATA" data-field="PROCESS" data-func="learProcess">学习进度</th>
									<th class="DATA" data-field="LEARNSTATE">学习状态</th>
									<th class="DATA" data-field="LTIME">上一次学习时间</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
							<tfoot>
								<tr>
									<td style="cursor: pointer;"><nav aria-label="...">
											<ul class="pagination  pagination-sm">
												<li><a class="lastPage" title="0">上一页</a></li>
												<li class="active"><a class="currentPage">loading...</a></li>
												<li><a class="nextPage" title="0">下一页 </a></li>
											</ul>
										</nav></td>
								</tr>
							</tfoot>
						</table>
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
	$(function() {
		initGride('learUserGrideId', {});
	});
	function learProcess(lineData, val) {
		var html = '<div class="progress" style="margin-bottom:0px;">'
				+ '<div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width:'
				+ val + '%">' + ' <span class="sr-only">45% Complete</span>'
				+ '</div>' + '</div>';
		return html;
	}
</script>
</html>