<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${majorview.major.title }-<PF:ParameterValue
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
	background-color: #172a34;
}

.wlp-praise-box.disable {
	background-color: #f3f3f3;
	cursor: default;
	border: 1px dashed #dddddd;
}

.wlp-praise-box.disable .media-heading {
	color: #999999;
}

.wlp-praise-box.disable img {
	opacity: 0.3;
}

.wlp-praise-box.disable:HOVER {
	border: 1px dashed #dddddd;
	border-radius: 6px 6px 6px 6px;
}
</style>
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
					<div
						style="margin-top: 50px; height: 20px; font-weight: 700; font-size: 18px;"
						class="wlp-view-types">
						专业学习： <span class="label label-default"
							style="padding-top: 4px; font-weight: 200; font-size: 14px;">步骤：${majorview.major.chapternum}</span>
						<span class="label label-default"
							style="padding-top: 4px; font-weight: 200; font-size: 14px;">课程：${majorview.major.classnum}</span>
					</div>
					<div style="padding-bottom: 20px; text-align: center;">
						<img class="img-thumbnail img-circle"
							style="width: 96px; height: 96px; margin: auto;"
							src="${majorview.imgurl }">
					</div>
					<div class="wlp-userspace-h1"
						style="text-shadow: 5px 2px 8px #000000; margin-top: 10px; margin-bottom: 10px;">${majorview.major.title }</div>
					<div
						style="padding: 15px; text-shadow: 0px 0px 18px #000000; text-align: center;">
						<span style="font-size: 14px; font-weight: 700;">专业简介: </span>${majorview.major.note}</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="container">
			<div style="margin-top: 20px;">
				<div style="text-align: center;">
					<c:if test="${isFavorite}">
						<a onclick="unFavorite('${majorview.major.id}')"
							class="btn btn-danger  btn-lg">&nbsp;&nbsp; <i
							class="glyphicon glyphicon-star"></i>&nbsp;已收藏专业&nbsp;&nbsp;
						</a>
					</c:if>
					<c:if test="${!isFavorite}">
						<a onclick="favorite('${majorview.major.id}')"
							class="btn btn-danger  btn-lg">&nbsp;&nbsp; <i
							class=" glyphicon glyphicon-star-empty "></i>&nbsp;未收藏专业&nbsp;&nbsp;
						</a>
					</c:if>
					<div style="margin-top: 8px; font-size: 12px;">-&nbsp;-&nbsp;&nbsp;已有${favoriteObj.num}人收藏&nbsp;&nbsp;-&nbsp;-</div>
				</div>
				<hr style="margin-bottom: 10px;" class="wlp-userspace-hr">
				<c:forEach items="${majorview.chapters }" var="node">
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
								<div style="padding-top: 6px; font-size: 14px; color: #999999;">${node.note }</div>
							</div>
						</div>
						<div class="row "
							style="background-color: #eceef1; padding: 20px; padding-top: 10px; border-radius: 12px;">
							<c:forEach items="${majorview.classes}" var="mclasst">
								<c:if test="${mclasst.chapterid ==node.id}">
									<div class="col-md-6" style="padding: 0px; padding-top: 20px"
										onclick="openClasstView('${mclasst.classid}',${mclasst.readable})">
										<div
											class="media wlp-praise-box ${mclasst.readable?'':'disable'}">
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
												</div>
											</div>
											<div
												style="height: 32px; font-size: 12px; color: #999999; overflow: hidden;">
												<hr class="wlp-userspace-hr">
												<div title="${mclasst.classt.shortintro}"
													style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">${mclasst.classt.shortintro}</div>
											</div>
											<c:if test="${USEROBJ!=null}">
												<div
													style="height: 32px; font-size: 12px; color: #999999; overflow: hidden; padding-top: 8px;">
													<c:if test="${USEROBJ!=null&&mclasst.readable }">
														<c:if test="${empty mclasst.userclass}">
															<div style="text-align: center;">
																<p class="text-primary">
																	<i class=" glyphicon glyphicon-play"></i>&nbsp;立即开始学习
																</p>
															</div>
														</c:if>
														<c:if test="${!empty mclasst.userclass}">
															<div class="progress"
																title="${mclasst.userclass.process }%">
																<c:if test="${mclasst.userclass.process>=100}">
																	<div
																		class="progress-bar progress-bar-success progress-bar-striped"
																		role="progressbar" aria-valuenow="20"
																		aria-valuemin="0" aria-valuemax="100"
																		style="width: ${mclasst.userclass.process }%">完成全部课时
																		${mclasst.userclass.process }%</div>
																</c:if>
																<c:if
																	test="${mclasst.userclass.process>0&&mclasst.userclass.process<100}">
																	<div
																		class="progress-bar progress-bar-danger progress-bar-striped active"
																		role="progressbar" aria-valuenow="20"
																		aria-valuemin="0" aria-valuemax="100"
																		style="width: ${mclasst.userclass.process }%">已完成
																		${mclasst.userclass.process }%</div>
																</c:if>
																<c:if test="${mclasst.userclass.process==0}">
																	<div style="text-align: center;">学习进度
																		${mclasst.userclass.process }%</div>
																</c:if>
															</div>
														</c:if>
													</c:if>
													<c:if test="${!mclasst.readable }">
														<div style="text-align: center;">
															<p class="text-danger">
																<i class="glyphicon glyphicon-stop"></i>&nbsp;无学习权限
															</p>
														</div>
													</c:if>
												</div>
											</c:if>
										</div>
									</div>
								</c:if>
							</c:forEach>
						</div>

					</div>
					<hr style="margin-bottom: 10px;" class="wlp-userspace-hr">
				</c:forEach>
			</div>
		</div>
	</div>
	<div class="container-fluid" style="padding: 0px;">
		<!-- 页脚 -->
		<jsp:include page="../commons/foot.jsp"></jsp:include>
	</div>
</body>
<script type="text/javascript">
	function openClasstView(classtid,able) {
		if(able){
			window.location.href = "<PF:basePath/>classweb/Pubview.do?classid="
					+ classtid;
		}
	}
	//收藏
	function favorite(majorid) {
		$.post('socialweb/favorite.do', {
			'appid' : majorid,
			'apptype' : '1'
		}, function(flag) {
			if (flag.STATE == 0) {
				location.reload();
			} else {
				alert(flag.MESSAGE);
			}
		}, 'json');
	}
	//取消收藏
	function unFavorite(majorid) {
		$.post('socialweb/unfavorite.do', {
			'appid' : majorid,
			'apptype' : '1'
		}, function(flag) {
			if (flag.STATE == 0) {
				location.reload();
			} else {
				alert(flag.MESSAGE);
			}
		}, 'json');
	}
</script>
</html>