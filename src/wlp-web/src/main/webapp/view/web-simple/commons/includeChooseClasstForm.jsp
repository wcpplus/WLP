<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<style>
.searchClassResultNode {
	cursor: pointer;
	border: 1px dashed #cccccc;
	padding: 4px;
	background-color: #eeeeee;
}
.searchClassResultNode:HOVER {
	border: 1px dashed #d9534f;
	background-color: #dddddd;
}
.searchClassResultNode img {
	width: 48px;
	height: 48px;
}

.searchClassResultNode h4 {
	font-size: 14px;
	padding: 4px;
	line-height: 18px;
}
</style>
<!-- Modal -->
<div class="modal fade" id="chooseClassForm" tabindex="-1" role="dialog"
	aria-labelledby="chooseClassOpLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="chooseClassOpLabel">选择课程</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-5" style="max-height: 400px; overflow: auto;">
						<input id="searchTypeid" type="hidden">
						<jsp:include page="/view/web-simple/commons/classTypeBox.jsp">
							<jsp:param value="classtype" name="appkey" />
							<jsp:param value="write" name="poptype" />
							<jsp:param value="false" name="isShowNum" />
						</jsp:include>
					</div>
					<div class="col-md-7">
						<div class="input-group input-group-sm">
							<input type="text" class="form-control" id="searchclassTitleId"
								placeholder="请输入课程名称..."> <span class="input-group-btn">
								<button class="btn btn-default" onclick="searchUserReadClasst()"
									type="button">查询</button>
								<button class="btn btn-default" onclick="clearSearchLimit()"
									type="button">清空</button>
							</span>
						</div>
						<hr class="wlp-userspace-hr">
						<div id="searchResultBoxId"></div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<nav aria-label="...">
					<input type="hidden" id="searchCurrentPageId">
					<ul class="pager" style="margin: 0px;">
						<li id="searchLastButtionId"><a
							href="javascript:searchLastPage();" title="">上一页</a></li>
						<li id="searchNextButtionId"><a
							href="javascript:searchNextPage();" title="">下一页</a></li>
					</ul>
				</nav>
			</div>
		</div>
	</div>
</div>
<script>
	$(function() {
		searchUserReadClasst(1);
	});
	function searchLastPage() {
		searchUserReadClasst(parseInt($('#searchCurrentPageId').val()) - 1);
	}
	function searchNextPage() {
		searchUserReadClasst(parseInt($('#searchCurrentPageId').val()) + 1);
	}
	//选中一个课时
	function chooseSearcheResultClass(classtid, name) {
		try {
			chooseClassHandle(classtid, name)
		} catch (e) {
			alert("请实现回调方法:chooseClassHandle(classtid, name)");
		}
	}
	//执行查询
	function searchUserReadClasst(page) {
		var typeid = $('#searchTypeid').val();
		var title = $('#searchclassTitleId').val();
		$('#searchResultBoxId').html("loadding... ...");
		$.post('classweb/PubClassSearch.do', {
			'word' : title,
			'typeid' : typeid,
			'page' : page
		}, function(flag) {
			loadClassNode(flag.newClasses.resultList, flag.newClasses);
		}, 'json');
	}
	//選中分類的回調函數
	function chooseClassTypeHandle(appkey, event, data) {
		$('#searchTypeid').val(data.id);
		searchUserReadClasst(1);
	}
	//加载结果集合
	function loadClassNode(nodes, result) {
		$('#searchResultBoxId').html("");
		$('#searchCurrentPageId').val(result.currentPage);
		if (result.currentPage > 1) {
			$('#searchLastButtionId').show();
		} else {
			$('#searchLastButtionId').hide();
		}
		if (result.currentPage >= result.totalPage) {
			$('#searchNextButtionId').hide();
		} else {
			$('#searchNextButtionId').show();
		}
		$(nodes)
				.each(
						function(i, obj) {
							var html = '   <div onclick="chooseSearcheResultClass(\''
									+ obj.ID
									+ '\',\''
									+ obj.NAME
									+ '\')" class="media searchClassResultNode" style="margin-top:12px;">';
							html = html + '    <div class="media-left">';
							html = html
									+ '        <img class="media-object" src="'+ obj.IMGURL +'" alt="...">';
							html = html + '    </div>';
							html = html + '    <div class="media-body">';
							html = html + '        <h4  class="media-heading">'
									+ obj.NAME + '</h4>';
							html = html + '    </div>';
							html = html + '</div>';
							$('#searchResultBoxId').append(
									"<div>" + html + "</div>");
						});
	}
	//清空查询条件
	function clearSearchLimit() {
		$('#searchTypeid').val('');
		$('#searchclassTitleId').val('');
		searchUserReadClasst(1);
		$(treeBoxKey).treeview('search', 'NONE');
	}
</script>