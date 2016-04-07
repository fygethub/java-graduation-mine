<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>sypro_1</title>
<jsp:include page="inc.jsp"></jsp:include>
</head>
<body>
	<div class="hero-unit">
		<h1>SyPro基础权限演示系统</h1>
		<div>
			<ul>
				<li>作者：孙宇</li>
				<li>前台由EasyUI1.3.3编写，后台是JAVA语言编写，应用框架spring mvc+hibernate4+maven</li>
			</ul>
		</div>
		<p>
			<a class="btn btn-primary btn-large" href="${pageContext.request.contextPath}/index.jsp" target="_blank"> 进入(里面有源码下载哦) </a>
		</p>
	</div>
	${pageContext.request.contextPath}
</body>
</html>