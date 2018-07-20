<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serviceurl" value="${ctx}/ver/auditflow"/>
<c:set var="serviceurlsub" value="${ctx}/ver/auditflowSub"/>
<html>
<head>
<title>审核流程表</title>
</head>
<body>
	<div id="yy-page-detail" class="container-fluid page-container page-content" >
		<div class="row yy-toolbar">
			<button id="yy-btn-backtolist" class="btn blue btn-sm">
				<i class="fa fa-rotate-left"></i> 返回
			</button>
		</div>
	<div>
		<form id="yy-form-detail" class="form-horizontal yy-form-detail">
			<fieldset disabled="disabled">
			<input name="uuid" type="text" class="hide" value="${entity.uuid}">
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">项目名称</label>
						<div class="col-md-8">
							<input name="name" type="text" class="form-control" value="${entity.name}">
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">主版本号</label>
						<div class="col-md-8">
							<input name="masver" type="text" class="form-control" value="${entity.masver}">
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">标注</label>
						<div class="col-md-8">
							<input name="subver" type="text" class="form-control" value="${entity.subver}">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label col-md-2">审核内容</label>
						<div class="col-md-10">
							<label><input name="yx" type="checkbox" value="1" class="" style="width: 20px;height: 20px;" 
								<c:if test="${entity.yx eq 1}">checked="checked"</c:if>/>
								遥信&nbsp;&nbsp;&nbsp;&nbsp;
							</label> 
							<label><input name="yc" type="checkbox" value="1" class="" style="width: 20px;height: 20px;" 
								<c:if test="${entity.yc eq 1}">checked="checked"</c:if>/>
								遥测&nbsp;&nbsp;&nbsp;&nbsp;
							</label>
							<label><input name="yk" type="checkbox" value="1" class="" style="width: 20px;height: 20px;" 
								<c:if test="${entity.yk eq 1}">checked="checked"</c:if>/>
								遥控 &nbsp;&nbsp;&nbsp;&nbsp;
							</label>
							<label><input name="yt" type="checkbox" value="1" class="" style="width: 20px;height: 20px;" 
								<c:if test="${entity.yt eq 1}">checked="checked"</c:if>/>
								遥调 &nbsp;&nbsp;&nbsp;&nbsp;
							</label>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">审核人员</label>
						<div class="col-md-8">
							<div class="input-group input-icon right">
									<input id="auditorUserUuid" name="auditorUser.uuid" type="hidden" value="${entity.auditorUser.uuid}"> 
									<i class="fa fa-remove" onclick="cleanDef('auditorUserUuid','auditorUserName');" title="清空"></i>
									<input id="auditorUserName" name="auditorUserName" type="text" class="form-control" readonly="readonly" 
										value="${entity.auditorUser.username}">
									<span class="input-group-btn">
										<button id="auditorUser-select-btn" class="btn btn-default btn-ref" type="button">
											<span class="glyphicon glyphicon-search"></span>
										</button>
									</span>
								</div>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">审批组</label>
						<div class="col-md-8">
							<div class="input-group input-icon right">
									<input id="approverUserUuid" name="approverUserGroup.uuid" type="hidden" value="${entity.approverUserGroup.uuid}"> 
									<i class="fa fa-remove" onclick="cleanDef('approverUserUuid','approverUserName');" title="清空"></i>
									<input id="approverUserName" name="approverUserName" type="text" class="form-control" readonly="readonly" 
										value="${entity.approverUserGroup.name}">
									<span class="input-group-btn">
										<button id="approverUser-select-btn" class="btn btn-default btn-ref" type="button">
											<span class="glyphicon glyphicon-search"></span>
										</button>
									</span>
								</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label col-md-2">备注</label>
						<div class="col-md-10">
							<textarea name="context" class="form-control">${entity.context}</textarea>
						</div>
					</div>
				</div>
			</div>
			</fieldset>
		</form>
		
		<div class="yy-tab" id="yy-page-sublist">
				<div class="tabbable-line">
					<ul class="nav nav-tabs ">
						<li class="active">
							<a href="#tab0" data-toggle="tab"> 遥信</a>
						</li>
						<li>
							<a href="#tab1" data-toggle="tab"> 遥测</a>
						</li>
						<li>
							<a href="#tab2" data-toggle="tab"> 遥控</a>
						</li>
						<li>
							<a href="#tab3" data-toggle="tab"> 遥调 </a>
						</li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="tab0">
							<div class="row yy-toolbar" id="subListToolId" style="">
								<button id="addNewSubYaoxin" class="btn blue btn-sm" type="button">
									<i class="fa fa-plus"></i> 添加遥信
								</button>
							</div>
							<table id="yy-table-yaoxin" class="yy-table">
								<thead>
									<tr>
										<th>序号</th>
										 <th class="table-checkbox"><input type="checkbox"
											class="group-checkable" data-set="#yy-table-yaoxin .checkboxes" /></th>
										<th>操作</th>
										<th>状态</th>
										<c:forEach items="${colsData.yxList}" var="list">
											<c:if test="${list.isDisplay eq 1}">
												<th>${list.columnAnno}</th>
											</c:if>
										</c:forEach>
									</tr>
								</thead>
								<tbody>
		
								</tbody>
							</table>
						</div>
						<div class="tab-pane" id="tab1">
							<div class="row yy-toolbar" id="subListToolId" style="">
								<button id="addNewSubYaoce" class="btn blue btn-sm" type="button">
									<i class="fa fa-plus"></i> 添加遥测
								</button>
							</div>
							<table id="yy-table-yaoce" class="yy-table-x">
								<thead>
									<tr>
										<th>序号</th>
									 	<th class="table-checkbox"><input type="checkbox"
											class="group-checkable" data-set="#yy-table-yaoce .checkboxes" /></th>
										<th>操作</th>
										<th>状态</th>
										<c:forEach items="${colsData.ycList}" var="list">
											<c:if test="${list.isDisplay eq 1}">
												<th>${list.columnAnno}</th>
											</c:if>
										</c:forEach>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						<div class="tab-pane" id="tab2">
							<div class="row yy-toolbar" id="subListToolId" style="">
								<button id="addNewSubYaokong" class="btn blue btn-sm" type="button">
									<i class="fa fa-plus"></i> 添加遥控
								</button>
							</div>
							<table id="yy-table-yaokong" class="yy-table-x">
								<thead>
									<tr>
										<th>序号</th>
									 	<th class="table-checkbox"><input type="checkbox"
											class="group-checkable" data-set="#yy-table-yaokong .checkboxes" /></th>
										<th>操作</th>
										<th>状态</th>
										<c:forEach items="${colsData.ykList}" var="list">
											<c:if test="${list.isDisplay eq 1}">
												<th>${list.columnAnno}</th>
											</c:if>
										</c:forEach>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						<div class="tab-pane" id="tab3">
							<div class="row yy-toolbar" id="subListToolId" style="">
								<button id="addNewSubYaotiao" class="btn blue btn-sm" type="button">
									<i class="fa fa-plus"></i> 添加遥调
								</button>
							</div>
							<table id="yy-table-yaotiao" class="yy-table-x">
								<thead>
									<tr>
										<th>序号</th>
										 <th class="table-checkbox"><input type="checkbox"
											class="group-checkable" data-set="#yy-table-yaotiao .checkboxes" /></th>
										<th>操作</th>
										<th>状态</th>
										<c:forEach items="${colsData.ytList}" var="list">
											<c:if test="${list.isDisplay eq 1}">
												<th>${list.columnAnno}</th>
											</c:if>
										</c:forEach>
									</tr>
								</thead>
								<tbody>
									
								</tbody>
							</table>				
						</div>
					</div>
				</div>
			</div>
	</div>
	<!-- 公用脚本 -->
	<%@include file="/WEB-INF/views/common/detailscript.jsp"%>
	<script type="text/javascript">
	
	var jsonResp = jQuery.parseJSON('${colsJson}');
	
	/* 子表操作 */
	var _subTableYaoxinCols = [{
		data : null,
		orderable : false,
		className : "center",
		width : "20"
	},{
		data : null,
		orderable : false,
		className : "center",
		width : "20",
		render : YYDataTableUtils.renderCheckCol
	},{
		data : "uuid",
		orderable : false,
		className : "center",
		width : "60",
		render : function(data, type, full) {
			//0:未审核 1：质疑   2：通过
			var passDiable="";
			var quesDiable="";
			if(!(full.substatus==null||full.substatus==''||full.substatus=='0'||full.substatus=='1')){
				passDiable='disabled="disabled"';
			}
			if(!(full.substatus==null||full.substatus==''||full.substatus=='0'||full.substatus=='2')){
				quesDiable='disabled="disabled"';
			}
			return "<div class='btn-group rap-btn-actiongroup'>"
			+ "<button id='yy-btn-pass-row' class='btn btn-xs btn-info edit' data-rel='tooltip' title='通过' "+passDiable+"><i class='fa fa-check'></i>通过</button>"
			+ "<button id='yy-btn-question-row' class='btn btn-xs btn-danger delete' data-rel='tooltip' title='质疑' "+quesDiable+"><i class='fa fa-hourglass-2'></i>质疑</button>"
			+ "</div>";
        }
	}, {
		data : 'substatus',
		width : "60",
		className : "center",
		render : function(data, type, full) {
            return YYDataUtils.getEnumName("AuditSubstatus", data);
		} 
    }];
	
	if(jsonResp){
		var cosList=jsonResp.yxList;
		if(cosList!=null&&cosList.length>0){
			for (i = 0; i < cosList.length; i++) {
				if(cosList[i].isDisplay=='1'){
					_subTableYaoxinCols.push({data : cosList[i].columnName,width : "150",orderable : false});
				}
			}
		}
	}
	
	/* 子表操作 */
	var _subTableYaoceCols = [{
		data : null,
		orderable : false,
		className : "center",
		width : "20"
	},{
		data : null,
		orderable : false,
		className : "center",
		width : "20",
		render : YYDataTableUtils.renderCheckCol
	},{
		data : "uuid",
		orderable : false,
		className : "center",
		width : "60",
		render : function(data, type, full) {
			//0:未审核 1：质疑   2：通过
			var passDiable="";
			var quesDiable="";
			if(!(full.substatus==null||full.substatus==''||full.substatus=='0'||full.substatus=='1')){
				passDiable='disabled="disabled"';
			}
			if(!(full.substatus==null||full.substatus==''||full.substatus=='0'||full.substatus=='2')){
				quesDiable='disabled="disabled"';
			}
			return "<div class='btn-group rap-btn-actiongroup'>"
			+ "<button id='yy-btn-pass-row' class='btn btn-xs btn-info edit' data-rel='tooltip' title='通过' "+passDiable+"><i class='fa fa-check'></i>通过</button>"
			+ "<button id='yy-btn-question-row' class='btn btn-xs btn-danger delete' data-rel='tooltip' title='质疑' "+quesDiable+"><i class='fa fa-hourglass-2'></i>质疑</button>"
			+ "</div>";
        }
	}, {
		data : 'substatus',
		width : "60",
		className : "center",
		render : function(data, type, full) {
            return YYDataUtils.getEnumName("AuditSubstatus", data);
		}
    }];
	
	if(jsonResp){
		var cosList=jsonResp.ycList;
		if(cosList!=null&&cosList.length>0){
			for (i = 0; i < cosList.length; i++) {
				if(cosList[i].isDisplay=='1'){
					_subTableYaoceCols.push({data : cosList[i].columnName,width : "150",orderable : false});
				}
			}
		}
	}
	
	/* 子表操作 */
	var _subTableYaokongCols = [{
		data : null,
		orderable : false,
		className : "center",
		width : "20"
	},{
		data : null,
		orderable : false,
		className : "center",
		width : "20",
		render : YYDataTableUtils.renderCheckCol
	},{
		data : "uuid",
		orderable : false,
		className : "center",
		width : "60",
		render : function(data, type, full) {
			//0:未审核 1：质疑   2：通过
			var passDiable="";
			var quesDiable="";
			if(!(full.substatus==null||full.substatus==''||full.substatus=='0'||full.substatus=='1')){
				passDiable='disabled="disabled"';
			}
			if(!(full.substatus==null||full.substatus==''||full.substatus=='0'||full.substatus=='2')){
				quesDiable='disabled="disabled"';
			}
			return "<div class='btn-group rap-btn-actiongroup'>"
			+ "<button id='yy-btn-pass-row' class='btn btn-xs btn-info edit' data-rel='tooltip' title='通过' "+passDiable+"><i class='fa fa-check'></i>通过</button>"
			+ "<button id='yy-btn-question-row' class='btn btn-xs btn-danger delete' data-rel='tooltip' title='质疑' "+quesDiable+"><i class='fa fa-hourglass-2'></i>质疑</button>"
			+ "</div>";
        }
	}, {
		data : 'substatus',
		width : "60",
		className : "center",
		render : function(data, type, full) {
            return YYDataUtils.getEnumName("AuditSubstatus", data);
		} 
    }];
	
	if(jsonResp){
		var cosList=jsonResp.ykList;
		if(cosList!=null&&cosList.length>0){
			for (i = 0; i < cosList.length; i++) {
				if(cosList[i].isDisplay=='1'){
					_subTableYaokongCols.push({data : cosList[i].columnName,width : "150",orderable : false});
				}
			}
		}
	}
	
	/* 子表操作 */
	var _subTableYaotiaoCols = [{
		data : null,
		orderable : false,
		className : "center",
		width : "20"
	},{
		data : null,
		orderable : false,
		className : "center",
		width : "20",
		render : YYDataTableUtils.renderCheckCol
	},{
		data : "uuid",
		orderable : false,
		className : "center",
		width : "60",
		render : function(data, type, full) {
			//0:未审核 1：质疑   2：通过
			var passDiable="";
			var quesDiable="";
			if(!(full.substatus==null||full.substatus==''||full.substatus=='0'||full.substatus=='1')){
				passDiable='disabled="disabled"';
			}
			if(!(full.substatus==null||full.substatus==''||full.substatus=='0'||full.substatus=='2')){
				quesDiable='disabled="disabled"';
			}
			return "<div class='btn-group rap-btn-actiongroup'>"
			+ "<button id='yy-btn-pass-row' class='btn btn-xs btn-info edit' data-rel='tooltip' title='通过' "+passDiable+"><i class='fa fa-check'></i>通过</button>"
			+ "<button id='yy-btn-question-row' class='btn btn-xs btn-danger delete' data-rel='tooltip' title='质疑' "+quesDiable+"><i class='fa fa-hourglass-2'></i>质疑</button>"
			+ "</div>";
        }
	}, {
		data : 'substatus',
		width : "60",
		className : "center",
		render : function(data, type, full) {
            return YYDataUtils.getEnumName("AuditSubstatus", data);
		} 
    }];
	
	if(jsonResp){
		var cosList=jsonResp.ytList;
		if(cosList!=null&&cosList.length>0){
			for (i = 0; i < cosList.length; i++) {
				if(cosList[i].isDisplay=='1'){
					_subTableYaotiaoCols.push({data : cosList[i].columnName,width : "150",orderable : false});
				}
			}
		}
	}
	
	
		$(document).ready(function() {
			
			YYUI.setUIAction('yy-table-yaoxin');//设置子表勾选全选
			YYUI.setUIAction('yy-table-yaoce');//设置子表勾选全选
			YYUI.setUIAction('yy-table-yaokong');//设置子表勾选全选
			YYUI.setUIAction('yy-table-yaotiao');//设置子表勾选全选
			//按钮绑定事件
			bindDetailActions();
			bindButtonAction();
			setValue();
			
			//子表
			_subYaoxinTableList = $('#yy-table-yaoxin').DataTable({
				"columns" : _subTableYaoxinCols,
				"createdRow" : selRowAction,
				//"scrollX" : true,
				"processing" : true,//加载时间长，显示加载中
				"paging" :false,
				"order" : []
			});
			//子表
			_subYaoceTableList = $('#yy-table-yaoce').DataTable({
				"columns" : _subTableYaoceCols,
				"createdRow" : selRowAction,
				//"scrollX" : true,
				"processing" : true,//加载时间长，显示加载中
				"paging" :false,
				"order" : []
			});
			//子表
			_subYaokongTableList = $('#yy-table-yaokong').DataTable({
				"columns" : _subTableYaokongCols,
				"createdRow" : selRowAction,
				//"scrollX" : true,
				"processing" : true,//加载时间长，显示加载中
				"paging" :false,
				"order" : []
			});
			//子表
			_subYaotiaoTableList = $('#yy-table-yaotiao').DataTable({
				"columns" : _subTableYaotiaoCols,
				"createdRow" : selRowAction,
				//"scrollX" : true,
				"processing" : true,//加载时间长，显示加载中
				"paging" :false,
				"order" : []
			});
			
			loadSubYaoxin();
			loadSubYaoce();
			loadSubYaokong();
			loadSubYaotiao();
		});

		//设置默认值
		function setValue() {
			if ('${openstate}' == 'add') {
				//$("select[name='is_use']").val('1');
			} else if ('${openstate}' == 'detail') {
				//$("input[name='uuid']").val('${entity.uuid}');
			}
		}

		
		function bindButtonAction(){
			$('#auditorUser-select-btn').on('click', function() {
				layer.open({
					type : 2,
					title : '请选择人员',
					shadeClose : false,
					shade : 0.8,
					area : [ '1000px', '90%' ],
					content : '${ctx}/sys/ref/refUserSel?callBackMethod=window.parent.callBackAuditorUser'
				});
			});
			
			$('#approverUser-select-btn').on('click', function() {
				layer.open({
					type : 2,
					title : '请选择人员',
					shadeClose : false,
					shade : 0.8,
					area : [ '1000px', '90%' ],
					content : '${ctx}/sys/ref/refUserGroup?callBackMethod=window.parent.callBackapproverUser'
				});
			});
			
			//添加按钮事件
			$('#addNewSubYaoxin').click(function(e) {
				e.preventDefault();
				openSubAdd('yx');
			});
			//添加按钮事件
			$('#addNewSubYaoce').click(function(e) {
				e.preventDefault();
				openSubAdd('yc');
			});
			//添加按钮事件
			$('#addNewSubYaokong').click(function(e) {
				e.preventDefault();
				openSubAdd('yk');
			});
			//添加按钮事件
			$('#addNewSubYaotiao').click(function(e) {
				e.preventDefault();
				openSubAdd('yt');
			});
		}
		
		
		function openSubAdd(type){
			layer.open({
				title : '添加',
				type : 2,
				area : [ '500px', '200px;' ],
				shadeClose : false,
				shade : 0.8,
				content : '${serviceurlsub}/toAddSub?mainid=${entity.uuid}&type='+type+'&callBackMethod=window.parent.callBackSelComStudent'
			});
		}
		
		//回调选择
		function callBackAuditorUser(data){
			$("#auditorUserUuid").val(data.uuid);
			$("#auditorUserName").val(data.username);
		}
		
		//回调选择
		function callBackapproverUser(data){
			$("#approverUserUuid").val(data.uuid);
			$("#approverUserName").val(data.name);
		}
		
		//加载子表
		function loadSubYaoxin(){
			var loadWaitLoad=layer.load(2);
			$.ajax({
				url : '${serviceurlsub}/query',
				data : {"search_EQ_main.uuid":'${entity.uuid}',"search_EQ_type":'yx'},//$("#yy-form-query").serializeArray(),
				dataType : 'json',
				type : 'post',
				success : function(data) {
					layer.close(loadWaitLoad);
					_subYaoxinTableList.clear();
					if(data.records!=null&&data.records.length>0){
						var records=data.records;
						var dataRecords=new Array();
						for (i = 0; i < records.length; i++) {
							var jsonData = jQuery.parseJSON(records[i].content);//数据库保存的信息
							if(jsonResp){
								var cosList=jsonResp.yxList;
								if(cosList!=null&&cosList.length>0){
									var obj={};
									obj.substatus=records[i].substatus;
									for (j = 0; j < cosList.length; j++) {
										if(cosList[j].isDisplay=='1'){
											var jsonColName=cosList[j].columnName;
											if(typeof (jsonData[jsonColName]) != "undefined"){
												eval("obj."+jsonColName+"="+jsonData[jsonColName]);
											}else{
												eval("obj."+jsonColName+"=null");
											}
										}
									}
									dataRecords.push(obj);
								}
							}
						}
						_subYaoxinTableList.rows.add(dataRecords);
					}
					_subYaoxinTableList.on('order.dt search.dt',
					        function() {
						_subYaoxinTableList.column(0, {
							        search: 'applied',
							        order: 'applied'
						        }).nodes().each(function(cell, i) {
							        cell.innerHTML = i + 1;
						        });
					}).draw();
				},
				error : function(data) {
					layer.close(loadWaitLoad);
					YYUI.failMsg('加载数据库失败!');
				}
			});
			//_subYaoxinTableList.column(0).visible(false);
		}
		//加载子表
		function loadSubYaoce(){
			var loadWaitLoad=layer.load(2);
			$.ajax({
				url : '${serviceurlsub}/query',
				data : {"search_EQ_main.uuid":'${entity.uuid}',"search_EQ_type":'yc'},//$("#yy-form-query").serializeArray(),
				dataType : 'json',
				type : 'post',
				success : function(data) {
					layer.close(loadWaitLoad);
					_subYaoceTableList.clear();
					if(data.records!=null&&data.records.length>0){
						var records=data.records;
						var dataRecords=new Array();
						for (i = 0; i < records.length; i++) {
							var jsonData = jQuery.parseJSON(records[i].content);//数据库保存的信息
							if(jsonResp){
								var cosList=jsonResp.ycList;
								if(cosList!=null&&cosList.length>0){
									var obj={};
									obj.substatus=records[i].substatus;
									for (j = 0; j < cosList.length; j++) {
										if(cosList[j].isDisplay=='1'){
											var jsonColName=cosList[j].columnName;
											if(typeof (jsonData[jsonColName]) != "undefined"){
												eval("obj."+jsonColName+"="+jsonData[jsonColName]);
											}else{
												eval("obj."+jsonColName+"=null");
											}
										}
									}
									dataRecords.push(obj);
								}
							}
						}
						_subYaoceTableList.rows.add(dataRecords);
					}
					_subYaoceTableList.on('order.dt search.dt',
					        function() {
						_subYaoceTableList.column(0, {
							        search: 'applied',
							        order: 'applied'
						        }).nodes().each(function(cell, i) {
							        cell.innerHTML = i + 1;
						        });
					}).draw();
				},
				error : function(data) {
					layer.close(loadWaitLoad);
					YYUI.failMsg('加载数据库失败!');
				}
			});
		}
		//加载子表
		function loadSubYaokong(){
			var loadWaitLoad=layer.load(2);
			$.ajax({
				url : '${serviceurlsub}/query',
				data : {"search_EQ_main.uuid":'${entity.uuid}',"search_EQ_type":'yk'},//$("#yy-form-query").serializeArray(),
				dataType : 'json',
				type : 'post',
				success : function(data) {
					layer.close(loadWaitLoad);
					_subYaokongTableList.clear();
					if(data.records!=null&&data.records.length>0){
						var records=data.records;
						var dataRecords=new Array();
						for (i = 0; i < records.length; i++) {
							var jsonData = jQuery.parseJSON(records[i].content);//数据库保存的信息
							if(jsonResp){
								var cosList=jsonResp.ykList;
								if(cosList!=null&&cosList.length>0){
									var obj={};
									obj.substatus=records[i].substatus;
									for (j = 0; j < cosList.length; j++) {
										if(cosList[j].isDisplay=='1'){
											var jsonColName=cosList[j].columnName;
											if(typeof (jsonData[jsonColName]) != "undefined"){
												eval("obj."+jsonColName+"="+jsonData[jsonColName]);
											}else{
												eval("obj."+jsonColName+"=null");
											}
										}
									}
									dataRecords.push(obj);
								}
							}
						}
						_subYaokongTableList.rows.add(dataRecords);
					}
					_subYaokongTableList.on('order.dt search.dt',
					        function() {
						_subYaokongTableList.column(0, {
							        search: 'applied',
							        order: 'applied'
						        }).nodes().each(function(cell, i) {
							        cell.innerHTML = i + 1;
						        });
					}).draw();
				},
				error : function(data) {
					layer.close(loadWaitLoad);
					YYUI.failMsg('加载数据库失败!');
				}
			});
		}
		//加载子表
		function loadSubYaotiao(){
			var loadWaitLoad=layer.load(2);
			$.ajax({
				url : '${serviceurlsub}/query',
				data : {"search_EQ_main.uuid":'${entity.uuid}',"search_EQ_type":'yt'},//$("#yy-form-query").serializeArray(),
				dataType : 'json',
				type : 'post',
				success : function(data) {
					layer.close(loadWaitLoad);
					_subYaotiaoTableList.clear();
					if(data.records!=null&&data.records.length>0){
						var records=data.records;
						var dataRecords=new Array();
						for (i = 0; i < records.length; i++) {
							var jsonData = jQuery.parseJSON(records[i].content);//数据库保存的信息
							if(jsonResp){
								var cosList=jsonResp.ytList;
								if(cosList!=null&&cosList.length>0){
									var obj={};
									obj.substatus=records[i].substatus;
									for (j = 0; j < cosList.length; j++) {
										if(cosList[j].isDisplay=='1'){
											var jsonColName=cosList[j].columnName;
											if(typeof (jsonData[jsonColName]) != "undefined"){
												eval("obj."+jsonColName+"="+jsonData[jsonColName]);
											}else{
												eval("obj."+jsonColName+"=null");
											}
										}
									}
									dataRecords.push(obj);
								}
							}
						}
						_subYaotiaoTableList.rows.add(dataRecords);
					}
					_subYaotiaoTableList.on('order.dt search.dt',
					        function() {
						_subYaotiaoTableList.column(0, {
							        search: 'applied',
							        order: 'applied'
						        }).nodes().each(function(cell, i) {
							        cell.innerHTML = i + 1;
						        });
					}).draw();
				},
				error : function(data) {
					layer.close(loadWaitLoad);
					YYUI.failMsg('加载数据库失败!');
				}
			});
		}
		
		 //定义行点击事件
		function selRowAction(nRow, aData, iDataIndex) {
			$(nRow).dblclick(function() {
				onViewDetailRow(aData, iDataIndex, nRow);
			});
		
			$('#yy-btn-pass-row', nRow).click(function() {
				layer.confirm("确定要通过吗？", function(index) {
					layer.close(index);
					var passLoading = layer.load(2);
					$.ajax({
						type : "POST",
						data :{"uuid": aData.uuid},
						url : "${serviceurlsub}/passSub",
						async : true,
						dataType : "json",
						success : function(data) {
							layer.close(passLoading);
							if (data.success) {
								YYUI.succMsg(YYMsg.alertMsg('sys-success-todo',['通过']));
								if(aData.type=='yx'){
									loadSubYaoxin();
								}else if(aData.type=='yc'){
									loadSubYaoce();
								}else if(aData.type=='yk'){
									loadSubYaokong();
								}else if(aData.type=='yt'){
									loadSubYaotiao();
								}
							}else {
								YYUI.promAlert(YYMsg.alertMsg('sys-fail-todo',['通过'])+data.msg);
							}
						},
						error : function(data) {
							layer.close(passLoading);
							YYUI.promAlert(YYMsg.alertMsg('sys-submit-http',null));
						}
					});
				});		
			});
		
			$('#yy-btn-question-row', nRow).click(function() {
				layer.confirm("确定要质疑吗？", function(index) {
					layer.close(index);
					var passLoading = layer.load(2);
					$.ajax({
						type : "POST",
						data :{"uuid": aData.uuid},
						url : "${serviceurlsub}/questionSub",
						async : true,
						dataType : "json",
						success : function(data) {
							layer.close(passLoading);
							if (data.success) {
								YYUI.succMsg(YYMsg.alertMsg('sys-success-todo',['质疑']));
								if(aData.type=='yx'){
									loadSubYaoxin();
								}else if(aData.type=='yc'){
									loadSubYaoce();
								}else if(aData.type=='yk'){
									loadSubYaokong();
								}else if(aData.type=='yt'){
									loadSubYaotiao();
								}
							}else {
								YYUI.promAlert(YYMsg.alertMsg('sys-fail-todo',['质疑'])+data.msg);
							}
						},
						error : function(data) {
							layer.close(passLoading);
							YYUI.promAlert(YYMsg.alertMsg('sys-submit-http',null));
						}
					});
				});	
			});
			
			
/* 			$('#yy-btn-grant-row', nRow).click(function() {
				layer.open({
					type : 2,
					title : '请选择权限',
					shadeClose : false,
					shade : 0.8,
					area : [ '300px', '90%' ],
					content : '${serviceurl}/selectFunc?rootSelectable=true&roleId='+aData.uuid, //iframe的url
				});
			}); */
		};
		
		//回调刷新
		function callBackRefreshSub(type){
			if(type=='yx'){
				loadSubYaoxin();
			}else if(type=='yc'){
				loadSubYaoce();
			}else if(type=='yk'){
				loadSubYaokong();
			}else if(type=='yt'){
				loadSubYaotiao();
			}
		}
	</script>
</body>
</html>
