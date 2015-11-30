<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@include file="../common/common_tag.jsp"%><!DOCTYPE html><html>    
<head>
<%@include file="../common/common_meta.jsp"%>
<title>标题</title>
<%@include file="../common/common_css.jsp"%>
<%@include file="../common/common_js.jsp"%>
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
					<h2>商家列表</h2>
					<div class="title">
						<div class="left-banner">
						</div>
						<div class="right-banner">
						</div>
						<div style="clear:both;height:0px;"></div> 
					</div>
				</div>
				<div class="main_bd" style="padding-top: 20px;">
					<form class="form-horizontal" id="searchForm" role="form"
						action="/web/${className?uncap_first}/list">
						<div class="form-group">
							<label class="col-sm-2 control-label">查询条件</label>
							<div class="col-sm-4">
								<input name="normalText" id="normalText" class="form-control" type="text"
									placeholder="输入查询条件" value="" />
							</div>
							<div class="col-sm-2">
								<button id="searchBtn" type="button" class="btn btn-info">查询</button>
							</div>
							<div class="col-sm-1">
								<a class="btn btn-success"
									href="/web/${className?uncap_first}/toAdd">新增</a>
							</div>
						</div>
					</form>
					<table class="table table-hover table-bordered">
						<thead>
							<tr class="tb_header">
								<#list fieldName as field>
		    	   					 <th> 字段名称 </th>
		    	   				</#list>
								<th>操作</th>
							
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${r'${pagination.rows}'}" var="obj">
								<tr>
									<#list fieldName as field>
										<td style="vertical-align: middle; text-align: center;">${r'${obj.' }${field.NAME} }</td>
									</#list>
									<td>
										<a class="op" href="/web/${className?uncap_first}/toEdit?id=${r'${obj.id}' }" style="color: #2ac1f2">编辑</a>
										<a class="op _delete" href="/web/${className?uncap_first}/remove?id=${r'${obj.id}' }" style="color: #2ac1f2">删除</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="main_footer">
					<cb:pagination url="/web/${className?uncap_first}/query" />
				</div>
			</div>
		</div>
	</div>
	
	<%@include file="../common/common_footer.jsp"%>
	<script type="text/javascript">
	
		$("#searchBtn").click(function() {
			$("#searchForm").submit();
		});
		
	</script>
</body>
</html>