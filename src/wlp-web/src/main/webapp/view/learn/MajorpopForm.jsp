<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--专业权限表单-->
<div class="easyui-layout" data-options="fit:true">
  <div class="TableTitle" data-options="region:'north',border:false">
    <span class="label label-primary"> 
    <c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
    </span>
  </div>
  <div data-options="region:'center'">
    <form id="dom_formMajorpop">
      <input type="hidden" id="entity_id" name="id" value="${entity.id}">
      <table class="editTable">
      <tr>
        <td class="title">
        MAJORID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_majorid" name="majorid" value="${entity.majorid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        ONAME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[32]']"
          id="entity_oname" name="oname" value="${entity.oname}">
        </td>
      </tr>
      <tr>
        <td class="title">
        OID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_oid" name="oid" value="${entity.oid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        FUNTYPE:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[0.5]']"
          id="entity_funtype" name="funtype" value="${entity.funtype}">
        </td>
      </tr>
      <tr>
        <td class="title">
        POPTYPE:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[0.5]']"
          id="entity_poptype" name="poptype" value="${entity.poptype}">
        </td>
      </tr>
    </table>
    </form>
  </div>
  <div data-options="region:'south',border:false">
    <div class="div_button" style="text-align: center; padding: 4px;">
      <c:if test="${pageset.operateType==1}">
      <a id="dom_add_entityMajorpop" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
      </c:if>
      <c:if test="${pageset.operateType==2}">
      <a id="dom_edit_entityMajorpop" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
      </c:if>
      <a id="dom_cancle_formMajorpop" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
    </div>
  </div>
</div>
<script type="text/javascript">
  var submitAddActionMajorpop = 'majorpop/add.do';
  var submitEditActionMajorpop = 'majorpop/edit.do';
  var currentPageTypeMajorpop = '${pageset.operateType}';
  var submitFormMajorpop;
  $(function() {
    //表单组件对象
    submitFormMajorpop = $('#dom_formMajorpop').SubmitForm( {
      pageType : currentPageTypeMajorpop,
      grid : gridMajorpop,
      currentWindowId : 'winMajorpop'
    });
    //关闭窗口
    $('#dom_cancle_formMajorpop').bind('click', function() {
      $('#winMajorpop').window('close');
    });
    //提交新增数据
    $('#dom_add_entityMajorpop').bind('click', function() {
      submitFormMajorpop.postSubmit(submitAddActionMajorpop);
    });
    //提交修改数据
    $('#dom_edit_entityMajorpop').bind('click', function() {
      submitFormMajorpop.postSubmit(submitEditActionMajorpop);
    });
  });
  //-->
</script>