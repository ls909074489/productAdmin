<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="serviceurl" value="${ctx}/ver/versioninfo"/>
<html>
<head>
<title>信息表核对</title>
</head>
<body>
	<div id="yy-page" class="container-fluid page-container">
		<div class="page-content" id="yy-page-list">
			<div class="row yy-searchbar form-inline" style="text-align: center;">
				<form id="yy-form-query">
					<span class="" id="currentStationSpan"
						style="font-weight: bold;font-size: 16px;"> 
						${currentStation.name}
					</span> 
					<span style="font-weight: bold;font-size: 16px;"> 信息表核对</span> 
					
					<div class="input-group input-icon right" style="display: none;">
							<input id="stationUuid" name="station.uuid" type="hidden" value="${entity.station.uuid}"> 
							<i class="fa fa-remove" onclick="cleanDef('stationUuid','stationName');" title="清空"></i>
							<input id="stationName" name="stationName" type="text" class="form-control" readonly="readonly" 
								value="${entity.station.name}">
							<span class="input-group-btn">
								<button id="station-select-btn" class="btn btn-default btn-ref" type="button">
									<span class="glyphicon glyphicon-search"></span>
								</button>
							</span>
					</div>
				</form>
			</div>
			
			<form id="yy-form-compare">
				<div class="row" style="margin-top: 20px;">
					<div class="col-md-1"></div>
					<div class="col-md-2">
						<div class="form-group">
							<label class="control-label">标准信息点表</label>
						</div>
					</div>
					<div class="col-md-4" id="deivceSelDiv" style="display: none;">
						<div class="form-group">
							<div class="col-md-10">
								<div class="input-group input-icon right">
										<input id="deviceUuid1" name="deviceUuid1" type="hidden" value=""> 
										<i class="fa fa-remove" onclick="cleanDef('deviceUuid1','deviceName1');" title="清空"></i>
										<input id="deviceName1" name="deviceName1" type="text" class="form-control" readonly="readonly" 
											value="${entity.device.name}">
										<span class="input-group-btn">
											<button id="device-select-btn" class="btn btn-default btn-ref" type="button">
												<span class="glyphicon glyphicon-search"></span>
											</button>
										</span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label class="control-label col-md-2">版本</label>
							<div class="col-md-10">
								<select class="yy-input-enumdata form-control" id="versionNum1" name="versionNum1">
									<option value=""></option>
									<c:forEach items="${standardList}" var="clist">
										<option value="${clist.uuid}">${clist.masver}
											<c:if test="${!empty clist.subver and clist.subver ne ''}">(${clist.subver})</c:if>
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="row" class="row" style="margin-top: 10px;">
					<div class="col-md-1"></div>
					<div class="col-md-2">
						<div class="form-group">
							<label class="control-label">典型信息点表</label>
						</div>
					</div>
					<div class="col-md-4" id="stationDeivceSelDiv" style="display: none;">
						<div class="form-group">
							<div class="col-md-10">
								<div class="input-group input-icon right">
										<input id="deviceUuid2" name="deviceUuid2" type="hidden" value=""> 
										<i class="fa fa-remove" onclick="cleanDef('deviceUuid2','deviceName2');" title="清空"></i>
										<input id="deviceName2" name="deviceName2" type="text" class="form-control" readonly="readonly" 
											value="${entity.device.name}">
										<span class="input-group-btn">
											<button id="stationDevice-select-btn" class="btn btn-default btn-ref" type="button">
												<span class="glyphicon glyphicon-search"></span>
											</button>
										</span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label class="control-label col-md-2">版本</label>
							<div class="col-md-10">
								<select class="yy-input-enumdata form-control" id="versionNum2" name="versionNum2">
									<option value=""></option>
									<c:forEach items="${typicalList}" var="clist">
										<option value="${clist.uuid}">${clist.masver}
											<c:if test="${!empty clist.subver and clist.subver ne ''}">(${clist.subver})</c:if>
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="row" style="margin-top: 10px;">
					<div class="col-md-3"></div>
					<div class="col-md-8">
						<div class="form-group">
							<div class="col-md-12">
								<label><input name="type" type="checkbox" value="tx" class="" style="width: 20px;height: 20px;" 
									<c:if test="${entity.yx eq 1}">checked="checked"</c:if> onchange="changeType(this);"/>
									 <!-- 特殊 -->遥信 &nbsp;&nbsp;&nbsp;&nbsp;
								</label> 
								<label><input name="type" type="checkbox" value="tc" class="" style="width: 20px;height: 20px;" 
									<c:if test="${entity.yc eq 1}">checked="checked"</c:if> onchange="changeType(this);"/>
									<!-- 特殊 -->遥测 &nbsp;&nbsp;&nbsp;&nbsp;
								</label>
								<label><input name="type" type="checkbox" value="tk" class="" style="width: 20px;height: 20px;" 
									<c:if test="${entity.yk eq 1}">checked="checked"</c:if> onchange="changeType(this);"/>
									<!-- 特殊 -->遥控  &nbsp;&nbsp;&nbsp;&nbsp;
								</label>
								<label><input name="type" type="checkbox" value="tt" class="" style="width: 20px;height: 20px;" 
									<c:if test="${entity.yt eq 1}">checked="checked"</c:if> onchange="changeType(this);"/>
									<!-- 特殊 -->遥调  &nbsp;&nbsp;&nbsp;&nbsp;
								</label>
								<button id="yy-btn-compare" class="btn blue btn-sm" type="button">
									<i class="fa fa-save"></i> 执行对比
								</button>
							</div>
						</div>
					</div>
				</div>
				<div id="compareResultId"></div>
			</form>
			<div class="yy-tab" id="yy-page-sublist">
				<div class="tabbable-line">
					<ul class="nav nav-tabs ">
						<li class="activexxx" id="yxLiId" style="display: none;">
							<a href="#tab0" data-toggle="tab" id="yxLiDiffId"> <!-- 特殊 -->遥信</a>
						</li>
						<li id="ycLiId" style="display: none;">
							<a href="#tab1" data-toggle="tab" id="ycLiDiffId"> <!-- 特殊 -->遥测</a>
						</li>
						<li id="ykLiId" style="display: none;" id="ykLiDiffId">
							<a href="#tab2" data-toggle="tab"> <!-- 特殊 -->遥控</a>
						</li>
						<li id="ytLiId" style="display: none;" id="ytLiDiffId">
							<a href="#tab3" data-toggle="tab"> <!-- 特殊 -->遥调 </a>
						</li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="tab0" style="display: none;">
							<div class="row yy-searchbar" style="display: none;">
								<div class="col-md-4 form-inline"  style="float: left;">
									<div id="div_col${colsData.yxDisplayCount}_filter" data-column="${colsData.yxDisplayCount}">
										结果&nbsp;&nbsp;
										<%-- <input type="text"  style="width: 100px;"
										class="column_filter2yx form-control input-sm" id="col${colsData.yxDisplayCount}_filter2yx"> --%>
										<!-- 隐藏000000和111111作为判断查询过滤的条件 -->
										<select class="column_filter2yx form-control input-sm" id="col${colsData.yxDisplayCount}_filter2yx">
											<option value=""></option>
											<option value="111111">有差异</option>
											<option value="000000">无差异</option>
										</select>
									</div>
								</div>
							</div>
							<table id="yy-table-yaoxin" class="yy-table">
								<%-- <thead>
									<tr id="title1">
										<th rowspan="2">序号</th>
										<!-- <th rowspan="2" class="table-checkbox" style=""><input type="checkbox"
											class="group-checkable" data-set="#yy-table-yaoxin .checkboxes" />
										</th>
										<th>操作</th>
										<th>状态</th> -->
										<th rowspan="2">子表类型</th>
										<th colspan="${colsData.yxShowCount}" class="yxThreadTrV1">1</th>
										<th colspan="${colsData.yxShowCount}" class="yxThreadTrV2">2</th>
										<th colspan="2" class="">对比结果</th>
									</tr>
									<tr id="title2">
										<c:forEach items="${colsData.yxList}" var="list">
											<c:if test="${list.isDisplay eq 1}">
												<th>${list.columnAnno}</th>
											</c:if>
										</c:forEach>
									</tr>
								</thead> --%>
								<thead id="yxHeadId">
								</thead>
								<tbody id="yxBodyId">
		
								</tbody>
							</table>
						</div>
						<div class="tab-pane" id="tab1" style="display: none;">
							<div class="row yy-searchbar" style="display: none;">
									<div class="col-md-4 form-inline"  style="float: left;">
										<div id="div_col${colsData.ycDisplayCount}_filter" data-column="${colsData.ycDisplayCount}">
											结果&nbsp;&nbsp;<!-- 隐藏000000和111111作为判断查询过滤的条件 -->
											<select class="column_filter2yc form-control input-sm" id="col${colsData.ycDisplayCount}_filter2yc">
												<option value=""></option>
												<option value="111111">有差异</option>
												<option value="000000">无差异</option>
											</select>
										</div>
									</div>
							</div>
							<table id="yy-table-yaoce" class="yy-table-x">
								<%-- <thead>
									<tr>
										<th rowspan="2">序号</th>
										<!-- <th rowspan="2" class="table-checkbox" style=""><input type="checkbox"
											class="group-checkable" data-set="#yy-table-yaoce .checkboxes" />
										</th>
										<th>操作</th>
										<th>状态</th> -->
										<th rowspan="2">子表类型</th>
										<th colspan="${colsData.ycShowCount}" class="yxThreadTrV1">1</th>
										<th colspan="${colsData.ycShowCount}" class="yxThreadTrV2">2</th>
										<th colspan="2" class="">对比结果</th>
									</tr>
									<tr id="title2">
										<c:forEach items="${colsData.ycList}" var="list">
											<c:if test="${list.isDisplay eq 1}">
												<th>${list.columnAnno}</th>
											</c:if>
										</c:forEach>
									</tr>
								</thead> --%>
								<thead id="ycHeadId">
								</thead>
								<tbody id="ycBodyId">
								</tbody>
							</table>
						</div>
						<div class="tab-pane" id="tab2" style="display: none;">
							<div class="row yy-searchbar" style="display: none;">
									<div class="col-md-4 form-inline"  style="float: left;">
										<div id="div_col${colsData.ykDisplayCount}_filter" data-column="${colsData.ykDisplayCount}">
											结果&nbsp;&nbsp;<!-- 隐藏000000和111111作为判断查询过滤的条件 -->
											<select class="column_filter2yk form-control input-sm" id="col${colsData.ykDisplayCount}_filter2yk">
												<option value=""></option>
												<option value="111111">有差异</option>
												<option value="000000">无差异</option>
											</select>
										</div>
									</div>
							</div>
							<table id="yy-table-yaokong" class="yy-table-x">
								<%-- <thead>
									<tr>
										<th rowspan="2">序号</th>
										<!-- <th rowspan="2" class="table-checkbox" style=""><input type="checkbox"
											class="group-checkable" data-set="#yy-table-yaokong .checkboxes" />
										</th>
										<th>操作</th>
										<th>状态</th> -->
										<th rowspan="2">子表类型</th>
										<th colspan="${colsData.ykShowCount}" class="yxThreadTrV1">1</th>
										<th colspan="${colsData.ykShowCount}" class="yxThreadTrV2">2</th>
										<th colspan="2" class="">对比结果</th>
									</tr>
									<tr id="title2">
										<c:forEach items="${colsData.ykList}" var="list">
											<c:if test="${list.isDisplay eq 1}">
												<th>${list.columnAnno}</th>
											</c:if>
										</c:forEach>
									</tr>
								</thead> --%>
								<thead id="ykHeadId">
								</thead>
								<tbody id="ykBodyId">
								</tbody>
							</table>
						</div>
						<div class="tab-pane" id="tab3" style="display: none;">
							<div class="row yy-searchbar" style="display: none;">
									<div class="col-md-4 form-inline"  style="float: left;">
										<div id="div_col${colsData.ytDisplayCount}_filter" data-column="${colsData.ytDisplayCount}">
											结果&nbsp;&nbsp;<!-- 隐藏000000和111111作为判断查询过滤的条件 -->
											<select class="column_filter2yt form-control input-sm" id="col${colsData.ytDisplayCount}_filter2yt">
												<option value=""></option>
												<option value="111111">有差异</option>
												<option value="000000">无差异</option>
											</select>
										</div>
									</div>
							</div>
							<table id="yy-table-yaotiao" class="yy-table-x">
								<%-- <thead>
									<tr>
										<th rowspan="2">序号</th>
										<!--<th rowspan="2" class="table-checkbox" style=""><input type="checkbox"
											class="group-checkable" data-set="#yy-table-yaotiao .checkboxes" />
										</th>
										<th>操作</th>
										<th>状态</th> -->
										<th rowspan="2">子表类型</th>
										<th colspan="${colsData.ytShowCount}" class="yxThreadTrV1">1</th>
										<th colspan="${colsData.ytShowCount}" class="yxThreadTrV2">2</th>
										<th colspan="2" class="">对比结果</th>
									</tr>
									<tr id="title2">
										<c:forEach items="${colsData.ytList}" var="list">
											<c:if test="${list.isDisplay eq 1}">
												<th>${list.columnAnno}</th>
											</c:if>
										</c:forEach>
									</tr>
								</thead> --%>
								<thead id="ytHeadId">
								</thead>
								<tbody id="ytBodyId">
									
								</tbody>
							</table>				
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

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
		<c:otherwise>
			<script type="text/javascript">
					$("#currentStationSpan").html('${currentStation.name}');
			</script>
		</c:otherwise>
	</c:choose>

	<script type="text/javascript">

	
var jsonResp = jQuery.parseJSON('${colsJson}');
	
	/* 子表操作 */
	var _subTableYaoxinCols = [{
		data : null,
		orderable : false,
		className : "center",
		width : "40"
	}/*,{
		data : "uuid",
		orderable : false,
		className : "center",
		width : "40",
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
    } */, {
		data : 'type',
		width : "60",
		visible : false,
		className : "center"
    }];
	
	if(jsonResp){
		var cosList=jsonResp.yxList;
		if(cosList!=null&&cosList.length>0){
			for (i = 0; i < cosList.length; i++) {
				if(cosList[i].isDisplay=='1'){
					if(cosList[i].columnName=='compareResult'){
						_subTableYaoxinCols.push({data : cosList[i].columnName,width : cosList[i].width,orderable : false,className : "center",
							render : function(data, type, full) {
								if(data==0){//隐藏000000和111111作为判断查询过滤的条件
									return '<span style="display: none;">000000</span><img alt="" src="${ctx}/assets/images/right1.jpg" width="25px;" height="25px;">';
								}else if(data>0){
									return '<span style="display: none;">111111</span><img alt="" src="${ctx}/assets/images/wrong1.jpg" width="25px;" height="25px;">';
								}else{
									return "";
								}
							} });
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
		width : "40"
	}/*,{
		data : "uuid",
		orderable : false,
		className : "center",
		width : "40",
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
    } */, {
		data : 'type',
		width : "60",
		visible : false,
		className : "center"
    }];
	
	if(jsonResp){
		var cosList=jsonResp.ycList;
		if(cosList!=null&&cosList.length>0){
			for (i = 0; i < cosList.length; i++) {
				if(cosList[i].isDisplay=='1'){
					//_subTableYaoceCols.push({data : cosList[i].columnName,width : cosList[i].width,orderable : false});
					if(cosList[i].columnName=='compareResult'){
						_subTableYaoceCols.push({data : cosList[i].columnName,width : cosList[i].width,orderable : false,className : "center",
							render : function(data, type, full) {
								if(data==0){//隐藏000000和111111作为判断查询过滤的条件
									return '<span style="display: none;">000000</span><img alt="" src="${ctx}/assets/images/right1.jpg" width="25px;" height="25px;">';
								}else if(data>0){
									return '<span style="display: none;">111111</span><img alt="" src="${ctx}/assets/images/wrong1.jpg" width="25px;" height="25px;">';
								}else{
									return "";
								}
							} });
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
		width : "40"
	}/*,{
		data : "uuid",
		orderable : false,
		className : "center",
		width : "40",
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
    } */, {
		data : 'type',
		width : "60",
		visible : false,
		className : "center"
    }];
	
	if(jsonResp){
		var cosList=jsonResp.ykList;
		if(cosList!=null&&cosList.length>0){
			for (i = 0; i < cosList.length; i++) {
				if(cosList[i].isDisplay=='1'){
					//_subTableYaokongCols.push({data : cosList[i].columnName,width : cosList[i].width,orderable : false});
					if(cosList[i].columnName=='compareResult'){
						_subTableYaokongCols.push({data : cosList[i].columnName,width : cosList[i].width,orderable : false,className : "center",
							render : function(data, type, full) {
								if(data==0){//隐藏000000和111111作为判断查询过滤的条件
									return '<span style="display: none;">000000</span><img alt="" src="${ctx}/assets/images/right1.jpg" width="25px;" height="25px;">';
								}else if(data>0){
									return '<span style="display: none;">111111</span><img alt="" src="${ctx}/assets/images/wrong1.jpg" width="25px;" height="25px;">';
								}else{
									return "";
								}
							} });
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
		width : "40"
	}/*,{
		data : "uuid",
		orderable : false,
		className : "center",
		width : "40",
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
    } */, {
		data : 'type',
		width : "60",
		visible : false,
		className : "center"
    }];
	
	if(jsonResp){
		var cosList=jsonResp.ytList;
		if(cosList!=null&&cosList.length>0){
			for (i = 0; i < cosList.length; i++) {
				if(cosList[i].isDisplay=='1'){
					//_subTableYaotiaoCols.push({data : cosList[i].columnName,width : cosList[i].width,orderable : false});
					if(cosList[i].columnName=='compareResult'){
						_subTableYaotiaoCols.push({data : cosList[i].columnName,width : cosList[i].width,orderable : false,className : "center",
							render : function(data, type, full) {
								if(data==0){//隐藏000000和111111作为判断查询过滤的条件
									return '<span style="display: none;">000000</span><img alt="" src="${ctx}/assets/images/right1.jpg" width="25px;" height="25px;">';
								}else if(data>0){
									return '<span style="display: none;">111111</span><img alt="" src="${ctx}/assets/images/wrong1.jpg" width="25px;" height="25px;">';
								}else{
									return "";
								}
							} });
					}else{
						_subTableYaotiaoCols.push({data : cosList[i].columnName,width : cosList[i].width,orderable : false});
					}
				}
			}
		}
	}
	
	//设置当前厂商
	function callSelectSessionStation(data){
		$("#currentStationSpan").html(data.name);
	}
	
	function filterColumnSel(i,t) {
		if(t=='yx'){
			$('#yy-table-yaoxin').DataTable().column(i).search(
					$('#col' + i + '_filter2'+t).val(), false, true).draw();
		}else if(t=='yc'){
			$('#yy-table-yaoce').DataTable().column(i).search(
					$('#col' + i + '_filter2'+t).val(), false, true).draw();
		}else if(t=='yk'){
			$('#yy-table-yaokong').DataTable().column(i).search(
					$('#col' + i + '_filter2'+t).val(), false, true).draw();
		}else if(t=='yt'){
			$('#yy-table-yaotiao').DataTable().column(i).search(
					$('#col' + i + '_filter2'+t).val(), false, true).draw();
		}
	}
	
	
	$(document).ready(function() {
		
		YYUI.setUIAction('yy-table-yaoxin');//设置子表勾选全选
		YYUI.setUIAction('yy-table-yaoce');//设置子表勾选全选
		YYUI.setUIAction('yy-table-yaokong');//设置子表勾选全选
		YYUI.setUIAction('yy-table-yaotiao');//设置子表勾选全选
		
		$("#stationUuid").val('${currentStation.uuid}');
		$("#stationName").val('${currentStation.name}');
		
		bindButtonActions();
		
		validateForms();
		
		
		/* //子表
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
		}); */
		
		$('select.column_filter2yx').on('keyup click', function() {
			filterColumnSel($(this).parents('div').attr('data-column'),"yx");
		});
		$('select.column_filter2yc').on('keyup click', function() {
			filterColumnSel($(this).parents('div').attr('data-column'),"yc");
		});
		$('select.column_filter2yk').on('keyup click', function() {
			filterColumnSel($(this).parents('div').attr('data-column'),"yk");
		});
		$('select.column_filter2yt').on('keyup click', function() {
			filterColumnSel($(this).parents('div').attr('data-column'),"yt");
		});
		 
	});
		
	
	function bindButtonActions(){
		
		//设备类型改变事件
		$("#versionType1").change(function(){
			if($(this).val()==1){//设备
				$("#versionNum1").empty();
				$("#deivceSelDiv").show();
			}else{
				$("#deivceSelDiv").hide();
				
				var stationVersionLoading = layer.load(2);
				$.ajax({
					type : "POST",
					data :{"tid": $(this).val(),"sid":'${currentStation.uuid}'},
					url : "${serviceurl}/dataByTidSid",
					async : true,
					dataType : "json",
					success : function(data) {
						$("#versionNum1").empty();
						$("#versionNum1").append("<option value=''></option>");
						layer.close(stationVersionLoading);
						if (data.success) {
							YYUI.succMsg('查询成功');
							var records=data.records;
							for (var i = 0; i < records.length; i++) {
			                   $("#versionNum1").append("<option value=" + records[i].uuid + ">" + records[i].masver + "</option>");
			               }
						}else {
							YYUI.promAlert("查询失败："+data.msg);
						}
					},
					error : function(data) {
						layer.close(stationVersionLoading);
						YYUI.promAlert(YYMsg.alertMsg('sys-submit-http',null));
					}
				});
			}
		});
		
		//主站类型改变事件
		$("#versionType2").change(function(){
			if($(this).val()==1){//设备
				$("#versionNum2").empty();
				$("#stationDeivceSelDiv").show();
			}else{
				$("#stationDeivceSelDiv").hide();
				
				var stationVersionLoading = layer.load(2);
				$.ajax({
					type : "POST",
					data :{"tid": $(this).val(),"sid":'${currentStation.uuid}'},
					url : "${serviceurl}/dataByTidSid",
					async : true,
					dataType : "json",
					success : function(data) {
						$("#versionNum2").empty();
						$("#versionNum2").append("<option value=''></option>");
						layer.close(stationVersionLoading);
						if (data.success) {
							YYUI.succMsg('查询成功');
							var records=data.records;
							for (var i = 0; i < records.length; i++) {
			                   $("#versionNum2").append("<option value=" + records[i].uuid + ">" + records[i].masver + "</option>");
			               }
						}else {
							YYUI.promAlert("查询失败："+data.msg);
						}
					},
					error : function(data) {
						layer.close(stationVersionLoading);
						YYUI.promAlert(YYMsg.alertMsg('sys-submit-http',null));
					}
				});
			}
		});
		
		
		$('#device-select-btn').on('click', function() {
			var sid=$("#stationUuid").val();
			if(sid!=null&&sid!=''){
				layer.open({
					type : 2,
					title : '请选择设备',
					shadeClose : false,
					shade : 0.8,
					area : [ '1000px', '90%' ],
					content : '${ctx}/sys/ref/refDeviceSel?callBackMethod=window.parent.callBackDevice&sid='+sid
				});
			}else{
				YYUI.promMsg('请先选择当前设备的厂站');
			}
		});
		
		$('#stationDevice-select-btn').on('click', function() {
			var sid=$("#stationUuid").val();
			if(sid!=null&&sid!=''){
				layer.open({
					type : 2,
					title : '请选择设备',
					shadeClose : false,
					shade : 0.8,
					area : [ '1000px', '90%' ],
					content : '${ctx}/sys/ref/refDeviceSel?callBackMethod=window.parent.callBackStationDevice&sid='+sid
				});
			}else{
				YYUI.promMsg('请先选择当前设备的厂站');
			}
		});
		
		//执行对比
		$("#yy-btn-compare").bind("click", function() {
			compareVersion();
		});
	}
	
	
	//回调选择
	function callBackDevice(data){
		$("#deviceUuid1").val(data.uuid);
		$("#deviceName1").val(data.name);

		var stationVersionLoading = layer.load(2);
		$.ajax({
			type : "POST",
			data :{"deviceId":data.uuid},
			url : "${serviceurl}/dataByDeviceId",
			async : true,
			dataType : "json",
			success : function(data) {
				$("#versionNum1").empty();
				$("#versionNum1").append("<option value=''></option>");
				layer.close(stationVersionLoading);
				if (data.success) {
					YYUI.succMsg('查询成功');
					var records=data.records;
					for (var i = 0; i < records.length; i++) {
	                   $("#versionNum1").append("<option value=" + records[i].uuid + ">" + records[i].masver + "</option>");
	               }
				}else {
					YYUI.promAlert("查询失败："+data.msg);
				}
			},
			error : function(data) {
				layer.close(stationVersionLoading);
				YYUI.promAlert(YYMsg.alertMsg('sys-submit-http',null));
			}
		});
	}
	//回调选择
	function callBackStationDevice(data){
		$("#deviceUuid2").val(data.uuid);
		$("#deviceName2").val(data.name);
		
		var stationVersionLoading = layer.load(2);
		$.ajax({
			type : "POST",
			data :{"deviceId":data.uuid},
			url : "${serviceurl}/dataByDeviceId",
			async : true,
			dataType : "json",
			success : function(data) {
				$("#versionNum2").empty();
				$("#versionNum2").append("<option value=''></option>");
				layer.close(stationVersionLoading);
				if (data.success) {
					YYUI.succMsg('查询成功');
					var records=data.records;
					for (var i = 0; i < records.length; i++) {
	                   $("#versionNum2").append("<option value=" + records[i].uuid + ">" + records[i].masver + "</option>");
	               }
				}else {
					YYUI.promAlert("查询失败："+data.msg);
				}
			},
			error : function(data) {
				layer.close(stationVersionLoading);
				YYUI.promAlert(YYMsg.alertMsg('sys-submit-http',null));
			}
		});
	}
	
	//表单校验
	function validateForms(){
		validata = $('#yy-form-compare').validate({
			onsubmit : true,
			rules : {
				'versionType1' : {required : true},
				'versionType2' : {required : true},
				'versionNum1' : {required : true},
				'versionNum2' : {required : true},
				'type' : {required : true}
			}
		});
	}
	
	//执行对比
	function compareVersion(){
		if (!$('#yy-form-compare').valid()) return false;
		
		var editview = layer.load(2);
		var opt = {
			url : "${serviceurl}/dataCheckResult",
			type : "post",
			success : function(data) {
				//$("#compareResultId").html("读取结果："+data.msg);
				if (data.success) {
					layer.close(editview);
					
					/* if($("#versionType1").val()==1){//设备
						$(".yxThreadTrV1").html($("#versionType1").find("option:selected").text()+"("+$("#deviceName1").val()+")版本"+$("#versionNum1").find("option:selected").text());
					}else{
						$(".yxThreadTrV1").html($("#versionType1").find("option:selected").text()+"版本"+$("#versionNum1").find("option:selected").text());
					}
					if($("#versionType2").val()==1){//设备
						$(".yxThreadTrV2").html($("#versionType2").find("option:selected").text()+"("+$("#deviceName2").val()+")版本"+$("#versionNum2").find("option:selected").text());
					}else{
						$(".yxThreadTrV2").html($("#versionType2").find("option:selected").text()+"版本"+$("#versionNum2").find("option:selected").text());
					}
					
					if(data.records!=null&&data.records.length>0){
						loadSubTable(_subYaoxinTableList,data.records[0].yxList,jsonResp.yxList);
						loadSubTable(_subYaoceTableList,data.records[0].ycList,jsonResp.ycList);
						loadSubTable(_subYaokongTableList,data.records[0].ykList,jsonResp.ykList);
						loadSubTable(_subYaotiaoTableList,data.records[0].ytList,jsonResp.ytList);
						
						$("#yxLiDiffId").html("遥信("+data.records[0].yxFailCount+")");
						$("#ycLiDiffId").html("遥测("+data.records[0].ycFailCount+")");
						$("#ykLiDiffId").html("遥控("+data.records[0].ykFailCount+")");
						$("#ytLiDiffId").html("遥调("+data.records[0].ytFailCount+")");
					}else{
						$("#yxLiDiffId").html("遥信");
						$("#ycLiDiffId").html("遥测");
						$("#ykLiDiffId").html("遥控");
						$("#ytLiDiffId").html("遥调");
					} */
					if(data.msg!=null&&data.msg!=''){
						var jsonData =jQuery.parseJSON(data.msg);
						var jsonRecords=jsonData.records;
						if(jsonRecords!=null&&jsonRecords.length>0){
							YYUI.succMsg('对比成功!');
							for(var i=0;i<jsonRecords.length;i++){
								var tVtype=jsonRecords[i].vtype;
								var tContent=jsonRecords[i].content;
								if(tVtype=='tx'&&tContent!=null&&tContent.length>0){
									//显示表头start
									var cosList=jsonResp.txCompares;
									if(cosList!=null&&cosList.length>0){
										var ycHeadHtml="<tr><th>序号</th>";
										for (k = 0; k < cosList.length; k++) {
											if(cosList[k].isDisplay=='1'){
												ycHeadHtml+=("<th>"+cosList[k].columnAnno+"</th>");
											}
										}
										ycHeadHtml+="</tr>";
										$("#yxHeadId").html(ycHeadHtml);
									}	
									//显示表头end
									var tBodyHtml="";
									for(var j=0;j<tContent.length;j++){
									  tBodyHtml+="<tr><td>"+(j+1)+"</td>";
									  for(var o in tContent[j]){
										  tBodyHtml+="<td>"+tContent[j][o]+"</td>";
								      }  
									  tBodyHtml+="</tr>";
									}
									$("#yxBodyId").html(tBodyHtml);
								}else if(tVtype=='tc'&&tContent!=null&&tContent.length>0){
									//显示表头start
									var cosList=jsonResp.tcCompares;
									if(cosList!=null&&cosList.length>0){
										var ycHeadHtml="<tr><th>序号</th>";
										for (k = 0; k < cosList.length; k++) {
											if(cosList[k].isDisplay=='1'){
												ycHeadHtml+=("<th>"+cosList[k].columnAnno+"</th>");
											}
										}
										ycHeadHtml+="</tr>";
										$("#ycHeadId").html(ycHeadHtml);
									}	
									//显示表头end
									var tBodyHtml="";
									for(var j=0;j<tContent.length;j++){
									  tBodyHtml+="<tr><td>"+(j+1)+"</td>";
									  for(var o in tContent[j]){
										  tBodyHtml+="<td>"+tContent[j][o]+"</td>";
								      }  
									  tBodyHtml+="</tr>";
									}
									$("#ycBodyId").html(tBodyHtml);
								}else if(tVtype=='tk'&&tContent!=null&&tContent.length>0){
									//显示表头start
									var cosList=jsonResp.tkCompares;
									if(cosList!=null&&cosList.length>0){
										var ycHeadHtml="<tr><th>序号</th>";
										for (k = 0; k < cosList.length; k++) {
											if(cosList[k].isDisplay=='1'){
												ycHeadHtml+=("<th>"+cosList[k].columnAnno+"</th>");
											}
										}
										ycHeadHtml+="</tr>";
										$("#ykHeadId").html(ycHeadHtml);
									}	
									//显示表头end
									var tBodyHtml="";
									for(var j=0;j<tContent.length;j++){
									  tBodyHtml+="<tr><td>"+(j+1)+"</td>";
									  for(var o in tContent[j]){
										  tBodyHtml+="<td>"+tContent[j][o]+"</td>";
								      }  
									  tBodyHtml+="</tr>";
									}
									//$("#ykHeadId").html();
									$("#ykBodyId").html(tBodyHtml);
								}else if(tVtype=='tt'&&tContent!=null&&tContent.length>0){
									//显示表头start
									var cosList=jsonResp.ttCompares;
									if(cosList!=null&&cosList.length>0){
										var ycHeadHtml="<tr><th>序号</th>";
										for (k = 0; k < cosList.length; k++) {
											if(cosList[k].isDisplay=='1'){
												ycHeadHtml+=("<th>"+cosList[k].columnAnno+"</th>");
											}
										}
										ycHeadHtml+="</tr>";
										$("#ytHeadId").html(ycHeadHtml);
									}	
									//显示表头end
									var tBodyHtml="";
									for(var j=0;j<tContent.length;j++){
									  tBodyHtml+="<tr><td>"+(j+1)+"</td>";
									  for(var o in tContent[j]){
										  tBodyHtml+="<td>"+tContent[j][o]+"</td>";
								      }  
									  tBodyHtml+="</tr>";
									}
									//$("#ytHeadId").html();
									$("#ytBodyId").html(tBodyHtml);
								}
							}
						}else{
							YYUI.promMsg('对比返回结果为空!');
						}
					}else{
						YYUI.promMsg('对比返回结果为空!');
						$("#yxBodyId").html("");
						$("#ycBodyId").html("");
						$("#ykBodyId").html("");
						$("#ytBodyId").html("");
					}
				} else {
					window.parent.YYUI.failMsg("对比失败：" + data.msg);
					layer.close(editview);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				window.parent.YYUI.promAlert("对比失败，HTTP错误。");
				layer.close(editview);
			}
		}
		$("#yy-form-compare").ajaxSubmit(opt);
	}
	
	function loadSubTable(_subTableList,records,cosList){
		_subTableList.clear();
		if(records!=null&&records.length>0){
			var dataRecords=new Array();
			for (i = 0; i < records.length; i++) {
				var jsonData = null;
				var jsonData2 =null;
				if(records[i].content1!=null&&records[i].content1!=''){
					jsonData = jQuery.parseJSON(records[i].content1);//数据库保存的信息
				}
				if(records[i].content2!=null&&records[i].content2!=''){
					jsonData2 = jQuery.parseJSON(records[i].content2);//数据库保存的信息
				}
				
				if(jsonResp){
					if(cosList!=null&&cosList.length>0){
						var obj={};
						obj.uuid="";
						obj.substatus="";
						obj.type="";
						obj.compareDiff=records[i].compareDiff;
						obj.compareResult=records[i].compareResult;
						
						for (j = 0; j < cosList.length; j++) {
							if(cosList[j].isDisplay=='1'){
								var jsonColName=cosList[j].columnName;
								jsonColName=jsonColName.replace("tab1","");
								
								if(jsonData!=null){
									if(typeof (jsonData[jsonColName]) != "undefined"){
										eval("obj.tab1"+jsonColName+"='"+jsonData[jsonColName]+"'");
									}else{
										eval("obj.tab1"+jsonColName+"=null");
									}
								}else{
									eval("obj.tab1"+jsonColName+"=null");
								}
								
								var jsonColName2=cosList[j].columnName;
								jsonColName2=jsonColName2.replace("tab2","");
								if(jsonData2!=null){
									if(typeof (jsonData2[jsonColName2]) != "undefined"){
										eval("obj.tab2"+jsonColName2+"='"+jsonData2[jsonColName2]+"'");
									}else{
										eval("obj.tab2"+jsonColName2+"=null");
									}
								}else{
									eval("obj.tab2"+jsonColName2+"=null");
								}
							}
						}
						dataRecords.push(obj);
					}
				}
			}
			_subTableList.rows.add(dataRecords);
		}
		_subTableList.on('order.dt search.dt',
		        function() {
			_subTableList.column(0, {
				        search: 'applied',
				        order: 'applied'
			        }).nodes().each(function(cell, i) {
				        cell.innerHTML = i + 1;
			        });
		}).draw();
	}
	
	
	//复选框改变事件
	function changeType(t){
		var checkVal=$(t).val();
		if($(t).is(':checked')){
			if(checkVal=='tx'){
				$("#yxLiId").css('display', '');
				$("#tab0").css('display', '');
				$("#yxLiId").addClass("active");
				$("#ycLiId").removeClass("active");
				$("#ykLiId").removeClass("active");
				$("#ytLiId").removeClass("active");
				$("#tab0").addClass("active");
				$("#tab1").removeClass("active");
				$("#tab2").removeClass("active");
				$("#tab3").removeClass("active");
			}else if(checkVal=='tc'){
				$("#ycLiId").css('display', '');
				$("#tab1").css('display', '');
				$("#yxLiId").removeClass("active");
				$("#ycLiId").addClass("active");
				$("#ykLiId").removeClass("active");
				$("#ytLiId").removeClass("active");
				$("#tab0").removeClass("active");
				$("#tab1").addClass("active");
				$("#tab2").removeClass("active");
				$("#tab3").removeClass("active");
			}else if(checkVal=='tk'){
				$("#ykLiId").css('display', '');
				$("#tab2").css('display', '');
				$("#yxLiId").removeClass("active");
				$("#ycLiId").removeClass("active");
				$("#ykLiId").addClass("active");
				$("#ytLiId").removeClass("active");
				$("#tab0").removeClass("active");
				$("#tab1").removeClass("active");
				$("#tab2").addClass("active");
				$("#tab3").removeClass("active");
			}else if(checkVal=='tt'){
				$("#ytLiId").css('display', '');
				$("#tab3").css('display', '');
				$("#yxLiId").removeClass("active");
				$("#ycLiId").removeClass("active");
				$("#ykLiId").removeClass("active");
				$("#ytLiId").addClass("active");
				$("#tab0").removeClass("active");
				$("#tab1").removeClass("active");
				$("#tab2").removeClass("active");
				$("#tab3").addClass("active");
			}
		}else{
			if(checkVal=='tx'){
				$("#yxLiId").hide();
				$("#tab0").hide();
				
				/* $("#tab1").css('display', 'inline');	
				$("#tab2").css('display', 'inline');	
				$("#tab3").css('display', 'inline'); */	
			}else if(checkVal=='tc'){
				$("#ycLiId").hide();
				$("#tab1").hide();
				
				/* $("#tab0").css('display', 'inline');	
				$("#tab2").css('display', 'inline');	
				$("#tab3").css('display', 'inline'); */	
			}else if(checkVal=='tk'){
				$("#ykLiId").hide();
				$("#tab2").hide();
				
				/* $("#tab0").css('display', 'inline');	
				$("#tab1").css('display', 'inline');	
				$("#tab3").css('display', 'inline'); */	
			}else if(checkVal=='tt'){
				$("#ytLiId").hide();
				$("#tab3").hide();
				
				/* $("#tab0").css('display', 'inline');	
				$("#tab1").css('display', 'inline');	
				$("#tab2").css('display', 'inline'); */	
			}
		}
	}
	</script>
</body>
</html>	

