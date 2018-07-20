<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="page-content" id="yy-page-list" style="" align="center">
		<h3>add by ls2008</h3>
		<hr>
		<input type="button" value="promMsg" onclick="YYUI.promMsg('hello world');"/>  YYUI.promMsg('hello world');
		<hr>
		<input type="button" value="promMsg" onclick="YYUI.promMsg('hello world',1000);"/>  YYUI.promMsg('hello world',1000);
		<hr>
		<input type="button" value="succMsg" onclick="YYUI.succMsg('操作成功!');"/>  YYUI.succMsg('操作成功!');
		<hr>
		<input type="button" value="failMsg" onclick="YYUI.failMsg('操作失败!');"/>  YYUI.failMsg('操作失败!');
		<hr>
		<input type="button" value="promAlert" onclick="YYUI.promAlert('操作失败!');"/>  YYUI.promAlert('操作失败!');
		<hr>
		
		////////////////////////////////////////////////////////////////////////////////////////////
		var listWaitLoad=layer.load(2);
		layer.close(listWaitLoad);
		
		////////////////////////////////////////////////////////////////////////////////////////////		
		
		window.open('${ctx}${templatePath}',"_blank");
		
		////////////////////////////////////////////////////////////////////////////////////////////
		window.open('${serviceurl}/exportExamResult?'+$("#yy-form-subquery").serialize(),"_blank"); 
		window.open('${serviceurl}/exportByCourseId?courseId=${entity.course.uuid}',"_blank"); 
		
		////////////////////////////////////////////////////////////////////////////////////////////
		layer.open({
					title:"选择接单组",
				    type: 2,
				    area: ['500px', '400px'],
				    shadeClose : false,
					shade : 0.8,
				    content: '${serviceurl}/listSendUser?complainWorkId=${entity.uuid}'
				});
				
		////////////////////////////////////////////////////////////////////////////////////////////	
		layer.confirm("快速受理将直接派单到自己并受理，是否确实要受理此工单？", function(index) {
				layer.close(index);
				
		});		
		
		////////////////////////////////////////////////////////////////////////////////////////////
		
		$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
			     //当切换tab时，强制重新计算列宽
			     $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
			} );
		
		////////////////////////////////////////////////////////////////////////////////////////////
		var waitInfoLoading = layer.load(2);
			$.ajax({
				type : "POST",
				data :{"uuid": '${entity.uuid}'},
				url : "${serviceurl}/delExInfo",
				async : true,
				dataType : "json",
				success : function(data) {
					layer.close(waitInfoLoading);
					console.info(data);
					if (data.success) {
						YYUI.succMsg(YYMsg.alertMsg('sys-success-todo',['撤销快递单']));
					}else {
						YYUI.promAlert(YYMsg.alertMsg('sys-fail-todo',['撤销快递单'])+data.msg);
					}
				},
				error : function(data) {
					layer.close(waitInfoLoading);
					YYUI.promAlert(YYMsg.alertMsg('sys-submit-http',null));
				}
			});
			
			
			$("#expressCorp").change(function(){
			if('${defaultEx}'==$(this).val()){
				$("#expressNumber").val();
				$("#expressNumber").attr("readonly","readonly");
			}else{
				 $("#expressNumber").removeAttr("readonly");
			}
		});
		
		_subEditTableList.column(4).visible(false);
</div>

	
