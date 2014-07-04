<%@ page language="java" contentType="text/html;charset=utf-8"	pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%>
<title>网盘</title>
<table class="table table-bordered table-striped">
	<tr>
		<th colspan="5">我的文件</th>
	</tr>
	<tr>
		<th>名称</th>
		<th>修改日期</th>
		<th>大小</th>
		<th>种类</th>
	</tr>
	<c:forEach items="${containees }" var="item">
		<tr>
			<td>${item._Name }</td>
			<td>
			<fmt:formatDate value="${item._DateLastModified}"
					pattern="${datePattern }" /></td>
			<td>--</td>
			<td>目录</td>
		</tr>
	</c:forEach>
</table>