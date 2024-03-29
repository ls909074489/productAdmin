<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serviceurl" value="${ctx}/ver/test1"/>
<c:set var="servicesuburl" value="${ctx}/ver/test1Sub"/>
<html>
<head>
<title>物料类型</title>
</head>
<body>
<div id="yy-page-detail" class="container-fluid page-container page-content">
		<div class="row yy-toolbar">
				<button id="yy-btn-backtolist" class="btn blue btn-sm">
					<i class="fa fa-rotate-left"></i> 返回
				</button>
		</div>
		<div>
			<form id="yy-form-detail" class="form-horizontal yy-form-detail">
				<input name="uuid" id="uuid" type="hidden" value="${entity.uuid}"/>
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label class="control-label col-md-4" >用户名</label>
							<div class="col-md-8" >
								<input name="name" id="name" type="text" value="${entity.name}" class="form-control">
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label class="control-label col-md-4" >111</label>
							<div class="col-md-8" >
								<input name="code" id="code" type="text" value="${entity.code}" class="form-control">
							</div>
						</div>
					</div>
				</div>

			</form>
		</div>
		<div class="tabbable-line">
			<ul class="nav nav-tabs ">
				<li class="active"><a href="#tab_15_1" data-toggle="tab">列表
				</a></li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active">
					<div class="row yy-toolbar" style="display: none;">
						<div role="form" class="form-inline">
							<form id="yy-form-subquery">	
								<input type="hidden" name="search_EQ_main.uuid" id="mainId" value="${entity.uuid}">	
								&nbsp;&nbsp;	
								<label for="search_LIKE_name" class="control-label">名称 </label>
								<input type="text" autocomplete="on" name="search_LIKE_name" id="search_LIKE_name" class="form-control input-sm">
								
								<label for="search_EQ_sex" class="control-label">性别 </label>
								<select class="yy-input-enumdata form-control" id="search_EQ_sex" name="search_EQ_sex" data-enum-group="sys_sex"></select>
								
								<button id="yy-btn-searchSub" type="button" class="btn btn-sm btn-info">
									<i class="fa fa-search"></i>查询
								</button>
								<button id="rap-searchbar-resetSub" type="reset" class="red">
									<i class="fa fa-undo"></i> 清空
								</button>	
							</form>
						</div>
					</div>
					<table id="yy-table-sublist" class="yy-table">
						<thead>
							<tr>
								<th>序号</th>	
								<th>22</th>
								<th>33</th>

							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 公用脚本 -->
	<%@include file="/WEB-INF/views/common/detailscript.jsp"%>
	
	
	<script type="text/javascript">
		var _subTableList;//子表
		var _deletePKs = new Array();//需要删除的PK数组
		var _columnNum;
		
		/* 子表操作 */
		var _subTableCols = [ {
				data : null,
				orderable : false,
				className : "center",
				width : "50"
			},{
				data : "subcode",
				width : "100",
				className : "center",
				orderable : true
			},{
				data : "subname",
				width : "100",
				className : "center",
				orderable : true
			}];

		 
		$(document).ready(function() {
			bindDetailActions();//綁定平台按鈕
			
			setValue();
			
			$("#yy-btn-searchSub").bind('click', onRefreshSub);//快速查询
			$("#yy-searchbar-resetSub").bind('click', onResetSub);//清空
			
		});
		

		//设置默认值
		function setValue(){
			if('${openstate}'=='add'){
				//$("input[name='billdate']").val('${billdate}');
			}else if('${openstate}'=='detail'){
				loadSubList();
			}
		}
		
		//刷新子表
		function onRefreshSub() {
			_subTableList.draw(); //服务器分页
		}
		//重置子表查询条件
		function onResetSub() {
			YYFormUtils.clearForm("yy-form-subquery");
			return false;
		}
		
		//加载从表数据 mainTableId主表Id
		function loadSubList(mainTableId) {
			_subTableList = $('#yy-table-sublist').DataTable({
				"columns" : _subTableCols,
				"createdRow" : YYDataTableUtils.setActions,
				"order" : [],//_setOrder  edit by liusheng
				"processing" : true,
				"retrieve": true,
				"searching" : false,
				"serverSide" : true,
				"showRowNumber" : true,
				"pagingType" : "bootstrap_full_number",
				"paging" : true,
				"fnDrawCallback" : fnDrawSubCallback,
				"ajax" : {
					"url" : '${servicesuburl}/query',
					"type" : 'POST',
					"data" : function(d) {
						d.orderby = getOrderbyParam(d);
						var _subqueryData = $("#yy-form-subquery").serializeArray();
						if (_subqueryData == null)
							return;
						$.each(_subqueryData, function(index) {
							if (this['value'] == null || this['value'] == "")
								return;
							d[this['name']] = this['value'];
						});
					},
					"dataSrc" : function(json) {
						_pageNumber = json.pageNumber;
						return json.records == null ? [] : json.records;
					}
				}
			});
		}
		
		function fnDrawSubCallback(){
			var pageLength = $('select[name="yy-table-list_length"]').val() || 10;
			_columnNum = _columnNum || 0;
			_subTableList.column(_columnNum).nodes().each(function(cell, i) {
				cell.innerHTML = i + 1+(_pageNumber)*pageLength;
			});
		}
		
		
		//服务器分页，排序
		function getOrderbyParam(d) {
			var orderby = d.order[0];
			if (orderby != null && null != _tableCols) {
				var dir = orderby.dir;
				var orderName = _tableCols[orderby.column].data;
				return orderName + "@" + dir;
			}
			return "uuid@desc";
		}
	</script>
</body>
</html>
