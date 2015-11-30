package com.blink.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.blink.domain.${className};
import com.blink.service.${className}Service;
import com.blink.constants.ResultJson;
import com.blink.constants.CommonStatus;

@Controller
public class ${className}Controller{
	private static final Logger LOGGER = Logger.getLogger(${className}Controller.class);

    @Resource
    private ${className}Service ${className?uncap_first}Service;

	@RequestMapping(value = "/${className?uncap_first}", method = RequestMethod.POST)
	@ResponseBody
	public ResultJson save(
		@RequestParam(value = "id",defaultValue="0",required=false) int id,
		<#list fieldName as field>
			@RequestParam(value = "${field.NAME}") ${field.TYPE} ${field.NAME}<#if field_has_next> ,</#if>
		</#list>
		) {
			//增加一些校验方法
			${className} ${className?uncap_first} = new ${className}();
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
			return new ResultJson(CommonStatus.SERVER_ERROR.getValue());
		}
		
		return new ResultJson(CommonStatus.SUCCESS.getValue());
	}
	
	
	@RequestMapping(value = "/${className?uncap_first}", method = RequestMethod.GET)
	@ResponseBody
	public ResultJson getDetail(Model model,
			@RequestParam(value = "id",required=true) int id) {
		try {
			if(id >0){
				${className} ${className?uncap_first} = ${className?uncap_first}Service.queryById(id);
				model.addAttribute("${className?uncap_first}", ${className?uncap_first});
				
				return new ResultJson(CommonStatus.SUCCESS.getValue(),${className?uncap_first});
			}
		} catch (Exception e) {
			LOGGER.error("${className}Controller->getDetail",e);
			return new ResultJson(CommonStatus.SERVER_ERROR.getValue());
		}
		
		return new ResultJson(CommonStatus.DATA_NOT_EXIST.getValue());
	}
	
	@RequestMapping(value = "/${className?uncap_first}/delete", method = RequestMethod.GET)
	@ResponseBody
	public ResultJson delete(@RequestParam(value = "id", defaultValue = "0") int id, Model model) {
		try {
			if(id > 0){
				${className} ${className?uncap_first} = ${className?uncap_first}Service.queryById(id);
				if(${className?uncap_first} != null){
					${className?uncap_first}Service.delete(id);
				}
				return new ResultJson(CommonStatus.SUCCESS.getValue());
			}
		} catch (Exception e) {
			LOGGER.error("${className}Controller->delete",e);
			return new ResultJson(CommonStatus.SERVER_ERROR.getValue());
		}
		
		return new ResultJson(CommonStatus.DATA_NOT_EXIST.getValue());
	}
	
	@RequestMapping(value = "/${className?uncap_first}/list", method = RequestMethod.GET)
	@ResponseBody
	public ResultJson toList(Model model,
			@RequestParam(value = "page",required=false,defaultValue="0") int page
			) {
		try {
			int pageSize = 20;
			List<${className}> ${className?uncap_first}s = ${className?uncap_first}Service.queryList(page, pageSize);
			if(CollectionUtils.isNotEmpty(${className?uncap_first}s)){
				return new ResultJson(CommonStatus.SUCCESS.getValue(),${className?uncap_first}s);
			}
		} catch (Exception e) {
			LOGGER.error("${className}Controller->toList",e);
			return new ResultJson(CommonStatus.SERVER_ERROR.getValue());
		}
		
		return new ResultJson(CommonStatus.DATA_NOT_EXIST.getValue());
	}
}
