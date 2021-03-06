<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="serviceurl" value="${ctx}/ver/test1"/>
<html>
<head>
<title>测试111</title>
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
			</div>
			<div class="row yy-searchbar form-inline">
				<form id="yy-form-query">
					<label for="search_EQ_billstatus" class="control-label">xxx</label>
					<select class="yy-input-enumdata form-control" id="search_EQ_billstatus" 
						name="search_EQ_billstatus" data-enum-group="BillApplyStatus"></select>

					<label for="search_LIKE_name" class="control-label">xxxxx</label>
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
							<th>名称</th>
							<th>编码</th>
							<th>日期</th>
							<th>日期时间</th>
							<th>是否显示</th>
							<th>类别</th>
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
				data : "name",
				width : "100",
				className : "center",
				orderable : true
			},{
				data : "code",
				width : "100",
				className : "center",
				orderable : true
			},{
				data : "datecols",
				width : "100",
				className : "center",
				orderable : true
			},{
				data : "timecols",
				width : "100",
				className : "center",
				orderable : true
			},{
				data : "isDisplay",
				width : "100",
				className : "center",
				orderable : true
			},{
				data : "type",
				width : "100",
				className : "center",
				render : function(data, type, full) {
					return YYDataUtils.getEnumName("sys_sex", data);
				},
				orderable : true
			},{
				data : "type111",
				width : "100",
				className : "center",
				orderable : true
			}];


		//var _setOrder = [[5,'desc']];
		$(document).ready(function() {
			_queryData = $("#yy-form-query").serializeArray();
			bindListActions();
			serverPage(null);
		});
	</script>
</body>
</html>	

