<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/top.jsp"%>
<div class="easyui-layout" data-options="fit:true">
	<form method="post" action="${ctx}/system/organization/orgtype" id="editNewForm">
		<div class="info-form" data-options="region:'center'">
		 	<fieldset>
		 		 <legend>XXXX信息</legend>
		 		 <ul class="info-group-one" >
					 <#list fieldName as field>
					<li class="required">
						<label class="required">${field.COMMENT}</label>
						<input class="easyui-validatebox" type="text"   name="${field.NAME}"   required="true" ></input>
					</li>
					 </#list>	
				   </ul>
		 	 </fieldset>
		 </div>
		 <div class="buttons" data-options="region:'south'">
		 	<input type="button" class="button" id="addSubmitBtn" value="确 定"/>
		 	<input type="button" class="button" id="addRestBtn" value="取 消"/>
         </div>
	</form>
</div>
<script type="text/javascript">
//<![CDATA[
$(".info-form input[type='text']").css({"width": "180px"});
$(function(){
	 //验证
	 $('#editNewForm').form({
				  url: "${ctx}/system/public/${className?lower_case}",
				  onSubmit:function(){
			return $(this).form('validate');
		},
		success:function(data){
			 $dtt.refresh();
		 	  top.closeDialog();
		  $.messager.show({title : '温馨提示:',msg : '添加成功!',timeout : 2000,showType : 'slide'});
		}
	 });
	 //表单提交
     $("#addSubmitBtn").click(function(){
		$("#editNewForm").submit();
	 });
	//重置页面
	 $("#addRestBtn").click(function(){
		 top.closeDialog();
	 }); 
});
//]]>
</script>
<%@ include file="/WEB-INF/content/bottom.jsp"%>