<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="include/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网盘</title>
<link rel="stylesheet"	href="/webjars/bootstrap/3.1.0/css/bootstrap.min.css">
<link rel="stylesheet" href="css/docs.css">
</head>
<body>
	<%@include file="include/header.jsp"%>
	<div class="container">
		<div class="row">
			<div class="col-md-3">
				<ul class="nav">
					<li><a href="#">全部文件</a></li>
					<li><a href="#">图片</a></li>
					<li><a href="#">文档</a></li>
					<li><a href="#">视频</a></li>
					<li><a href="#">音乐</a></li>
					<li><a href="#">其他</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="col-md-9" role="main"></div>

<script src="/webjars/jquery/1.11.1/jquery.min.js"></script>
<script	src="/webjars/bootstrap/3.1.0/js/bootstrap.min.js"></script>
</body>
</html>