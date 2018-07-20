<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	var _raplayout,_queryData,_roleFuncTree,_roleFuncTreeView;
	var _pageType="edit";
	var validator; 
	var _tableCols = [ {
		data : "uuid",
		orderable : false,
		className : "center",
		width : "15",
		render : YYDataTableUtils.renderCheckCol
	}, {
		data : "uuid",
		className : "center",
		orderable : false,
		width : "50",
		render: function (data, type, row, meta ) {
			return renderRoleActionCol(data,type,row,meta);
        }
	}, {
		data : 'code',
		width : "60",
		className : "center",
		orderable : false
	}, {
		data : 'name',
		width : "80",
		className : "center",
		orderable : false
	}, {
		data : 'flowtype',
		width : "60",
		className : "center",
		orderable : false,
		render : function(data, type, full) {
			return YYDataUtils.getEnumName("flow_type", data);
		}
	}, {
		data : 'flownode',
		width : "60",
		className : "center",
		orderable : false,
		render : function (data, type, full) {
			return YYDataUtils.getEnumName("flow_node", data);
		}
	}, {
		data : 'flowaction',
		width : "60",
		className : "center",
		orderable : false,
		render : function(data, type, full) {
			return YYDataUtils.getEnumName("flow_action", data);
		}
	}, {
		data : null,
		width : "70",
		className : "center",
		orderable : false,
		render : function(data, type, full) {
			if(data.usergroup != null){
				return data.usergroup.name;
			} else {
				return "";
			}
		}
	}, {
		data : 'nextflowcode',
		width : "70",
		className : "center",
		orderable : false
	}];

	
	function onRoleRefresh() {
		loadList();
	}
	function onRefresh() {
		loadList();
	}
	
	
	
	function filterGlobal() {
		$('#yy-table-list').DataTable().search($('#global_filter').val(),
				$('#global_regex').prop('checked'),
				$('#global_smart').prop('checked')).draw();
	}

	function filterColumn(i) {
		$('#yy-table-list').DataTable().column(i).search(
				$('#col' + i + '_filter').val(), false, true).draw();
	}
	
	 $(document).ready(function() {
			_tableList = $('#yy-table-list').DataTable({
				"columns" : _tableCols,
				"createdRow" : roleRowAction,
				//"dom" : '<"top">rt<"bottom"iflp><"clear">',
				"order" : [[2,"asc"]]
			});
			
			$('input.global_filter').on('keyup click', function() {
				filterGlobal();
			});
			$('input.column_filter').on('keyup click', function() {
				filterColumn($(this).parents('div').attr('data-column'));
			});
			
			
			//设置布局
			_raplayout=setBodyLayout();
			
			//按钮绑定事件
			bindButtonActions();
			
			$("#yy-btn-add").bind('click', onAdd);//新增
			
			$("#yy-btn-save").bind("click", function() {//点击保存角色按钮
				onSave(true);
			});
			$("#yy-btn-search").bind('click', onQuery);//查询
			$('#yy-btn-cancel').bind('click', onCancel);//编辑页面返回
			
			$('#yy-btn-backtolist').bind('click', onCancel);//查看页面返回
			
			
			//发货机构
			$('#yy-usergroup-select').on('click', function() {
				layer.open({
					type : 2,
					title : '请选择流程用户组',
					shadeClose : false,
					shade : 0.8,
					area : [ '800px', '90%' ],
					content : '${ctx}/sys/data/listProcessUserGroup?callBackMethod=window.parent.callBackSelectUserGroup' //iframe的url
				});
			});
			
			//验证 表单
			validateForms();
		});
	
	   //验证表单
		function validateForms(){
			validator =$('#yy-form-edit').validate({
				rules : {
					code : {required : true,maxlength:20},
					name: {required : true,maxlength:20,minlength:2},
					description: {maxlength:200}
				}
			}); 
		}
	 
	 //定义行点击事件
	function roleRowAction(nRow, aData, iDataIndex) {
		$(nRow).dblclick(function() {
			//onEditRow(aData, iDataIndex, nRow);
		});
	
		$('#yy-btn-view-row', nRow).click(function() {
			//_pageType="view";
			//$("#funcAction").hide();
			//$("#funcActionView").hide();
			//onViewDetailRow(aData, iDataIndex, nRow);
			_pageType="edit";
			$("#funcAction").hide();
			$("#funcActionView").hide();
			onEditRow(aData, iDataIndex, nRow);
		});
	
		$('#yy-btn-edit-row', nRow).click(function() {
			_pageType="edit";
			$("#funcAction").hide();
			$("#funcActionView").hide();
			onEditRow(aData, iDataIndex, nRow);
		});
	
		$('#yy-btn-remove-row', nRow).click(function() {
			removeRecord('${serviceurl}/delItem', [ aData.uuid ], function() {
				_tableList.row(nRow).remove().draw(false);
			});
		});
		
	};
	 
	 //显示自定义的行按钮
	  function renderRoleActionCol(data, type, full) {
			return "<div class='yy-btn-actiongroup'>" 
			+ "<button id='yy-btn-view-row' class='btn btn-xs btn-success' data-rel='tooltip' title='查看'><i class='fa fa-search-plus'></i></button>"
			+ "<button id='yy-btn-edit-row' class='btn btn-xs btn-info' data-rel='tooltip' title='编辑'><i class='fa fa-edit'></i></button>"
			+ "<button id='yy-btn-remove-row' class='btn btn-xs btn-danger' data-rel='tooltip' title='删除'><i class='fa fa-trash-o'></i></button>"
			+ "</div>";
		}
		
	 
	 //点击添加
	 function onAdd(){
		 var treeId = getSelectedTreeId();
			if (treeId==null||treeId == "") {
				layer.alert("请选择节点");
				return false;
			}

			$("#yy-btn-relate-user").hide();//隐藏关联用户按钮
			
			YYFormUtils.clearForm('yy-form-edit');

			$("input[name='processgroup.uuid']").val(treeId);
			//$("#yy-form-edit").validate().resetForm();
			validator.resetForm();
			$(".error").removeClass("error");
			
			YYUI.setEditMode();
			_raplayout.hide("west");
			
			$("select[name='flowtype']").val("1");
			$("select[name='flownode']").val("2");
			$("select[name='flowaction']").val("approve");
			$("select[name='flowsend']").val("1");
	 }
	 
	 
	//取消编辑，返回列表视图
	function onCancel() {
		YYUI.setListMode();
		_raplayout.show("west");
		onRoleRefresh();
	}
	 
	// zTree 当前选择的节点
	function getSelectedTreeId() {
		var treeId;
		var nodes = _zTree.getSelectedNodes();
		if (nodes.length > 0) {
			treeId = nodes[0].id;
		}
		return treeId;
	}
	
	//保存，isClose 是否保存后关闭视图，否为继续增加状态
	function onSave(isClose) {
		var flag = true;
		 if (!$('#yy-form-edit').valid())
			flag = false; 
		/* if(!checkRepeat($('#rap-form-edit input[name="roleCode"]'))){
			flag = false;
		} */
		if(flag==false){
			return false;
		}
		var posturl = "${serviceurl}/add";
		var pk = $("input[name='uuid']").val();
		if (pk != "" && typeof (pk) != "undefined")
			posturl = "${serviceurl}/update";
		var opt = {
			url : posturl,
			type : "post",
			success : function(data) {
				if (data.success) {
					onRoleRefresh();
					if (isClose) {
						YYUI.setListMode();
					} else {
						onAdd();
					}
					//doAfterSaveSuccess(data);
				} else {
					alert("保存出现错误：" + data.msg)
				}
			}
		}
		$("#yy-form-edit").ajaxSubmit(opt);
	}
	
	// _tableList: 表格对象， roleGroupId: 当前用户组ID
	function loadList() {
		_queryData= $("#yy-form-query").serializeArray();
		$.ajax({
			type : 'post',
			url : '${serviceurl}/query',
			dataType : 'json',
			data : _queryData,
			success : function(data) {
				_tableList.clear();
				_tableList.rows.add(data.records);
				_tableList.draw();
			}
		});
	}
	
	 function showData(data) {
		    $("#yy-btn-relate-user").show();//显示关联用户按钮
			$("input[name='uuid']").val(data.uuid);//
			$("input[name='processgroup.uuid']").val(data.processgroup.uuid);
			$("input[name='code']").val(data.code);
			$("input[name='name']").val(data.name);
			$("input[name='nextflowcode']").val(data.nextflowcode);
			
			$("select[name='flowsend']").val(data.flowsend);
			$("select[name='flowaction']").val(data.flowaction);
			$("select[name='flowtype']").val(data.flowtype);
			$("select[name='flownode']").val(data.flownode);
			
			$("input[name='url']").val(data.url);
			
			if(data.usergroup!=null){
				$("input[name='usergroup.uuid']").val(data.usergroup.uuid);
				$("input[name='usergroupName']").val(data.usergroup.name);
			}else{
				$("input[name='usergroup.uuid']").val();
				$("input[name='usergroupName']").val();
			}
			$("textarea[name='description']").val(data.description);
			
	}
	
	 
	 function onQuery(){
		 var rolegroupId = $("#search_processGroupId").val();
			if(rolegroupId==null||rolegroupId.length==0){
				layer.alert("请选择角色组");
				return false;
			}
			//获取查询数据，在表格刷新的时候自动提交到后台
			_queryData = $("#yy-form-query").serializeArray();
			loadList();
	 }
	 
	 //点击编辑按钮
	function onEditRow(aData, iDataIndex, nRow) {
		YYFormUtils.clearForm('yy-form-edit');
		showData(aData);
		YYUI.setEditMode();
	}
	 
	//点击查看按钮
	function onViewDetailRow(data, rowidx) {
		YYFormUtils.clearForm('yy-form-detail');
		showData(data);
		YYUI.setDetailMode();
	}
	 
	
	//绑定按钮事件
 	function bindButtonActions() {
		$('#yy-btn-refresh').bind('click', onRefresh);
	}
	
	 
	//获取当前角色的id
	function getObjId(){
		return $("input[name='uuid']").val();
	}
	
	
	
	//树点击事件
	function selfOnClick(event, treeId, treeNode){
		if (!_isSelected) {
			alert("编辑状态，不能操作节点。");
			_zTree.selectNode(_selectedId);
			return false;
		}
		$("#search_processGroupId").val(treeNode.id);
		_selectedId = treeNode;
		_zTree.selectNode(treeNode);
		showTable(treeNode);
	}
	
	

	//界面初始化
	function showTable(treeNode) {
		$("#search_LIKE_name").val("");
		_queryData = $("#yy-form-query").serializeArray();
		$.ajax({
			type : 'post',
			url : '${serviceurl}/query',
			dataType : 'json',
			data : _queryData,
			success : function(data) {
				_tableList.clear();
				_tableList.rows.add(data.records);
				_tableList.draw();
			}
		});
	}
	
	//回调选择流程用户组
	function callBackSelectUserGroup(data){
		$("#usergroupId").val(data.uuid);
		$("#usergroupName").val(data.name);
	}
</script>
