<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<PF:basePath/>">
    <title>用户评论数据管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="/view/conf/include.jsp"></jsp:include>
  </head>
  <body class="easyui-layout">
    <div data-options="region:'north',border:false">
      <form id="searchCommentsuserForm">
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
      <table id="dataCommentsuserGrid">
        <thead>
          <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th field="OBJID" data-options="sortable:true" width="50">OBJID</th>
            <th field="PARENTID" data-options="sortable:true" width="80">PARENTID</th>
            <th field="NOTE" data-options="sortable:true" width="40">NOTE</th>
            <th field="CTIME" data-options="sortable:true" width="50">CTIME</th>
            <th field="USERID" data-options="sortable:true" width="60">USERID</th>
            <th field="ID" data-options="sortable:true" width="20">ID</th>
          </tr>
        </thead>
      </table>
    </div>
  </body>
  <script type="text/javascript">
    var url_delActionCommentsuser = "commentsuser/del.do";//删除URL
    var url_formActionCommentsuser = "commentsuser/form.do";//增加、修改、查看URL
    var url_searchActionCommentsuser = "commentsuser/query.do";//查询URL
    var title_windowCommentsuser = "用户评论管理";//功能名称
    var gridCommentsuser;//数据表格对象
    var searchCommentsuser;//条件查询组件对象
    var toolBarCommentsuser = [ {
      id : 'view',
      text : '查看',
      iconCls : 'icon-tip',
      handler : viewDataCommentsuser
    }, {
      id : 'add',
      text : '新增',
      iconCls : 'icon-add',
      handler : addDataCommentsuser
    }, {
      id : 'edit',
      text : '修改',
      iconCls : 'icon-edit',
      handler : editDataCommentsuser
    }, {
      id : 'del',
      text : '删除',
      iconCls : 'icon-remove',
      handler : delDataCommentsuser
    } ];
    $(function() {
      //初始化数据表格
      gridCommentsuser = $('#dataCommentsuserGrid').datagrid( {
        url : url_searchActionCommentsuser,
        fit : true,
        fitColumns : true,
        'toolbar' : toolBarCommentsuser,
        pagination : true,
        closable : true,
        checkOnSelect : true,
        border:false,
        striped : true,
        rownumbers : true,
        ctrlSelect : true
      });
      //初始化条件查询
      searchCommentsuser = $('#searchCommentsuserForm').searchForm( {
        gridObj : gridCommentsuser
      });
    });
    //查看
    function viewDataCommentsuser() {
      var selectedArray = $(gridCommentsuser).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionCommentsuser + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
            + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winCommentsuser',
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
    function addDataCommentsuser() {
      var url = url_formActionCommentsuser + '?operateType='+PAGETYPE.ADD;
      $.farm.openWindow( {
        id : 'winCommentsuser',
        width : 600,
        height : 300,
        modal : true,
        url : url,
        title : '新增'
      });
    }
    //修改
    function editDataCommentsuser() {
      var selectedArray = $(gridCommentsuser).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionCommentsuser + '?operateType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winCommentsuser',
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
    function delDataCommentsuser() {
      var selectedArray = $(gridCommentsuser).datagrid('getSelections');
      if (selectedArray.length > 0) {
        // 有数据执行操作
        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
        $.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
          if (flag) {
            $(gridCommentsuser).datagrid('loading');
            $.post(url_delActionCommentsuser + '?ids=' + $.farm.getCheckedIds(gridCommentsuser,'ID'), {},
              function(flag) {
                var jsonObject = JSON.parse(flag, null);
                $(gridCommentsuser).datagrid('loaded');
                if (jsonObject.STATE == 0) {
                  $(gridCommentsuser).datagrid('reload');
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