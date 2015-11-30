<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/common_tag.jsp"%><!DOCTYPE html>
<html>
<head>
<%@include file="../common/common_meta.jsp"%>
<title>标题</title>
<%@include file="../common/common_css.jsp"%>
<%@include file="../common/common_js.jsp"%>
<link type="text/css" rel="stylesheet" href="/assets/css/datepicker.css">
<script type="text/javascript"
	src="/assets/js/bootstrap-datepicker.js"></script>
<script type="text/javascript"
	src="/assets/js/locales/bootstrap-datepicker.zh-CN.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="/assets/js/plugins/jquery.validate.js"></script>
<script type="text/javascript"
	src="/assets/js/plugins/jquery.validate-ex.js"></script>
</head>
<body>
	<%@include file="../common/common_header.jsp"%>
	<div class="body">
		<div class="body_box">
			<div class="col_left">
				<%@include file="../common/common_left_menu.jsp"%>
			</div>
			<div class="col_main">
				<div class="main_hd">
					<h2>新建</h2>
					<div class="title">
						<div class="left-banner"></div>
						<div class="right-banner"></div>
						<div style="clear: both; height: 0px;"></div>
					</div>
				</div>
				<div class="main_bd">
					<form id="form" role="form" class="form-horizontal" method="post" action="/web/${className?uncap_first}" enctype="multipart/form-data">
						<#list fieldName as field>
							<div class="form-group">
								<label for="normalText" class="col-sm-2 control-label">名称</label>
								<div class="col-sm-8">
									<input name="${field.NAME}" id="${field.NAME}" class="form-control" type="text"
										placeholder="请输入" />
								</div>
							</div>
						</#list>
						
						<div style="text-align: center; padding: 20px;">
							<input type="button" id="save" value="提交" class="btn btn-default" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../common/common_footer.jsp"%>
	<script type="text/javascript">
		$("#date").datepicker({
			format : "yyyy-mm-dd",
			language : "zh-CN",
			multidate : false
		});
		
		//表单提交
	     $("#save").click(function(){
			$("#form").submit();
		 });
	</script>
</body>
</html>