<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serviceurl" value="${ctx}/info/purchase"/>
<html>
<head>
<title>间隔信息</title>
</head>
<body>
	<div id="yy-page-detail" class="container-fluid page-container page-content" >
		<div class="row yy-toolbar">
			<button id="yy-btn-backtolist" class="btn blue btn-sm">
				<i class="fa fa-rotate-left"></i> 返回
			</button>
		</div>
	<div>
		<form id="yy-form-detail" class="form-horizontal yy-form-detail">
			<input name="uuid" type="text" class="hide" value="${entity.uuid}">
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">名称</label>
						<div class="col-md-8">
							<input name="name" type="text" class="form-control" value="${entity.name}">
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">价格</label>
						<div class="col-md-8">
							<input name="price" type="text" class="form-control" value="${entity.price}">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label col-md-2">备注</label>
						<div class="col-md-10">
							<textarea name="memo" class="form-control">${entity.memo}</textarea>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<!-- 公用脚本 -->
	<%@include file="/WEB-INF/views/common/detailscript.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			//按钮绑定事件
			bindDetailActions();
			//bindButtonAction();
			setValue();

			YYFormUtils.lockForm("yy-form-detail");
		});

		//设置默认值
		function setValue() {
			if ('${openstate}' == 'add') {
				//$("select[name='is_use']").val('1');
			} else if ('${openstate}' == 'detail') {
				//$("input[name='uuid']").val('${entity.uuid}');
			}
		}

	</script>
</body>
</html>
