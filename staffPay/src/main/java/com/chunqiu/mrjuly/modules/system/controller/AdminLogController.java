package com.chunqiu.mrjuly.modules.system.controller;

import com.chunqiu.mrjuly.common.vo.Grid;
import com.chunqiu.mrjuly.common.vo.GridParam;
import com.chunqiu.mrjuly.common.vo.Json;
import com.chunqiu.mrjuly.common.annotation.Log;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import lombok.extern.slf4j.Slf4j;

import com.chunqiu.mrjuly.common.persistence.BaseController;
import com.chunqiu.mrjuly.modules.system.model.AdminLog;
import com.chunqiu.mrjuly.modules.system.service.AdminLogService;

/**
 * systemController
 * @author qj
 * @version 2019-01-14
 */
@Slf4j
@Controller
@RequestMapping(value = "${adminPath}/system/adminLog")
public class AdminLogController extends BaseController {

	@Autowired
	private AdminLogService adminLogService;
	
	/**
	 * @Description：system列表页面
	 * @author qj
	 */
	@Log("登录日志页面")
	@RequestMapping("index")
	@RequiresPermissions("system:adminLog:view")
	public String index(){
		return "system/adminLogList";
	}
	
	/**
	 * @Description：system列表数据
	 * @author qj
	 */
	@Log("查询日志列表")
	@RequestMapping("list")
	@ResponseBody
	@RequiresPermissions("system:adminLog:view")
	public Grid list(AdminLog adminLog, GridParam param) {
		return adminLogService.findPage(adminLog, param);
	}
	/**
	 * @Description：system表单页面--新增/编辑
	 * @author qj
	 */
	@Log("日志新增/编辑列表")
	@RequestMapping(value = "form")
	@RequiresPermissions("system:adminLog:view")
	public String form(AdminLog adminLog, ModelMap model) {
		adminLog = adminLogService.get(adminLog.getId());
		model.addAttribute("adminLog", adminLog);
		return "system/adminLogForm";
	}
	/**
	 * @Description：system新增/编辑 保存方法
	 * @author qj
	 */
	@Log("保存日志")
	@RequestMapping("saveAdminLog")
	@RequiresPermissions("system:adminLog:edit")
	public String saveAdminLog(AdminLog adminLog, ModelMap model){
		try{
			adminLogService.save(adminLog);
		}catch(Exception e){
			log.error("保存失败！ msg={}", e.getMessage(), e);

			model.addAttribute("error", "保存失败！");
			return form(adminLog, model);
		}
		return successPath;
	}
	/**
	 * @Description：system数据删除方法
	 * @author qj
	 */
	@Log("删除日志")
	@RequestMapping("delAdminLog")
	@ResponseBody
	@RequiresPermissions("system:adminLog:edit")
	public Json delAdminLog(AdminLog adminLog){
		Json json = new Json();
		try{
			adminLogService.delete(adminLog);
			json.setSuccess(true);
		}catch(Exception e){
			log.error("删除失败！ msg={}", e.getMessage(), e);

			json.setSuccess(false);
			json.setMsg("删除失败！");
		}
		return json;
	}

}