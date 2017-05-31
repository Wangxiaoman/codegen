package com.cattsoft.fast.codegen.freemarker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.StringUtils;

public class SystemServiceCodeGen extends SysCodeGen {
	// 实体
	private static final String ENTITY_TEMPLATE = "Entity.ftl";
	// dao
	private static final String DAO_TEMPLATE = "dao.ftl";
	private static final String DAO_ORACLE_TEMPLATE = "dao-oracle.ftl";
	// service
	private static final String SERVICE_TEMPLATE = "Service.ftl";
	private static final String SERVICEIMPL_TEMPLATE = "ServiceImpl.ftl";
	
	// 创建Entity
	public void createEntity(String template) throws SQLException {
		FreeMarkerUtils.getTemplate(SYSTEM_TEMPLATE_DIR, template);
		List<String> tableNameList = Utils.getAllTableName(mysqlOrOracle);
		for (int i = 0; i < tableNameList.size(); i++) {
			String tableName = tableNameList.get(i);
			String className = Utils.toClassName(tableName);
			String strPath = fileDir + "vo//" + className + ".java";
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("packagePath", packagePath);
			hashMap.put("className", className);
			List<HashMap<String, Object>> list = Utils.getColumnDataType(tableName,mysqlOrOracle);
			for(Map<String,Object> map : list){
				if(!StringUtils.isNullOrEmpty(String.valueOf(map.get("COMMENT")))){
					map.put("COMMENT","//"+map.get("COMMENT"));
				}
			}
			hashMap.put("fieldName", list);
			hashMap.put("domainWithoutNot", domainWithoutNot);
			hashMap.put("packagePath", packagePath);
			hashMap.put("filedMap", Utils.getColumnMap(tableName,mysqlOrOracle));
			FreeMarkerUtils.createFile(hashMap, strPath);
			
		}
	}

	/*
	 * DAO实现类
	 */
	public void createDAOImpl(String template, String flag, String dir)
			throws SQLException {
		FreeMarkerUtils.getTemplate(SYSTEM_TEMPLATE_DIR, template);
		List<String> tableNameList = Utils.getAllTableName(mysqlOrOracle);
		for (int i = 0; i < tableNameList.size(); i++) {
			String tableName = tableNameList.get(i);
			//List  column =Utils.getDBColumn(tableName);
			String className = Utils.toClassName(tableName);
			StringBuilder insertSqlA = getInsrtSqlA(tableName);
			StringBuilder insertSqlB = getInsrtSqlB(tableName);
			StringBuilder updateSql = getUpdateSql(tableName).append(" where id=#{id}");

			StringBuilder queryBeanSql = null;
			if(mysqlOrOracle == Utils.MYSQL){
			    queryBeanSql = getQueryBeanSqlA(tableName).append(" from ").append(tableName).append(" order by id desc limit #{offset},#{limit}");
			}else{
			    queryBeanSql = getOracleQueryBeanSql(tableName);
			}
			
			StringBuilder queryBeanSqlById = getQueryBeanSqlA(tableName).append(" from ").append(tableName).append(" where id=#{id}");
			StringBuilder deleteSql = new StringBuilder("delete from ").append(tableName).append(" where id=#{id}");
			String primaryKeyName = Utils.toFieldName(Utils
					.getPrimaryKey(tableName));// 主键（java里形式显示）
			String strPath = fileDir + dir + "//" + className + flag + ".java";
			// 取主键（数据库字段显示出来）
			String primaryKey = Utils.getPrimaryKey(tableName);
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("tableName", tableName);
			hashMap.put("DBPK", primaryKey);
			hashMap.put("className", className);
			hashMap.put("primaryKey", primaryKeyName);
			hashMap.put("insertsql1", insertSqlA);
			hashMap.put("insertsql2", insertSqlB);
			hashMap.put("updatesql", updateSql);
			hashMap.put("querysql", queryBeanSql);
			hashMap.put("queryBeanSqlById", queryBeanSqlById);
			hashMap.put("deleteSql", deleteSql);
			hashMap.put("countSql", getCountSql(tableName));
			
			
			List<String> columnNameList = Utils.getColumn(tableName,mysqlOrOracle);
			List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			// 列取值，不取第1个主键。主键有另外的方法
			for (int a = 1; a < columnNameList.size(); a++) {
				HashMap<String, Object> hash = new HashMap<String, Object>();

				hash.put("NAME", columnNameList.get(a) + "");
				list.add(hash);

			}
			
			hashMap.put("fieldName", list);
			hashMap.put("filedMap", Utils.getColumnMap(tableName,mysqlOrOracle));
			hashMap.put("packagePath", packagePath);
			FreeMarkerUtils.createFile(hashMap, strPath);
		}
	}

	// 获取insertsql语句append第1句
	public static StringBuilder getInsrtSqlA(String tableName)
			throws SQLException {

		List<String> dbColumns = Utils.getDBColumn(tableName,mysqlOrOracle);
		StringBuilder strInsert = new StringBuilder("insert into " + tableName + " (");

		int beginIndex = (mysqlOrOracle==Utils.MYSQL?1:0);
		for (int i=beginIndex;i<dbColumns.size();i++) {
			String dbColumn = dbColumns.get(i);
			if(!insertWithoutColumnList.contains(Utils.toFieldName(dbColumn))){
				strInsert.append(dbColumn + ",");
			}
		}
		strInsert.deleteCharAt(strInsert.lastIndexOf(","));
		
		strInsert.append(")");
		System.out.println(strInsert);

		return strInsert;

	}

	// 获取insertsql语句append第2句
	public static StringBuilder getInsrtSqlB(String tableName)
			throws SQLException {

		List<String> columns = Utils.getColumn(tableName,mysqlOrOracle);
		StringBuilder strInsertValue = new StringBuilder("values (");
		if(mysqlOrOracle==Utils.ORACLE){
		    strInsertValue.append("SEQ_").append(tableName).append("_ID.nextval").append(",");
		}
		for (int i=1;i<columns.size();i++) {
			String column = columns.get(i);
			if(!insertWithoutColumnList.contains(Utils.toFieldName(column))){
				strInsertValue.append("#{").append(column + "},");
			}
		}
		strInsertValue.deleteCharAt(strInsertValue.lastIndexOf(","));
		strInsertValue.append(")");
		System.out.println(strInsertValue);

		return strInsertValue;
	}

	// 获取updatesql语句append第2句
	public static StringBuilder getUpdateSql(String tableName)
			throws SQLException {

		List<HashMap<String, Object>> list = Utils.getColumnDataType(tableName,mysqlOrOracle);
		StringBuilder strInsertValue = new StringBuilder(" update ").append(tableName).append(" set ");
		for (int i = 1; i < list.size(); i++) {
			HashMap<String, Object> hsp = list.get(i);
			String cloumn = (String) hsp.get("NAME");
			if(!updateWithoutColumnList.contains(Utils.toFieldName(cloumn))){
				strInsertValue.append(cloumn).append("=#{").append(Utils.toFieldName(cloumn)).append("},");
			}
		}
		strInsertValue.deleteCharAt(strInsertValue.lastIndexOf(","));
		StringBuilder str = new StringBuilder();
		str.append(strInsertValue);
		return str;
	}

	// 获取qureyBeansql语句append第1句
	public static StringBuilder getQueryBeanSqlA(String tableName)
			throws SQLException {
		List<HashMap<String, Object>> list = Utils.getColumnDataType(tableName,mysqlOrOracle);
		StringBuilder strQuery = new StringBuilder("select ");
		for (int i = 0; i < list.size() ; i++) {
			HashMap<String, Object> hsp = list.get(i);
			String cloumn = (String) hsp.get("NAME");
			strQuery.append(cloumn + ",");
		}
		strQuery.deleteCharAt(strQuery.lastIndexOf(","));
		return strQuery;
	}
	
	// 获取qureyBeansql语句append第1句
    public static StringBuilder getOracleQueryBeanSql(String tableName)
            throws SQLException {
        List<HashMap<String, Object>> list = Utils.getColumnDataType(tableName,Utils.ORACLE);
        StringBuilder strQuery = new StringBuilder("SELECT * from ( SELECT ROWNUM AS rowno, ");
        for (int i = 0; i < list.size() ; i++) {
            HashMap<String, Object> hsp = list.get(i);
            String cloumn = (String) hsp.get("NAME");
            strQuery.append(cloumn + ",");
        }
        strQuery.deleteCharAt(strQuery.lastIndexOf(","));
        strQuery.append(" from ").append(tableName).append(") table_alias ");
        strQuery.append(" WHERE table_alias.rowno <= ? AND table_alias.rowno >= ?");
        
        return strQuery;
    }
	
	public static StringBuilder getCountSql(String tableName){
		StringBuilder strQuery = new StringBuilder("select count(1) from ").append(tableName);
		return strQuery;
	}

	public void createDao(String template) throws SQLException {
		createDAOImpl(template, "Mapper", "mapper");
	}
	
	public void createIService(String template) throws SQLException {
		create(template, "Service", "service");
	}

	public void createService(String template) throws SQLException {
		createImpl(template, "ServiceImpl", "service//impl");
	}

	public  void create() throws SQLException {
		createEntity(ENTITY_TEMPLATE);
		if(mysqlOrOracle == Utils.MYSQL){
		    createDao(DAO_TEMPLATE);
		}else{
		    createDao(DAO_ORACLE_TEMPLATE);
		}
		createIService(SERVICE_TEMPLATE);
		createService(SERVICEIMPL_TEMPLATE);
     }

}
