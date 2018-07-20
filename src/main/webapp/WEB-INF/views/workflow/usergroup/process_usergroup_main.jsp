<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serviceurl" value="${ctx}/process/usergroup" />
<html>
<head>
<title>用户组管理</title>
</head>
<body>
	<div id="yy-page" class="container-fluid page-container">
			<!-- 查看页面 -->
		<%@include file="process_usergroup_list.jsp"%>
		<!-- 编辑页面 -->
		<%@include file="process_usergroup_edit.jsp"%>
		
		<!-- 明细页面 -->
		<%@include file="process_usergroup_detail.jsp"%>
		
		<!-- 公用脚本 -->
		<%@include file="/WEB-INF/views/common/commonscript.jsp"%>

		<!-- 功能脚本 -->
		<%@include file="process_usergroup_script.jsp"%>
		
	</div>
</body>
</html>