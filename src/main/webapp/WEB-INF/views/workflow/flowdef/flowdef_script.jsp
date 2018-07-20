<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	var _tableCols = [ {
		data : "uuid",
		orderable : false,
		className : "center",
		/* visible : false, */
		width : "30",
		render : YYDataTableUtils.renderCheckCol
	}, {
		data : "uuid",
		className : "center",
		orderable : false,
		render : YYDataTableUtils.renderActionCol,
		width : "50"
	}, {
		data : 'billtype',
		width : "80",
		className : "center",
		orderable : true
	}, {
		data : 'businesstype',
		width : "80",
		className : "center",
		orderable : true
	}, {
		data : 'flowcode',
		width : "80",
		className : "center",
		orderable : true
	}, {
		data : 'action',
		width : "80",
		className : "center",
		orderable : true
	}, {
		data : 'flowtype',
		width : "80",
		className : "center",
		orderable : true
	}, {
		data : 'flowgroup',
		width : "80",
		className : "center",
		orderable : true
	} ];

	function onRefresh() {
		//非服务器分页
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
			"createdRow" : YYDataTableUtils.setActions,
			"processing" : true,//加载时间长，显示加载中
			"order" : []
		});

		$('input.global_filter').on('keyup click', function() {
			filterGlobal();
		});

		//按钮绑定事件
		bindButtonActions();
		//加载数据
		loadList();
	});

	function showData(data) {
		$("input[name='uuid']").val(data.uuid);
		$("input[name='billtype']").val(data.billtype);
		$("input[name='action']").val(data.action);
		$("input[name='businesstype']").val(data.businesstype);
		$("input[name='flowtype']").val(data.flowtype);
		$("input[name='flowgroup']").val(data.flowgroup);
		$("input[name='flowcode']").val(data.flowcode);
		$("input[name='creatorname']").val(data.creatorname);
		$("input[name='createtime']").val(data.createtime);
		$("input[name='modifiername']").val(data.modifiername);
		$("input[name='modifytime']").val(data.modifytime);
	}
</script>
