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
<title>课时检索-<PF:ParameterValue key="config.sys.title" /></title>
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
<jsp:include page="commons/style.jsp"></jsp:include>
<body>
	<div class="container-fluid">
		<div class="row">
			<!-- 导航 -->
			<jsp:include page="../commons/head.jsp"></jsp:include>
		</div>
	</div>
	<div class="container-fluid wlp-sp-content">
		<div class="container-fluid wlp-sp-formbox">
			<div class="container">
				<div class="row">
					<div class="col-md-3"></div>
					<div class="col-md-6">
						<jsp:include page="commons/searchForm.jsp"></jsp:include>
					</div>
				</div>
				<div>
					<jsp:include page="commons/tabs.jsp">
						<jsp:param value="hour" name="current" />
					</jsp:include>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="row" style="margin-top: 20px;">
				<div class="col-md-12">
					<div class="row">
						<c:if test="${empty result.resultList}">
							<div
								style="text-align: center; margin: 50px; color: #aaaaaa; border: dashed 1px #ccc; padding: 50px; background-color: #ffffff;">当前未检索到任何结果,请修改关键字后重新检索...</div>
						</c:if>
						<c:forEach items="${result.resultList}" var="node">
							<div class="media"
								style="background-color: #ffffff; padding: 10px; margin: 10px;">
								<div class="media-left hidden-xs hidden-sm">
									<a> <img style="height: 100px; width: 141px;"
										class="media-object" src="${node.IMGURL}" alt="课程展示图">
									</a>
								</div>
								<div class="media-body" style="padding: 8px; padding-top: 0px;">
									<h4 class="media-heading">
										<a style="text-decoration: none;"
											href="hourweb/PubContinue.do?classid=${node.ID}& hourid=${node.HOURID}">${node.TITLE}</a>
									</h4>
									<h5 class="media-heading" style="margin-top: 12px;">
										课程： <a style="text-decoration: none;"
											href="classweb/Pubview.do?classid=${node.ID}">${node.NAME}</a>
									</h5>
									<div style="font-size: 12px; padding: 2px;">
										<c:forEach items="${node.TYPES}" var="type" varStatus="stat">
											<c:if test="${stat.index>0 }">
												<span style="color: #eeeeee; font-weight: 700;">&nbsp;/&nbsp;</span>
											</c:if>
											<span style="color: #999999;">${type.name }</span>
										</c:forEach>
									</div>
									<div>
										<span class="label label-default hidden-xs hidden-sm"
											style="padding-top: 4px;">时长:${node.ALTIME}分钟</span>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
					<div>
						<jsp:include page="/view/web-simple/commons/dataResultPages.jsp">
							<jsp:param value="${result.currentPage}" name="current" />
							<jsp:param value="${result.totalPage}" name="total" />
							<jsp:param value="javascript:goPage(${result.currentPage+1});"
								name="nexturl" />
							<jsp:param value="javascript:goPage(${result.currentPage-1});"
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
	function clickTag(tagid) {
		$('#tagInputId').val(tagid);
		$('#searchFormId').submit();
	}
</script>
</html>