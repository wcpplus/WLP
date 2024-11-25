<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--专业课程表单-->
<div class="easyui-layout" data-options="fit:true">
  <div class="TableTitle" data-options="region:'north',border:false">
    <span class="label label-primary"> 
    <c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
    </span>
  </div>
  <div data-options="region:'center'">
    <form id="dom_formMajorclass">
      <input type="hidden" id="entity_id" name="id" value="${entity.id}">
      <table class="editTable">
      <tr>
        <td class="title">
        CHAPTERID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_chapterid" name="chapterid" value="${entity.chapterid}">
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
        SORT:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[5]']"
          id="entity_sort" name="sort" value="${entity.sort}">
        </td>
      </tr>
      <tr>
        <td class="title">
        MAJORID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_majorid" name="majorid" value="${entity.majorid}">
        </td>
      </tr>
    </table>
    </form>
  </div>
  <div data-options="region:'south',border:false">
    <div class="div_button" style="text-align: center; padding: 4px;">
      <c:if test="${pageset.operateType==1}">
      <a id="dom_add_entityMajorclass" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
      </c:if>
      <c:if test="${pageset.operateType==2}">
      <a id="dom_edit_entityMajorclass" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
      </c:if>
      <a id="dom_cancle_formMajorclass" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
    </div>
  </div>
</div>
<script type="text/javascript">
  var submitAddActionMajorclass = 'majorclass/add.do';
  var submitEditActionMajorclass = 'majorclass/edit.do';
  var currentPageTypeMajorclass = '${pageset.operateType}';
  var submitFormMajorclass;
  $(function() {
    //表单组件对象
    submitFormMajorclass = $('#dom_formMajorclass').SubmitForm( {
      pageType : currentPageTypeMajorclass,
      grid : gridMajorclass,
      currentWindowId : 'winMajorclass'
    });
    //关闭窗口
    $('#dom_cancle_formMajorclass').bind('click', function() {
      $('#winMajorclass').window('close');
    });
    //提交新增数据
    $('#dom_add_entityMajorclass').bind('click', function() {
      submitFormMajorclass.postSubmit(submitAddActionMajorclass);
    });
    //提交修改数据
    $('#dom_edit_entityMajorclass').bind('click', function() {
      submitFormMajorclass.postSubmit(submitEditActionMajorclass);
    });
  });
  //-->
</script>