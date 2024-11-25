<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<PF:basePath/>">
    <title>课程分类权限数据管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="/view/conf/include.jsp"></jsp:include>
  </head>
  <body class="easyui-layout">
    <div data-options="region:'north',border:false">
      <form id="searchClasstypepopForm">
        <table class="editTable">
          <tr style="text-align: center;">
            <td colspan="4">
              <a id="a_search" href="javascript:void(0)"
                class="easyui-linkbutton" iconCls="icon-search">查询</a>
              <a id="a_reset" href="javascript:void(0)"
                class="easyui-linkbutton" iconCls="icon-reload">清除条件</a>
            </td>
          </tr>
        </table>
      </form>
    </div>
    <div data-options="region:'center',border:false">
      <table id="dataClasstypepopGrid">
        <thead>
          <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th field="TYPEID" data-options="sortable:true" width="60">TYPEID</th>
            <th field="ONAME" data-options="sortable:true" width="50">ONAME</th>
            <th field="OID" data-options="sortable:true" width="30">OID</th>
            <th field="FUNTYPE" data-options="sortable:true" width="70">FUNTYPE</th>
            <th field="POPTYPE" data-options="sortable:true" width="70">POPTYPE</th>
            <th field="ID" data-options="sortable:true" width="20">ID</th>
          </tr>
        </thead>
      </table>
    </div>
  </body>
  <script type="text/javascript">
    var url_delActionClasstypepop = "classtypepop/del.do";//删除URL
    var url_formActionClasstypepop = "classtypepop/form.do";//增加、修改、查看URL
    var url_searchActionClasstypepop = "classtypepop/query.do";//查询URL
    var title_windowClasstypepop = "课程分类权限管理";//功能名称
    var gridClasstypepop;//数据表格对象
    var searchClasstypepop;//条件查询组件对象
    var toolBarClasstypepop = [ {
      id : 'view',
      text : '查看',
      iconCls : 'icon-tip',
      handler : viewDataClasstypepop
    }, {
      id : 'add',
      text : '新增',
      iconCls : 'icon-add',
      handler : addDataClasstypepop
    }, {
      id : 'edit',
      text : '修改',
      iconCls : 'icon-edit',
      handler : editDataClasstypepop
    }, {
      id : 'del',
      text : '删除',
      iconCls : 'icon-remove',
      handler : delDataClasstypepop
    } ];
    $(function() {
      //初始化数据表格
      gridClasstypepop = $('#dataClasstypepopGrid').datagrid( {
        url : url_searchActionClasstypepop,
        fit : true,
        fitColumns : true,
        'toolbar' : toolBarClasstypepop,
        pagination : true,
        closable : true,
        checkOnSelect : true,
        border:false,
        striped : true,
        rownumbers : true,
        ctrlSelect : true
      });
      //初始化条件查询
      searchClasstypepop = $('#searchClasstypepopForm').searchForm( {
        gridObj : gridClasstypepop
      });
    });
    //查看
    function viewDataClasstypepop() {
      var selectedArray = $(gridClasstypepop).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionClasstypepop + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
            + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winClasstypepop',
          width : 600,
          height : 300,
          modal : true,
          url : url,
          title : '浏览'
        });
      } else {
        $.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
            'info');
      }
    }
    //新增
    function addDataClasstypepop() {
      var url = url_formActionClasstypepop + '?operateType='+PAGETYPE.ADD;
      $.farm.openWindow( {
        id : 'winClasstypepop',
        width : 600,
        height : 300,
        modal : true,
        url : url,
        title : '新增'
      });
    }
    //修改
    function editDataClasstypepop() {
      var selectedArray = $(gridClasstypepop).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionClasstypepop + '?operateType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winClasstypepop',
          width : 600,
          height : 300,
          modal : true,
          url : url,
          title : '修改'
        });
      } else {
        $.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
            'info');
      }
    }
    //删除
    function delDataClasstypepop() {
      var selectedArray = $(gridClasstypepop).datagrid('getSelections');
      if (selectedArray.length > 0) {
        // 有数据执行操作
        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
        $.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
          if (flag) {
            $(gridClasstypepop).datagrid('loading');
            $.post(url_delActionClasstypepop + '?ids=' + $.farm.getCheckedIds(gridClasstypepop,'ID'), {},
              function(flag) {
                var jsonObject = JSON.parse(flag, null);
                $(gridClasstypepop).datagrid('loaded');
                if (jsonObject.STATE == 0) {
                  $(gridClasstypepop).datagrid('reload');
              } else {
                var str = MESSAGE_PLAT.ERROR_SUBMIT
                    + jsonObject.MESSAGE;
                $.messager.alert(MESSAGE_PLAT.ERROR, str, 'error');
              }
            });
          }
        });
      } else {
        $.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
            'info');
      }
    }
  </script>
</html>