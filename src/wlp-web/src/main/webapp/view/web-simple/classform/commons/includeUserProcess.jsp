<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div style="top: -50px; padding: 0px; margin-top: 20px;">
	<div class="panel panel-default"
		style="filter: progid:DXImageTransform.Microsoft.Shadow(color=#909090, direction=120, strength=4); -moz-box-shadow: 2px 2px 10px #909090; -webkit-box-shadow: 2px 2px 10px #909090; box-shadow: 2px 2px 10px #909090;">
		<div class="panel-body" style="width: 100%;">

			<c:if
				test="${USEROBJ==null||learnView.userclass==null||learnView.userclass.pstate!='3'}">
				<a href="hourweb/PubContinue.do?classid=${classview.classt.id}"
					class="btn btn-danger  btn-lg"
					style="width: 100%; margin-top: 20px; margin-bottom: 20px;"> <i
					class="glyphicon glyphicon-play-circle" style="top: 3px;"></i>&nbsp;
					<c:if test="${USEROBJ==null }">查看课程</c:if> <c:if
						test="${USEROBJ!=null }">
						<c:if test="${learnView.learned }">
							继续学习
						</c:if>
						<c:if test="${!learnView.learned }">
							开始学习
						</c:if>
					</c:if>
				</a>
			</c:if>
			<c:if
				test="${USEROBJ!=null&&learnView.userclass!=null&&learnView.userclass.pstate=='3'}">
				<a href="hourweb/PubContinue.do?classid=${classview.classt.id}"
					class="btn  btn-success  btn-lg"
					style="width: 100%; margin-top: 20px; margin-bottom: 20px;"> <i
					class="glyphicon glyphicon-play-circle" style="top: 3px;"></i>&nbsp;
					复习课程（已完成）
				</a>
			</c:if>
			<img class="img-rounded"
				style="width: 100%; margin-top: 0px; margin-bottom: 20px;"
				src="${classview.imgurl}">
			<div class="wlp-userspace-h2" style="margin-left: -20px;">
				<c:if test="${USEROBJ==null }">未登陆用户</c:if>
				<c:if test="${USEROBJ!=null }">${USEROBJ.name}：
					<c:if test="${learnView.learned }">
							学习进度${learnView.userclass.process }%
					</c:if>
					<c:if test="${!learnView.learned }">
							未学习该课程
					</c:if>
				</c:if>
			</div>
			<hr style="margin-bottom: 10px;" class="wlp-userspace-hr">
			<c:if test="${USEROBJ!=null&&learnView.learned }">
				<div class="progress" title="${learnView.userclass.process }%">
					<c:if test="${learnView.userclass.process<100}">
						<div class="progress-bar progress-bar-danger progress-bar-striped active"
							role="progressbar" aria-valuenow="20" aria-valuemin="0"
							aria-valuemax="100"
							style="width: ${learnView.userclass.process }%">
							${learnView.userclass.process }%</div>
					</c:if>
					<c:if test="${learnView.userclass.process>=100}">
						<div class="progress-bar progress-bar-success progress-bar-striped"
							role="progressbar" aria-valuenow="20" aria-valuemin="0"
							aria-valuemax="100"
							style="width: ${learnView.userclass.process }%">
							${learnView.userclass.process }%</div>
					</c:if>
				</div>
			</c:if>
			<div style="text-align: center; color: #999999;">本课程总学时${classview.classt.altime }分钟</div>
		</div>
	</div>
</div>