<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<PF:basePath/>">
    <title>应用绑定资源数据管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="/view/conf/include.jsp"></jsp:include>
  </head>
  <body class="easyui-layout">
    <div data-options="region:'north',border:false">
      <form id="searchAppbindresourceForm">
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
      <table id="dataAppbindresourceGrid">
        <thead>
          <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th field="OBJID" data-options="sortable:true" width="50">OBJID</th>
            <th field="SYSID" data-options="sortable:true" width="50">SYSID</th>
            <th field="RESOURCETYPE" data-options="sortable:true" width="120">RESOURCETYPE</th>
            <th field="RESOURCEURL" data-options="sortable:true" width="110">RESOURCEURL</th>
            <th field="CTIME" data-options="sortable:true" width="50">CTIME</th>
            <th field="USERID" data-options="sortable:true" width="60">USERID</th>
            <th field="ID" data-options="sortable:true" width="20">ID</th>
          </tr>
        </thead>
      </table>
    </div>
  </body>
  <script type="text/javascript">
    var url_delActionAppbindresource = "appbindresource/del.do";//删除URL
    var url_formActionAppbindresource = "appbindresource/form.do";//增加、修改、查看URL
    var url_searchActionAppbindresource = "appbindresource/query.do";//查询URL
    var title_windowAppbindresource = "应用绑定资源管理";//功能名称
    var gridAppbindresource;//数据表格对象
    var searchAppbindresource;//条件查询组件对象
    var toolBarAppbindresource = [ {
      id : 'view',
      text : '查看',
      iconCls : 'icon-tip',
      handler : viewDataAppbindresource
    }, {
      id : 'add',
      text : '新增',
      iconCls : 'icon-add',
      handler : addDataAppbindresource
    }, {
      id : 'edit',
      text : '修改',
      iconCls : 'icon-edit',
      handler : editDataAppbindresource
    }, {
      id : 'del',
      text : '删除',
      iconCls : 'icon-remove',
      handler : delDataAppbindresource
    } ];
    $(function() {
      //初始化数据表格
      gridAppbindresource = $('#dataAppbindresourceGrid').datagrid( {
        url : url_searchActionAppbindresource,
        fit : true,
        fitColumns : true,
        'toolbar' : toolBarAppbindresource,
        pagination : true,
        closable : true,
        checkOnSelect : true,
        border:false,
        striped : true,
        rownumbers : true,
        ctrlSelect : true
      });
      //初始化条件查询
      searchAppbindresource = $('#searchAppbindresourceForm').searchForm( {
        gridObj : gridAppbindresource
      });
    });
    //查看
    function viewDataAppbindresource() {
      var selectedArray = $(gridAppbindresource).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionAppbindresource + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
            + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winAppbindresource',
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
    function addDataAppbindresource() {
      var url = url_formActionAppbindresource + '?operateType='+PAGETYPE.ADD;
      $.farm.openWindow( {
        id : 'winAppbindresource',
        width : 600,
        height : 300,
        modal : true,
        url : url,
        title : '新增'
      });
    }
    //修改
    function editDataAppbindresource() {
      var selectedArray = $(gridAppbindresource).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionAppbindresource + '?operateType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winAppbindresource',
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
    function delDataAppbindresource() {
      var selectedArray = $(gridAppbindresource).datagrid('getSelections');
      if (selectedArray.length > 0) {
        // 有数据执行操作
        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
        $.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
          if (flag) {
            $(gridAppbindresource).datagrid('loading');
            $.post(url_delActionAppbindresource + '?ids=' + $.farm.getCheckedIds(gridAppbindresource,'ID'), {},
              function(flag) {
                var jsonObject = JSON.parse(flag, null);
                $(gridAppbindresource).datagrid('loaded');
                if (jsonObject.STATE == 0) {
                  $(gridAppbindresource).datagrid('reload');
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