package com.ott.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ott.Mapper.${className}Mapper;
import com.ott.domain.${className};
import com.ott.service.${className}Service;
import com.ott.platform.web.paging.Pagination;

@Service
public class ${className}ServiceImpl implements ${className}Service {
    @Resource
    private ${className}Mapper ${className?uncap_first}Mapper;

    @Override
    public int insert(${className} ${className?uncap_first}) throws Exception {
    	try{
        	return ${className?uncap_first}Mapper.insert(${className?uncap_first});
        }catch(Exception ex){
        	throw ex;
        }
    }

    @Override
    public int update(${className} ${className?uncap_first}) throws Exception {
    	try{
        	return ${className?uncap_first}Mapper.update(${className?uncap_first});
        }catch(Exception ex){
        	throw ex;
        }
    }

    @Override
    public int delete(int id) throws Exception {
    	try{
        	return ${className?uncap_first}Mapper.delete(id);
    	}catch(Exception ex){
        	throw ex;
        }
    }

    @Override
    public List<${className}> queryList(int page,int pageSize) throws Exception {
    	try{
			List<${className}> result = ${className?uncap_first}Mapper.queryList(page*pageSize, pageSize);
			return result ;
        }catch(Exception ex){
	    	throw ex;
	    }
    }

    @Override
    public ${className} queryById(int id) throws Exception {
        try{	
        	return ${className?uncap_first}Mapper.getById(id);
        }catch(Exception ex){
	    	throw ex;
	    }
    }

}
