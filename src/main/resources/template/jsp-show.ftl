<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/top.jsp"%>
		<div class="easyui-layout" data-options="fit:true" >
	<form method="post" action="${ctx}/system/security/${className?lower_case}" id="viewForm">
	  <div class="info-form" data-options="region:'center'" >
			  <fieldset >
		 		 <legend>XXXX信息</legend>
		 		 <ul class="info-group-one">
					<#list fieldName as field>
						<li>
							<label>${field.COMMENT}</label>
							${model}${field.NAME}${flag}
						</li>
					</#list>
					</ul>
			</fieldset>
	    </div>
         <div class="buttons" data-options="region:'south'">
		 	<input type="button" class="button" id="closeSubmitBtn" value="关 闭"/>
         </div>
	</form>
</div>
<script type="text/javascript">
//<![CDATA
$(function(){
	//关闭页面
	 $("#closeSubmitBtn").click(function(){
		  top.closeDialog();
	 }); 
});
//]]>
</script>
<%@ include file="/WEB-INF/content/bottom.jsp"%>