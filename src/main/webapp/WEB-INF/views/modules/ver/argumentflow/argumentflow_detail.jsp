<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serviceurl" value="${ctx}/ver/argumentflow"/>
<html>
<head>
<title>审核流程表</title>
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
			<fieldset disabled="disabled">
				<input name="uuid" type="text" class="hide" value="${entity.uuid}">
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label class="control-label col-md-4">项目名称</label>
							<div class="col-md-8">
								<input name="name" type="text" class="form-control" value="${entity.name}">
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label class="control-label col-md-4">主版本号</label>
							<div class="col-md-8">
								<input name="masver" type="text" class="form-control" value="${entity.masver}">
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label class="control-label col-md-4">标注</label>
							<div class="col-md-8">
								<input name="subver" type="text" class="form-control" value="${entity.subver}">
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-8">
						<div class="form-group">
							<label class="control-label col-md-2">审核内容</label>
							<div class="col-md-10">
								<label><input name="xxdbsh" type="checkbox" value="1" class="" style="width: 20px;height: 20px;" 
									<c:if test="${entity.xxdbsh eq 1}">checked="checked"</c:if>/>
									信息点表审核&nbsp;&nbsp;&nbsp;&nbsp;
								</label> 
								<label><input name="wjjy" type="checkbox" value="1" class="" style="width: 20px;height: 20px;" 
									<c:if test="${entity.wjjy eq 1}">checked="checked"</c:if>/>
									文件校验&nbsp;&nbsp;&nbsp;&nbsp;
								</label>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label class="control-label col-md-4">审核人员</label>
							<div class="col-md-8">
								<div class="input-group input-icon right">
										<input id="auditorUserUuid" name="auditorUser.uuid" type="hidden" value="${entity.auditorUser.uuid}"> 
										<i class="fa fa-remove" title="清空"></i>
										<input id="auditorUserName" name="auditorUserName" type="text" class="form-control" readonly="readonly" 
											value="${entity.auditorUser.username}">
										<span class="input-group-btn">
											<button id="auditorUser-select-btn" class="btn btn-default btn-ref" type="button">
												<span class="glyphicon glyphicon-search"></span>
											</button>
										</span>
									</div>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label class="control-label col-md-4">审批组</label>
							<div class="col-md-8">
								<div class="input-group input-icon right">
										<input id="approverUserUuid" name="approverUserGroup.uuid" type="hidden" value="${entity.approverUserGroup.uuid}"> 
										<i class="fa fa-remove" title="清空"></i>
										<input id="approverUserName" name="approverUserName" type="text" class="form-control" readonly="readonly" 
											value="${entity.approverUserGroup.name}">
										<span class="input-group-btn">
											<button id="approverUser-select-btn" class="btn btn-default btn-ref" type="button">
												<span class="glyphicon glyphicon-search"></span>
											</button>
										</span>
									</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-8">
						<div class="form-group">
							<label class="control-label col-md-2">备注</label>
							<div class="col-md-10">
								<textarea name="context" class="form-control">${entity.context}</textarea>
							</div>
						</div>
					</div>
				</div>
			</fieldset>
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

			//YYFormUtils.lockForm("yy-form-detail");
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
