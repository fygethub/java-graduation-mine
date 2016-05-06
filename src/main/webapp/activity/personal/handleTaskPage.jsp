<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="java.util.Date" %>
<script>
	$(document).ready(function(){
		window.setTimeout(function() {
			editor = KindEditor.create('#note', {
				width : '680px',
				height : '150px',
				items : [ 'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste', 'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript', 'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/', 'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak', 'anchor', 'link', 'unlink' ],
				uploadJson : '${pageContext.request.contextPath}/fileController/upload',
				fileManagerJson : '${pageContext.request.contextPath}/fileController/fileManage',
				allowFileManager : true
			});
			parent.$.messager.progress('close');
		},1)
		
		
		function fileManage() {
		editor.loadPlugin('filemanager', function() {
			editor.plugin.filemanagerDialog({
				viewType : 'VIEW',
				dirName : 'image',
				clickFn : function(url, title) {
					//KindEditor('#url').val(url);
					editor.insertHtml($.formatString('<img src="{0}" alt="" />', url));
					editor.hideDialog();
				}
			});
		});
	}
		
		
		
	})
	
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	  <div data-options="region:'center',border:false" title="" style="overflow: hidden;">
			<form id="form" method="post">
				<table class="table table-hover table-condensed">
					<tr>
						<th>编号</th>
						<td><input name="id" type="text" class="span2" value="${pageLeaveBill.id}" readonly="readonly"><input type="hidden" name="taskId" value="${ taskid}"/></td>
						<th>请假人</th>
						<td><input name="user" type="text" readonly="readonly" class="easyui-validatebox span2" data-options="required:true" value="${pageLeaveBill.user}"></td>
					</tr>
					<tr>
						<th>请假天数</th>
						<td><input name="days" type="text" readonly class="span2" value="${pageLeaveBill.days}" ></td>
						<th>请假事由</th>
						<td><input name="content" type="text" readonly  class="easyui-validatebox span2" data-options="required:true" value="${pageLeaveBill.content}"></td>
					</tr>
					<tr>
						<th>批注</th>
						<td colspan="3"><textarea name="comment" id="note" cols="50" rows="5" style="visibility: hidden;">${pageLeaveBill.remark}</textarea></td>
					</tr>
				</table>
				<c:if test="${empty  commentList }">
					<div>暂时没有批注</div>
				</c:if>
				
				<c:if test="${not empty  commentList }">
					<table class="table table-hover table-condensed">
					<tr>
							<th>时间</th>
							<th>批注人</th>
							<th>批注信息</th>
					</tr>
						<c:forEach items="${commentList}" var="commont">
						<tr>
							<td>${commont.time }</td>
							<td>${commont.userId }</td>
							<td>${commont.fullMessage }</td>
						</tr>
					</c:forEach>
					
				</table>
				</c:if>
				
			</form>
	  </div>
</div>