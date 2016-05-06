<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>请假管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
	var datagrid;
	$(document).ready(function(){
	parent.$.messager.progress('close');
     datagrid=$('#leaveTable').datagrid({
    	 url : '${pageContext.request.contextPath}/leaveBillController/datagrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'leaveDate',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns:[[
		          {field:'id',title:'ID',width:100,hidden:true},
		          {field:'user',title:'请假人',width:100},
		          {field:'days',title:'请假天数',width:100},
		          {field:'content',title:'请假内容',width:100},
		          {field:'leaveDate',title:'请假时间',width:100,formatter:function(value,row,index){
		        	  /* var time= new Date(row.leaveDate);
		        	  var year=time.getFullYear();
		        	  var month=time.getMonth();
		        	  var day=time.getDay();
		        	  var hour=time.getHours();
		        	  var min=time.getMinutes();
		        	  var sec=time.getSeconds();
		        	  if(hour < 10)
		        		  hour='0'+hour;
		        	  
		        	  return year+"/"+month+"/"+day+" "+hour+":"+min+":"+sec; */
		        	  return new Date(row.leaveDate);
		          }},
		          {field:'remark',title:'备注',width:100,hidden:true},
		         
		          {field:'state',title:'请假单状态',width:100,formatter:function(value,row,index){
		        	  if(row.state == '0')
		        		  return '待提交';
		        	  if(row.state == '1')
		        		return '正在审批';  
		        	  
		        	  
		          }},
		          {field:'cz',title:'操作',width:100,
		        	formatter:function(value,row,index){
		        		var str = '';
		        		if(row.state == '0'){
							str += $.formatString('<a onclick="editFun(\'{0}\');">编辑</a>', row.id);
							str += '&nbsp;';
							str += $.formatString('<a onclick="deleteFun(\'{0}\');">删除</a>', row.id);
							str += '&nbsp;';
							str += $.formatString('<a onclick="viewFun(\'{0}\');">查看</a>', row.id);
							str += '&nbsp';
							str += $.formatString('<a onclick="startFun(\'{0}\');">提交申请</a>',row.id);
		        		}
		        		if(row.state == '1'){
		        			str += $.formatString('<a onclick="approvalFun(\'{0}\');">查看审批</a>', row.id);
		        		}
		        		return str;
		        	}}
	          ]],
	          toolbar : '#toolbar',
			  onLoadSuccess : function() {
					//$('#searchForm table').show();
					parent.$.messager.progress('close');
	
					$(this).datagrid('tooltip');
				},
			  onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#menu').menu('show', {
						left : e.pageX,
						top : e.pageY
					});
				},
			onDblClickCell :function(rowIndex, field, value){
			
				$(this).datagrid('unselectRow',rowIndex);
			}
		});	 	
		
		
	})
	
	/**add funtion*/
	function addFun(){
		parent.$.modalDialog({
			title:'添加请假单',
			width:780,
			height:500,
			href:'${pageContext.request.contextPath}/leaveBillController/leaveBillAddpage',
			buttons:[{
				text:'添加',
				handler:function(){
					parent.$.modalDialog.openner_dataGrid = datagrid;
					var f=parent.$.modalDialog.handler.find('#form');
					console.log('提交')
					f.submit();
				}
			}]
		})
		parent.$.messager.progress('close');
	}
	
	/**start */
	function startFun(id){
		parent.$.messager.progress();
		$.post('${pageContext.request.contextPath}/personalProcessController/startprocess',{
			id:id
		}, function(result) {
			if (result.success) {
				parent.$.messager.alert('提示', result.msg, 'info');
				datagrid.datagrid('reload');
			}
			parent.$.messager.progress('close');
			datagrid.datagrid('unselectAll');
		}, 'JSON');
		
	}
	
	
	/**edit */
	function editFun(id){
		parent.$.messager.progress('close');
		if(id==undefined){
			var rows=datagrid.datagrid('getSelections');
			id=rows[0].id;
		}
		$.modalDialog({
			title:'编辑',
			width:780,
			height:500,
			href:'${pageContext.request.contextPath}/leaveBillController/editPage?id='+id,
			buttons:[{
				text:'编辑',
				handler:function(){
					parent.$.modalDialog.fyEditdatagrid = datagrid;
					var f=$('#form');
					f.submit();
				}
			}]
		})
		parent.$.messager.progress('close');
	}
	
	/**delete*/
	function deleteFun(id){
		if(id==undefined){
			var rows=datagrid.datagrid('getSelections');
			id=rows[0].id;
		}
		parent.$.messager.confirm('询问','您是否要删除当前请假单?',function(r){
			if(r){
				parent.$.messager.progress({
					title:'提示',
					text:'数据处理中，请稍后...'
				});
				$.post('${pageContext.request.contextPath}/leaveBillController/delete',{
					id:id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						datagrid.datagrid('reload');
					}
					parent.$.messager.progress('close');
				}, 'JSON');
			}
		})
		
	}
	
	/**view*/
	 function viewFun(id){
		parent.$.messager.progress('close');
		console.log('查看'+id)
		if(id == undefined){
			var rows=datagrid.datagrid('getSelections');
			id=rows[0].id;
		}
		parent.$.modalDialog({
			title:'查看请假单',
			width:780,
			height:500,
			href:'${pageContext.request.contextPath}/leaveBillController/view?id=' + id,
		});
	} 
	
	
	/**approvalFun*/
	function approvalFun(id){
		parent.$.messager.progress('close');
		$.post('${pageContext.request.contextPath}/',{
			id:id
		}, function(result) {
			if (result.success) {
				parent.$.messager.alert('提示', result.msg, 'info');
				datagrid.datagrid('reload');
			}
			parent.$.messager.progress('close');
		}, 'JSON');
	}
	
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false">
			<table id="leaveTable">
			</table>
		</div>
	</div>
	<div id="toolbar">
		<a onclick="addFun();"  class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
	</div>
	<div id="menu" class="easyui-menu" style="width: 120px; display: none;">
			<div onclick="addFun();" data-options="iconCls:'pencil_add'">增加</div>
			<div onclick="deleteFun();" data-options="iconCls:'pencil_delete'">删除</div>
			<div onclick="editFun();" data-options="iconCls:'pencil'">编辑</div>
	</div>
</body>
</html>