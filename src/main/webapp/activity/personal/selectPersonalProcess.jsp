<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>查询个人</title>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
	var datagrid;
	$(document).ready(function(){
	parent.$.messager.progress('close');
     datagrid=$('#leaveTable').datagrid({
    	 url : '${pageContext.request.contextPath}/personalProcessController/task',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
		//	sortName : 'leaveDate',
		//	sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns:[[
		          {field:'id',title:'ID',width:100},
		          {field:'name',title:'任务名',width:100},
		          {field:'createTime',title:'时间',width:100,
						formatter:function(value, row, index){
							var date=new Date(value);
							
							return date.toLocaleDateString() +" "+date.toTimeString().split(" ")[0];						}},
		          {field:'assignee',title:'办理人',width:100},
		         {field:'cz',title:'操作',width:150,formatter:function(value,row,index){
		        	var str='';
		        	str += $.formatString('<a onclick="doTaskFun(\'{0}\');">办理任务</a>', row.id);
		        	str += '&nbsp';
		        	str += $.formatString('<a target="_black" href="${pageContext.request.contextPath}/processController/searchProcessPicPage?id={0}">查看当前流程图</a>', row.id);
		        	return str;
		         }}
	          ]],
			  onLoadSuccess : function() {
					//$('#searchForm table').show();
					parent.$.messager.progress('close');
	
					$(this).datagrid('tooltip');
				},
			onDblClickCell :function(rowIndex, field, value){
			
				$(this).datagrid('unselectRow',rowIndex);
			}
		});	 	
		
		
	})
	
	function doTaskFun(id){
		parent.$.messager.progress('close');
		if(id == undefined){
			var rows=datagrid.datagrid('getSelections');
			id=rows[0].id;
		}
		
		$.modalDialog({
			title:'编辑',
			width:780,
			height:500,
			href:'${pageContext.request.contextPath}/personalProcessController/taskEditPage?id='+id,
			buttons:'#tb',
		})
	}
	
	function lookFun(){
		console.log('lookFun');
	}
	
	function outcomeFun(outcome){
		console.log('outcomeFun'+outcome)
		var form=$.modalDialog.handler.find('#form').form('submit',{
			url : '${pageContext.request.contextPath}/personalProcessController/submitTtask',
			onSubmit : function(param) {
				editor.sync();
				param.outcome=outcome;
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					datagrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				} 
			}
		});
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
	
</body>
</html>