<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serviceurl" value="${ctx}/ver/argumentflow"/>
<html>
<head>
<title>审核流程表</title>
</head>
<body>
	<div id="yy-page-edit" class="container-fluid page-container page-content" >
		<div class="row yy-toolbar">
			<button id="yy-btn-save" class="btn blue btn-sm">
				<i class="fa fa-save"></i> 保存
			</button>
			<button id="yy-btn-cancel" class="btn blue btn-sm">
				<i class="fa fa-rotate-left"></i> 取消
			</button>
		</div>
	<div>
		<form id="yy-form-edit" class="form-horizontal yy-form-edit">
			<input name="uuid" type="text" class="hide" value="${entity.uuid}">
			<input name="auditType" type="text" class="hide" value="1">
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4 required">项目名称</label>
						<div class="col-md-8">
							<input name="name" type="text" class="form-control" value="${entity.name}">
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4 required">主版本号</label>
						<div class="col-md-8">
							<input name="masver" type="text" class="form-control" value="${entity.masver}">
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4 required">标注</label>
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
						<label class="control-label col-md-4 required">审核人员</label>
						<div class="col-md-8">
							<div class="input-group input-icon right">
									<input id="auditorUserUuid" name="auditorUser.uuid" type="hidden" value="${entity.auditorUser.uuid}"> 
									<i class="fa fa-remove" onclick="cleanDef('auditorUserUuid','auditorUserName');" title="清空"></i>
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
						<label class="control-label col-md-4 required">审批组</label>
						<div class="col-md-8">
							<div class="input-group input-icon right">
									<input id="approverUserUuid" name="approverUserGroup.uuid" type="hidden" value="${entity.approverUserGroup.uuid}"> 
									<i class="fa fa-remove" onclick="cleanDef('approverUserUuid','approverUserName');" title="清空"></i>
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
		</form>
	</div>
	<!-- 公用脚本 -->
	<%@include file="/WEB-INF/views/common/editscript.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			//按钮绑定事件
			bindEditActions();
			bindButtonAction();
			validateForms();
			setValue();
		});

		//设置默认值
		function setValue() {
			if ('${openstate}' == 'add') {
				//$("select[name='is_use']").val('1');
			} else if ('${openstate}' == 'edit') {
				//$("input[name='uuid']").val('${entity.uuid}');
			}
		}

		
		function bindButtonAction(){
			$('#auditorUser-select-btn').on('click', function() {
				layer.open({
					type : 2,
					title : '请选择人员',
					shadeClose : false,
					shade : 0.8,
					area : [ '1000px', '90%' ],
					content : '${ctx}/sys/ref/refUserSel?callBackMethod=window.parent.callBackAuditorUser'
				});
			});
			
			$('#approverUser-select-btn').on('click', function() {
				layer.open({
					type : 2,
					title : '请选择人员',
					shadeClose : false,
					shade : 0.8,
					area : [ '1000px', '90%' ],
					content : '${ctx}/sys/ref/refUserGroup?callBackMethod=window.parent.callBackapproverUser'
				});
			});
		}
		//表单校验
		function validateForms(){
			validata = $('#yy-form-edit').validate({
				onsubmit : true,
				rules : {
					'name' : {required : true,maxlength : 100},
					'masver' : {required : true,maxlength : 100},
					'subver' : {required : true,maxlength : 100},
					'auditorUserName' : {required : true,maxlength : 100},
					'approverUserName' : {required : true,maxlength : 100},
					'context' : {maxlength : 200}
				}
			});
		}
		
		//回调选择
		function callBackAuditorUser(data){
			$("#auditorUserUuid").val(data.uuid);
			$("#auditorUserName").val(data.username);
		}
		
		//回调选择
		function callBackapproverUser(data){
			$("#approverUserUuid").val(data.uuid);
			$("#approverUserName").val(data.name);
		}
	</script>
</body>
</html>
