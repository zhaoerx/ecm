<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/taglibs.jsp"%>
<title>待办任务</title>

<table class="table table-bordered table-striped">
	<tr>
		<th colspan="5">我的任务</th>
	</tr>
	<tr>
		<th>流程名称</th>
		<th>任务</th>
		<th>任务编号</th>
		<th>主题</th>
		<th>流程历史</th>
	</tr>
	<c:forEach items="${workItems }" var="item">
		<tr>
			<td>${item.workflowName }</td>
			<td>${item.stepName }</td>
			<td>${item.workObjectNumber }</td>
			<td>${item.subject }</td>
			<td><a
				href="tracker/TrackerPro.html#workflowName=${item.workflowName};workflowNumber=${item.workflowNumber};fieldNames=F_Duration"
				target="${item.workflowNumber}"><i
					class="icon-play"></i></a></td>
		</tr>
	</c:forEach>
</table>
