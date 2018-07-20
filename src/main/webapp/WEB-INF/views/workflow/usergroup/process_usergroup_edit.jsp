<%@ page contentType="text/html;charset=UTF-8"%>
<div class="page-content hide" id="yy-page-edit">
	<div class="row yy-toolbar">
		<button id="yy-btn-save" class="btn blue btn-sm">
			<i class="fa fa-save"></i> 保存
		</button>
		<button id="yy-btn-relate-user" class="btn btn-sm btn-info">
			<i class="fa fa-user"></i> 关联用户
		</button>
		<button id="yy-btn-cancel" class="btn blue btn-sm">
			<i class="fa fa-rotate-left"></i> 取消
		</button>
	</div>

	<div class="row">
		<form id="yy-form-edit" class="form-horizontal yy-form-edit">
			<input name="uuid" type="hidden"> 
			<input name="processgroup.uuid" type="hidden">
			<div class="row">
					<div class="col-md-4" id="outletsCodeDiv">
						<div class="form-group">
							<label class="control-label col-md-4 required">用户组编码</label>
							<div class="col-md-8">
								<input id="org_code" name="code" type="text" class="form-control" value="">
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label class="control-label col-md-2 required" id="outletsNameLabel">用户组名称</label>
							<div class="col-md-10">
								<input class="form-control " name="name" type="text">
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label class="control-label col-md-2 required" id="outletsNameLabel">是否默认审核组</label>
							<div class="col-md-10">
								<select name="grouptype" class="yy-input-enumdata form-control" data-enum-group="BooleanType"></select>
							</div>
						</div>
					</div>
			</div>
			<div class="row">
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label col-md-2">描述</label>
						<div class="col-md-10">
							<!-- <input class="form-control" name="description"  type="text"> -->
							<textarea id="roleDescId" rows="" cols="" class="form-control" name="description" style="width: 100%;"></textarea>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div class="row" id="assDivIdEdit">
		<div class="tabbable">
			<ul id="myTab" class="nav nav-tabs">
				<li class="active"><a href="#user-relation" id="user-relation-tab" data-toggle="tab"> <i
						class="blue icon-magnet bigger-110"></i> 关联用户
				</a></li>
			</ul>

			<div class="tab-content">
				<!-- 已关联用户 【开始】 -->
				<div class="tab-pane active" id="user-relation">
					<div class="row-fluid">
						<table id="tableList-AssUser" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th>操作</th>
									<th>登录账号</th>
									<th>工号</th>
									<th>用户名</th>
									<th>所属单元</th>
									<th>最后登录IP</th>
									<th>最后登录时间</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
				<!-- 已关联用户 【结束】 -->
			</div>
		</div>
	</div>
</div>