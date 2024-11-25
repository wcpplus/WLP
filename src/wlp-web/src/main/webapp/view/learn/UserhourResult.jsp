<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<PF:basePath/>">
    <title>用户课时数据管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="/view/conf/include.jsp"></jsp:include>
  </head>
  <body class="easyui-layout">
    <div data-options="region:'north',border:false">
      <form id="searchUserhourForm">
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
      <table id="dataUserhourGrid">
        <thead>
          <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th field="ETIME" data-options="sortable:true" width="50">ETIME</th>
            <th field="STIME" data-options="sortable:true" width="50">STIME</th>
            <th field="CLASSID" data-options="sortable:true" width="70">CLASSID</th>
            <th field="USERID" data-options="sortable:true" width="60">USERID</th>
            <th field="PSTATE" data-options="sortable:true" width="60">PSTATE</th>
            <th field="HOURID" data-options="sortable:true" width="60">HOURID</th>
            <th field="ID" data-options="sortable:true" width="20">ID</th>
          </tr>
        </thead>
      </table>
    </div>
  </body>
  <script type="text/javascript">
    var url_delActionUserhour = "userhour/del.do";//删除URL
    var url_formActionUserhour = "userhour/form.do";//增加、修改、查看URL
    var url_searchActionUserhour = "userhour/query.do";//查询URL
    var title_windowUserhour = "用户课时管理";//功能名称
    var gridUserhour;//数据表格对象
    var searchUserhour;//条件查询组件对象
    var toolBarUserhour = [ {
      id : 'view',
      text : '查看',
      iconCls : 'icon-tip',
      handler : viewDataUserhour
    }, {
      id : 'add',
      text : '新增',
      iconCls : 'icon-add',
      handler : addDataUserhour
    }, {
      id : 'edit',
      text : '修改',
      iconCls : 'icon-edit',
      handler : editDataUserhour
    }, {
      id : 'del',
      text : '删除',
      iconCls : 'icon-remove',
      handler : delDataUserhour
    } ];
    $(function() {
      //初始化数据表格
      gridUserhour = $('#dataUserhourGrid').datagrid( {
        url : url_searchActionUserhour,
        fit : true,
        fitColumns : true,
        'toolbar' : toolBarUserhour,
        pagination : true,
        closable : true,
        checkOnSelect : true,
        border:false,
        striped : true,
        rownumbers : true,
        ctrlSelect : true
      });
      //初始化条件查询
      searchUserhour = $('#searchUserhourForm').searchForm( {
        gridObj : gridUserhour
      });
    });
    //查看
    function viewDataUserhour() {
      var selectedArray = $(gridUserhour).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionUserhour + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
            + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winUserhour',
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
    function addDataUserhour() {
      var url = url_formActionUserhour + '?operateType='+PAGETYPE.ADD;
      $.farm.openWindow( {
        id : 'winUserhour',
        width : 600,
        height : 300,
        modal : true,
        url : url,
        title : '新增'
      });
    }
    //修改
    function editDataUserhour() {
      var selectedArray = $(gridUserhour).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionUserhour + '?operateType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winUserhour',
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
    function delDataUserhour() {
      var selectedArray = $(gridUserhour).datagrid('getSelections');
      if (selectedArray.length > 0) {
        // 有数据执行操作
        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
        $.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
          if (flag) {
            $(gridUserhour).datagrid('loading');
            $.post(url_delActionUserhour + '?ids=' + $.farm.getCheckedIds(gridUserhour,'ID'), {},
              function(flag) {
                var jsonObject = JSON.parse(flag, null);
                $(gridUserhour).datagrid('loaded');
                if (jsonObject.STATE == 0) {
                  $(gridUserhour).datagrid('reload');
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