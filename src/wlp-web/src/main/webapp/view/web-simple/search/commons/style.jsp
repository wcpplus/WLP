<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<style>
.wlp-userspace-h1 {
	font-size: 36px;
	text-align: center;
}

.picLUp {
	background: url(text/img/demo/webfullstack-bg.jpg) no-repeat;
	width: 100%;
	background-size: 100% 100%;
	color: white;
	background-color: #000000;
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

.list-group {
	border-radius: 4px;
	-webkit-box-shadow: 0 0px 0px rgba(0, 0, 0, 0);
	box-shadow: 0 0px 0px rgba(0, 0, 0, 0);
}

.wlp-typeline-ul {
	margin-left: -20px;
}

.wlp-typeline-ul li {
	float: left;
	padding: 4px;
	padding-left: 8px;
	padding-right: 8px;
	list-style-type: none;
	font-size: 16px;
	margin-right: 20px;
	cursor: pointer;
	list-style-type: none;
}

.wlp-typeline-ul li:HOVER {
	color: #f20d0d;
}

.wlp-typeline-ul li.active {
	color: #f20d0d;
	font-weight: 700;
	background-color: #fef1f1;
	border-radius: 4px 4px 4px 4px;
	font-weight: 700;
}

.wlp-praiseback-box {
	background-color: #f2f2f2;
	padding: 13px;
	border-radius: 6px 6px 6px 6px;
	border: 1px solid #f2f2f2;
	margin: 8px;
	cursor: pointer;
	margin: 8px;
}

.wlp-praiseback-box:HOVER {
	background-color: #fafafa;
	padding: 13px;
	border-radius: 15px 15px 15px 15px;
	border: 1px dashed #dddddd;
	cursor: pointer;
}

.wlp-mark-red {
	color: #f20d0d;
}

.wlp-search-tabs a {
	cursor: pointer;
}

.wlp-sp-content {
	padding-top: 20px;
	background-color: #f3f5f7;
	padding-left: 0px;
	padding-right: 0px;
}

.wlp-sp-formbox {
	padding-top: 60px;
	background-color: #ffffff;
	box-shadow: 0 4px 8px 0 rgba(28, 31, 33, .1);
}

.nav-tabs li.active a {
	background-color: #e1e3e5;
	font-weight: 700;
}

.nav-tabs li a {
	font-weight: 700;
}
</style>