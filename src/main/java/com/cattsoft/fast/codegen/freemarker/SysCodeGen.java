package com.cattsoft.fast.codegen.freemarker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cattsoft.fast.codegen.db.ConnectionInit;

public class SysCodeGen {

  // system-web文件绝对地址加载目录
  public static String SYSTEM_TEMPLATE_DIR = null;
  // 文件生成目录
  public static String fileDir = null;
  // insert语句中不需要的字段
  public static List<String> insertWithoutColumnList = null;
  // update语句中不需要的字段
  public static List<String> updateWithoutColumnList = null;
  //
  public static String domainWithoutNot = "";
  
  // 默认为mysql
  public static int mysqlOrOracle;

  public SysCodeGen() {
    new ConnectionInit();
  }

  /*
   * 接口
   */
  public void create(String template, String flag, String dir) throws SQLException {
    FreeMarkerUtils.getTemplate(SYSTEM_TEMPLATE_DIR, template);
    List<String> tableNameList = Utils.getAllTableName(mysqlOrOracle);
    for (int i = 0; i < tableNameList.size(); i++) {
      String tableName = tableNameList.get(i);
      String className = Utils.toClassName(tableName);
      String strPath = fileDir + dir + "//" + className + flag + ".java";
      HashMap<String, Object> hashMap = new HashMap<String, Object>();
      hashMap.put("className", className);
      FreeMarkerUtils.createFile(hashMap, strPath);
    }
  }

  /*
   * 实现类
   */
  public void createImpl(String template, String flag, String dir) throws SQLException {
    FreeMarkerUtils.getTemplate(SYSTEM_TEMPLATE_DIR, template);
    List<String> tableNameList = Utils.getAllTableName(mysqlOrOracle);
    for (int i = 0; i < tableNameList.size(); i++) {
      String tableName = tableNameList.get(i);
      String className = Utils.toClassName(tableName);
      String primaryKeyName = Utils.toFieldName(Utils.getPrimaryKey(tableName));// 主键
      String strPath = fileDir + dir + "//" + className + flag + ".java";
      HashMap<String, Object> hashMap = new HashMap<String, Object>();
      hashMap.put("className", className);
      hashMap.put("primaryKey", primaryKeyName);
      List<String> columnNameList = Utils.getColumn(tableName,mysqlOrOracle);
      List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
      for (int a = 0; a < columnNameList.size(); a++) {
        HashMap<String, Object> hash = new HashMap<String, Object>();
        hash.put("NAME", columnNameList.get(a));
        list.add(hash);
      }
      hashMap.put("fieldName", list);
      FreeMarkerUtils.createFile(hashMap, strPath);
    }
  }

}
