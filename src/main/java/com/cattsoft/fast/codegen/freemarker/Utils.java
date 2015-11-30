package com.cattsoft.fast.codegen.freemarker;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cattsoft.fast.codegen.db.OperateDB;

public class Utils {
	
	public static String strTableName=null;
	public static String strDatabase=null;
	
	//得到 Fast_dev 用户下所有 表名字
	public static List<String> getAllTableName() throws SQLException{
		List<String> list=new ArrayList<String>();
		
		if(strTableName!=null&&strTableName.length()>0){
			String str[]=strTableName.split(",");
			list = Arrays.asList(str);
		}else{
			StringBuilder strSql=new StringBuilder("show tables from ").append(strDatabase);
			OperateDB operateDb=new OperateDB("FAST");
			ResultSet rs=operateDb.executeQuery(strSql.toString());
			while(rs.next()){
				list.add(rs.getString("Tables_in_"+strDatabase));
			}
			operateDb.releaseConnection();		
		}
		return list;	
	}
	
	//得到 Fast_dev 用户下每张 表 所对应的 列名去掉“_”字母转换变成JAVA实体类属性
	public static List<String> getColumn(String tableName) throws SQLException{
		OperateDB operateDb=new OperateDB("FAST");
		List<String> list=new ArrayList<String>();
		StringBuilder strSql=new StringBuilder("show full fields from "+tableName);	
		ResultSet rs=operateDb.executeQuery(strSql.toString());
		while(rs.next()){
			list.add(toFieldName(rs.getString("Field")));
		}
		operateDb.releaseConnection();		
		return list;	
	}
	//获取数据库用户下每张表对应的字段（不做改变）
	public static List<String> getDBColumn(String tableName) throws SQLException{
		OperateDB operateDb=new OperateDB("FAST");
		List<String> list=new ArrayList<String>();
		StringBuilder strSql=new StringBuilder("show full fields from "+tableName);	
		ResultSet rs=operateDb.executeQuery(strSql.toString());
		while(rs.next()){
			list.add(rs.getString("Field"));
		}
		operateDb.releaseConnection();		
		return list;	
	}
	
	//得到 Fast_dev 用户下每张 表 所对应的 列名(未转变)、数据类型及列的注释
	public static List<HashMap<String,Object>> getColumnDataType(String tableName) throws SQLException{
		OperateDB operateDb=new OperateDB("FAST");
		List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
		StringBuilder strSql=new StringBuilder("show full fields from "+tableName);	
		ResultSet rs=operateDb.executeQuery(strSql.toString());
		while(rs.next()){
			HashMap<String,Object> hsp=new HashMap<String,Object>();
			hsp.put("NAME", rs.getString("Field"));
			String dataType = rs.getString("Type").toLowerCase();
			if(dataType.contains("char") || dataType.contains("text")){
				hsp.put("TYPE", String.class.getSimpleName());
			}else if(dataType.contains("int")){
				hsp.put("TYPE", int.class.getSimpleName());
			}else if(dataType.contains("time")){
				hsp.put("TYPE", Date.class.getSimpleName());
			}else if(dataType.contains("float")){
				hsp.put("TYPE", float.class.getSimpleName());
			}else if(dataType.contains("double")){
				hsp.put("TYPE", double.class.getSimpleName());
			}else if(dataType.contains("decimal")){
				hsp.put("TYPE", BigDecimal.class.getSimpleName());
			}
			
			hsp.put("COMMENT", rs.getString("Comment"));
			list.add(hsp);
		}
		operateDb.releaseConnection();		
		return list;		
	}
	
	//得到 Fast_dev 用户下每张 表 所对应的 列名(未转变)、数据类型及列的注释
	public static List<HashMap<String,Object>> getColumnNameAndType(String tableName) throws SQLException{
		OperateDB operateDb=new OperateDB("FAST");
		List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
		StringBuilder strSql=new StringBuilder("show full fields from "+tableName);	
		ResultSet rs=operateDb.executeQuery(strSql.toString());
		while(rs.next()){
			HashMap<String,Object> hsp=new HashMap<String,Object>();
			hsp.put("NAME", toFieldName(rs.getString("Field")));
			String dataType = rs.getString("Type").toLowerCase();
			if(dataType.contains("char") || dataType.contains("text")){
				hsp.put("TYPE", String.class.getSimpleName());
			}else if(dataType.contains("int")){
				hsp.put("TYPE", int.class.getSimpleName());
			}else if(dataType.contains("time")){
				hsp.put("TYPE", Date.class.getSimpleName());
			}else if(dataType.contains("float")){
				hsp.put("TYPE", float.class.getSimpleName());
			}else if(dataType.contains("double")){
				hsp.put("TYPE", double.class.getSimpleName());
			}else if(dataType.contains("decimal")){
				hsp.put("TYPE", BigDecimal.class.getSimpleName());
			}else{
				hsp.put("TYPE", String.class.getSimpleName());
			}
			
			hsp.put("COMMENT", rs.getString("Comment"));
			list.add(hsp);
		}
		operateDb.releaseConnection();		
		return list;		
	}
	
	//得到表对应 的主键名称
	//全部使用innodb，都用自增主键
	public static String getPrimaryKey(String tableName)throws SQLException{
		return "id";	
	}
	
	
	//首字母转小写
	 public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
 
    //首字母转大写
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    
	//变成Java 类样式的 类名     既每个单词的首字大写
    public static String toClassName(String name){
    	String strName[]=name.split("_");
    	String className="";
    	if(strName.length==1){
    		className=toUpperCaseFirstOne(name.toLowerCase());
    		return className;
    	}
    	for(String str:strName){
    		className=className+toUpperCaseFirstOne(str.toLowerCase());
    		}
    	return className;
    }
    /**
	 * 获取数据库字段对应的对象字段map jianglinzeng
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static Map<String,String> getColumnMap(String tableName) throws SQLException{
		OperateDB operateDb=new OperateDB("FAST");
		Map<String,String> map =new HashMap<String, String>();
		StringBuilder strSql=new StringBuilder("show full fields from "+tableName);	
		ResultSet rs=operateDb.executeQuery(strSql.toString());
		while(rs.next()){
			map.put(toFieldName(rs.getString("Field")),rs.getString("Field"));
		}
		operateDb.releaseConnection();		
		return map;	
	}
	
	/**
	 * 不要第1条记录 获取数据库字段对应的对象字段map wanghaolin
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static Map<String,String> getColumnMapElseByOne(String tableName) throws SQLException{
		OperateDB operateDb=new OperateDB("FAST");
		Map<String,String> map =new HashMap<String, String>();
		StringBuilder strSql = new StringBuilder();
		strSql.append("select distinct column_name  from dba_cons_columns where table_name='"+tableName+"'" );
		strSql.append(" and column_name not in( select distinct b.column_name ");
		strSql.append(" from dba_constraints a, dba_cons_columns b where a.table_name = '"+tableName+"'");
		strSql.append(" AND a.CONSTRAINT_TYPE = 'P' and a.constraint_name = b.constraint_name)");
		ResultSet rs=operateDb.executeQuery(strSql.toString());
		while(rs.next()){
			map.put(toFieldName(rs.getString("column_name")),rs.getString("column_name"));
		}
		operateDb.releaseConnection();		
		return map;	
	}

    
  //变成Java类样式的 字段名    即第一个单词首字母小写，其余单词首字母大写
    public static String toFieldName(String name){
    	String strName[]=name.split("_");
    	String fieldName="";
    	if(strName.length==1){
    		return name.toLowerCase();
    	}
    	for(int i=0;i<strName.length;i++){
    		if(i==0){
    			fieldName=strName[0].toLowerCase();
    		}
    		if(i>0){
    			fieldName=fieldName+toUpperCaseFirstOne(strName[i].toLowerCase());
    		}
    	}
    	return fieldName;
    }
	
	public static void main(String arg[]) throws SQLException{
		System.out.println(toFieldName(getPrimaryKey("POSITION")));
		System.out.println(Date.class.getSimpleName());
	}
	
}
