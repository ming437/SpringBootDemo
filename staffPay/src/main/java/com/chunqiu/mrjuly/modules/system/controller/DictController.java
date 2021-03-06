package com.chunqiu.mrjuly.modules.system.controller;

import com.chunqiu.mrjuly.common.vo.Grid;
import com.chunqiu.mrjuly.common.vo.GridParam;
import com.chunqiu.mrjuly.common.vo.Json;
import com.chunqiu.mrjuly.modules.system.service.DictService;
import com.chunqiu.mrjuly.common.annotation.Log;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import lombok.extern.slf4j.Slf4j;

import com.chunqiu.mrjuly.common.persistence.BaseController;
import com.chunqiu.mrjuly.modules.system.model.Dict;

import java.util.List;

/**
 * 字典信息Controller
 * @author wcf
 * @version 2018-11-14
 */
@Slf4j
@Controller
@RequestMapping(value = "${adminPath}/system/dict")
public class DictController extends BaseController {

	@Autowired
	private DictService dictService;
	
	/**
	 * @Description：字典信息列表页面
	 * @author wcf
	 */
	@Log("登录字典页面")
	@RequestMapping("index")
	@RequiresPermissions("system:dict:view")
	public String index(){
		return "system/dictList";
	}
	
	/**
	 * @Description：字典信息列表数据
	 * @author wcf
	 */
	@Log("查询字典列表")
	@RequestMapping("list")
	@ResponseBody
	@RequiresPermissions("system:dict:view")
	public Grid list(Dict dict, GridParam param) {
		return dictService.findPage(dict, param);
	}
	/**
	 * @Description：字典信息表单页面--新增/编辑
	 * @author wcf
	 */
	@Log("登录字典新增/编辑页面")
	@RequestMapping(value = "form")
	@RequiresPermissions("system:dict:view")
	public String form(Dict dict, ModelMap model) {
		if(dict.getValue() == null && dict.getId() != null){
			dict = dictService.get(dict.getId());
		}
		model.addAttribute("dict", dict);
		return "system/dictForm";
	}
	/**
	 * @Description：字典信息新增/编辑 保存方法
	 * @author wcf
	 */
	@Log("保存字典")
	@RequestMapping("saveDict")
	@RequiresPermissions("system:dict:edit")
	public String saveDict(Dict dict, ModelMap model){
		try{
			dictService.save(dict);
		}catch(Exception e){
			log.error("保存失败！ msg={}", e.getMessage(), e);

			model.addAttribute("error", "保存失败！");
			return form(dict, model);
		}
		return successPath;
	}
	/**
	 * @Description：字典信息数据删除方法
	 * @author wcf
	 */
	@Log("删除字典")
	@RequestMapping("delDict")
	@ResponseBody
	@RequiresPermissions("system:dict:edit")
	public Json delDict(Dict dict){
		Json json = new Json();
		try{
			dictService.delete(dict);
			json.setSuccess(true);
		}catch(Exception e){
			log.error("删除失败！ msg={}", e.getMessage(), e);

			json.setSuccess(false);
			json.setMsg("删除失败！");
		}
		return json;
	}

	@Log("获取所有字典")
	@ResponseBody
	@RequestMapping(value = "listData")
	public List<Dict> listData(@RequestParam(required=false) String type) {
		Dict dict = new Dict();
		dict.setType(type);
		return dictService.findList(dict);
	}
}