<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="include/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>已办任务</title>
<link rel="stylesheet"	href="/webjars/bootstrap/3.1.0/css/bootstrap.min.css">
</head>
<body>
	<%@include file="include/header.jsp"%>
	<table class="table table-bordered table-striped">
		<tr>
			<th colspan="6">我的已办任务</th>
		</tr>
		<tr>
			<th>流程名称</th>
			<th>任务</th>
			<th>任务编号</th>
			<th>主题</th>
			<th>处理时间</th>
			<th>流程历史</th>
		</tr>
		<c:forEach items="${historyItems }" var="item">
			<tr>
				<td>${item.workflowName }</td>
				<td>${item.stepName }</td>
				<td>${item.f_WorkFlowNumber }</td>
				<td>${item.subject }</td>
				<td><fmt:formatDate value="${item.f_TimeStamp}"
						pattern="MM/dd/yyyy HH:mm aa" type="time" timeStyle="full" /></td>
				<td><a
					href="tracker/TrackerPro.html#workflowName=${item.workflowName};workflowNumber=${item.f_WorkFlowNumber};fieldNames=F_Duration"
					target="{item.f_WorkFlowNumber}"><i class="glyphicon glyphicon-play"></i></a></td>
			</tr>
		</c:forEach>
	</table>
<script src="/webjars/jquery/1.11.1/jquery.min.js"></script>
<script	src="/webjars/bootstrap/3.1.0/js/bootstrap.min.js"></script>
</body>
</html>