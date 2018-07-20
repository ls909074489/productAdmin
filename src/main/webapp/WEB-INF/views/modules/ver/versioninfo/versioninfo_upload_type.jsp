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
					<div class="row yy-toolbar" id="subListToolId" style="">
						<button id="addNewSubYaoxin" class="btn blue btn-sm" type="button">
							<i class="fa fa-plus"></i> 添加设备
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
			</div>
	</div>

		<script type="text/javascript">
			var yxArr=new Array();
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
		
			$(document).ready(function() {
				
				$("#yy-import-btn-confirm").bind('click', confirmImport);//确定导入
				$("#yy-btn-cancel").bind('click', function(){
					var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
					parent.layer.close(index); //再执行关闭 
				});
				
				//添加子表
				$("#addNewSubYaoxin").on('click',  function() {addNewSubFunc('tx');});//添加子表
				
				//子表
				_subYaoxinTableList = $('#yy-table-yaoxin').DataTable({
					"columns" : _subTableYaoxinCols,
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
				
				var subData = null;
				//所有需要保存的参数
				subData = {
					"yxsubList" :yxsubList
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
					    	   layer.close(listWaitLoad);
							   YYUI.failMsg('查询失败，HTTP错误。');
					       },
					       success: function(data){
					    	   layer.close(listWaitLoad);
								if(data.success){
									var records=data.records;
									if(records!=null&&records.length>0){
										for(var i=0;i<records.length;i++){
											yxArr.push(records[i].bdev);
										}
									}else{
										
									}
								}else{
									yxArr=new Array();
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
					$('#'+timestampid).select2();
				}
			}
			
			function onRemoveRowFunc(t){
				var nRow = $(t).closest('tr')[0];
				var t_vtype=$(t).attr("vtype");
				var row = _subYaoxinTableList.row(nRow);
				row.remove().draw();
			}
			
			//改变设备
			function changeDev(t){
				$(t).parent().parent().find("[name='vname']").val($(t).val());
			}
		</script>
	</div>
</body>
</html>