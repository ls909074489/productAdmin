<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serviceurl" value="${ctx}/ver/versioninfosub"/>
<html>
<head>
<title>查看</title>
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
			<div class="row" style="">
				<form id="yy-form-edit" >
					<input name="uuid" type="hidden" value="${entity.uuid}">
					<table class="yy-table">
						<thead>
							<tr>
								<th style="width: 100px;">
								<button id="addNewSub" class="btn blue btn-sm btn-info" type="button">
									<i class="fa fa-plus"></i> 添加
								</button>
								</th>
								<c:forEach items="${colsData.linksList}" var="list">
									<c:if test="${list.isDisplay eq 1}">
										<th>${list.columnAnno}</th>
									</c:if>
								</c:forEach>
							</tr>
						</thead>
						<tbody id="yy-table-linksBody">
						</tbody>
					</table>
				</form>
			</div>
	</div>

		<script type="text/javascript">
			
			var jsonResp = jQuery.parseJSON('${colsJson}');
		
			$(document).ready(function() {
				var arr=jQuery.parseJSON('${linksArr}');
				if(arr!=null&&arr.length>0){
					var trHtml="";
					for(var j=0;j<arr.length;j++){
						trHtml+="<tr style='text-align: center;'><td><input type='hidden' class='form-control' name='rowindex' value='1'/></td>";
						if(jsonResp){
							var cosList=jsonResp.linksList;
							if(cosList!=null&&cosList.length>0){
								for (i = 0; i < cosList.length; i++) {
									if(cosList[i].isDisplay=='1'){
										var tColName=cosList[i].columnName;
										if(typeof (arr[j][tColName]) != "undefined"){
											trHtml+=("<td><input type='text' class='form-control' name='"+tColName+"' value='"+arr[j][tColName]+"'/></td>");
										}else{
											trHtml+=("<td><input type='text' class='form-control' name='"+tColName+"' value=''/></td>");
										}
										
									}
								}
							}
						}
						trHtml+="</tr>";
					}
					$("#yy-table-linksBody").html(trHtml);
				}else{
					$("#yy-table-linksBody").html();
				}
				
				$("#yy-btn-cancel").bind('click', function(){
					var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
					parent.layer.close(index); //再执行关闭 
				});
				$("#yy-btn-save").bind('click', onSave);//确定导入
				
				var addHtml="<tr style='text-align: center;'><td><input type='hidden' class='form-control' name='rowindex' value='1'/></td>";
				if(jsonResp){
					var cosList=jsonResp.linksList;
					if(cosList!=null&&cosList.length>0){
						for (i = 0; i < cosList.length; i++) {
							if(cosList[i].isDisplay=='1'){
								var tColName=cosList[i].columnName;
								addHtml+=("<td><input type='text' class='form-control' name='"+tColName+"' value=''/></td>");
							}
						}
					}
				}
				addHtml+="</tr>";
				$("#addNewSub").bind('click', function(){
					$("#yy-table-linksBody").append(addHtml);
				});
			});
			

			//确定导入
			function onSave(){
				var mainValidate=$('#yy-form-edit').valid();
				if(!mainValidate){
					return false;
				}
				var posturl = "${serviceurl}/saveSubLink";

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