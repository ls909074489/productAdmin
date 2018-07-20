<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serviceurl" value="${ctx}/ver/versioninfo"/>
<c:set var="servicesuburl" value="${ctx}/ver/versioninfosub"/>
<html>
<head>
<title>导入</title>
</head>
<body>
	<div id="yy-page" class="container-fluid page-container">
	<div class="page-content" id="yy-page-list">
			<div class="row yy-toolbar">
				<button id="yy-import-btn-confirm" class="btn blue btn-sm btn-info" >
					提&nbsp;&nbsp;&nbsp;&nbsp;交
				</button>
				<button id="yy-btn-cancel" class="btn blue btn-sm">
					<i class="fa fa-rotate-left"></i> 取消
				</button>
			</div>
			<div class="row" style="">
				<form id="yy-form-edit">
					<div id="imexlate-text-upload">
						<div class="imexlate-text-input" id="imexlate-text-input-span"><span id="imexlate-text-input-text">&nbsp;&nbsp;&nbsp;&nbsp;标准信息点表：</span></div>
						<div class="imexlate-text-input">
							<input type="hidden" name="mainindex" id="mainindex" value="${mainindex}">
							<select class="yy-input-enumdata form-control" id="sId" name="sId" onchange="changeSid(this);">
								<option value=""></option>
								<c:forEach items="${standardList}" var="clist">
									<option value="${clist.mainindex}">${clist.masver}
									<c:if test="${!empty clist.subver}">(${clist.subver})</c:if>
									</option>
								</c:forEach>
							</select>
						</div>
						<%-- <div class="imexlate-text-input" id="imexlate-text-input-span"><span id="imexlate-text-input-text">&nbsp;&nbsp;&nbsp;&nbsp;标准映射表：</span></div>
						<div class="imexlate-text-input">
							<select class="yy-input-enumdata form-control" id="mtId" name="mtId">
								<option value=""></option>
								<c:forEach items="${mappingTableList}" var="clist">
									<option value="${clist.uuid}">${clist.name}</option>
								</c:forEach>
							</select>
						</div> --%>
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
										<i class="fa fa-plus"></i> 添加遥信
									</button>
								</div>
								<table id="yy-table-yaoxin" class="yy-table">
									<thead>
										<tr>
											<th>序号</th>
											<!-- <th class="table-checkbox" style=""><input type="checkbox"
												class="group-checkable" data-set="#yy-table-yaoxin .checkboxes" />
											</th> -->
											<th>操作</th>
											<th>子表类型</th>
											<th>设备标识</th>
											<th>用户输入的设备名称</th>
											<th>开关标识</th>
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
								<table id="yy-table-yaoce" class="yy-table">
									<thead>
										<tr>
											<th>序号</th>
											<!-- <th class="table-checkbox" style=""><input type="checkbox"
												class="group-checkable" data-set="#yy-table-yaoce .checkboxes" />
											</th> -->
											<th>操作</th>
											<th>子表类型</th>
											<th>设备标识</th>
											<th>用户输入的设备名称</th>
											<th>开关标识</th>
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
								<table id="yy-table-yaokong" class="yy-table">
									<thead>
										<tr>
											<th>序号</th>
											<!-- <th class="table-checkbox" style=""><input type="checkbox"
												class="group-checkable" data-set="#yy-table-yaokong .checkboxes" />
											</th> -->
											<th>操作</th>
											<th>子表类型</th>
											<th>设备标识</th>
											<th>用户输入的设备名称</th>
											<th>开关标识</th>
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
								<table id="yy-table-yaotiao" class="yy-table">
									<thead>
										<tr>
											<th>序号</th>
											<!-- <th class="table-checkbox" style=""><input type="checkbox"
												class="group-checkable" data-set="#yy-table-yaotiao .checkboxes" />
											</th> -->
											<th>操作</th>
											<th>子表类型</th>
											<th>设备标识</th>
											<th>用户输入的设备名称</th>
											<th>开关标识</th>
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
	</div>

		<script type="text/javascript">
			var yxArr=new Array();
			var ycArr=new Array();
			var ykArr=new Array();
			var ytArr=new Array();
			/* 子表操作 */
			var _subTableYaoxinCols = [{
				data : null,
				orderable : false,
				className : "center",
				width : "20"
			} ,{
				data : "uuid",
				orderable : false,
				className : "center",
				width : "40",
				render : function(data, type, full) {
					return "<div class='yy-btn-actiongroup'>"
					+ "<button id='yy-btn-remove-row' type='button' onclick='onRemoveRowFunc(this);' vtype='tx' class='btn btn-xs btn-danger delete' data-rel='tooltip' title='删除'><i class='fa fa-trash-o'></i></button>"
					+ "</div>";
				}
			} , {
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
		    },{
				data : 'ts',
				width : "60",
				className : "center",
				render : function(data, type, full) {
					return '<select id="'+data+'" name="vdev" class="yy-select2-single form-control" onchange="changeDev(this);"></select>';
				}
		    },{
				data : 'vname',
				width : "60",
				className : "center",
				render : function(data, type, full) {
					if(data==null){
						data="";
					}
					return '<input class="form-control" value="'+ data + '" name="vname">';
				}
		    },{
				data : 'vswitch',
				width : "60",
				className : "center",
				render : function(data, type, full) {
					if(data==null){
						data="";
					}
					return '<input class="form-control" value="'+ data + '" name="vswitch">';
				}
		    }];
			
			/* 子表操作 */
			var _subTableYaoceCols = [{
				data : null,
				orderable : false,
				className : "center",
				width : "20"
			} ,{
				data : "uuid",
				orderable : false,
				className : "center",
				width : "40",
				render : function(data, type, full) {
					return "<div class='yy-btn-actiongroup'>"
					+ "<button id='yy-btn-remove-row' type='button' onclick='onRemoveRowFunc(this);' vtype='tc' class='btn btn-xs btn-danger delete' data-rel='tooltip' title='删除'><i class='fa fa-trash-o'></i></button>"
					+ "</div>";
				}
			} , {
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
		    },{
				data : 'ts',
				width : "60",
				className : "center",
				render : function(data, type, full) {
					return '<select id="'+data+'" name="vdev" class="yy-select2-single form-control" onchange="changeDev(this);"></select>';
				}
		    },{
				data : 'vname',
				width : "60",
				className : "center",
				render : function(data, type, full) {
					if(data==null){
						data="";
					}
					return '<input class="form-control" value="'+ data + '" name="vname">';
				}
		    },{
				data : 'vswitch',
				width : "60",
				className : "center",
				render : function(data, type, full) {
					if(data==null){
						data="";
					}
					return '<input class="form-control" value="'+ data + '" name="vswitch">';
				}
		    }];
			
			/* 子表操作 */
			var _subTableYaokongCols = [{
				data : null,
				orderable : false,
				className : "center",
				width : "20"
			} ,{
				data : "uuid",
				orderable : false,
				className : "center",
				width : "40",
				render : function(data, type, full) {
					return "<div class='yy-btn-actiongroup'>"
					+ "<button id='yy-btn-remove-row' type='button' onclick='onRemoveRowFunc(this);' vtype='tk' class='btn btn-xs btn-danger delete' data-rel='tooltip' title='删除'><i class='fa fa-trash-o'></i></button>"
					+ "</div>";
				}
			} , {
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
		    },{
				data : 'ts',
				width : "60",
				className : "center",
				render : function(data, type, full) {
					return '<select id="'+data+'" name="vdev" class="yy-select2-single form-control" onchange="changeDev(this);"></select>';
				}
		    },{
				data : 'vname',
				width : "60",
				className : "center",
				render : function(data, type, full) {
					if(data==null){
						data="";
					}
					return '<input class="form-control" value="'+ data + '" name="vname">';
				}
		    },{
				data : 'vswitch',
				width : "60",
				className : "center",
				render : function(data, type, full) {
					if(data==null){
						data="";
					}
					return '<input class="form-control" value="'+ data + '" name="vswitch">';
				}
		    }];
			
			/* 子表操作 */
			var _subTableYaotiaoCols = [{
				data : null,
				orderable : false,
				className : "center",
				width : "20"
			} ,{
				data : "uuid",
				orderable : false,
				className : "center",
				width : "40",
				render : function(data, type, full) {
					return "<div class='yy-btn-actiongroup'>"
					+ "<button id='yy-btn-remove-row' type='button' onclick='onRemoveRowFunc(this);' vtype='tt' class='btn btn-xs btn-danger delete' data-rel='tooltip' title='删除'><i class='fa fa-trash-o'></i></button>"
					+ "</div>";
				}
			} , {
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
		    },{
				data : 'ts',
				width : "60",
				className : "center",
				render : function(data, type, full) {
					return '<select id="'+data+'" name="vdev" class="yy-select2-single form-control" onchange="changeDev(this);"></select>';
				}
		    },{
				data : 'vname',
				width : "60",
				className : "center",
				render : function(data, type, full) {
					if(data==null){
						data="";
					}
					return '<input class="form-control" value="'+ data + '" name="vname">';
				}
		    },{
				data : 'vswitch',
				width : "60",
				className : "center",
				render : function(data, type, full) {
					if(data==null){
						data="";
					}
					return '<input class="form-control" value="'+ data + '" name="vswitch">';
				}
		    }];
		
			$(document).ready(function() {
				$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
				     //当切换tab时，强制重新计算列宽
				     $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
				} );
				
				$("#yy-import-btn-confirm").bind('click', confirmImport);//确定导入
				$("#yy-btn-cancel").bind('click', function(){
					var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
					parent.layer.close(index); //再执行关闭 
				});
				
				//添加子表
				$("#addNewSubYaoxin").on('click',  function() {addNewSubFunc('tx');});//添加子表
				$("#addNewSubYaoce").on('click',  function() {addNewSubFunc('tc');});//添加子表
				$("#addNewSubYaokong").on('click',  function() {addNewSubFunc('tk');});//添加子表
				$("#addNewSubYaotiao").on('click',  function() {addNewSubFunc('tt');});//添加子表
				
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
				
				 //验证表单
				validateForms();
			});
			
			
			//确定导入
			function confirmImport(){
				var mainValidate=$('#yy-form-edit').valid();
				if(!mainValidate){
					return false;
				}
				
				var posturl = "${serviceurl}/uploadExplainType";

				//保存新增的子表记录 
				var yxsubList = new Array();
		        var yx_table = $("#yy-table-yaoxin").dataTable();
		        var yx_rows = yx_table.fnGetNodes();
		        for(var i = 0; i < yx_rows.length; i++){
		        	yxsubList.push(getRowData(yx_rows[i]));
		        }
		        var ycsubList = new Array();
		        var yc_table = $("#yy-table-yaoce").dataTable();
		        var yc_rows = yc_table.fnGetNodes();
		        for(var i = 0; i < yc_rows.length; i++){
		        	ycsubList.push(getRowData(yc_rows[i]));
		        }
		        var yksubList = new Array();
		        var yk_table = $("#yy-table-yaokong").dataTable();
		        var yk_rows = yk_table.fnGetNodes();
		        for(var i = 0; i < yk_rows.length; i++){
		        	yksubList.push(getRowData(yk_rows[i]));
		        }
		        var ytsubList = new Array();
		        var yt_table = $("#yy-table-yaotiao").dataTable();
		        var yt_rows = yt_table.fnGetNodes();
		        for(var i = 0; i < yt_rows.length; i++){
		        	ytsubList.push(getRowData(yt_rows[i]));
		        }
				
				/* var formData = new FormData();
				formData.append("mainindex", "${mainindex}");
				formData.append("mtId", $("#mtId").val());
				formData.append("sId", $("#sId").val());
				formData.append("yxsubList", yxsubList);
				formData.append("ycsubList", ycsubList);
				formData.append("yksubList", yksubList);
				formData.append("ytsubList", ytsubList);
				var importLoad = layer.msg('数据生成中...', {icon:16,time: 500*1000});
				$.ajax( {
					url : posturl,
					//data: formData,
					data : subData,
		            cache: false,
		            contentType: false,
		            processData: false,
		            type: 'POST',     
					success : function(data) {
						if(data.success){
							layer.close(importLoad);
							data.fileName=$("#fileName").val();
							YYUI.succMsg("操作成功,重新加载页面。");
							var _method = '${callBackMethod}';
							if (_method) {
								eval(_method + "(data)");
							} else {
								window.parent.callBackMethod(data);
							}
							
							var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
							parent.layer.close(index); //再执行关闭 
						} else{
							layer.close(importLoad);
							YYUI.promAlert("操作错误：" + data.msg);
						}
					},
					"error" : function(XMLHttpRequest, textStatus, errorThrown) {
						layer.close(importLoad);
						YYUI.failMsg("操作失败。");
					}
				}); */
				
				var subData = null;
				//所有需要保存的参数
				subData = {
					"yxsubList" :yxsubList,
					"ycsubList" :ycsubList,
					"yksubList" :yksubList,
					"ytsubList" :ytsubList
				};
				var saveWaitLoad=layer.load(2);
				var opt = {
					url : posturl,
					type : "post",
					data : subData,
					success : function(data) {
						layer.close(saveWaitLoad);
						if (data.success == true) {
							layer.close(saveWaitLoad);
							data.fileName=$("#fileName").val();
							YYUI.succMsg("操作成功,重新加载页面。");
							var _method = '${callBackMethod}';
							if (_method) {
								eval(_method + "(data)");
							} else {
								window.parent.callBackMethod(data);
							}
							var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
							parent.layer.close(index); //再执行关闭 
						} else {
							layer.close(saveWaitLoad);
							YYUI.promAlert("操作错误：" + data.msg);
						}
					}
				}
				$("#yy-form-edit").ajaxSubmit(opt);
			}	
			
			//提交数据时需要特殊处理checkbox的值
			function getRowData(nRow){
				var data = $('input, select', nRow).not('input[type="checkbox"]').serialize();
				//处理checkbox的值
				$('input[type="checkbox"]',nRow).each(function(){
					var checkboxName = $(this).attr("name");
					var checkboxValue = "false";
					if(this.checked){
						checkboxValue = "true";
					}
					data = data+"&"+checkboxName+"="+checkboxValue;
				});
				return data;
			}
			
			function validateForms(){
				$('#yy-form-edit').validate({
					rules : {
		           		'mtId' : {required : true},
		           		'sId' : {required : true}
					}	
				}); 
			}
			
			
			//改变标准点表
			function changeSid(t){
				var t_sId=$("#sId").val();
				if(t_sId==null||t_sId==''){
					yxArr=new Array();
					ycArr=new Array();
					ykArr=new Array();
					ytArr=new Array();
					YYUI.failMsg("请选择标准信息点表");
				}else{
					var listWaitLoad=layer.load(2);
					$.ajax({
					       url: '${servicesuburl}/queryStandardByMainId?orderby=pointno@asc',
					       type: 'post',
					       data:{'search_EQ_mainindex':$(t).val()},
					       dataType: 'json',
					       error: function(){
								yxArr=new Array();
								ycArr=new Array();
								ykArr=new Array();
								ytArr=new Array();
					    	   layer.close(listWaitLoad);
							   YYUI.failMsg('查询失败，HTTP错误。');
					       },
					       success: function(data){
					    	   layer.close(listWaitLoad);
								if(data.success){
									var records=data.records;
									if(records!=null&&records.length>0){
										//var jsonData =null;
										for(var i=0;i<records.length;i++){
											//jsonData = jQuery.parseJSON(records[i].content);
											if(records[i].type=='tx'){
												//yxArr.push(jsonData.bdev);
												yxArr.push(records[i].bdev);
											}else if(records[i].type=='tc'){
												//ycArr.push(jsonData.bdev);
												ycArr.push(records[i].bdev);
											}else if(records[i].type=='tk'){
												//ykArr.push(jsonData.bdev);
												ykArr.push(records[i].bdev);
											}else if(records[i].type=='tt'){
												//ytArr.push(jsonData.bdev);
												ytArr.push(records[i].bdev);
											}
										}
									}else{
										
									}
								}else{
									yxArr=new Array();
									ycArr=new Array();
									ykArr=new Array();
									ytArr=new Array();
									YYUI.failMsg("查询失败.");
								}
					       }
					   });
				}
			}
			
			function addNewSubFunc(t){
				var t_sId=$("#sId").val();
				if(t_sId==null||t_sId==''){
					YYUI.promMsg('请先选择标准信息点表');
				}else{
					var timestampid = new Date().getTime();
					timestampid=timestampid+""+parseInt(10*Math.random());//生成随机数
					var newData = [ {
						uuid : '',
						type : '',
						ts : timestampid,
						vdev : '',
						vname : '',
						vswitch : ''
					} ];
					
					if(t=='tx'){
						var nRow = _subYaoxinTableList.rows.add(newData).draw().nodes()[0];//获得第一个tr节点
						_subYaoxinTableList.on('order.dt search.dt',
						        function() {
							_subYaoxinTableList.column(0, {
								        search: 'applied',
								        order: 'applied'
							        }).nodes().each(function(cell, i) {
								        cell.innerHTML = i + 1;
							        });
						}).draw();
						$('#'+timestampid).append('<option value=""></option>');
						if(yxArr!=null&&yxArr.length>0){
							for(var i=0;i<yxArr.length;i++){
								$('#'+timestampid).append('<option value="'+yxArr[i]+'">'+yxArr[i]+'</option>');
							}
						}
					}else if(t=='tc'){
						var nRow = _subYaoceTableList.rows.add(newData).draw().nodes()[0];//获得第一个tr节点
						_subYaoceTableList.on('order.dt search.dt',
						        function() {
							_subYaoceTableList.column(0, {
								        search: 'applied',
								        order: 'applied'
							        }).nodes().each(function(cell, i) {
								        cell.innerHTML = i + 1;
							        });
						}).draw();
						$('#'+timestampid).append('<option value=""></option>');
						if(ycArr!=null&&ycArr.length>0){
							for(var i=0;i<ycArr.length;i++){
								$('#'+timestampid).append('<option value="'+ycArr[i]+'">'+ycArr[i]+'</option>');
							}
						}
					}else if(t=='tk'){
						var nRow = _subYaokongTableList.rows.add(newData).draw().nodes()[0];//获得第一个tr节点
						_subYaokongTableList.on('order.dt search.dt',
						        function() {
							_subYaokongTableList.column(0, {
								        search: 'applied',
								        order: 'applied'
							        }).nodes().each(function(cell, i) {
								        cell.innerHTML = i + 1;
							        });
						}).draw();
						$('#'+timestampid).append('<option value=""></option>');
						if(ykArr!=null&&ykArr.length>0){
							for(var i=0;i<ykArr.length;i++){
								$('#'+timestampid).append('<option value="'+ykArr[i]+'">'+ykArr[i]+'</option>');
							}
						}
					}else if(t=='tt'){
						var nRow = _subYaotiaoTableList.rows.add(newData).draw().nodes()[0];//获得第一个tr节点
						_subYaotiaoTableList.on('order.dt search.dt',
						        function() {
							_subYaotiaoTableList.column(0, {
								        search: 'applied',
								        order: 'applied'
							        }).nodes().each(function(cell, i) {
								        cell.innerHTML = i + 1;
							        });
						}).draw();
						$('#'+timestampid).append('<option value=""></option>');
						if(ytArr!=null&&ytArr.length>0){
							for(var i=0;i<ytArr.length;i++){
								$('#'+timestampid).append('<option value="'+ytArr[i]+'">'+ytArr[i]+'</option>');
							}
						}
					}
					$('#'+timestampid).select2();
				}
			}
			
			function onRemoveRowFunc(t){
				var nRow = $(t).closest('tr')[0];
				var t_vtype=$(t).attr("vtype");
				if(t_vtype=='tx'){
					var row = _subYaoxinTableList.row(nRow);
					row.remove().draw();
				}else if(t_vtype=='tc'){
					var row = _subYaoceTableList.row(nRow);
					row.remove().draw();
				}else if(t_vtype=='tk'){
					var row = _subYaokongTableList.row(nRow);
					row.remove().draw();
				}else if(t_vtype=='tt'){
					var row = _subYaotiaoTableList.row(nRow);
					row.remove().draw();
				}		
			}
			
			//改变设备
			function changeDev(t){
				$(t).parent().parent().find("[name='vname']").val($(t).val());
			}
		</script>
	</div>
</body>
</html>