<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div style="width: 100%; margin-top: 20px;">
	<c:if test="${!empty majorview}">
		<div style="padding-bottom: 20px; text-align: center;">
			<img class="img-rounded"
				style="width: 96px; height: 96px; margin: auto;"
				src="${majorview.imgurl }">
		</div>
		<div class="wlp-userspace-h2">${majorview.major.title }</div>
	</c:if>
	<c:if test="${empty majorview}">
		<div class="wlp-userspace-h2">新专业</div>
	</c:if>
	<hr class="wlp-userspace-hr">
	<c:if test="${!empty majorview}">
		<ul class="nav nav-pills nav-stacked">
			<li role="presentation" ${param.type=='base'?'class="active"':'' }><a
				href="majorweb/mng.do?majorid=${majorview.major.id}"><i
					class="glyphicon glyphicon-pencil"></i>&nbsp;&nbsp;編輯专业信息</a></li>
			<li role="presentation" ${param.type=='chapter'?'class="active"':'' }><a
				href="majorweb/mng.do?majorid=${majorview.major.id}&type=chapter"><i
					class="glyphicon glyphicon-pencil"></i>&nbsp;&nbsp;维护学习步骤</a></li>
			<li role="presentation"><a target="_blank"
				href="majorweb/Pubview.do?majorid=${majorview.major.id}"><i
					class="glyphicon glyphicon-log-out"></i>&nbsp;&nbsp;预览专业</a></li>
			<li role="presentation"><a href="userspace/tempmajor.do"><i
					class="glyphicon glyphicon-menu-left"></i>&nbsp;&nbsp;返回列表</a></li>
		</ul>
	</c:if>
</div>