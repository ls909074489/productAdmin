<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	var _queryData,_roleFuncTree,_roleFuncTreeView;
	var _tableListAssUser,_tableListAssUserView;//角色下的用户列表
	var _pageType="edit";
	var validator; 
	var _tableCols = [ {
		data : "uuid",
		orderable : false,
		className : "center",
		/*visible : false, */
		width : "20",
		render : YYDataTableUtils.renderCheckCol
	},{
		data : "uuid",
		className : "center",
		orderable : false,
		"render": function (data,type,row,meta ) {
			return renderRowActionCol(data,type,row,meta);
         },
		width : "120"
	}, {
		data : 'code',
		width : "15%",
		className : "center",
		orderable : false
	}, {
		data : 'name',
		width : "25%",
		className : "center",
		orderable : false
	}, {
		data : 'description',
		width : "40%",
		className : "center",
		orderable : false
	}];

	//角色下的用户列表表头 编辑
	var _tableColsAssUser = [{
		data : "uuid",
		className : "center",
		orderable : false,
		render : function(data, type, full) {
					return "<div class='btn-group rap-btn-actiongroup'>"  + 
							"<button id='rap-btn-remove-row-edit' class='btn btn-xs btn-danger' data-rel='tooltip' title='删除'><i class='fa fa-trash-o'></i></button>" + "</div>";
				  },
		width : "5"
	},{
		data : 'loginname',
		width : "10%",
		orderable : true
	},{
		data : 'jobnumber',
		width : "25%",
		orderable : true
	}, {
		data : 'username',
		width : "10%",
		orderable : true
	}, {
		data : 'orgname',
		width : "20%",
		orderable : true
	}, {
		data : 'last_ip',
		width : "15%",
		orderable : true
	}, {
		data : 'last_time',
		width : "15%",
		orderable : true
	}];
	
	//角色下的用户列表表头 查看
	var _tableColsAssUserView = [{
		data : 'loginname',
		width : "10%",
		orderable : true
	},{
		data : 'jobnumber',
		width : "25%",
		orderable : true
	}, {
		data : 'username',
		width : "10%",
		orderable : true
	}, {
		data : 'orgname',
		width : "20%",
		orderable : true
	}, {
		data : 'last_ip',
		width : "15%",
		orderable : true
	}, {
		data : 'last_time',
		width : "15%",
		orderable : true
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
				"order": [] 
			});
			
			$('input.global_filter').on('keyup click', function() {
				filterGlobal();
			});
			$('input.column_filter').on('keyup click', function() {
				filterColumn($(this).parents('div').attr('data-column'));
			});
			
			//角色绑定的用户列表-编辑
			_tableListAssUser = $('#tableList-AssUser').DataTable({
				"columns": _tableColsAssUser,
				"createdRow" : function(nRow, aData, iDataIndex) {
					$('#rap-btn-remove-row-edit', nRow).click(function() {
						onRemoveRowEdit(aData, iDataIndex,nRow);
					});
				},
				"order": [] 
			});
			//角色绑定的用户列表-查看
			_tableListAssUserView = $('#tableList-AssUserView').DataTable({
				"columns": _tableColsAssUserView,
				"createdRow" : function(nRow, aData, iDataIndex) {
					$('#rap-btn-remove-row-edit', nRow).click(function() {
						onRemoveRowEdit(aData, iDataIndex,nRow);
					});
				},
				"order": [] 
			});
			
			
			//按钮绑定事件
			bindButtonActions();
			
			$("#yy-btn-add").bind('click', onAdd);//新增
			$("#yy-btn-relate-user").bind('click', onSelectUser);//分配角色的用户
			
			$("#yy-btn-save").bind("click", function() {//点击保存角色按钮
				onSaveRole(true);
			});
			$("#yy-btn-search").bind('click', onQuery);//查询
			$('#yy-btn-cancel').bind('click', onCancel);//编辑页面返回
			
			$('#yy-btn-backtolist').bind('click', onCancel);//查看页面返回
			
			loadList();
			//验证 表单
			validateForms();
		});
	
	   //验证表单
		function validateForms(){
			validator =$('#yy-form-edit').validate({
				rules : {
					code : {required : true,maxlength:20,minlength:2},
					name: {required : true,maxlength:20,minlength:2},
					description: {maxlength:200}
				}
			}); 
		}
	 
	 //定义行点击事件
	function roleRowAction(nRow, aData, iDataIndex) {
		$(nRow).dblclick(function() {
			onViewDetailRow(aData, iDataIndex, nRow);
		});
	
		$('#yy-btn-view-row', nRow).click(function() {
			_pageType="view";
			$("#funcAction").hide();
			$("#funcActionView").hide();
			onViewDetailRow(aData, iDataIndex, nRow);
		});
	
		$('#yy-btn-edit-row', nRow).click(function() {
			_pageType="edit";
			$("#funcAction").hide();
			$("#funcActionView").hide();
			onEditRow(aData, iDataIndex, nRow);
		});
	
		$('#yy-btn-remove-row', nRow).click(function() {
			removeRecord('${serviceurl}/delUserGroup', [ aData.uuid ], function() {
				_tableList.row(nRow).remove().draw(false);
			});
		});
		
		
		$('#yy-btn-grant-row', nRow).click(function() {
			 layer.open({
					type : 2,
					title : '请选择用户',
					shadeClose : false,
					shade : 0.8,
					area : [ '90%', '96%' ],
					content : '${serviceurl}/selectUser?groupId='+aData.uuid
				});
		});
	};
	 
	 //显示自定义的行按钮
	  function renderRowActionCol(data, type, full) {
			return "<div class='yy-btn-actiongroup'>" 
	        + "<button id='yy-btn-grant-row' class='btn btn-xs btn-success' data-rel='tooltip' title='授权'><i class='fa fa-group'></i></button>"
			+ "<button id='yy-btn-view-row' class='btn btn-xs btn-success' data-rel='tooltip' title='查看'><i class='fa fa-search-plus'></i></button>"
			+ "<button id='yy-btn-edit-row' class='btn btn-xs btn-info' data-rel='tooltip' title='编辑'><i class='fa fa-edit'></i></button>"
			+ "<button id='yy-btn-remove-row' class='btn btn-xs btn-danger' data-rel='tooltip' title='删除'><i class='fa fa-trash-o'></i></button>"
			+ "</div>";
		}
		
	 
	 //点击添加
	 function onAdd(){
			hideAss();//隐藏关联用户和分配功能 
			$("#yy-btn-relate-user").hide();//隐藏关联用户按钮
			
			YYFormUtils.clearForm('yy-form-edit');
			validator.resetForm();
			$(".error").removeClass("error");
			
			YYUI.setEditMode();
	 }
	 
	 
	//角色分配用户
	 function onSelectUser(){
		 layer.open({
				type : 2,
				title : '请选择用户',
				shadeClose : false,
				shade : 0.8,
				area : [ '90%', '96%' ],
				content : '${serviceurl}/selectUser?groupId='+getObjId(), //iframe的url
				cancel: function(index){
					loadListAssUser();
			    }
			});
	 }
	 
	 
	//取消编辑，返回列表视图
	function onCancel() {
		YYUI.setListMode();
		//hideAss();
		onRoleRefresh();
	}
	//保存，isClose 是否保存后关闭视图，否为继续增加状态
	function onSaveRole(isClose) {
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
			$("input[name='name']").val(data.name);			
			$("input[name='code']").val(data.code);			
			$("select[name='grouptype']").val(data.grouptype);	
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
		assigned("edit");
	}
	 
	//点击查看按钮
	function onViewDetailRow(data, rowidx) {
		YYFormUtils.clearForm('yy-form-detail');
		showData(data);
		YYUI.setDetailMode();
		assigned("view");
	}
	 
	function onSearchRoleFuncTree(){
		var searchValue = $('#searchTreeNodeEdit').val();
		YyZTreeUtils.searchNode(_roleFuncTree,searchValue);
	}
	
	function onSearchRoleFuncTreeView(){
		var searchValue = $('#searchTreeNodeView').val();
		YyZTreeUtils.searchNode(_roleFuncTreeView,searchValue);
	}
	
	//绑定按钮事件
 	function bindButtonActions() {
		$('#yy-btn-refresh').bind('click', onRefresh);
	}
	
	 
	function assigned(operType) {
		if(operType=="view"){//查看
			loadListAssUserView();
			 showAss();
		}else{//编辑
			loadListAssUser();
			showAss();
		}
	}
	
	//显示 关联用户和分配功能
	function showAss(){
		 $("#assDivIdEdit").show();
		 $("#assDivIdView").show();
	}
	//隐藏 关联用户和分配功能
	function hideAss(){
		 $("#assDivIdEdit").hide();
		 $("#assDivIdView").hide();
	}
	
	//角色下用户    编辑
	function loadListAssUser() {
		$.ajax({
			url : '${serviceurl}/findByProcessUserGroupId',
			data : {
				'groupId' : getObjId()
			},
			dataType : 'json',
			success : function(data) {
				_tableListAssUser.clear();
				_tableListAssUser.rows.add(data.records);
				_tableListAssUser.draw();
			}
		});
	}
	//角色下用户   查看
	function loadListAssUserView() {
		$.ajax({
			url : '${serviceurl}/findByProcessUserGroupId',
			data : {
				'groupId' : getObjId()
			},
			dataType : 'json',
			success : function(data) {
				_tableListAssUserView.clear();
				_tableListAssUserView.rows.add(data.records);
				_tableListAssUserView.draw();
			}
		});
	}
	
	
	//获取当前角色的id
	function getObjId(){
		return $("input[name='uuid']").val();
	}
	
	
	//行操作：删除   
	//@data 行数据
	//@rowidx 行下标
	function onRemoveRowEdit(data, rowidx,row) {
		//询问框
		layer.confirm('确实要删除吗？', {
		    btn: ['确定','取消'] //按钮
		}, function(){
		    var groupId = getObjId();
			$.ajax({
				"dataType" : "json",
				"type" : "POST",
				"url" : "${ctx}/process/usergrouprelation/delUser",
				"data" : {
					"userId" : [data.uuid].toString(),
					"groupId" : groupId
				},
				"success" : function(data) {
					if (data.success) {
							//row.remove();
							loadListAssUser();
							YYUI.succMsg('操作成功!');
					} else {
						 YYUI.promAlert('操作失败:'+ data.msg);
					}
				},
				"error" : function(XMLHttpRequest, textStatus, errorThrown) {
					 YYUI.promAlert('操作失败!');
				}
			});
		}, function(){
		    
		});
	}
	
	
	

	//界面初始化
	function loadList() {
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
	
</script>
