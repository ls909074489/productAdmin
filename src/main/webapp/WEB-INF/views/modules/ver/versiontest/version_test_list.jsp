<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="serviceurl" value="${ctx}/ver/versionTest"/>
<html>
<head>
<title>信息点表点号测试</title>
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
				<!-- <button id="yy-btn-submit" class="btn yellow btn-sm btn-info">
					<i class="fa fa-send"></i> 提交
				</button> -->
				<button id="yy-btn-refresh" class="btn blue btn-sm">
					<i class="fa fa-refresh"></i> 刷新
				</button>
				<!-- <button id="yy-btn-export" queryformId="yy-form-query" class="btn green btn-sm">
					<i class="fa fa-chevron-up"></i> 导出
				</button>
				
				<select class="yy-input-enumdata"  style="height: 26px;" id="ntype"  name="ntype" data-enum-group="exportVersionFileType">
				</select>	 -->	
			</div>
			<div class="row yy-searchbar form-inline">
				<form id="yy-form-query">
					<input type="hidden" autocomplete="on" name="search_EQ_type"
						id="search_EQ_type" class="form-control input-sm" value="${type}">
						
					<!-- <label for="search_EQ_billstatus" class="control-label">审批状态</label>
					<select class="yy-input-enumdata form-control" id="search_EQ_billstatus" 
						name="search_EQ_billstatus" data-enum-group="BillStatus"></select> -->
						
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
							<th>名称</th>
							<th>版本号</th>
							<th>核对日期</th>
							<th>主站核对负责人</th>
							<th>主站核对人</th>
							<th>测试方式</th>
							<th>主站测试结论</th>
							<th>厂站测试人员</th>
							<th>创建时间</th>
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
			width : "20"
		},{
			data : "uuid",
			orderable : false,
			className : "center",
			/* visible : false, */
			width : "20",
			render : YYDataTableUtils.renderCheckCol
		},{
			data : "uuid",
			className : "center",
			orderable : false,
			render : YYDataTableUtils.renderActionCol,//
			width : "50"
		},{
			data : "name",
			width : "50",
			className : "center",
			orderable : true/* ,
			render : function(data, type, full) {
			       return YYDataUtils.getEnumName("VersionType", data);
			} */
		},{
			data : "versioninfo",
			width : "30",
			className : "center",
			orderable : true,
			render : function(data, type, full) {
			       if(data!=null){
			    	   var vtype=YYDataUtils.getEnumName("VersionType", data.type);
			    	   if(YYDataUtils.getEnumName("VersionType", data.type)!=null){
			    		   vtype="("+vtype+")";
			    	   }else{
			    		   vtype="";
			    	   }
			    	   var tsubver=data.type;
			    	   if(tsubver!=null&&tsubver!=""){
			    		   tsubver="-"+tsubver;
			    	   }else{
			    		   tsubver="";
			    	   }
			    	   return vtype+data.masver+tsubver;
			       }else{
			    	   return "";
			       }
			}
		},{
			data : "checkingDate",
			width : "30",
			className : "center",
			orderable : true
		},{
			data : "masterCharger",
			width : "50",
			className : "center",
			orderable : true,
			render : function(data, type, full) {
			       if(data!=null){
			    	   return data.username;
			       }else{
			    	   return "";
			       }
			}
		},{
			data : "stationCharger",
			width : "50",
			className : "center",
			orderable : true,
			render : function(data, type, full) {
			       if(data!=null){
			    	   return data.username;
			       }else{
			    	   return "";
			       }
			}
		},{
			data : "testMethod",
			width : "10%",
			className : "center",
			orderable : true
		},{
			data : "masterTestMsg",
			width : "10%",
			className : "center",
			orderable : true
		},{
			data : "stationTestPerson",
			width : "10%",
			className : "center",
			orderable : true
		},{
			data : "createtime",
			width : "10%",
			className : "center",
			orderable : true
		}];


		var _setOrder = [[11,'desc']];
		$(document).ready(function() {
			_queryData = $("#yy-form-query").serializeArray();
			bindListActions();
			serverPage(null);
			
			$("#yy-btn-export").bind('click', function(){
				var pks = YYDataTableUtils.getSelectPks();
				if(pks==""){
					YYUI.failMsg("请选择导出数据");
					return;
				}
				var t_ntype=$("#ntype").val();
				if(t_ntype==null||t_ntype==''){
					window.open('${serviceurl}/exportByPks?pks='+pks.toString(),"_blank");
				}else{
					if(pks.length>1){
						YYUI.promMsg('只能选择一个进行导出');
					}else{
						onExportWithType(pks.toString(),t_ntype);						
					}
				}
			});
		});
		
		//增加
		function onAdd() {
			if (!onAddBefore()) {
				return false;
			}
			layer.open({
				title:"新增",
				type : 2,
				title : false,//标题
				shadeClose : false,//是否点击遮罩关闭
				shade : 0.8,//透明度
				/* closeBtn : 0,//关闭按钮 */
				area : [ '90%', '300px' ],
				content : '${serviceurl}/onAdd?'+ _addParam, //iframe的url
			});
			onAddAfter();
		}
		
		
		//新增回调
		function callbackOnAdd(uuid){
			layer.open({
				type : 2,
				title : false,//标题
				shadeClose : false,//是否点击遮罩关闭
				shade : 0.8,//透明度
				closeBtn : 0,//关闭按钮
				area : [ '100%', '100%' ],
				content : '${serviceurl}/onEdit?uuid=' + uuid + '&' + _editParam, //iframe的url
			});
		}
		
		
		//导出
		function onExportWithType(id,ntype){
			var downLoadLoading = layer.load(2);
			$.ajax({
				type : "POST",
				data :{"mainId": id,"ntype":ntype},
				url : "${serviceurl}/exportCsvByMainId",
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
	</script>
</body>
</html>	

