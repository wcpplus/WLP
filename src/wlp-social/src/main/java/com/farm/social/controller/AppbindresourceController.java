package com.farm.social.controller;

import com.farm.social.domain.AppbindResource;
import com.farm.social.service.AppbindresourceServiceInter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import com.farm.web.easyui.EasyUiUtils;
import java.util.Map;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import com.farm.core.page.RequestMode;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

/* *
 *功能：应用绑定资源控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/appbindresource")
@Controller
public class AppbindresourceController extends WebUtils{
  private final static Logger log = Logger.getLogger(AppbindresourceController.class);
  @Resource
  private AppbindresourceServiceInter appbindResourceServiceImpl;


        /**
   * 查询结果集合
   * 
   * @return
   */
  @RequestMapping("/query")
  @ResponseBody
  public Map<String, Object> queryall(DataQuery query,
      HttpServletRequest request) {
        // TODO 自动生成代码,修改后请去除本注释
    try {
      query = EasyUiUtils.formatGridQuery(request, query);
      DataResult result = appbindResourceServiceImpl
          .createAppbindresourceSimpleQuery(query).search();
      return ViewMode.getInstance()
          .putAttrs(EasyUiUtils.formatGridData(result))
          .returnObjMode();
    } catch (Exception e) {
      log.error(e.getMessage());
      return ViewMode.getInstance().setError(e.getMessage(),e)
          .returnObjMode();
    }
  }


  /**
   * 提交修改数据
   * 
   * @return
   */
  @RequestMapping("/edit")
  @ResponseBody
  public Map<String, Object> editSubmit(AppbindResource entity, HttpSession session) {
        // TODO 自动生成代码,修改后请去除本注释
    try {
      entity = appbindResourceServiceImpl.editAppbindresourceEntity(entity,
          getCurrentUser(session));
      return ViewMode.getInstance().setOperate(OperateType.UPDATE)
          .putAttr("entity", entity).returnObjMode();
      
    } catch (Exception e) {
      log.error(e.getMessage());
      return ViewMode.getInstance().setOperate(OperateType.UPDATE)
          .setError(e.getMessage(),e).returnObjMode();
    }
  }

        /**
   * 提交新增数据
   * 
   * @return
   */
  @RequestMapping("/add")
  @ResponseBody
  public Map<String, Object> addSubmit(AppbindResource entity, HttpSession session) {
        // TODO 自动生成代码,修改后请去除本注释
    try {
      entity = appbindResourceServiceImpl.insertAppbindresourceEntity(entity,
          getCurrentUser(session));
      return ViewMode.getInstance().setOperate(OperateType.ADD)
                                        .putAttr("entity", entity).returnObjMode();
    } catch (Exception e) {
      log.error(e.getMessage());
      return ViewMode.getInstance().setOperate(OperateType.ADD)
                                       .setError(e.getMessage(),e).returnObjMode();
    }
  }

  /**
   * 删除数据
   * 
   * @return
   */
  @RequestMapping("/del")
  @ResponseBody
  public Map<String, Object> delSubmit(String ids, HttpSession session) {
    try {
      for (String id : parseIds(ids)) {
        appbindResourceServiceImpl.deleteAppbindresourceEntity(id,
            getCurrentUser(session));
      }
      return ViewMode.getInstance().returnObjMode();
    } catch (Exception e) {
      log.error(e.getMessage());
      return ViewMode.getInstance().setError(e.getMessage(),e)
          .returnObjMode();
    }
  }

  @RequestMapping("/list")
  public ModelAndView index(HttpSession session) {
    return ViewMode.getInstance()
        .returnModelAndView("social/AppbindresourceResult");
  }
        /**
   * 显示详细信息（修改或浏览时）
   *
   * @return
   */
  @RequestMapping("/form")
  public ModelAndView view(RequestMode pageset, String ids) {
    try {
      switch (pageset.getOperateType()) {
      case (0):{//查看
        return ViewMode.getInstance().putAttr("pageset", pageset)
            .putAttr("entity", appbindResourceServiceImpl.getAppbindresourceEntity(ids))
            .returnModelAndView("social/AppbindresourceForm");
      }
      case (1): {// 新增
        return ViewMode.getInstance().putAttr("pageset", pageset)
            .returnModelAndView("social/AppbindresourceForm");
      }
      case (2):{//修改
        return ViewMode.getInstance().putAttr("pageset", pageset)
            .putAttr("entity", appbindResourceServiceImpl.getAppbindresourceEntity(ids))
            .returnModelAndView("social/AppbindresourceForm");
      }
      default:
        break;
      }
      return ViewMode.getInstance().returnModelAndView("social/AppbindresourceForm");
    } catch (Exception e) {
      return ViewMode.getInstance().setError(e + e.getMessage(),e)
          .returnModelAndView("social/AppbindresourceForm");
    }
  }
}
