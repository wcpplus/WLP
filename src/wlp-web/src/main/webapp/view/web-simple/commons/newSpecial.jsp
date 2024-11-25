<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<c:forEach items="${newMajor.resultList}" var="node">
	<div class="col-md-3" style="padding: 0px; padding-top: 20px">
		<div class="media wlp-praise-box" title="${node.NOTE}"
			onclick="openMajorView('${node.ID}') ">
			<div class="media-left">
				<a href="#"> <img class="media-object minImg"
					src="${node.IMGURL}" alt="...">
				</a>
			</div>
			<div class="media-body">
				<div class="media-heading">
					<div title="${node.TITLE}"
						style="text-overflow: ellipsis; overflow: hidden; display: -webkit-box; -webkit-line-clamp: 1; -webkit-box-orient: vertical;">
						${node.TITLE}</div>
				</div>
				<div style="color: #999999;">
					<span>步骤:${node.CHAPTERNUM}</span><span style="margin-left: 8px;">课程:${node.CLASSNUM}</span>
				</div>
			</div>
		</div>
	</div>
</c:forEach>
<script type="text/javascript">
	function openMajorView(majorid) {
		window.location = "<PF:basePath/>majorweb/Pubview.do?majorid="
				+ majorid;
	}
</script>

