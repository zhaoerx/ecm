<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<nav class="navbar navbar-inverse " role="navigation">
  <!-- Brand and toggle get grouped for better mobile display -->
  <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-navbar-collapse">
      <span class="sr-only">Toggle navigation</span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    <a class="navbar-brand" href="./">ECM Assets</a>
  </div>

  <!-- Collect the nav links, forms, and other content for toggling -->
  <div class="collapse navbar-collapse" id="bs-navbar-collapse">
    <ul class="nav navbar-nav">
      <li><a href="mywork.do">待办任务</a></li>
      <li><a href="myhistory.do">已办任务</a></li>
      <li><a href="pan.jsp">网盘</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="#">客户端下载</a></li>
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">更多 <b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li><a href="#">帮助中心</a></li>
          <li><a href="#">历史记录</a></li>
          <li><a href="#">版本更新</a></li>
          <li class="divider"></li>
          <li><a href="#">问题反馈</a></li>
        </ul>
      </li>
    </ul>
  </div><!-- /.navbar-collapse -->
</nav>