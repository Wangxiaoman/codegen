package com.cattsoft.fast.codegen.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OperateDB {

  private Connection myConn;
  private Statement stmt;
  private ResultSet rs;

  public Connection getMyConn() {
    return myConn;
  }

  public void setMyConn(Connection myConn) {
    this.myConn = myConn;
  }

  public OperateDB() {
    this.myConn = new ConnectionInit().getConnection();
    try {
      this.stmt = myConn.createStatement();
    } catch (Exception e) {
      System.out.println("can't createStatement");
      e.printStackTrace();
    }
  }

  public void getConnection() throws SQLException {
    try {
      this.stmt = myConn.createStatement();
    } catch (Exception e) {
      System.out.println("can't createStatement");
      e.printStackTrace();
    }
  }

  public void releaseConnection() {
    try {
      if (rs != null) {
        rs.close();
      }
      System.out.println("rs is closed");
    } catch (Exception e) {
      System.out.println("can't releaseResultSet");
      e.printStackTrace();
    }
    try {
      if (stmt != null) {
        stmt.close();
      }
      System.out.println("stmt is closed");
    } catch (Exception e) {
      System.out.println("can't releaseStatement");
      e.printStackTrace();
    }
    if (myConn != null) ConnectionInit.releaseConnection(myConn);
    System.out.println("myConn is closed");
  }

  public ResultSet executeQuery(String sql) throws SQLException {
    getConnection();
    rs = stmt.executeQuery(sql);
    return rs;
  }

  public int executeUpdate(String sql) throws SQLException {
    getConnection();
    int i = stmt.executeUpdate(sql);
    return i;
  }

  public static void main(String[] args) throws SQLException {
    OperateDB mydbt = new OperateDB();
    // mydbt.executeUpdate("insert into table_info values('1','1','1','1','1')");
    // ResultSet rs = mydbt.executeQuery("select * from table_info");
    // while(rs.next()){
    // //ResultSet rs1=mydbt.executeQuery(sql)
    // System.out.println(rs.getString(1));
    // System.out.println(rs.getString(2));
    // System.out.println(rs.getString(3));
    // }
    mydbt.releaseConnection();
  }
}
