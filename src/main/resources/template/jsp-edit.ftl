<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/top.jsp"%>
<div class="easyui-layout" data-options="fit:true">
			<form method="post"  action="${ctx}/system/organization/${className?lower_case}" id="editForm">
			  <input name="_method" value="put" type="hidden"/>
				<div class="info-form" data-options="region:'center'">
		 	      <fieldset>
		 		    <legend>xxXX信息</legend>
		 		    <ul class="info-group-one" >
				       <#list fieldName as field>
					   <li class="required">
						 <label>${field.COMMENT}</label>
						 <input type="text" class="easyui-validatebox"  name="${field.NAME}" value="${model}${field.NAME}${flag}"   maxlength="20" required="true" placeholder=""></input>
					   </li>
				       </#list>	
					</ul>
		 	 </fieldset>
		 </div>
		 <div class="buttons" data-options="region:'south'">
		 	<input type="button" class="button" id="editSubmitBtn" value="确 定"/>
		 	<input type="button" class="button" id="editRestBtn" value="取 消"/>
         </div>
	</form>
</div>
<script type="text/javascript">
//<![CDATA[
$(".info-form input[type='text']").css({"width": "180px"});
//验证
	 var key="${model}${primaryKey}${flag}";
	$('#editForm').form({
	    url:"${ctx}/system/public/${className?lower_case}/"+key, 
		onSubmit:function(){
		return $(this).form('validate');
	},
	success:function(data){
		$dtt.refresh();
	 	top.closeDialog();		
	 	$.messager.show({title : '温馨提示:',msg : '修改成功!',timeout : 2000,showType : 'slide'});
	}
});
//表单提交
$("#editSubmitBtn").click(function(){
	$("#editForm").submit();
});
//重置页面
$("#editRestBtn").click(function(){
	top.closeDialog();	
}); 
//]]>
</script>
<%@ include file="/WEB-INF/content/bottom.jsp"%>