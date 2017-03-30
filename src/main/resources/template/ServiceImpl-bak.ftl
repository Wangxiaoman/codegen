package com.ott.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ott.dao.${className}Dao;
import com.ott.domain.${className};
import com.ott.service.${className}Service;
import com.ott.platform.web.paging.Pagination;

@Service
public class ${className}ServiceImpl implements ${className}Service {
    @Resource
    private ${className}Dao ${className?uncap_first}Dao;

    @Override
    public int insert(${className} ${className?uncap_first}) throws Exception {
    	try{
        	return ${className?uncap_first}Dao.insert(${className?uncap_first});
        }catch(Exception ex){
        	throw ex;
        }
    }

    @Override
    public int update(${className} ${className?uncap_first}) throws Exception {
    	try{
        	return ${className?uncap_first}Dao.update(${className?uncap_first});
        }catch(Exception ex){
        	throw ex;
        }
    }

    @Override
    public int delete(int id) throws Exception {
    	try{
        	return ${className?uncap_first}Dao.delete(id);
    	}catch(Exception ex){
        	throw ex;
        }
    }

    @Override
    public Pagination<${className}> queryWebList(int page,int pageSize) throws Exception {
	    try{
	    	int totalSize = ${className?uncap_first}Dao.getCount();
			List<${className}> ${className?uncap_first}s = ${className?uncap_first}Dao.queryList(page*pageSize, pageSize);
			Pagination<${className}> result = new Pagination<${className}>(page,totalSize, ${className?uncap_first}s, pageSize);
			return result ;
        }catch(Exception ex){
	    	throw ex;
	    }
    }
    
    @Override
    public List<${className}> queryList(int page,int pageSize) throws Exception {
    	try{
			List<${className}> result = ${className?uncap_first}Dao.queryList(page*pageSize, pageSize);
			return result ;
        }catch(Exception ex){
	    	throw ex;
	    }
    }

    @Override
    public ${className} queryById(int id) throws Exception {
        try{	
        	return ${className?uncap_first}Dao.getById(id);
        }catch(Exception ex){
	    	throw ex;
	    }
    }

}
