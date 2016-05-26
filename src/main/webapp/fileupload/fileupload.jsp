<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传文件</title>
<link href="${pageContext.request.contextPath}/jslib/bootstrapFileupload/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath }/jslib/bootstrapFileupload/css/default.css" type="text/css">
<link href="${pageContext.request.contextPath }/jslib/bootstrapFileupload/css/fileinput.css" media="all" rel="stylesheet" type="text/css" >
</head>
<body>
	<div class="container kv-main">
           	<form enctype="multipart/form-data">
               
                <hr>最后一个
                <div class="form-group">
                    <input id="file-5" class="file" type="file" >
                </div>
            </form>
	</div>

	<script src="${pageContext.request.contextPath}/jslib/jquery-1.8.3.js" type="text/javascript" charset="utf-8"></script>
    <script src="${pageContext.request.contextPath}/jslib/bootstrapFileupload/js/fileinput.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/jslib/bootstrapFileupload/js/fileinput_locale_zh.js" type="text/javascript"></script>
	<script charset="utf-8" src="${pageContext.request.contextPath}/jslib/bootstrapFileupload/js/bootstrap.min.js" charset="utf-8"></script>

	<script>
	    $("#file-5").fileinput({
	        uploadUrl: '#', 
	        allowedFileExtensions : ['jpg', 'png','gif'],
	        overwriteInitial: false,
	        maxFileSize: 1000,
	        maxFilesNum: 10,
	        //allowedFileTypes: ['image', 'video', 'flash'],
	        slugCallback: function(filename) {
	            return filename.replace('(', '_').replace(']', '_');
	        }
		});
		</script>

</body>
</html>