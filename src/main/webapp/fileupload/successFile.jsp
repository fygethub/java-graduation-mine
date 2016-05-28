<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传成功</title>
<jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
        <div  style="text-align:center; width:400px;height:200px;border:1px solid red;margin:10px auto;border-radius:5px;padding-left: 10px;">
				<h1>文件上传成功</h1>
				<a style="position: relative; top:100px;" class="easyui-linkbutton" href="${pageContext.request.contextPath }/fileController/fileManagePage">继续上传</a>
		</div>
</body>
</html>