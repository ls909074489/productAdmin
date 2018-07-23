<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="serviceurl" value="${ctx}/info/purchase"/>
<html>
<head>
<title>间隔信息</title>
</head>
<body>
	<div id="yy-page" class="container-fluid page-container">
		<div class="page-content" id="yy-page-list">
			<div class="row yy-toolbar">
				<button id="yy-btn-add" class="btn blue btn-sm">
					<i class="fa fa-plus"></i> 新增
				</button>
				<button id="yy-btn-remove" class="btn red btn-sm">
					<i class="fa fa-trash-o"></i> 删除
				</button>
				<button id="yy-btn-refresh" class="btn blue btn-sm">
					<i class="fa fa-refresh"></i> 刷新
				</button>
				<button id="yy-btn-submit" class="btn yellow btn-sm btn-info">
					<i class="fa fa-send"></i> 工厂审核
				</button>
				<button id="yy-btn-sales" class="btn yellow btn-sm btn-info">
					<i class="fa fa-send"></i> 业务员审核
				</button>
			</div>
			<div class="row yy-searchbar form-inline">
				<form id="yy-form-query">
					<label for="search_LIKE_name" class="control-label">名称</label>
					<input type="text" autocomplete="on" name="search_LIKE_name"
						id="search_LIKE_name" class="form-control input-sm">
						
					<button id="yy-btn-search" type="button" class="btn btn-sm btn-info">
						<i class="fa fa-search"></i>查询
					</button>
					<button id="rap-searchbar-reset" type="reset" class="red">
						<i class="fa fa-undo"></i> 清空
					</button>
				</form>
			</div>
			<div class="row">
				<table id="yy-table-list" class="yy-table">
					<thead>
						<tr>
							<th style="width: 30px;">序号</th>
							<th class="table-checkbox">
								<input type="checkbox" class="group-checkable" data-set="#yy-table-list .checkboxes"/>
							</th>
							<th>操作</th>
							<th>创建时间</th>
							<th>申请状态</th>
							<th>名称</th>
							<th>备注</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
	</div>

	<!-- 公用脚本 -->
	<%@include file="/WEB-INF/views/common/listscript.jsp"%>
	<%@include file="/WEB-INF/views/common/commonscript_approve.jsp"%>

	<script type="text/javascript">
		_isNumber = true;
		var _tableCols = [ {
							data : null,
							orderable : false,
							className : "center",
							width : "50"
						},{
							data : "uuid",
							orderable : false,
							className : "center",
							/* visible : false, */
							width : "40",
							render : YYDataTableUtils.renderCheckCol
						},{
							data : "uuid",
							className : "center",
							orderable : false,
							render : YYDataTableUtils.renderActionCol,
							width : "50"
						},{
							data : "createtime",
							width : "10",
							className : "center",
							visible : false,
						},{
							data : 'billstatus',
							width : "80",
							className : "center",
							orderable : false,
							render : function(data, type, full) {
							   return '<a onclick="onApproveLook(\'OrderLog\',\''+full.uuid+'\');">'+YYDataUtils.getEnumName("BillApplyStatus", data)+'</a>';
							}
						},{
							data : "name",
							width : "40%",
							className : "center",
							orderable : true
						},{
							data : "memo",
							width : "30%",
							className : "center",
							orderable : true
						}];


		var _setOrder = [[3,'desc']];
		$(document).ready(function() {
			$('#yy-btn-sales').bind('click', function(){
				onApprovex(false);
			});//审核
			
			_queryData = $("#yy-form-query").serializeArray();
			bindListActions();
			serverPage(null);
		});
		
		function onApproveLook(billtype,billid){
			var link = '${ctx}/sys/message/msgList?msgtype=2&billtype='+billtype+"&billid="+billid;
			layer.open({
				type : 2,
				title : '<spring:message code="sys.comm.auditopinion"/>',<!-- '审批意见' -->
				shadeClose : true,
				shade : 0.8,
				area : [ '90%', '80%' ],
				content : link
			//iframe的url
			});
		}
		
		//删除前检查
		function checkDelete(pks) {
			if (pks.length < 1) {
				YYUI.promMsg(YYMsg.alertMsg('sys-delete-choose'));//"请选择需要删除的记录"
				return;
			}
			for (var i = 0; i < pks.length; i++) {
				var row = $("input[value='" + pks[i] + "']").closest("tr");
				var billstatus = _tableList.row(row).data().billstatus;
				console.info(billstatus);
				if (billstatus == undefined) {
					return true;
				}
				if (billstatus != 0) {
					YYUI.failMsg(YYMsg.alertMsg('sys-delete-check'));//存在不能删除的记录！"
					return false;
				}
			}
			return true;
		}
		
		//行修改   @data 行数据  @rowidx 行下标
		function onEditRow(aData, iDataIndex, nRow) {
			if (!onEditRowBefore(aData, iDataIndex, nRow)) {
				return;
			}
			if (aData.billstatus ==null || aData.billstatus == 10|| aData.billstatus == 15) {
				layer.open({
					type : 2,
					title : false,//标题
					shadeClose : false,//是否点击遮罩关闭
					shade : 0.8,//透明度
					closeBtn : 0,//关闭按钮
					area : [ '100%', '100%' ],
					content : '${serviceurl}/onEdit?uuid=' + aData.uuid + '&' + _editParam, //iframe的url
				});
			}else{
				YYUI.promMsg("当前状态不能修改");
				return;
			}
		}
		
		//提交
		function onSubmit() {
			var pks = YYDataTableUtils.getSelectPks();
			if (doBeforeSubmit(pks)) {
				submitRecord('${serviceurl}/factoryConfirm', pks, onRefresh);
			}
		}
		//提交前检查
		function checkSubmit(pks) {
			if (pks.length < 1) {
				YYUI.promMsg(YYMsg.alertMsg('sys-submit-choose'));//"请选择需要提交的记录"
				return;
			}
			for (var i = 0; i < pks.length; i++) {
				var row = $("input[value='" + pks[i] + "']").closest("tr");
				var billstatus = _tableList.row(row).data().billstatus;
				console.info(billstatus);
				if (billstatus != 1 && billstatus != 4) {
					YYUI.failMsg(YYMsg.alertMsg('sys-submit-relevance'));//"存在不能提交的记录！"
					//return false;
				}
			}
			return true;
		}
		
		
		
		function checkSales(pks) {
			if (pks.length < 1) {
				YYUI.promMsg("请选择需要审核的记录");
				return;
			}
			for (var i = 0; i < pks.length; i++) {
				var row = $("input[value='" + pks[i] + "']").closest("tr");
				var billstatus = _tableList.row(row).data().billstatus;
				console.info(billstatus);
				if (billstatus != 1 && billstatus != 4) {
					YYUI.failMsg(YYMsg.alertMsg('sys-submit-relevance'));//"存在不能提交的记录！"
					//return false;
				}
			}
			return true;
		}
		
		var _approveParam = {};
		//审核
		function onApprovex(isClose){
			console.info("doApprovex");
			var pks = YYDataTableUtils.getSelectPks();
			if(checkSales(pks)){
				var modal = $("#approveRemark");
				modal.find('#content').val("");
				modal.find('.modal-title').text("审核意见");
				modal.find('#approvepass').unbind("click");
				modal.find('#approvepass').bind("click",
						function() {
							modal.find('.error').text('');
							var content = modal.find('#content').val();
							if(content!=null && content.length>200){
								YYUI.failMsg("审核意见不能超过200个字符！");
								return;
							}
							
							var waitInfoLoading = layer.load(2);
							$.ajax({
								type : "POST",
								data :{"approveRemark": content,"pks":pks.toString()},
								url : "${serviceurl}/salesConfirm",
								async : true,
								dataType : "json",
								success : function(data) {
									layer.close(waitInfoLoading);
									console.info(data);
									if (data.success) {
										YYUI.succMsg('审核成功!');
										onRefresh(true);
									}else {
										YYUI.failMsg("审核出现错误：" + data.msg)
									}
								},
								error : function(data) {
									layer.close(waitInfoLoading);
									YYUI.promAlert(YYMsg.alertMsg('sys-submit-http',null));
								}
							});
						});
				modal.find('#approvenopass').unbind("click");
				modal.find('#approvenopass').bind("click",
						function() {
							modal.find('.error').text('');
							var content = modal.find('#content').val();
							if(content==null||content.length==0){
								YYUI.failMsg("退回意见不能为空！");
								return;
							}
							if(content!=null && content.length>200){
								YYUI.failMsg("退回意见不能超过200个字符！");
								return;
							}

							var waitInfoLoading = layer.load(2);
							$.ajax({
								type : "POST",
								data :{"approveRemark": content,"pks":pks.toString()},
								url : "${serviceurl}/salesReject",
								async : true,
								dataType : "json",
								success : function(data) {
									layer.close(waitInfoLoading);
									if (data.success) {
										$('#approveRemark').modal('hide');
										YYUI.succMsg('退回成功!');
										onRefresh(true);
									}else {
										YYUI.failMsg("退回出现错误：" + data.msg)
									}
								},
								error : function(data) {
									layer.close(waitInfoLoading);
									YYUI.promAlert(YYMsg.alertMsg('sys-submit-http',null));
								}
							});
						});
				$('#approveRemark').modal({
					toggle : "modal"
				});
			}
		}
	</script>
</body>
</html>	

