<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<style>
.wlp-evaluation-list li {
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

.wlp-evaluation-list li img {
	height: 48px;
	width: 48px;
	border-radius: 24px;
}

.wlp-evaluation-list .evaluation-text {
	font-size: 14px;
	color: #666666;
	background-color: #ffffff;
	padding: 10px;
	border-radius: 7px;
	border: 1px solid #eeeeee;
}

.wlp-evaluation-list h4 {
	font-size: 16px;
	color: #333333;
}

.wlp-evaluation-list .comment-time {
	font-size: 14px;
	color: #999999;
	margin-right: 20px;
}

.wlp-evaluation-list .evaluation-parent {
	border-left: 5px solid #999999;
	padding: 10px;
	margin-top: 4px;
	background-color: #eeeeee;
}

.wlp-evaluation-list .evaluation-parentuser {
	color: #333333;
	font-weight: 700;
}
</style>

<style>
.wlp-evaluation {
	color: #999999;
}

.wlp-evaluation .active {
	color: #ff9900;
}
</style>
<div style="height: 35px;">
	<span style="color: #999999; font-size: 12px;"> 当前课程评价<span
		id="evaluationAllNumId"></span>条
	</span>
	<button onclick="publicEvaluation();" style="float: right;"
		type="button" class="btn btn-default">发布评价</button>
</div>
<hr style="margin-bottom: 10px;" class="wlp-userspace-hr">
<ul class="wlp-evaluation-list">
	<li style="border-radius: 22px;"><div class="media">
			<div class="media-left">
				<div style="width: 60px; font-weight: 700;">
					综合<br />评分
				</div>
			</div>
			<div class="media-left">
				<div style="width: 120px; font-weight: 700;">
					<div style="font-size: 28px; font-weight: 700;">
						<span id="evaluationObjScoreId">0</span>分
					</div>
				</div>
			</div>
			<div class="media-body" style="padding-top: 4px;">
				<span style="font-size: 28px;" class="wlp-evaluation" data-num="3"
					id="evaluationObjStarId" data-max="5"> </span> <span
					style="color: #999999; float: right; margin-top: 8px;"><span
					id="evaluationAllUserNumId"></span>人参与评价</span>
			</div>
		</div></li>
</ul>
<ul class="wlp-evaluation-list" id="evaluationListDataBoxId">

</ul>
<nav aria-label="...">
	<input type="hidden" id="loadCurrentPageId">
	<ul class="pager" style="margin: 0px;">
		<li id="loadLastButtionId"><a href="javascript:loadLastPage();">上一页</a></li>
		<li id="loadNextButtionId"><a href="javascript:loadNextPage();">下一页</a></li>
	</ul>
</nav>
<jsp:include page="evaluationBoxForm.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		loadEvaluation(1);
		initEvalutionStarDom(".wlp-evaluation")
	});
	function initEvalutionStarDom(doms) {
		var star_n = '<i class="glyphicon glyphicon-star"></i>';
		var star_a = '<i class="glyphicon glyphicon-star active"></i>';
		$(doms).each(function(i, obj) {
			var cnum = $(obj).attr('data-num');
			var maxnum = $(obj).attr('data-max');
			$(obj).html('');
			for (i = 1; i <= maxnum; i++) {
				if (i <= cnum) {
					$(obj).append(star_a);
				} else {
					$(obj).append(star_n);
				}
			}
		});
	}
	function loadLastPage() {
		loadEvaluation(parseInt($('#loadCurrentPageId').val()) - 1);
	}
	function loadNextPage() {
		loadEvaluation(parseInt($('#loadCurrentPageId').val()) + 1);
	}
	//发布评论
	function publicEvaluation() {
		if (!'${USEROBJ.id}') {
			alert('请先登陆用户');
			return;
		}
		$('#evaluationParentidId').val('');
		$('#evaluationOpLabel').text("用户评价");
		$('#evaluationNoteId').val('');
		$('#evaluationForm').modal({
			keyboard : false
		});
		chooseStart('starFormId', 0);
	}
	//加载评论
	function loadEvaluation(page) {
		$.post('socialweb/PubloadEvaluation.do', {
			'appid' : '${param.appid}',
			'page' : page
		}, function(flag) {
			if (flag.STATE == 0) {
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
				$('#evaluationAllNumId').html(flag.result.totalSize);
				$('#evaluationAllUserNumId').html(flag.result.totalSize);
				$('#evaluationListDataBoxId').html('');
				$('#evaluationObjScoreId').html((flag.OBJ.score).toFixed(2));
				$('#evaluationObjStarId').attr('data-num',
						parseInt(flag.OBJ.score));
				if (flag.result.totalSize <= 0) {
					$('#evaluationListDataBoxId').append(
							"<li ><center>~暂无评价</center></li>");
				}
				$(flag.result.resultList).each(function(i, obj) {
					$('#evaluationListDataBoxId').append(creatNode(obj));
				});
				initEvalutionStarDom(".wlp-evaluation");
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
		var html18 = '		<h4 class="media-heading">'
				+ obj.user.name
				+ ':&nbsp;&nbsp;&nbsp;&nbsp;<span class="wlp-evaluation" data-num="'
				+ (obj.SCORE)
				+ '" data-max="5"> </span><span class="comment-time" style="float:right">'
				+ obj.CTIME + '</span></h4>' + '			';
		var html19 = obj.NOTE ? '		<div class="evaluation-text">' + obj.NOTE
				+ '</div>' : '';
		var html24 = '	</div>';
		var html25 = '</div></li>';
		var nodeHtml = html11 + html12 + html13 + html14 + html15 + html16
				+ html17 + html18 + html19 + html24 + html25;
		return nodeHtml;
	}
</script>
