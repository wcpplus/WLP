<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<PF:basePath/>">
    <title>专业权限数据管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="/view/conf/include.jsp"></jsp:include>
  </head>
  <body class="easyui-layout">
    <div data-options="region:'north',border:false">
      <form id="searchMajorpopForm">
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
      <table id="dataMajorpopGrid">
        <thead>
          <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th field="MAJORID" data-options="sortable:true" width="70">MAJORID</th>
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
    var url_delActionMajorpop = "majorpop/del.do";//删除URL
    var url_formActionMajorpop = "majorpop/form.do";//增加、修改、查看URL
    var url_searchActionMajorpop = "majorpop/query.do";//查询URL
    var title_windowMajorpop = "专业权限管理";//功能名称
    var gridMajorpop;//数据表格对象
    var searchMajorpop;//条件查询组件对象
    var toolBarMajorpop = [ {
      id : 'view',
      text : '查看',
      iconCls : 'icon-tip',
      handler : viewDataMajorpop
    }, {
      id : 'add',
      text : '新增',
      iconCls : 'icon-add',
      handler : addDataMajorpop
    }, {
      id : 'edit',
      text : '修改',
      iconCls : 'icon-edit',
      handler : editDataMajorpop
    }, {
      id : 'del',
      text : '删除',
      iconCls : 'icon-remove',
      handler : delDataMajorpop
    } ];
    $(function() {
      //初始化数据表格
      gridMajorpop = $('#dataMajorpopGrid').datagrid( {
        url : url_searchActionMajorpop,
        fit : true,
        fitColumns : true,
        'toolbar' : toolBarMajorpop,
        pagination : true,
        closable : true,
        checkOnSelect : true,
        border:false,
        striped : true,
        rownumbers : true,
        ctrlSelect : true
      });
      //初始化条件查询
      searchMajorpop = $('#searchMajorpopForm').searchForm( {
        gridObj : gridMajorpop
      });
    });
    //查看
    function viewDataMajorpop() {
      var selectedArray = $(gridMajorpop).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionMajorpop + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
            + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winMajorpop',
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
    function addDataMajorpop() {
      var url = url_formActionMajorpop + '?operateType='+PAGETYPE.ADD;
      $.farm.openWindow( {
        id : 'winMajorpop',
        width : 600,
        height : 300,
        modal : true,
        url : url,
        title : '新增'
      });
    }
    //修改
    function editDataMajorpop() {
      var selectedArray = $(gridMajorpop).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionMajorpop + '?operateType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winMajorpop',
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
    function delDataMajorpop() {
      var selectedArray = $(gridMajorpop).datagrid('getSelections');
      if (selectedArray.length > 0) {
        // 有数据执行操作
        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
        $.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
          if (flag) {
            $(gridMajorpop).datagrid('loading');
            $.post(url_delActionMajorpop + '?ids=' + $.farm.getCheckedIds(gridMajorpop,'ID'), {},
              function(flag) {
                var jsonObject = JSON.parse(flag, null);
                $(gridMajorpop).datagrid('loaded');
                if (jsonObject.STATE == 0) {
                  $(gridMajorpop).datagrid('reload');
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