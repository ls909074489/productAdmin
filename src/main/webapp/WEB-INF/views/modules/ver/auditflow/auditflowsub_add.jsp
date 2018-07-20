<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serviceurl" value="${ctx}/ver/auditflowSub"/>
<html>
<head>
<title>审核子表</title>
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
			<input name="main.uuid" type="hidden" class="form-control" value="${mainid}">
			<input name="type" type="hidden" class="form-control" value="${type}">
			<table style="width: 95%;">
				<%-- <tr>
					<td>事件类型</td>
					<td>
						<input name="eventType" type="text" class="form-control" value="${entity.eventType}">
					</td>
					<td>描述</td>
					<td>
						<input name="description" type="text" class="form-control" value="${entity.description}">
					</td>
				</tr> --%>
				
				<tr>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;模拟读取内容</td>
					<td style="width: 360px;">
						<textarea name="content" class="form-control" rows="5">{ "col1": 2,"col2": 2,"total": 2,"col3": 1,"pageNumber": 1,"success": true,"msg": ""}</textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 公用脚本 -->
	<script type="text/javascript">
		$(document).ready(function() {
			//按钮绑定事件
			bindButtonAction();
			validateForms();
			setValue();
			
			$("#yy-btn-cancel").bind('click', closeDialog);
			
			$("#yy-btn-save").bind("click", function() {
				onSave(isClose);
			});
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
			
		}
		
		//保存
		var isClose=true;
	 	function onSave(isClose) {
			if (!$('#yy-form-edit').valid()) return false;

			var editview = layer.load(2);
			
			var posturl = "${serviceurl}/saveSub";
			var opt = {
				url : posturl,
				type : "post",
				success : function(data) {
					if (data.success) {
						layer.close(editview);
						window.parent.YYUI.succMsg('保存成功!');
						window.parent.callBackRefreshSub('${type}');
						var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
						parent.layer.close(index); //再执行关闭 
					} else {
						window.parent.YYUI.failMsg("保存失败：" + data.msg);
						layer.close(editview);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					window.parent.YYUI.promAlert("保存失败，HTTP错误。");
					layer.close(editview);
				}
			}
			$("#yy-form-edit").ajaxSubmit(opt);
		} 
	 	
		//取消编辑视图
		function closeDialog() {
			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			parent.layer.close(index); //再执行关闭 
		}
	 	
		//表单校验
		function validateForms(){
			validata = $('#yy-form-edit').validate({
				onsubmit : true,
				rules : {
					'eventType' : {required : true,maxlength : 100},
					'description' : {required : true,maxlength : 100}
				}
			});
		}
		
	</script>
</body>
</html>
