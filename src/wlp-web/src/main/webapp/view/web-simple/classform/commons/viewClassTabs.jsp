<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<style>
.nav-tabs a {
	font-weight: 700;
}
</style>
<style>
.wlp-userspace-h1 {
	font-size: 36px;
	text-align: center;
}

.picLUp {
	width: 100%;
	background-size: 100% 100%;
	color: white;
	background-color: #424f5c;
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

.wlp-hour-box:HOVER {
	background-color: #fef3f3;
	color: #f20d0d;
}

.wlp-hour-box {
	padding: 4px;
	padding-top: 8px;
	cursor: pointer;
}
</style>
<ul class="nav nav-tabs" style="margin-top: 20px;">
	<c:if test="${!empty classview.introText }">
		<li role="presentation" ${param.current=='home'?'class="active"':'' }><a
			href="classweb/Pubview.do?classid=${classview.classt.id }">&nbsp;&nbsp;<i
				class="glyphicon glyphicon-book"></i>
				&nbsp;&nbsp;课程介绍&nbsp;&nbsp;&nbsp;
		</a></li>
	</c:if>
	<li role="presentation"
		${param.current=='chapter'?'class="active"':'' }><a
		id="backTabLi"
		href="classweb/Pubview.do?type=chapter&classid=${classview.classt.id }">&nbsp;&nbsp;<i
			class="glyphicon glyphicon-list-alt"></i>
			&nbsp;&nbsp;章节目录&nbsp;&nbsp;&nbsp;
	</a></li>
	<li role="presentation"
		${param.current=='comments'?'class="active"':'' }><a
		id="backTabLi"
		href="classweb/Pubview.do?type=comments&classid=${classview.classt.id }">&nbsp;&nbsp;<i
			class="glyphicon glyphicon-comment"></i>
			&nbsp;&nbsp;课程评论&nbsp;&nbsp;&nbsp;
	</a></li>
	<li role="presentation"
		${param.current=='evaluation'?'class="active"':'' }><a
		id="backTabLi"
		href="classweb/Pubview.do?type=evaluation&classid=${classview.classt.id }">&nbsp;&nbsp;<i
			class="glyphicon glyphicon-heart"></i>
			&nbsp;&nbsp;课程评价&nbsp;&nbsp;&nbsp;
	</a></li>
	<c:forEach items="${resources}" var="node">
		<li role="presentation"><a target="_blank"
			style="cursor: pointer;"
			onclick="goOutsys('${node.baseurl}${node.resourceurl}')">&nbsp;&nbsp;<i
				class="glyphicon ${node.resourceicon}"></i>
				&nbsp;&nbsp;${node.apptypename}${node.resourcename }&nbsp;&nbsp;&nbsp;
		</a></li>
	</c:forEach>
	<c:if test="${writeAble}">
		<li role="presentation"
			${param.current=='userinfo'?'class="active"':'home' }><a
			id="backTabLi"
			href="classweb/Pubview.do?type=userinfo&classid=${classview.classt.id }">&nbsp;&nbsp;<i
				class="glyphicon glyphicon-user"></i>
				&nbsp;&nbsp;学员信息&nbsp;&nbsp;&nbsp;
		</a></li>
	</c:if>
</ul>
<script type="text/javascript">
	function goOutsys(url) {
		window.open(url);
	}
</script>