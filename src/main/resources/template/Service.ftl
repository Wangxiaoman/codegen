package ${packagePath}.service;

import ${packagePath}.domain.${className};
import ${packagePath}.platform.web.paging.Pagination;

import java.util.List;

public interface ${className}Service {
    /**
     * 插入
     *
     * @param entity 实体对象
     * @return 实体对象
     * @ 系统异常
     */
     int insert(${className} ${className?uncap_first}) ;

    /**
     * 修改
     *
     * @param entity 实体对象
     * @return 更新记录数
     * @ 系统异常
     */
     int update(${className} ${className?uncap_first}) ;

    /**
     * 删除
     *
     * @param id
     * @return 删除记录数
     * @ 系统异常
     */
     int delete(int id) ;

    /**
     * 查询集合
     *
     * @param page 查询开始页数； pageSize 数量
     * @return 实体对象列表
     * @ 系统异常
     */
    List<${className}> queryList(int page,int pageSize) ;

    /**
     * 查询对象
     *
     * @param id 
     * @return 实体对象
     * @ 系统异常
     */
    ${className} queryById(int id) ;

}