<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serviceurlsub" value="${ctx}/ver/stationapprove"/>
<html>
<head>
<title>导入</title>
</head>
<body>
	<div id="yy-page" class="container-fluid page-container">
	<div class="page-content" id="yy-page-list">
			<div class="row yy-toolbar">
				<button id="yy-btn-save" class="btn blue btn-sm">
					<i class="fa fa-save"></i> 保存
				</button>
				<button id="yy-btn-cancel" class="btn blue btn-sm">
					<i class="fa fa-rotate-left"></i> 取消
				</button>
			</div>
			<div class="row" style="margin-left: 50px;">
				<form id="yy-form-edit" >
					<table style="">
					<input name="uuid" id="uuid" type="text" class="hide" value="${subEntity.uuid}">
					<input name="type" id="type" type="text" class="hide" value="${type}">
					<input name="mainindex" id="mainindex" type="text" class="hide" value="${mainindex}">
					<c:forEach items="${colsData.yxList}" var="list">
						<c:if test="${list.isDisplay eq 1}">
							<tr style="height: 52px;">
								<td>${list.columnAnno}</td>
								<td><input type="text" name="${list.columnName}" class="form-control jsonDisplaceEle" style="margin-left: 20px;"></td>
							</tr>
						</c:if>
					</c:forEach>
						<tr style="height: 52px;">
							<td>备注</td>
							<td>
								<textarea name="versionmemo" class="form-control" style="margin-left: 20px;">${subEntity.versionmemo}</textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>
	</div>

		<script type="text/javascript">
			
			$(document).ready(function() {
				
				$("#yy-btn-save").bind("click", function() {
					onSave();
				});
				
				$("#yy-btn-cancel").bind('click', function(){
					var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
					parent.layer.close(index); //再执行关闭 
				});
				
				var subContent='${subEntity.content}';

				if(subContent!=null&&subContent!=''){
					var jsonData = jQuery.parseJSON(subContent);//数据库保存的信息
					
					$(".jsonDisplaceEle").each(function(){
						$(this).val(jsonData[$(this).attr("name")]);
					});
				}
				 //验证表单
				validateForms();
			});
			
			
			//确定导入
			
			function validateForms(){
				$('#yy-form-edit').validate({
					rules : {
						'pointno' : {required : true,digits : true,maxlength : 8},
		           		'versionmemo' : {maxlength : 200}
					}	
				}); 
			}
			
			
			function onSave(isClose) {
				if (!$('#yy-form-edit').valid()) return false;
				
		        var editview = layer.load(2);
		        
				var opt = {
					url : "${serviceurlsub}/saveQuestionSub",
					type : "post",
					success : function(data) {
						if (data.success) {
							layer.close(editview);
							window.parent.YYUI.succMsg('保存成功!');
							var subType=data.records[0].type;
							if(subType=='yx'){
								window.parent.loadSubYaoxin(data.records[0].mainindex);
							}else if(subType=='yc'){
								window.parent.loadSubYaoce(data.records[0].mainindex);
							}else if(subType=='yk'){
								window.parent.loadSubYaokong(data.records[0].mainindex);
							}else if(subType=='yt'){
								window.parent.loadSubYaotiao(data.records[0].mainindex);
							}else if(subType=='tx'){
								window.parent.loadSubYaoxin(data.records[0].mainindex);
							}else if(subType=='tc'){
								window.parent.loadSubYaoce(data.records[0].mainindex);
							}else if(subType=='tk'){
								window.parent.loadSubYaokong(data.records[0].mainindex);
							}else if(subType=='tt'){
								window.parent.loadSubYaotiao(data.records[0].mainindex);
							}
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
		</script>
	</div>
</body>
</html>