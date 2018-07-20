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
			<div class="" id="approveRemark">
				  <div class="">
				    <div class="">
				      <div class="modal-body">
				        <form id="yy-form-edit">
				        	<input type="hidden" name="uuid" value="${uuid}"/>
				          <div class="form-group">
				            <textarea rows="5" class="form-control" id="content" name="content"></textarea>
				          </div>
				        </form>
				      </div>
				      <div class="modal-footer">
				      	<button type="button" class="btn btn-primary" id="approvepass">提交</button>
				      	<!-- <button type="button" class="btn red btn-primary" id="approvenopass">退回</button> -->
				        <button type="button" class="btn btn-default" data-dismiss="modal" id="closeBtn">关闭</button>
				      </div>
				    </div>
				  </div>
				</div>
	</div>

		<script type="text/javascript">
			
			$(document).ready(function() {
				
				$("#approvepass").bind('click', onSave);
				
				$("#closeBtn").bind('click', closeDialog);
			});
			
			function onSave() {
				// layer.confirm("确定要${operStr}吗？", function(index) {
				layer.confirm("确定提交吗？", function(index) {

					layer.close(index);
					
					var editview = layer.load(2);
					var opt = {
						url : "${formAction}",
						type : "post",
						success : function(data) {
							if (data.success) {
								layer.close(editview);
								window.parent.YYUI.succMsg('保存成功!');
								var _method='${callBackMethod}';
								if(_method){
						        	eval(_method+"(data)"); 
								}else{
									window.parent.callBackSelect(aData);
								}
							} else {
								//window.parent.YYUI.failMsg("保存出现错误：" + data.msg);
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
				});	
			}
			
			function closeDialog(){
				var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
				parent.layer.close(index); //再执行关闭 
			}
		</script>
	</div>
</body>
</html>