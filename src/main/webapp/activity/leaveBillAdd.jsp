<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	$(document).ready(function(){
	/* 	var editor;
		window.setTimeout(function() {	
			editor=KindEditor.create('#noteAdd',{
				width:'680px',
				height:'300',
				items:[ 'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste', 'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript', 'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/', 'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak', 'anchor', 'link', 'unlink' ],
				allowFileManager : true,
				uploadJson : '${pageContext.request.contextPath}/fileController/upload',
				fileManagerJson : '${pageContext.request.contextPath}/fileController/fileManage',
				
			})
			parent.$.messager.progress('close');
		},1) */
		
		$('#form').form({
			url:'${pageContext.request.contextPath}/leaveBillController/add',
			dataType:'json',
			 onSubmit:function(){
				//editor.sync();
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
			success:function(result){
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if(result.success){
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
					parent.$.messager.show({
						msg:result.msg,
						title:'提示',
					})
				}
				else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		})
	})	
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div  data-options="region:'center',border:false" title="请假单" style="overflow:hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
				<tr>
					<th>编号</th>
					<td><input name="id" type="text" class="span2" value="${pageLeaveBill.id}" readonly="readonly"></td>
					<th>请假人</th>
					<td><input name="name" type="text" readOnly class="easyui-validatebox span2" data-options="required:true" value="${sessionInfo.name }"></td>
				</tr>
				<tr>
					<th>请假天数</th>
					<td><input name="days" type="text" class="span2" value="" ></td>
					<th>请假事由</th>
					<td><input name="content" type="text"  class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
				<!-- <tr>
					<th>描述</th>
					<td colspan="3"><textarea name="remark" id="noteAdd" cols="50" rows="5" style="visibility: hidden;"></textarea></td>
				</tr> -->
			</table>
		</form>
	</div>
</div>