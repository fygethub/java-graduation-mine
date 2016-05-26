<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>历史查询</title>
<jsp:include page="../../inc.jsp"></jsp:include>

<script>
var dataGrid;
$(function(){
	parent.$.messager.progress('close');
	dataGrid=$('#dataGrid').datagrid({
		url:'${pageContext.request.contextPath}/histroyController/datagrid',
		fit:true,
		fitColumns:true,
		border:false,
		idField:'executionId',
		pagination:true,
		pageSize:10,
		pageList:[10,20,30,40],
		checkOnSelect:false,
		selectOnCheck:false,
		nowrap:false,
		striped:true,
		rownumbers:true,
		singleSelect:true,
		columns:[[{field:'executionId',title:'执行ID',width:100},
					{field:'activityType',title:'流程类型',width:100},
					{field:'assignee',title:'办理人',width:100},
					{field:'activityName',title:'流程名字',width:100},
					{field:'durationInMillis',title:'历经时间',width:100,formatter:function(value,row,index){
						return value;
					},sorter:function(a,b){
						console.log(a);
					}},
					{field:'startTime',title:'开始时间',width:100,
						formatter:function(value, row, index){
							var date=new Date(value);
							
							return date.toLocaleDateString() +" "+date.toTimeString().split(" ")[0];
						}},
						{field:'endTime',title:'结束时间',width:100,
							formatter:function(value, row, index){
								var date=new Date(value);
								
								return date.toLocaleDateString() +" "+date.toTimeString().split(" ")[0];
							}}
					]],
		toolbar:'#toolbar',
		onLoadSuccess:function(){
			$('#searchForm table').show();
			parent.$.messager.progress('close');
			$(this).datagrid('tooltip');
		}
	})
	
})

function hisearch(){
	dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north',border:false,height:50" >
			<div style="position:absolute; right:0px;height:30px;">
				<form id="searchForm">
					<table>	
						<tbody>
							<tr><td><label>查询</label></td><td valign="middle">	<input   style="margin-top:9px;" type="text" name="search"/></td><td>	<a class="easyui-linkbutton" onclick="hisearch()">搜索</a></td></tr>
						</tbody>
					</table>
				</form>	
			</div>	
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>				
	</div> 
	
</body>
</html>