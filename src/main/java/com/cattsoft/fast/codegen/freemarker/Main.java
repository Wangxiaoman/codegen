package com.cattsoft.fast.codegen.freemarker;

import java.sql.SQLException;



public class Main {

	public static void main(String[] args) throws SQLException {
		SystemWebCodeGen sysWeb=new SystemWebCodeGen();
		//创建System-web中的jsp、delegate、controller
		sysWeb.createWeb();
		//创建配置文件
		//sysWeb.createApplicationXml();
		SystemServiceCodeGen sys=new SystemServiceCodeGen();
		//创建ENTITY,ISDAO,IMDAO,ISERVICE,IDOMAIN,SDAOIMPL,MDAOIMPL,SERVICEIMPL,DOMAINIMPL
		sys.create();
		
	}

}
