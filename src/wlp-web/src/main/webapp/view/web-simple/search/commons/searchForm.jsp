<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<form action="search/Pubsearch.do" method="post" id="searchFormId">
	<div class="input-group input-group-lg" style="margin-bottom: 20px;">
		<span class="input-group-btn">
			<button class="btn btn-danger" type="submit">搜索</button>
		</span> <input type="text" name="word" class="form-control"
			placeholder="请输入关键字... ..." value="${word}">
	</div>
	<input type="hidden" name="tagids" id="tagInputId" value="${tagids}">
	<input type="hidden" name="model" id="modelId" value="${model}">
	<input type="hidden" name="page" id="pageId" value="${page}">
</form>
<script type="text/javascript">
	function goPage(page) {
		$('#pageId').val(page);
		$('#searchFormId').submit();
	}
	function goModel(model) {
		$('#modelId').val(model);
		$('#searchFormId').submit();
	}
</script>
