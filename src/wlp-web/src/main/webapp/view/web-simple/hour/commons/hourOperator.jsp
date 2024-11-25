<%@page import="com.farm.parameter.FarmParameterService"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!-- Modal -->
<div class="modal fade" id="hourOperatorWin" tabindex="-1" role="dialog"
	style="margin-top: 150px;" aria-labelledby="hourOperatorTitle">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-body">
				<p
					style="text-align: center; font-size: 16px; color: #666666; margin-top: 20px;">
					<c:if test="${learnView.userclass.pstate!='3'}">
						<i style="position: relative; top: 4px; font-size: 22px;"
							class="glyphicon glyphicon-ok-circle"></i>&nbsp;即将完成当前课时的学习...
					</c:if>
					<c:if test="${learnView.userclass.pstate=='3'}">
						<div style="color: #00b43c; font-size: 18px; text-align: center;">
							<i style="position: relative; top: 4px; font-size: 22px;"
								class="glyphicon glyphicon-ok-circle"></i>&nbsp;已完成本课程全部课时的学习!
						</div>
					</c:if>
				</p>
			</div>
			<c:if test="${learnView.userclass.pstate!='3'}">
				<div class="modal-footer">
					<div class="btn-group btn-group-justified" role="group"
						aria-label="Justified button group">
						<a class="btn btn-default" role="button" data-dismiss="modal">取消</a>
						<a href="javascript:reLearn();" class="btn btn-info" role="button">重新学习</a>
						<a
							href="hourweb/PubContinue.do?classid=${hourview.classview.classt.id }&type=finish"
							class="btn btn-success" role="button">完成&nbsp;-&nbsp;学习下一节</a>
					</div>
				</div>
			</c:if>
		</div>
	</div>
</div>
<script type="text/javascript">
	function reLearn() {
		location.reload();
	}
</script>