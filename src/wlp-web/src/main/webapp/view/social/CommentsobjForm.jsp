<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--评论对象表单-->
<div class="easyui-layout" data-options="fit:true">
  <div class="TableTitle" data-options="region:'north',border:false">
    <span class="label label-primary"> 
    <c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
    </span>
  </div>
  <div data-options="region:'center'">
    <form id="dom_formCommentsobj">
      <input type="hidden" id="entity_id" name="id" value="${entity.id}">
      <table class="editTable">
      <tr>
        <td class="title">
        NUM:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[5]']"
          id="entity_num" name="num" value="${entity.num}">
        </td>
      </tr>
      <tr>
        <td class="title">
        APPTYPE:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[0.5]']"
          id="entity_apptype" name="apptype" value="${entity.apptype}">
        </td>
      </tr>
      <tr>
        <td class="title">
        APPID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_appid" name="appid" value="${entity.appid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        PCONTENT:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[64]']"
          id="entity_pcontent" name="pcontent" value="${entity.pcontent}">
        </td>
      </tr>
    </table>
    </form>
  </div>
  <div data-options="region:'south',border:false">
    <div class="div_button" style="text-align: center; padding: 4px;">
      <c:if test="${pageset.operateType==1}">
      <a id="dom_add_entityCommentsobj" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
      </c:if>
      <c:if test="${pageset.operateType==2}">
      <a id="dom_edit_entityCommentsobj" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
      </c:if>
      <a id="dom_cancle_formCommentsobj" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
    </div>
  </div>
</div>
<script type="text/javascript">
  var submitAddActionCommentsobj = 'commentsobj/add.do';
  var submitEditActionCommentsobj = 'commentsobj/edit.do';
  var currentPageTypeCommentsobj = '${pageset.operateType}';
  var submitFormCommentsobj;
  $(function() {
    //表单组件对象
    submitFormCommentsobj = $('#dom_formCommentsobj').SubmitForm( {
      pageType : currentPageTypeCommentsobj,
      grid : gridCommentsobj,
      currentWindowId : 'winCommentsobj'
    });
    //关闭窗口
    $('#dom_cancle_formCommentsobj').bind('click', function() {
      $('#winCommentsobj').window('close');
    });
    //提交新增数据
    $('#dom_add_entityCommentsobj').bind('click', function() {
      submitFormCommentsobj.postSubmit(submitAddActionCommentsobj);
    });
    //提交修改数据
    $('#dom_edit_entityCommentsobj').bind('click', function() {
      submitFormCommentsobj.postSubmit(submitEditActionCommentsobj);
    });
  });
  //-->
</script>