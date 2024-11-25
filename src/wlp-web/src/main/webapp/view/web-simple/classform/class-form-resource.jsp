<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>课程外部资源- <PF:ParameterValue key="config.sys.title" /></title>
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
<script charset="utf-8"
	src="<PF:basePath/>text/javascript/wlp.bootstrap.gride.js"></script>
<style>
</style>
</head>
<style>
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
					<jsp:include page="commons/classLeftInfo.jsp">
						<jsp:param value="resource" name="type" />
					</jsp:include>
				</div>
				<div class="col-xs-9">
					<div style="margin-top: 20px;">
						<div class="wlp-userspace-h2">课程资源</div>
						<hr style="margin-bottom: 20px;" class="wlp-userspace-hr">
						<div class="table-responsive">
							<table class="table table-striped" id="learUserGrideId"
								action="classweb/loadClassResources.do?classtid=${classview.classt.id}">
								<thead>
									<tr>
										<th class="NO" style="width: 1%;">#</th>
										<th class="DATA" data-field="APPTYPE" style="width: 40%;"
											data-func="resourceBindFunc">绑定类型</th>
										<th class="DATA" data-field="URL" data-func="resourceUrlFunc"
											style="word-break: break-all;width: 40%;">资源地址</th>
										<!-- <th class="DATA" data-field="TITLE" style="width: 220px;">课程/课时名称</th> -->
										<!-- <th class="DATA" data-field="RESOURCETYPE" style="width: 80px;">资源类型</th> -->
										<!-- <th class="DATA" data-field="SYSNAME" style="width: 80px;">系统名称</th> -->
										<th class="DATA" data-field="PROCESS" data-func="userFunc"
											style="width: 15%;"><button
												onclick="openResourceFormWin()" type="button"
												class="btn btn-info btn-sm">添加</button></th>
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
	</div>
	<div class="container-fluid" style="padding: 0px;">
		<!-- 页脚 -->
		<jsp:include page="../commons/foot.jsp"></jsp:include>
	</div>
	<jsp:include page="commons/includeResourceForm.jsp"></jsp:include>
</body>
<script type="text/javascript">
	$(function() {
		initGride('learUserGrideId', {});
	});
	//构造用户按钮
	function userFunc(lineData, val) {
		return '<button type="button" onClick="openResourceFormWin(\''
				+ lineData.ID
				+ '\');" class="btn btn-primary btn-xs">編輯</button>&nbsp;<button onClick="delResource(\''
				+ lineData.ID
				+ '\');" type="button" class="btn btn-danger btn-xs">刪除</button>';
	}
	//构造资源绑定类型
	function resourceBindFunc(lineData, val) {
		if (lineData.TITLE) {
			return lineData.APPTYPE + "-" + lineData.TITLE;
		} else {
			return lineData.APPTYPE;
		}
	}
	//构造资源超链接
	function resourceUrlFunc(lineData, val) {
		return lineData.SYSNAME + "-" + lineData.RESOURCETYPE + "&nbsp;:&nbsp;"
				+ "<a style='font-size: 12px;' href='"+val+"' target='_blank'>"
				+ val + "</a>";
	}

	//編輯或添加資源
	function openResourceFormWin(id) {
		$('#classResourceForm').modal({
			keyboard : false
		//,backdrop : false
		});
		//初始化章節表單
		initResourceForm(id);
	}

	//刪除資源
	function delResource(resourceid) {
		if (confirm("是否刪除本资源？")) {
			$.post('classweb/delresource.do', {
				'resourceid' : resourceid,
				'classid' : '${classview.classt.id}'
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