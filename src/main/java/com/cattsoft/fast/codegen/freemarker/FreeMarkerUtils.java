package com.cattsoft.fast.codegen.freemarker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerUtils {

	private static Configuration cf=null ;	
	private static Template template=null;
	private FreeMarkerUtils(){}
	
	public static Configuration getConfiguration(){
		if(cf==null){
			cf=new Configuration();
		}
		return cf;
	}
	/**
	 * 绝对地址加载
	 * */
	public static Template getTemplate(String dir,String fileName){
		Configuration cf=getConfiguration();		
		try {
			cf.setObjectWrapper(new DefaultObjectWrapper()); 
			cf.setTemplateLoader(new FileTemplateLoader(new File(dir)));
			template=cf.getTemplate(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return template;	
	}
	
	/**
	 * 生成文件
	 * */
	
	public static void createFile(Map<String,Object> data,String filePath){
		FileOutputStream fos=null;
		OutputStreamWriter osw=null;
		try {
			  fos=new FileOutputStream(new File(filePath));
			try {
				 osw=new OutputStreamWriter(fos,"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(template!=null){
			try {
				template.process(data, osw);
				System.out.println("SUCCESS");
			} catch (TemplateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					fos.close();
					osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		} 
	}
}
