<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--用户课时表单-->
<div class="easyui-layout" data-options="fit:true">
  <div class="TableTitle" data-options="region:'north',border:false">
    <span class="label label-primary"> 
    <c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
    </span>
  </div>
  <div data-options="region:'center'">
    <form id="dom_formUserhour">
      <input type="hidden" id="entity_id" name="id" value="${entity.id}">
      <table class="editTable">
      <tr>
        <td class="title">
        ETIME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[7]']"
          id="entity_etime" name="etime" value="${entity.etime}">
        </td>
      </tr>
      <tr>
        <td class="title">
        STIME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[7]']"
          id="entity_stime" name="stime" value="${entity.stime}">
        </td>
      </tr>
      <tr>
        <td class="title">
        CLASSID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_classid" name="classid" value="${entity.classid}">
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
      <tr>
        <td class="title">
        PSTATE:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[0.5]']"
          id="entity_pstate" name="pstate" value="${entity.pstate}">
        </td>
      </tr>
      <tr>
        <td class="title">
        HOURID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_hourid" name="hourid" value="${entity.hourid}">
        </td>
      </tr>
    </table>
    </form>
  </div>
  <div data-options="region:'south',border:false">
    <div class="div_button" style="text-align: center; padding: 4px;">
      <c:if test="${pageset.operateType==1}">
      <a id="dom_add_entityUserhour" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
      </c:if>
      <c:if test="${pageset.operateType==2}">
      <a id="dom_edit_entityUserhour" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
      </c:if>
      <a id="dom_cancle_formUserhour" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
    </div>
  </div>
</div>
<script type="text/javascript">
  var submitAddActionUserhour = 'userhour/add.do';
  var submitEditActionUserhour = 'userhour/edit.do';
  var currentPageTypeUserhour = '${pageset.operateType}';
  var submitFormUserhour;
  $(function() {
    //表单组件对象
    submitFormUserhour = $('#dom_formUserhour').SubmitForm( {
      pageType : currentPageTypeUserhour,
      grid : gridUserhour,
      currentWindowId : 'winUserhour'
    });
    //关闭窗口
    $('#dom_cancle_formUserhour').bind('click', function() {
      $('#winUserhour').window('close');
    });
    //提交新增数据
    $('#dom_add_entityUserhour').bind('click', function() {
      submitFormUserhour.postSubmit(submitAddActionUserhour);
    });
    //提交修改数据
    $('#dom_edit_entityUserhour').bind('click', function() {
      submitFormUserhour.postSubmit(submitEditActionUserhour);
    });
  });
  //-->
</script>