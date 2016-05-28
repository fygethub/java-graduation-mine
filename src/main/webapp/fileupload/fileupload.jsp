<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../inc.jsp"></jsp:include> 
<title>上传文件</title>
<link href="${pageContext.request.contextPath}/jslib/bootstrapFileupload/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath }/jslib/bootstrapFileupload/css/default.css" type="text/css">
<link href="${pageContext.request.contextPath }/jslib/bootstrapFileupload/css/fileinput.css" media="all" rel="stylesheet" type="text/css" >
</head>
<body>
	<div class="container kv-main">
           	<form enctype="multipart/form-data" id="upload_form" method="post" >
				<label style="margin-left:25%;display: block;">文件描述</label>
				<textarea name="remark" id="note" cols="30" rows="5" style="visibility: hidden;"></textarea>
                <div style="width:80%;margin-top: 10px;margin-bottom:20px;">
                    <input  class="projectfile" type="file" name="file">
                </div>
                <button class="btn btn-primary" id="submit" style="position:absolute; left:40%;">提交</button>
            </form>
	</div>

	<%-- <script src="${pageContext.request.contextPath}/jslib/bootstrapFileupload/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script> --%>
    <script src="${pageContext.request.contextPath}/jslib/bootstrapFileupload/js/fileinput.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/jslib/bootstrapFileupload/js/fileinput_locale_zh.js" type="text/javascript"></script>
	<script charset="utf-8" src="${pageContext.request.contextPath}/jslib/bootstrapFileupload/js/bootstrap.min.js" charset="utf-8"></script>

	<script>
		var editor;
		$(document).ready(function(){
			parent.$.messager.progress('close');
			window.setTimeout(function() {	
				editor=KindEditor.create('#note',{
					width:'680',
					height:'300',
					items:[ 'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste', 'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript', 'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/', 'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat'],
					allowFileManager : true,
				})
			},1) 
			
			 $(".projectfile").fileinput({
                showUpload : false,
                showRemove : false,
                language : 'zh',
                allowedFileExtensions : ['jpg', 'png','gif','doc','txt'],
                maxFileSize : 2000,

            });
					  
			$("#submit").click(function(){
				
				$("#upload_form").form('submit',{
					 url:'${pageContext.request.contextPath}/fileController/upload',    
				    onSubmit: function(){    
				       if($("input").val()=="" || $("input").val()==undefined){
				    	  return false;
				       }   
				    }

				})
			})
			parent.$.messager.progress('close');
			
		
		});
		
			
		</script>

</body>
</html>