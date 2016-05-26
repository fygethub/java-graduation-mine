<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 个人查询流程图片 ，查询流程图 -->
<!DOCTYPE html>
<html>
<head>
<title>查询个人</title>
<jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
	<!-- 1.获取到规则流程图 -->
	<img style="position: absolute;top: 0px;left: 0px;" src="${pageContext.request.contextPath }/processController/searchProcessPic?id=${deploymentId }&name=${imageName}"/>
	
	<!-- 2.根据当前活动的坐标，动态绘制DIV -->
	<div style="position: absolute;border:1px solid red;top:${map.y-25 }px;left: ${map.x -25 }px;width: ${map.width }px;height:${map.height }px; ">
	</div>
	</body>

	<%  out=pageContext.pushBody();  
     %> 
</body>
</html>