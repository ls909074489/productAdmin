<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serviceurl" value="${ctx}/ver/versioninfo"/>
<c:set var="serviceurlsub" value="${ctx}/ver/versioninfosub"/>
<c:set var="slaveserviceurl" value="${ctx}/ver/slaveapprove"/>
<html>
<head>
<title>版本信息表</title>
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
			<%-- <c:if test="${empty entity.billstatus or entity.billstatus eq 1}">
				<button id="yy-btn-submit" class="btn yellow btn-sm btn-info">
					<i class="fa fa-send"></i> 保存提交
				</button>
			</c:if> --%>
		</div>
	<div>
		<form id="yy-form-edit" class="form-horizontal yy-form-edit">
			<input name="uuid" type="hidden" value="${entity.uuid}">
			<input name="mainindex" id="mainindex" type="hidden"  value="${entity.mainindex}">
			<input name="operType" id="operType" type="hidden"  value="">
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4 required">厂站</label>
						<div class="col-md-8">
								<%-- <div class="input-group input-icon right">
									<input id="stationUuid" name="station.uuid" type="hidden" value="${entity.station.uuid}"> 
									<i class="fa fa-remove" onclick="cleanDef('stationUuid','stationName');" title="清空"></i>
									<input id="stationName" name="stationName" type="text" class="form-control" readonly="readonly" 
										value="${entity.station.name}">
									<span class="input-group-btn">
										<button id="station-select-btn" class="btn btn-default btn-ref" type="button">
											<span class="glyphicon glyphicon-search"></span>
										</button>
									</span>
								</div> --%>
								<input id="stationUuid" name="station.uuid" type="hidden" value="${entity.station.uuid}"> 
								<input id="stationName" name="stationName" type="text" class="form-control" readonly="readonly" 
										value="${entity.station.name}">
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4 required">类型</label>
						<div class="col-md-8">
							<fieldset disabled="disabled">
								<select class="yy-input-enumdata form-control" id="type" 
									name="type" data-enum-group="VersionType"></select>
							</fieldset>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4 required">主版本号</label>
						<div class="col-md-8">
							<input name="masver" type="text" class="form-control" value="${entity.masver}" readonly="readonly">
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
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4 required">版本文件</label>
						<div class="col-md-8">
							<%-- <input name="pkgPath" type="text" class="form-control" value="${entity.pkgPath}"> --%>
							
							<div class="input-group input-icon right">
									<input id="pkgPath" name="pkgPath" type="hidden" value="${entity.pkgPath}"> 
									<i class="fa fa-remove" onclick="cleanDef('pkgPath','pkgPathName');" title="清空"></i>
									<input id="pkgPathName" name="pkgPathName" type="text" class="form-control" readonly="readonly" 
										value="${entity.pkgPathName}">
									<span class="input-group-btn">
										<button id="pkgPath-select-btn" class="btn btn-default btn-ref" type="button">
											<span class="glyphicon glyphicon-search"></span>
										</button>
									</span>
							</div>
						</div>
					</div>
				</div>
				<%-- <div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4 required">数据包校验</label>
						<div class="col-md-8">
							<input name="pkgCs" type="text" class="form-control" value="${entity.pkgCs}">
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">数据包说明</label>
						<div class="col-md-8">
							<input name="pkgDesc" type="text" class="form-control" value="${entity.pkgDesc}">
						</div>
					</div>
				</div> --%>
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
		</form>
		
		<div class="yy-tab" id="yy-page-sublist">
				<div class="tabbable-line">
					<ul class="nav nav-tabs ">
						<li class="active">
							<a href="#tab0" data-toggle="tab"> <!-- 特殊 -->遥信</a>
						</li>
						<li>
							<a href="#tab1" data-toggle="tab"> <!-- 特殊 -->遥测</a>
						</li>
						<li>
							<a href="#tab2" data-toggle="tab"> <!-- 特殊 -->遥控</a>
						</li>
						<li>
							<a href="#tab3" data-toggle="tab"> <!-- 特殊 -->遥调 </a>
						</li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="tab0">
							<div class="row yy-toolbar" id="subListToolId" style="">
								<button id="addNewSubYaoxin" class="btn blue btn-sm" type="button">
									<i class="fa fa-plus"></i> 添加<!-- 特殊 -->遥信
								</button>
							</div>
							<table id="yy-table-yaoxin" class="yy-table">
								<thead>
									<tr>
										<th>序号</th>
										<!-- <th class="table-checkbox" style=""><input type="checkbox"
											class="group-checkable" data-set="#yy-table-yaoxin .checkboxes" />
										</th> -->
										<!-- <th>操作</th> -->
										<!-- <th>状态</th> -->
										<th>子表类型</th>
										<c:forEach items="${colsData.txList}" var="list">
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
									<i class="fa fa-plus"></i> 添加<!-- 特殊 -->遥测
								</button>
							</div>
							<table id="yy-table-yaoce" class="yy-table">
								<thead>
									<tr>
										<th>序号</th>
										<!-- <th class="table-checkbox" style=""><input type="checkbox"
											class="group-checkable" data-set="#yy-table-yaoce .checkboxes" />
										</th> -->
										<!-- <th>操作</th> -->
										<!-- <th>状态</th> -->
										<th>子表类型</th>
										<c:forEach items="${colsData.tcList}" var="list">
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
									<i class="fa fa-plus"></i> 添加<!-- 特殊 -->遥控
								</button>
							</div>
							<table id="yy-table-yaokong" class="yy-table">
								<thead>
									<tr>
										<th>序号</th>
										<!-- <th class="table-checkbox" style=""><input type="checkbox"
											class="group-checkable" data-set="#yy-table-yaokong .checkboxes" />
										</th> -->
										<!-- <th>操作</th> -->
										<!-- <th>状态</th> -->
										<th>子表类型</th>
										<c:forEach items="${colsData.tkList}" var="list">
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
									<i class="fa fa-plus"></i> 添加<!-- 特殊 -->遥调
								</button>
							</div>
							<table id="yy-table-yaotiao" class="yy-table-x">
								<thead>
									<tr>
										<th>序号</th>
										<!-- <th class="table-checkbox" style=""><input type="checkbox"
											class="group-checkable" data-set="#yy-table-yaotiao .checkboxes" />
										</th> -->
										<!-- <th>操作</th> -->
										<!-- <th>状态</th> -->
										<th>子表类型</th>
										<c:forEach items="${colsData.ttList}" var="list">
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
	<%@include file="/WEB-INF/views/common/editscript.jsp"%>
	<script type="text/javascript">
	
	var jsonResp = jQuery.parseJSON('${colsJson}');
	
		/* 子表操作 */
		var _subTableYaoxinCols = [{
			data : null,
			orderable : false,
			className : "center",
			width : "20"
		},/* {
			data : "uuid",
			orderable : false,
			className : "center",
			width : "40",
			render : YYDataTableUtils.renderCheckCol
		}, *//* {
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
		}, *//*  {
			data : 'substatus',
			width : "60",
			className : "center",
			render : function(data, type, full) {
				var contentStr="";
				if(full.content!=null){
					contentStr='<span class="contentSpan">'+full.content+'</span>';
				}else{
					contentStr='<input id="content" name="content" type="hidden" value="">';
				}
	            return YYDataUtils.getEnumName("AuditSubstatus", data)+contentStr;
			} 
	    }, */ {
			data : 'type',
			width : "60",
			visible : false,
			className : "center",
			render : function(data, type, full) {
				if(data==null){
					data="";
				}
				return '<input class="form-control" value="'+ data + '" name="type">';
			}
	    }];
		
		if(jsonResp){
			var cosList=jsonResp.txList;
			if(cosList!=null&&cosList.length>0){
				for (i = 0; i < cosList.length; i++) {
					if(cosList[i].isDisplay=='1'){
						if(cosList[i].columnName=='pointno'){
							_subTableYaoxinCols.push({data : cosList[i].columnName,width : cosList[i].width,orderable : false,
								render : function(data, type, full) {
									if(full.haslinks!=null&&full.haslinks=='1'){
										return '<a onclick="showLinksInfo(\''+full.uuid+'\');">'+data+'&nbsp;<i class="fa fa-search-plus"></i></a>';
									}else{
										return data;
									}
								}});
						}else{
							_subTableYaoxinCols.push({data : cosList[i].columnName,width : cosList[i].width,orderable : false});	
						}
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
		},/* {
			data : "uuid",
			orderable : false,
			className : "center",
			width : "40",
			render : YYDataTableUtils.renderCheckCol
		}, *//* {
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
		}, *//*  {
			data : 'substatus',
			width : "60",
			className : "center",
			render : function(data, type, full) {
				var contentStr="";
				if(full.content!=null){
					contentStr='<input id="content" name="content" type="hidden" value="'+full.content+'">';
				}else{
					contentStr='<input id="content" name="content" type="hidden" value="">';
				}
	            return YYDataUtils.getEnumName("AuditSubstatus", data)+contentStr;
			}
	    }, */ {
			data : 'type',
			width : "60",
			visible : false,
			className : "center",
			render : function(data, type, full) {
				if(data==null){
					data="";
				}
				return '<input class="form-control" value="'+ data + '" name="type">';
			}
	    }];
		
		if(jsonResp){
			var cosList=jsonResp.tcList;
			if(cosList!=null&&cosList.length>0){
				for (i = 0; i < cosList.length; i++) {
					if(cosList[i].isDisplay=='1'){
						if(cosList[i].columnName=='pointno'){
							_subTableYaoceCols.push({data : cosList[i].columnName,width : cosList[i].width,orderable : false,
								render : function(data, type, full) {
									if(full.haslinks!=null&&full.haslinks=='1'){
										return '<a onclick="showLinksInfo(\''+full.uuid+'\');">'+data+'&nbsp;<i class="fa fa-search-plus"></i></a>';
									}else{
										return data;
									}
								}});
						}else{
							_subTableYaoceCols.push({data : cosList[i].columnName,width : cosList[i].width,orderable : false});	
						}
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
		},/* {
			data : "uuid",
			orderable : false,
			className : "center",
			width : "40",
			render : YYDataTableUtils.renderCheckCol
		}, *//* {
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
		}, */ /* {
			data : 'substatus',
			width : "60",
			className : "center",
			render : function(data, type, full) {
				var contentStr="";
				if(full.content!=null){
					contentStr='<input id="content" name="content" type="hidden" value="'+full.content+'">';
				}else{
					contentStr='<input id="content" name="content" type="hidden" value="">';
				}
	            return YYDataUtils.getEnumName("AuditSubstatus", data)+contentStr;
			} 
	    }, */ {
			data : 'type',
			width : "60",
			visible : false,
			className : "center",
			render : function(data, type, full) {
				if(data==null){
					data="";
				}
				return '<input class="form-control" value="'+ data + '" name="type">';
			}
	    }];
		
		if(jsonResp){
			var cosList=jsonResp.tkList;
			if(cosList!=null&&cosList.length>0){
				for (i = 0; i < cosList.length; i++) {
					if(cosList[i].isDisplay=='1'){
						if(cosList[i].columnName=='pointno'){
							_subTableYaokongCols.push({data : cosList[i].columnName,width : cosList[i].width,orderable : false,
								render : function(data, type, full) {
									if(full.haslinks!=null&&full.haslinks=='1'){
										return '<a onclick="showLinksInfo(\''+full.uuid+'\');">'+data+'&nbsp;<i class="fa fa-search-plus"></i></a>';
									}else{
										return data;
									}
								}});
						}else{
							_subTableYaokongCols.push({data : cosList[i].columnName,width : cosList[i].width,orderable : false});	
						}
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
		},/* {
			data : "uuid",
			orderable : false,
			className : "center",
			width : "40",
			render : YYDataTableUtils.renderCheckCol
		}, *//* {
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
		},  *//* {
			data : 'substatus',
			width : "60",
			className : "center",
			render : function(data, type, full) {
				var contentStr="";
				if(full.content!=null){
					contentStr='<input id="content" name="content" type="hidden" value="'+full.content+'">';
				}else{
					contentStr='<input id="content" name="content" type="hidden" value="">';
				}
	            return YYDataUtils.getEnumName("AuditSubstatus", data)+contentStr;
			} 
	    }, */ {
			data : 'type',
			width : "60",
			visible : false,
			className : "center",
			render : function(data, type, full) {
				if(data==null){
					data="";
				}
				return '<input class="form-control" value="'+ data + '" name="type">';
			}
	    }];
		
		if(jsonResp){
			var cosList=jsonResp.ttList;
			if(cosList!=null&&cosList.length>0){
				for (i = 0; i < cosList.length; i++) {
					if(cosList[i].isDisplay=='1'){
						if(cosList[i].columnName=='pointno'){
							_subTableYaotiaoCols.push({data : cosList[i].columnName,width : cosList[i].width,orderable : false,
								render : function(data, type, full) {
									if(full.haslinks!=null&&full.haslinks=='1'){
										return '<a onclick="showLinksInfo(\''+full.uuid+'\');">'+data+'&nbsp;<i class="fa fa-search-plus"></i></a>';
									}else{
										return data;
									}
								}});
						}else{
							_subTableYaotiaoCols.push({data : cosList[i].columnName,width : cosList[i].width,orderable : false});	
						}
					}
				}
			}
		}
	
		$(document).ready(function() {
			YYUI.setUIAction('yy-table-yaoxin');//设置子表勾选全选
			YYUI.setUIAction('yy-table-yaoce');//设置子表勾选全选
			YYUI.setUIAction('yy-table-yaokong');//设置子表勾选全选
			YYUI.setUIAction('yy-table-yaotiao');//设置子表勾选全选
			//子表
			_subYaoxinTableList = $('#yy-table-yaoxin').DataTable({
				"columns" : _subTableYaoxinCols,
				//"createdRow" : selRowAction,
				//"scrollX" : true,
				"processing" : true,//加载时间长，显示加载中
				"paging" :false,
				"order" : []
			});
			//子表
			_subYaoceTableList = $('#yy-table-yaoce').DataTable({
				"columns" : _subTableYaoceCols,
				//"createdRow" : selRowAction,
				//"scrollX" : true,
				"processing" : true,//加载时间长，显示加载中
				"paging" :false,
				"order" : []
			});
			//子表
			_subYaokongTableList = $('#yy-table-yaokong').DataTable({
				"columns" : _subTableYaokongCols,
				//"createdRow" : selRowAction,
				//"scrollX" : true,
				"processing" : true,//加载时间长，显示加载中
				"paging" :false,
				"order" : []
			});
			//子表
			_subYaotiaoTableList = $('#yy-table-yaotiao').DataTable({
				"columns" : _subTableYaotiaoCols,
				//"createdRow" : selRowAction,
				//"scrollX" : true,
				"processing" : true,//加载时间长，显示加载中
				"paging" :false,
				"order" : []
			});
			//按钮绑定事件
			bindEditActions();
			bindButtonAction();
			validateForms();
			setValue();
		});

		//设置默认值
		function setValue() {
			if ('${openstate}' == 'add') {
				//$("select[name='is_use']").val('1');
				$("select[name='type']").val('${type}');
				$("#stationUuid").val('${currentStation.uuid}');
				$("#stationName").val('${currentStation.name}');
				
				$("input[name='masver']").val('${MaxNextMasver}');//设置默认的最大版本
			} /* else if ('${openstate}' == 'edit') {
				$("select[name='type']").val('${type}');
				
				loadSubYaoxin('${entity.mainindex}');
				loadSubYaoce('${entity.mainindex}');
				loadSubYaokong('${entity.mainindex}');
				loadSubYaotiao('${entity.mainindex}');
			} */
		}
		
		function bindButtonAction(){
			$("#yy-btn-submit").on('click', onSubmit);//提交
			
			$('#station-select-btn').on('click', function() {
				layer.open({
					type : 2,
					title : '请选择厂站',
					shadeClose : false,
					shade : 0.8,
					area : [ '1000px', '90%' ],
					content : '${ctx}/sys/ref/refStationSel?callBackMethod=window.parent.callBackStation'
				});
			});
			
			$('#pkgPath-select-btn').on('click', function() {
				layer.open({
					type : 2,
					title : '请选择文件',
					shadeClose : false,
					shade : 0.8,
					area : [ '500px', '160px' ],
					content : '${serviceurl}/toUploadFile?callBackMethod=window.parent.callBackUploadFile&mainindex='+$("#mainindex").val()
				});
			});
			
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
			
			//添加子表
			$("#addNewSubYaoxin").on('click',  function() {addNewSubFunc('tx');});//添加子表
			$("#addNewSubYaoce").on('click',  function() {addNewSubFunc('tc');});//添加子表
			$("#addNewSubYaokong").on('click',  function() {addNewSubFunc('tk');});//添加子表
			$("#addNewSubYaotiao").on('click',  function() {addNewSubFunc('tt');});//添加子表
		}
		
		function addNewSubFunc(t){
			layer.open({
				title:"信息",
			    type: 2,
			    area: ['400px', '400px'],
			    shadeClose : false,
				shade : 0.8,
			    content: '${slaveserviceurl}/toQuestionPage?type='+t+'&mainindex='+$("#mainindex").val()
			});
		}

		//表单校验
		function validateForms(){
			validata = $('#yy-form-edit').validate({
				onsubmit : true,
				rules : {
					'stationName' : {required : true},
					'type' : {required : true,maxlength : 2},
					'pkgPath' : {required : true,maxlength : 200},
					'pkgDesc' : {maxlength : 100},
					'auditorUserName' : {required : true,maxlength : 100},
					'approverUserName' : {required : true,maxlength : 100},
					'context' : {maxlength : 200},
				}
			});
		}
		
		
		//主子表保存
		function onSave(isClose) {
			var subValidate=validOther();
			var mainValidate=$('#yy-form-edit').valid();
			if(!subValidate||!mainValidate){
				return false;
			}
			
			var subData = subData = {};
			
			$("#operType").val("0");//0保存 1：保存并提交
			
			var posturl = "${serviceurl}/addwithsub";
			var pk = $("input[name='uuid']").val();
			if (pk != "" && typeof (pk) != null)
				posturl = "${serviceurl}/updatewithsub";
				
			var saveWaitLoad=layer.load(2);
			var opt = {
				url : posturl,
				type : "post",
				data : subData,
				success : function(data) {
					layer.close(saveWaitLoad);
					if (data.success == true) {
						//_deletePKs = new Array();
						//_addList = new Array();
						hasLoadSub = false;
						YYUI.succMsg('保存成功!');
						window.parent.onRefresh(true);
						closeEditView();
						window.location.reload();
					} else {
						YYUI.promAlert("保存失败：" + data.msg);
					}
				}
			}
			$("#yy-form-edit").ajaxSubmit(opt);
		}
		
		//保存并提交
		function onSubmit(){
			var subValidate=validOther();
			var mainValidate=$('#yy-form-edit').valid();
			if(!subValidate||!mainValidate){
				return false;
			}
			
			var subData = null;
			//所有需要保存的参数
			subData = {};
			
			$("#operType").val("1");//0保存 1：保存并提交

			var posturl = "${serviceurl}/addwithsub";
			var pk = $("input[name='uuid']").val();
			if (pk != "" && typeof (pk) != null)
				posturl = "${serviceurl}/updatewithsub";
				
			var saveWaitLoad=layer.load(2);
			var opt = {
				url : posturl,
				type : "post",
				data : subData,
				success : function(data) {
					layer.close(saveWaitLoad);
					if (data.success == true) {
						hasLoadSub = false;
						YYUI.succMsg('保存成功!');
						window.parent.onRefresh(true);
						closeEditView();
						window.location.reload();
					} else {
						YYUI.promAlert("保存失败：" + data.msg);
					}
				}
			}
			$("#yy-form-edit").ajaxSubmit(opt);
		}
		
		//回调选择
		function callBackStation(data){
			$("#stationUuid").val(data.uuid);
			$("#stationName").val(data.name);
		}
		
		//回调上传文件
		function callBackUploadFile(data){
			$("#pkgPathName").val(data.fileName);
			if(data.records!=null&&data.records.length>0){
				$("#mainindex").val(data.records[0].mainindex);
				$("#pkgPath").val(data.records[0].pkgPath);
				loadSubYaoxin(data.records[0].mainindex);
				loadSubYaoce(data.records[0].mainindex);
				loadSubYaokong(data.records[0].mainindex);
				loadSubYaotiao(data.records[0].mainindex);

				/* $("input[name='search_EQ_mainindex']").val(data.records[0].mainindex);
				_subYaoxinTableList.draw(); //服务器分页
				_subYaoceTableList.draw(); //服务器分页
				_subYaokongTableList.draw(); //服务器分页
				_subYaotiaoTableList.draw(); //服务器分页 */
			}else{
				_subYaoxinTableList.clear();
				_subYaoceTableList.clear();
				_subYaokongTableList.clear();
				_subYaotiaoTableList.clear();
			}
		}
		
		
		
		//加载子表
		function loadSubYaoxin(mainindex){
			var loadWaitLoad=layer.load(2);
			if($("#mainindex").val()==null||$("#mainindex").val()==''){
				$("#mainindex").val(mainindex);
			}
			$.ajax({
				url : '${serviceurlsub}/query?orderby=pointno@asc',
				data : {"search_EQ_mainindex":mainindex,"search_EQ_type":'tx'},//$("#yy-form-query").serializeArray(),
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
								var cosList=jsonResp.txList;
								if(cosList!=null&&cosList.length>0){
									var obj={};
									obj.uuid=records[i].uuid;
									obj.substatus=records[i].substatus;
									obj.type=records[i].type;
									obj.haslinks=records[i].haslinks;
									for (j = 0; j < cosList.length; j++) {
										if(cosList[j].isDisplay=='1'){
											var jsonColName=cosList[j].columnName;
											if(typeof (jsonData[jsonColName]) != "undefined"){
												eval("obj."+jsonColName+"='"+jsonData[jsonColName]+"'");
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
		function loadSubYaoce(mainindex){
			var loadWaitLoad=layer.load(2);
			if($("#mainindex").val()==null||$("#mainindex").val()==''){
				$("#mainindex").val(mainindex);
			}
			$.ajax({
				url : '${serviceurlsub}/query?orderby=pointno@asc',
				data : {"search_EQ_mainindex":mainindex,"search_EQ_type":'tc'},//$("#yy-form-query").serializeArray(),
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
								var cosList=jsonResp.tcList;
								if(cosList!=null&&cosList.length>0){
									var obj={};
									obj.uuid=records[i].uuid;
									obj.substatus=records[i].substatus;
									obj.type=records[i].type;
									obj.haslinks=records[i].haslinks;
									for (j = 0; j < cosList.length; j++) {
										if(cosList[j].isDisplay=='1'){
											var jsonColName=cosList[j].columnName;
											if(typeof (jsonData[jsonColName]) != "undefined"){
												eval("obj."+jsonColName+"='"+jsonData[jsonColName]+"'");
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
		function loadSubYaokong(mainindex){
			var loadWaitLoad=layer.load(2);
			if($("#mainindex").val()==null||$("#mainindex").val()==''){
				$("#mainindex").val(mainindex);
			}
			$.ajax({
				url : '${serviceurlsub}/query?orderby=pointno@asc',
				data : {"search_EQ_mainindex":mainindex,"search_EQ_type":'tk'},//$("#yy-form-query").serializeArray(),
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
								var cosList=jsonResp.tkList;
								if(cosList!=null&&cosList.length>0){
									var obj={};
									obj.uuid=records[i].uuid;
									obj.substatus=records[i].substatus;
									obj.type=records[i].type;
									obj.haslinks=records[i].haslinks;
									for (j = 0; j < cosList.length; j++) {
										if(cosList[j].isDisplay=='1'){
											var jsonColName=cosList[j].columnName;
											if(typeof (jsonData[jsonColName]) != "undefined"){
												eval("obj."+jsonColName+"='"+jsonData[jsonColName]+"'");
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
		function loadSubYaotiao(mainindex){
			var loadWaitLoad=layer.load(2);
			if($("#mainindex").val()==null||$("#mainindex").val()==''){
				$("#mainindex").val(mainindex);
			}
			$.ajax({
				url : '${serviceurlsub}/query?orderby=pointno@asc',
				data : {"search_EQ_mainindex":mainindex,"search_EQ_type":'tt'},//$("#yy-form-query").serializeArray(),
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
								var cosList=jsonResp.ttList;
								if(cosList!=null&&cosList.length>0){
									var obj={};
									obj.uuid=records[i].uuid;
									obj.substatus=records[i].substatus;
									obj.type=records[i].type;
									obj.haslinks=records[i].haslinks;
									for (j = 0; j < cosList.length; j++) {
										if(cosList[j].isDisplay=='1'){
											var jsonColName=cosList[j].columnName;
											if(typeof (jsonData[jsonColName]) != "undefined"){
												eval("obj."+jsonColName+"='"+jsonData[jsonColName]+"'");
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
		//查看连接内容
		function showLinksInfo(subId){
			layer.open({
				title:"查看",
			    type: 2,
			    area: ['90%', '90%'],
			    shadeClose : false,
				shade : 0.8,
				content: '${serviceurlsub}/toLinksInfoPage?subId='+subId
			});
		}
	</script>
</body>
</html>
