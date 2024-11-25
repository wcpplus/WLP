<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<ul class="nav nav-tabs wlp-search-tabs" role="tablist">
	<li role="presentation" ${param.current=='classt'?'class="active"':'' }><a onclick="goModel('classt')"  aria-controls="home" role="tab" data-toggle="tab" >&nbsp;&nbsp;检索课程&nbsp;&nbsp;</a></li>
	<li role="presentation" ${param.current=='hour'?'class="active"':'' }><a onclick="goModel('hour')" aria-controls="profile" role="tab" data-toggle="tab">&nbsp;&nbsp;检索课时&nbsp;&nbsp;</a></li>
	<li role="presentation" ${param.current=='major'?'class="active"':'' }><a onclick="goModel('major')"  aria-controls="messages" role="tab" data-toggle="tab">&nbsp;&nbsp;检索专业&nbsp;&nbsp;</a></li>
</ul>