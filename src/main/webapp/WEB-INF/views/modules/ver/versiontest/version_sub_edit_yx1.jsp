<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serviceurl" value="${ctx}/ver/versionTest"/>
<c:set var="serviceurlsub" value="${ctx}/ver/versionTestSub"/>
<html>
<head>
<title>点号测试</title>
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
			<span style="float: right;font-weight: bold;font-size: 16px;padding-top: 5px;">通信管理机A&nbsp;&nbsp;</span>
			<span style="float: right;font-weight: bold;font-size: 16px;padding-top: 5px;display: none;">系统误差值${absVal}&nbsp;&nbsp;</span>
		</div>
	<div style="width: 780px;">
		<form id="yy-form-edit" class="form-horizontal yy-form-edit">
			<input name="uuid" type="hidden" value="${entity.uuid}">
			
			<table style="margin-left: 20px;" class="yy-table">
				<tr>
					<th style="width: 60px;text-align:center;">是否合格</th>
					<th style="width: 150px;text-align:center;">测试时间</th>
					<th style="width: 60px;text-align:center;">备注</th>
				</tr>
				<tr>
					<td>
						<fieldset>
							<select name="isPass1" class="yy-input-enumdata form-control" data-enum-group="BooleanType"></select>
						</fieldset>
					</td>
					<td>
						<input type="text" name="masterTime1" id="masterTime1" value="<fmt:formatDate value='${entity.masterTime1}' pattern='yyyy-MM-dd HH:mm:ss'/>" 
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate form-control">
					</td>
					<td>
						<input type="text" name="memo1" id="memo1" value="${entity.memo1}" class="form-control">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 公用脚本 -->
	<%@include file="/WEB-INF/views/common/editscript.jsp"%>
	<script type="text/javascript">
	
		
		$(document).ready(function() {
			//按钮绑定事件
			bindEditActions();
			validateForms();
			$("select[name='isPass1']").val('${entity.isPass1}');
		});
		

		//表单校验
		function validateForms(){
			validata = $('#yy-form-edit').validate({
				onsubmit : true,//digits
				rules : {
					'masterVal1' : {/* required : true, */number : true},
					'stationVal1' : {/* required : true, */number : true},
					'memo1' : {maxlength : 200}
				}
			});
		}
		
		function changeVal(){
			var masterVal1=$("#masterVal1").val();
			var stationVal1=$("#stationVal1").val();
			if(masterVal1==null||masterVal1==""){
				masterVal1=0;
			}
			if(stationVal1==null||stationVal1==""){
				stationVal1=0;
			}
			var pageAbs=Math.abs(masterVal1-stationVal1);
			$("#absVal1").val(pageAbs);
			var absVal='${absVal}';
			if(pageAbs>absVal){
				$("select[name='isPass1']").val('0');
			}else{
				$("select[name='isPass1']").val('1');
			}
		}
		
		//主子表保存
		function onSave(isClose) {
			var mainValidate=$('#yy-form-edit').valid();
			if(!mainValidate){
				return false;
			}
			
			var posturl = "${serviceurl}/${saveMethod}";
				
			var saveWaitLoad=layer.load(2);
			var opt = {
				url : posturl,
				type : "post",
				success : function(data) {
					layer.close(saveWaitLoad);
					if (data.success == true) {
						hasLoadSub = false;
						YYUI.succMsg('保存成功!');
						var type='${entity.type}';
						if(type=='yx'||type=='tx'){
							window.parent._subYaoxinTableList.draw(); //服务器分页
						}else if(type=='yc'||type=='tc'){
							window.parent._subYaoceTableList.draw(); //服务器分页
						}else if(type=='yk'||type=='tk'){
							window.parent._subYaokongTableList.draw(); //服务器分页
						}else if(type=='yt'||type=='tt'){
							window.parent._subYaotiaoTableList.draw(); //服务器分页
						}
						closeEditView();
						window.location.reload();
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
