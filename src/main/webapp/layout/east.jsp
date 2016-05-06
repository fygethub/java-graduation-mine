<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript" charset="utf-8">

	formatString = function(str) {
		for ( var i = 0; i < arguments.length - 1; i++) {
			str = str.replace("{" + i + "}", arguments[i + 1]);
		}
		return str;
	};
	$(document).ready(function(){
	setTimeout("$('#layout_east_onlineDatagrid').datagrid('reload')",2000);
		
	$('#layout_east_onlineDatagrid').datagrid({
			url : '${pageContext.request.contextPath}/onlineController/datagrid', 
			title : '',
			iconCls : '',
			fit : true,
			fitColumns : true,
			pagination : true,
			pageSize : 10,
			pageList : [ 10 ],
			nowarp : false,
			border : false,
			idField : 'id',
			sortName : 'logindatetime',
			sortOrder : 'desc',
			frozenColumns : [ [ {
				title : '编号',
				field : 'id',
				width : 150,
				hidden : true
			} ] ],
			columns : [ [ {
				title : '登录名',
				field : 'loginname',
				width : 100,
				sortable : true,
				formatter : function(value, rowData, rowIndex) {
					return formatString('<span title="{0}">{1}</span>', value, value);
				}
			}, {
				title : 'IP',
				field : 'ip',
				width : 150,
				sortable : true,
				formatter : function(value, rowData, rowIndex) {
					return formatString('<span title="{0}">{1}</span>', value, value);
				}
			}, {
				title : '登录时间',
				field : 'logindatetime',
				width : 150,
				sortable : true,
				formatter : function(value, rowData, rowIndex) {
					return formatString('<span title="{0}">{1}</span>', value, value);
				},
				hidden : true
			} ] ],
			onClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data) {
				$('#layout_east_onlinePanel').panel('setTitle', '( ' + data.total + ' )人在线');
			}
		}).datagrid('getPager').pagination({
			showPageList : false,
			showRefresh : false,
			beforePageText : '',
			afterPageText : '/{pages}',
			displayMsg : ''
		});
		
		$('#layout_east_calendar').calendar({
			fit : true,
			current : new Date(),
			border : false,
			onSelect : function(date) {
				$(this).calendar('moveTo', new Date());
			}
		});
		$('#layout_east_onlinePanel').panel({
			tools : [ {
				iconCls : 'database_refresh',
				handler : function() {
					$('#layout_east_onlineDatagrid').datagrid('reload');
				}
			} ]
		});
		$('#layout_east_onlineDatagrid').datagrid('reload');	
		 window.setInterval(function() {
			var $datagrid= $('#layout_east_onlineDatagrid')
			var rows_length=$datagrid.datagrid('getRows').length;
			$.ajax({
				url:'${pageContext.request.contextPath}/onlineController/datagrid',
				type:'POST',
				success:function(data){
					//data=JSON.parse(data);
					/* for(var i=0;i<data.rows.length;i++){
						$datagrid.datagrid('updateRow',{index:i,row:{ip:data.rows[i].ip,loginname:data.rows[i].loginname}})
						console.log("updata:"+i)
					}
					
					while(rows.length > data.rows.length ){
						$datagrid.datagrid('deleteRow',data.rows.length);
						console.log('delete row:');
					} */
					if(rows_length != data.rows.length){
						$datagrid.datagrid('reload');
					}
				},
				error:function(){
					$.messager.show({
						title:'messger',
						msg:'error',
					});
				}
			})
	
		}, 5000); 
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 180px; overflow: hidden;">
		<div id="layout_east_calendar"></div>
	</div>
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<div id="layout_east_onlinePanel" data-options="fit:true,border:false" title="用户在线列表">
			<table id="layout_east_onlineDatagrid"></table>
		</div>
	</div>
</div>