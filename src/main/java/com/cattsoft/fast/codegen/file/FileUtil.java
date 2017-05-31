package com.cattsoft.fast.codegen.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.cattsoft.fast.codegen.db.ConnectionInit;

public class FileUtil {
  // 配置文件加载
  private static Properties pro;
  
  static{
    InputStream is = null;
    String configPath = "jdbc-config.properties";
    ClassLoader classLoader = ConnectionInit.class.getClassLoader();
    File file = new File(classLoader.getResource(configPath).getFile());

    try {
      is = new FileInputStream(file);
      pro = new Properties();
      pro.load(is);
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
  
  public static Properties getProperties(){
    return pro;
  }
  
  public static void genFolder() {
    // ,"delegate","delegate//impl","delegate//impl//ejb","delegate//impl//bean","dao//impl","domain","domain//impl","applicationXml"
    String folders[] = new String[] { "vo", "mapper", "service", "service//impl", "controller", "controller//web", "jsp" };
    String strBaseDir = pro.getProperty("createFileDir");
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
