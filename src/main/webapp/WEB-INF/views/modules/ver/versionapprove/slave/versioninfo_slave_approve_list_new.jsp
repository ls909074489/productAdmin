<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="serviceurl" value="${ctx}/ver/slaveapprove"/>
<html>
<head>
<title>信息点信息审批</title>
</head>
<body>
	<div id="yy-page" class="container-fluid page-container">
		<div class="page-content" id="yy-page-list">
			<div class="row yy-toolbar">
				<button id="yy-btn-refresh" class="btn blue btn-sm">
					<i class="fa fa-refresh"></i> 刷新
				</button>
				<button id="yy-btn-export" queryformId="yy-form-query" class="btn green btn-sm">
					<i class="fa fa-chevron-up"></i> 导出
				</button>
					
				<select class="yy-input-enumdata"  style="height: 26px;" id="ntype"  name="ntype" data-enum-group="exportVersionFileType">
				</select>	
			</div>
			<div class="row yy-searchbar form-inline">
				<form id="yy-form-query">
					<%-- <input type="hidden" autocomplete="on" name="search_EQ_type"
						id="search_EQ_type" class="form-control input-sm" value="${type}"> --%>
						
					<label for="search_EQ_billstatus" class="control-label">审批状态</label>
					<select class="yy-input-enumdata form-control" id="search_EQ_billstatus" 
						name="search_EQ_billstatus" data-enum-group="deviceApproveListBillStatus"></select>
						
					<!-- <label for="search_EQ_masver" class="control-label">设备名称</label>
					<input type="text" autocomplete="on" name="search_LIKE_device.name"
						id="search_LIKE_device.name" class="form-control input-sm"> -->
						
					<label for="search_EQ_masver" class="control-label">主版本号</label>
					<input type="text" autocomplete="on" name="search_EQ_masver"
						id="search_EQ_masver" class="form-control input-sm">
						
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
							<th>审批状态</th>
							<th>创建时间</th>
							<th>类型</th>
							<th>主版本号</th>
							<th>标注</th>
							<th>提交时间</th>
							<th>是否审核</th>
							<th>版本文件</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
	</div>

	<!-- 公用脚本 -->
	<%@include file="/WEB-INF/views/common/listscript.jsp"%>

	<c:choose>
		<c:when test="${hasSelStation eq 0}">
			<script type="text/javascript">
				layer.open({
					title:"请选择",
				    type: 2,
				    area: ['500px', '250px'],
				    shadeClose : false,
					shade : 0.8,
					closeBtn : 0,
				    content: '${ctx}/sys/org/toChangeStation'
				});
			</script>
		</c:when>
	</c:choose>
	<script type="text/javascript">
		//回到选择当前的厂商纷纷
		function callSelectSessionStation(data){
			onRefresh();
		}
		
		_isNumber = true;
		_editParam = 'type=${type}';
		_addParam = 'type=${type}';
		var _tableCols = [ {
							data : null,
							orderable : false,
							className : "center",
							width : "30"
						},{
							data : "uuid",
							orderable : false,
							className : "center",
							/* visible : false, */
							width : "30",
							render : YYDataTableUtils.renderCheckCol
						},{
							data : "uuid",
							className : "center",
							orderable : false,
							render : YYDataTableUtils.renderViewEditActionCol,
							width : "50"
						}, {
							data : 'billstatus',
							width : "60",
							orderable : false,
							className : "center",
							render : function(data, type, full) {
								if(data!=null){
									return '<a onclick="onApproveLook(\'VersionInfoLog\',\''+full.uuid+'\');">'+YYDataUtils.getEnumName("BillStatus", data)+'</a>';
								}else{
									return "";
								}
							}
						},{
							data : "createtime",
							width : "10%",
							className : "center",
							orderable : true,
							visible : false
						},{
							data : "type",
							width : "50",
							className : "center",
							orderable : true,
							render : function(data, type, full) {
							       return YYDataUtils.getEnumName("VersionType", data);
							}
						},{
							data : "masver",
							width : "10%",
							className : "center",
							orderable : true
						},{
							data : "subver",
							width : "10%",
							className : "center",
							orderable : true
						},{
							data : "submitTime",
							width : "10%",
							className : "center",
							orderable : true
						},{
							data : "isCheck",
							width : "10%",
							className : "center",
							orderable : true,
							render : function(data, type, full) {
							       return YYDataUtils.getEnumName("BooleanType", data);
							}
						},{
							data : "pkgPathName",
							width : "30",
							className : "left",
							orderable : true,
							render : function(data, type, full) {
								if(full.pkgPath!=null&&full.pkgPath!=''){
									return "<button type='button' class='btn btn-xs btn-info' data-rel='tooltip' title='下载' onclick=\"downLoadVerFile('"+full.uuid+"');\">"+
									"<input id=\""+full.uuid+"\" type=\"hidden\" value=\""+full.pkgPath+"\">"+
									"<i class='fa fa-download'></i></button><a  onclick=\"downLoadVerFile('"+full.uuid+"');\">"+data+"</a>";
								}else{
									return '';
								}
							}
						}];


		var _setOrder = [[4,'desc']];
		$(document).ready(function() {
			$('#ntype option:eq(1)').attr('selected','selected');
			
			_queryData = $("#yy-form-query").serializeArray();
			bindListActions();
			serverPage("${serviceurl}/dataSlaveApprove?approveType=${approveType}");
			
			$("#yy-btn-export").bind('click', function(){
				var pks = YYDataTableUtils.getSelectPks();
				if(pks==""){
					YYUI.failMsg("请选择导出数据");
					return;
				}
				var t_ntype=$("#ntype").val();
				if(t_ntype==null||t_ntype==''){
					window.open('${ctx}/ver/versioninfo/exportByPks?pks='+pks.toString(),"_blank");
				}else{
					if(pks.length>1){
						YYUI.promMsg('只能选择一个进行导出');
					}else{
						onExportWithType(pks.toString(),t_ntype);						
					}
				}
			});

		});
		
		//导出
		function onExportWithType(id,ntype){
			var downLoadLoading = layer.load(2);
			$.ajax({
				type : "POST",
				data :{"mainId": id,"ntype":ntype},
				url : "${ctx}/ver/versioninfo/exportCsvByMainId",
				async : true,
				dataType : "json",
				success : function(data) {
					layer.close(downLoadLoading);
					console.info(data);
					if (data.success) {
						YYUI.succMsg(YYMsg.alertMsg('sys-success-todo',['导出CSV']));
						if(data.records!=null&&data.records.length>0){
							YYUI.succMsg(YYMsg.alertMsg('sys-success-todo',['导出CSV']));
							for(var i=0;i<data.records.length;i++){
								window.open('${ctx}/ver/versioninfo/exportCscFile?fileName='+data.records[i],"_blank"); 
							}
						}else{
							YYUI.promAlert("生成CSV文件路径为空");
						}
					}else {
						YYUI.promAlert(YYMsg.alertMsg('sys-fail-todo',['导出CSV'])+data.msg);
					}
				},
				error : function(data) {
					layer.close(downLoadLoading);
					YYUI.promAlert(YYMsg.alertMsg('sys-submit-http',null));
				}
			});
		}
		
		//行修改   @data 行数据  @rowidx 行下标
		function onEditRow(aData, iDataIndex, nRow) {
			if (!onEditRowBefore(aData, iDataIndex, nRow)) {
				return;
			}
			if (aData.billstatus !=null && aData.billstatus == '3') {
				layer.open({
					type : 2,
					title : false,//标题
					shadeClose : false,//是否点击遮罩关闭
					shade : 0.8,//透明度
					closeBtn : 0,//关闭按钮
					area : [ '100%', '100%' ],
					content : '${serviceurl}/onEditApproveSlave?uuid=' + aData.uuid + '&' + _editParam+"&approveType=${approveType}", //iframe的url
				});
			}else{
				YYUI.promMsg("审批状态为审核中的才能审批");//已经提交或者审核的数据不能修改。
				return;
			}
		}
		//行查看 @data 行数据 @rowidx 行下标
		function onViewDetailRow(data, rowidx, row) {
			layer.open({
				type : 2,
				title : false,//标题
				shadeClose : false,//是否点击遮罩关闭
				shade : 0.8,//透明度
				closeBtn : 0,//关闭按钮
				area : [ '100%', '100%' ],
				content : '${serviceurl}/onDetailApproveSlave?uuid=' + data.uuid + '&' + _detailParam+"&approveType=${approveType}", //iframe的url
			});
		}
		
		//查看审批记录
		function onApproveLook(billtype,billid){
			var link = '${ctx}/sys/message/msgList?msgtype=2&billtype='+billtype+"&billid="+billid;
			layer.open({
				type : 2,
				title : '审批意见',
				shadeClose : true,
				shade : 0.8,
				area : [ '90%', '80%' ],
				content : link
			//iframe的url
			});
		}
		
		//下载附件
		function downLoadVerFile(uuid){
			window.open('${ctx}/ver/versioninfo/downloadVerFile?uuid='+uuid,"_blank");
		}
	</script>
</body>
</html>	

