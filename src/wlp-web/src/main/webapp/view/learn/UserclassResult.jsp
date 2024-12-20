<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<PF:basePath/>">
    <title>用户课程数据管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="/view/conf/include.jsp"></jsp:include>
  </head>
  <body class="easyui-layout">
    <div data-options="region:'north',border:false">
      <form id="searchUserclassForm">
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
      <table id="dataUserclassGrid">
        <thead>
          <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th field="NAMEBACK" data-options="sortable:true" width="80">NAMEBACK</th>
            <th field="CLASSID" data-options="sortable:true" width="70">CLASSID</th>
            <th field="PSTATE" data-options="sortable:true" width="60">PSTATE</th>
            <th field="ETIME" data-options="sortable:true" width="50">ETIME</th>
            <th field="STIME" data-options="sortable:true" width="50">STIME</th>
            <th field="CHOURSID" data-options="sortable:true" width="80">CHOURSID</th>
            <th field="USETIME" data-options="sortable:true" width="70">USETIME</th>
            <th field="PROCESS" data-options="sortable:true" width="70">PROCESS</th>
            <th field="ID" data-options="sortable:true" width="20">ID</th>
          </tr>
        </thead>
      </table>
    </div>
  </body>
  <script type="text/javascript">
    var url_delActionUserclass = "userclass/del.do";//删除URL
    var url_formActionUserclass = "userclass/form.do";//增加、修改、查看URL
    var url_searchActionUserclass = "userclass/query.do";//查询URL
    var title_windowUserclass = "用户课程管理";//功能名称
    var gridUserclass;//数据表格对象
    var searchUserclass;//条件查询组件对象
    var toolBarUserclass = [ {
      id : 'view',
      text : '查看',
      iconCls : 'icon-tip',
      handler : viewDataUserclass
    }, {
      id : 'add',
      text : '新增',
      iconCls : 'icon-add',
      handler : addDataUserclass
    }, {
      id : 'edit',
      text : '修改',
      iconCls : 'icon-edit',
      handler : editDataUserclass
    }, {
      id : 'del',
      text : '删除',
      iconCls : 'icon-remove',
      handler : delDataUserclass
    } ];
    $(function() {
      //初始化数据表格
      gridUserclass = $('#dataUserclassGrid').datagrid( {
        url : url_searchActionUserclass,
        fit : true,
        fitColumns : true,
        'toolbar' : toolBarUserclass,
        pagination : true,
        closable : true,
        checkOnSelect : true,
        border:false,
        striped : true,
        rownumbers : true,
        ctrlSelect : true
      });
      //初始化条件查询
      searchUserclass = $('#searchUserclassForm').searchForm( {
        gridObj : gridUserclass
      });
    });
    //查看
    function viewDataUserclass() {
      var selectedArray = $(gridUserclass).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionUserclass + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
            + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winUserclass',
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
    function addDataUserclass() {
      var url = url_formActionUserclass + '?operateType='+PAGETYPE.ADD;
      $.farm.openWindow( {
        id : 'winUserclass',
        width : 600,
        height : 300,
        modal : true,
        url : url,
        title : '新增'
      });
    }
    //修改
    function editDataUserclass() {
      var selectedArray = $(gridUserclass).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionUserclass + '?operateType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winUserclass',
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
    function delDataUserclass() {
      var selectedArray = $(gridUserclass).datagrid('getSelections');
      if (selectedArray.length > 0) {
        // 有数据执行操作
        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
        $.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
          if (flag) {
            $(gridUserclass).datagrid('loading');
            $.post(url_delActionUserclass + '?ids=' + $.farm.getCheckedIds(gridUserclass,'ID'), {},
              function(flag) {
                var jsonObject = JSON.parse(flag, null);
                $(gridUserclass).datagrid('loaded');
                if (jsonObject.STATE == 0) {
                  $(gridUserclass).datagrid('reload');
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