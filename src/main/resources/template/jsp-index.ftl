<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/top.jsp"%>
<!-- 查询列表页面-->
<div class="easyui-layout" data-options="fit:true">
     <!-- 上部:查询表单 -->
	 <div data-options="region:'north'" class="search" >
		<fieldset>
			<legend>XXXX管理</legend>
	          <form id="serachForm">
		       <table class="search-table">
			     <tr>
			      <#list fieldName as field>
		    	    <th> ${field.COMMENT} </th>
				  <td><input type="text" name="${field.NAME}"/></td>
		          </#list>
		              <td class="search-buttons">
						 <shiro:hasPermission name="system:organization:organization:query">
							<input type="button" class="button" id="qryBtn" value="查  询" />
							<input type="button" class="button" id="clearBtn" value="清  除" />
						 </shiro:hasPermission>
			          </td>
					</tr>
				</table>
			</form>
		</fieldset>
	</div>
	<!-- 显示列表-->
	<div id="flexgrid" data-options="region:'center'" class="content">
				<table id="flexTable"></table>
	</div>
	<!-- 底部:操作按钮(需自行修改name="内容")-->
	<div data-options="region:'south'" class="buttons">
		<shiro:hasPermission name="system:organization:orgtype:view">
    				<input type="button" class="button" id="showBtn" value="查 看"/>
				</shiro:hasPermission>
                <shiro:hasPermission name="system:organization:orgtype:add">
    				<input type="button" class="button" id="addBtn" value="增 加"/>
				</shiro:hasPermission>
				<shiro:hasPermission name="system:organization:orgtype:edit">
    				<input type="button" class="button" id="editBtn" value="修 改"/>
				</shiro:hasPermission>
				<shiro:hasPermission name="system:organization:orgtype:delete">
    				<input type="button" class="button" id="delBtn" value="注 销"/>
				</shiro:hasPermission>
	</div>
</div>
<script type="text/javascript">
//<![CDATA[
 $(function() {
	$("#flexTable").flexigrid({
	        title:"XXXXXX",
			url:"${ctx}/system/public/${className?lower_case}?search=true",
			hiddenArea:[{name:"${primaryKey?uncap_first}"}],//隐藏标识 在页面不显示
			colModel : [
				<#list fieldName as field>
				{display : '${field.COMMENT}',name : '${field.NAME}',width : 130,sortable : false,align : 'center'}<#if field_has_next>,</#if> 
			</#list>
				]
		});
        //查询
		$("#qryBtn").live('click', function() {
			$("#flexTable").flexRefresh();
		});
		//重置
		$("#clearBtn").live('click', function() {
			$("#serachForm")[0].reset();
		});
		//新增
		$("#addBtn").click(function() {
			var url='${ctx}/system/public/${className?lower_case}/new';
			top.openDialog({href:url,resizable: false,height: 230,width: 380,title: "新增XXXX",modal: true
	        })
		});
		//修改
		$("#editBtn").click(
				function() {
					if (validateSelected()) {//验证只能选中单行，如查看，修改
					var key = getSelectRowByName("${primaryKey?uncap_first}");
			var url='${ctx}/system/public/${className?lower_case}/' + key + '/edit';
			top.openDialog({href : url,resizable : false,height : 230,width : 380,title : "修改XXXX",modal : true
					});
					}
				});
		//查看
		$("#showBtn").click(function() {
			if (validateSelected()) {//验证只能选中单行，如查看，修改
			var key =getSelectRowByName("${primaryKey?uncap_first}");
			var url='${ctx}/system/public/${className?lower_case}/' + key;
			top.openDialog({href : url,resizable : false,height :314,width : 380,title : "查看组织机构类型",modal : true
			});
			}
		});
		//删除
		$("#delBtn").click(
				function() {
					if (validateSelected(true)) {//验证必须选中一行
						$.messager.confirm('删除提示', '您是否确定永久删除选中数据?', function(r){
						if (r) {
						   var keys =getSelectRowByName("${primaryKey?uncap_first}");
					       var url = "${ctx}/system/public/${className?lower_case}/"
							+ keys + "!destroy";
							$.ajax({
								async : false,
								cache : false,
								type : 'POST',
								dataType : "json",
								url : url,//请求的action路径
								success : function(data) {
									if (data != null) {//返回异常信息
										$.messager.alert('提示', data.errorMsg,
												'提示信息');
									}
								}
							});
							$.messager.show({
								title : '温馨提示:',
								msg : '删除成功!',
								timeout : 2000,
								showType : 'slide'
							});
							$("#flexTable").flexReload();
						}
					});
				}
			});
	});
	//刷新flexgird 数据
	function refresh() {
		$("#flexTable").flexReload();
	}
//]]>
</script>
<%@ include file="/WEB-INF/content/bottom.jsp"%>