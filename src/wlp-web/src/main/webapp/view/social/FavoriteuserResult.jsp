<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<PF:basePath/>">
    <title>用户收藏数据管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="/view/conf/include.jsp"></jsp:include>
  </head>
  <body class="easyui-layout">
    <div data-options="region:'north',border:false">
      <form id="searchFavoriteuserForm">
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
      <table id="dataFavoriteuserGrid">
        <thead>
          <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th field="OBJID" data-options="sortable:true" width="50">OBJID</th>
            <th field="CTIME" data-options="sortable:true" width="50">CTIME</th>
            <th field="DOTYPE" data-options="sortable:true" width="60">DOTYPE</th>
            <th field="USERID" data-options="sortable:true" width="60">USERID</th>
            <th field="ID" data-options="sortable:true" width="20">ID</th>
          </tr>
        </thead>
      </table>
    </div>
  </body>
  <script type="text/javascript">
    var url_delActionFavoriteuser = "favoriteuser/del.do";//删除URL
    var url_formActionFavoriteuser = "favoriteuser/form.do";//增加、修改、查看URL
    var url_searchActionFavoriteuser = "favoriteuser/query.do";//查询URL
    var title_windowFavoriteuser = "用户收藏管理";//功能名称
    var gridFavoriteuser;//数据表格对象
    var searchFavoriteuser;//条件查询组件对象
    var toolBarFavoriteuser = [ {
      id : 'view',
      text : '查看',
      iconCls : 'icon-tip',
      handler : viewDataFavoriteuser
    }, {
      id : 'add',
      text : '新增',
      iconCls : 'icon-add',
      handler : addDataFavoriteuser
    }, {
      id : 'edit',
      text : '修改',
      iconCls : 'icon-edit',
      handler : editDataFavoriteuser
    }, {
      id : 'del',
      text : '删除',
      iconCls : 'icon-remove',
      handler : delDataFavoriteuser
    } ];
    $(function() {
      //初始化数据表格
      gridFavoriteuser = $('#dataFavoriteuserGrid').datagrid( {
        url : url_searchActionFavoriteuser,
        fit : true,
        fitColumns : true,
        'toolbar' : toolBarFavoriteuser,
        pagination : true,
        closable : true,
        checkOnSelect : true,
        border:false,
        striped : true,
        rownumbers : true,
        ctrlSelect : true
      });
      //初始化条件查询
      searchFavoriteuser = $('#searchFavoriteuserForm').searchForm( {
        gridObj : gridFavoriteuser
      });
    });
    //查看
    function viewDataFavoriteuser() {
      var selectedArray = $(gridFavoriteuser).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionFavoriteuser + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
            + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winFavoriteuser',
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
    function addDataFavoriteuser() {
      var url = url_formActionFavoriteuser + '?operateType='+PAGETYPE.ADD;
      $.farm.openWindow( {
        id : 'winFavoriteuser',
        width : 600,
        height : 300,
        modal : true,
        url : url,
        title : '新增'
      });
    }
    //修改
    function editDataFavoriteuser() {
      var selectedArray = $(gridFavoriteuser).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionFavoriteuser + '?operateType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winFavoriteuser',
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
    function delDataFavoriteuser() {
      var selectedArray = $(gridFavoriteuser).datagrid('getSelections');
      if (selectedArray.length > 0) {
        // 有数据执行操作
        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
        $.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
          if (flag) {
            $(gridFavoriteuser).datagrid('loading');
            $.post(url_delActionFavoriteuser + '?ids=' + $.farm.getCheckedIds(gridFavoriteuser,'ID'), {},
              function(flag) {
                var jsonObject = JSON.parse(flag, null);
                $(gridFavoriteuser).datagrid('loaded');
                if (jsonObject.STATE == 0) {
                  $(gridFavoriteuser).datagrid('reload');
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