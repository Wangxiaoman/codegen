package ${packagePath}.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ${packagePath}.Mapper.${className}Mapper;
import ${packagePath}.domain.${className};
import ${packagePath}.service.${className}Service;
import ${packagePath}.platform.web.paging.Pagination;

@Service
public class ${className}ServiceImpl implements ${className}Service {
    @Resource
    private ${className}Mapper ${className?uncap_first}Mapper;

    @Override
    public int insert(${className} ${className?uncap_first}) {
        return ${className?uncap_first}Mapper.insert(${className?uncap_first});
    }

    @Override
    public int update(${className} ${className?uncap_first}) throws Exception {
        return ${className?uncap_first}Mapper.update(${className?uncap_first});
    }

    @Override
    public int delete(int id) throws Exception {
        return ${className?uncap_first}Mapper.delete(id);
    }

    @Override
    public List<${className}> queryList(int page,int pageSize) throws Exception {
		List<${className}> result = ${className?uncap_first}Mapper.queryList(page*pageSize, pageSize);
		return result ;
    }

    @Override
    public ${className} queryById(int id) throws Exception {
        return ${className?uncap_first}Mapper.getById(id);
    }

}
