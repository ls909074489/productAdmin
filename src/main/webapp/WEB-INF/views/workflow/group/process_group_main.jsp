<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serviceurl" value="${ctx}/process/group" />
<html>
<head>
<title>流程组</title>
</head>
<body>

	<div id="leftdiv" class="ui-layout-west">
		<div class="row-fluid">
			<div class="row-fluid" style="padding-bottom: 5px;">
				<div class="btn-group btn-group-solid">
					<!-- <button id="yy-expandAll" type="button" class="btn btn-sm blue">
						全展开
					</button>
					<button  id="yy-collapseAll" type="button" class="btn btn-sm green ">
						全折叠
					</button> -->
					<button id="yy-expandSon" type="button" class="btn btn-sm blue">展开</button>
					<button id="yy-collapseSon" type="button" class="btn btn-sm green ">折叠</button>
					<button id="yy-treeRefresh" type="button" class="btn btn-sm blue">刷新</button>
				</div>
			</div>
			<span class="span12"> <input name="searchTreeNode" id="searchTreeNode" class="search-query form-control"
				type="text" autocomplete="off" placeholder="查找...">
			</span>
		</div>
		<div id="ztree" class="ztree"></div>
	</div>

	<div id="maindiv" class="ui-layout-center">
		<div class="row yy-toolbar" id="yy-toolbar-list">
			<!-- <button id="yy-btn-refresh" class="btn blue btn-sm">
				<i class="fa fa-refresh"></i> 刷新
			</button> -->
			<button id="yy-btn-add" class="btn blue btn-sm">
				<i class="fa fa-plus"></i> 新增
			</button>
			<button id="yy-btn-edit" class="btn blue btn-sm">
				<i class="fa fa-edit"></i> 修改
			</button>
			<button id="yy-btn-remove" class="btn red btn-sm btn-info">
				<i class="fa fa-trash-o"></i> 删除
			</button>
		</div>
		<div class="row yy-toolbar hide" id="yy-toolbar-edit">
			<button id="yy-btn-save" class="btn blue btn-sm">
				<i class="fa fa-save"></i> 保存
			</button>
			<button id="yy-btn-cancel" class="btn blue btn-sm">
				<i class="fa fa-rotate-left" class="btn blue btn-sm"></i> 取消
			</button>
		</div>
		<div>
			<form id="yy-form-edit" class="form-horizontal">
				<input name="uuid" type="hidden" value=""> <input name="parentid" type="hidden" value="">
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label class="control-label col-md-4 required-tip">组代码</label>
							<div class="col-md-8">
								<input name="group_code" type="text" class="form-control">
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label class="control-label col-md-4 required-tip">组名称</label>
							<div class="col-md-8">
								<input name="group_name" type="text" class="form-control">
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label class="control-label col-md-4">是否启用</label>
							<div class="col-md-8">
								<select class="yy-input-enumdata form-control" id="isuse" name="isuse" data-enum-group="BooleanType"></select>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-8">
						<div class="form-group">
							<label class="control-label col-md-2">超链接</label>
							<div class="col-md-10">
								<input name="link" type="text" class="form-control">
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-8">
						<div class="form-group">
							<label class="control-label col-md-2">备注</label>
							<div class="col-md-10">
								<textarea rows="" cols="" name="description" class="form-control"></textarea>
							</div>
						</div>
					</div>
				</div>
				<!-- 系统信息 -->
				<%@include file="/WEB-INF/views/common/sys/sys_info.jsp"%>
			</form>
		</div>
	</div>

	<!-- 树功能脚本 -->
	<jsp:include page="/WEB-INF/views/common/ztreescript.jsp" flush="true">
		<jsp:param name="serviceurl" value="${serviceurl}" />
		<jsp:param name="dataTreeUrl" value="${serviceurl}/dataGroup" />
		<jsp:param name="onDblClickMethod" value="selfOnClick" />
		<jsp:param name="onClickMethod" value="selfOnClick" />
	</jsp:include>


	<script type="text/javascript">
		var validator;
		//绑定按钮事件
		function bindDefaultActions() {
			$('#yy-btn-add').bind('click', onAdd);
			$('#yy-btn-edit').bind('click', onEdit);
			$('#yy-btn-remove').bind('click', onRemove);
			$('#yy-btn-refresh').bind('click', onRefresh);
			$('#yy-btn-save').bind('click', onSave);
			$('#yy-btn-cancel').bind('click', onCancel);
		}

		//显示列表的ToolBar
		function showListToolBar() {
			$('#yy-toolbar-edit').addClass("hide");
			$('#yy-toolbar-list').removeClass("hide");
			YYFormUtils.lockForm('yy-form-edit');
			_isSelected = true;
		}

		//显示编辑的ToolBar
		function showEditToolBar() {
			$('#yy-toolbar-edit').removeClass("hide");
			$('#yy-toolbar-list').addClass("hide");
			YYFormUtils.unlockForm('yy-form-edit');
			_isSelected = false;
		}

		//新增
		function onAdd() {
			var pid;
			var node = getSelectedNodes();
			if (typeof (node) == 'undefined') {
				YYUI.promMsg("请先选择节点");
				return;
			} else {
				YYFormUtils.clearForm('yy-form-edit');
				pid = node.id;
				$("input[name='parentid']").val(pid);
				validator.resetForm();
				$(".error").removeClass("error");
			}
			showEditToolBar();

			$("select[name='isuse']").val("1");
		}

		//修改
		function onEdit() {
			var node = getSelectedNodes();
			if (typeof (node) == 'undefined') {
				YYUI.promMsg("请选择节点");
				return;
			}
			YYFormUtils.clearForm('yy-form-edit');
			showData(node);
			showEditToolBar();
		}

		//保存
		function onSave() {
			var posturl = "${serviceurl}/add";
			var isAdd = true;
			pk = $("#yy-form-edit input[name='uuid']").val();
			if (pk != "") {
				posturl = "${serviceurl}/update";
				isAdd = false;
			}
			if (!$("#yy-form-edit").valid())
				return false;
			var opt = {
				url : posturl,
				type : "post",
				success : function(data) {
					if (data.success == true) {
						onRefresh();
						showListToolBar();
						_selectedId = data.records[0].uuid;
					} else {
						YYUI.promAlert("保存失败：" + data.msg);
					}
				}
			}
			$("#yy-form-edit").ajaxSubmit(opt);
		}

		//取消
		function onCancel() {
			var node = getSelectedNodes();
			if (node) {
				showData(node);
			} else {
				YYFormUtils.clearForm('yy-form-edit');
			}
			showListToolBar();
		}

		//删除
		function onRemove() {
			var node = getSelectedNodes();
			if (typeof (node) == 'undefined') {
				YYUI.promMsg("请先选择节点");
				return;
			}
			/* if(!node.nodeData.islast){
				YYUI.promMsg("不能删除父节点！");
				return;
			} */
			var cNode = node.children;
			if (typeof (cNode) != 'undefined' && cNode.length > 0) {
				YYUI.promMsg("不能删除父节点！");
				return;
			}
			YYDataUtils.removeRecord("${serviceurl}/delete", node.id,
					onRefresh, true, true);
			YYFormUtils.clearForm('yy-form-edit');
		}

		//刷新
		function onRefresh() {
			_zTree.reAsyncChildNodes(null, 'refresh', false);
			_selectedId = null;
			YYFormUtils.clearForm('yy-form-edit');
		}

		//绑定参照事件
		function bindDefActions() {
		}

		//界面初始化
		function showData(treeNode) {
			$("input[name='uuid']").val(treeNode.nodeData.uuid);
			$("input[name='parentid']").val(treeNode.nodeData.parentid);
			$("input[name='group_code']").val(treeNode.nodeData.group_code);
			$("input[name='group_name']").val(treeNode.nodeData.group_name);
			
			$("select[name='isuse']").val(treeNode.nodeData.isuse);
			$("input[name='link']").val(treeNode.nodeData.link);

			$("textarea[name='description']")
					.val(treeNode.nodeData.description);
			$("input[name='creatorname']").val(treeNode.nodeData.creatorname);
			$("input[name='createtime']").val(treeNode.nodeData.createtime);
			$("input[name='modifiername']").val(treeNode.nodeData.modifiername);
			$("input[name='modifytime']").val(treeNode.nodeData.modifytime);
		}

		function selfOnClick(event, treeId, treeNode) {
			if (!_isSelected) {
				YYUI.promMsg("编辑状态，不能操作节点。");
				_zTree.selectNode(_selectedId);
				return false;
			}
			_selectedId = treeNode;
			_zTree.selectNode(treeNode);
			showData(treeNode);
		}

		$(document).ready(function() {
			var yy_layout = $("body").layout({
				applyDefaultStyles : true,
				west : {
					size : 250
				}
			});

			//绑定按钮
			bindDefaultActions();
			//绑定参照事件
			bindDefActions();

			YYFormUtils.lockForm('yy-form-edit');

			//验证 表单
			validateForms();
			

		});

		//验证表单
		function validateForms() {
			validator = $('#yy-form-edit').validate({
				rules : {
					group_name : {
						required : true,
						maxlength : 20
					},
					group_code : {
						required : true,
						maxlength : 20
					},
					description : {
						maxlength : 200
					}
				}
			});
		}
	</script>

</body>
</html>