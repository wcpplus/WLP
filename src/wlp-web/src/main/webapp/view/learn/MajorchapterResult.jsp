<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<PF:basePath/>">
    <title>专业步骤数据管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="/view/conf/include.jsp"></jsp:include>
  </head>
  <body class="easyui-layout">
    <div data-options="region:'north',border:false">
      <form id="searchMajorchapterForm">
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
      <table id="dataMajorchapterGrid">
        <thead>
          <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th field="SORT" data-options="sortable:true" width="40">SORT</th>
            <th field="MAJORID" data-options="sortable:true" width="70">MAJORID</th>
            <th field="NOTE" data-options="sortable:true" width="40">NOTE</th>
            <th field="TITLE" data-options="sortable:true" width="50">TITLE</th>
            <th field="ID" data-options="sortable:true" width="20">ID</th>
          </tr>
        </thead>
      </table>
    </div>
  </body>
  <script type="text/javascript">
    var url_delActionMajorchapter = "majorchapter/del.do";//删除URL
    var url_formActionMajorchapter = "majorchapter/form.do";//增加、修改、查看URL
    var url_searchActionMajorchapter = "majorchapter/query.do";//查询URL
    var title_windowMajorchapter = "专业步骤管理";//功能名称
    var gridMajorchapter;//数据表格对象
    var searchMajorchapter;//条件查询组件对象
    var toolBarMajorchapter = [ {
      id : 'view',
      text : '查看',
      iconCls : 'icon-tip',
      handler : viewDataMajorchapter
    }, {
      id : 'add',
      text : '新增',
      iconCls : 'icon-add',
      handler : addDataMajorchapter
    }, {
      id : 'edit',
      text : '修改',
      iconCls : 'icon-edit',
      handler : editDataMajorchapter
    }, {
      id : 'del',
      text : '删除',
      iconCls : 'icon-remove',
      handler : delDataMajorchapter
    } ];
    $(function() {
      //初始化数据表格
      gridMajorchapter = $('#dataMajorchapterGrid').datagrid( {
        url : url_searchActionMajorchapter,
        fit : true,
        fitColumns : true,
        'toolbar' : toolBarMajorchapter,
        pagination : true,
        closable : true,
        checkOnSelect : true,
        border:false,
        striped : true,
        rownumbers : true,
        ctrlSelect : true
      });
      //初始化条件查询
      searchMajorchapter = $('#searchMajorchapterForm').searchForm( {
        gridObj : gridMajorchapter
      });
    });
    //查看
    function viewDataMajorchapter() {
      var selectedArray = $(gridMajorchapter).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionMajorchapter + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
            + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winMajorchapter',
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
    function addDataMajorchapter() {
      var url = url_formActionMajorchapter + '?operateType='+PAGETYPE.ADD;
      $.farm.openWindow( {
        id : 'winMajorchapter',
        width : 600,
        height : 300,
        modal : true,
        url : url,
        title : '新增'
      });
    }
    //修改
    function editDataMajorchapter() {
      var selectedArray = $(gridMajorchapter).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionMajorchapter + '?operateType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winMajorchapter',
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
    function delDataMajorchapter() {
      var selectedArray = $(gridMajorchapter).datagrid('getSelections');
      if (selectedArray.length > 0) {
        // 有数据执行操作
        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
        $.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
          if (flag) {
            $(gridMajorchapter).datagrid('loading');
            $.post(url_delActionMajorchapter + '?ids=' + $.farm.getCheckedIds(gridMajorchapter,'ID'), {},
              function(flag) {
                var jsonObject = JSON.parse(flag, null);
                $(gridMajorchapter).datagrid('loaded');
                if (jsonObject.STATE == 0) {
                  $(gridMajorchapter).datagrid('reload');
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