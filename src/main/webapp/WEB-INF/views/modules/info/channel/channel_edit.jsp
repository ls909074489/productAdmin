<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serviceurl" value="${ctx}/info/channel"/>
<html>
<head>
<title>厂站通道信息</title>
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
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4 required">名称</label>
						<div class="col-md-8">
							<input name="name" type="text" class="form-control" value="${entity.name}">
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4 required">厂站</label>
						<div class="col-md-8">
							<div class="input-group input-icon right">
									<input id="stationUuid" name="station.uuid" type="hidden" value="${entity.station.uuid}"> 
									<i class="fa fa-remove" onclick="cleanDef('stationUuid','stationName');" title="清空"></i>
									<input id="stationName" name="stationName" type="text" class="form-control" readonly="readonly" 
										value="${entity.station.name}">
									<span class="input-group-btn">
										<button id="station-select-btn" class="btn btn-default btn-ref" type="button">
											<span class="glyphicon glyphicon-search"></span>
										</button>
									</span>
								</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4 required">通道类型</label>
						<div class="col-md-8">
							<select class="yy-input-enumdata form-control" id="channelType" 
							name="channelType" data-enum-group="ChannelType"></select>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4 required">通道参数</label>
						<div class="col-md-8">
							<input name="argument" type="text" class="form-control" value="${entity.argument}">
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
				$("select[name='channelType']").val('${entity.channelType}');
			}
		}
		
		function bindButtonAction(){
			$('#station-select-btn').on('click', function() {
				layer.open({
					type : 2,
					title : '请选择厂站',
					shadeClose : false,
					shade : 0.8,
					area : [ '1000px', '90%' ],
					content : '${ctx}/sys/ref/refStationSel?callBackMethod=window.parent.callBackStation'
				});
			});
		}

		//表单校验
		function validateForms(){
			validata = $('#yy-form-edit').validate({
				onsubmit : true,
				rules : {
					'name' : {required : true,maxlength : 100},
					'channelType' : {required : true,maxlength : 100},
					'argument' : {required : true,maxlength : 100},
					'stationName' : {required : true},
					'memo' : {maxlength : 200},
				}
			});
		}
		
		//回调选择
		function callBackStation(data){
			$("#stationUuid").val(data.uuid);
			$("#stationName").val(data.name);
		}
	</script>
</body>
</html>
