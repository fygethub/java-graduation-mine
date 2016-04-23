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
<c:if test="${fn:contains(sessionInfo.resourceList, '/fileController/fileManage.do')}">
	<script type="text/javascript">
		$.canEdit = true;
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
	<div class="box">
		<form action="${pageContext.request.contextPath}/processController/upload" method="post" enctype="multipart/form-data">
			<h1>excel资料上传	</h1>
			<table>
				<tr><td>工件名：<input name="name"></td></tr>
				<tr><td><input type="file"></td></tr>
				<tr><td><input type="submit" value="提交"></td></tr>
			</table>
		</form>
	
	</div>
<script type="text/javascript">	
	$(document).ready(function(){
		parent.$.messager.progress('close');
	})
</script>
</body>
</html>