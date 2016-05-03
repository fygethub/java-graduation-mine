<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var eidtor;
	$(function(){
		window.setTimeout(function() {
			window.setTimeout(function() {
				editor = KindEditor.create('#noteAdd', {
					width : '670px',
					height : '370px',
					items : [ 'fullscreen', 'preview', 'print' ]
				});
				//editor.readonly();
				parent.$.messager.progress('close');
			}, 1)
	})
	})
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false">
		<table class="table table-hover table-condensed">
				<tr>
					<th>编号</th>
					<td><input name="id" type="text" readOnly class="span2" value="${leaveBill.id}" readonly="readonly"></td>
					<th>请假人</th>
					<td><input name="user" type="text" readOnly class="easyui-validatebox span2" data-options="required:true" value="${leaveBill.user}"></td>
				</tr>
				<tr>
					<th>请假天数</th>
					<td><input name="days" type="text" readOnly class="span2" value="${leaveBill.days}" ></td>
					<th>请假事由</th>
					<td><input name="content" type="text" readOnly class="easyui-validatebox span2" data-options="required:true" value="${leaveBill.content}"></td>
				</tr>
				<tr>
					<th>描述</th>
					<td colspan="3"><textarea name="remark" readOnly id="noteAdd" cols="50" rows="5" style="visibility: hidden;">${leaveBill.remark}</textarea></td>
				</tr>
		</table>
	</div>
</div>