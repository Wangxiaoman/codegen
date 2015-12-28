package com.cattsoft.fast.codegen.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

import com.cattsoft.fast.codegen.freemarker.SysCodeGen;
import com.cattsoft.fast.codegen.freemarker.Utils;

public class ConnectionInit {

	Statement sm = null;
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
		genFolder();
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

	public static void releaseConnection(Connection _myconn) {
		try {
			_myconn.close();
		} catch (SQLException e) {
		}
	}

	private static void trace(String content) {
		System.out.print("-->");
		System.out.println(content);
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

	private static void getConfigInfo() {
		InputStream is = null;
		try {
			String configPath = "jdbc-config.properties";

			ClassLoader classLoader = ConnectionInit.class.getClassLoader();
			File file = new File(classLoader.getResource(configPath).getFile());

			is = new FileInputStream(file);

			Properties pro = new Properties();
			pro.load(is);
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
			SysCodeGen.insertWithoutColumnList = Arrays.asList(pro.getProperty(
					"insertWithoutColumn").split(","));
			SysCodeGen.updateWithoutColumnList = Arrays.asList(pro.getProperty(
					"updateWithoutColumn").split(","));
			
			SysCodeGen.domainWithoutNot = pro.getProperty("domainWithoutNot");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void genFolder() {
		// ,"delegate","delegate//impl","delegate//impl//ejb","delegate//impl//bean","dao//impl","domain","domain//impl","applicationXml"
		String folders[] = new String[] { "vo", "dao", "service",
				"service//impl", "controller", "controller//web", "jsp" };
		String strBaseDir = SysCodeGen.fileDir;
		File baseDir = new File(strBaseDir);
		baseDir.mkdir();
		for (String str : folders) {
			String folder = strBaseDir + str;
			File file = new File(folder);
			if (!file.exists()) {
				file.mkdir();
			}
		}
	}

}
