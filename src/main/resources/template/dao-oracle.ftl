package ${packagePath}.dao;

import ${packagePath}.domain.${className};

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
public interface ${className}Mapper {
    
    @Insert("${insertsql1} ${insertsql2}")
	@Options(useGeneratedKeys = true)
	int insert(${className} ${className?uncap_first}); 
	
	@Update("${updatesql}")
	int update(${className} ${className?uncap_first});
	
	@Delete("${deleteSql}")
	int delete(@Param("id")int id);
	
	@Select("${querysql}")
	List<${className}> queryList(@Param("offset")int offset,@Param("limit")int limit);
	
	@Select("${queryBeanSqlById}")
	${className} getById(@Param("id")int id);
	
	@Select("${countSql}")
	int getCount();
}
