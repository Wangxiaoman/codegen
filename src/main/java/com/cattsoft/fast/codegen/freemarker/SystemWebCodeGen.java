package com.cattsoft.fast.codegen.freemarker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SystemWebCodeGen extends SysCodeGen{

	

	private static final String CONTROLLER_TEMPLATE="controller.ftl";
	private static final String CONTROLLER_JSON_TEMPLATE="controllerJson.ftl";

	
//	private static final String JSP_EDIT_TEMPLATE="jsp-edit.ftl";
//	private static final String JSP_EDITNEW_TEMPLATE="jsp-editNew.ftl";
//	private static final String JSP_INDEX_TEMPLATE="jsp-index.ftl";
//	private static final String JSP_SHOW_TEMPLATE="jsp-show.ftl";
	private static final String APPLICATION="applicationContext-system-service.ftl";
	
	
	private static final String JSP_LIST_TEMPLATE="jsp-list.ftl";
	private static final String JSP_ADD_TEMPLATE="jsp-add.ftl";

	public void controller(String template,String flag,String dir,boolean isWeb) throws SQLException{
		FreeMarkerUtils.getTemplate(SYSTEM_TEMPLATE_DIR, template);
		List<String> tableNameList=Utils.getAllTableName();
		for(int i=0;i<tableNameList.size();i++){	
			String tableName=tableNameList.get(i);
			String className=Utils.toClassName(tableName);
			String primaryKeyName=Utils.toFieldName(Utils.getPrimaryKey(tableName));
			String strPath=null;
			if(isWeb){
				strPath = fileDir+dir+"//"+className+"Web"+flag+".java";
			}else{
				strPath=fileDir+dir+"//"+className+flag+".java";
			}
			HashMap<String,Object> hashMap=new HashMap<String,Object>();
			hashMap.put("className", className);
			hashMap.put("primaryKey",primaryKeyName);
			
			List<HashMap<String,Object>> listAll = Utils.getColumnNameAndType(tableName);
			List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
			for(HashMap<String,Object> map : listAll){
				String fieldName = String.valueOf(map.get("NAME"));
				if(!insertWithoutColumnList.contains(fieldName)){
					list.add(map);
				}
			}
			
			hashMap.put("fieldName", list);
			FreeMarkerUtils.createFile(hashMap, strPath);
		}
	}
	
	public  void createIDelegate(String template) throws SQLException{
		 create(template,"Delegate","delegate");
	}
	public  void createDelegateEjb(String template) throws SQLException{
		 createImpl(template,"Delegate","delegate//impl//ejb");
	}
	public  void createDelegateBean(String template) throws SQLException{
		 createImpl(template,"Delegate","delegate//impl//bean");
	}
	
	public void createController(String template) throws SQLException{
		controller(template,"Controller","controller//web",true);
	}
	
	public void createJsonController(String template) throws SQLException{
		controller(template,"Controller","controller",false);
	}
	
	public void createJspShow(String template,String dir) throws SQLException{
		FreeMarkerUtils.getTemplate(SYSTEM_TEMPLATE_DIR, template);
		List<String> tableNameList = Utils.getAllTableName();
		for (int i = 0; i < tableNameList.size(); i++) {
			String tableName = tableNameList.get(i);
			String className = Utils.toClassName(tableName);
			String strPath = fileDir +dir+"//" + className.toLowerCase()+"-show" + ".jsp";
			System.out.println(strPath);
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("className", className);
			hashMap.put("ctx", "${ctx}");
			hashMap.put("model", "${model.");
			hashMap.put("flag", "}");
			List<HashMap<String,Object>> list = Utils.getColumnNameAndType(tableName);
			hashMap.put("fieldName", list);
			FreeMarkerUtils.createFile(hashMap, strPath);
		}
	}
	public void createJspEdit(String template,String dir) throws SQLException{
		FreeMarkerUtils.getTemplate(SYSTEM_TEMPLATE_DIR, template);
		List<String> tableNameList = Utils.getAllTableName();
		for (int i = 0; i < tableNameList.size(); i++) {
			String tableName = tableNameList.get(i);
			String className = Utils.toClassName(tableName);
			String strPath = fileDir +dir+"//" + className.toLowerCase()+"-edit" + ".jsp";
			String primaryKeyName=Utils.toFieldName(Utils.getPrimaryKey(tableName));
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("className", className);
			hashMap.put("ctx", "${ctx}");
			hashMap.put("model", "${model.");
			hashMap.put("flag", "}");
			hashMap.put("primaryKey",primaryKeyName);
			List<HashMap<String,Object>> list = Utils.getColumnNameAndType(tableName);
			hashMap.put("fieldName", list);
			FreeMarkerUtils.createFile(hashMap, strPath);
		}
	}
	public void createJspNew(String template,String dir) throws SQLException{
		FreeMarkerUtils.getTemplate(SYSTEM_TEMPLATE_DIR, template);
		List<String> tableNameList = Utils.getAllTableName();
		for (int i = 0; i < tableNameList.size(); i++) {
			String tableName = tableNameList.get(i);
			String className = Utils.toClassName(tableName);
			String primaryKeyName=Utils.toFieldName(Utils.getPrimaryKey(tableName));
			String strPath = fileDir +dir+"//" + Utils.toFieldName(tableName)+"-add" + ".jsp";
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("className", className);
			hashMap.put("ctx", "${ctx}");
			hashMap.put("primaryKey",primaryKeyName);
			List<HashMap<String,Object>> listAll = Utils.getColumnNameAndType(tableName);
			
			List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
			for(HashMap<String,Object> map : listAll){
				String fieldName = String.valueOf(map.get("NAME"));
				if(!insertWithoutColumnList.contains(fieldName)){
					list.add(map);
				}
			}
			
			hashMap.put("fieldName", list);
			FreeMarkerUtils.createFile(hashMap, strPath);
		}
	}
	public void createJspIndex(String template,String dir) throws SQLException{
		FreeMarkerUtils.getTemplate(SYSTEM_TEMPLATE_DIR, template);
		List<String> tableNameList = Utils.getAllTableName();
		for (int i = 0; i < tableNameList.size(); i++) {
			String tableName = tableNameList.get(i);
			String className = Utils.toClassName(tableName);
			String primaryKeyName=Utils.toFieldName(Utils.getPrimaryKey(tableName));
			String strPath = fileDir +dir+"//" + Utils.toFieldName(tableName)+"-query" + ".jsp";
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("className", className);
			hashMap.put("ctx", "${ctx}");
			hashMap.put("primaryKey",primaryKeyName);
			List<HashMap<String,Object>> list = Utils.getColumnNameAndType(tableName);
			hashMap.put("fieldName", list);
			FreeMarkerUtils.createFile(hashMap, strPath);
		}
	}
	
	public void createApplicationXml() throws SQLException{
		FreeMarkerUtils.getTemplate(SYSTEM_TEMPLATE_DIR, APPLICATION);
		List<String> tableNameList =Utils.getAllTableName();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		String strPath = fileDir +"applicationXml//"+"applicationContext-system-service" + ".xml";
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (int a = 0; a < tableNameList.size(); a++) {
			HashMap<String, Object> hash = new HashMap<String, Object>();
			hash.put("NAME", Utils.toClassName(tableNameList.get(a)));
			list.add(hash);
		}
		hashMap.put("serviceName", list);
		FreeMarkerUtils.createFile(hashMap, strPath);
	}
	
	public void createJsp() throws SQLException{
//	createJspEdit(SystemWebCodeGen.JSP_EDIT_TEMPLATE,"jsp");
//  createJspShow(SystemWebCodeGen.JSP_SHOW_TEMPLATE,"jsp");
//	createJspNew(SystemWebCodeGen.JSP_EDIT_TEMPLATE,"jsp");
	  
		createJspNew(SystemWebCodeGen.JSP_ADD_TEMPLATE,"jsp");
		createJspIndex(SystemWebCodeGen.JSP_LIST_TEMPLATE,"jsp");
		

	}
	
	public void createDelegate() throws SQLException{
//		createDelegateEjb(SystemWebCodeGen.DELEGATE_EJB_TEMPLATE);
//		createDelegateBean(SystemWebCodeGen.DELEGATE_BEAN_TEMPLATE);
//		createIDelegate(SystemWebCodeGen.IDELEGATE_TEMPLATE);
	}

	
	public void createWeb()throws SQLException{
//		createJspIndex(SystemWebCodeGen.JSP_INDEX_TEMPLATE,"jsp");
//		createJspEdit(SystemWebCodeGen.JSP_EDIT_TEMPLATE,"jsp");
//		createJspNew(SystemWebCodeGen.JSP_EDITNEW_TEMPLATE,"jsp");
//		createJspShow(SystemWebCodeGen.JSP_SHOW_TEMPLATE,"jsp");
//	  createJspNew(SystemWebCodeGen.JSP_EDIT_TEMPLATE,"jsp");
	  
		createJspIndex(SystemWebCodeGen.JSP_LIST_TEMPLATE,"jsp");
		createJspNew(SystemWebCodeGen.JSP_ADD_TEMPLATE,"jsp");
		
		createController(SystemWebCodeGen.CONTROLLER_TEMPLATE);
		createJsonController(SystemWebCodeGen.CONTROLLER_JSON_TEMPLATE);
//		createDelegateEjb(SystemWebCodeGen.DELEGATE_EJB_TEMPLATE);
//		createDelegateBean(SystemWebCodeGen.DELEGATE_BEAN_TEMPLATE);
//		createIDelegate(SystemWebCodeGen.IDELEGATE_TEMPLATE);
	}

	

}
