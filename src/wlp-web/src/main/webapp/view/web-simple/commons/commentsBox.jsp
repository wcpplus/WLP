<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<style>
.wlp-comments-list li {
	list-style: none;
	margin-left: -40px;
	min-height: 50px;
	border: 1px dashed #999999;
	margin-top: 20px;
	margin-bottom: 20px;
	padding: 20px;
	border-radius: 4px;
	background-color: #f8fafc;
}

.wlp-comments-list li img {
	height: 48px;
	width: 48px;
	border-radius: 24px;
}

.wlp-comments-list .comments-text {
	font-size: 14px;
	color: #666666;
	background-color: #ffffff;
	padding: 10px;
	border-radius: 7px;
	border: 1px solid #eeeeee;
}

.wlp-comments-list h4 {
	font-size: 16px;
	color: #333333;
}

.wlp-comments-list .comment-time {
	font-size: 14px;
	color: #999999;
	margin-right: 20px;
}

.wlp-comments-list .comments-parent {
	border-left: 5px solid #999999;
	padding: 10px;
	margin-top: 4px;
	background-color: #eeeeee;
}

.wlp-comments-list .comments-parentuser {
	color: #333333;
	font-weight: 700;
}
</style>
<div style="height: 35px;">
	<span style="color: #999999; font-size: 12px;"> 当前课程评论<span
		id="commentsAllNumId"></span>条
	</span>
	<button onclick="publicComments();" style="float: right;" type="button"
		class="btn btn-default">发布评论</button>
</div>
<hr style="margin-bottom: 10px;" class="wlp-userspace-hr">
<ul class="wlp-comments-list" id="commentsListDataBoxId">
	<li>评论加载中... ...</li>
</ul>
<nav aria-label="...">
	<input type="hidden" id="loadCurrentPageId">
	<ul class="pager" style="margin: 0px;">
		<li id="loadLastButtionId"><a href="javascript:loadLastPage();">上一页</a></li>
		<li id="loadNextButtionId"><a href="javascript:loadNextPage();">下一页</a></li>
	</ul>
</nav>
<jsp:include page="commentsBoxForm.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		loadComments(1);
	});
	function loadLastPage() {
		loadComments(parseInt($('#loadCurrentPageId').val()) - 1);
	}
	function loadNextPage() {
		loadComments(parseInt($('#loadCurrentPageId').val()) + 1);
	}
	//发布评论
	function publicComments() {
		if (!'${USEROBJ.id}') {
			alert('请先登陆用户');
			return;
		}
		$('#commentsParentidId').val('');
		$('#commentsOpLabel').text("新评论");
		$('#commentsNoteId').val('');
		$('#commentsForm').modal({
			keyboard : false
		});
	}
	//回复评论
	function replyComments(parentId) {
		if (!'${USEROBJ.id}') {
			alert('请先登陆用户');
			return;
		}
		$('#commentsParentidId').val(parentId);
		$('#commentsOpLabel').text("回复评论");
		$('#commentsNoteId').val('');
		$('#commentsForm').modal({
			keyboard : false
		});
	}
	//加载评论
	function loadComments(page) {
		$.post('socialweb/PubloadComments.do', {
			'appids' : '${param.appid}',
			'page' : page
		}, function(flag) {
			if (flag.STATE == 0) {
				$('#commentsListDataBoxId').html('');
				$('#loadCurrentPageId').val(flag.result.currentPage);
				if (flag.result.currentPage > 1) {
					$('#loadLastButtionId').show();
				} else {
					$('#loadLastButtionId').hide();
				}
				if (flag.result.currentPage >= flag.result.totalPage) {
					$('#loadNextButtionId').hide();
				} else {
					$('#loadNextButtionId').show();
				}
				$('#commentsAllNumId').html(flag.result.totalSize);
				if (flag.result.totalSize <= 0) {
					$('#commentsListDataBoxId').append(
							"<li ><center>~暂无评论~</center></li>");
				}
				$(flag.result.resultList).each(function(i, obj) {
					$('#commentsListDataBoxId').append(creatNode(obj));
				});
			} else {
				alert(flag.MESSAGE);
			}
		}, 'json');
	}
	function creatNode(obj) {
		var html11 = '<li><div class="media">';
		var html12 = '	<div class="media-left">';
		var html13 = '		<a  > ';
		var html14 = '			<img class="media-object" src="'+obj.IMGURL+'" alt="...">';
		var html15 = '		</a>';
		var html16 = '	</div>';
		var html17 = '	<div class="media-body">';
		var html18 = '		<h4 class="media-heading">' + obj.user.name + '</h4>';
		var html19 = '		<div class="comments-text">' + obj.NOTE
				+ creatParentNode(obj.parent,obj.parentNote) + '</div>';
		var html20 = '		<div style="text-align: right; padding-top: 10px;">';
		var html21 = '			<span class="comment-time">' + obj.CTIME + '</span>';
		var html22 = '			<button onclick="replyComments(\''
				+ obj.ID
				+ '\');" type="button" class="btn btn-default btn-sm">回复</button>';
		var html23 = '		</div>';
		var html24 = '	</div>';
		var html25 = '</div></li>';
		var nodeHtml = html11 + html12 + html13 + html14 + html15 + html16
				+ html17 + html18 + html19 + html20 + html21 + html22 + html23
				+ html24 + html25;
		return nodeHtml;
	}
	function creatParentNode(parent,note) {
		if (parent) {
			var html = "<div class='comments-parent'><span class='comments-parentuser'>"
					+ parent.user.name + ":</span> " + note + "</div>";
			return html;
		} else {
			return '';
		}

	}
</script>
