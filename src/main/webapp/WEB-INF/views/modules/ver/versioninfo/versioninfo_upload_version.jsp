<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serviceurl" value="${ctx}/ver/versioninfo"/>
<html>
<head>
<title>导入</title>
</head>
<body>
	<div id="yy-page" class="container-fluid page-container">
	<div class="page-content" id="yy-page-list">
			<div class="row yy-toolbar">
				<button id="yy-import-btn-confirm" class="btn blue btn-sm btn-info">
					提&nbsp;&nbsp;&nbsp;&nbsp;交
				</button>
				<button id="yy-btn-cancel" class="btn blue btn-sm btn-info">
					<i class="fa fa-rotate-left"></i> 取消
				</button>
			</div>
			<div class="row" style="margin-left: 20px;">
				<form id="yy-form-edit" >
					<table>
						<tr>
							<td style="color: #e02222;">类型</td>
							<td style="width: 180px;">
								<select class="yy-input-enumdata form-control" id="versionType1"
								 name="versionType1">
								 	<option value=""></option>
								 	<!-- <option value="1">设备</option> -->
								 	<option value="3">主站</option>
								 	<option value="4">标准</option>
								 	<option value="5">子站</option>
								 	<option value="6">典型</option>
								 </select>
							</td>
							<td style="color: #e02222;">版本标识</td>
							<td style="width: 180px;">
								<select class="yy-input-enumdata form-control" id="versionNum1" name="versionNum1">
									<option value=""></option>
								</select>
							</td>
						</tr>
					</table>
				</form>
			</div>
	</div>

		<script type="text/javascript">
			
			$(document).ready(function() {
				$("#versionType1").change(function(){
					if($(this).val()==1){//设备
						$("#versionNum1").empty();
						$("#deivceSelDiv").show();
					}else{
						$("#deivceSelDiv").hide();
						
						var stationVersionLoading = layer.load(2);
						$.ajax({
							type : "POST",
							data :{"tid": $(this).val(),"sid":'${currentStation.uuid}'},
							url : "${serviceurl}/dataByTidSid",
							async : true,
							dataType : "json",
							success : function(data) {
								$("#versionNum1").empty();
								$("#versionNum1").append("<option value=''></option>");
								layer.close(stationVersionLoading);
								if (data.success) {
									YYUI.succMsg('查询成功');
									var records=data.records;
									var t_subver="";
									for (var i = 0; i < records.length; i++) {
										 t_subver="";
										 if(records[i].subver!=null&&records[i].subver!=''){
											 t_subver="("+records[i].subver+")";
										 }
					                   $("#versionNum1").append("<option value=" + records[i].uuid + ">" + records[i].masver+t_subver + "</option>");
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
					}
				});				
				
				
				$("#yy-import-btn-confirm").bind('click', confirmImport);//确定导入
				$("#yy-btn-cancel").bind('click', function(){
					var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
					parent.layer.close(index); //再执行关闭 
				});
				
				//验证表单
				validateForms();
			});
			
			
			function validateForms(){
				$('#yy-form-edit').validate({
					rules : {
		           		'versionType1' : {required : true},
		           		'versionNum1' : {required : true}
					}	
				}); 
			}
			
			//确定导入
			function confirmImport(){
				var mainValidate=$('#yy-form-edit').valid();
				if(!mainValidate){
					return false;
				}
				var posturl = "${serviceurl}/uploadVersion";

				var saveWaitLoad=layer.load(2);
				var opt = {
					url : posturl,
					type : "post",
					success : function(data) {
						layer.close(saveWaitLoad);
						if (data.success == true) {
							layer.close(saveWaitLoad);
							data.fileName=$("#fileName").val();
							YYUI.succMsg("操作成功,重新加载页面。");
							var _method = '${callBackMethod}';
							if (_method) {
								eval(_method + "(data)");
							} else {
								window.parent.callBackMethod(data);
							}
							var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
							parent.layer.close(index); //再执行关闭 
						} else {
							layer.close(saveWaitLoad);
							YYUI.promAlert("操作错误：" + data.msg);
						}
					}
				}
				$("#yy-form-edit").ajaxSubmit(opt);
			}
		</script>
	</div>
</body>
</html>