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
	</tr>
	<c:forEach items="${containees }" var="item">
		<tr>
			<td>
			<c:if test="${item.type == 0 }">
			<a href="myfiles.do?path=${item.path }">
			<i class="icon-folder-close"></i>${item.name }
			</a>
			</c:if>
			<c:if test="${item.type == 1 }">
			
			<i class="icon-file"></i>${item.name }
			</c:if>
			 
			 </td>
			<td>
			<fmt:formatDate value="${item.lastModifiedDate}"
					pattern="${datePattern }" /></td>
			<td>${item.size }</td>
		</tr>
	</c:forEach>
</table>