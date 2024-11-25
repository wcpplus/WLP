<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--应用绑定资源表单-->
<div class="easyui-layout" data-options="fit:true">
  <div class="TableTitle" data-options="region:'north',border:false">
    <span class="label label-primary"> 
    <c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
    </span>
  </div>
  <div data-options="region:'center'">
    <form id="dom_formAppbindresource">
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
        SYSID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_sysid" name="sysid" value="${entity.sysid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        RESOURCETYPE:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[0.5]']"
          id="entity_resourcetype" name="resourcetype" value="${entity.resourcetype}">
        </td>
      </tr>
      <tr>
        <td class="title">
        RESOURCEURL:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[128]']"
          id="entity_resourceurl" name="resourceurl" value="${entity.resourceurl}">
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
      <a id="dom_add_entityAppbindresource" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
      </c:if>
      <c:if test="${pageset.operateType==2}">
      <a id="dom_edit_entityAppbindresource" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
      </c:if>
      <a id="dom_cancle_formAppbindresource" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
    </div>
  </div>
</div>
<script type="text/javascript">
  var submitAddActionAppbindresource = 'appbindresource/add.do';
  var submitEditActionAppbindresource = 'appbindresource/edit.do';
  var currentPageTypeAppbindresource = '${pageset.operateType}';
  var submitFormAppbindresource;
  $(function() {
    //表单组件对象
    submitFormAppbindresource = $('#dom_formAppbindresource').SubmitForm( {
      pageType : currentPageTypeAppbindresource,
      grid : gridAppbindresource,
      currentWindowId : 'winAppbindresource'
    });
    //关闭窗口
    $('#dom_cancle_formAppbindresource').bind('click', function() {
      $('#winAppbindresource').window('close');
    });
    //提交新增数据
    $('#dom_add_entityAppbindresource').bind('click', function() {
      submitFormAppbindresource.postSubmit(submitAddActionAppbindresource);
    });
    //提交修改数据
    $('#dom_edit_entityAppbindresource').bind('click', function() {
      submitFormAppbindresource.postSubmit(submitEditActionAppbindresource);
    });
  });
  //-->
</script>