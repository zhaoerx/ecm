<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="include/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ECM Assets</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet"	href="/webjars/bootstrap/3.1.0/css/bootstrap.min.css">
<body>
<%@include file="include/header.jsp"%>
<h1>Hello, this is ECM Assets page.</h1>
	<h2>
		<a href="mywork.do">我的待办任务</a>
	</h2>
	<h2>
		<a href="myhistory.do">我的已办任务</a>
	</h2>
	<div class="container-fluid">
  <div class="row-fluid">
    <div class="span4">
      <!--Sidebar content-->
    </div>
    <div class="span8">
      <!--Body content-->
    </div>
  </div>
</div>
<script src="/webjars/jquery/1.11.1/jquery.min.js"></script>
<script	src="/webjars/bootstrap/3.1.0/js/bootstrap.min.js"></script>
</body>
</html>