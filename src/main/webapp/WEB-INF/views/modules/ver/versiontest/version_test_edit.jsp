<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serviceurl" value="${ctx}/ver/versionTest"/>
<c:set var="serviceurlsub" value="${ctx}/ver/versionTestSub"/>
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
			<!-- <button id="yy-btn-submit" class="btn yellow btn-sm btn-info">
				<i class="fa fa-send"></i> 保存提交
			</button> -->
			<button id="yy-btn-export" class="btn green btn-sm btn-info">
				<i class="fa fa-chevron-up"></i> 导出
			</button>
		</div>
	<div>
		<form id="yy-form-edit" class="form-horizontal yy-form-edit">
			<input name="uuid" type="hidden" value="${entity.uuid}">
			<div class="row">
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label col-md-2 required">名称</label>
						<div class="col-md-10">
							<input name="name" type="text" class="form-control" value="${entity.name}">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">核对日期</label>
						<div class="col-md-8">
							<input type="text" name="checkingDate" id="checkingDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate form-control" value="${entity.checkingDate}">
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">审核人员</label>
						<div class="col-md-8">
							<div class="input-group input-icon right">
									<input id="masterChargerUuid" name="masterCharger.uuid" type="hidden" value="${entity.masterCharger.uuid}"> 
									<i class="fa fa-remove" onclick="cleanDef('masterChargerUuid','masterChargerName');" title="清空"></i>
									<input id="masterChargerName" name="masterChargerName" type="text" class="form-control" readonly="readonly" 
										value="${entity.masterCharger.username}">
									<span class="input-group-btn">
										<button id="masterCharger-select-btn" class="btn btn-default btn-ref" type="button">
											<span class="glyphicon glyphicon-search"></span>
										</button>
									</span>
								</div>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">主站核对人</label>
						<div class="col-md-8">
							<div class="input-group input-icon right">
								<input id="stationChargerUuid" name="stationCharger.uuid" type="hidden" value="${entity.stationCharger.uuid}"> 
								<i class="fa fa-remove" onclick="cleanDef('stationChargerUuid','stationChargerName');" title="清空"></i>
								<input id="stationChargerName" name="stationChargerName" type="text" class="form-control" readonly="readonly" 
									value="${entity.stationCharger.username}">
								<span class="input-group-btn">
									<button id="stationCharger-select-btn" class="btn btn-default btn-ref" type="button">
										<span class="glyphicon glyphicon-search"></span>
									</button>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">测试方式</label>
						<div class="col-md-8">
							<select name="testMethod" class="yy-input-enumdata form-control" data-enum-group="TestMethod"></select>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">主站测试结论</label>
						<div class="col-md-8">
							<input name="masterTestMsg" type="text" class="form-control" value="${entity.masterTestMsg}">
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">厂站测试人员</label>
						<div class="col-md-8">
							<input name="stationTestPerson" type="text" class="form-control" value="${entity.stationTestPerson}">
						</div>
					</div>
				</div>
			</div>
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
						<!-- <li>
							<a href="#tab3" data-toggle="tab"> 遥调 </a>
						</li> -->
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="tab0">
							<div class="row yy-toolbar" id="subListToolId" style="">
								<!-- <button id="yy-btn-pass-yxall" class="btn btn-sm btn-info edit" 
									data-rel="tooltip" title="通过">
									<i class="fa fa-check"></i>通过
								</button>
								<button id="yy-btn-question-yxall" class="btn btn-sm btn-danger delete" 
									data-rel="tooltip" title="质疑">
									<i class="fa fa-hourglass-2"></i>质疑
								</button> -->
								<label><input type="radio" name="yxEditDevice" value="1" checked="checked" class="subRowDevice">通信管理机A</label>
								<label><input type="radio" name="yxEditDevice" value="2" class="subRowDevice">通信管理机B</label>
								
								<div role="form" class="form-inline" style="display: inline-block;">
									<form id="yy-form-subquery-yx">	
										<input type="hidden" name="search_EQ_main.uuid" id="" value="${entity.uuid}" class="yy-input">	
										<input type="hidden" name="search_IN_type" id="" value="yx,tx" class="yy-input">
										&nbsp;&nbsp;	
										<label for="search_EQ_substatus" class="control-label">点号名称</label>
										<!-- <select class="yy-input-enumdata form-control" id="search_EQ_substatus" name="search_EQ_substatus" data-enum-group="VersionSubStatus"></select> -->
										<input type="text" autocomplete="on" name="search_LIKE_pointname"
											id="search_LIKE_pointname" class="form-control input-sm">
										
										&nbsp;&nbsp;	
										<button class="yy-btn-searchSub" type="button" class="btn btn-sm btn-info">
											<i class="fa fa-search"></i>查询
										</button>
										<button class="yy-searchbar-resetSub" type="reset" class="red">
											<i class="fa fa-undo"></i> 清空
										</button>	
									</form>
								</div>
							</div>
							<table id="yy-table-yaoxin" class="yy-table">
								<thead>
									<tr id="title1">
										<th rowspan="2">序号</th>
										<th class="table-checkbox" style="" rowspan="2"><input type="checkbox"
											class="group-checkable" data-set="#yy-table-yaoxin .checkboxes" />
										</th>
										<th rowspan="2">操作</th>
										<th rowspan="2">子表类型</th>
										<th rowspan="2">遥信点号</th>
										<th rowspan="2">点号名称</th>
										<th colspan="3" class="yxThreadTrV1">通信管理机A</th>
										<th colspan="3" class="yxThreadTrV2">通信管理机B</th>
									</tr>
									<tr id="title2">
										<th>测试是否通过</th>
										<th>测试时间</th>
										<th>备注</th>
										<th>测试是否通过</th>
										<th>测试时间</th>
										<th>备注</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						<div class="tab-pane" id="tab1">
							<div class="row yy-toolbar" id="subListToolId" style="">
								<!-- <button id="yy-btn-pass-ycall" class="btn btn-sm btn-info edit" 
									data-rel="tooltip" title="通过">
									<i class="fa fa-check"></i>通过
								</button>
								<button id="yy-btn-question-ycall" class="btn btn-sm btn-danger delete" 
									data-rel="tooltip" title="质疑">
									<i class="fa fa-hourglass-2"></i>质疑
								</button> -->
								
								<label><input type="radio" name="ycEditDevice" value="1" checked="checked" class="subRowDevice">通信管理机A</label>
								<label><input type="radio" name="ycEditDevice" value="2" class="subRowDevice">通信管理机B</label>
								
								<div role="form" class="form-inline" style="display: inline-block;">
									<form id="yy-form-subquery-yc">	
										<input type="hidden" name="search_EQ_main.uuid" id="" value="${entity.uuid}" class="yy-input" class="yy-input">	
										<input type="hidden" name="search_IN_type" id="" value="yc,tc" class="yy-input" class="yy-input">
										&nbsp;&nbsp;	
										<label for="search_EQ_substatus" class="control-label">点号名称</label>
										<!-- <select class="yy-input-enumdata form-control" id="search_EQ_substatus" name="search_EQ_substatus" data-enum-group="VersionSubStatus"></select> -->
										<input type="text" autocomplete="on" name="search_LIKE_pointname"
											id="search_LIKE_pointname" class="form-control input-sm">
										
										&nbsp;&nbsp;	
										<button class="yy-btn-searchSub" type="button" class="btn btn-sm btn-info">
											<i class="fa fa-search"></i>查询
										</button>
										<button class="yy-searchbar-resetSub" type="reset" class="red">
											<i class="fa fa-undo"></i> 清空
										</button>	
									</form>
								</div>
							</div>
							<table id="yy-table-yaoce" class="yy-table">
								<thead>
									<tr id="title1">
										<th rowspan="2">序号</th>
										<th class="table-checkbox" style="" rowspan="2"><input type="checkbox"
											class="group-checkable" data-set="#yy-table-yaoce .checkboxes" />
										</th>
										<th rowspan="2">操作</th>
										<th rowspan="2">子表类型</th>
										<th rowspan="2">点号</th>
										<th rowspan="2">名称</th>
										<th colspan="7" class="yxThreadTrV1">通信管理机A</th>
										<th colspan="7" class="yxThreadTrV2">通信管理机B</th>
									</tr>
									<tr id="title2">
										<th>主站端显示值</th>
										<th>测试时间</th>
										<th>厂站端显示值</th>
										<th>测试时间</th>
										<th>误差值</th>
										<th>是否合格</th>
										<th>备注</th>
										<th>主站端显示值</th>
										<th>测试时间</th>
										<th>厂站端显示值</th>
										<th>测试时间</th>
										<th>误差值</th>
										<th>是否合格</th>
										<th>备注</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						<div class="tab-pane" id="tab2">
							<div class="row yy-toolbar" id="subListToolId" style="">
								<!-- <button id="yy-btn-pass-ykall" class="btn btn-sm btn-info edit" 
									data-rel="tooltip" title="通过">
									<i class="fa fa-check"></i>通过
								</button>
								<button id="yy-btn-question-ykall" class="btn btn-sm btn-danger delete" 
									data-rel="tooltip" title="质疑">
									<i class="fa fa-hourglass-2"></i>质疑
								</button> -->
								
								<label><input type="radio" name="ykEditDevice" value="1" checked="checked" class="subRowDevice">通信管理机A</label>
								<label><input type="radio" name="ykEditDevice" value="2" class="subRowDevice">通信管理机B</label>
								
								<div role="form" class="form-inline" style="display: inline-block;">
									<form id="yy-form-subquery-yk">	
										<input type="hidden" name="search_EQ_main.uuid" id="" value="${entity.uuid}" class="yy-input">	
										<input type="hidden" name="search_IN_type" id="" value="yk,tk" class="yy-input">
										&nbsp;&nbsp;	
										<label for="search_EQ_substatus" class="control-label">点号名称</label>
										<!-- <select class="yy-input-enumdata form-control" id="search_EQ_substatus" name="search_EQ_substatus" data-enum-group="VersionSubStatus"></select> -->
										<input type="text" autocomplete="on" name="search_LIKE_pointname"
											id="search_LIKE_pointname" class="form-control input-sm">
										
										&nbsp;&nbsp;	
										<button class="yy-btn-searchSub" type="button" class="btn btn-sm btn-info">
											<i class="fa fa-search"></i>查询
										</button>
										<button class="yy-searchbar-resetSub" type="reset" class="red">
											<i class="fa fa-undo"></i> 清空
										</button>	
									</form>
								</div>
							</div>
							<table id="yy-table-yaokong" class="yy-table">
								<thead>
									<tr id="title1">
										<th rowspan="2">序号</th>
										<th class="table-checkbox" style="" rowspan="2"><input type="checkbox"
											class="group-checkable" data-set="#yy-table-yaokong .checkboxes" />
										</th>
										<th rowspan="2">操作</th>
										<th rowspan="2">子表类型</th>
										<th rowspan="2">点号</th>
										<th rowspan="2">名称</th>
										<th rowspan="2">遥信点号</th>
										<th rowspan="2">遥信名称</th>
										<th colspan="5" class="yxThreadTrV1">通信管理机A</th>
										<th colspan="5" class="yxThreadTrV2">通信管理机B</th>
									</tr>
									<tr id="title2">
										<th>遥控执行</th>
										<th>测试时间</th>
										<th>遥信返回</th>
										<th>测试时间</th>
										<th>备注</th>
										<th>遥控执行</th>
										<th>测试时间</th>
										<th>遥信返回</th>
										<th>测试时间</th>
										<th>备注</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						<%-- <div class="tab-pane" id="tab3">
							<div class="row yy-toolbar" id="subListToolId" style="">
								<!-- <button id="yy-btn-pass-ytall" class="btn btn-sm btn-info edit" 
									data-rel="tooltip" title="通过">
									<i class="fa fa-check"></i>通过
								</button>
								<button id="yy-btn-question-ytall" class="btn btn-sm btn-danger delete" 
									data-rel="tooltip" title="质疑">
									<i class="fa fa-hourglass-2"></i>质疑
								</button> -->
								
								<label><input type="radio" name="ytEditDevice" value="1" checked="checked" class="subRowDevice">通信管理机A</label>
								<label><input type="radio" name="ytEditDevice" value="2" class="subRowDevice">通信管理机B</label>
								
								<div role="form" class="form-inline" style="display: inline-block;">
									<form id="yy-form-subquery-yt">	
										<input type="hidden" name="search_EQ_main.uuid" id="" value="${entity.uuid}" class="yy-input">	
										<input type="hidden" name="search_IN_type" id="" value="yt,tt" class="yy-input">
										&nbsp;&nbsp;	
										<label for="search_EQ_substatus" class="control-label">点号名称</label>
										<!-- <select class="yy-input-enumdata form-control" id="search_EQ_substatus" name="search_EQ_substatus" data-enum-group="VersionSubStatus"></select> -->
										<input type="text" autocomplete="on" name="search_LIKE_pointname"
											id="search_LIKE_pointname" class="form-control input-sm">
										
										&nbsp;&nbsp;	
										<button class="yy-btn-searchSub" type="button" class="btn btn-sm btn-info">
											<i class="fa fa-search"></i>查询
										</button>
										<button class="yy-searchbar-resetSub" type="reset" class="red">
											<i class="fa fa-undo"></i> 清空
										</button>	
									</form>
								</div>
							</div>
							<table id="yy-table-yaotiao" class="yy-table">
								<thead>
									<tr id="title1">
										<th rowspan="2">序号</th>
										<th class="table-checkbox" style="" rowspan="2"><input type="checkbox"
											class="group-checkable" data-set="#yy-table-yaotiao .checkboxes" />
										</th>
										<th rowspan="2">操作</th>
										<th rowspan="2">子表类型</th>
										<th rowspan="2">点号</th>
										<th rowspan="2">名称</th>
										<th colspan="7" class="yxThreadTrV1">通信管理机A</th>
										<th colspan="7" class="yxThreadTrV2">通信管理机B</th>
									</tr>
									<tr id="title2">
										<th>主站端显示值</th>
										<th>测试时间</th>
										<th>厂站端显示值</th>
										<th>测试时间</th>
										<th>误差值</th>
										<th>是否合格</th>
										<th>备注</th>
										<th>主站端显示值</th>
										<th>测试时间</th>
										<th>厂站端显示值</th>
										<th>测试时间</th>
										<th>误差值</th>
										<th>是否合格</th>
										<th>备注</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>				
						</div> --%>
					</div>
				</div>
			</div>
	</div>
	<!-- 公用脚本 -->
	<%@include file="/WEB-INF/views/common/editscript.jsp"%>
	<script type="text/javascript">
	
	
		/* 子表操作 */
		var _subTableYaoxinCols = [{
			data : null,
			orderable : false,
			className : "center",
			width : "20"
		},{
			data : "uuid",
			orderable : false,
			className : "center",
			width : "40",
			visible : false,
			render : YYDataTableUtils.renderCheckCol
		},{
			data : "uuid",
			orderable : false,
			className : "center",
			width : "30",
			render : function(data, type, full) {
				return "<div class='btn-group rap-btn-actiongroup'>"
				+ "<button id='yy-btn-edit-row' class='btn btn-xs btn-info edit' data-rel='tooltip' title='编辑'><i class='fa fa-edit'></i>编辑</button>"
				+ "</div>";
	        }
		}, {
			data : 'type',
			width : "60",
			visible : false,
			className : "center"
	    }, {
			data : 'pointno',
			width : "30",
			className : "center"
	    }, {
			data : 'pointname',
			width : "60",
			className : "center"
	    }, {
			data : 'isPass1',
			width : "40",
			className : "center" ,
			render : function(data, type, full) {
			       return YYDataUtils.getEnumName("BooleanType", data);
			}
	    }, {
			data : 'masterTime1',
			width : "60",
			className : "center"
	    }, {
			data : 'memo1',
			width : "60",
			className : "center"
	    }, {
			data : 'isPass2',
			width : "40",
			className : "center" ,
			render : function(data, type, full) {
			       return YYDataUtils.getEnumName("BooleanType", data);
			}
	    }, {
			data : 'masterTime2',
			width : "60",
			className : "center"
	    }, {
			data : 'memo2',
			width : "60",
			className : "center"
	    }];
		
		
		/* 子表操作 */
		var _subTableYaoceCols = [{
			data : null,
			orderable : false,
			className : "center",
			width : "20"
		},{
			data : "uuid",
			orderable : false,
			className : "center",
			width : "40",
			visible : false,
			render : YYDataTableUtils.renderCheckCol
		},{
			data : "uuid",
			orderable : false,
			className : "center",
			width : "30",
			render : function(data, type, full) {
				return "<div class='btn-group rap-btn-actiongroup'>"
				+ "<button id='yy-btn-edit-row' class='btn btn-xs btn-info edit' data-rel='tooltip' title='编辑'><i class='fa fa-edit'></i>编辑</button>"
				+ "</div>";
	        }
		}, {
			data : 'type',
			width : "60",
			visible : false,
			className : "center"
	    }, {
			data : 'pointno',
			width : "30",
			className : "center"
	    }, {
			data : 'pointname',
			width : "60",
			className : "center"
	    }, {
			data : 'masterVal1',
			width : "60",
			className : "center"
	    }, {
			data : 'masterTime1',
			width : "60",
			className : "center"
	    }, {
			data : 'stationVal1',
			width : "60",
			className : "center"
	    }, {
			data : 'stationTime1',
			width : "60",
			className : "center"
	    }, {
			data : 'absVal1',
			width : "60",
			className : "center",
			render : function(data, type, full) {
				 if(data!=null){
			    	   return data+"%";
			       }else{
			    	   return "";
			       }
			}
	    }, {
			data : 'isPass1',
			width : "40",
			className : "center" ,
			render : function(data, type, full) {
			       return YYDataUtils.getEnumName("BooleanType", data);
			}
	    }, {
			data : 'memo1',
			width : "60",
			className : "center"
	    }, {
			data : 'masterVal2',
			width : "60",
			className : "center"
	    }, {
			data : 'masterTime2',
			width : "60",
			className : "center"
	    }, {
			data : 'stationVal2',
			width : "60",
			className : "center"
	    }, {
			data : 'stationTime2',
			width : "60",
			className : "center"
	    }, {
			data : 'absVal2',
			width : "60",
			className : "center",
			render : function(data, type, full) {
			       if(data!=null){
			    	   return data+"%";
			       }else{
			    	   return "";
			       }
			}
	    }, {
			data : 'isPass2',
			width : "40",
			className : "center",
			render : function(data, type, full) {
			       return YYDataUtils.getEnumName("BooleanType", data);
			}
	    }, {
			data : 'memo2',
			width : "60",
			className : "center"
	    }];
		
		/* 子表操作 */
		var _subTableYaokongCols = [{
			data : null,
			orderable : false,
			className : "center",
			width : "20"
		},{
			data : "uuid",
			orderable : false,
			className : "center",
			width : "40",
			visible : false,
			visible : false,
			render : YYDataTableUtils.renderCheckCol
		},{
			data : "uuid",
			orderable : false,
			className : "center",
			width : "30",
			render : function(data, type, full) {
				return "<div class='btn-group rap-btn-actiongroup'>"
				+ "<button id='yy-btn-edit-row' class='btn btn-xs btn-info edit' data-rel='tooltip' title='编辑'><i class='fa fa-edit'></i>编辑</button>"
				+ "</div>";
	        }
		}, {
			data : 'type',
			width : "60",
			visible : false,
			className : "center"
	    }, {
			data : 'pointno',
			width : "30",
			className : "center"
	    }, {
			data : 'pointname',
			width : "60",
			className : "center"
	    }, {
			data : 'dyyxPointNum',
			width : "30",
			className : "center"
	    }, {
			data : 'dyyxPointName',
			width : "60",
			className : "center"
	    }, {
			data : 'ykPass1',
			width : "40",
			className : "center" ,
			render : function(data, type, full) {
			       return YYDataUtils.getEnumName("BooleanType", data);
			}
	    }, {
			data : 'masterTime1',
			width : "60",
			className : "center"
	    }, {
			data : 'yxPass1',
			width : "40",
			className : "center" ,
			render : function(data, type, full) {
			       return YYDataUtils.getEnumName("BooleanType", data);
			}
	    }, {
			data : 'stationTime1',
			width : "60",
			className : "center"
	    }, {
			data : 'memo1',
			width : "60",
			className : "center"
	    }, {
			data : 'ykPass2',
			width : "40",
			className : "center" ,
			render : function(data, type, full) {
			       return YYDataUtils.getEnumName("BooleanType", data);
			}
	    }, {
			data : 'masterTime2',
			width : "60",
			className : "center"
	    }, {
			data : 'yxPass2',
			width : "40",
			className : "center" ,
			render : function(data, type, full) {
			       return YYDataUtils.getEnumName("BooleanType", data);
			}
	    }, {
			data : 'stationTime2',
			width : "60",
			className : "center"
	    }, {
			data : 'memo2',
			width : "60",
			className : "center"
	    }];
		
		
		/* 子表操作 */
		var _subTableYaotiaoCols = [{
			data : null,
			orderable : false,
			className : "center",
			width : "20"
		},{
			data : "uuid",
			orderable : false,
			className : "center",
			width : "40",
			visible : false,
			render : YYDataTableUtils.renderCheckCol
		},{
			data : "uuid",
			orderable : false,
			className : "center",
			width : "30",
			render : function(data, type, full) {
				return "<div class='btn-group rap-btn-actiongroup'>"
				+ "<button id='yy-btn-edit-row' class='btn btn-xs btn-info edit' data-rel='tooltip' title='编辑'><i class='fa fa-edit'></i>编辑</button>"
				+ "</div>";
	        }
		}, {
			data : 'type',
			width : "60",
			visible : false,
			className : "center"
	    }, {
			data : 'pointno',
			width : "30",
			className : "center"
	    }, {
			data : 'pointname',
			width : "60",
			className : "center"
	    }, {
			data : 'masterVal1',
			width : "60",
			className : "center"
	    }, {
			data : 'masterTime1',
			width : "60",
			className : "center"
	    }, {
			data : 'stationVal1',
			width : "60",
			className : "center"
	    }, {
			data : 'stationTime1',
			width : "60",
			className : "center"
	    }, {
			data : 'absVal1',
			width : "60",
			className : "center"
	    }, {
			data : 'isPass1',
			width : "10",
			className : "center" ,
			render : function(data, type, full) {
			       return YYDataUtils.getEnumName("BooleanType", data);
			}
	    }, {
			data : 'memo1',
			width : "60",
			className : "center"
	    }, {
			data : 'masterVal2',
			width : "60",
			className : "center"
	    }, {
			data : 'masterTime2',
			width : "60",
			className : "center"
	    }, {
			data : 'stationVal2',
			width : "60",
			className : "center"
	    }, {
			data : 'stationTime2',
			width : "60",
			className : "center"
	    }, {
			data : 'absVal2',
			width : "60",
			className : "center"
	    }, {
			data : 'isPass2',
			width : "10",
			className : "center",
			render : function(data, type, full) {
			       return YYDataUtils.getEnumName("BooleanType", data);
			}
	    }, {
			data : 'memo2',
			width : "60",
			className : "center"
	    }];
	
		//刷新子表
		function onRefreshSub(t) {
			var formId=$(t).closest("form").attr("id");
			if(formId=='yy-form-subquery-yx'){
				_subYaoxinTableList.draw(); //服务器分页
			}else if(formId=='yy-form-subquery-yc'){
				_subYaoceTableList.draw(); //服务器分页
			}else if(formId=='yy-form-subquery-yk'){
				_subYaokongTableList.draw(); //服务器分页
			}else if(formId=='yy-form-subquery-yt'){
				_subYaotiaoTableList.draw(); //服务器分页
			}
		}
		//重置子表查询条件
		function onResetSub(t) {
			var formId=$(t).closest("form").attr("id");
			YYFormUtils.clearForm(formId);
			return false;
		}
		
		function fnDrawCallbackYx(){
			var pageLength = $('select[name="yy-table-yaoxin_length"]').val() || 10;
			_subYaoxinTableList.column(0).nodes().each(function(cell, i) {
				cell.innerHTML = i + 1 + (_pageNumberYx) * pageLength;
			});
		}
		function fnDrawCallbackYc(){
			var pageLength = $('select[name="yy-table-yaoce_length"]').val() || 10;
			_subYaoceTableList.column(0).nodes().each(function(cell, i) {
				cell.innerHTML = i + 1 + (_pageNumberYc) * pageLength;
			});
		}
		function fnDrawCallbackYk(){
			var pageLength = $('select[name="yy-table-yaokong_length"]').val() || 10;
			_subYaokongTableList.column(0).nodes().each(function(cell, i) {
				cell.innerHTML = i + 1 + (_pageNumberYk) * pageLength;
			});
		}
		function fnDrawCallbackYt(){
			var pageLength = $('select[name="yy-table-yaotiao_length"]').val() || 10;
			_subYaotiaoTableList.column(0).nodes().each(function(cell, i) {
				cell.innerHTML = i + 1 + (_pageNumberYt) * pageLength;
			});
		}
		
		var _pageNumberYx,_pageNumberYc,_pageNumberYt,_pageNumberYk;
		
		$(document).ready(function() {
			$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
			     //当切换tab时，强制重新计算列宽
			     $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
			} );
			
			YYUI.setUIAction('yy-table-yaoxin');//设置子表勾选全选
			YYUI.setUIAction('yy-table-yaoce');//设置子表勾选全选
			YYUI.setUIAction('yy-table-yaokong');//设置子表勾选全选
			YYUI.setUIAction('yy-table-yaotiao');//设置子表勾选全选
			$(".yy-btn-searchSub").bind('click', function(){onRefreshSub(this);});//快速查询
			$(".yy-searchbar-resetSub").bind('click', function(){onResetSub(this);});//清空
			$("#yy-btn-export").bind('click', onExport);//导出
			
			
			var yxFreshLoad=null;
			var ycFreshLoad=null;
			var ykFreshLoad=null;
			var ytFreshLoad=null;
			//子表
			_subYaoxinTableList = $('#yy-table-yaoxin').DataTable({
				"columns" : _subTableYaoxinCols,
				"createdRow" : selRowAction,
				//"scrollX" : true,
				"processing" : true,//加载时间长，显示加载中
				"paging" :false,
				"order" : [],
				"scrollX" : true,
				"processing" : false,
				"searching" : false,
				"serverSide" : true,
				"showRowNumber" : true,
				"pagingType" : "bootstrap_full_number",
				//"pageLength" : 15,
				"paging" : true,
				"fnDrawCallback" : fnDrawCallbackYx,//列对齐设置
				"ajax" : {
					"url" : '${serviceurlsub}/query?orderby=pointno@asc',
					"type" : 'POST',
					"sync":'false',
					"data" : function(d) {
						yxFreshLoad = layer.load(2);
						//d.orderby = getOrderbyParam(d);
						var _subqueryData = $("#yy-form-subquery-yx").serializeArray();
						if (_subqueryData == null)
							return;
						$.each(_subqueryData, function(index) {
							if (this['value'] == null || this['value'] == "")
								return;
							d[this['name']] = this['value'];
						});
					},
					"dataSrc" : function(json) {
						if(yxFreshLoad != null) {
							layer.close(yxFreshLoad);
						}
						_pageNumberYx = json.pageNumber;
						return json.records == null ? [] : json.records;
					}
				},
				"initComplete": function(settings, json) {
					if(yxFreshLoad != null) {
						layer.close(yxFreshLoad);
					}
				}
			});
			//子表
			_subYaoceTableList = $('#yy-table-yaoce').DataTable({
				"columns" : _subTableYaoceCols,
				"createdRow" : selRowAction,
				//"scrollX" : true,
				"processing" : true,//加载时间长，显示加载中
				"paging" :false,
				"order" : [],
				"scrollX" : true,
				"processing" : false,
				"searching" : false,
				"serverSide" : true,
				"showRowNumber" : true,
				"pagingType" : "bootstrap_full_number",
				//"pageLength" : 15,
				"paging" : true,
				"fnDrawCallback" : fnDrawCallbackYc,//列对齐设置
				"ajax" : {
					"url" : '${serviceurlsub}/query?orderby=pointno@asc',
					"type" : 'POST',
					"sync":'false',
					"data" : function(d) {
						ycFreshLoad = layer.load(2);
						//d.orderby = getOrderbyParam(d);
						var _subqueryData = $("#yy-form-subquery-yc").serializeArray();
						if (_subqueryData == null)
							return;
						$.each(_subqueryData, function(index) {
							if (this['value'] == null || this['value'] == "")
								return;
							d[this['name']] = this['value'];
						});
					},
					"dataSrc" : function(json) {
						if(ycFreshLoad != null) {
							layer.close(ycFreshLoad);
						}
						_pageNumberYc = json.pageNumber;
						return json.records == null ? [] : json.records;
					}
				},
				"initComplete": function(settings, json) {
					if(ycFreshLoad != null) {
						layer.close(ycFreshLoad);
					}
				}
			});
			//子表
			_subYaokongTableList = $('#yy-table-yaokong').DataTable({
				"columns" : _subTableYaokongCols,
				"createdRow" : selRowAction,
				//"scrollX" : true,
				"processing" : true,//加载时间长，显示加载中
				"paging" :false,
				"order" : [],
				"scrollX" : true,
				"processing" : false,
				"searching" : false,
				"serverSide" : true,
				"showRowNumber" : true,
				"pagingType" : "bootstrap_full_number",
				//"pageLength" : 15,
				"paging" : true,
				"fnDrawCallback" : fnDrawCallbackYk,//列对齐设置
				"ajax" : {
					"url" : '${serviceurlsub}/query?orderby=pointno@asc',
					"type" : 'POST',
					"sync":'false',
					"data" : function(d) {
						ykFreshLoad = layer.load(2);
						//d.orderby = getOrderbyParam(d);
						var _subqueryData = $("#yy-form-subquery-yk").serializeArray();
						if (_subqueryData == null)
							return;
						$.each(_subqueryData, function(index) {
							if (this['value'] == null || this['value'] == "")
								return;
							d[this['name']] = this['value'];
						});
					},
					"dataSrc" : function(json) {
						if(ykFreshLoad != null) {
							layer.close(ykFreshLoad);
						}
						_pageNumberYk = json.pageNumber;
						return json.records == null ? [] : json.records;
					}
				},
				"initComplete": function(settings, json) {
					if(ykFreshLoad != null) {
						layer.close(ykFreshLoad);
					}
				}
			});
			//子表
			/* _subYaotiaoTableList = $('#yy-table-yaotiao').DataTable({
				"columns" : _subTableYaotiaoCols,
				"createdRow" : selRowAction,
				//"scrollX" : true,
				"processing" : true,//加载时间长，显示加载中
				"paging" :false,
				"order" : [],
				"scrollX" : true,
				"processing" : false,
				"searching" : false,
				"serverSide" : true,
				"showRowNumber" : true,
				"pagingType" : "bootstrap_full_number",
				//"pageLength" : 15,
				"paging" : true,
				"fnDrawCallback" : fnDrawCallbackYt,//列对齐设置
				"ajax" : {
					"url" : '${serviceurlsub}/query?orderby=pointno@asc',
					"type" : 'POST',
					"sync":'false',
					"data" : function(d) {
						ytFreshLoad = layer.load(2);
						//d.orderby = getOrderbyParam(d);
						var _subqueryData = $("#yy-form-subquery-yt").serializeArray();
						if (_subqueryData == null)
							return;
						$.each(_subqueryData, function(index) {
							if (this['value'] == null || this['value'] == "")
								return;
							d[this['name']] = this['value'];
						});
					},
					"dataSrc" : function(json) {
						if(ytFreshLoad != null) {
							layer.close(ytFreshLoad);
						}
						_pageNumberYt = json.pageNumber;
						return json.records == null ? [] : json.records;
					}
				},
				"initComplete": function(settings, json) {
					if(ytFreshLoad != null) {
						layer.close(ytFreshLoad);
					}
				}
			}); */
			
			
			//按钮绑定事件
			bindEditActions();
			bindButtonAction();
			validateForms();
			setValue();
		});
		
		 //定义行点击事件
		function selRowAction(nRow, aData, iDataIndex) {
			$(nRow).dblclick(function() {
				//onViewDetailRow(aData, iDataIndex, nRow);
				console.info("dblclick");
			});
			
			 $('#yy-btn-edit-row', nRow).click(function() {
				 var t_type=aData.type;
				 var radioVal="";
				 console.info(t_type);
				 if(t_type=='yx'||t_type=='tx'){
					 radioVal = $("input[name='yxEditDevice']:checked").val();
				 }else if(t_type=='yc'||t_type=='tc'){
					 radioVal = $("input[name='ycEditDevice']:checked").val();
				 }else if(t_type=='yk'||t_type=='tk'){
					 radioVal = $("input[name='ykEditDevice']:checked").val();
				 }else if(t_type=='yt'||t_type=='tt'){
					 radioVal = $("input[name='ytEditDevice']:checked").val();
				 }
				 console.info(radioVal);
				 console.info(radioVal==2);
				 if(radioVal==1){
					 layer.open({
							title:"修改",
						    type: 2,
						    area: ['800px', '200px'],
						    shadeClose : false,
							shade : 0.8,
						    content: '${serviceurl}/toEditSub1?subId='+aData.uuid+"&type="+t_type
						});
				 }else if(radioVal==2){
						layer.open({
							title:"修改",
						    type: 2,
						    area: ['800px', '200px'],
						    shadeClose : false,
							shade : 0.8,
						    content: '${serviceurl}/toEditSub2?subId='+aData.uuid+"&type="+aData.type
						});
				 }else{
					 YYUI.promMsg('子表类型为'+aData.type+"不正确");
				 }
			});
		};

		//设置默认值
		function setValue() {
			if ('${openstate}' == 'add') {
				//
			} else if ('${openstate}' == 'edit') {
				$("select[name='testMethod']").val('${entity.testMethod}');
			}
		}
		
		function bindButtonAction(){
			$('#masterCharger-select-btn').on('click', function() {
				layer.open({
					type : 2,
					title : '请选择人员',
					shadeClose : false,
					shade : 0.8,
					area : [ '1000px', '90%' ],
					content : '${ctx}/sys/ref/refUserSel?callBackMethod=window.parent.callBackMasterCharger'
				});
			});
			
			$('#stationCharger-select-btn').on('click', function() {
				layer.open({
					type : 2,
					title : '请选择人员',
					shadeClose : false,
					shade : 0.8,
					area : [ '1000px', '90%' ],
					content : '${ctx}/sys/ref/refUserSel?callBackMethod=window.parent.callBackStationCharger'
				});
			});
		}

		//回调选择
		function callBackMasterCharger(data){
			$("#masterChargerUuid").val(data.uuid);
			$("#masterChargerName").val(data.username);
		}
		
		//回调选择
		function callBackStationCharger(data){
			$("#stationChargerUuid").val(data.uuid);
			$("#stationChargerName").val(data.username);
		}
		
		//表单校验
		function validateForms(){
			validata = $('#yy-form-edit').validate({
				onsubmit : true,
				rules : {
					'stationName' : {required : true},
					'versionNum1' : {required : true},
					'name' : {required : true,maxlength : 50},
					'masterTestMsg' : {maxlength : 200},
					'stationTestPerson' : {maxlength : 20}
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
			
			var posturl = "${serviceurl}/update";
				
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
		
		
		
		//加载子表
		function loadSubYaoxin(mainindex){
			var loadWaitLoad=layer.load(2);
			$.ajax({
				url : '${serviceurlsub}/query?orderby=pointno@asc',
				data : {"search_EQ_main.uuid":mainindex,"search_IN_type":'yx,tx'},//$("#yy-form-query").serializeArray(),
				dataType : 'json',
				type : 'post',
				success : function(data) {
					layer.close(loadWaitLoad);
					_subYaoxinTableList.clear();
					if(data.records!=null&&data.records.length>0){
						_subYaoxinTableList.rows.add(data.records);
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
			$.ajax({
				url : '${serviceurlsub}/query?orderby=pointno@asc',
				data : {"search_EQ_main.uuid":mainindex,"search_IN_type":'yc,tc'},//$("#yy-form-query").serializeArray(),
				dataType : 'json',
				type : 'post',
				success : function(data) {
					layer.close(loadWaitLoad);
					_subYaoceTableList.clear();
					if(data.records!=null&&data.records.length>0){
						_subYaoceTableList.rows.add(data.records);
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
			$.ajax({
				url : '${serviceurlsub}/query?orderby=pointno@asc',
				data : {"search_EQ_main.uuid":mainindex,"search_IN_type":'yk,tk'},//$("#yy-form-query").serializeArray(),
				dataType : 'json',
				type : 'post',
				success : function(data) {
					layer.close(loadWaitLoad);
					_subYaokongTableList.clear();
					if(data.records!=null&&data.records.length>0){
						_subYaokongTableList.rows.add(data.records);
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
			$.ajax({
				url : '${serviceurlsub}/query?orderby=pointno@asc',
				data : {"search_EQ_main.uuid":mainindex,"search_IN_type":'yt,tt'},//$("#yy-form-query").serializeArray(),
				dataType : 'json',
				type : 'post',
				success : function(data) {
					layer.close(loadWaitLoad);
					_subYaotiaoTableList.clear();
					if(data.records!=null&&data.records.length>0){
						_subYaotiaoTableList.rows.add(data.records);
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
		
		//导出
		function onExport(){
			YYUI.promMsg('正在导出，请稍后.',3000);
			window.location.href='${serviceurl}/exportByMainId?mainId=${entity.uuid}';
		}
	</script>
</body>
</html>
