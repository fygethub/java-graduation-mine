<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>test_tree</title>
<jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
<ul id="tt" ></ul>  
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$("#tt").tree({
			url:'${pageContext.request.contextPath}/roleController/allTree',
		})
	})

</script>
</html>