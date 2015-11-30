package com.blink.controller.web;

import java.util.List;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.blink.domain.${className};
import com.blink.service.${className}Service;
import com.blink.platform.web.paging.Pagination;

@Controller
@RequestMapping("/web")
public class ${className}WebController{
	private static final Logger LOGGER = Logger.getLogger(${className}WebController.class);

    @Resource
    private ${className}Service ${className?uncap_first}Service;

	@RequestMapping(value = "/${className?uncap_first}", method = RequestMethod.POST)
	public String save(
		@RequestParam(value = "id",required=false,defaultValue = "0") int id,
		<#list fieldName as field>
		@RequestParam(value = "${field.NAME}",required=false) ${field.TYPE} ${field.NAME},
		</#list>
		Model model
		) {
		
			${className} ${className?uncap_first} = new ${className}();
			${className?uncap_first}.setId(id);
		<#list fieldName as field>
			${className?uncap_first}.set${field.NAME?cap_first}(${field.NAME});
		</#list>
		try {
			if(id>0){
				${className?uncap_first}Service.update(${className?uncap_first});
			}else{
				${className?uncap_first}Service.insert(${className?uncap_first});
			}
		} catch (Exception e) {
			LOGGER.error("${className}Controller->save",e);
		}
		
		return "redirect:/web/${className?uncap_first}/list";
	}
	
	
	@RequestMapping(value = "/${className?uncap_first}/toEdit", method = RequestMethod.GET)
	public String toEdit(Model model,
			@RequestParam(value = "id",required=true) int id) {
		try {
			if(id >0){
				${className} ${className?uncap_first} = ${className?uncap_first}Service.queryById(id);
				model.addAttribute("${className?uncap_first}", ${className?uncap_first});
			}
		} catch (Exception e) {
			LOGGER.error("${className}Controller->edit",e);
		}
		
		return "/${className?uncap_first}/edit";
	}
	
	
	@RequestMapping(value = "/${className?uncap_first}/toAdd", method = RequestMethod.GET)
	public String toAdd(Model model) {
		return "/${className?uncap_first}/add";
	}
	
	@RequestMapping(value = "/${className?uncap_first}/delete", method = RequestMethod.GET)
	public String delete(@RequestParam(value = "id", defaultValue = "0") int id, Model model) {
		try {
			if(id > 0){
				${className} ${className?uncap_first} = ${className?uncap_first}Service.queryById(id);
				if(${className?uncap_first} != null){
					${className?uncap_first}Service.delete(id);
				}
			}
		} catch (Exception e) {
			LOGGER.error("${className}Controller->delete",e);
		}
		
		return "redirect:/web/${className?uncap_first}/list";
	}
	
	@RequestMapping(value = "/${className?uncap_first}/list", method = RequestMethod.GET)
	public String toList(Model model,
			@RequestParam(value = "page",required=false,defaultValue="0") int page
			) {
		try {
			int pageSize = 20;
			
			Pagination<${className}> result = ${className?uncap_first}Service.queryWebList(page, pageSize);
			if(result != null){
				model.addAttribute("pagination", result);
			}
		} catch (Exception e) {
			LOGGER.error("${className}Controller->list",e);
		}
		
		return "/${className?uncap_first}/query";
	}
}
