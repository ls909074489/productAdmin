<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serviceurl" value="${ctx}/ver/versionTest"/>
<c:set var="serviceurlsub" value="${ctx}/ver/versionTestSub"/>
<html>
<head>
<title>版本信息表</title>
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
			<!-- <button id="yy-btn-submit" class="btn yellow btn-sm btn-info">
				<i class="fa fa-send"></i> 保存提交
			</button> -->
		</div>
	<div>
		<form id="yy-form-edit" class="form-horizontal yy-form-edit">
			<input name="uuid" type="hidden" value="${entity.uuid}">
			<input name="mainindex" id="mainindex" type="hidden"  value="${entity.mainindex}">
			
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4 required">类型</label>
						<div class="col-md-8">
							<select class="yy-input-enumdata form-control" id="versionType1"
								 name="versionType1">
							 	<option value=""></option>
							 	<option value="3">主站</option>
							 	<option value="5">子站</option>
							 </select>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4 required">版本</label>
						<div class="col-md-8">
							<select class="yy-input-enumdata form-control" id="versionNum1" name="versionNum1">
								<option value=""></option>
							</select>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label col-md-2 required">名称</label>
						<div class="col-md-10">
							<input name="name" type="text" class="form-control" value="${entity.name}">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">核对日期</label>
						<div class="col-md-8">
							<input type="text" name="checkingDate" id="checkingDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate form-control" style="">
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">审核人员</label>
						<div class="col-md-8">
							<div class="input-group input-icon right">
									<input id="masterChargerUuid" name="masterCharger.uuid" type="hidden" value="${entity.masterCharger.uuid}"> 
									<i class="fa fa-remove" onclick="cleanDef('masterChargerUuid','masterChargerName');" title="清空"></i>
									<input id="masterChargerName" name="masterChargerName" type="text" class="form-control" readonly="readonly" 
										value="${entity.masterCharger.username}">
									<span class="input-group-btn">
										<button id="masterCharger-select-btn" class="btn btn-default btn-ref" type="button">
											<span class="glyphicon glyphicon-search"></span>
										</button>
									</span>
								</div>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">主站核对人</label>
						<div class="col-md-8">
							<div class="input-group input-icon right">
								<input id="stationChargerUuid" name="stationCharger.uuid" type="hidden" value="${entity.stationCharger.uuid}"> 
								<i class="fa fa-remove" onclick="cleanDef('stationChargerUuid','stationChargerName');" title="清空"></i>
								<input id="stationChargerName" name="stationChargerName" type="text" class="form-control" readonly="readonly" 
									value="${entity.stationCharger.username}">
								<span class="input-group-btn">
									<button id="stationCharger-select-btn" class="btn btn-default btn-ref" type="button">
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
						<label class="control-label col-md-4">测试方式</label>
						<div class="col-md-8">
							<select class="yy-input-enumdata form-control" id="testMethod" name="testMethod" data-enum-group="TestMethod"></select>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">主站测试结论</label>
						<div class="col-md-8">
							<input name="masterTestMsg" type="text" class="form-control" value="${entity.masterTestMsg}">
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">厂站测试人员</label>
						<div class="col-md-8">
							<input name="stationTestPerson" type="text" class="form-control" value="${entity.stationTestPerson}">
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
			validateForms();
			
			
			//设备类型改变事件
			$("#versionType1").change(function(){
				var stationVersionLoading = layer.load(2);
				$.ajax({
					type : "POST",
					data :{"tid": $(this).val(),"sid":'${currentStation.uuid}'},
					url : "${ctx}/ver/versioninfo/dataByTidSid",
					async : true,
					dataType : "json",
					success : function(data) {
						$("#versionNum1").empty();
						$("#versionNum1").append("<option value=''></option>");
						layer.close(stationVersionLoading);
						if (data.success) {
							YYUI.succMsg('查询成功');
							var records=data.records;
							var t_masver="";
							var t_subver="";
							for (var i = 0; i < records.length; i++) {
								 t_masver="";
								 t_subver="";
								 if(records[i].masver!=null&&records[i].masver!=''){
									 t_masver= records[i].masver;
								 }
								 if(records[i].subver!=null&&records[i].subver!=''){
									 t_subver="("+records[i].subver+")";
								 }
			                   $("#versionNum1").append("<option value=" + records[i].uuid + ">" + t_masver+t_subver + "</option>");
			                }
						}else {
							YYUI.promAlert("查询失败："+data.msg);
						}
					},
					error : function(data) {
						layer.close(stationVersionLoading);
						YYUI.promAlert(YYMsg.alertMsg('sys-submit-http',null));
					}
				});
			});
			
			$('#masterCharger-select-btn').on('click', function() {
				layer.open({
					type : 2,
					title : '请选择人员',
					shadeClose : false,
					shade : 0.8,
					area : [ '1000px', '90%' ],
					content : '${ctx}/sys/ref/refUserSel?callBackMethod=window.parent.callBackMasterCharger'
				});
			});
			
			$('#stationCharger-select-btn').on('click', function() {
				layer.open({
					type : 2,
					title : '请选择人员',
					shadeClose : false,
					shade : 0.8,
					area : [ '1000px', '90%' ],
					content : '${ctx}/sys/ref/refUserSel?callBackMethod=window.parent.callBackStationCharger'
				});
			});
		});
		
		//回调选择
		function callBackMasterCharger(data){
			$("#masterChargerUuid").val(data.uuid);
			$("#masterChargerName").val(data.username);
		}
		
		//回调选择
		function callBackStationCharger(data){
			$("#stationChargerUuid").val(data.uuid);
			$("#stationChargerName").val(data.username);
		}
		
		//表单校验
		function validateForms(){
			validata = $('#yy-form-edit').validate({
				onsubmit : true,
				rules : {
					'stationName' : {required : true},
					'versionNum1' : {required : true},
					'name' : {required : true,maxlength : 50},
					'masterTestMsg' : {maxlength : 200},
					'stationTestPerson' : {maxlength : 20}
				}
			});
		}
		
		
		//主子表保存
		function onSave(isClose) {
			var subValidate=validOther();
			var mainValidate=$('#yy-form-edit').valid();
			if(!subValidate||!mainValidate){
				return false;
			}
			
			var subData = subData = {};
			
			$("#operType").val("0");//0保存 1：保存并提交
			
			var posturl = "${serviceurl}/add";
				
			var saveWaitLoad=layer.load(2);
			var opt = {
				url : posturl,
				type : "post",
				data : subData,
				success : function(data) {
					layer.close(saveWaitLoad);
					if (data.success == true) {
						hasLoadSub = false;
						YYUI.succMsg('保存成功!');
						window.parent.callbackOnAdd(data.records[0].uuid);
						closeEditView();
					} else {
						YYUI.promAlert("保存失败：" + data.msg);
					}
				}
			}
			$("#yy-form-edit").ajaxSubmit(opt);
		}
	</script>
</body>
</html>
