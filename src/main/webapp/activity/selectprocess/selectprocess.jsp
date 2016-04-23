<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传文件</title>
<jsp:include page="../../inc.jsp"></jsp:include>
<style type="text/css">
*{margin:0;padding:0;}
body{font-size:12px;font-family:"微软雅黑";color:#ccc;background:url(img/bg.jpg)}
.box{width:400px;height:200px;border:1px solid red;margin:100px auto;padding:20px 300px;}
h1{font-size:30px;color:#6d4242;text-shadow:5px 5px 10px #111;}
.box .selectBtn{width:200px;height:40px;border-radius:5px;font-size:30px;}
</style>
</head>
<body>
	<div class="box">
			select my process	
	</div>
<script type="text/javascript">	
	$(document).ready(function(){
		parent.$.messager.progress('close');
	})
</script>
</body>
</html>