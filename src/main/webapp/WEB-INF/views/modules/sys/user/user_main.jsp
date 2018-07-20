<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serviceurl" value="${ctx}/sys/user" />
<html>
<head>
<title>用户管理</title>
</head>
<body>
	<div id="yy-page" class="container-fluid page-container">

		<!-- 查看页面 -->
		<%@include file="user_list.jsp"%>

		<!-- 编辑页面 -->
		<%@include file="user_edit.jsp"%>
		
		<!-- 明细页面 -->
		<%@include file="user_detail.jsp"%>
		
		<!-- 公用脚本 -->
		<%@include file="/WEB-INF/views/common/commonscript.jsp"%>

		<!-- 功能脚本 -->
		<%@include file="user_script.jsp"%>

	</div>
</body>
</html>