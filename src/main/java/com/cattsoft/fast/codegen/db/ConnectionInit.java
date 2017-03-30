package com.cattsoft.fast.codegen.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

import com.cattsoft.fast.codegen.file.FileUtil;
import com.cattsoft.fast.codegen.freemarker.SysCodeGen;
import com.cattsoft.fast.codegen.freemarker.Utils;

public class ConnectionInit {

  private static String dbdrivername = null;
  private static String dburl = null;
  private static String username = null;
  private static String password = null;
  Connection conn = null;

  public Connection getConn() {
    return conn;
  }

  public void setConn(Connection conn) {
    this.conn = conn;
  }

  public ConnectionInit() {
    getConfigInfo();
    FileUtil.genFolder();
  }

  public Connection getConnection() {
    try {
      trace(username + "/" + password);
      Class.forName(dbdrivername);
      java.util.Properties info = new java.util.Properties();
      info.put("user", username);
      info.put("password", password);
      info.put("defaultRowPrefetch", "15");
      info.put("internal_logon", "normal");
      conn = DriverManager.getConnection(dburl, info);
    } catch (Exception ex) {
      if (conn != null) {
        try {
          conn.close();
        } catch (Exception exx) {
        }
      }
      ex.printStackTrace();
    }
    return conn;
  }

  public static void releaseConnection(Connection myconn) {
    try {
      myconn.close();
    } catch (SQLException e) {
    }
  }

  private static void trace(String content) {
    System.out.print("-->");
    System.out.println(content);
  }

  private static void getConfigInfo() {
    try {
      Properties pro = FileUtil.getProperties();

      dbdrivername = pro.getProperty("database.jdbc.driver");
      dburl = pro.getProperty("database.jdbc.url");
      username = pro.getProperty("database.jdbc.username");
      password = pro.getProperty("database.jdbc.password");
      // 模板存放地址
      SysCodeGen.SYSTEM_TEMPLATE_DIR = pro.getProperty("templateDir");

      // 文件生成地址
      SysCodeGen.fileDir = pro.getProperty("createFileDir");
      // 载入表名字
      Utils.strTableName = pro.getProperty("tableName");
      Utils.strDatabase = pro.getProperty("strDatabase");

      // 特殊字段
      SysCodeGen.insertWithoutColumnList = Arrays.asList(pro.getProperty("insertWithoutColumn").split(","));
      SysCodeGen.updateWithoutColumnList = Arrays.asList(pro.getProperty("updateWithoutColumn").split(","));

      SysCodeGen.domainWithoutNot = pro.getProperty("domainWithoutNot");
      
      SysCodeGen.mysqlOrOracle = Integer.valueOf(pro.getProperty("mysql.or.oracle"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {
    String configPath = "jdbc-config.properties";

    ClassLoader classLoader = ConnectionInit.class.getClassLoader();
    File file = new File(classLoader.getResource(configPath).getFile());

    Properties pro = new Properties();
    pro.load(new FileInputStream(file));
    System.out.println(pro.keySet());

    new ConnectionInit().getConnection();
  }
}
