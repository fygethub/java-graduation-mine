<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传文件</title>
<jsp:include page="../../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/processController/processdel')}">
	<script type="text/javascript">
		$.canDel = true;
	</script>
</c:if>

<style type="text/css">
*{margin:0;padding:0;}
body{font-size:12px;font-family:"微软雅黑";color:#ccc;
.box{width:400px;height:200px;border:1px solid red;margin:100px auto;padding:20px 300px;}
h1{font-size:30px;color:#6d4242;text-shadow:5px 5px 10px #111;}
.box .selectBtn{width:200px;height:40px;border-radius:5px;font-size:30px;}
</style>
</head>
<body>
	<div style="display:block;height:340px;">
		<table id="deploytable">
		</table>
	</div>
	<div style="display: block;">
		<table id="definetable"></table>
	</div>
	<div class="box">
		<form action="${pageContext.request.contextPath}/processController/upload" method="post" enctype="multipart/form-data">
			<h1>excel资料上传	</h1>
			<table>
				<tr><td>工件名：<input name="processName"></td></tr>
				<tr><td><input type="file" name=deployfile></td></tr>
				<tr><td><input type="submit" value="提交"></td></tr>
			</table>
		</form>
	
	</div>
<script type="text/javascript">	
	var deployGrid;
	var defineGrid;
	$(document).ready(function(){
		parent.$.messager.progress('close');
		deployGrid=$("#deploytable").datagrid({
				url:'${pageContext.request.contextPath}/processController/processdeployList',
				title:'部署信息列表',
				pagination:true,
				rownumbers:true,
				nowrap:true,
				fit : true,
				fitColumns : true,
				pageList:[10,20,30],
				columns:[[
					{field:'id',title:'ID',width:100},
					{field:'name',title:'流程名称',width:100},
					{field:'deploymentTime',title:'发布时间',width:100,
						formatter:function(value,row,index){
							var time=new Date(value);
							console.log(time);
							return time;
						},
					},
					{field:'cz',title:'操作',width:100,formatter:function(value,row,index){
						return "<a href=\"#\" onclick=delfunction(value,row,index)>删除</a>";
					}},
				]],
				onLoadSuccess:function(){
					$('#deploytable').datagrid('tooltip');
				}
			})
		defineGrid=$("#definetable").datagrid({
				url:'${pageContext.request.contextPath}/processController/processDefineList',
				title:'流程定义信息列表',
				pagination:true,
				rownumbers:true,
				nowrap:true,
				fitColumns : true,
				pageList:[10,20,30],
				columns:[[
					{field:'id',title:'ID',width:100},
					{field:'name',title:'流程名称',width:100},
					{field:'key',title:'流程定义的Key',width:100},
					{field:'description',title:'描述信息',width:100},
					{field:'version',title:'版本',width:100},
					{field:'resourceName',title:'流程定义规则文件名称',width:100},
					{field:'diagramResourceName',title:'流程定义的规则文件名称',width:100},
					{field:'deploymentId',title:'部署Id',width:100},
					{field:'cz',title:'操作',width:100,
						formatter :function(value,row,index){
						var str="";
						str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.deploymentId, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
						str += '&nbsp';
						str += $.formatString('<a target="_blank" onclick="searchFun(\'{0}\',\'{1}\')">查看流程图</a>',row.deploymentId,row.diagramResourceName);
						return str;	
						}
					},
				]],
				toolbar : '#toolbar',
				onContextMenu : function(e, row) {
					e.preventDefault();
					$(this).treegrid('unselectAll');
					$(this).treegrid('select', row.id);
					$('#menu').menu('show', {
						left : e.pageX,
						top : e.pageY
					});
				},
				onLoadSuccess : function() {
					parent.$.messager.progress('close');
					$(this).treegrid('tooltip');
				}
			})
	})
	
	/**删除流程定义*/
	function deleteFun(id){
		if (id != undefined) {
			defineGrid.datagrid('selectRecord', id);
		}
		var node=defineGrid.datagrid('getSelected');
		if(node){
			parent.$.messager.confirm('询问', '您是否要删除当前流程？',function(b){
					if(b){
						parent.$.messager.progress({
							title : '提示',
							text : '数据处理中，请稍后....'
						});
						$.post('${pageContext.request.contextPath}/processController/processdel', {
							id : id
						}, function(result) {
							if (result) {
								parent.$.messager.alert('提示', '删除成功', 'info');
								defineGrid.datagrid('reload');
								deployGrid.datagrid('reload');
							}
							parent.$.messager.progress('close');
						}, 'JSON');
					}
				});
			}
		}
	/**查看流程图片*/
	function searchFun(id,name){
		parent.$.messager.progress({
			title:'提示',
			text:'数据处理中，请稍后....',
		}) 
		console.log('查看流程图片:id'+id+"name:"+name)
		$.post('${pageContext.request.contextPath}/processController/searchProcessPic',{id:id,name:name},function(result){
			
			parent.$.messager.progress('close');
		})
	}
</script>
	<div id="menu" class="easyui-menu" style="width: 120px; display: none;">
			<div onclick="addFun();" data-options="iconCls:'pencil_add'">增加</div>
			<div onclick="deleteFun();" data-options="iconCls:'pencil_delete'">删除</div>
			<div onclick="editFun();" data-options="iconCls:'pencil'">编辑</div>
	</div>
	<div id="toolbar" style="display: none;">
		<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
		<a onclick="defineGrid.datagrid('reload');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'transmit'">刷新</a>
	</div>
</body>
</html>