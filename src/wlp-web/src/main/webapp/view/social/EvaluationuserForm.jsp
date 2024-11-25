<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--用户评价表单-->
<div class="easyui-layout" data-options="fit:true">
  <div class="TableTitle" data-options="region:'north',border:false">
    <span class="label label-primary"> 
    <c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
    </span>
  </div>
  <div data-options="region:'center'">
    <form id="dom_formEvaluationuser">
      <input type="hidden" id="entity_id" name="id" value="${entity.id}">
      <table class="editTable">
      <tr>
        <td class="title">
        OBJID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_objid" name="objid" value="${entity.objid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        SCORE:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[5]']"
          id="entity_score" name="score" value="${entity.score}">
        </td>
      </tr>
      <tr>
        <td class="title">
        NOTE:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[1,024]']"
          id="entity_note" name="note" value="${entity.note}">
        </td>
      </tr>
      <tr>
        <td class="title">
        CTIME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[8]']"
          id="entity_ctime" name="ctime" value="${entity.ctime}">
        </td>
      </tr>
      <tr>
        <td class="title">
        USERID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_userid" name="userid" value="${entity.userid}">
        </td>
      </tr>
    </table>
    </form>
  </div>
  <div data-options="region:'south',border:false">
    <div class="div_button" style="text-align: center; padding: 4px;">
      <c:if test="${pageset.operateType==1}">
      <a id="dom_add_entityEvaluationuser" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
      </c:if>
      <c:if test="${pageset.operateType==2}">
      <a id="dom_edit_entityEvaluationuser" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
      </c:if>
      <a id="dom_cancle_formEvaluationuser" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
    </div>
  </div>
</div>
<script type="text/javascript">
  var submitAddActionEvaluationuser = 'evaluationuser/add.do';
  var submitEditActionEvaluationuser = 'evaluationuser/edit.do';
  var currentPageTypeEvaluationuser = '${pageset.operateType}';
  var submitFormEvaluationuser;
  $(function() {
    //表单组件对象
    submitFormEvaluationuser = $('#dom_formEvaluationuser').SubmitForm( {
      pageType : currentPageTypeEvaluationuser,
      grid : gridEvaluationuser,
      currentWindowId : 'winEvaluationuser'
    });
    //关闭窗口
    $('#dom_cancle_formEvaluationuser').bind('click', function() {
      $('#winEvaluationuser').window('close');
    });
    //提交新增数据
    $('#dom_add_entityEvaluationuser').bind('click', function() {
      submitFormEvaluationuser.postSubmit(submitAddActionEvaluationuser);
    });
    //提交修改数据
    $('#dom_edit_entityEvaluationuser').bind('click', function() {
      submitFormEvaluationuser.postSubmit(submitEditActionEvaluationuser);
    });
  });
  //-->
</script>