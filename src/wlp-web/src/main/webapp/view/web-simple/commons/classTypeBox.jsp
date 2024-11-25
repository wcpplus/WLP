<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!-- 展示分類樹 -->
<style>
<!--
.list-group {
	border-radius: 4px;
	-webkit-box-shadow: 0 0px 0px rgba(0, 0, 0, 0);
	box-shadow: 0 0px 0px rgba(0, 0, 0, 0);
}

.wlp-type-falg {
	color: #f01414;
	font-size: 14px;
	font-weight: 200;
	margin-left: 0.2em;
}
-->
</style>
<script
	src="<PF:basePath/>text/lib/bootstrap-treeview/bootstrap-treeview.min.js"></script>
<div id="tree"></div>
<script type="text/javascript">
	var treeObj;
	var treeBoxKey = "#tree";
	$(function() {
		treeObj = $(treeBoxKey).treeview({
			showBorder : false,
			data : '[{"text": "数据加载中...","id":"di1111"}]'
		});
		$.post("category/Publoadclasstype.do", {
			poptype : '${param.poptype}',isShowNum:'${param.isShowNum}'
		}, function(flag) {
			treeObj = $(treeBoxKey).treeview({
				data : flag.data,
				showBorder : false,
				highlightSelected : false,
				onNodeSelected : function(event, data) {
					callTreeselectBackFun(event, data);
				}
			});
		}, 'json');
	});
	function callTreeselectBackFun(event, data) {
		$('#chooseTypeModal').modal('hide');
		try {
			$(treeBoxKey).treeview('search',[data.id]);
			chooseClassTypeHandle('${param.appkey}', event, data);
		} catch (e) {
			alert('请实现方法	chooseClassTypeHandle(typekey,event, data);');
		}
	}
</script>