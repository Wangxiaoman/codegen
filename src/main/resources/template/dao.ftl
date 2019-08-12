package ${packagePath}.mapper;

import ${packagePath}.domain.${className};

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ${className}Mapper {
    
    @Insert("${insertsql1} values ${insertsql2}")
	@Options(useGeneratedKeys = true)
	int insert(${className} ${className?uncap_first}); 
	
	@Insert({
        "<script>"
        + "${insertsql1} values"
        + "<foreach item='item' index='index' collection='${className?uncap_first}s' separator=',' >"
        +    "${insertSqlBatch}"
        + "</foreach>"
        +"</script>" 
    })
    int batchInsert(@Param("${className?uncap_first}s")List<${className}> ${className?uncap_first}s);
	
	@Update("${updatesql}")
	int update(${className} ${className?uncap_first});
	
	@Delete("${deleteSql}")
	int delete(@Param("id")int id);
	
	@Select("${querysql}")
	List<${className}> queryList(@Param("offset")int offset,@Param("limit")int limit);
	
	@Select({
        "<script>"
            + "select * from ${queryBeanSqlNoCondition} WHERE id in "
            + "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"
            +    "${r'#{item}'}"
            + "</foreach>" 
        +"</script>" 
    })
    List<${className}> queryListByIds(@Param("ids")List<Integer> ids);
	
	@Select("${queryBeanSqlById}")
	${className} getById(@Param("id")int id);
	
	@Select("${countSql}")
	int getCount();
}
